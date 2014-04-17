package cb.petal;
import java.util.Collection;

/**
 * Represents InteractionDiagram object
 *
 * @version $Id: InteractionDiagram.java,v 1.10 2001/07/20 11:54:08 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InteractionDiagram extends Diagram {
  public InteractionDiagram(PetalNode parent, Collection params) {
    super(parent, "InteractionDiagram", params);
  }

  public InteractionDiagram() {
    super("InteractionDiagram");
  }

  // TODO
  protected View searchView(String qual_name) {
    throw new RuntimeException("TODO: Not implemented yet");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
