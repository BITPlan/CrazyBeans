package cb.petal;

/**
 * Represents UseCase object
 *
 * @version $Id: UseCase.java,v 1.13 2001/07/30 15:50:33 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class UseCase extends Inheritable {
  static final long serialVersionUID = -6446937716836493799L;

  public UseCase(PetalNode parent, java.util.Collection params) {
    super(parent, "UseCase", params);
  }

  public UseCase() {
    super("UseCase");
  }

  /** Add super use case of this use case, i.e. adds InheritanceRelationship to
   * "superclasses" list.
   * @return implicitly created relationship object
   */
  public InheritanceRelationship addSuperUseCase(UseCase caze) {
    return addSuperClassifier(caze);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
