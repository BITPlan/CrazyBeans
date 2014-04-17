package cb.petal;
import java.util.Collection;

/**
 * Represents SynchronizationView object
 *
 * @version $Id: SynchronizationView.java,v 1.1 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SynchronizationView extends View {
  public SynchronizationView(PetalNode parent, Collection params, int tag) {
    super(parent, "SynchronizationView", params, tag);
  }

  public SynchronizationView() {
    super("SynchronizationView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
