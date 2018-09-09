/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.util;

import cb.petal.DescendingVisitor;
import cb.petal.EmptyVisitor;
import cb.petal.PetalNodeList;
import cb.petal.PetalObject;
import cb.petal.Visitor;


/**
 * Just take a visitor "piggy-backed" and apply it to all petal
 * objects during traversal. This useful when translating petal files
 * to other structures, e.g.,  a converter to Java. This class is
 * related to the Builder pattern; the piggy-backed visitor will
 * usually be a subclass of EmptyVisitor.
 *
 * @see EmptyVisitor
 *
 * @version $Id: PiggybackVisitor.java,v 1.2 2001/06/22 09:07:13 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class PiggybackVisitor extends DescendingVisitor {
  private Visitor v;

  /**
   * initialize me with another visitor
   * @param v
   */
  public PiggybackVisitor(Visitor v) {
    this.v = v;
  }
  
  /**
   * apply both visitors to lists
   */
  @Override
  public void visit(PetalNodeList list) {
    list.accept(v);
    super.visit(list);
  }

  /**
   * apply both visitors for the given obj
   * @param obj - the object to visit
   */
  @Override
  public void visitObject(PetalObject obj) {
    obj.accept(v);
    super.visitObject(obj);
  }
}
