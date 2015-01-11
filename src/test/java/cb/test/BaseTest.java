/**
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

import java.io.File;

import cb.util.PetalObjectFactory;

/**
 * base test class - supplies PetalObjectFactory
 * 
 * @author wf
 * @since 2015-01-11
 *
 */
public class BaseTest {
	PetalObjectFactory factory;

	/**
	 * Logging may be enabled by setting debug to true
	 */
	protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger("cb.test");

	boolean debug = false;

	/**
	 * the list of example model File Names
	 */
	protected String[] exampleModelFileNames = { "Barat.mdl", "empty.mdl",
			"JDK-12_01.mdl", // FIXME throws unknown variable exception
			"project.mdl", "ComponentDiagram98.mdl", "RUP01.mdl",
			"uni.mdl", "Hospital98.mdl" };

	/**
	 * enable debugging
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * check debugging state
	 * 
	 * @return
	 */
	public boolean isDebug() {
		return this.debug;
	}

	/**
	 * get the file name for the given exampleModelFileName
	 * 
	 * @param exampleModelFileName
	 * @return
	 */
	public String getExampleFileName(String exampleModelFileName) {
		String result = "examples" + File.separatorChar + exampleModelFileName;
		return result;
	}

	/**
	 * construct the BaseTest
	 */
	public BaseTest() {
		factory = PetalObjectFactory.getInstance();
		// relative classpath to use (for maven in src/main/resources)
		factory.setTemplateRoot("/templates/");

	}
}
