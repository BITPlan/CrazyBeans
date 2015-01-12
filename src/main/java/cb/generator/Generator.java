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
package cb.generator;

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
	 * @return the dump File path
	 */
	public String getDumpPath();

	/**
	 * @param dump the dump to set
	 */
	public void setDumpPath(String dump);
	
	/**
	 * dump the result of the generator
	 * @throws Exception  - if something goes wrong
	 */
	public void dump() throws Exception;
}
