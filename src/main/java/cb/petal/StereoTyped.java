package cb.petal;

/**
 * Denote that a petal object may have an attribute "stereotype", e.g. classes,
 * methods, ...
 *
 * @version $Id: StereoTyped.java,v 1.4 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface StereoTyped {
  public String getStereotype();

  public void setStereotype(String c);
}
