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

public abstract class NamedQuidObject extends QuidObject implements Named {

  /**
   * create a NamedQuidObject for the given parent with the given name and the
   * given collection of params
   * @param parent
   * @param name
   * @param params
   */
  protected NamedQuidObject(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  protected NamedQuidObject(String name) {
    super(name);
  }
  
  /**
   * 
   */
  private static final long serialVersionUID = -2863290181609263690L;

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

}
