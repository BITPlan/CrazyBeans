package cb.petal;
import java.util.Collection;

/**
 * Represents LinkView object
 *
 * @version $Id: LinkView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class LinkView extends QuiduView {
  public LinkView(PetalNode parent, Collection params, int tag) {
    super(parent, "LinkView", params, tag);
  }

  public LinkView() {
    super("LinkView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
