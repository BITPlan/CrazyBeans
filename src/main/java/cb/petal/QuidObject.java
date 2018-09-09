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
 * Super class for all petal objects that have a "quid" property
 * defined, i.e. a globally unique identifier.
 *
 * @version $Id: QuidObject.java,v 1.6 2001/07/09 07:48:52 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class QuidObject extends PetalObject {
  static final long serialVersionUID = -1442259881047075234L;

  /**
   * create a QuidObject for the given parent with the given name and the
   * given collection of params
   * @param parent
   * @param name
   * @param params
   */
  protected QuidObject(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  protected QuidObject(String name) {
    super(name);
  }

  static String getQuid(QuidObject obj) {
    return PetalObject.getPropertyAsString(obj, "quid");
  }

  /** 
   * Registers this object.
   */
  public void init() {
    getRoot().registerQuidObject(this);
  }

  public void setQuid(String quid) {
    defineProperty("quid", quid);
  }

  public String getQuid() {
    return getQuid(this);
  }

  public long getQuidAsLong() {
    return Long.parseLong(getQuid(), 16);
  }

  public void setQuidAsLong(long quid) {
    defineProperty("quid", Long.toHexString(quid).toUpperCase());
  }
}
