/**
 * Copyright (C) 2016 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.generator.java;

import java.util.Collection;

/**
 * a package
 *
 */
public interface Package extends Node {
	public String getQualifiedName();
	public void addClass(Class c);
	public Collection<Class> getClasses();
}