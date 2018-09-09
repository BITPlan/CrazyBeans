/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

import java.util.*;

/**
 * Super class for all relationship view objects.
 *
 * @version $Id: RelationshipView.java,v 1.3 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class RelationshipView extends QuiduView implements HasQuidu
{
  static final long serialVersionUID = -7371455691568399461L;

  protected RelationshipView(PetalNode parent, String name, Collection params, int tag) {
    super(parent, name, params, tag);
  }

  protected RelationshipView(String name) {
    super(name);
  }

  public void setLabel(ItemLabel label) {
    defineProperty("label", label);
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setStereotype(SegLabel label) {
    defineProperty("stereotype", label);
  }

  public SegLabel getStereotype() {
    PetalNode node = getProperty("stereotype");

    // May be boolean otherwise, brrrr
    return (node instanceof SegLabel)? (SegLabel)node : null;
  }
}

