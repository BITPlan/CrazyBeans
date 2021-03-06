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
 * Represents AssociationViewNew object, i.e. the view for an association.
 *
 * @version $Id: AssociationViewNew.java,v 1.10 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see Association
 */
public class AssociationViewNew extends QuiduView implements SegLabeled {
  static final long serialVersionUID = 5927996457897905931L;

  public AssociationViewNew(PetalNode parent, Collection params, int tag) {
    super(parent, "AssociationViewNew", params, tag);
  }

  public AssociationViewNew() {
    super("AssociationViewNew");
  }

  public RoleView getFirstRoleView() {
    return (RoleView)getRoleviewList().getElements().get(0);
  }

  public RoleView getSecondRoleView() {
    return (RoleView)getRoleviewList().getElements().get(1);
  }

  public PetalNodeList getRoleviewList() {
    return (PetalNodeList)getProperty("roleview_list");
  }

  public void setRoleviewList(PetalNodeList o) {
    defineProperty("roleview_list", o);
  }

  public SegLabel getLabel() {
    return (SegLabel)getProperty("label");
  }

  public void setLabel(SegLabel o) {
    defineProperty("label", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
