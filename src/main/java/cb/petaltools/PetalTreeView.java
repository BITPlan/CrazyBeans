/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *

 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.petaltools;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
	private JFrame frame;
	
	/**
	 * @return the parser
	 */
	public PetalParser getParser() {
		return parser;
	}

	/**
	 * @return the frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * show the tree view for the given model FileName
	 * @param modelFileName
	 * @throws InterruptedException 
	 */
	public void showTree(String modelFileName) throws InterruptedException {
		parser=PetalParser.createParser(modelFileName);
		
		PetalFile tree = parser.parse();
		showTree(tree,null);
	}
	
	/**
	 * show the given Tree
	 * @param tree
	 * @param the Object to lock with (if any)
	 * @throws InterruptedException 
	 */
	public void showTree(PetalFile tree, Object lock) throws InterruptedException {
		JTree jtree = new PetalTree(tree);
		frame = new JFrame();
		frame.setTitle(tree.getModelName());
		JPanel panel = new JPanel();
		panel.setLayout(new java.awt.BorderLayout());
		frame.setContentPane(panel);

		JScrollPane scroll = new JScrollPane();
		scroll.setPreferredSize(new java.awt.Dimension(400, 500));
		scroll.setViewportView(jtree);

		panel.add(scroll, java.awt.BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		// shall we keep the tree visible?
		if (lock!=null) {
			dowait(lock);
		}
	}
	
	/**
	 * wait on the given lock
	 * @param lock
	 * @throws InterruptedException 
	 */
	public void dowait(final Object lock) throws InterruptedException {
		Thread t = new Thread() {
      public void run() {
          synchronized(lock) {
              while (frame.isVisible())
                  try {
                      lock.wait();
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              System.out.println("Working now");
          }
      }
  };
  t.start();

  frame.addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosing(WindowEvent arg0) {
          synchronized (lock) {
              frame.setVisible(false);
              lock.notify();
          }
      }

  });

  t.join();
}
	}
	


