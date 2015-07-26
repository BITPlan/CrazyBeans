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
import java.util.LinkedHashMap;

import org.junit.Test;

import cb.generator.GeneratorVisitor;
import cb.parser.PetalParser;
import cb.petal.Attribute;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.Tag;

/**
 * test the tagged Value handling
 * @author wf
 *
 */
public class TestTaggedValues {
  
  public static class TaggedValueVisitor extends GeneratorVisitor {

    private LinkedHashMap<String, Tag> tagmap;
    /**
     * create this visitor
     * @param tree
     */
    public TaggedValueVisitor(PetalFile tree) {
      super.setTree(tree);
    }
    
    @Override
    public void init() throws Exception {
      tagmap=new LinkedHashMap<String,Tag>();
    }

    @Override
    public void start() throws Exception {
      
    }

    @Override
    public void dump() throws Exception {
      
    }
    
    public void visit(Attribute attribute) { 
      PetalNode parent = attribute.getParent();
      System.out.println(parent.getClass().getName());
      System.out.println(attribute.getTool());
      ArrayList<PetalNode> properties = attribute.getPropertyList();
      for (PetalNode property:properties) {
        System.out.println(property.getClass().getName());
      }
    }
  }
  
  @Test
  public void testTaggedValueHandling() throws Exception {
    // test File (taken from BITPlan smartGENERATOR test cases ...
    File petalFile=new File("examples/AF2006_AC001.mdl");
    PetalObject.strict = false;
    PetalFile petalTree = PetalParser.createParser(petalFile.getPath()).parse();
    TaggedValueVisitor visitor = new TaggedValueVisitor(petalTree);
    petalTree.accept(visitor);
    visitor.dump();
  }

}
