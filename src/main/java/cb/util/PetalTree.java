package cb.util;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import cb.petal.List;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.StringLiteral;
import cb.petal.Tagged;
import cb.petal.Value;
import cb.parser.PetalParser;
import cb.test.*;

import java.util.Iterator;

/** Display petal file in JTree. The tooltip text displays the property name, if
 * the currently selected node is a child of a PetalObject.
 *
 * @version $Id: PetalTree.java,v 1.1 2001/07/06 11:09:39 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class PetalTree extends JTree {
  protected static final ImageIcon PLUS_ICON   = getIcon("PLUS.GIF");
  protected static final ImageIcon MINUS_ICON  = getIcon("MINUS.GIF");
  protected static final ImageIcon OPENED_ICON = getIcon("OPENED.GIF");
  protected static final ImageIcon CLOSED_ICON = getIcon("CLOSED.GIF");
  protected static final ImageIcon LEAF_ICON   = getIcon("LEAF.GIF");
 
  public PetalTree(PetalFile tree) {
    super(tree);
    getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    ToolTipManager.sharedInstance().registerComponent(this);
    putClientProperty("JTree.lineStyle", "Angled");
    setShowsRootHandles(false);
    setUI(new MyTreeUI());
    setCellRenderer(new MyTreeCellRenderer());
  }

  private static ImageIcon getIcon(String name) {
    try {
      return new ImageIcon(PetalTree.class.getResource("/images/" + name));
    } catch(Exception e) {
      System.err.println("Couldn't find image: " + name);
    }
    
    return new ImageIcon();
  }

  private static class MyTreeUI extends javax.swing.plaf.metal.MetalTreeUI {
    public javax.swing.Icon getExpandedIcon() {
      return MINUS_ICON;
    }
    
    public javax.swing.Icon getCollapsedIcon() {
      return PLUS_ICON;
    }
  }

  private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {
    public java.awt.Component getTreeCellRendererComponent(JTree tree,
							   java.lang.Object value, boolean sel,
							   boolean expanded, boolean leaf,
							   int row, boolean hasFocus) {

      if(value instanceof PetalFile)
	value = "Root";
      else if(value instanceof PetalObject) {
	PetalObject  obj = (PetalObject)value;
	StringBuffer buf = new StringBuffer(obj.getName());

	for(Iterator i = obj.getParameterList().iterator(); i.hasNext(); )
	  buf.append(" \"" + i.next() + "\"");

	if(obj instanceof Tagged)
	  buf.append(" " + ((Tagged)obj).getTag());
	
	value = buf;
      } else if(value instanceof List) {
	value = "list " + ((List)value).getName();
      } else if((value instanceof StringLiteral)) {
	value = '"' + (String)((StringLiteral)value).getLiteralValue() + '"';
      } else if((value instanceof Value)) {
	value = ((Value)value).getLiteralValue();
      }

      super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

      if(leaf) {
	setIcon(LEAF_ICON);
      } else {
	if(expanded)
	  setIcon(OPENED_ICON);
	else
	  setIcon(CLOSED_ICON);
      }

      return this;
    }
  }

  public String getToolTipText(java.awt.event.MouseEvent e) {
    if(e != null) {
      //int      row  = getRowForLocation(e.getX(), e.getY());
      TreePath path = getPathForLocation(e.getX(), e.getY());

      if((path != null)) {
	PetalNode node = (PetalNode)path.getLastPathComponent();

	path = path.getParentPath();

	if(path != null) {
	  PetalNode parent = (PetalNode)path.getLastPathComponent();

	  if(parent instanceof PetalObject)
	    return ((PetalObject)parent).getPropertyName(node);
	}
      }
    }	

    return null;
  }
}
