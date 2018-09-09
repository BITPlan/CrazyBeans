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
 * Represents InheritView object, i.e. view for InheritanceRelationship
 *
 * @version $Id: InheritView.java,v 1.10 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see InheritanceRelationship
 */
public class InheritView extends RelationshipView {
  public InheritView(PetalNode parent, Collection params, int tag) {
    super(parent, "InheritView", params, tag);
  }

  public InheritView() {
    super("InheritView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
