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
 * Super class for literals like int, String, value, etc.
 *
 * @version $Id: Literal.java,v 1.7 2001/07/09 07:48:52 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Literal implements PetalNode {
  static final long serialVersionUID = 3238150669982522033L;
  private String kind;

  protected Literal(String kind) {
    this.kind = kind;
  }

  public String getKind() { return kind; }
  public int getChildCount() { return 0; }

  /** @return object corresponding to the usual Java hierarchy, e.g., an Integer for
   * an IntegerLiteral.
   */
  public abstract java.lang.Object getLiteralValue();

  public java.lang.Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      return null;
    }
  }
}
