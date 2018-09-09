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
 * Represents top level petal object.
 *
 * @version $Id: Petal.java,v 1.6 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Petal extends PetalObject {
  public Petal(java.util.Collection params) {
    super(null, "Petal", params);
  }

  public Petal() {
    super("Petal");
  }

  public int getCharSet() {
    return getPropertyAsInteger("charset");
  }

  public void setCharSet(int o) {
    defineProperty("charset", o);
  }

  public String getWritten() {
    return getPropertyAsString("_written");
  }

  public void setWritten(String o) {
    defineProperty("_written", o);
  }

  public int getVersion() {
    return getPropertyAsInteger("version");
  }

  public void setVersion(int o) {
    defineProperty("version", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
