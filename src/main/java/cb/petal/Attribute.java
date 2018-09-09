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
 * Represents Attribute objects used in tagged values @see ClassAttribute for Attributes of classes
 *
 * @version $Id: Attribute.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Attribute extends PetalObject {

  /**
   * construct me
   * @param parent
   * @param params
   */
  public Attribute(PetalNode parent, Collection params) {
    super(parent, "Attribute", params);
  }
  
  public Attribute() {
    super("Attribute");
  }

  public String getTool() {
    return getPropertyAsString("tool");
  }

  public void setTool(String o) {
    defineProperty("tool", o);
  }

  public String getAttributeName() {
    return getPropertyAsString("name");
  }

  public void setAttributeName(String o) {
    defineProperty("name", o);
  }

  public PetalNode getValue() {
    return getProperty("value");
  }
  
  /**
   * get the string representation of the value independent
   * of the internal details
   * 
   * @return the string representation of the value
   */
  public String getStringValue() {
    PetalNode valueNode=getValue();
    if (valueNode instanceof Value) {
      Value value=(Value)valueNode;
      return value.getStringValue();
    } else if (valueNode instanceof StringLiteral){
      StringLiteral literal=(StringLiteral)valueNode;
      return literal.getValue();
    } else if (valueNode instanceof List){
      List list=(List)valueNode;
      return String.format("{%d}",list.size());
    } else if (valueNode instanceof BooleanLiteral){
      BooleanLiteral bliteral=(BooleanLiteral)valueNode;
      return ""+bliteral.getValue();
    } else {
      return "";
    }
  }

  public void setValue(PetalNode o) {
    defineProperty("value", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
