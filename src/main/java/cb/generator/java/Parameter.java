/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.generator.java;

/**
 * Represents a formal parameter of a method,
 *
 * @version $Id: Parameter.java,v 1.2 2001/06/22 09:06:34 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Parameter extends Node {
  public void   setType(String p);
  public String getType();
}
