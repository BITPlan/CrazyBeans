package cb.petal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.awt.Dimension;

/**
 * Represents UseCaseDiagram object
 *
 * @version $Id: UseCaseDiagram.java,v 1.11 2001/07/19 12:40:40 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class UseCaseDiagram extends Diagram {
  static final long serialVersionUID = -2214424470131913554L;

  public UseCaseDiagram(PetalNode parent, Collection params) {
    super(parent, "UseCaseDiagram", params);
  }

  public UseCaseDiagram() {
    super("UseCaseDiagram");
  }

  /** Adds a use case view to the presentation view, sets location and tags
   * and calls init().
   */
  public void addToView(UseCaseView view) {
    int index = addToViewsList(view);

    view.setLocation(new Location(getX(), getY()));

    ItemLabel label = view.getLabel();

    if(label != null)
      label.setParentView(new Tag(index));

    label = view.getStereotype();

    if(label != null)
      label.setParentView(new Tag(index));
  }

  private static HashSet set = new HashSet(Arrays.asList(new java.lang.Object[] {
    UseCaseView.class, ClassView.class }));

  protected View searchView(String qual_name) {
    return searchView(qual_name, set);
  }

  /** Class and super class and the according views must have been added
   * to the model already. Sets tag as well as client and supplier tags.
   */
  public void addToView(InheritView view) {
    view.setParent(this);
    addRelationship(view, (InheritanceRelationship)view.getReferencedObject());
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
