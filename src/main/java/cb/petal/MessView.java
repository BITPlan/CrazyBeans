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
 * Represents MessView object
 *
 * @version $Id: MessView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class MessView extends View {
  public MessView(PetalNode parent, Collection params, int tag) {
    super(parent, "MessView", params, tag);
  }

  public MessView() {
    super("MessView");
  }

  public SegLabel getLabel() {
    return (SegLabel)getProperty("label");
  }

  public void setLabel(SegLabel o) {
    defineProperty("label", o);
  }

  public Tag getObjectArc() {
    return (Tag)getProperty("object_arc");
  }

  public void setObjectArc(Tag o) {
    defineProperty("object_arc", o);
  }

  public double getPctDist() {
    return getPropertyAsFloat("pctDist");
  }

  public void setPctDist(double o) {
    defineProperty("pctDist", o);
  }

  public int getOrientation() {
    return getPropertyAsInteger("orientation");
  }

  public void setOrientation(int o) {
    defineProperty("orientation", o);
  }

  public int getDir() {
    return getPropertyAsInteger("dir");
  }

  public void setDir(int o) {
    defineProperty("dir", o);
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

  public Tag getDataFlowView() {
    return (Tag)getProperty("DataFlowView");
  }

  public void setDataFlowView(Tag o) {
    defineProperty("DataFlowView", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
