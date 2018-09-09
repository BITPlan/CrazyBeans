/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.junit.Test;

import cb.generator.java.Class;
import cb.generator.java.Factory;
import cb.generator.java.JavaGenerator;
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
	  Example uni=Example.get("uni");
		PetalFile tree = PetalParser.createParser(uni.getFilePath())
				.parse();
		String dump = uni.getSrcRoot();
		if (debug)
		  LOGGER.log(Level.INFO,dump);

		JavaGenerator gen = new JavaGenerator(tree, dump);
		tree.accept(new PiggybackVisitor(gen));
		gen.dump();
		assertEquals(0,gen.getWarn());

		/*
		 * With Barat there are some special considerations: We suppress the
		 * generation of associations, and take the all classes in barat.reflect are
		 * marked as interfaces.
		 */
		Example barat=Example.get("Barat");
		tree = PetalParser.createParser(barat.getFilePath()).parse();
		Factory f = new Factory() {
			public void addAssociation(cb.generator.java.Class class1, Role role1,
					cb.generator.java.Class class2, Role role2, cb.generator.java.Class assoc_class) {
				return; // Just do nothing here
			}
		};

		Factory.setInstance(f);

		gen = new JavaGenerator(tree, dump) {
			public void visit(cb.petal.Class clazz) {
				if (clazz.getQualifiedName().startsWith("Logical View::barat::reflect"))
					clazz.setStereotype("Interface"); // Meant to be interfaces
				super.visit(clazz);
			}
		};

		tree.accept(new PiggybackVisitor(gen));
		gen.dump();
		assertEquals(0,gen.getWarn());
		for (Entry<String, List<Class>> ce:gen.getClassesByPackage().entrySet()) {
			for (Class clazz:ce.getValue()) {
				// ids should look like 34CF28DD000A
				assertEquals(12,clazz.getId().length());
			}
		}
	}
}
