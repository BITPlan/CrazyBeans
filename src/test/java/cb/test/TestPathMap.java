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
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import cb.parser.PathMap;

/**
 * test https://github.com/BITPlan/CrazyBeans/issues/14
 * 
 * @author wf
 *
 */
public class TestPathMap extends BaseTest {

  @Test
  public void testDefaultPathMap() {
    PathMap pathMap = new PathMap();
    File currentDir = new File(".");
    File r = pathMap.resolveReference("$CURDIR", currentDir);
    assertEquals(r.getAbsolutePath(), currentDir.getAbsolutePath());
  }

  @Test
  public void testPathMapFromMap() {
    Map<String, String> pmap = new HashMap<String, String>();
    pmap.put("$PATH1", "path1");
    pmap.put("$PATH2", "./path2");
    PathMap pathMap = new PathMap(pmap);
    File currentDir = new File(".");
    File path1 = pathMap.resolveReference("$PATH1", currentDir);
    assertEquals("path1", path1.getPath());
    File path2 = pathMap.resolveReference("$PATH2", currentDir);
    assertEquals("." + File.separator + "path2", path2.getPath());
  }

  @Test
  public void testPathMapFromIni() throws Exception {
    Properties props = new Properties();
    props.put("$PATH1", "path1");
    File propFile = File.createTempFile("pathmap", ".ini");
    props.store(new FileOutputStream(propFile), "testPathMapFromIni");
    PathMap pathMap = new PathMap(propFile);
    assertFalse(pathMap.isRegistry());
    File currentDir = new File(".");
    File path1 = pathMap.resolveReference("$PATH1", currentDir);
    assertEquals("path1", path1.getPath());
  }

  @Test
  public void testPathMapFromRegistryFile() throws Exception {
    File regFile = new File("examples/pathmap.reg");
    assertTrue(regFile.exists());
    PathMap pathMap = new PathMap(regFile, Charset.forName("UTF-16"));
    assertTrue(pathMap.isRegistry());
    Map<String, String> pmap = pathMap.getPathMap();
    assertEquals(18, pmap.size());
    //debug=true;
    if (debug)
      for (Object keyObject : pmap.keySet()) {
        System.out
            .println(String.format("%s=%s", keyObject, pmap.get(keyObject)));
      }
    String frameworkPath="C:\\Program Files (x86)\\Rational\\Rose\\framework\\frameworks";
    String foundPath=pmap.get("$FRAMEWORK_PATH");
    assertEquals(frameworkPath.length(), foundPath.length());
    assertEquals(frameworkPath, foundPath);
    File currentDir = new File(".");
    File path1 = pathMap.resolveReference("$FRAMEWORK_PATH", currentDir);
    assertEquals(frameworkPath, path1.getPath());
    File path2=pathMap.resolveReference("$FRAMEWORK_SUBPATH",currentDir);
    assertEquals(frameworkPath+"/subpath",path2.getPath());
    String commentedPath=pmap.get("$COMMENTED_PATH");
    assertEquals("somepath",commentedPath);
  }
}
