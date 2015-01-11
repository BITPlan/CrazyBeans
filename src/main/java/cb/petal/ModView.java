package cb.petal;
import java.util.Collection;

/**
 * Represents ModView object
 *
 * @version $Id: ModView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ModView extends QuiduView {
  public ModView(PetalNode parent, Collection params) {
    super(parent, "ModView", params, -1);
  }

  public ModView() {
    super("ModView");
  }

  public void setTypeParameter(String o) {
    params.set(1, o);
  }

  public String getTypeParameter() {
    return (String)params.get(1);
  }

  public void setSpecificationParameter(String o) {
    params.set(2, o);
  }

  public String getSpecificationParameter() {
    return (String)params.get(2);
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
