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

/**
 * pathMap handling
 * 
 * @author wf
 *
 */
public class PathMap {
  protected static Logger LOGGER = Logger.getLogger("cb.parser");

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
      }
      pathMap.put(key, value);
    }
  }

  /**
   * Resolve reference to external file, e.g.,
   * "$CURDIR\\ConsolidatedView\\ConsolidatedView.cat"
   *
   * @return file handle or null if file can not be located
   */
  public File resolveReference(String path, File currentDir) {
    if (currentDir == null) {
      LOGGER.log(Level.WARNING, "Could not resolve reference to " + path
          + ", use parser.setCurrentDir()");
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
          str=resolveVariables(str,currentDir);
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
   * @param str - the string to resolve
   * @param currentDir - the current directory
   * @return - the string with resolved Variables
   */
  private String resolveVariables(String str, File currentDir) {
    String pathEntry = null;

    if (pathMap != null) {
      pathEntry = (String) pathMap.get(str);
    }
    if (pathEntry == null) {
      throw new RuntimeException("Unknown variable " + str);
    } else {
      if ("&".equals(pathEntry)) {
        str = currentDir.getPath();
      } else if (pathEntry.startsWith(".")) {
        str = currentDir.getPath() + pathEntry.replaceFirst("\\./", "/");
      } else {
        str = pathEntry;
      }
    }
    // did new variables show up during dereferencing>?
    if (str.contains("$")) {
      str=this.resolveReference(str, currentDir).getPath();
    }
    return str;
  }

}
