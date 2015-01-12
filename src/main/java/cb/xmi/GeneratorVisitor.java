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
	 * The Rose Petal file to convert
	 */
	private PetalFile tree;

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
