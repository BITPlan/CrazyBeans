package cb.petal;
import java.util.Collection;

/**
 * Represents Meta class object
 *
 * @version $Id: MetaClass.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class MetaClass extends Class {
  public MetaClass(PetalNode parent, Collection params) {
    super(parent, "Metaclass", params);
  }

  public MetaClass() {
    setName("Metaclass");
  }
}
