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
 * Represents ItemLabel object attached to View objects, ClassView in particular
 *
 * @version $Id: ItemLabel.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ItemLabel extends PetalObject {
  static final long serialVersionUID = 434686941683143909L;
  
  public ItemLabel(PetalNode parent, Collection params) {
    super(parent, "ItemLabel", params);
  }

  public ItemLabel() {
    super("ItemLabel");
  }

  public Tag getParentView() {
    return (Tag)getProperty("Parent_View");
  }

  public void setParentView(Tag o) {
    defineProperty("Parent_View", o);
  }

  public Location getLocation() {
    return (Location)getProperty("location");
  }

  public void setLocation(Location o) {
    defineProperty("location", o);
  }

  public int getAnchorLoc() {
    return getPropertyAsInteger("anchor_loc");
  }

  public void setAnchorLoc(int o) {
    defineProperty("anchor_loc", o);
  }

  public int getNlines() {
    return getPropertyAsInteger("nlines");
  }

  public void setNlines(int o) {
    defineProperty("nlines", o);
  }

  public int getMaxWidth() {
    return getPropertyAsInteger("max_width");
  }

  public void setMaxWidth(int o) {
    defineProperty("max_width", o);
  }

  public int getJustify() {
    return getPropertyAsInteger("justify");
  }

  public void setJustify(int o) {
    defineProperty("justify", o);
  }

  public String getLabel() {
    return getPropertyAsString("label");
  }

  public void setLabel(String o) {
    defineProperty("label", o);
  }

  public int getFillColor() {
    return getPropertyAsInteger("fill_color");
  }

  public void setFillColor(int o) {
    defineProperty("fill_color", o);
  }

  public int getAnchor() {
    return getPropertyAsInteger("anchor");
  }

  public void setAnchor(int o) {
    defineProperty("anchor", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}

