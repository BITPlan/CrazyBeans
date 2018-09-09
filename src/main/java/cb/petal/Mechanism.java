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
 * Represents Mechanism object
 *
 * @version $Id: Mechanism.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Mechanism extends PetalObject implements Tagged {
  public Mechanism(PetalNode parent, Collection params) {
    super(parent, "Mechanism", params);
    setTag(tag);
  }

  public Mechanism() {
    super("Mechanism");
  }

  private int tag = -1;

  public void setTag(int t) { tag = t; }

  public int  getTag()      { return tag; }

  public PetalNodeList getLogicalModels() {
    return (PetalNodeList)getProperty("logical_models");
  }

  public void setLogicalModels(PetalNodeList o) {
    defineProperty("logical_models", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
