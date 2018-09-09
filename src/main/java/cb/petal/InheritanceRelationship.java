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
 * Represents inheritance relationship between classes.
 *
 * @version $Id: InheritanceRelationship.java,v 1.8 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class InheritanceRelationship extends Relationship {
  public InheritanceRelationship(PetalNode parent, Collection params) {
    super(parent, "Inheritance_Relationship", params);
  }

  public InheritanceRelationship() {
    super("Inheritance_Relationship");
  }

  public PetalNodeList getAttributes() {
    return (PetalNodeList)getProperty("attributes");
  }

  public void setAttributes(PetalNodeList o) {
    defineProperty("attributes", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
