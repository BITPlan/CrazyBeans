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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import cb.petal.Documented;
import cb.petal.PetalNode;
import cb.petal.PetalObject;

/**
 * Simple representation of a node.
 *
 * @version $Id: NodeImpl.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class NodeImpl implements Node {
  protected static Logger LOGGER = Logger.getLogger("cb.generator.java");
  
  protected String name;
  protected String access;
  protected Documented documentedObject;

  public void setName(String n) {
    name = n;
  }

  public String getName() {
    return name;
  }

  public void setAccess(String a) {
    access = a;
  }

  public String getAccess() {
    return access;
  }

  /**
   * @return the documentedObject
   */
  public Documented getDocumentedObject() {
    return documentedObject;
  }

  /**
   * @param documentedObject
   *          the documentedObject to set
   */
  public void setDocumentedObject(Documented documentedObject) {
    this.documentedObject = documentedObject;
  }

  public boolean is(String s) {
    return access.toLowerCase().indexOf(s.toLowerCase()) >= 0;
  }

  protected static void print(java.io.PrintWriter stream, String pre, String o,
      String post) {
    if ((o != null) && !"".equals(o))
      stream.print(pre + o + post);
  }

  /**
   * get the documentation
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<String> getDocumentation() {
    List<String> result = new ArrayList<String>();
    PetalObject pobject = (PetalObject) documentedObject;
    if (pobject == null) {
      LOGGER.log(Level.WARNING,"getDocumentation called with null documentedObject");
    } else {
      PetalNode doc = pobject.getProperty("documentation");
      cb.petal.StringLiteral str = (cb.petal.StringLiteral) doc;
      if (str != null) {
        result = new ArrayList<String>(str.getLines());
      }
    }
    return result;
  }

  /**
   * print the documentation to the given stream
   * @param stream
   */
  protected void printDocumentation(java.io.PrintWriter stream) {
    if (documentedObject != null) {
      stream.print("  /**");

      for (String line : this.getDocumentation()) {
        stream.println(" * " + line);
      }

      stream.println("  */");
    }
  }
}
