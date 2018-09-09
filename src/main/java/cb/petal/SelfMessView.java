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
 * Represents SelfMessView object = message to self in sequence diagram
 *
 * @version $Id: SelfMessView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SelfMessView extends QuiduView {
  public SelfMessView(PetalNode parent, Collection params, int tag) {
    super(parent, "SelfMessView", params, tag);
  }

  public SelfMessView() {
    super("SelfMessView");
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
