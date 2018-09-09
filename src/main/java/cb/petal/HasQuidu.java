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
 * Denote that a petal object contains a reference to another object via
 * the property "quidu".
 *
 * @version $Id: HasQuidu.java,v 1.5 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface HasQuidu extends PetalNode {
  public void setQuidu(String quid);
  public String getQuidu();
  public long getQuiduAsLong();
  public void setQuiduAsLong(long quid);
  /** @return object referenced via "quidu" property
   */
  public QuidObject getReferencedObject();
}
