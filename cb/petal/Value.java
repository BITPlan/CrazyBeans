package cb.petal;

/**
 * Values like in (value Text "foo")
 *
 * @version $Id: Value.java,v 1.11 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Value extends Literal {
  static final long serialVersionUID=-1364810248783653817L;

  private String        name;
  private StringLiteral value;

  public Value(String name, StringLiteral value) {
    super("value");
    setValueName(name);
    setValue(value);
  }

  public void   setValueName(String n) { name = n.intern(); }
  public String getValueName()         { return name; }
  public void          setValue(StringLiteral v) { value = v; }
  public StringLiteral getValue()                { return value; }

  public String getStringValue() { return value.getValue(); }

  public String toString() { return "(value " + name + " " + value + ")"; }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public java.lang.Object getLiteralValue() {
    return name + " \"" + value.getLiteralValue() + '"';
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof Value) && (((Value)o).value.equals(this.value)) &&
      (((Value)o).name.equals(this.name));
  }
}
