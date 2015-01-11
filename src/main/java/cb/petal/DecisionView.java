package cb.petal;
import java.util.Collection;

/**
 * Represents DecisionView object
 *
 * @version $Id: DecisionView.java,v 1.1 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class DecisionView extends View {
  public DecisionView(PetalNode parent, Collection params, int tag) {
    super(parent, "DecisionView", params, tag);
  }

  public DecisionView() {
    super("DecisionView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
