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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import cb.petal.Attribute;
import cb.petal.Documented;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.StringLiteral;
import cb.petal.Value;

/**
 * Simple representation of a node.
 *
 * @version $Id: NodeImpl.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class NodeImpl implements Node {
  protected static Logger LOGGER = Logger.getLogger("cb.generator.java");
  public static final boolean debug = false;
  protected String name;
  protected String access;
  protected Documented documentedObject;
  Map<String, String> taggedValues = new LinkedHashMap<String, String>();

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

  /**
   * @return the taggedValues
   */
  public Map<String, String> getTaggedValues() {
    return taggedValues;
  }

  /**
   * get the PropertyValue of the given property
   * 
   * @param property
   * @return
   */
  public String getPropertyValue(PetalNode property) {
    if (property instanceof StringLiteral) {
      StringLiteral sl = (StringLiteral) property;
      return sl.getValue();
    } else if (property instanceof Value) {
      Value v = (Value) property;
      return v.getStringValue();
    }
    if (debug)
      LOGGER.log(Level.INFO, "can't get value of property "
          + property.getClass().getName());
    return "";
  }

  /**
   * add the given list of Attributes as taggedValues to this node
   * 
   * @param attributes
   */
  public void addTaggedValues(List<Attribute> attributes) {
    for (Attribute attribute : attributes) {
      ArrayList<PetalNode> properties = attribute.getPropertyList();
      String tool=getPropertyValue(properties.get(0));
      String name = getPropertyValue(properties.get(1));
      String value = getPropertyValue(properties.get(2));
      taggedValues.put(name, value);
    }
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
      LOGGER.log(Level.WARNING,
          "getDocumentation called with null documentedObject");
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
   * 
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
