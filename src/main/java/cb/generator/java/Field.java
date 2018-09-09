/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.generator.java;

import java.util.List;

/**
 * Represents a field of a class.
 *
 * @version $Id: Field.java,v 1.2 2001/06/22 09:06:34 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Field extends Node {
  public void   setInitialValue(String s);
  public String getInitialValue();
  public void   setType(String p);
  public String getType();
  public List<String> getDocumentation();
}
