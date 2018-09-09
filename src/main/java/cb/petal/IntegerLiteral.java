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
 * Integer literal.
 *
 * @version $Id: IntegerLiteral.java,v 1.9 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class IntegerLiteral extends Literal {
  static final long serialVersionUID=-3040774411406090728L;

  private int value;

  public IntegerLiteral(int value) {
    super("<int>");
    this.value = value;
  }

  public int    getValue()        { return value; }
  public void setValue(int i) { value = i; }

  public String toString() { return "" + value; }

  public java.lang.Object getLiteralValue() {
    return new Integer(value);
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof IntegerLiteral) && (((IntegerLiteral)o).value == this.value);
  }
}
