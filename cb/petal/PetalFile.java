package cb.petal;

import java.util.*;
import javax.swing.tree.*;
import javax.swing.event.*;

/**
 * Top level node (aka. model) containing two children: Petal and Design. To be
 * used in GUI applications it also may be used as the tree model for a JTree.
 *
 * @version $Id: PetalFile.java,v 1.15 2001/11/01 15:56:49 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class PetalFile implements PetalNode, TreeModel {
  private Petal  petal;
  private Design design;
  private String model_name = "anonymous";

  static final long serialVersionUID = 5388875684404513535L;

  public PetalFile(Petal petal, Design design) {
    setPetal(petal);
    setDesign(design);
  }

  public PetalFile() { }

  public boolean equals(java.lang.Object o) {
    return (o instanceof PetalFile) && (((PetalFile)o).petal.equals(this.petal)) &&
      (((PetalFile)o).design.equals(this.design));
  }

  public java.lang.Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      return null;
    }
  }

  public void   setPetal(Petal p)         { petal = p;  }
  public Petal  getPetal()                { return petal; }
  public void   setDesign(Design p)       { design = p;  }
  public Design getDesign()               { return design; }
  public String getModelName()            { return model_name; }
  public void   setModelName(String  v)   { model_name = v; }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public String getKind() { return "root node"; }

  public String toString() {
    return petal + cb.util.Constants.getNewLine() + design;
  }

  private static Random random = new Random();

  /** @return unique quid - as with Rational Rose this is simply the current
   * time expressed as the number of seconds elapsed since January 1, 1970 +
   * some random number.
   * If the resulting string is shorter than 12 characters it is filled up
   * with some random characters.
   */
  public static String getQuid() {
    String s = Long.toHexString(new Date().getTime() +
    	Math.abs(random.nextLong())).toUpperCase();
    int diff = 12 - s.length();

    for(int i=0; i < diff; i++) {
      int rand = Math.abs(random.nextInt()) % 16;

      s = s + Integer.toHexString(rand).toUpperCase();
    }

    return s;
  }

  /** Register the quids for objects so they can be mapped back to
   * objects.
   */
  private HashMap quids = new HashMap(); // Map<String,PetalObject>

  /** Register class by its quid property.
   */
  public final void registerQuidObject(String quid, QuidObject obj) {
    quids.put(quid, obj);
  }

  /** Register class by its "quid" property.
   */
  public final void registerQuidObject(QuidObject obj) {
    registerQuidObject(QuidObject.getQuid(obj), obj);
  }

  /** @return object by its "quid" (if registered via init())
   */
  public final QuidObject getQuidObject(String quid) {
    return (QuidObject)quids.get(quid);
  }

  /** @return class object by its "quid" property (if registered via init())
   */
  public final Class getClassByQuid(String quid) {
    return (Class)getQuidObject(quid);
  }

  /** @return class object by the quidu reference, i.e. a HasQuidu contains
   * a reference to a class denoted by the "quidu" property
   */
  public final Class getClassByQuidu(HasQuidu obj) {
    return (Class)this.getReferencedObject(obj);
  }

  public final QuidObject getReferencedObject(HasQuidu obj) {
    return getQuidObject(obj.getQuidu());
  }

  private HashMap classes = new HashMap();

  /** Register class by its fully qualified name
   */
  public final void registerClass(Class clazz) {
    classes.put(clazz.getQualifiedName(), clazz);
  }

  /** @return class by its fully qualified name
   */
  public final Class getClassByQualifiedName(String qual) {
    return (Class)classes.get(qual);
  }

  private HashMap assocs = new HashMap(); // Map<String,List<Association>>

  /** Register the association internally, i.e. associate it with the
   * given classes. So the classes can look up the associations
   * related to them.
   */
  public final void registerAssociation(Association a) {
    for(Iterator i = a.getRoles().getElements().iterator(); i.hasNext(); ) {
      Role   role = (Role)i.next();
      String quid = role.getQuidu();

      ArrayList list = (java.util.ArrayList)assocs.get(quid);

      if(list == null)
	assocs.put(quid, list = new ArrayList());

      list.add(a);
    }
  }

  /** If the association has registered itself properly (done in
   * Association.init() by default) one can look up what associations
   * a class has.
   */
  public final java.util.List getAssociations(cb.petal.Class clazz) {
    return (java.util.List)assocs.get(clazz.getQuid());
  }

  /** @return top level LogicalCategory
   */
  public LogicalCategory getLogicalCategory() {
    return (LogicalCategory)design.getProperty("root_category");
  }

  /** @return top level UseCaseCategory
   */
  public UseCaseCategory getUseCaseCategory() {
    return (UseCaseCategory)design.getProperty("root_usecase_package");
  }

  private int tag_counter = 1;

  /** Tags are numbered consecutively within a model file. So we just
   * use in internal counter.
   */
  public final int getNewTag() {
    return tag_counter++;
  }

  /**************************** TreeModel methods **********************/

  private ArrayList treeModelListeners = new ArrayList();

  /**
   * @return the root of the tree which is just "this" object.
   */
  public java.lang.Object getRoot() {
    return this;
  }

  /**
   * Adds a listener for the TreeModelEvent posted after the tree changes.
   */
  public void addTreeModelListener(TreeModelListener l) {
    treeModelListeners.add(l);
  }

  /**
   * Removes a listener previously added with addTreeModelListener().
   */
  public void removeTreeModelListener(TreeModelListener l) {
    treeModelListeners.remove(l);
  }

  /**
   * @return true if (petal) node is a leaf.
   */
  public boolean isLeaf(java.lang.Object node) {
    return (node instanceof Literal);
  }

  /**
   * Returns the number of children of parent.
   */
  public int getChildCount(java.lang.Object node) {
    PetalNode p = (PetalNode)node;
    return p.getChildCount();
  }

  /**
   * Returns the child of parent at index index in the parent's child array.
   */
  public java.lang.Object getChild(java.lang.Object node, int index) {
    if(node instanceof PetalFile) {
      PetalFile p = (PetalFile)node;
      switch(index) {
      case 0: return petal;
      case 1: return design;
      default: throw new RuntimeException("Illegal index for PetalFile: " + index);
      }
    } else if(node instanceof PetalObject) {
      PetalObject p = (PetalObject)node;

      return p.getProperty(index);
    } else if(node instanceof List) {
      return ((List)node).get(index);
    } else
      return null;
  }

  public int getIndexOfChild(java.lang.Object node, java.lang.Object child) {
    if(node instanceof PetalFile) {
      if(child == petal)
	return 0;
      else if(child == design)
	return 1;
      else
	throw new RuntimeException("Not a child of PetalFile: " + child);
    } else if(node instanceof PetalObject) {
      PetalObject p = (PetalObject)node;
      return p.indexOf((PetalNode)child);
    } else
      return 0;
  }

  public int getChildCount() {
    return 2;
  }

  /**
   * Messaged when the user has altered the value for the item
   * identified by path to newValue. TODO
   */
  public void valueForPathChanged(TreePath path, java.lang.Object newValue) {
    System.out.println("*** valueForPathChanged : "
		       + path + " --> " + newValue);
  }
}
