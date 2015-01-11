package cb.util;

import cb.petal.DescendingVisitor;
import cb.petal.EmptyVisitor;
import cb.petal.PetalObject;
import cb.petal.Visitor;
import cb.test.*;

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

  public PiggybackVisitor(Visitor v) {
    this.v = v;
  }

  public void visitObject(PetalObject obj) {
    obj.accept(v);
    super.visitObject(obj);
  }
}
