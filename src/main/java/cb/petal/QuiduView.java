package cb.petal;

import java.util.*;

/**
 * Super class for all view objects with a "quidu" property defined.
 *
 * @version $Id: QuiduView.java,v 1.4 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class QuiduView extends View implements HasQuidu
{
  static final long serialVersionUID = -7371455691558399461L;

  protected QuiduView(PetalNode parent, String name, Collection params, int tag) {
    super(parent, name, params, tag);
  }

  protected QuiduView(String name) {
    super(name);
  }

  public void setQuidu(String quid) {
    defineProperty("quidu", quid);
  }

  public String getQuidu() {
    return QuiduObject.getQuidu(this);
  }

  public long getQuiduAsLong() {
    return Long.parseLong(getQuidu(), 16);
  }

  public void setQuiduAsLong(long quid) {
    defineProperty("quidu", Long.toHexString(quid).toUpperCase());
  }

  /** @return object referenced via "quidu"
   */
  public QuidObject getReferencedObject() {
    return getRoot().getReferencedObject(this);
  }
}

