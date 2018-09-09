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
 * Represents StateView object
 *
 * @version $Id: StateView.java,v 1.9 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class StateView extends QuiduView {
  public StateView(PetalNode parent, Collection params, int tag) {
    super(parent, "StateView", params, tag);
  }

  public StateView() {
    super("StateView");
  }

  public void setIDParameter(String o) {
    params.set(1, o);
  }

  public String getIDParameter() {
    return (String)params.get(1);
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public Compartment getCompartment() {
    return (Compartment)getProperty("compartment");
  }

  public void setCompartment(Compartment o) {
    defineProperty("compartment", o);
  }

  public Tag getParentView() {
    return (Tag)getProperty("Parent_View");
  }

  public void setParentView(Tag o) {
    defineProperty("Parent_View", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
