package cb.petal;
import java.util.Collection;

/**
 * Represents ObjectDiagram object
 *
 * @version $Id: ObjectDiagram.java,v 1.9 2001/11/20 11:24:29 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ObjectDiagram extends Diagram {
  public ObjectDiagram(PetalNode parent, Collection params) {
    super(parent, "ObjectDiagram", params);
  }

  public ObjectDiagram() {
    super("ObjectDiagram");
  }

  // TODO
  protected View searchView(String qual_name) {
    throw new RuntimeException("TODO: Not implemented yet");
  }

  public Tag getMechanismRef() {
    return (Tag)getProperty("mechanism_ref");
  }

  public void setMechanismRef(Tag o) {
    defineProperty("mechanism_ref", o);
  }

  public String getTitle() {
    return getPropertyAsString("title");
  }

  public void setTitle(String o) {
    defineProperty("title", o);
  }

  public int getZoom() {
    return getPropertyAsInteger("zoom");
  }

  public void setZoom(int o) {
    defineProperty("zoom", o);
  }

  public int getMaxHeight() {
    return getPropertyAsInteger("max_height");
  }

  public void setMaxHeight(int o) {
    defineProperty("max_height", o);
  }

  public int getMaxWidth() {
    return getPropertyAsInteger("max_width");
  }

  public void setMaxWidth(int o) {
    defineProperty("max_width", o);
  }

  public int getOriginX() {
    return getPropertyAsInteger("origin_x");
  }

  public void setOriginX(int o) {
    defineProperty("origin_x", o);
  }

  public int getOriginY() {
    return getPropertyAsInteger("origin_y");
  }

  public void setOriginY(int o) {
    defineProperty("origin_y", o);
  }

  public List getItems() {
    return (List)getProperty("items");
  }

  public void setItems(List o) {
    defineProperty("items", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
