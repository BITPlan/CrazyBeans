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
 * Represents InterObjView object
 *
 * @version $Id: InterObjView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InterObjView extends QuiduView {
  public InterObjView(PetalNode parent, Collection params, int tag) {
    super(parent, "InterObjView", params, tag);
  }

  public InterObjView() {
    super("InterObjView");
  }

  public ItemLabel getLabel() {
    return (ItemLabel)getProperty("label");
  }

  public void setLabel(ItemLabel o) {
    defineProperty("label", o);
  }

  public int getIconHeight() {
    return getPropertyAsInteger("icon_height");
  }

  public void setIconHeight(int o) {
    defineProperty("icon_height", o);
  }

  public int getIconWidth() {
    return getPropertyAsInteger("icon_width");
  }

  public void setIconWidth(int o) {
    defineProperty("icon_width", o);
  }

  public int getAnnotation() {
    return getPropertyAsInteger("annotation");
  }

  public void setAnnotation(int o) {
    defineProperty("annotation", o);
  }

  public FocusOfControl getFocusOfControl() {
    return (FocusOfControl)getProperty("Focus_Of_Control");
  }

  public void setFocusOfControl(FocusOfControl o) {
    defineProperty("Focus_Of_Control", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
