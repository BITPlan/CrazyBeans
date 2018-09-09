/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.generator.java;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import cb.petal.ClassCategory;

/**
 * implementation of a package
 * @author wf
 *
 */
public class PackageImpl extends NodeImpl implements Package {

	String qname;
	Collection<Class> classes=new ArrayList<Class>();
	
	/**
	 * construct me from the given category
	 * @param category
	 */
	public PackageImpl(ClassCategory category) {
		super(category);
	}

	@Override
	public String getQualifiedName() {
		return qname;
	}
	
	/**
	 * set the qualified name
	 * @param qualifiedName
	 */
	public void setQualifiedName(String qualifiedName) {
		qname=qualifiedName;
	}

	@Override
	public void addClass(Class c) {
		classes.add(c);
		
	}

	@Override
	public Collection<Class> getClasses() {
		return classes;
	}

	@Override
	public void dump(PrintWriter stream) {
		// FIXME ... e.g. package.info file?
	}

	

}
