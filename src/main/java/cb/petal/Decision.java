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
 * Represents Decision object
 *
 * @version $Id: Decision.java,v 1.1 2001/11/30 12:16:51 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Decision extends NamedQuidObject {
  public Decision(PetalNode parent, Collection params) {
    super(parent, "Decision", params);
  }

  public Decision() {
    super("Decision");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
