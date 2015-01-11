/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.test;

import org.junit.Test;

import cb.generator.Factory;
import cb.generator.Generator;
import cb.parser.PetalParser;
import cb.petal.PetalFile;
import cb.petal.Role;
import cb.util.PiggybackVisitor;

/**
 * Create sources for the university model. Also create <a href=
 * "http://barat.sourceforge.net/">Barat</A> class sources from model. In the
 * latter example it shows how to subclass the Generator and Factory in order to
 * adapt them to the behaviour you want.
 *
 * @version $Id: Test2.java,v 1.8 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class TestGenerator extends BaseTest {


	@Test
	public void testGenerator() throws Exception {
		/*
		 * For the university classes we just use the predefined behaviour
		 */
		PetalFile tree = PetalParser.createParser(getExampleFileName("uni.mdl"))
				.parse();
		String dump = cb.util.Constants.isDOS() ? "C:\\TEMP\\TestGenerator" : "/tmp/TestGenerator";

		Generator gen = new Generator(tree, dump, ".java");
		tree.accept(new PiggybackVisitor(gen));
		gen.dump();

		/*
		 * With Barat there are some special considerations: We suppress the
		 * generation of associations, and take the all classes in barat.reflect are
		 * marked as interfaces.
		 */
		tree = PetalParser.createParser(getExampleFileName("Barat.mdl")).parse();
		Factory f = new Factory() {
			public void addAssociation(cb.generator.Class class1, Role role1,
					cb.generator.Class class2, Role role2, cb.generator.Class assoc_class) {
				return; // Just do nothing here
			}
		};

		Factory.setInstance(f);

		gen = new Generator(tree, dump, ".java") {
			public void visit(cb.petal.Class clazz) {
				if (clazz.getQualifiedName().startsWith("Logical View::barat::reflect"))
					clazz.setStereotype("Interface"); // Meant to be interfaces
				super.visit(clazz);
			}
		};

		tree.accept(new PiggybackVisitor(gen));
		gen.dump();
	}
}