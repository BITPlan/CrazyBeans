package cb.petal;

/**
 * Represents font object.
 *
 * @version $Id: Font.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Font extends PetalObject {
  public Font(PetalNode parent, java.util.Collection params) {
    super(parent, "Font", params);
  }

  public Font() {
    super("Font");
  }

  public int getSize() {
    return getPropertyAsInteger("size");
  }

  public void setSize(int o) {
    defineProperty("size", o);
  }

  public String getFace() {
    return getPropertyAsString("face");
  }

  public void setFace(String o) {
    defineProperty("face", o);
  }

  public boolean getBold() {
    return getPropertyAsBoolean("bold");
  }

  public void setBold(boolean o) {
    defineProperty("bold", o);
  }

  public boolean getItalics() {
    return getPropertyAsBoolean("italics");
  }

  public void setItalics(boolean o) {
    defineProperty("italics", o);
  }

  public boolean getUnderline() {
    return getPropertyAsBoolean("underline");
  }

  public void setUnderline(boolean o) {
    defineProperty("underline", o);
  }

  public boolean getStrike() {
    return getPropertyAsBoolean("strike");
  }

  public void setStrike(boolean o) {
    defineProperty("strike", o);
  }

  public int getColor() {
    return getPropertyAsInteger("color");
  }

  public void setColor(int o) {
    defineProperty("color", o);
  }

  public boolean getDefaultColor() {
    return getPropertyAsBoolean("default_color");
  }

  public void setDefaultColor(boolean o) {
    defineProperty("default_color", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
