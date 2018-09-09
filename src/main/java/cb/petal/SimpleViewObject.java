/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

import java.util.*;

/**
 * Very simple object, basically just a record.
 *
 * @version $Id: SimpleViewObject.java,v 1.5 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SimpleViewObject extends QuiduView {
  public SimpleViewObject(PetalNode parent, String name, Collection params, int tag) {
    super(parent, name, params, tag);
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }
}
