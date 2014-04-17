package cb.petal;
import java.text.NumberFormat;
import java.util.*;

/**
 * Floating point literal (in fact a double).
 *
 * @version $Id: FloatLiteral.java,v 1.10 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class FloatLiteral extends Literal {
  static final long serialVersionUID=1505488095051523558L;

  private double value;
  private static NumberFormat format;

  static {
    format = NumberFormat.getInstance(Locale.ENGLISH);
    format.setMaximumFractionDigits(6);
    format.setMinimumFractionDigits(6);
  }

  public FloatLiteral(double value) {
    super("<float>");
    this.value = value;
  }

  public double getValue()         { return value; }
  public void   setValue(double d) { value = d; }

  public java.lang.Object getLiteralValue() {
    return new Float(value);
  }

  public String toString() {
    return format.format(value);
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof FloatLiteral) && (((FloatLiteral)o).value == this.value);
  }
}
