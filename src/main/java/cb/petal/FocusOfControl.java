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
 * Represents Focus_Of_Control object
 *
 * @version $Id: FocusOfControl.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class FocusOfControl extends PetalObject implements Tagged {
  private int tag = -1;

  public FocusOfControl(PetalNode parent, Collection params, int tag) {
    super(parent, "Focus_Of_Control", params);
    setTag(tag);
  }

  public FocusOfControl() {
    super("Focus_Of_Control");
  }
  
  public void setTag(int t) { tag = t; }
  public int  getTag()      { return tag; }

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

  public Tag getInterObjView() {
    return (Tag)getProperty("InterObjView");
  }

  public void setInterObjView(Tag o) {
    defineProperty("InterObjView", o);
  }

  public int getHeight() {
    return getPropertyAsInteger("height");
  }

  public void setHeight(int o) {
    defineProperty("height", o);
  }

  public int getYCoord() {
    return getPropertyAsInteger("y_coord");
  }

  public void setYCoord(int o) {
    defineProperty("y_coord", o);
  }

  public boolean getNested() {
    return getPropertyAsBoolean("Nested");
  }

  public void setNested(boolean o) {
    defineProperty("Nested", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
