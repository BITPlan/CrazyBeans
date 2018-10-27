/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite for CrazyBeans
 * 
 * @author wf
 * @since 2015-01-11
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestTemplates.class, TestPathMap.class, TestUniversityModel.class,
    TestGenerator.class, TestPetalTreeView.class, TestPetalTool.class,
    TestTaggedValues.class, TestParser.class})
public class TestSuite {

}
