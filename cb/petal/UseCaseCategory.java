package cb.petal;

/**
 * Use case class category.
 *
 * @version $Id: UseCaseCategory.java,v 1.6 2001/07/17 14:38:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class UseCaseCategory extends ClassCategory {
  static final long serialVersionUID = 824437667323709051L;

  public UseCaseCategory(PetalObject parent) {
    super(parent, "Use Case View");
  }

  public UseCaseCategory() {
    super(null, "Use Case View");
  }

  public void addToModel(UseCaseCategory cat) {
    add(cat);
  }

  public void removeFromModel(UseCaseCategory cat) {
    remove(cat);
  }

  /** Add a use case to the model. Sets parent and calls init() on use case.
   */
  public void addToModel(UseCase caze) {
    add(caze);
  }

  public void removeFromModel(UseCase caze) {
    remove(caze);
  }

  /** Typically every Use Case view has just one "Main" use case diagram
   */
  public UseCaseDiagram getFirstUseCaseDiagram() {
    return (UseCaseDiagram)lookupDiagram(UseCaseDiagram.class);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
