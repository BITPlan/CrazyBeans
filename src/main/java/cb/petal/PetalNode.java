/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

/**
 * Super class for all petal nodes, e.g., objects, lists, value, literals, etc..
 *
 * @version $Id: PetalNode.java,v 1.9 2001/07/06 11:10:50 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @since 2001
 */
public interface PetalNode extends java.io.Serializable, Cloneable {
  /**
   * get the kind of this PetalNode
   * @return - the PetaNode kind
   */
  public abstract String getKind();
  
  /**
   * get the number of child nodes
   * @return the number of child nodes
   */
  public abstract int getChildCount();
  
  /**
   * accept the given visitor
   * @param v - the visitor
   */
  public abstract void accept(Visitor v);
}
