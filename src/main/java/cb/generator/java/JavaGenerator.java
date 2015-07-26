/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.generator.java;

import cb.generator.GeneratorVisitor;
import cb.parser.*;
import cb.util.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

import cb.petal.Association;
import cb.petal.Attribute;
import cb.petal.ClassAttribute;
import cb.petal.HasQuidu;
import cb.petal.Operation;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.QuidObject;
import cb.petal.RealizeRelationship;
import cb.petal.Role;
import cb.petal.UsesRelationship;

/**
 * Convert a petal file into a set of (Java) classes. This class is mainly
 * responsible for the traversal while the factory is responsible for creation
 * objects and setting up relationships.
 *
 * @version $Id: Generator.java,v 1.8 2001/11/01 15:56:48 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class JavaGenerator extends GeneratorVisitor {
  protected static Logger LOGGER = Logger.getLogger("cb.generator.java");
  public static final boolean debug=false;
  
  protected Factory factory = Factory.getInstance();
  private String suffix;
  private PiggybackVisitor visitor;
  private Map<String, List<Attribute>> taggedValueMap=new LinkedHashMap<String,List<Attribute>>();
  public Map<String, List<Class>> classesByPackage;

  /**
   * generate the java code
   * 
   * @param tree
   * @param dump
   *          where to dump generated files
   */
  public JavaGenerator(PetalFile tree, File dump) {
    this(tree, dump, ".java");
  }

  /**
   * generate the java code with the given suffix
   * 
   * @param tree
   * @param dump
   *          where to dump generated files
   * @param suffix
   *          e.g. java
   */
  public JavaGenerator(PetalFile tree, File dump, String suffix) {
    if (dump!=null)
      this.setDumpPath(dump.getPath());
    this.suffix = suffix;
    setTree(tree);
  }

  /**
   * generate java from the given dump_path
   * 
   * @param tree
   * @param dump_path
   */
  public JavaGenerator(PetalFile tree, String dump_path) {
    this(tree, new File(dump_path));
  }

  /**
   * @return class given by quid or null if it isn't a class
   */
  protected Class getClass(String quid) {
    Class clazz = (Class) factory.getObject(quid);

    if (clazz == null) {
      QuidObject obj = getTree().getQuidObject(quid);

      if (obj instanceof cb.petal.Class) {
        visit((cb.petal.Class) obj);
        clazz = (Class) factory.getObject(quid);
      }
    }

    return clazz;
  }

  protected Class getClass(HasQuidu obj) {
    return getClass(obj.getQuidu());
  }

  /**
   * @return containing class or null if it isn't a class
   */
  protected Class getParentClass(PetalObject obj) {
    return getClass(((QuidObject) obj.getParent()).getQuid());
  }

  public void visit(cb.petal.InheritanceRelationship rel) {
    Class c = getParentClass(rel);
    if (c != null)
      factory.addSuperClass(c, getClass(rel));
  }

  public void visit(RealizeRelationship rel) {
    Class c = getParentClass(rel);
    if (c != null)
      factory.addImplementedInterface(c, getClass(rel));
  }

  public void visit(UsesRelationship rel) {
    Class c = getParentClass(rel);
    if (c != null)
      factory.addUsedClass(c, getClass(rel), rel);
  }

  /**
   * visit an association
   */
  public void visit(Association assoc) {
    Role first = assoc.getFirstRole();
    Role second = assoc.getSecondRole();
    Class class1 = getClass(first);
    Class class2 = getClass(second);

    if (class1 != null && class2 != null) {
      cb.petal.Class assc = assoc.getAssociationClass();
      Class ac = null;
      if (assc != null)
        ac = getClass(assc.getQuid());

      factory.addAssociation(class1, first, class2, second, ac);
    }
  }

  /**
   * visite the given class
   */
  public void visit(cb.petal.Class clazz) {
    String quid = clazz.getQuid();

    if (factory.getObject(quid) == null) {
      Class cl = factory.createClass(clazz);
      factory.addObject(quid, cl);
    }
  }

  /**
   * visit the given attribute
   */
  public void visit(ClassAttribute attr) {
    Field f = factory.createField(attr);
    factory.addObject(attr.getQuid(), f);
    Class c = getParentClass(attr);
    factory.addField(c, f);
  }

  /**
   * visit the given operation
   */
  public void visit(Operation op) {
    Method m = factory.createMethod(op);
    factory.addObject(op.getQuid(), m);
    Class c = getParentClass(op);
    if (c != null)
      factory.addMethod(c, m);
  }

  /**
   * tagged Value handling
   */
  public void visit(Attribute attribute) {
    // go two steps up the hierarchy - the attribute is part of a set
    // PetalObject attributeSet = (PetalObject) attribute.getParent();
    // the parent of the set is the owner ...
    PetalNode parent = attribute.getParent();
    if (parent instanceof QuidObject) {
      QuidObject parentquidObj = (QuidObject) parent;
      String quid = parentquidObj.getQuid();
      if (debug)
        LOGGER.log(Level.INFO,"parent for attribute is "+parent.getClass().getName()+"("+quid+")");
      List<Attribute> attrs=null;
      if (taggedValueMap.containsKey(quid)) {
        attrs=taggedValueMap.get(quid);
      } else {
        attrs=new ArrayList<Attribute>();
        taggedValueMap.put(quid, attrs);
      }
      attrs.add(attribute);
    } else {
      if (debug)
        LOGGER.log(Level.INFO,"parent for attribute is "+parent.getClass().getName());
    }
  }

  /**
   * initialize my visitor
   */
  @Override
  public void init() {
    visitor = new PiggybackVisitor(this);
  }

  @Override
  public void start() throws Exception {
    getTree().accept(visitor);
    // pass2 tagged Value handling
    for (Entry<String, List<Attribute>> taggedValueEntry:taggedValueMap.entrySet()) {
       String quid=taggedValueEntry.getKey();
       NodeImpl node = (NodeImpl)factory.getObject(quid);
       if (node!=null)
         node.addTaggedValues(taggedValueEntry.getValue());
    }
  }

  @Override
  public void dump() throws IOException {
    for (@SuppressWarnings("rawtypes")
    Iterator i = factory.getObjects().iterator(); i.hasNext();) {
      Node n = (Node) i.next();

      if (n instanceof Class) {
        Class clazz = (Class) n;

        String path = clazz.getPackage().replace('.', File.separatorChar);
        File file = new File(new File(this.getDumpPath()), path);
        file.mkdirs();

        file = new File(file, File.separatorChar + clazz.getName() + suffix);

        FileOutputStream out = new FileOutputStream(file);
        PrintWriter stream = new PrintWriter(new OutputStreamWriter(out));

        clazz.dump(stream);
        stream.close();
        out.close();
      }
    }
  }

  /**
   * get the Classes
   * @return
   */
  public Map<String,List<Class>> getClassesByPackage() {
    if (classesByPackage == null) {
      classesByPackage = new TreeMap<String,List<Class>>();
      for (@SuppressWarnings("rawtypes")
      Iterator i = factory.getObjects().iterator(); i.hasNext();) {
        Node n = (Node) i.next();
        if (debug)
          LOGGER.log(Level.INFO, n.getClass().getName());
        if (n instanceof Class) {
          Class clazz = (Class) n;
          String pack=clazz.getPackage();
          List<Class> classes;
          if (classesByPackage.containsKey(pack)) {
            classes=classesByPackage.get(pack);
          } else {
            classes=new ArrayList<Class>();
            classesByPackage.put(pack,classes);
          }
          classes.add(clazz);
        }
      }
    }
    return classesByPackage;
  }

  /**
   * main routine to test from command line
   * 
   * @param args
   */
  public static void main(String[] args) {
    try {
      PetalFile tree = PetalParser.parse(args);
      String dump = cb.util.Constants.getTmp();
      JavaGenerator gen = new JavaGenerator(tree, dump);
      gen.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
