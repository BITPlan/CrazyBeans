package cb.petal;

/**
 * Tuple (name, value), like in ("DataBaseSet" 800)
 *
 * @version $Id: Tuple.java,v 1.10 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Tuple extends Literal {
  static final long serialVersionUID=-143079303638884203L;

  private String name;
  private int    value;

  public Tuple(String name, int value) {
    super("<tuple>");
    this.name = name;
    this.value = value;
  }

  public void   setName(String n) { name = n; }
  public String getName()         { return name; }
  public void   setValue(int v)   { value = v; }
  public int    getValue()        { return value; }

  public String toString() { return "(\"" + name + "\" " + value + ")"; }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public java.lang.Object getLiteralValue() {
    return toString();
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof Tuple) && (((Tuple)o).value == this.value) &&
      (((Tuple)o).name.equals(this.name));
  }
}
