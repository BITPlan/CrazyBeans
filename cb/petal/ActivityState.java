package cb.petal;
import java.util.Collection;

/**
 * Represents ActivityState object
 *
 * @version $Id: ActivityState.java,v 1.1 2001/11/30 12:16:50 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ActivityState extends QuidObject implements Named {
  public ActivityState(PetalNode parent, Collection params) {
    super(parent, "ActivityState", params);
  }

  public ActivityState() {
    super("ActivityState");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
