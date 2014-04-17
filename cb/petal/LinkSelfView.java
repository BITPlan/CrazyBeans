package cb.petal;
import java.util.Collection;

/**
 * Represents LinkSelfView object
 *
 * @version $Id: LinkSelfView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class LinkSelfView extends QuiduView {
  public LinkSelfView(PetalNode parent, Collection params, int tag) {
    super(parent, "LinkSelfView", params, tag);
  }

  public LinkSelfView() {
    super("LinkSelfView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
