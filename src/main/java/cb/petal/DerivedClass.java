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
 * Super class for instantiated and paramerized class objects.
 *
 * @version $Id: DerivedClass.java,v 1.5 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class DerivedClass extends Class implements HasQuidu {
  public DerivedClass(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  protected DerivedClass(String name) {
    setName(name);
  }

  public PetalNodeList getUsedNodes() {
    return (PetalNodeList)getProperty("used_nodes");
  }

  public void setUsedNodes(PetalNodeList o) {
    defineProperty("used_nodes", o);
  }

  public void setQuidu(String quid) {
    defineProperty("quidu", quid);
  }

  public String getQuidu() {
    return QuiduObject.getQuidu(this);
  }

  public long getQuiduAsLong() {
    return Long.parseLong(getQuidu(), 16);
  }

  public void setQuiduAsLong(long quid) {
    defineProperty("quidu", Long.toHexString(quid).toUpperCase());
  }

  public QuidObject getReferencedObject() {
    return getRoot().getReferencedObject(this);
  }
}
