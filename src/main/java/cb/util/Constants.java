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
package cb.util;

import cb.petal.LogicalCategory;
import cb.petal.PetalNode;
import cb.petal.UseCaseCategory;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Global constants and utility methods.
 *
 * @version $Id: Constants.java,v 1.14 2002/07/23 19:56:26 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Constants {
  public static final int TAB = 4; // Default number of indendation chars
  public static final String INDENT = "    ";

  private static boolean DOS_MODE = true;
  public static final String DOS = "\r\n";
  public static final String UNIX = "\n";

  public static final String VERSION = "1.0";

  private static final boolean isDOS() {
    return java.io.File.separatorChar == '\\'; // heuristic check
  }

  public static void setMode(boolean DOS) {
    Constants.DOS_MODE = DOS;
  }

  public static String getNewLine() {
    return DOS_MODE ? DOS : UNIX;
  }
  
  public enum TmpMode {
    tmp, TEMP, env, TempFile
  }

  /**
   * mode how to find temporary directory may be
   * tmp: just use /tmp
   * TEMP: original version checking for isDOS and using C:\\TEMP\\ if in that environment
   * env: get the tmp file from the environment variable
   * TempFile: use javas tempfile facility to find an internal temporary directory to use
   */
  public static TmpMode tempMode = TmpMode.TEMP;
  
  /**
   * get a temporary directory
   * @return the temporary directory
   */
  public static String getTmp() {
    return getTmp(TmpMode.TEMP);
  }

  /**
   * get a temporary directory
   * @return the path to the temporary directory
   */
  public static String getTmp(TmpMode tmpMode) {
    String result = "/tmp";
    switch (tmpMode) {
    case TEMP:
      result = cb.util.Constants.isDOS() ? "C:\\TEMP" : "/tmp";
      break;
    case env:
      result=System.getProperty("java.io.tmpdir"); 
      break;
    case TempFile:
      File tmpDetect;
      try {
        tmpDetect = File.createTempFile("crazybeans", ".txt");
        result=tmpDetect.getParentFile().getAbsolutePath();
        tmpDetect.delete(); 
      } catch (IOException e) {
        // simply use "/tmp/ then
      }
      default:
        // keep default
    }
    return result;
  }

  public static String makeName(String name) {
    return makeName(name, Collections.EMPTY_LIST, null);
  }

  /**
   * Convert a Rose name (which may contains white space, e.g.) to a Java name.
   */
  public static String makeName(String name, java.util.List params,
      PetalNode parent) {
    if (name == null)
      return null;

    char[] ch = name.toCharArray();
    StringBuffer buf = new StringBuffer();
    boolean underscore = true;

    for (int i = 0; i < ch.length; i++) {
      switch (ch[i]) {
      case '_':
      case ' ':
        underscore = true;
        break;

      default:
        if (underscore)
          buf.append(Character.toUpperCase(ch[i]));
        else
          buf.append(ch[i]);

        underscore = false;
        break;
      }
    }

    String ret = buf.toString();

    if (ret.equals("ClassCategory")) { // Find more specific class
      String obj = (String) params.get(0);

      if (obj.equals("Use Case View") || (parent instanceof UseCaseCategory))
        return "UseCaseCategory";
      else if (obj.equals("Logical View")
          || (parent instanceof LogicalCategory))
        return "LogicalCategory";
      else
        return ret;
    } else
      return ret;
  }

  private static HashSet primitive = new HashSet(Arrays.asList(new String[] {
      "int", "double", "long", "char", "byte", "float", "short" }));

  public static String getValueForType(String t) {
    if (primitive.contains(t))
      return "(" + t + ")0";
    else if (t.equals("boolean"))
      return "false";
    else
      return "null";
  }
}
