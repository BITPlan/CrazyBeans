package cb.petal;
import java.util.Collection;

/**
 * Represents Dependency_Relationship object
 *
 * @version $Id: DependencyRelationship.java,v 1.7 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class DependencyRelationship extends Relationship {
  public DependencyRelationship(PetalNode parent, Collection params) {
    super(parent, "Dependency_Relationship", params);
  }

  public DependencyRelationship() {
    super("Dependency_Relationship");
  }

  public boolean getSupplierIsSubsystem() {
    return getPropertyAsBoolean("supplier_is_subsystem");
  }

  public void setSupplierIsSubsystem(boolean o) {
    defineProperty("supplier_is_subsystem", o);
  }

  public boolean getSupplierIsSpec() {
    return getPropertyAsBoolean("supplier_is_spec");
  }

  public void setSupplierIsSpec(boolean o) {
    defineProperty("supplier_is_spec", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
