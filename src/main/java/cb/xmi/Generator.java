/** Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * @since 2015-01
 */
package cb.xmi;

import cb.petal.PetalFile;

/**
 * interface for Generators
 * @author wf
 *
 */
public interface Generator {
	/**
	 * @return the parser result tree
	 */
	public PetalFile getTree();

	/**
	 * @param file
	 */
	public void setTree(PetalFile file);
	
	/**
	 * @return the dump
	 */
	public String getDumpFileName();

	/**
	 * @param dump the dump to set
	 */
	public void setDumpFileName(String dump);
}
