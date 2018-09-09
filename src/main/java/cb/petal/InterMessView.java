/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;
import java.util.Collection;

/**
 * Represents InterMessView object
 *
 * @version $Id: InterMessView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InterMessView extends View {
  public InterMessView(PetalNode parent, Collection params, int tag) {
    super(parent, "InterMessView", params, tag);
  }

  public InterMessView() {
    super("InterMessView");
  }

  public SegLabel getLabel() {
    return (SegLabel)getProperty("label");
  }

  public void setLabel(SegLabel o) {
    defineProperty("label", o);
  }

  public Tag getFocusSrc() {
    return (Tag)getProperty("Focus_Src");
  }

  public void setFocusSrc(Tag o) {
    defineProperty("Focus_Src", o);
  }

  public Tag getFocusEntry() {
    return (Tag)getProperty("Focus_Entry");
  }

  public void setFocusEntry(Tag o) {
    defineProperty("Focus_Entry", o);
  }

  public Location getOrigin() {
    return (Location)getProperty("origin");
  }

  public void setOrigin(Location o) {
    defineProperty("origin", o);
  }

  public Location getTerminus() {
    return (Location)getProperty("terminus");
  }

  public void setTerminus(Location o) {
    defineProperty("terminus", o);
  }

  public int getOrdinal() {
    return getPropertyAsInteger("ordinal");
  }

  public void setOrdinal(int o) {
    defineProperty("ordinal", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
