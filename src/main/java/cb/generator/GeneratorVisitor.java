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

import cb.petal.DescendingVisitor;
import cb.petal.PetalFile;

/**
 * a Visitor that is used for generation
 * @author wf
 *
 */
public class GeneratorVisitor extends DescendingVisitor implements Generator {
	/**
	 * Where to dump the result of this generator
   */
  private String     dump;
  
	/**
	 * The Rose Petal file to convert
	 */
	private PetalFile tree;

	/**
	 * @return the dump
	 */
	@Override
	public String getDumpPath() {
		return dump;
	}

	/**
	 * @param dump the dump to set
	 */
	@Override
	public void setDumpPath(String dump) {
		this.dump = dump;
	}

	/**
	 * @return the tree
	 */
	@Override
	public PetalFile getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to set
	 */
	@Override
	public void setTree(PetalFile tree) {
		this.tree = tree;
	}

}
