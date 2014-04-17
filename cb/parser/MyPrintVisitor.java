package cb.parser;
import java.util.Iterator;
import java.io.*;
import java.util.Collection;
import java.util.Stack;
import cb.util.*;
import cb.petal.*;

/**
 * (Experimental) Just prints some information about the traversed class.
 *
 * @version $Id: MyPrintVisitor.java,v 1.7 2001/11/01 15:56:49 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class MyPrintVisitor extends PrintVisitor {
  public MyPrintVisitor(PrintStream out) {
    super(out);
  }

  public void visitObject(PetalObject obj) {
    PetalNode node = obj.getProperty("quidu");

    if(node != null) {
      if(!(obj instanceof HasQuidu))
	System.err.println(obj.getName() + " has quidu " + node);
    } else if(obj instanceof HasQuidu) {
      System.err.println(obj.getName() + " has NO quidu ");
    }


    super.visitObject(obj);
  }

  public void visit(cb.petal.Class obj) {
    cb.petal.Class super_ = obj.getSuperclass();
    java.util.List list   = obj.getAssociations();

    System.err.println(obj.getQualifiedName());

    if(super_ != null) {
      System.err.println("Super class of " + obj.getNameParameter() + ":" +
			 super_.getNameParameter());
    } else if(list != null) {

      System.err.print("Associations of " + obj.getNameParameter() + ":");

      for(Iterator i = list.iterator(); i.hasNext(); ) {
	Association a = (Association)i.next();
	System.err.println(((Named)a.getFirstClient()).getNameParameter() + " <-> " +
			   ((Named)a.getSecondClient()).getNameParameter());

	cb.petal.Class clazz = a.getAssociationClass();
	if(clazz != null)
	  System.err.println("ASSOCIATIONCLASS:" + clazz.getNameParameter());
      }
    }

    visitObject(obj);
  }

  public void visit(Association obj) {
    java.util.List list = obj.getRoles().getElements();

    Role role1   = (Role)list.get(0);
    Role role2   = (Role)list.get(1);

    QuidObject class1 = obj.getFirstClient();
    QuidObject class2 = obj.getSecondClient();

    String name1 = role1.getNameParameter();
    String name2 = role2.getNameParameter();
    String card1 = role1.getCardinality();
    String card2 = role2.getCardinality();

    System.err.println("Association named " + obj.getNameParameter() + " between:\n" +
		       ((Named)class1).getNameParameter() +
		       "(" + name1 + ":" + card1 +
		       ":" + role1.isNavigable() + ":" + role1.isAggregate() +
		       ")" +
		       "\n" +
		       ((Named)class2).getNameParameter() +
		       "(" + name2 + ":" + card2 +
		       ":" + role2.isNavigable() + ":" + role2.isAggregate() +
		       ")"
		       );
    visitObject(obj);
  }

  public static void main(String[] args) {
    PetalFile tree = PetalParser.parse(args);
    tree.accept(new MyPrintVisitor(System.out));
  }
}
