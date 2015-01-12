/**
 * Copyright (C) 2015 BITPlan GmbH
 *

 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.logging.Level;

import org.junit.Test;

import cb.petal.AssocAttachView;
import cb.petal.Association;
import cb.petal.AttachView;
import cb.petal.ClassView;
import cb.petal.InheritView;
import cb.petal.ItemLabel;
import cb.petal.LogicalCategory;
import cb.petal.NoteView;
import cb.petal.Operation;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.RealizeView;
import cb.petal.SegLabel;
import cb.petal.UseCaseCategory;
import cb.petal.UseCaseView;
import cb.petal.UsesView;

/**
 * test templates
 * 
 * @author wf
 * @since 2015-01-11
 *
 */
public class TestTemplates extends BaseTest {

	/**
	 * check the given PetalNode
	 * 
	 * @param petalNode
	 */
	public void check(PetalNode petalNode) {
		assertNotNull(petalNode.getKind());
		String expectedKind = "object";
		if (petalNode instanceof PetalFile)
			expectedKind = "root node";
		if (debug)
			LOGGER.log(Level.INFO, petalNode.getKind());
		assertEquals(expectedKind, petalNode.getKind());
	}

	/**
	 * test the template
	 */
	@Test
	public void testTemplates() {
		String templatePath = factory.getTemplateRoot();
		File templateDir = new File("src/main/resources" + templatePath);
		assertTrue(templateDir.exists());

		Class<?> petalClasses[] = {
				// ActorView.class - ser file exists but no class!
				AssocAttachView.class, Association.class, AttachView.class,
				cb.petal.Class.class, ClassView.class, InheritView.class,
				ItemLabel.class, LogicalCategory.class, NoteView.class,
				Operation.class, PetalFile.class, RealizeView.class, SegLabel.class,
				// StereotypeView.class,
				UseCaseCategory.class,
				// UseCaseStereotypeView.class,
				UseCaseView.class, UsesView.class, PetalFile.class };
		for (Class<?> petalClass : petalClasses) {
			String objectType = petalClass.getSimpleName();
			File template = new File(templateDir, objectType + ".ser");
			assertTrue(template.exists());
			PetalNode result = factory.getTemplate(objectType);
			assertEquals(petalClass, result.getClass());
			check(result);
		}
	}
}
