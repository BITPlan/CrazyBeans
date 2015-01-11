package cb.petal;
import java.util.Collection;

/**
 * Represents Mechanism object
 *
 * @version $Id: Mechanism.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Mechanism extends PetalObject implements Tagged {
  public Mechanism(PetalNode parent, Collection params) {
    super(parent, "Mechanism", params);
    setTag(tag);
  }

  public Mechanism() {
    super("Mechanism");
  }

  private int tag = -1;

  public void setTag(int t) { tag = t; }

  public int  getTag()      { return tag; }

  public List getLogicalModels() {
    return (List)getProperty("logical_models");
  }

  public void setLogicalModels(List o) {
    defineProperty("logical_models", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
