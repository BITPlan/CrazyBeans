package cb.petal;
import java.util.Collection;

/**
 * Represents SynchronizationState object
 *
 * @version $Id: SynchronizationState.java,v 1.1 2001/11/30 12:16:51 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SynchronizationState extends QuidObject implements Named {
  public SynchronizationState(PetalNode parent, Collection params) {
    super(parent, "SynchronizationState", params);
  }

  public SynchronizationState() {
    super("SynchronizationState");
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
