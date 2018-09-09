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
 * Represents RealizeView object
 *
 * @version $Id: RealizeView.java,v 1.7 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see RealizeRelationship
 */
public class RealizeView extends RelationshipView {
  public RealizeView(PetalNode parent, Collection params, int tag) {
    super(parent, "RealizeView", params, tag);
  }

  public RealizeView() {
    super("RealizeView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
