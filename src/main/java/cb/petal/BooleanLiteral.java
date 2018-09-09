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
 * Boolean literal which may obviously either be true or false.
 *
 * @version $Id: BooleanLiteral.java,v 1.9 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class BooleanLiteral extends Literal {
  private boolean value;
  static final long serialVersionUID=-6022972681974975784L;

  public BooleanLiteral(boolean value) {
    super("<boolean>");
    this.value = value;
  }

  public boolean getValue()          { return value; }

  public void setValue(boolean v)          { value = v; }

  public String toString() { return ("" + value).toUpperCase(); }

  public java.lang.Object getLiteralValue() {
    if(value)
      return Boolean.TRUE;
    else
      return Boolean.FALSE;
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof BooleanLiteral) && (((BooleanLiteral)o).value == this.value);
  }
}
