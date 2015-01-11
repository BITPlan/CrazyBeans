package cb.petal;
import java.util.Collection;

/**
 * Represents ClassInstanceView object
 *
 * @version $Id: ClassInstanceView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassInstanceView extends QuiduView {
  public ClassInstanceView(PetalNode parent, Collection params, int tag) {
    super(parent, "ClassInstanceView", params, tag);
  }

  public ClassInstanceView() {
    super("ClassInstanceView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
