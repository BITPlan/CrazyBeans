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

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Test;

import cb.generator.java.Class;
import cb.generator.java.Field;
import cb.generator.java.JavaGenerator;
import cb.generator.java.Method;
import cb.generator.java.Node;
import cb.parser.PetalParser;
import cb.petal.PetalFile;
import cb.petal.PetalObject;

/**
 * test the tagged Value handling
 * 
 * @author wf
 *
 */
public class TestTaggedValues {
  boolean debug = false;

  /**
   * inspect the given node for name values and return them as a comma separated
   * name=value list
   * 
   * @param node
   * @return
   */
  public String inspectNode(Node node) {
    String result = "";
    String delim = "";
    if (debug)
      System.out.println(node.getClass().getSimpleName().replace("Impl", "")
          + " " + node.getName());
    for (Entry<String, String> taggedValueEntry : node.getTaggedValues()
        .entrySet()) {
      String name = taggedValueEntry.getKey();
      String value = taggedValueEntry.getValue();
      if (debug)
        System.out.println("\t" + name + "=" + value);
      result += delim + name + "=" + value;
      delim = ",";
    }
    if (!"".equals(result))
      result += "\n";
    return result;
  }

  @Test
  public void testTaggedValueHandling() throws Exception {
    // test File (taken from BITPlan smartGENERATOR test cases ...
    File petalFile = new File("examples/AF2006_AC001.mdl");
    PetalObject.strict = false;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    JavaGenerator gen = new JavaGenerator(petalTree, (File) null);
    gen.init();
    gen.start();
    String taggedValueTxt = "";
    for (Entry<String, List<Class>> packageEntry : gen.getClassesByPackage()
        .entrySet()) {
      if (debug)
        System.out.println("Package: '" + packageEntry.getKey() + "'");
      for (Class clazz : packageEntry.getValue()) {
        taggedValueTxt += inspectNode(clazz);
        for (Field field : clazz.getFields()) {
          taggedValueTxt += inspectNode(field);
        }
        for (Method method : clazz.getMethods()) {
          taggedValueTxt += inspectNode(method);
        }
      }
    }
    if (debug)
      System.out.println(taggedValueTxt);
    assertEquals("Template=,XMIId=Class1\n" + "XMIId=attribute1\n"
        + "XMIId=operation1\n" + "Template=,XMIId=Class2\n", taggedValueTxt);
  }

}
