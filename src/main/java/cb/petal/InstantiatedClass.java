package cb.petal;
import java.util.Collection;

/**
 * Represents Instantiated_Class object
 *
 * @version $Id: InstantiatedClass.java,v 1.9 2001/06/27 10:26:03 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InstantiatedClass extends DerivedClass {
  public InstantiatedClass(PetalNode parent, Collection params) {
    super(parent, "Instantiated_Class", params);
  }

  public InstantiatedClass() {
    super("Instantiated_Class");
  }

  public void setActualParameter(String o) {
    params.set(0, o);
  }

  public String getActualParameter() {
    return (String)params.get(0);
  }

  public String getModule() {
    return getPropertyAsString("module");
  }

  public void setModule(String o) {
    defineProperty("module", o);
  }

  public InstantiationRelationship getInstantiationRelationship() {
    return (InstantiationRelationship)getProperty("instantiation_relationship");
  }

  public void setInstantiationRelationship(InstantiationRelationship o) {
    defineProperty("instantiation_relationship", o);
  }

  public String getNonclassname() {
    return getPropertyAsString("nonclassname");
  }

  public void setNonclassname(String o) {
    defineProperty("nonclassname", o);
  }
}
