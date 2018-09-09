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
 * Tuple of integer values like (1520, 96). Usually used to define coordinates.
 *
 * @version $Id: Location.java,v 1.11 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Location extends Literal {
  static final long serialVersionUID=7569774575687169931L;

  private int value1, value2;

  public Location(int first, int second) {
    super("<location>");
    value1 = first;
    value2 = second;
  }

  public int getFirstValue() { return value1; }
  public void setFirstValue(int v) { value1 = v; }

  public int getSecondValue() { return value2; }
  public void setSecondValue(int v) { value2 = v; }

  public String toString() { return "(" + value1 + ", " + value2 + ")"; }

  public java.lang.Object getLiteralValue() {
    return toString();
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof Location) && (((Location)o).value1 == this.value1)
      && (((Location)o).value2 == this.value2);
  }
}
