package cb.petal;

/**
 * Denote that a petal object has a SegLabel label
 *
 * @version $Id: SegLabeled.java,v 1.2 2001/06/18 15:20:05 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface SegLabeled {
  public SegLabel getLabel();
  public void setLabel(SegLabel o);
}
