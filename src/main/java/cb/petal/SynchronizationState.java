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
 * Represents SynchronizationState object
 *
 * @version $Id: SynchronizationState.java,v 1.1 2001/11/30 12:16:51 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class SynchronizationState extends NamedQuidObject {
  public SynchronizationState(PetalNode parent, Collection params) {
    super(parent, "SynchronizationState", params);
  }

  public SynchronizationState() {
    super("SynchronizationState");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
