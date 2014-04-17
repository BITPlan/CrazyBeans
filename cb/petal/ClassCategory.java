package cb.petal;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Class category is used for structuring into submodels and -views, it may contain
 * further class categories.
 *
 * @version $Id: ClassCategory.java,v 1.17 2002/09/03 19:33:02 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassCategory extends QuidObject
  implements AccessQualified, Named, Documented, StereoTyped
{
  static final long serialVersionUID=-8273790375346338894L;

  protected ClassCategory(PetalNode parent, String name) {
    super(parent, "Class_Category", Arrays.asList(new String[] { name }));
  }

  public ClassCategory() {
    this(null, "Class Category");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public String getDocumentation() {
    return getPropertyAsString("documentation");
  }

  public void setDocumentation(String o) {
    defineProperty("documentation", o);
  }

  public String getExportControl() {
    return getPropertyAsString("exportControl");
  }

  public void setExportControl(String o) {
    defineProperty("exportControl", o);
  }

  public boolean getGlobal() {
    return getPropertyAsBoolean("global");
  }

  public void setGlobal(boolean o) {
    defineProperty("global", o);
  }

  /** The returned values depend on what kind of class category this
   * is. In the logical view this returns class, association and mechanism
   * objects. It may of course also contain further ClassCategory objects.
   */
  public List getLogicalModels() {
    return (List)getProperty("logical_models");
  }

  public void setLogicalModels(List o) {
    defineProperty("logical_models", o);
  }

  /** This returns a list of diagrams, ClassDiagram objects, e.g.
   */
  public List getLogicalPresentations() {
    return (List)getProperty("logical_presentations");
  }

  public void setLogicalPresentations(List o) {
    defineProperty("logical_presentations", o);
  }

  /** Find diagram by given class
   */
  protected java.lang.Object lookupDiagram(java.lang.Class clazz) {
    List list = getLogicalPresentations();

    if((list == null) || (list.size() == 0))
      return null;
    else {
      for(Iterator i = list.getElements().iterator(); i.hasNext(); ) {
        java.lang.Object o = i.next();

  	if(o.getClass() == clazz)
            return o;
      }
    }

    return null;
  }

  protected void add(PetalObject obj) {
    getLogicalModels().add(obj);
    obj.setParent(this);
    obj.init();
  }

  protected void remove(PetalObject obj) {
    getLogicalModels().remove(obj);
    obj.setParent(null);
  }

  public void addToModel(Association assoc) {
    add(assoc);
  }

  public void removeFromModel(Association assoc) {
    remove(assoc);
  }

  public String getStereotype() {
    return getPropertyAsString("stereotype");
  }

  public void setStereotype(String c) {
    defineProperty("stereotype", c);
  }

  /** Add a class to the model. Sets parent and calls init() on class.
   */
  public void addToModel(Class clazz) {
    add(clazz);
  }

  public void removeFromModel(Class clazz) {
    remove(clazz);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}

