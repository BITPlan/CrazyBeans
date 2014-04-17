package cb.petal;

/**
 * Represents defaults for top level design object.
 *
 * @version $Id: Defaults.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Defaults extends PetalObject {
  public Defaults(PetalNode parent, java.util.Collection params) {
    super(parent, "defaults", params);
  }

  public Defaults() {
    super("defaults");
  }

  public double getRightMargin() {
    return getPropertyAsFloat("rightMargin");
  }

  public void setRightMargin(double o) {
    defineProperty("rightMargin", o);
  }

  public double getLeftMargin() {
    return getPropertyAsFloat("leftMargin");
  }

  public void setLeftMargin(double o) {
    defineProperty("leftMargin", o);
  }

  public double getTopMargin() {
    return getPropertyAsFloat("topMargin");
  }

  public void setTopMargin(double o) {
    defineProperty("topMargin", o);
  }

  public double getBottomMargin() {
    return getPropertyAsFloat("bottomMargin");
  }

  public void setBottomMargin(double o) {
    defineProperty("bottomMargin", o);
  }
  public double getPageOverlap() {
    return getPropertyAsFloat("pageOverlap");
  }

  public void setPageOverlap(double o) {
    defineProperty("pageOverlap", o);
  }

  public boolean getClipIconLabels() {
    return getPropertyAsBoolean("clipIconLabels");
  }

  public void setClipIconLabels(boolean o) {
    defineProperty("clipIconLabels", o);
  }

  public boolean getAutoResize() {
    return getPropertyAsBoolean("autoResize");
  }

  public void setAutoResize(boolean o) {
    defineProperty("autoResize", o);
  }

  public boolean getSnapToGrid() {
    return getPropertyAsBoolean("snapToGrid");
  }

  public void setSnapToGrid(boolean o) {
    defineProperty("snapToGrid", o);
  }

  public int getGridX() {
    return getPropertyAsInteger("gridX");
  }

  public void setGridX(int o) {
    defineProperty("gridX", o);
  }

  public int getGridY() {
    return getPropertyAsInteger("gridY");
  }

  public void setGridY(int o) {
    defineProperty("gridY", o);
  }

  public Font getDefaultFont() {
    return (Font)getProperty("defaultFont");
  }

  public void setDefaultFont(Font o) {
    defineProperty("defaultFont", o);
  }

  public int getShowMessageNum() {
    return getPropertyAsInteger("showMessageNum");
  }

  public void setShowMessageNum(int o) {
    defineProperty("showMessageNum", o);
  }

  public boolean getShowClassOfObject() {
    return getPropertyAsBoolean("showClassOfObject");
  }
  public void setShowClassOfObject(boolean o) {
    defineProperty("showClassOfObject", o);
  }

  public String getNotation() {
    return getPropertyAsString("notation");
  }

  public void setNotation(String o) {
    defineProperty("notation", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
