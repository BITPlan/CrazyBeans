/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 */
package cb.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite for CrazyBeans
 * @author wf
 * @since 2015-01-11
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestTemplates.class, TestUniversityModel.class,
		TestGenerator.class, TestPetalTreeView.class , TestPetalTool.class, TestTaggedValues.class})
public class TestSuite {

}
