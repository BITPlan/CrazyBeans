package cb.petal;
import java.util.Collection;

/**
 * Represents InterfaceView object
 *
 * @version $Id: InterfaceView.java,v 1.1 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InterfaceView extends ClassView  {
  static final long serialVersionUID = 2535811997000685425L;

  public InterfaceView(PetalNode parent, Collection params, int tag) {
    super(parent, "InterfaceView", params, tag);
  }

  public InterfaceView() {
    super("InterfaceView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
