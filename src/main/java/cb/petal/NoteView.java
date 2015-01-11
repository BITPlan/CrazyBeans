package cb.petal;
import java.util.Collection;

/**
 * Represents NoteView object, i.e. a note on the screan that may be attached to an
 * entity.
 *
 * @version $Id: NoteView.java,v 1.9 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class NoteView extends View {
  public NoteView(PetalNode parent, Collection params, int tag) {
    super(parent, "NoteView", params, tag);
  }

  public NoteView() {
    super("NoteView");
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
