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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.logging.Level;

import org.junit.Test;

import cb.petaltools.PetalTool;

/**
 * test the XMI Export
 * 
 * @author wf
 *
 */
public class TestPetalTool extends BaseTest {

  /**
   * for grabbing stderr
   */
  private String errText;
  private String outText;

  /**
   * test the petal tool command line
   * 
   * @param args
   *          - command line arguments
   * @param expectedExit
   *          - the expected exit code
   * @param sleepTime
   * @throws Exception
   */
  public void testPetalTool(String args[], int expectedExit, int sleepTime)
      throws Exception {
    PetalTool.testMode = true;
    PrintStream stdout = System.out;
    PrintStream stderr = System.err;
    ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    try {
      System.setOut(new PrintStream(outStream, true));
      System.setErr(new PrintStream(errStream, true));
      PetalTool.main(args);
      Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
      // ignore
    } finally {
      System.setOut(stdout);
      System.setErr(stderr);
    }
    errText = errStream.toString();
    outText = outStream.toString();
    if (debug) {
      LOGGER.log(Level.INFO, "stderr:\n" + errText);
      LOGGER.log(Level.INFO, "stdout:\n" + outText);
    }
    assertEquals("" + Arrays.toString(args), expectedExit, PetalTool.exitCode);
  }

  @Test
  public void testHelp() throws Exception {
    String[] args = { "-h" };
    // debug=true;
    testPetalTool(args, 1, 0);
    assertTrue(errText
        .contains("github: https://github.com/BITPlan/CrazyBeans"));
    assertTrue(errText.contains("usage:"));
  }

  /**
   * test exporting the example model files to the different formats
   * 
   * @throws Exception
   */
  @Test
  public void testExportExamples() throws Exception {
    for (Example exampleModel : exampleModels) {
      String exampleModelFilePath = exampleModel.getFilePath();
      String formats[] = { "xmi", "rose","java" };
      for (String format : formats) {
        String[] args = { "--input", exampleModelFilePath, "--format", format, "--source-root", exampleModel.getSrcRoot() };
        // debug = true;
        if (debug)
          LOGGER.log(Level.INFO, format + "-Export for " + exampleModelFilePath
              + " with " + Arrays.toString(args));
        debug = false;
        int expectedExitCode = 0;
        if ("examples/project.mdl".equals(exampleModelFilePath)
            && "xmi".equals(format)) {
          expectedExitCode = 1; // RuntimeException:Can't map access qualifier:
          // implementation
        }
        if ("examples/JDK-12_01.mdl".equals(exampleModelFilePath)) {
          expectedExitCode = 1; // FIXME - see BaseTest reason for problem
        }
        testPetalTool(args, expectedExitCode, 0);
      }
    }
  }
  
}
