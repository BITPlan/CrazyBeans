package cb.petal;

/**
 * Denote that a petal object may have documentation associated with it.
 *
 * @version $Id: Documented.java,v 1.3 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Documented {
  public String getDocumentation();
  public void setDocumentation(String o);
}
