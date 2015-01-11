package cb.petal;
import java.util.Collection;

/**
 * Represents module object
 *
 * @version $Id: Module.java,v 1.9 2001/08/01 14:26:56 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Module extends QuidObject implements Named, StereoTyped {
  public Module(PetalNode parent, Collection params) {
    super(parent, "module", params);
  }

  public Module() {
    super("module");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
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

  public List getAttributes() {
    return (List)getProperty("attributes");
  }

  public void setAttributes(List o) {
    defineProperty("attributes", o);
  }

  public String getStereotype() {
    return getPropertyAsString("stereotype");
  }

  public void setStereotype(String o) {
    defineProperty("stereotype", o);
  }

  public List getVisibleModules() {
    return (List)getProperty("visible_modules");
  }

  public void setVisibleModules(List o) {
    defineProperty("visible_modules", o);
  }

  public String getPath() {
    return getPropertyAsString("path");
  }

  public void setPath(String o) {
    defineProperty("path", o);
  }

  public String getLanguage() {
    return getPropertyAsString("language");
  }

  public void setLanguage(String o) {
    defineProperty("language", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
