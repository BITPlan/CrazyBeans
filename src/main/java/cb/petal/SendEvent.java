package cb.petal;
import java.util.Collection;

/**
 * Represents sendEvent object
 *
 * @version $Id: SendEvent.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SendEvent extends QuidObject {
  public SendEvent(PetalNode parent, Collection params) {
    super(parent, "sendEvent", params);
  }

  public SendEvent() {
    super("sendEvent");
  }

  public Event getEvent() {
    return (Event)getProperty("Event");
  }

  public void setEvent(Event o) {
    defineProperty("Event", o);
  }

  public String getTarget() {
    return getPropertyAsString("target");
  }

  public void setTarget(String o) {
    defineProperty("target", o);
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
