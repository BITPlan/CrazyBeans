/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.petaltools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import ru.novosoft.uml.xmi.IncompleteXMIException;
import cb.generator.java.JavaGenerator;
import cb.parser.PetalParser;
import cb.parser.PrintVisitor;
import cb.petal.PetalFile;
import cb.util.PiggybackVisitor;
import cb.xmi.XMIGenerator;

/**
 * Convert Rose file into different formats. You'll need to install <a
 * href="http://nsuml.sourceforge.net/">NSUML</a> to run format=xmi. 
 * This code is derived from Test4. This test also
 * shows how to reduce memory consumption by omitting certain nodes when parsing
 * the petal file.
 *
 * @version $Id: Test4.java,v 1.5 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class PetalTool {
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
		System.err.println("usage: java cb.petaltools.PetalTool");
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

	@Option(name = "-i", aliases = { "--input" }, usage = "input\nthe path to the rational rose input .mdl file - will use stdin if omitted or '-' is specified as input parameter")
	protected String input = "-";

	@Option(name = "-o", aliases = { "--output" }, usage = "output\nthe path to the output file - will use stdout if omitted or '-' is specified as output parameter")
	protected String output = "-";

	@Option(name = "-f", aliases = { "--format" }, usage = "output format \ndefault: xmi, could also be rose,java or none")
	protected String format = "xmi";
	
  @Option(name = "-src", aliases = { "--source-root" }, usage = "path to source\nthe path to the folder where the generated (java) code should be created")
	protected String srcRoot=null;

	@Option(name = "-tv", aliases = { "--treeview" }, usage = "treeView\na Java Swing based GUI to show the petal tree is started")
	protected boolean showTree = false;

	/**
	 * export the given input Rational Rose mdl file to the given output Rational
	 * Rose mdl file
	 * 
	 * @param input
	 *          - the path to the .mdl file if null then use System.in
	 * @param output
	 *          - the path to the .xmi output file if null use System.out
	 * @throws Exception 
	 */
	public void exportXmi(String input, String output) throws Exception {
		try {
			Class.forName("ru.novosoft.uml.MBase");
		} catch (ClassNotFoundException e) {
			System.err
					.println("Please install NSUML first: http://nsuml.sourceforge.net/");
			return;
		}

		PetalParser parser;
		parser = PetalParser.createParser(input);

		// Abandon all diagrams since they're not used by the generator anyway
		// This saves us a lot of memory
		parser.setIgnoredNodes(new Class[] { cb.petal.Diagram.class,
				cb.petal.View.class });

		PetalFile tree = parser.parse();
		XMIGenerator gen;
		gen = new XMIGenerator(tree, output);
		gen.run();
	}
	
	/**
	 * get the PetalFile Tree for the given input
	 * @param input - the input file name or "-" for stdin
	 */
	public PetalFile getTree(String input) {
	  PetalParser parser;
    parser = PetalParser.createParser(input);
    PetalFile tree = parser.parse();
    return tree;
	}
	
	/**
	 * export to rational rose petal file
	 * @param input - the petal file to read
	 * @param output - the petal file to create
	 * @throws FileNotFoundException
	 */
	private void exportRose(String input, String output) throws FileNotFoundException {
	  PetalFile tree=getTree(input);
    PrintVisitor printVisitor;
    if ("-".equals(output)) {
      printVisitor=new PrintVisitor(System.out);
    } else {
      PrintStream printStream = new PrintStream(
          new FileOutputStream(output));
      printVisitor=new PrintVisitor(printStream);
    }
    printVisitor.visit(tree);  
  }
	
	/**
	 * export the given input to Java
	 * @param input
	 * @param output
	 * @param srcRoot
	 * @throws IOException 
	 */
	private void exportJava(String input, String output, String srcRoot) throws IOException {
	  PetalFile tree=getTree(input);
	  JavaGenerator gen = new JavaGenerator(tree, srcRoot);
    tree.accept(new PiggybackVisitor(gen));
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
				if (this.input == null) {
					usage("no parameters specified");
				} else {
					if (this.showTree) {
						PetalTreeView petalTreeView = new PetalTreeView();
						petalTreeView.showTree(this.input);
					}
					format=this.format.toLowerCase().trim();
					if ("none".equals(format)) {
					  // just ignore this format
	          exitCode = 0;
					}	if ("xmi".equals(format)) {
						this.exportXmi(input, output);
	          exitCode = 0;
					} else if ("rose".equals(this.format.toLowerCase())) {
					  this.exportRose(input,output);
	          exitCode = 0;
					} else if ("java".equals(this.format.toLowerCase())) {
					  if (srcRoot==null) {
					    usage("srcRoot may not be empty");
					  } else {
					    this.exportJava(input,output,srcRoot);
					  }
					}
				}
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
		PetalTool xmiexport = new PetalTool();
		int result = xmiexport.maininstance(args);
		if (!testMode && result != 0)
			System.exit(result);
	}

}
