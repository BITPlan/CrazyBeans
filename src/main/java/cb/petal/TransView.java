package cb.petal;
import java.util.Collection;

/**
 * Represents TransView object
 *
 * @version $Id: TransView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class TransView extends QuiduView {
  public TransView(PetalNode parent, Collection params, int tag) {
    super(parent, "TransView", params, tag);
  }

  public TransView() {
    super("TransView");
  }

  public boolean getXOffset() {
    return getPropertyAsBoolean("x_offset");
  }

  public void setXOffset(boolean o) {
    defineProperty("x_offset", o);
  }

  public SegLabel getLabel() {
    return (SegLabel)getProperty("label");
  }

  public void setLabel(SegLabel o) {
    defineProperty("label", o);
  }

  public List getVertices() {
    return (List)getProperty("vertices");
  }

  public void setVertices(List o) {
    defineProperty("vertices", o);
  }

  public Location getOriginAttachment() {
    return (Location)getProperty("origin_attachment");
  }

  public void setOriginAttachment(Location o) {
    defineProperty("origin_attachment", o);
  }

  public Location getTerminalAttachment() {
    return (Location)getProperty("terminal_attachment");
  }

  public void setTerminalAttachment(Location o) {
    defineProperty("terminal_attachment", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
