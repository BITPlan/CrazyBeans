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
 * Represents Compartment object
 *
 * @version $Id: Compartment.java,v 1.7 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Compartment extends PetalObject {
  public Compartment(PetalNode parent, Collection params) {
    super(parent, "Compartment", params);
  }

  public Compartment() {
    super("Compartment");
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

  public String getIconStyle() {
    return getPropertyAsString("icon_style");
  }

  public void setIconStyle(String o) {
    defineProperty("icon_style", o);
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

  public void accept(Visitor v) {
    v.visit(this);
  }
}
