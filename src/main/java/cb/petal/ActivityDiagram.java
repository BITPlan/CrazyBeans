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
 * Represents ActivityDiagram object
 *
 * @version $Id: ActivityDiagram.java,v 1.1 2001/11/20 11:24:28 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ActivityDiagram extends Diagram {
  public ActivityDiagram(PetalNode parent, Collection params) {
    super(parent, "ActivityDiagram", params);
  }

  public ActivityDiagram() {
    super("ActivityDiagram");
  }

  // TODO
  protected View searchView(String qual_name) {
    throw new RuntimeException("TODO: Not implemented yet");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
