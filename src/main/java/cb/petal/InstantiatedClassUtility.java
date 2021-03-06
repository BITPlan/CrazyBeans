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
 * Represents Instantiated_Class object
 *
 * @version $Id: InstantiatedClassUtility.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InstantiatedClassUtility extends InstantiatedClass {
  public InstantiatedClassUtility(PetalNode parent, Collection params) {
    super(parent, params);
    setName("Instantiated_Class_Utility");
  }

  public InstantiatedClassUtility() {
    setName("Instantiated_Class_Utility");
  }
}
