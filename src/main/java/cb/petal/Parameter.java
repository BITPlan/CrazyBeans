/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;
import java.util.Collection;

/**
 * Represents Parameter object for operations (methods).
 *
 * @version $Id: Parameter.java,v 1.11 2002/07/23 19:56:26 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Parameter extends PetalObject implements Named,Documented {
  public Parameter(PetalNode parent, Collection params) {
    super(parent, "Parameter", params);
  }

  public Parameter() {
    super("Parameter");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public String getType() {
    return getPropertyAsString("type");
  }

  public void setType(String o) {
    defineProperty("type", o);
  }

  public String getInitialValue() {
    return getPropertyAsString("initv");
  }

  public void setInitialValue(String o) {
    defineProperty("initv", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public String getDocumentation() {
    return getPropertyAsString("documentation");
  }

  public void setDocumentation(String o) {
    defineProperty("documentation", o);
  }
}
