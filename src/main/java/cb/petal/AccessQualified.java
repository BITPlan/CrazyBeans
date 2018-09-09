package cb.petal;

/**
 * Denote that a petal object may have an attribute "exportControl", e.g. classes,
 * methods, ..., which refers to a qualifier like "public", "protected", ...
 *
 * @version $Id: AccessQualified.java,v 1.5 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface AccessQualified {
  public String getExportControl();
  public void setExportControl(String o);
  /**
   * get the visibility of this access object
   * @return the visibilty
   */
  public default Visibility getVisibility() {
    String exportControl=this.getExportControl();
    Visibility visibility=Visibility.valueOf(exportControl==null?"UNDEFINED":exportControl.toUpperCase());
    return visibility;
  }
}
