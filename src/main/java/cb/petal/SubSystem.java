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
 * Represents SubSystem object
 *
 * @version $Id: SubSystem.java,v 1.10 2001/08/01 14:26:56 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SubSystem extends QuidObject implements Named, StereoTyped {
	// FIXME - why is this not documented?
  public SubSystem(PetalNode parent, Collection params) {
    super(parent, "SubSystem", params);
  }

  public SubSystem() {
    super("SubSystem");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public String getStereotype() {
    return getPropertyAsString("stereotype");
  }

  public void setStereotype(String o) {
    defineProperty("stereotype", o);
  }

  public PetalNodeList getPhysicalModels() {
    return (PetalNodeList)getProperty("physical_models");
  }

  public void setPhysicalModels(PetalNodeList o) {
    defineProperty("physical_models", o);
  }

  public PetalNodeList getPhysicalPresentations() {
    return (PetalNodeList)getProperty("physical_presentations");
  }

  public void setPhysicalPresentations(PetalNodeList o) {
    defineProperty("physical_presentations", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
