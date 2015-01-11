package cb.petal;
import java.util.Collection;

/**
 * Represents ActivityDiagram object
 *
 * @version $Id: ActivityDiagram.java,v 1.1 2001/11/20 11:24:28 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ActivityDiagram extends Diagram {
  public ActivityDiagram(PetalNode parent, Collection params) {
    super(parent, "ActivityDiagram", params);
  }

  public ActivityDiagram() {
    super("ActivityDiagram");
  }

  // TODO
  protected View searchView(String qual_name) {
    throw new RuntimeException("TODO: Not implemented yet");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
