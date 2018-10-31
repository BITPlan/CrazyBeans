/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import cb.parser.PetalParser;
import cb.petal.AccessQualified;
import cb.petal.Class;
import cb.petal.ClassView;
import cb.petal.Compartment;
import cb.petal.DescendingVisitor;
import cb.petal.InheritView;
import cb.petal.LogicalCategory;
import cb.petal.Operation;
import cb.petal.Parameter;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.QuidObject;
import cb.petal.SemanticInfo;
import cb.petal.View;
import cb.petal.Visibility;

/**
 * test the petal parser
 * 
 * @author wf
 *
 */
public class TestParser {
  public static boolean debug = false;

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
    int expectedVisibilities[] = { 4, 0, 1, 0, 5 };
    for (Visibility visibility : Visibility.values()) {
      assertEquals(visibility.toString(),
          expectedVisibilities[visibility.ordinal()],
          accessVisitor.visibilityCounter[visibility.ordinal()]);
    }
    QuidObject op1Obj = petalTree.getQuidObject("485A74FD0148");
    assertNotNull(op1Obj);
    assertTrue(op1Obj instanceof Operation);
    Operation op1 = (Operation) op1Obj;
    ArrayList<PetalNode> params = op1.getParameters().get();
    assertEquals(1,params.size());
    Parameter param=(Parameter) params.get(0);
    assertEquals("param1",param.getNameParameter());
    assertEquals("string",param.getType());
  }

  @Test
  public void testShowOperationSignature() {
    File petalFile = new File("examples/AF1010_P1.mdl");
    PetalObject.strict = false;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();

    ClassView classView = (ClassView) petalTree.getView(2);

    assertEquals("Logical View::af1010::Main::Class",
        classView.getQualifiedName());
    assertTrue(classView.getShowOperationSignature());
  }

  class ClassVisitor extends DescendingVisitor {
    int classCount = 0;

    public void visit(Class clazz) {
      super.visit(clazz);
      if (debug)
        System.out.println(
            String.format("%s(%s)", clazz.getQualifiedName(), clazz.getQuid()));
      classCount++;
    }
  }

  @Test
  public void testPathMap() {
    File petalFile = new File("examples/sgtest2018-09.mdl");
    PetalObject.strict = true;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    ClassVisitor classVisitor = new ClassVisitor();
    petalTree.accept(classVisitor);
    assertEquals(3, classVisitor.classCount);
    assertEquals("5B97A8DD0075", petalTree
        .getClassByQualifiedName("Logical View::com::bitplan::sgtest::ClassA")
        .getQuid());
    assertEquals("5B97A90901D2",
        petalTree
            .getClassByQualifiedName("Logical View::com::bitplan::catB::ClassB")
            .getQuid());
    assertEquals("5B97A9AC001F",
        petalTree
            .getClassByQualifiedName("Logical View::com::bitplan::catC::ClassC")
            .getQuid());
  }

  @Test
  public void testListFiles() {
    File petalFile = new File("examples/sgtest2018-09.mdl");
    PetalObject.strict = true;
    PetalParser parser = PetalParser.createParser(petalFile.getPath());
    PetalFile petalTree = parser.parse();
    assertNotNull(petalTree);
    List<File> files = parser.getFiles();
    assertEquals(3, files.size());
    Date now = new Date();
    assertFalse(parser.filesNewerThen(now));

  }

  @Test
  /**
   * test for https://github.com/BITPlan/CrazyBeans/issues/12
   */
  public void testTagLookup() {
    File petalFile = new File("examples/Hospital98.mdl");
    PetalObject.strict = true;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    View v1 = petalTree.getView(147);
    assertEquals(147, v1.getTag());
    assertTrue(v1 instanceof InheritView);
    View sview = v1.getSupplierView();
    View cview = v1.getClientView();
    assertEquals(134, sview.getTag());
    assertEquals(121, cview.getTag());
    assertTrue(sview instanceof ClassView);
    assertTrue(cview instanceof ClassView);
  }

  @Test
  public void testCompartmentItems() {
    File petalFile = new File("examples/ComponentDiagram98.mdl");
    PetalObject.strict = true;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    ClassView courseView = (ClassView) petalTree.getView(154);
    assertEquals("Logical View::UniversityArtifacts::Course",
        courseView.getQualifiedNameParameter());
    Compartment compartment = courseView.getCompartment();
    assertEquals(7, compartment.getChildCount());
    List<String> citems = compartment.getCompartmentItems();
    assertEquals(3, citems.size());
    assertEquals("+ getName()", citems.get(0));
    assertEquals("+ addProfessor()", citems.get(1));
    assertEquals("- name", citems.get(2));
  }

  @Test
  public void testSelfTransViewSupport() {
    File petalFile = new File("examples/AnswerinMachine.mdl");
    PetalObject.strict = true;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    View selfTransView = petalTree.getView(5);
    assertNotNull(selfTransView);
    System.out.println(selfTransView.getClass().getName());
  }
}
