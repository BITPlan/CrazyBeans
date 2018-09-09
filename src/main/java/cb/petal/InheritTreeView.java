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
 * Represents InheritTreeView object
 *
 * @version $Id: InheritTreeView.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InheritTreeView extends QuiduView {
  public InheritTreeView(PetalNode parent, Collection params, int tag) {
    super(parent, "InheritTreeView", params, tag);
  }

  public InheritTreeView() {
    super("InheritTreeView");
  }

  public List getVertices() {
    return (List)getProperty("vertices");
  }

  public void setVertices(List o) {
    defineProperty("vertices", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
