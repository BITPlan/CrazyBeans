package cb.petal;
import java.util.Collection;
import java.util.Iterator;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Arrays;

/**
 * Represents ClassDiagram object contained within a LogicalCategory
 * object. Items to the view can be added with the addToView()
 * methods which call init() on the given Petal Object after adding
 * them to the items list.
 *
 * @version $Id: ClassDiagram.java,v 1.17 2001/07/19 12:40:40 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassDiagram extends Diagram {
  static final long serialVersionUID = 8346233868380071759L;

  public ClassDiagram(PetalNode parent, Collection params) {
    super(parent, "ClassDiagram", params);
  }

  public ClassDiagram() {
    super("ClassDiagram");
  }

  /** Adds a note view to the presentation view, sets location and tags
   * and calls init().
   */
  public void addToView(NoteView view) {
    int index = addToViewsList(view);

    view.setLocation(new Location(getX(), getY()));

    // Don't set location on labels ...
    ItemLabel label = view.getLabel();

    if(label != null) {
      label.setParentView(new Tag(index));
      label.setLocation(view.getLocation());
    }
  }

  /** Attach note to some already added view object.
  */
  public void addAttachView(NoteView from, View to) {
    AttachView view = cb.util.PetalObjectFactory.getInstance().createAttachView();
    int index = addToViewsList(view);
    view.setSupplier(new Tag(from.getTag()));
    view.setClient(new Tag(to.getTag()));
  }

  private static HashSet set = new HashSet(Arrays.asList(new java.lang.Object[] {
    ClassView.class}));

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

  /** Classes and the according views must have been added
   * to the model already. Sets tag as well as client and supplier tags.
   */
  public void addToView(UsesView view) {
    view.setParent(this);
    addRelationship(view, (UsesRelationship)view.getReferencedObject());
  }

  /** Class and interface and the according views must have been added
   * to the model already. Sets tag as well as client and supplier tags.
   */
  public void addToView(RealizeView view) {
    view.setParent(this);
    addRelationship(view, (RealizeRelationship)view.getReferencedObject());
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
