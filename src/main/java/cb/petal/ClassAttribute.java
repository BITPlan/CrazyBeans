 package cb.petal;
import java.util.Collection;

/**
 * Represents class attribute (aka field) of class object.
 *
 * @version $Id: ClassAttribute.java,v 1.12 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassAttribute extends AccessObject {
  static final long serialVersionUID = -5435324245812367476L;

  public ClassAttribute(PetalNode parent, Collection params) {
    super(parent, "ClassAttribute", params);
  }

  public ClassAttribute() {
    super("ClassAttribute");
  }

  public String getType() {
    return getPropertyAsString("type");
  }

  public void setType(String o) {
    defineProperty("type", o);
  }

  public String getInitialValue() {
    return getPropertyAsString("initv");
  }

  public void setInitialValue(String o) {
    defineProperty("initv", o);
  }

  public boolean getStatic() {
    return getPropertyAsBoolean("static");
  }

  public void setStatic(boolean s) {
    defineProperty("static", s);
  }

  public boolean getDerived() {
     return getPropertyAsBoolean("derived");
  }

  public void setDerived(boolean s) {
    defineProperty("derived", s);
  }

  public String getContainment() {
    return getPropertyAsString("Containment");
  }

  public void setContainment(String o) {
    defineProperty("Containment", o);
  }
  public void accept(Visitor v) {
    v.visit(this);
  }
}
