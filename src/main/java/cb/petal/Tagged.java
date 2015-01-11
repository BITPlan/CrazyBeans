package cb.petal;

/**
 * Denote that a petal object has a tag, i.e. @12
 *
 * @version $Id: Tagged.java,v 1.4 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Tagged {
  public void setTag(int t);
  public int  getTag();
}
