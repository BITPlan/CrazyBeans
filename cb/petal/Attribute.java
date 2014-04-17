package cb.petal;
import java.util.Collection;

/**
 * Represents Attribute object
 *
 * @version $Id: Attribute.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Attribute extends PetalObject {
  public Attribute(PetalNode parent, Collection params) {
    super(parent, "Attribute", params);
  }

  public Attribute() {
    super("Attribute");
  }

  public String getTool() {
    return getPropertyAsString("tool");
  }

  public void setTool(String o) {
    defineProperty("tool", o);
  }

  public String getAttributeName() {
    return getPropertyAsString("name");
  }

  public void setAttributeName(String o) {
    defineProperty("name", o);
  }

  public PetalNode getValue() {
    return getProperty("value");
  }

  public void setValue(PetalNode o) {
    defineProperty("value", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
