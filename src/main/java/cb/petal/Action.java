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
 * Represents action object
 *
 * @version $Id: Action.java,v 1.9 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Action extends NamedQuidObject  {
  public Action(PetalNode parent, Collection params) {
    super(parent, "action", params);
  }

  public Action() {
    super("action");
  }

  public ActionTime getActionTime() {
    return (ActionTime)getProperty("ActionTime");
  }

  public void setActionTime(ActionTime o) {
    defineProperty("ActionTime", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
