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
 * Represents Link object
 *
 * @version $Id: Link.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Link extends QuidObject {
  public Link(PetalNode parent, Collection params) {
    super(parent, "Link", params);
  }

  public Link() {
    super("Link");
  }

  public String getSupplier() {
    return getPropertyAsString("supplier");
  }

  public void setSupplier(String o) {
    defineProperty("supplier", o);
  }

  public String getSupplierContainment() {
    return getPropertyAsString("supplier_containment");
  }

  public void setSupplierContainment(String o) {
    defineProperty("supplier_containment", o);
  }

  public String getClientContainment() {
    return getPropertyAsString("client_containment");
  }

  public void setClientContainment(String o) {
    defineProperty("client_containment", o);
  }

  public PetalNodeList getMessages() {
    return (PetalNodeList)getProperty("messages");
  }

  public void setMessages(PetalNodeList o) {
    defineProperty("messages", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
