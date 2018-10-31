/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cb.parser.PathMap;
import cb.util.Constants.TmpMode;
import cb.util.PetalObjectFactory;

/**
 * base test class - supplies PetalObjectFactory
 * 
 * @author wf
 * @since 2015-01-11
 *
 */
public class BaseTest {
  PetalObjectFactory factory;

  /**
   * Logging may be enabled by setting debug to true
   */
  protected static java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger("cb.test");

  boolean debug = false;

  public static class Example {
    String name;
    String modelName;
    String srcRoot;
    public PathMap pathMap;
    static Map<String, Example> examples = new HashMap<String, Example>();

    /**
     * create an Example for the given Model name
     * 
     * @param name
     */
    public Example(String name, String... pathEntries) {
      init(name, name + ".mdl", name);
      pathMap = new PathMap(pathEntries);
    }

    /**
     * create an Example for the given Modelname with the given source Root
     * 
     * @param modelName
     * @param srcRoot
     */
    public void init(String name, String modelName, String srcRoot) {
      this.name = name;
      this.modelName = modelName;
      this.srcRoot = srcRoot;
      examples.put(name, this);
    }

    /**
     * return the example with the given name
     * 
     * @param name
     * @return
     */
    public static Example get(String name) {
      Example result = examples.get(name);
      return result;
    }

    /**
     * get the file path for me
     * 
     * @return the exampleModelFilePath for me
     */
    public String getFilePath() {
      String result = "examples" + File.separatorChar + modelName;
      return result;
    }

    /**
     * get a source Directory root in the given subDirectory of the standard
     * temporary directory
     * 
     * @return the path to the srcRoot
     * @throws IOException
     */
    public String getSrcRoot() throws IOException {
      cb.util.Constants.tempMode = TmpMode.env;
      String result = cb.util.Constants.getTmp() + "/" + srcRoot;
      return result;
    }

  }

  /**
   * the list of example model File Names
   */
  protected Example[] exampleModels = { new Example("Barat"),
      new Example("empty"),
      new Example("JDK-12_01", "$FRAMEWORK_PATH", "./javaframework"),
      new Example("project"), new Example("ComponentDiagram98"),
      new Example("RUP01"), new Example("uni"), new Example("Hospital98"),
      new Example("AnswerinMachine"),
      new Example("sequanceDiagram")};

  /**
   * enable debugging
   * 
   * @param debug
   */
  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  /**
   * check debugging state
   * 
   * @return the debug state
   */
  public boolean isDebug() {
    return this.debug;
  }

  /**
   * construct the BaseTest
   */
  public BaseTest() {
    factory = PetalObjectFactory.getInstance();
    // relative classpath to use (for maven in src/main/resources)
    factory.setTemplateRoot("/templates/");
  }
}
