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

public class DestructionMarker extends View{

  protected DestructionMarker(PetalNode parent, String name, Collection params,
      int tag) {
    super(parent, "DestructionMarker", params, tag);
  }
  
  public DestructionMarker() {
    super("DestructionMarker");
  }

  @Override
  public void accept(Visitor v) {
    v.visit(this);
  }

}
