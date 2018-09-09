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
 * Represents Processes object
 *
 * @version $Id: Processes.java,v 1.9 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Processes extends QuidObject {
  public Processes(PetalNode parent, Collection params) {
    super(parent, "Processes", params);
  }

  public Processes() {
    super("Processes");
  }

  public PetalNodeList getProcsNDevs() {
    return (PetalNodeList)getProperty("ProcsNDevs");
  }

  public void setProcsNDevs(PetalNodeList o) {
    defineProperty("ProcsNDevs", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
