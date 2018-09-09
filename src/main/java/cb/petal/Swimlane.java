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
 * Represents Swimlane object
 *
 * @version $Id: Swimlane.java,v 1.1 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Swimlane extends QuiduView {
  public Swimlane(PetalNode parent, Collection params, int tag) {
    super(parent, "Swimlane", params, tag);
  }

  public Swimlane() {
    super("Swimlane");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
