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
 * Represents Message object
 *
 * @version $Id: Message.java,v 1.8 2001/06/22 09:10:36 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Message extends NamedQuidObject  {
  public Message(PetalNode parent, Collection params) {
    super(parent, "Message", params);
  }

  public Message() {
    super("Message");
  }

  public String getFrequency() {
    return getPropertyAsString("frequency");
  }

  public void setFrequency(String o) {
    defineProperty("frequency", o);
  }

  public String getSynchronization() {
    return getPropertyAsString("synchronization");
  }

  public void setSynchronization(String o) {
    defineProperty("synchronization", o);
  }

  public String getDir() {
    return getPropertyAsString("dir");
  }

  public void setDir(String o) {
    defineProperty("dir", o);
  }

  public String getSequence() {
    return getPropertyAsString("sequence");
  }

  public void setSequence(String o) {
    defineProperty("sequence", o);
  }

  public int getOrdinal() {
    return getPropertyAsInteger("ordinal");
  }

  public void setOrdinal(int o) {
    defineProperty("ordinal", o);
  }

  public String getOperation() {
    return getPropertyAsString("Operation");
  }

  public void setOperation(String o) {
    defineProperty("Operation", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
