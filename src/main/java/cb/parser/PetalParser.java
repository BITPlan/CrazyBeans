/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import cb.petal.Design;
import cb.petal.Petal;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalNodeList;
import cb.petal.PetalObject;
import cb.petal.StringLiteral;
import cb.petal.Value;
import cb.petal.Visitor;

/**
 * Optimized parser for Rational Rose Petal files.
 *
 * @version $Id: PetalParser.java,v 1.19 2005/05/09 20:11:58 moroff Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class PetalParser {
  protected static Logger LOGGER = Logger.getLogger("cb.parser");
  private Lexer lexer;

  private PetalNode current_parent = null;
  private Stack<PetalNode> parent_stack = new Stack<PetalNode>();
  private PathMap pathMap;

  public List<File> getFiles() {
    return files;
  }

  public void setFiles(List<File> files) {
    this.files = files;
  }

  /**
   * save the parent
   * 
   * @param new_parent
   */
  private void saveParent(PetalNode new_parent) {
    parent_stack.push(current_parent);
    current_parent = new_parent;
  }

  /**
   * restore the parent
   */
  private void restoreParent() {
    current_parent = (PetalNode) parent_stack.pop();
  }

  private static ObjectFactory factory = ObjectFactory.getInstance();
 
  /**
   * create a petal Parser with the given path Map
   * 
   * @param r
   * @param pathMap
   */
  public PetalParser(Reader r, PathMap pathMap) {
    this.lexer = new Lexer(r);
    // if a pathMap was supplied use it
    if (pathMap != null) {
      this.pathMap = pathMap;
    } else {
      this.pathMap=new PathMap();
    }
  }

  private List<java.lang.Class> ignored_nodes = Collections.EMPTY_LIST;

  /**
   * If the parser finds such a node while building the petal tree, the node
   * will be ignored and not added to the tree. E.g,
   * setIgnoredNodes(Diagram.class) will abandon all diagrams of the model.
   */
  public void setIgnoredNodes(java.lang.Class[] nodes) {
    ignored_nodes = new ArrayList(Arrays.asList(nodes));
  }

  public java.lang.Class[] getIgnoredNodes() {
    java.lang.Class[] nodes = new java.lang.Class[ignored_nodes.size()];
    ignored_nodes.toArray(nodes);
    return nodes;
  }

  /**
   * get filter flag for PetalNodes that should be ignored
   * 
   * @param obj
   *          - the object to check
   * @return - true if the object should be ignored
   */
  private boolean ignored(PetalNode obj) {
    for (Iterator i = ignored_nodes.iterator(); i.hasNext();) {
      java.lang.Class clazz = (java.lang.Class) i.next();
      if (clazz.isInstance(obj)) {
        return true;
      }
    }
    return false;
  }

  /**
   * match the givne kind and match
   * 
   * @param kind
   * @param match
   * @return
   */
  private Token match(int kind, String match) {
    Token t = lexer.getToken();

    if (t.kind != kind)
      throw new RuntimeException("Mismatch: Expected " + kind + " but got "
          + t.kind + " at line " + t.line);

    if ((match != null) && !match.equals(t.image))
      throw new RuntimeException("Mismatch: Expected " + match + " but got "
          + t.image + " at line " + t.line);

    return t;
  }

  private Token match(int kind) {
    return match(kind, null);
  }

  private ArrayList<String> list = new ArrayList<String>(); // Reused list to
                                                            // collect docs

  /**
   * (...)* wildcard
   * 
   * @return images
   */
  private ArrayList<String> matchAll(int kind) {
    list.clear();

    Token t = lexer.getToken();

    while (t.kind == kind) {
      list.add(t.image);
      t = lexer.getToken();
    }

    lexer.ungetToken(t);

    return list;
  }

  /**
   * [...] optional
   * 
   * @return image
   */
  private Token matchAny(int kind) {
    Token t = lexer.getToken();

    if (t.kind == kind)
      return t;
    else {
      lexer.ungetToken(t);
      return null;
    }
  }

  /**
   * create a parser from the given file name
   * 
   * @param file_name
   *          or "-" to denote stdin
   * @return - the parser for the given file name
   */
  public static PetalParser createParser(String file_name) {
    PetalParser parser = null;
    if ("-".equals(file_name)) {
      parser = PetalParser.createParser(System.in);
    } else {
      parser = PetalParser.createParser(file_name, null);
    }
    return parser;
  }

  /**
   * create a parser for the given file name and pathMap
   * 
   * @param file_name
   * @param pathMap
   * @return - the parser
   */
  public static PetalParser createParser(String file_name,
     PathMap pathMap) {
    return createParser(new File(file_name), pathMap);
  }

  /**
   * create parser for the given Url and pathMap
   * 
   * @param url
   * @param pathMap
   * @return -the parser
   */
  public static PetalParser createParser(java.net.URL url,
      PathMap pathMap) {
    try {
      return createParser(url.openStream(), pathMap);
    } catch (IOException e) {
      throw new RuntimeException(e.toString());
    }
  }

  private String model_name = "anonymous";

  /**
   * crate a parser for the given file
   * 
   * @param file
   * @return - the parser
   */
  public static PetalParser createParser(File file) {
    return createParser(file, null);
  }

  /**
   * create a parser for the given file and pathMap
   * 
   * @param file
   * @param pathMap
   * @return the parser
   */
  public static PetalParser createParser(File file,
      PathMap pathMap) {
    try {
      PetalParser parser = new PetalParser(new FileReader(file), pathMap);
      parser.getFiles().add(file);
      String name = file.getName();
      int index = name.lastIndexOf('.');

      if (index > 0)
        parser.model_name = name.substring(0, index);

      parser.setCurrentDir(file);

      return parser;
    } catch (IOException e) {
      throw new RuntimeException(e.toString());
    }
  }

  private File _current;
  private List<File> files=new ArrayList<File>();

  /**
   * Set current, i.e., the directory where the source MDL file is located so
   * that references to external .CAT files can be resolved.
   */
  public void setCurrentDir(File dir) {
    if (!dir.isDirectory()) {
      dir = dir.getParentFile();
    }

    _current = dir;
  }

  
  public static PetalParser createParser(Reader stream) {
    return createParser(stream, null);
  }

  /**
   * create a parser with the given path Map
   * @param stream
   * @param pathMap
   * @return the parser
   */
  public static PetalParser createParser(Reader stream,
      PathMap pathMap) {
    return new PetalParser(stream, pathMap);
  }

  /**
   * create a parser form the given input stream
   * 
   * @param stream
   * @return - return the parser
   */
  public static PetalParser createParser(InputStream stream) {
    return createParser(stream, null);
  }

  public static PetalParser createParser(InputStream stream,
      PathMap pathMap) {
    return new PetalParser(new InputStreamReader(stream), pathMap);
  }

  /**
   * Top level construct are always petal and design objects
   */
  public PetalFile parse() {
    PetalObject petal, design;
    PetalFile file = new PetalFile();
    current_parent = file;

    petal = parseObject();
    design = parseObject();

    file.setPetal((Petal) petal);
    file.setDesign((Design) design);

    file.setModelName(model_name);
    return file;
  }

  /*
   * Example: (object ClassView "Class" "Use Case View::Student" @76 location
   * (160, 176))
   */
  public PetalObject parseObject() {
    match(Lexer.LPAREN);
    Token.dispose(match(Lexer.IDENT, "object"));

    Token t1 = match(Lexer.IDENT);
    ArrayList docs = matchAll(Lexer.STRING);
    Token t3 = matchAny(Lexer.TAG);

    PetalNode parent = ignored(current_parent) ? null : current_parent;
    PetalObject obj = factory.createObject(parent, t1.image, docs,
        t3 == null ? null : t3.image);
    saveParent(obj);

    /*
     * List of properties
     */
    Token t4 = matchAny(Lexer.IDENT);

    while (t4 != null) {
      PetalNode prop = parseValue(false);

      if (prop != null)
        obj.addProperty(t4.image, prop);

      t4 = matchAny(Lexer.IDENT);
    }

    match(Lexer.RPAREN);

    restoreParent();
    Token.dispose(t1);
    Token.dispose(t3);
    Token.dispose(t4);

    String cat = obj.getPropertyAsString("file_name");

    /**
     * Load any external .cat file and "paste" it in
     */
    if (cat != null) {
      File file=null;
      if (pathMap==null) {
        LOGGER.log(Level.WARNING,"Could not resolve reference to " + cat
            + " pathMap is undefined - please create and set it");
      } else {
        file = pathMap.resolveReference(cat,_current);
      }
      if (file != null) {
        if (file.exists()) {
          PetalParser p = PetalParser.createParser(file, pathMap);
          p.current_parent = parent;
          p.parseObject();
          // recursively add files from sub parsers
          this.getFiles().addAll(p.getFiles());
          return p.parseObject();
        } else {
          // file does not exist ...
          String msg=String.format("%s does not exist",file.getPath());
          if (PetalObject.strict) {
            throw new RuntimeException(msg);
          } else {
            LOGGER.log(Level.WARNING, msg);
          }
        }
      }
    }

    if (!ignored(obj)) {
      obj.init();
      return obj;
    } else {
      return null;
    }
  }

  private static PetalNode RPAREN = new PetalNode() {
    public String getKind() {
      return null;
    }

    public int getChildCount() {
      return 0;
    }

    public void accept(Visitor v) {
    }
  };

  public PetalNode parseValue(boolean rparen_ok) {
    Token t = lexer.getToken();

    switch (t.kind) {
    case Lexer.STRING:
      Token.dispose(t);
      return factory.createString(t.image, false);

    case Lexer.MULTI_STRING:
      Token.dispose(t);
      return factory.createString(t.image, true);

    case Lexer.INTEGER:
      Token.dispose(t);
      return factory.createInteger(t.image);

    case Lexer.FLOAT:
      Token.dispose(t);
      return factory.createFloat(t.image);

    case Lexer.BOOLEAN:
      return factory.createBoolean(t.image);

    case Lexer.TAG:
      Token.dispose(t);
      return factory.createTag(t.image);

    case Lexer.LPAREN:
      Token t2 = lexer.getToken();

      switch (t2.kind) {
      case Lexer.IDENT:
        lexer.ungetToken(t2);
        lexer.ungetToken(t);

        if (t2.image.equals("object")) {
          return parseObject();
        } else if (t2.image.equals("list")) {
          return parseList();
        } else if (t2.image.equals("value")) {
          return parseValueObject();
        } else
          throw new RuntimeException("Unexpected " + t2.image + " after (");

      case Lexer.INTEGER:
        match(Lexer.COMMA);
        Token t3 = match(Lexer.INTEGER);
        match(Lexer.RPAREN);

        return factory.createLocation(t2.image, t3.image);

      case Lexer.STRING:
        Token t4 = match(Lexer.INTEGER);
        match(Lexer.RPAREN);
        return factory.createTuple(t2.image, t4.image);

      default:
        throw new RuntimeException("Unexpected " + t2.image + "after (");
      }

    default:
      if ((t.kind == Lexer.RPAREN) && rparen_ok)
        return RPAREN;
      else
        throw new RuntimeException("Unexpected " + t.image);
    }
  }

  /*
   * Example: (list unit_reference_list (object Module_Diagram "Main" quid
   * "35CB163B03CF"))
   */
  public PetalNodeList parseList() {
    match(Lexer.LPAREN);
    Token.dispose(match(Lexer.IDENT, "list"));

    Token t = matchAny(Lexer.IDENT);
    PetalNodeList list = factory.createList(t == null ? null : t.image);
    Token.dispose(t);

    PetalNode obj;

    while ((obj = parseValue(true)) != RPAREN)
      if (obj != null) // May be ignored
        list.add(obj);

    return list;
  }

  public Value parseValueObject() {
    match(Lexer.LPAREN);
    Token.dispose(match(Lexer.IDENT, "value"));
    Token t = match(Lexer.IDENT);
    Token t2 = lexer.getToken();
    StringLiteral str;

    switch (t2.kind) {
    case Lexer.STRING:
      str = factory.createString(t2.image, false);
      break;
    case Lexer.MULTI_STRING:
      str = factory.createString(t2.image, true);
      break;
    default:
      throw new RuntimeException("Unexpected " + t2.image + " in (value ...)");
    }

    match(Lexer.RPAREN);

    Token.dispose(t);
    Token.dispose(t2);
    return factory.createValue(t.image, str);
  }

  public static void main(String[] args) {
    parse(args, null);
  }

  /**
   * Utility method for main
   */
  public static PetalFile parse(String[] args, PathMap pathMap) {
    PetalParser parser;

    if (args.length == 0)
      parser = PetalParser.createParser(System.in, pathMap);
    else {
      try {
        URL url = new URL(args[0]);
        parser = PetalParser.createParser(url, pathMap);
      } catch (MalformedURLException e) {
        parser = PetalParser.createParser(args[0], pathMap);
      }
    }

    try {
      return parser.parse();
    } catch (Exception ex) {
      System.out.println("Exception at " + parser.lexer.getLine());
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * Utility method for main
   */
  public static PetalFile parse(String[] args) {
    return parse(args, null);
  }

  /**
   * get the pathMap
   * @return Returns the pathMap.
   */
  public PathMap getPathMap() {
    return pathMap;
  }

  /**
   * @param pathMap
   *          The pathMap to set.
   */
  public void setPathMap(PathMap pathMap) {
    this.pathMap = pathMap;
  }

  /**
   * are there any files that are newer then the given date?
   * @param date
   * @return true if any newer
   */
  public boolean filesNewerThen(Date date) {
    boolean newer=false;
    for (File file:files) {
      if (file.lastModified()>date.getTime()) {
        return true;
      }
    }
    return newer;
  }
}
