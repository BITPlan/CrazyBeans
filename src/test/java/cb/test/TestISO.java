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
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import cb.parser.PathMap;
import cb.parser.PetalParser;
import cb.petal.PetalFile;
import cb.petal.PetalObject;
import cb.util.Constants;

public class TestISO {

  @Test
  public void testMetaClass() {
    assertEquals("MetaClass",Constants.makeName("Metaclass"));
  }
  
  @Test
  public void testIsoExample() {
    // the example files are public but might be copyrighted 
    // so you might want to download them yourself using the getisoexample
    // script in examples/iso
    File isoExamples = new File(
        "examples//iso/www.isotc211.org/hmmg/OldFiles/Model2007-11-14/");
    // if the examples directory exists we assume it contains the models from
    // http://www.isotc211.org/hmmg/OldFiles/Model2007-11-14/
    if (isoExamples.exists()) {
      File petalFile = new File(isoExamples,"All ISO Model.mdl");
      assertTrue(petalFile.exists());
      PetalObject.strict = false;
      PathMap pathMap=new PathMap("$All_ISO",petalFile.getParent(),"$MyDoc",petalFile.getParent());
      PetalFile petalTree = PetalParser.createParser(petalFile.getPath(),pathMap).parse();
      assertEquals("All ISO Model",petalTree.getModelName());
    }
  }

}
