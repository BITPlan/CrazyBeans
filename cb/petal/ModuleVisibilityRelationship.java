package cb.petal;
import java.util.Collection;

/**
 * Represents Module_Visibility_Relationship object
 *
 * @version $Id: ModuleVisibilityRelationship.java,v 1.1 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ModuleVisibilityRelationship extends Relationship {
  public ModuleVisibilityRelationship(PetalNode parent, Collection params) {
    super(parent, "Module_Visibility_Relationship", params);
  }

  public ModuleVisibilityRelationship() {
    super("Module_Visibility_Relationship");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
