package cb.petal;

import java.util.*;

/**
 * Super class for all petal objects that have access qualifiers, i.e.,
 * "Public", "Protected", "Private", or "Implementation" (whatever that means, probably
 * it reads "language dependent")
 *
 * @version $Id: AccessObject.java,v 1.4 2001/07/09 07:48:52 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class AccessObject extends QuidObject
  implements AccessQualified, Named, StereoTyped, Documented
{
  static final long serialVersionUID = -1442259881847075234L;

  protected AccessObject(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  protected AccessObject(String name) {
    super(name);
  }

  public void setNameParameter(String o) {
    params.set(0, o);
  }

  public String getNameParameter() {
    return (String)params.get(0);
  }

  public String getDocumentation() {
    return getPropertyAsString("documentation");
  }

  public void setDocumentation(String o) {
    defineProperty("documentation", o);
  }

  public String getStereotype() {
    return getPropertyAsString("stereotype");
  }

  public void setStereotype(String c) {
    defineProperty("stereotype", c);
  }

  public String getExportControl() {
    return getPropertyAsString("exportControl");
  }

  public void setExportControl(String o) {
    defineProperty("exportControl", o);
  }

  public boolean isPublic() {
    return "Public".equals(getExportControl());
  }

  public boolean isProtected() {
      return "Protected".equals(getExportControl());
  }

  public boolean isPrivate() {
      return "Private".equals(getExportControl());
  }
}
