package cb.petal;
import java.util.Collection;

/**
 * Represents UseCaseView object
 *
 * @version $Id: UseCaseView.java,v 1.9 2001/07/19 12:40:40 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class UseCaseView extends QuiduView implements Qualified {
  static final long serialVersionUID = 8100428687661863064L;

  public UseCaseView(PetalNode parent, Collection params, int tag) {
    super(parent, "UseCaseView", params, tag);
  }

  /**
   * @param name String like "Logical View::University::Professor"
   */
  public void setQualifiedNameParameter(String name) {
    params.set(0, name);
  }

  public String getQualifiedNameParameter() {
    return (String)params.get(0);
  }

  public UseCaseView() {
    super("UseCaseView");
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public ItemLabel getStereotype() {
    return (ItemLabel)getProperty("stereotype");
  }

  public void setStereotype(ItemLabel o) {
    defineProperty("stereotype", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}

