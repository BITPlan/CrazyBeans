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
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import cb.parser.PetalParser.ParseContext;
import cb.petal.PetalObject;

/**
 * pathMap handling
 * 
 * @author wf
 *
 */
public class PathMap {
  protected static Logger LOGGER = Logger.getLogger("cb.parser");
  // Max recursion depth
  public static int MAX_LOOPS=10;
      
  // where the path map is kept
  Map<String, String> pathMap;

  // true if this pathMap is from a windows registry file
  private boolean registry;

  /**
   * get the map
   * 
   * @return the pathMap
   */
  public Map<String, String> getPathMap() {
    return pathMap;
  }

  /**
   * set the map
   * 
   * @param pathMap
   *          - the map to set
   */
  public void setPathMap(Map<String, String> pathMap) {
    this.pathMap = pathMap;
  }

  public boolean isRegistry() {
    return registry;
  }

  /**
   * create me from a list of Name/Value parameters
   * 
   * @param nameValues
   */
  public PathMap(String... nameValues) {
    pathMap = new HashMap<String, String>();
    // create default Path Map entries
    this.pathMap.put("$CRS_HOME", "&");
    this.pathMap.put("$CURDIR", "&");
    // add given entries
    if (nameValues.length > 0) {
      if (nameValues.length % 2 != 0) {
        throw new IllegalArgumentException(
            "lenght of pathEntries should be even but is " + nameValues.length);
      }
      for (int i = 0; i < nameValues.length; i += 2) {
        String name = nameValues[i];
        String value = nameValues[i + 1];
        pathMap.put(name, value);
      }
    }
  }

  /**
   * construct me from the given map
   * 
   * @param pmap
   */
  public PathMap(Map<String, String> pmap) {
    this();
    for (Entry<String, String> pentry : pmap.entrySet()) {
      pathMap.put(pentry.getKey(), pentry.getValue());
    }
  }

  /**
   * create me from the given Property file
   * 
   * @param propFile
   * @throws Exception
   */
  public PathMap(File propFile) throws Exception {
    this(propFile, Charset.forName("UTF-8"));
  }

  /**
   * check whether a text is quoted
   * 
   * @param text
   * @return true if the text starts and ends with a double quote
   */
  public boolean isQuoted(String text) {
    boolean quoted = text.startsWith("\"") && text.endsWith("\"");
    return quoted;
  }

  /**
   * remove leading and trailing quotes fro the given text
   * 
   * @param text
   * @return the text with the two quotes removed
   */
  private String unQuote(String text) {
    if (isQuoted(text))
      return text.substring(1, text.length() - 1);
    else
      return text;
  }

  /**
   * create me from the given Property file
   * 
   * @param propFile
   * @param charSet
   *          - e.g. UTF-8 or UTF-16 for Windows registry
   * @throws Exception
   */
  public PathMap(File propFile, Charset charSet) throws Exception {
    this();
    Properties props = new Properties();
    FileInputStream propInput = new FileInputStream(propFile);
    InputStreamReader propReader = new InputStreamReader(propInput, charSet);
    props.load(propReader);
    registry = props.containsKey("Windows")
        && props.getProperty("Windows").startsWith("Registry Editor");

    for (Object keyObj : props.keySet()) {
      String key = keyObj.toString();
      String value = props.getProperty(key);
      // registry entries are quoted
      if (isQuoted(key)) {
        key = "$" + unQuote(key);
        value = unQuote(value);
        value = unComment(value);
      }
      pathMap.put(key, value);
    }
  }

  /**
   * remove comments from the given text
   * 
   * @param text
   *          with optional }# comment
   * @return the text without the comment
   */
  private String unComment(String text) {
    text = text.replaceAll("\\}#.*", "");
    return text;
  }

  /**
   * Resolve reference to external file, e.g.,
   * "$CURDIR\\ConsolidatedView\\ConsolidatedView.cat"
   *
   * @return file handle or null if file can not be located
   */
  public File resolveReference(String path, ParseContext context) {
    Integer loopCounter = new Integer(0);
    return resolveReference(path, context, loopCounter);
  }

  /**
   * resolve reference and avoid endless recursion by counting
   * 
   * @param path
   * @param context
   * @param loopCounter
   * @return the resolved reference
   */
  public File resolveReference(String path, ParseContext context,
      Integer loopCounter) {
    if (loopCounter>MAX_LOOPS) {
      error(context,String.format("resolve Reference looped more than %3d times to resolve %s - giving up - you might want to check your path map", MAX_LOOPS,path));
      return null;
    }
    if (context.getCurrentDir() == null) {
      error(context,String.format("Could not resolve reference to %s",path));
      return null;
    } else {
      StringTokenizer st = new StringTokenizer(path, "\\/");
      ArrayList<String> list = new ArrayList<String>();

      while (st.hasMoreTokens()) {
        list.add(st.nextToken());
      }

      StringBuffer new_path = new StringBuffer();

      for (Iterator<String> i = list.iterator(); i.hasNext();) {
        String str = (String) i.next();

        // do we need to resolve the string?
        if (str.startsWith("$")) {
          str = resolveVariables(str, context, loopCounter);
        }

        new_path.append(str);

        if (i.hasNext()) {
          new_path.append(File.separatorChar);
        }
      }

      return new File(new_path.toString());
    }
  }

  /**
   * resolve the variables in the given string
   * 
   * @param str
   *          - the string to resolve
   * @param context
   *          - the current directory
   * @param loopCounter
   * @return - the string with resolved Variables
   */
  private String resolveVariables(String str, ParseContext context,
      Integer loopCounter) {
    String pathEntry = null;

    if (pathMap != null) {
      pathEntry = (String) pathMap.get(str);
    }
    if (pathEntry == null) {
      error(context,String.format("Unknown variable %s",str));
    } else {
      if ("&".equals(pathEntry)) {
        str = context.getCurrentDir().getPath();
      } else if (pathEntry.startsWith(".") && !(pathEntry.startsWith(".."))) {
        str = context.getCurrentDir().getPath()
            + pathEntry.replaceFirst("\\./", "/");
      } else {
        str = pathEntry;
      }
    }
    // did new variables show up during dereferencing>?
    if (str.contains("$")) {
      File reference= this.resolveReference(str, context, ++loopCounter);
      if (reference!=null)
        str =reference.getPath();
    }
    return str;
  }

  /**
   * handle the given error with the given message in the given context
   * @param context
   * @param msg
   */
  private void error(ParseContext context,String msg) {
    msg = String.format("%s in %s", msg,
        context.getContext());
    if (PetalObject.strict) {
      throw new RuntimeException(msg);
    } else {
      LOGGER.log(Level.WARNING, msg);
    }
    
  }

  /**
   * get the  path map entry for the given key
   * @param key
   * @return the value looked up
   */
  public String get(String key) {
    String value=this.getPathMap().get(key);
    return value;
  }

  /**
   * return me as a string array
   * @return a string array
   */
  public String[] asStringArray() {
    Map<String, String> map = this.getPathMap();
    String[] strings=new String[map.size()*2];
    int i=0;
    for (String key:map.keySet()) {
      strings[i]=key;
      strings[i+1]=map.get(key);
      i+=2;
    }
    return strings;
  }

}
