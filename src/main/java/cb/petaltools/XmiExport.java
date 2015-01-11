/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
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
package cb.petaltools;

import java.io.IOException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import ru.novosoft.uml.xmi.IncompleteXMIException;
import cb.parser.PetalParser;
import cb.petal.PetalFile;
import cb.xmi.XMIGenerator;

/**
 * Convert Rose file into XMI format. You'll need to install <a
 * href="http://nsuml.sourceforge.net/">NSUML</a> to run this example. It also
 * shows how to reduce memory consumption by omitting certain nodes when parsing
 * the petal file.
 *
 * @version $Id: Test4.java,v 1.5 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class XmiExport {
	public static final String VERSION = "0.0.1";
	public static boolean testMode = false;

	/**
	 * handle the given Throwable (in commandline mode)
	 * 
	 * @param t
	 */
	public void handle(Throwable t) {
		System.out.flush();
		System.err.println(t.getClass().getSimpleName() + ":" + t.getMessage());
		if (debug)
			t.printStackTrace();
	}

	/**
	 * show the Version
	 */
	public static void showVersion() {
		System.err.println("cb.petal Version: " + VERSION);
		System.err.println();
		System.err.println(" github: https://github.com/BITPlan/CrazyBeans");
		System.err.println("");
	}

	/**
	 * show a usage
	 */
	public void usage(String msg) {
		System.err.println(msg);

		showVersion();
		System.err.println("usage: java cb.petaltools.XmiExport");
		parser.printUsage(System.err);
		exitCode = 1;
	}

	/**
	 * show Help
	 */
	public void showHelp() {
		String msg = "Help\n";
		usage(msg);
	}

	private CmdLineParser parser;
	public static int exitCode;
	/**
	 * set to true for debugging
	 */
	@Option(name = "-d", aliases = { "--debug" }, usage = "debug\nadds debugging output")
	protected boolean debug = false;

	@Option(name = "-h", aliases = { "--help" }, usage = "help\nshow this usage")
	boolean showHelp = false;

	@Option(name = "-v", aliases = { "--version" }, usage = "showVersion\nshow current version if this switch is used")
	boolean showVersion = false;
	
	@Option(name = "-i", aliases = { "--input" }, usage = "input\nthe path to the rational rose input .mdl file - will use stdin if omitted")
	protected String input = null;
	
	@Option(name = "-o", aliases = { "--output" }, usage = "output\nthe path to the xmi output .xmi file - will use stdout if omitted")
	protected String output = null;
	
	@Option(name = "-ne", aliases = { "--noexport" }, usage = "no export\nthe xmi export is supressed")
	protected boolean noExport=false;
	
	@Option(name = "-tv", aliases = { "--treeview" }, usage = "treeView\na Java Swing based GUI to show the petal tree is started")
	protected boolean showTree=false;

	/**
	 * export the given input Rational Rose mdl file to the given output Rational Rose mdl file
	 * @param input - the path to the .mdl file if null then use System.in 
	 * @param output - the path to the .xmi output file if null use System.out
	 * @throws IOException
	 * @throws IncompleteXMIException
	 */
	public void xmiexport(String input, String output) throws IOException, IncompleteXMIException {
		try {
			Class.forName("ru.novosoft.uml.MBase");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Please install NSUML first: http://nsuml.sourceforge.net/");
			return;
		}

		PetalParser parser;
		if (input != null) {
			parser = PetalParser.createParser(input);
		} else {
			parser = PetalParser.createParser(System.in);
		}

		// Abandon all diagrams since they're not used by the generator anyway
		// This saves us a lot of memory
		parser.setIgnoredNodes(new Class[] { cb.petal.Diagram.class,
				cb.petal.View.class });

		PetalFile tree = parser.parse();
		XMIGenerator gen;
		gen = new XMIGenerator(tree, output);

		gen.start();
		gen.dump();
	}

	/**
	 * main instance - this is the non-static version of main - it will run as a
	 * static main would but return it's exitCode to the static main the static
	 * main will then decide whether to do a System.exit(exitCode) or not.
	 * 
	 * @param args
	 *          - command line arguments
	 * @return - the exit Code to be used by the static main program
	 */
	protected int maininstance(String[] args) {
		parser = new CmdLineParser(this);
		try {
			parser.parseArgument(args);
			if (debug)
				showVersion();
			if (this.showVersion) {
				showVersion();
			} else if (this.showHelp) {
				showHelp();
			} else {
				if (this.showTree) {
					PetalTreeView petalTreeView=new PetalTreeView();
					petalTreeView.showTree(this.input);
				}
				if (!this.noExport) {
					this.xmiexport(input, output);
				}
				exitCode = 0;
			}
		} catch (CmdLineException e) {
			// handling of wrong arguments
			usage(e.getMessage());
		} catch (Exception e) {
			handle(e);
			exitCode = 1;
		}
		return exitCode;
	}

	/**
	 * XMIExport from the command line
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		XmiExport xmiexport = new XmiExport();
		int result = xmiexport.maininstance(args);
		if (!testMode && result != 0)
			System.exit(result);
	}

}
