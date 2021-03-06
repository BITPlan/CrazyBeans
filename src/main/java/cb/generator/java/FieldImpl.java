/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.generator.java;

import java.io.PrintWriter;

import cb.petal.ClassAttribute;

/**
 * Simple representation of a Java field.
 *
 * @version $Id: FieldImpl.java,v 1.2 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class FieldImpl extends NodeImpl implements Field {
  private   String init, type;
  protected ClassAttribute attribute;

  /**
   * create me from the given Attribute
   * @param attr
   */
  public FieldImpl(ClassAttribute attr) { 
  		super(attr);
  		setAttribute(attr);
  }

  public void           setAttribute(ClassAttribute a) { super.setDocumentedObject(a);;attribute = a; }
  public ClassAttribute getAttribute()                 { return attribute; }

  public void   setInitialValue(String s) { init = s; }
  public String getInitialValue() { return init; }
  public void   setType(String p) { type = p; }
  public String getType() { return type; }

  /** Default implementation prints Java code
   */
  public void dump(PrintWriter stream) {
    printDocumentation(stream);

    stream.print("  ");
    print(stream, "", getAccess(), " ");
    stream.print(getType() + " " + getName());
    print(stream, " = ", getInitialValue(), "");
    stream.println(";");
  }

  public boolean equals(Object o) {
    if(o instanceof Field) {
      Field f = (Field)o;

      return getType().equals(f.getType()) && getName().equals(f.getName());
    } else
      return false;
  }
}
