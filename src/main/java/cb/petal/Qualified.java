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
 * Denote that a petal object has a qualified name parameter, e.g.
 * "Logical View::University::Professor"
 *
 * @version $Id: Qualified.java,v 1.1 2001/07/19 12:40:40 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Qualified {
  public void setQualifiedNameParameter(String name);
  public String getQualifiedNameParameter();
}
