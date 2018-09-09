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
 * Super class for all relationships between classes.
 *
 * @version $Id: Relationship.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Relationship extends QuiduObject
  implements HasSupplier, StereoTyped, Labeled, Documented
{
  public Relationship(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  public Relationship(String name) {
    super(name);
  }

  public String getSupplier() {
    return getPropertyAsString("supplier");
  }

  public void setSupplier(String o) {
    defineProperty("supplier", o);
  }

  public String getStereotype() {
    return getPropertyAsString("stereotype");
  }

  public void setStereotype(String c) {
    defineProperty("stereotype", c);
  }

  public void setLabel(String o) {
    defineProperty("label", o);
  }

  public String getLabel() {
    return getPropertyAsString("label");
  }

  public void setDocumentation(String o) {
    defineProperty("documentation", o);
  }

  public String getDocumentation() {
    return getPropertyAsString("documentation");
  }
}
