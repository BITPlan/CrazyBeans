/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.generator.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import cb.generator.GeneratorVisitor;
import cb.parser.PetalParser;
import cb.petal.PetalFile;

/**
 * Convert a petal file into a set of (Java) classes. This class is mainly
 * responsible for the traversal while the factory is responsible for creation
 * objects and setting up relationships.
 *
 * @version $Id: Generator.java,v 1.8 2001/11/01 15:56:48 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class JavaGenerator extends GeneratorVisitor {

	private String suffix;
	private int warn=0;

	public int getWarn() {
		return warn;
	}

	public void setWarn(int warn) {
		this.warn = warn;
	}

	/**
	 * generate the java code
	 * 
	 * @param tree
	 * @param dump
	 *          where to dump generated files
	 */
	public JavaGenerator(PetalFile tree, File dump) {
		this(tree, dump, ".java");
	}

	/**
	 * generate the java code with the given suffix
	 * 
	 * @param tree
	 * @param dump
	 *          where to dump generated files
	 * @param suffix
	 *          e.g. java
	 */
	public JavaGenerator(PetalFile tree, File dump, String suffix) {
		if (dump != null)
			this.setDumpPath(dump.getPath());
		this.suffix = suffix;
		setTree(tree);
	}

	/**
	 * generate java from the given dump_path
	 * 
	 * @param tree
	 * @param dump_path
	 */
	public JavaGenerator(PetalFile tree, String dump_path) {
		this(tree, new File(dump_path));
	}

	@Override
	public void dump() throws IOException {
		for (Node n : factory.getObjects()) {

			if (n instanceof Class) {
				Class clazz = (Class) n;
				Package pack = clazz.getPackage();
				if (pack == null) {
					setWarn(getWarn() + 1);
					LOGGER.log(Level.WARNING,
							"package missing for class " + clazz.getQualifiedName());
				} else {
					String qname=pack.getQualifiedName();
					if (qname==null) {
						LOGGER.log(Level.SEVERE,"qualified name missing for ");
						
					}
					String path = qname
							.replace('.', File.separatorChar);
					File file = new File(new File(this.getDumpPath()), path);
					file.mkdirs();

					file = new File(file, File.separatorChar + clazz.getName() + suffix);

					FileOutputStream out = new FileOutputStream(file);
					PrintWriter stream = new PrintWriter(new OutputStreamWriter(out));

					clazz.dump(stream);
					stream.close();
					out.close();
				}
			}
		}
	}

	/**
	 * get the Classes
	 * 
	 * @return the list of classes by Packagecd /
	 */
	public Map<String, List<Class>> getClassesByPackage() {
		if (classesByPackage == null) {
			classesByPackage = new TreeMap<String, List<Class>>();
			for (@SuppressWarnings("rawtypes")
			Iterator i = factory.getObjects().iterator(); i.hasNext();) {
				Node n = (Node) i.next();
				if (debug)
					LOGGER.log(Level.INFO, n.getClass().getName());
				if (n instanceof Class) {
					Class clazz = (Class) n;
					String pack = clazz.getPackage().getQualifiedName();
					List<Class> classes;
					if (classesByPackage.containsKey(pack)) {
						classes = classesByPackage.get(pack);
					} else {
						classes = new ArrayList<Class>();
						classesByPackage.put(pack, classes);
					}
					classes.add(clazz);
				}
			}
		}
		return classesByPackage;
	}

	/**
	 * main routine to test from command line
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PetalFile tree = PetalParser.parse(args);
			String dump = cb.util.Constants.getTmp();
			JavaGenerator gen = new JavaGenerator(tree, dump);
			gen.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
