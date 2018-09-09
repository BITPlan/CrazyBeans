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
 * Represents ClassView object
 *
 * @version $Id: ClassView.java,v 1.12 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassView extends QuiduView implements Qualified {
  static final long serialVersionUID = 2535811997000685425L;

  public ClassView(PetalNode parent, Collection params, int tag) {
    super(parent, "ClassView", params, tag);
  }

  protected ClassView(PetalNode parent, String name, Collection params, int tag) {
    super(parent, name, params, tag);
  }

  public ClassView() {
    super("ClassView");
  }

  protected ClassView(String name) {
    super(name);
  }

  /**
   * @param name String like "Logical View::University::Professor"
   */
  public void setQualifiedNameParameter(String name) {
    params.set(1, name);
  }

  public String getQualifiedNameParameter() {
    return (String)params.get(1);
  }

  public boolean getShowCompartmentStereotypes() {
    return getPropertyAsBoolean("ShowCompartmentStereotypes");
  }

  public void setShowCompartmentStereotypes(boolean o) {
    defineProperty("ShowCompartmentStereotypes", o);
  }

  public boolean getIncludeAttribute() {
    return getPropertyAsBoolean("IncludeAttribute");
  }

  public void setIncludeAttribute(boolean o) {
    defineProperty("IncludeAttribute", o);
  }

  public boolean getIncludeOperation() {
    return getPropertyAsBoolean("IncludeOperation");
  }

  public void setIncludeOperation(boolean o) {
    defineProperty("IncludeOperation", o);
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public ItemLabel getStereotype() {
    return (ItemLabel)getProperty("stereotype");
  }

  public void setStereotype(ItemLabel o) {
    defineProperty("stereotype", o);
  }

  public int getAnnotation() {
    return getPropertyAsInteger("annotation");
  }

  public void setAnnotation(int o) {
    defineProperty("annotation", o);
  }

  public Compartment getCompartment() {
    return (Compartment)getProperty("compartment");
  }

  public void setCompartment(Compartment o) {
    defineProperty("compartment", o);
  }

  public boolean getAutoResize() {
    return getPropertyAsBoolean("autoResize");
  }

  public void setAutoResize(boolean o) {
    defineProperty("autoResize", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
