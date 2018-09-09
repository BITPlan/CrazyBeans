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
 * Represents Visibility_Relationship object
 *
 * @version $Id: VisibilityRelationship.java,v 1.7 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class VisibilityRelationship extends Relationship {
  public VisibilityRelationship(PetalNode parent, Collection params) {
    super(parent, "Visibility_Relationship", params);
  }

  public VisibilityRelationship() {
    super("Visibility_Relationship");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
