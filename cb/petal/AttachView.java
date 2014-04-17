package cb.petal;
import java.util.Collection;

/**
 * Represents AttachView object which means the line drawn between a note (view) and some
 * entity.
 *
 * @version $Id: AttachView.java,v 1.9 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class AttachView extends View {
  public AttachView(PetalNode parent, Collection params, int tag) {
    super(parent, "AttachView", params, tag);
  }

  public AttachView() {
    super("AttachView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
