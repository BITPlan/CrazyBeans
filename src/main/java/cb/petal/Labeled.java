package cb.petal;

/**
 * Denote that a petal object may have a label.
 *
 * @version $Id: Labeled.java,v 1.5 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Labeled {
  public void   setLabel(String o);
  public String getLabel();
}
