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
	public String getDump() {
		return dump;
	}

	/**
	 * @param dump the dump to set
	 */
	public void setDump(String dump) {
		this.dump = dump;
	}

	/**
	 * @return the tree
	 */
	public PetalFile getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to set
	 */
	public void setTree(PetalFile tree) {
		this.tree = tree;
	}

}
