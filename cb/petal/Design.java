package cb.petal;
import java.util.Collection;

/**
 * Represents top level design object.
 *
 * @version $Id: Design.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Design extends PetalObject implements Named {
  public Design(Collection params) {
    super(null, "Design", params);
  }

  public Design() {
    super("Design");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public boolean getIsUnit() {
    return getPropertyAsBoolean("is_unit");
  }

  public void setIsUnit(boolean o) {
    defineProperty("is_unit", o);
  }

  public boolean getIsLoaded() {
    return getPropertyAsBoolean("is_loaded");
  }

  public void setIsLoaded(boolean o) {
    defineProperty("is_loaded", o);
  }
 
  public Defaults getDefaults() {
    return (Defaults)getProperty("defaults");
  }

  public void setDefaults(Defaults o) {
    defineProperty("defaults", o);
  }

  /** @return Use case view
   */
  public ClassCategory getUsecaseView() {
    return getRootUsecasePackage();
  }

  public void setUsecaseView(ClassCategory o) {
    setRootUsecasePackage(o);
  }

  /** @return Use case view
   */
  public ClassCategory getRootUsecasePackage() {
    return (ClassCategory)getProperty("root_usecase_package");
  }

  public void setRootUsecasePackage(ClassCategory o) {
    defineProperty("root_usecase_package", o);
  }

  public ClassCategory getLogicalView() {
    return getRootCategory();
  }

  public void setLogicalView(ClassCategory o) {
    setRootCategory(o);
  }

  /** @return Logical view
   */
  public ClassCategory getRootCategory() {
    return (ClassCategory)getProperty("root_category");
  }

  public void setRootCategory(ClassCategory o) {
    defineProperty("root_category", o);
  }

  /** @return Component view
   */
  public SubSystem getRootSubsystem() {
    return (SubSystem)getProperty("root_subsystem");
  }

  public void setRootSubsystem(SubSystem o) {
    defineProperty("root_subsystem", o);
  }

  public Processes getProcessStructure() {
    return (Processes)getProperty("process_structure");
  }

  public void setProcessStructure(Processes o) {
    defineProperty("process_structure", o);
  }

  public Properties getProperties() {
    return (Properties)getProperty("properties");
  }

  public void setProperties(Properties o) {
    defineProperty("properties", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
