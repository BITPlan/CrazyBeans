package test;

import cb.petal.*;
import cb.parser.*;
import java.util.*;
import cb.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * Display petal file visually. Property names are displayed as tool tips.
 *
 * @version $Id: Test3.java,v 1.3 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Test3 {
  public static void main(String[] args) throws Exception {
    PetalParser parser;

    if(args.length == 0)
      parser = PetalParser.createParser(System.in);
    else
      parser = PetalParser.createParser(args[0]);

    PetalFile tree = parser.parse();
    
    JTree        jtree = new PetalTree(tree);
    final JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    panel.setLayout(new java.awt.BorderLayout());
    frame.setContentPane(panel);
    
    JScrollPane scroll = new JScrollPane();
    scroll.setPreferredSize(new java.awt.Dimension(400, 500));
    scroll.setViewportView(jtree);
    
    panel.add(scroll, java.awt.BorderLayout.CENTER);
      
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(java.awt.event.WindowEvent event){
	frame.setVisible(false);
	frame.dispose();
	System.exit(0);
      }
    });

    frame.pack();
    frame.setVisible(true);
  }
}
