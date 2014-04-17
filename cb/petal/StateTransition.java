package cb.petal;
import java.util.Collection;

/**
 * Represents State_Transition object
 *
 * @version $Id: StateTransition.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class StateTransition extends QuiduObject
  implements Labeled, HasSupplier
{
  public StateTransition(PetalNode parent, Collection params) {
    super(parent, "State_Transition", params);
  }

  public StateTransition() {
    super("State_Transition");
  }

  public String getLabel() {
    return getPropertyAsString("label");
  }

  public void setLabel(String o) {
    defineProperty("label", o);
  }

  public String getSupplier() {
    return getPropertyAsString("supplier");
  }

  public void setSupplier(String o) {
    defineProperty("supplier", o);
  }

  public Event getEvent() {
    return (Event)getProperty("Event");
  }

  public void setEvent(Event o) {
    defineProperty("Event", o);
  }

  public Action getAction() {
    return (Action)getProperty("action");
  }

  public void setAction(Action o) {
    defineProperty("action", o);
  }

  public SendEvent getSendEvent() {
    return (SendEvent)getProperty("sendEvent");
  }

  public void setSendEvent(SendEvent o) {
    defineProperty("sendEvent", o);
  }

  public String getCondition() {
    return getPropertyAsString("condition");
  }

  public void setCondition(String o) {
    defineProperty("condition", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
