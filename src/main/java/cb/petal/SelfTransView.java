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
 * SelfTransView
 */
public class SelfTransView extends QuiduView {
  /**
   * 
   */
  private static final long serialVersionUID = -6776083763275745763L;

  /**
   * construct a simple View Object
   * @param parent
   * @param name
   * @param params
   * @param tag
   */
  public SelfTransView(PetalNode parent, String name, Collection params, int tag) {
    super(parent, name, params, tag);
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }
}
