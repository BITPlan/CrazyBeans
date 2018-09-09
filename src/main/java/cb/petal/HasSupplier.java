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
 * the property "supplier", i.e., a fully qualified name like
 * "Logical View::University::Professor".
 *
 * @version $Id: HasSupplier.java,v 1.4 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface HasSupplier {
  public String getSupplier();
  public void setSupplier(String o);
}
