package cb.petal;
import java.util.Collection;

/**
 * Represents Realize relationship ("implements").
 *
 * @version $Id: RealizeRelationship.java,v 1.6 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class RealizeRelationship extends Relationship {
  public RealizeRelationship(PetalNode parent, Collection params) {
    super(parent, "Realize_Relationship", params);
  }

  public RealizeRelationship() {
    super("Realize_Relationship");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
