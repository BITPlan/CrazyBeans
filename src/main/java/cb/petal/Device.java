/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;
import java.util.Collection;

/**
 * Represents Device object
 *
 * @version $Id: Device.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Device extends QuidObject {
  public Device(PetalNode parent, Collection params) {
    super(parent, "Device", params);
  }

  public Device() {
    super("Device");
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

  public String getCharacteristics() {
    return getPropertyAsString("characteristics");
  }

  public void setCharacteristics(String o) {
    defineProperty("characteristics", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
