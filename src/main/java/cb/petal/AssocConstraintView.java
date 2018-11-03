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
 * Represents AssocConstraintView object 
 *
 * @see AssociationViewNew
 */
public class AssocConstraintView extends View {
  public AssocConstraintView(PetalNode parent, Collection params, int tag) {
    super(parent, "AssocConstraintView", params, tag);
  }

  public AssocConstraintView() {
    super("AssocAttachView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
