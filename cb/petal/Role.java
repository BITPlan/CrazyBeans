package cb.petal;
import java.util.Collection;

/**
 * Represents Role object
 *
 * @version $Id: Role.java,v 1.16 2001/08/01 14:26:56 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Role extends QuiduObject
  implements AccessQualified, HasSupplier, Named, Documented
{
  static final long serialVersionUID = -8702356058432809151L;

  public Role(PetalNode parent, Collection params) {
    super(parent, "Role", params);
  }

  public Role() {
    super("Role");
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public String getSupplier() {
    return getPropertyAsString("supplier");
  }

  public void setSupplier(String o) {
    defineProperty("supplier", o);
  }

  /** @return cardinality of role as string
   */
  public String getCardinality() {
    Value v = getClientCardinality();

    if(v != null)
      return v.getStringValue();
    else
      return null;
  }

  public void setCardinality(String card) {
     setClientCardinality(new Value("cardinality", new StringLiteral(card)));
  }

  /** @return role object of other side of this association
   */
  public Role getOtherRole() {
    Association assoc = (Association)getParent();
    return (assoc.getFirstRole() == this)? assoc.getSecondRole() : assoc.getFirstRole();
  }

  /** @return true if this an aggregation, i.e., the rhomb is not filled, this depends
   * on the containment of the other role
   */
  public boolean isAggregation() {
    return getIsAggregate() && !isComposition();
  }

  /** @return true if this a composite, i.e., the rhomb is filled,
   * this depends on the containment of the other role.
   */
  public boolean isComposition() {
    if(getIsAggregate()) {
      String cont = getOtherRole().getContainment();

      if(cont == null)
	return false;

      return "by value".equals(cont.toLowerCase());
    } else
      return false;
  }

  public Value getClientCardinality() {
    return (Value)getProperty("client_cardinality");
  }

  public void setClientCardinality(Value o) {
    defineProperty("client_cardinality", o);
  }

  public boolean getIsAggregate() {
    return getPropertyAsBoolean("is_aggregate");
  }

  public void setIsAggregate(boolean o) {
    defineProperty("is_aggregate", o);
  }

  public List getAttributes() {
    return (List)getProperty("attributes");
  }

  public void setAttributes(List o) {
    defineProperty("attributes", o);
  }

  public String getRoleName() {
    return getNameParameter();
  }

  public void setRoleName(String o) {
    setNameParameter(o);
    setLabel(o);
  }

  public String getLabel() {
    return getPropertyAsString("label");
  }

  public void setLabel(String o) {
    defineProperty("label", o);
  }

  public String getContainment() {
    return getPropertyAsString("Containment");
  }

  /** Possible values are: "By value" and "By reference"
   */
  public void setContainment(String o) {
    defineProperty("Containment", o);
  }

  public String getConstraints() {
    return getPropertyAsString("Constraints");
  }

  public void setConstraints(String o) {
    defineProperty("Constraints", o);
  }

  public boolean isNavigable() {
    return getPropertyAsBoolean("is_navigable");
  }

  public void isNavigable(boolean o) {
    defineProperty("is_navigable", o);
  }

  public boolean isAggregate() {
    return getPropertyAsBoolean("is_aggregate");
  }

  public void isAggregate(boolean o) {
    defineProperty("is_aggregate", o);
  }

  public boolean isPrincipal() {
    return getPropertyAsBoolean("is_principal");
  }

  public void isPrincipal(boolean o) {
    defineProperty("is_principal", o);
  }

  public String getDocumentation() {
    return getPropertyAsString("documentation");
  }

  public void setDocumentation(String o) {
    defineProperty("documentation", o);
  }

  public boolean getStatic() {
    return getPropertyAsBoolean("static");
  }

  public void setStatic(boolean o) {
    defineProperty("static", o);
  }

  public String getExportControl() {
    return getPropertyAsString("exportControl");
  }

  public void setExportControl(String o) {
    defineProperty("exportControl", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
