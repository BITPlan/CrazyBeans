package cb.petal;
import java.util.Collection;

/**
 * Represents Connection_Relationship object
 *
 * @version $Id: ConnectionRelationship.java,v 1.7 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ConnectionRelationship extends Relationship {
  public ConnectionRelationship(PetalNode parent, Collection params) {
    super(parent, "Connection_Relationship", params);
  }

  public ConnectionRelationship() {
    super("Connection_Relationship");
  }

  public boolean getSupplierIsDevice() {
    return getPropertyAsBoolean("supplier_is_device");
  }

  public void setSupplierIsDevice(boolean o) {
    defineProperty("supplier_is_device", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
