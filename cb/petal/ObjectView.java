package cb.petal;
import java.util.Collection;

/**
 * Represents ObjectView object
 *
 * @version $Id: ObjectView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ObjectView extends QuiduView {
  public ObjectView(PetalNode parent, Collection params, int tag) {
    super(parent, "ObjectView", params, tag);
  }

  public ObjectView() {
    super("ObjectView");
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public int getAnnotation() {
    return getPropertyAsInteger("annotation");
  }

  public void setAnnotation(int o) {
    defineProperty("annotation", o);
  }

  public int getSubobjects() {
    return getPropertyAsInteger("subobjects");
  }

  public void setSubobjects(int o) {
    defineProperty("subobjects", o);
  }

  public double getXOffset() {
    return getPropertyAsFloat("x_offset");
  }

  public void setXOffset(double o) {
    defineProperty("x_offset", o);
  }

  public double getYOffset() {
    return getPropertyAsFloat("y_offset");
  }

  public void setYOffset(double o) {
    defineProperty("y_offset", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
