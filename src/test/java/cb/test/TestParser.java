/**
 * Copyright (C) 2018 BITPlan GmbH
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

import java.io.File;

import org.junit.Test;

import cb.parser.PetalParser;
import cb.petal.LogicalCategory;
import cb.petal.PetalFile;
import cb.petal.PetalObject;

/**
 * test the petal parser
 * @author wf
 *
 */
public class TestParser {

  /**
   * test the parser
   */
  @Test
  public void testParser() {
    // test File (taken from BITPlan smartGENERATOR test cases ...
    File petalFile = new File("examples/AF2006_AC001.mdl");
    PetalObject.strict = false;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    assertEquals(2,petalTree.getChildCount());
    LogicalCategory rootCat = petalTree.getLogicalCategory();
    assertEquals(7,rootCat.getChildCount());
  }
}
