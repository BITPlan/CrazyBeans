package test;

import cb.petal.*;
import cb.parser.*;
import java.util.*;
import cb.util.*;
import cb.generator.Generator;
import cb.generator.Factory;
import java.io.File;

/**
 * Create sources for the university model. Also create <a href=
 * "http://barat.sourceforge.net/">Barat</A> class sources from model. In
 * the latter example it shows how to subclass the Generator and Factory
 * in order to adapt them to the behaviour you want.
 *
 * @version $Id: Test2.java,v 1.8 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Test2 {
  public static void main(String[] args) throws Exception {
    /* For the university classes we just use the predefined behaviour
     */
    PetalFile tree = PetalParser.createParser(".." + File.separatorChar +
					      "examples" + File.separatorChar +
					      "uni.mdl").parse();
    String    dump = cb.util.Constants.isDOS()? "C:\\TEMP\\Test2" : "/tmp/Test2";

    Generator gen  = new Generator(tree, dump, ".java");
    tree.accept(new PiggybackVisitor(gen));
    gen.dump();


    /* With Barat there are some special considerations: We suppress the generation
     * of associations, and take the all classes in barat.reflect are marked as interfaces.
     */
    tree = PetalParser.createParser(".." + File.separatorChar + "examples" +
				    File.separatorChar + "Barat.mdl").parse();
    Factory   f    = new Factory() {
      public void addAssociation(cb.generator.Class class1, Role role1,
				 cb.generator.Class class2, Role role2,
				 cb.generator.Class assoc_class) {
	return; // Just do nothing here
      }
    };

    Factory.setInstance(f);

    gen = new Generator(tree, dump, ".java") {
      public void visit(cb.petal.Class clazz) {
	if(clazz.getQualifiedName().startsWith("Logical View::barat::reflect"))
	  clazz.setStereotype("Interface"); // Meant to be interfaces
	super.visit(clazz);
      }
    };

    tree.accept(new PiggybackVisitor(gen));
    gen.dump();
  }
}
