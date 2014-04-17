package cb.petal;
import java.util.Collection;

/**
 * Represents Instantiated_Class object
 *
 * @version $Id: InstantiatedClassUtility.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InstantiatedClassUtility extends InstantiatedClass {
  public InstantiatedClassUtility(PetalNode parent, Collection params) {
    super(parent, params);
    setName("Instantiated_Class_Utility");
  }

  public InstantiatedClassUtility() {
    setName("Instantiated_Class_Utility");
  }
}
