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
import cb.petal.AccessQualified;
import cb.petal.DescendingVisitor;
import cb.petal.LogicalCategory;
import cb.petal.PetalFile;
import cb.petal.PetalObject;
import cb.petal.Visibility;

/**
 * test the petal parser
 * 
 * @author wf
 *
 */
public class TestParser {

  class AccessVisitor extends DescendingVisitor {
    public int visitCount = 0;
    public int[] visibilityCounter = new int[Visibility.values().length];

    public void visitObject(PetalObject pobj) {
      super.visitObject(pobj);
      visitCount++;
      if (pobj instanceof AccessQualified) {
        AccessQualified aq = (AccessQualified) pobj;
        Visibility visibility = aq.getVisibility();
        visibilityCounter[visibility.ordinal()]++;
      }
    }
  }

  /**
   * test the parser
   */
  @Test
  public void testParser() {
    // test File (taken from BITPlan smartGENERATOR test cases ...
    File petalFile = new File("examples/AF2006_AC001.mdl");
    PetalObject.strict = false;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    assertEquals(2, petalTree.getChildCount());
    LogicalCategory rootCat = petalTree.getLogicalCategory();
    assertEquals(7, rootCat.getChildCount());
    AccessVisitor accessVisitor = new AccessVisitor();
    petalTree.accept(accessVisitor);
    assertEquals(136, accessVisitor.visitCount);
    int expectedVisibilities[] = { 4, 0, 1,0, 5 };
    for (Visibility visibility : Visibility.values()) {
      assertEquals(visibility.toString(),
          expectedVisibilities[visibility.ordinal()], 
          accessVisitor.visibilityCounter[visibility.ordinal()]);
    }
  }
}
