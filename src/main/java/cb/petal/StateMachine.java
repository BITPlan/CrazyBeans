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
 * Represents State_Machine object
 *
 * @version $Id: StateMachine.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class StateMachine extends QuidObject {
  public StateMachine(PetalNode parent, Collection params) {
    super(parent, "State_Machine", params);
  }

  public StateMachine() {
    super("State_Machine");
  }

  public List getStates() {
    return (List)getProperty("states");
  }

  public void setStates(List o) {
    defineProperty("states", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
