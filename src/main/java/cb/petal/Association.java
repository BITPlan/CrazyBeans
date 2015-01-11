package cb.petal;
import java.util.*;

/**
 * Represents Association object
 *
 * @version $Id: Association.java,v 1.15 2001/08/01 14:26:56 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Association extends AccessObject {
  static final long serialVersionUID = 2685090660257573719L;

  public Association(PetalNode parent, Collection params) {
    super(parent, "Association", params);
  }

  public Association() {
    super("Association");
  }

  /** @return Class or UseCase
   */
  public QuidObject getFirstClient() {
    return getFirstRole().getReferencedObject();
  }

  /** @return Class or UseCase
   */
  public QuidObject getSecondClient() {
    return getSecondRole().getReferencedObject();
  }

  /** An association contains exactly two roles. Get the first one.
   */
  public Role getFirstRole() {
    return (Role)getRoles().getElements().get(0);
  }

  /** An association contains exactly two roles. Get the second one.
   */
  public Role getSecondRole() {
    return (Role)getRoles().getElements().get(1);
  }

  /** Register this association internally, i.e. associate it with the
   * attached classes. So the classes can look up the associations
   * related to them, too.<p>
   * If this association has an association class, set its
   * isAssociationClass() flag.
   */
  public void init() {
    super.init();
    getRoot().registerAssociation(this);

    Class clazz = getAssociationClass();
    if(clazz != null)
      clazz.isAssociationClass(true);
  }

  public Class getAssociationClass() {
    String s = getPropertyAsString("AssociationClass");

    if(s != null) {
      Class clazz = getRoot().getClassByQualifiedName(s);

      if(clazz == null)
	System.err.println("Warning: Could not find association class " + s +
			   " (forward declaration?)");

      return clazz;
    } else
      return null;
  }

  public void setAssociationClass(Class o) {
    setAssociationClass(o.getQualifiedName());
  }

  /** Set association class via its fully qualified name like
   * "Logical View::University::Period".
   */
  public void setAssociationClass(String qual_name) {
    defineProperty("AssociationClass", qual_name);
  }

  public List getRoles() {
    return (List)getProperty("roles");
  }

  public void setRoles(List o) {
    defineProperty("roles", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
