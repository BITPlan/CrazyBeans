package cb.petal;

/**
 * Super class for all petal nodes, e.g., objects, lists, value, literals, etc..
 *
 * @version $Id: PetalNode.java,v 1.9 2001/07/06 11:10:50 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface PetalNode extends java.io.Serializable, Cloneable {
  public abstract String getKind();
  public abstract int getChildCount();
  public abstract void accept(Visitor v);
}
