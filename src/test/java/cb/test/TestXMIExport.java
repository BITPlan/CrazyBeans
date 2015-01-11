/** Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import org.junit.Test;

import cb.petaltools.XmiExport;

/**
 * test the XMI Export
 * 
 * @author wf
 *
 */
public class TestXMIExport extends BaseTest {

	/** 
	 * for grabbing stderr
	 */
	private String errText;
	private String outText;


	/**
	 * test the MJPegStreamer
	 * 
	 * @param args
	 *          - command line arguments
	 * @param expectedExit
	 *          - the expected exit code
	 * @param sleepTime
	 * @throws Exception
	 */
	public void testXmiExport(String args[], int expectedExit, int sleepTime)
			throws Exception {
		XmiExport.testMode = true;
		PrintStream stdout = System.out;
		PrintStream stderr = System.err;
		ByteArrayOutputStream errStream = new ByteArrayOutputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			System.setOut(new PrintStream(outStream, true));
			System.setErr(new PrintStream(errStream, true));
			XmiExport.main(args);
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
		assertEquals(expectedExit, XmiExport.exitCode);
	}

	@Test
	public void testHelp() throws Exception {
		String[] args = { "-h" };
		// debug=true;
		testXmiExport(args, 1, 0);
		assertTrue(errText.contains("github: https://github.com/BITPlan/CrazyBeans"));
		assertTrue(errText.contains("usage:"));
	}

	@Test
	public void testXMIExportExamples() throws Exception {
		for (String exampleModelFileName : exampleModelFileNames) {
			String exampleModelFilePath = super
					.getExampleFileName(exampleModelFileName);
			String[] args = { "-i", exampleModelFilePath };
			// debug=true;
			if (debug)
				LOGGER.log(Level.INFO,"XMI-Export for "+exampleModelFilePath);
			// debug=false;
			int expectedExitCode=0;
			if ("examples/JDK-12_01.mdl".equals(exampleModelFilePath)) {
				expectedExitCode=1; // FIXME - see BaseTest reason for problem
			} else if ("examples/project.mdl".equals(exampleModelFilePath)) {
				expectedExitCode=1; // RuntimeException:Can't map access qualifier: implementation
			}
			testXmiExport(args,expectedExitCode,0);
		}
	}

}
