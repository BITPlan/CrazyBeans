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
 * Represents uses relationship in use case diagrams, in class
 * diagrams this is also used to express dependency relationships.
 *
 * @version $Id: UsesRelationship.java,v 1.11 2001/07/09 07:48:52 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class UsesRelationship extends Relationship implements AccessQualified {
  public UsesRelationship(PetalNode parent, Collection params) {
    super(parent, "Uses_Relationship", params);
  }

  public UsesRelationship() {
    super("Uses_Relationship");
  }

  public List getAttributes() {
    return (List)getProperty("attributes");
  }

  public void setAttributes(List o) {
    defineProperty("attributes", o);
  }

  public String getExportControl() {
    return getPropertyAsString("exportControl");
  }

  public void setExportControl(String o) {
    defineProperty("exportControl", o);
  }

  public Value getClientCardinality() {
    return (Value)getProperty("client_cardinality");
  }

  public void setClientCardinality(Value o) {
    defineProperty("client_cardinality", o);
  }

  public Value getSupplierCardinality() {
    return (Value)getProperty("supplier_cardinality");
  }

  public void setSupplierCardinality(Value o) {
    defineProperty("supplier_cardinality", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
