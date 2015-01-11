package cb.petal;
import java.util.Collection;

/**
 * Represents UsesView object
 *
 * @version $Id: UsesView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class UsesView extends RelationshipView {
  public UsesView(PetalNode parent, Collection params, int tag) {
    super(parent, "UsesView", params, tag);
  }

  public UsesView() {
    super("UsesView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
