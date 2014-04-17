package cb.petal;

// TODO: The quidu has to point to the models "Component View", i.e., the
// "root_subsystem" property.

/**
 * The Logical category contains class diagrams. The interesting part
 * are the two lists "logical_models" and "logical_presentations"
 * where the former contains the data model and the latter contains
 * class diagrams. One can add items to the model with addToModel()
 * methods. Items to the view can be added with the addToView()
 * methods in ClassDiagram. Both types of methods call init() on the
 * given Petal Object after adding them to the appropriate list.
 *
 * @version $Id: LogicalCategory.java,v 1.9 2001/07/17 14:38:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see ClassDiagram
 */
public class LogicalCategory extends ClassCategory {
  static final long serialVersionUID=421992761725074833L;

  public LogicalCategory(PetalObject parent) {
    super(parent, "Logical View");
  }

  public LogicalCategory() {
    super(null, "Logical View");
  }

  public String getSubsystem() {
    return getPropertyAsString("subsystem");
  }

  public void setSubsystem(String o) {
    defineProperty("subsystem", o);
  }

  public void addToModel(LogicalCategory cat) {
    add(cat);
  }

  public void removeFromModel(LogicalCategory cat) {
    remove(cat);
  }

  /** Typically every Logical view has just one "Main" class diagram
   */
  public ClassDiagram getFirstClassDiagram() {
    return (ClassDiagram)lookupDiagram(ClassDiagram.class);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
