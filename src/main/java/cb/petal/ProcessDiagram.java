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
 * Represents Process_Diagram object
 *
 * @version $Id: ProcessDiagram.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ProcessDiagram extends QuidObject {
  public ProcessDiagram(PetalNode parent, Collection params) {
    super(parent, "Process_Diagram", params);
  }

  public ProcessDiagram() {
    super("Process_Diagram");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public String getTitle() {
    return getPropertyAsString("title");
  }

  public void setTitle(String o) {
    defineProperty("title", o);
  }

  public int getZoom() {
    return getPropertyAsInteger("zoom");
  }

  public void setZoom(int o) {
    defineProperty("zoom", o);
  }

  public int getMaxHeight() {
    return getPropertyAsInteger("max_height");
  }

  public void setMaxHeight(int o) {
    defineProperty("max_height", o);
  }

  public int getMaxWidth() {
    return getPropertyAsInteger("max_width");
  }

  public void setMaxWidth(int o) {
    defineProperty("max_width", o);
  }

  public int getOriginX() {
    return getPropertyAsInteger("origin_x");
  }

  public void setOriginX(int o) {
    defineProperty("origin_x", o);
  }

  public int getOriginY() {
    return getPropertyAsInteger("origin_y");
  }

  public void setOriginY(int o) {
    defineProperty("origin_y", o);
  }

  public List getItems() {
    return (List)getProperty("items");
  }

  public void setItems(List o) {
    defineProperty("items", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
