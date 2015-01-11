package cb.petal;
import java.util.Collection;

/**
 * Pre or postcondition of method
 *
 * @version $Id: SemanticInfo.java,v 1.3 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SemanticInfo extends PetalObject {
  public SemanticInfo(PetalNode parent, Collection params) {
    super(parent, "Semantic_Info", params);
  }

  public SemanticInfo() {
    super("Semantic_Info");
  }

  public String getPDL() {
    return getPropertyAsString("PDL");
  }

  public void setPDL(String c) {
    defineProperty("PDL", c);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
