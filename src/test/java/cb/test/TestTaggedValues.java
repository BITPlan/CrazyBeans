/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1

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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import cb.generator.GeneratorVisitor;
import cb.generator.java.JavaGenerator;
import cb.parser.PetalParser;
import cb.petal.Attribute;
import cb.petal.DescendingVisitor;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.StringLiteral;
import cb.petal.Value;

/**
 * test the tagged Value handling
 * @author wf
 *
 */
public class TestTaggedValues {
  
  // public static class TaggedValueVisitor extends GeneratorVisitor {
  public static class TaggedValueVisitor extends JavaGenerator {
    protected static Logger LOGGER = Logger.getLogger("cb.test");

    /**
     * create this visitor
     * @param tree
     */
    public TaggedValueVisitor(PetalFile tree) {
      // super.setTree(tree);
      super(tree,(File)null);
    }
 
    
    public void visit(Attribute attribute) { 
      PetalNode parent = attribute.getParent();
      String className=parent.getClass().getName();
      if ("cb.petal.Class".equals(className)) {
        LOGGER.log(Level.INFO,"class "+className+" is parent");
      }
      System.out.println(className);
      System.out.println(attribute.getTool());
      System.out.println(attribute.getValue());
      ArrayList<PetalNode> properties = attribute.getPropertyList();
      for (PetalNode property:properties) {
        System.out.println(property.getClass().getName());
        if (property instanceof StringLiteral) {
          StringLiteral sl = (StringLiteral)property;
          System.out.println("\t"+sl.getValue());
        } else if (property instanceof Value) {
          Value v=(Value)property;
          System.out.println("\t"+v.getValue());
        }
        
      }
    }

 
  /*
    @Override
    public void init() throws Exception {
      // TODO Auto-generated method stub
      
    }


    @Override
    public void start() throws Exception {
      getTree().accept(this);
    }


    @Override
    public void dump() throws Exception {
      // TODO Auto-generated method stub
      
    }
 */
  }
  
  @Test
  public void testTaggedValueHandling() throws Exception {
    // test File (taken from BITPlan smartGENERATOR test cases ...
    File petalFile=new File("examples/AF2006_AC001.mdl");
    PetalObject.strict = false;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    JavaGenerator gen=new JavaGenerator(petalTree,(File)null);
    gen.init();
    gen.start();
    /*
    TaggedValueVisitor visitor = new TaggedValueVisitor(petalTree);
    visitor.init();
    visitor.start();
    */
  }

}
