/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.petaltools;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import cb.parser.PetalParser;
import cb.petal.PetalFile;
import cb.util.PetalTree;

/**
 * show a tree view of the given petal file
 * @author wf
 *
 */
public class PetalTreeView {
	
	private PetalParser parser;

	/**
	 * show the tree view for the given model FileName
	 * @param modelFileName
	 */
	public void showTree(String modelFileName) {
		if (modelFileName == null) {
			parser = PetalParser.createParser(System.in);
		} else {
			parser = PetalParser.createParser(modelFileName);
		}
		
		PetalFile tree = parser.parse();

		JTree jtree = new PetalTree(tree);
		final JFrame frame = new JFrame();
		frame.setTitle(tree.getModelName());
		JPanel panel = new JPanel();
		panel.setLayout(new java.awt.BorderLayout());
		frame.setContentPane(panel);

		JScrollPane scroll = new JScrollPane();
		scroll.setPreferredSize(new java.awt.Dimension(400, 500));
		scroll.setViewportView(jtree);

		panel.add(scroll, java.awt.BorderLayout.CENTER);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent event) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

}
