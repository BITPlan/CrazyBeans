package cb.petal;
import java.util.Collection;

/**
 * Represents DataFlowView object
 *
 * @version $Id: DataFlowView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class DataFlowView extends QuiduView {
  public DataFlowView(PetalNode parent, Collection params, int tag) {
    super(parent, "DataFlowView", params, tag);
  }

  public DataFlowView() {
    super("DataFlowView");
  }

  public SegLabel getLabel() {
    return (SegLabel)getProperty("label");
  }

  public void setLabel(SegLabel o) {
    defineProperty("label", o);
  }

  public int getDir() {
    return getPropertyAsInteger("dir");
  }

  public void setDir(int o) {
    defineProperty("dir", o);
  }

  public Tag getMessView() {
    return (Tag)getProperty("MessView");
  }

  public void setMessView(Tag o) {
    defineProperty("MessView", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
