package cb.petal;
import java.util.Collection;

/**
 * Represents Label object
 *
 * @version $Id: Label.java,v 1.11 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Label extends PetalObject implements Tagged {
  public Label(PetalNode parent, Collection params) {
    super(parent, "Label", params);
    setTag(tag);
  }

  public Label() {
    super("Label");
  }

  private int tag = -1;

  public void setTag(int t) { tag = t; }

  public int  getTag()      { return tag; }

  public Location getLocation() {
    return (Location)getProperty("location");
  }

  public void setLocation(Location o) {
    defineProperty("location", o);
  }

  public Font getFont() {
    return (Font)getProperty("font");
  }

  public void setFont(Font o) {
    defineProperty("font", o);
  }

  public int getNlines() {
    return getPropertyAsInteger("nlines");
  }

  public void setNlines(int o) {
    defineProperty("nlines", o);
  }

  public int getMaxWidth() {
    return getPropertyAsInteger("max_width");
  }

  public void setMaxWidth(int o) {
    defineProperty("max_width", o);
  }

  public String getLabel() {
    return getPropertyAsString("label");
  }

  public void setLabel(String o) {
    defineProperty("label", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
