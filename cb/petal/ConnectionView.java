package cb.petal;
import java.util.Collection;

/**
 * Represents ConnectionView object
 *
 * @version $Id: ConnectionView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ConnectionView extends QuiduView {
  public ConnectionView(PetalNode parent, Collection params, int tag) {
    super(parent, "ConnectionView", params, tag);
  }

  public ConnectionView() {
    super("ConnectionView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
