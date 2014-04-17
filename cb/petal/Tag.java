package cb.petal;

/**
 * Reference to object.
 *
 * @version $Id: Tag.java,v 1.7 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Tag extends Literal {
  static final long serialVersionUID=-5297792541671900279L;

  private int value;

  public Tag(int v) {
    super("<tag>");
    value = v;
  }

  public void setValue(int v) { value = v; }
  public int  getValue()      { return value; }

  public String toString() { return "@" + value; }
  
  public java.lang.Object getLiteralValue() {
    return toString();
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof Tag) && (((Tag)o).value == this.value);
  }
}
