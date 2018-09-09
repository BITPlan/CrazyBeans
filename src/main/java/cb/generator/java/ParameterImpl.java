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

import cb.petal.Parameter;

/**
 * Simple representation of a method parameter.
 *
 * @version $Id: ParameterImpl.java,v 1.2 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ParameterImpl extends NodeImpl implements cb.generator.java.Parameter {
  private   String  type;
  protected cb.petal.Parameter parameter;

  /**
   * construct me
   */
  public ParameterImpl() {
  		super(null); // TODO can not get id from parameters since cb.petal.Parameter is not a quid object
  }

  public void               setParameter(cb.petal.Parameter p) { super.setDocumentedObject(p);;parameter = p; }
  public cb.petal.Parameter getParameter()                     { return parameter; }

  public void   setType(String p) { type = p; }
  public String getType() { return type; }

  /** Default implementation prints Java code
   */
  public void dump(PrintWriter stream) {
    print(stream, "", getAccess(), " ");
    stream.print(getType() + " " + getName());
  }

  public boolean equals(Object o) {
    if(o instanceof Parameter) {
      Parameter p = (Parameter)o;

      return getType().equals(p.getType()) && getName().equals(p.getName());
    } else
      return false;
  }
}
