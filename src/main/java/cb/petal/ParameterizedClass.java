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
 * Represents Parameterized_Class object
 *
 * @version $Id: ParameterizedClass.java,v 1.9 2001/06/27 10:26:03 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ParameterizedClass extends DerivedClass {
  public ParameterizedClass(PetalNode parent, Collection params) {
    super(parent, "Parameterized_Class", params);
  }

  public ParameterizedClass() {
    super("Parameterized_Class");
  }

  public void setFormalParameter(String o) {
    params.set(0, o);
  }

  public String getFormalParameter() {
    return (String)params.get(0);
  }

  public String getModule() {
    return getPropertyAsString("module");
  }

  public void setModule(String o) {
    defineProperty("module", o);
  }

  public List getNestedClasses() {
    return (List)getProperty("nestedClasses");
  }

  public void setNestedClasses(List o) {
    defineProperty("nestedClasses", o);
  }

  public String getNonclassname() {
    return getPropertyAsString("nonclassname");
  }

  public void setNonclassname(String o) {
    defineProperty("nonclassname", o);
  }

  public boolean getAbstract() {
    return getPropertyAsBoolean("abstract");
  }

  public void setAbstract(boolean o) {
    defineProperty("abstract", o);
  }
}
