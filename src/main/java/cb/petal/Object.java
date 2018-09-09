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
 * Represents Object object
 *
 * @version $Id: Object.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Object extends QuiduObject implements Named {
  public Object(PetalNode parent, Collection params) {
    super(parent, "Object", params);
  }

  public Object() {
    super("Object");
  }

  /** @return instantiated class
   */
  public Class getInstantiatedClass() {
    return getRoot().getClassByQuidu(this);
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public PetalNodeList getCollaborators() {
    return (PetalNodeList)getProperty("collaborators");
  }

  public void setCollaborators(PetalNodeList o) {
    defineProperty("collaborators", o);
  }

  public String getClassName() {
    return getPropertyAsString("class");
  }

  public void setClassName(String o) {
    defineProperty("class", o);
  }

  public String getPersistence() {
    return getPropertyAsString("persistence");
  }

  public void setPersistence(String o) {
    defineProperty("persistence", o);
  }

  public boolean getMulti() {
    return getPropertyAsBoolean("multi");
  }

  public void setMulti(boolean o) {
    defineProperty("multi", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
