/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
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
	 * initialize the generator
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * start the generator
	 * @throws Exception - if something goes wrong
	 */
	public void start() throws Exception;
	
	/**
	 * dump the result of the generator
	 * @throws Exception  - if something goes wrong
	 */
	public void dump() throws Exception;
	
	
	/**
	 * calls init, start and dump but may be overridden
	 * @throws Exception 
	 */
	public void run() throws Exception;
}
