package cb.petal;
import java.util.Collection;

/**
 * Represents Processes object
 *
 * @version $Id: Processes.java,v 1.9 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Processes extends QuidObject {
  public Processes(PetalNode parent, Collection params) {
    super(parent, "Processes", params);
  }

  public Processes() {
    super("Processes");
  }

  public List getProcsNDevs() {
    return (List)getProperty("ProcsNDevs");
  }

  public void setProcsNDevs(List o) {
    defineProperty("ProcsNDevs", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
