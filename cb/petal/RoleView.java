package cb.petal;
import java.util.Collection;

/**
 * Represents RoleView object
 *
 * @version $Id: RoleView.java,v 1.10 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class RoleView extends QuiduView implements SegLabeled {
  static final long serialVersionUID = -903647066739783608L;

  public RoleView(PetalNode parent, Collection params, int tag) {
    super(parent, "RoleView", params, tag);
  }

  public RoleView() {
    super("RoleView");
  }

  public Tag getParentView() {
    return (Tag)getProperty("Parent_View");
  }

  public void setParentView(Tag o) {
    defineProperty("Parent_View", o);
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

  public void accept(Visitor v) {
    v.visit(this);
  }
}
