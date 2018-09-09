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
 * Represents AssocAttachView object which is means it is just the line drawn between
 * an association and an association class.
 *
 * @version $Id: AssocAttachView.java,v 1.9 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see AssociationViewNew
 */
public class AssocAttachView extends View {
  public AssocAttachView(PetalNode parent, Collection params, int tag) {
    super(parent, "AssocAttachView", params, tag);
  }

  public AssocAttachView() {
    super("AssocAttachView");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
