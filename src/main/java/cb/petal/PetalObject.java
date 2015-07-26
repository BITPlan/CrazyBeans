/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.petal;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Super class for all petal objects which have a list of
 * properties. Unfortunately, property names may occur multiply. This can
 * happen, e.g., if there are multiple notes attached to a class. Thus it
 * is not implemented with a HashMap as one might think.
 *
 * @version $Id: PetalObject.java,v 1.29 2005/06/06 11:50:17 moroff Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class PetalObject implements PetalNode {
  protected static Logger LOGGER = Logger.getLogger("cb.petal");
  static final long serialVersionUID = 7215267546012147332L;

  public static final ArrayList EMPTY = new ArrayList() {
    static final long serialVersionUID = -3029611157463610527L;

    public int size() {
      return 0;
    }

    public boolean contains(java.lang.Object o) {
      return false;
    }

    public java.lang.Object get(int i) {
      throw new IndexOutOfBoundsException("Index: " + i);
    }
  };

  private ArrayList<String> names = new ArrayList<String>();
  private ArrayList<PetalNode> values = new ArrayList<PetalNode>();
  private String name;
  protected ArrayList params = EMPTY;
  private PetalNode parent;

  /**
   * @param parent
   *          node in the petal tree, either another PetalObject or PetalFile
   * @param name
   *          of the object, e.g., "ClassCategory"
   * @param params
   *          list of parameters, e.g., "Class" "Logical View::templates::Class"
   */
  protected PetalObject(PetalNode parent, String name, ArrayList params) {
    setParent(parent);
    setName(name);
    setParameterList(params);
  }

  /**
   * @param parent
   *          node in the petal tree, either another PetalObject or PetalFile
   * @param name
   *          of the object, e.g., "ClassCategory"
   * @param params
   *          list of parameters, e.g., "Class" "Logical View::templates::Class"
   */
  protected PetalObject(PetalNode parent, String name, Collection params) {
    this(parent, name, new ArrayList(params));
  }

  /**
   * @param name
   *          of the object, e.g., "ClassCategory"
   */
  protected PetalObject(String name) {
    setName(name);
  }

  /**
   * @return shallow copy of object, do not forget to assign a new quid
   *         if you want to use it within the same model.
   */
  @SuppressWarnings("unchecked")
  public java.lang.Object clone() {
    PetalObject obj = null;

    try {
      obj = (PetalObject) super.clone();
    } catch (CloneNotSupportedException e) {
      return null;
    }

    obj.names = (ArrayList<String>) names.clone();
    obj.values = (ArrayList<PetalNode>) values.clone();
    obj.params = (params == EMPTY) ? EMPTY : (ArrayList) params.clone();

    return obj;
  }

  /**
   * @return true if the name and all properties are equal without regarding the
   *         order of the properties.
   */
  public boolean equals(Object o) {
    if ((o != null) && (o.getClass() == this.getClass())) {
      PetalObject obj = (PetalObject) o;

      if (!this.name.equals(obj.name))
        return false;

      if (this.values.size() != obj.values.size())
        return false;

      TreeSet<String> n1 = new TreeSet<String>(this.names);
      TreeSet<String> n2 = new TreeSet<String>(obj.names);

      if (!n1.equals(n2))
        return false;
      Iterator<PetalNode> j = values.iterator();
      for (Iterator<String> i = names.iterator(); i.hasNext();) {
        String name =i.next();
        PetalNode value1 = (PetalNode) j.next();
        PetalNode value2 = obj.getProperty(name);

        if (!value1.equals(value2))
          return false;
      }

      return true;
    } else
      return false;
  }

  /**
   * Perform any initial actions after all properties have been set up.
   * Called by PetalParser when all properties have been defined or when
   * a new object created by the user is added to the model.
   */
  public void init() {
  }

  public void setParent(PetalNode p) {
    parent = p;
  }

  public PetalNode getParent() {
    return parent;
  }

  public String getKind() {
    return "object";
  }

  public void setName(String n) {
    name = n.intern();
  }

  public String getName() {
    return name;
  }

  /**
   * @return top-level root node of this model
   */
  public final PetalFile getRoot() {
    PetalNode parent = this.parent;

    while (!(parent instanceof PetalFile))
      parent = ((PetalObject) parent).parent;

    return (PetalFile) parent;
  }

  /**
   * Override property at i, value's "parent" reference points to
   * this object afterwards
   */
  public final void setProperty(int i, String name, PetalNode value) {
    if (value == null)
      throw new RuntimeException("Value for " + name + " must not be null");

    names.set(i, name.intern()); // Use intern() to save lots of memory
    values.set(i, value);
  }

  /**
   * Add a property (which may already exist, Petal files allow to define
   * properties multiply).
   *
   * @return index of property
   */
  public final int addProperty(String name, PetalNode value) {
    if (value == null)
      throw new RuntimeException("Value for " + name + " must not be null");

    names.add(name.intern()); // Use intern() to save lots of memory
    values.add(value);

    return names.size() - 1;
  }

  /**
   * This method is strict in that it does not use equals() to search
   * the list of properties but ==, since values, in particular
   * literals may occur more than once.
   */
  public final int indexOf(PetalNode value) {
    int j = 0;
    for (Iterator<PetalNode> i = values.iterator(); i.hasNext(); j++)
      if (i.next() == value)
        return j;

    return -1;
  }

  /**
   * This method uses the strict indexOf method to find the value.
   */
  public final String getPropertyName(PetalNode value) {
    int i = indexOf(value);

    if (i >= 0)
      return (String) names.get(i);
    else
      return null;
  }

  /**
   * @return number of properties
   */
  public int getNoProperties() {
    return names.size();
  }

  /**
   * @return number of properties
   */
  public int getChildCount() {
    return names.size();
  }

  /**
   * Override property if exists already or add it if not.
   *
   * @return index of property
   */
  public final int defineProperty(String name, PetalNode value) {
    int index = names.indexOf(name);

    if (index >= 0) {
      setProperty(index, name, value);
      return index;
    } else
      return addProperty(name, value);
  }

  /**
   * @return property at given index
   */
  public final PetalNode getProperty(int i) {
    return (PetalNode) values.get(i);
  }

  /**
   * @return first occurrence of property name
   */
  public final PetalNode getProperty(String name) {
    int index = names.indexOf(name);

    if (index >= 0)
      return (PetalNode) values.get(index);
    else
      return null;
  }

  /**
   * Override property if exists already or add it if not.
   * @param name
   * @param value
   */
  public final void defineProperty(String name, String value) {
    ArrayList<String> list = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(value, "\r\n");

    while (st.hasMoreTokens())
      list.add(st.nextToken());

    StringLiteral lit = new StringLiteral(list);
    lit.setMulti(list.size() > 1);

    defineProperty(name, lit);
  }

  /**
   * @return given property, or null if it doesn't exist.
   */
  public String getPropertyAsString(String name) {
    PetalNode node = getProperty(name);
    if (node instanceof StringLiteral) {
      StringLiteral literal = (StringLiteral) node;

      return literal.getValue();
    } else if (node instanceof IntegerLiteral) {
      IntegerLiteral literal = (IntegerLiteral) node;

      return Integer.toString(literal.getValue());
    } else if (node instanceof BooleanLiteral) {
      BooleanLiteral literal = (BooleanLiteral) node;

      return Boolean.toString(literal.getValue());
    } else if (node instanceof Value) {
      Value value = (Value) node;

      return value.getStringValue();
    } else if (node instanceof Tuple) {
      Tuple tuple = (Tuple) node;

      java.lang.Object literal = tuple.getLiteralValue();
      java.lang.Class literalClass = literal.getClass();
      String literalKind = tuple.getName();
      int literalValue = tuple.getValue();
      Design design = getDesign();
      Properties properties = (Properties) design.getProperty("properties");
      List attributes = (List) properties.getProperty("attributes");
      PetalObject parent = (PetalObject) getParent();
      String parentKind = parent.getName();
      for (Iterator iter = attributes.getElements().iterator(); iter.hasNext();) {
        Attribute attribute = (Attribute) iter.next();
        String attrName = attribute.getPropertyAsString("name");

        if (attrName.endsWith(parentKind)) {
          List list = (List) attribute.getValue();

          for (Iterator iterator = list.getElements().iterator(); iterator
              .hasNext();) {
            Attribute attribute2 = (Attribute) iterator.next();

            if (attribute2.getPropertyAsString("name").equals(literalKind)) {
              List list2 = (List) attribute2.getProperty("value");

              for (Iterator iterator2 = list2.getElements().iterator(); iterator2
                  .hasNext();) {
                Attribute attribute3 = (Attribute) iterator2.next();

                if (attribute3.getPropertyAsInteger("value") == literalValue) {
                  return attribute3.getPropertyAsString("name");
                }
              }
            }
          }
        }
      }
      if (literal instanceof PetalObject) {
        PetalObject petalObject = (PetalObject) literal;

        return petalObject.getPropertyAsString(name);
      }
    }

    if (node == null) {
      LOGGER.log(Level.FINER,"No such property: " + name + " for " + this.name);
      return null;
    }

    return null;
  }

  Design getDesign() {
    Design design = null;
    PetalObject parent = (PetalObject) getParent();

    while (parent != null && design == null) {
      if (parent instanceof Design)
        design = (Design) parent;
      else
        parent = (PetalObject) parent.getParent();
    }
    return design;
  }

  public static boolean strict = true;

  /**
   * Strict variant: If the property does not exist, an exception is thrown
   * 
   * @param obj
   * @param prop
   * @return
   */
  static String getPropertyAsString(PetalObject obj, String prop) {
    StringLiteral s = (StringLiteral) obj.getProperty(prop);

    if (s == null) {
      if (strict)
        throw new RuntimeException("No property named " + prop + " for " + obj);
      else
        s = new StringLiteral("?");
    }

    return s.getValue();
  }

  /**
   * @return given property, or Integer.MIN_VALUE if it doesn't exist.
   */
  public int getPropertyAsInteger(String name) {
    IntegerLiteral s = (IntegerLiteral) getProperty(name);

    if (s == null) {
      // System.err.println("No such property: " + name + " for " + this);
      return Integer.MIN_VALUE;
    }

    return s.getValue();
  }

  /**
   * Override property if exists already or add it if not.
   */
  public void defineProperty(String name, int value) {
    defineProperty(name, new IntegerLiteral(value));
  }

  /**
   * @return given property, or false if it doesn't exist.
   */
  public boolean getPropertyAsBoolean(String name) {
    BooleanLiteral s = (BooleanLiteral) getProperty(name);

    if (s == null) {
      // System.err.println("No such property: " + name + " for " + this);
      return false;
    }

    return s.getValue();
  }

  /**
   * Override property if exists already or add it if not.
   */
  public void defineProperty(String name, boolean value) {
    defineProperty(name, new BooleanLiteral(value));
  }

  /**
   * @return given property, or Double.MIN_VALUE if it doesn't exist.
   */
  public double getPropertyAsFloat(String name) {
    FloatLiteral s = (FloatLiteral) getProperty(name);

    if (s == null) {
      // System.err.println("No such property: " + name + " for " + this);
      return Double.MIN_VALUE;
    }

    return s.getValue();
  }

  /**
   * Override property if exists already or add it if not.
   */
  public void defineProperty(String name, double value) {
    defineProperty(name, new FloatLiteral(value));
  }

  /**
   * Remove property with given name
   */
  public void removeProperty(String name) {
    removeProperty(names.indexOf(name));
  }

  /**
   * Remove property at given index
   */
  public void removeProperty(int index) {
    if (index >= 0) {
      names.remove(index);
      values.remove(index);
    }
  }

  /**
   * Move property within list of properties, i.e. change order.
   */
  public void moveProperty(int from, int to) {
    if (from == to)
      return;

    String name = (String) names.get(from);
    PetalNode value = (PetalNode) values.get(from);

    if (from < to)
      to--;

    removeProperty(from);
    names.add(to, name);
    values.add(to, value);
  }

  /**
   * @return the longest name in names list, needed for indentation issues
   */
  public String getLongestName() {
    String max = "";

    for (Iterator i = names.iterator(); i.hasNext();) {
      String s = (String) i.next();

      if (s.length() > max.length())
        max = s;
    }

    return max;
  }

  /**
   * get the properties with the given name
   * @param name
   * @return all properties with key "name"
   */
  public ArrayList<PetalNode> getProperties(String name) {
    ArrayList<PetalNode> list = new ArrayList<PetalNode>();
    Iterator<PetalNode> j = values.iterator();
    for (Iterator<String> i = names.iterator(); i.hasNext();) {
      String s =  i.next();
      PetalNode o = j.next();

      if (s.equals(name))
        list.add(o);
    }

    return list;
  }

  /**
   * @return all property names
   */
  public ArrayList<String> getNames() {
    return (ArrayList<String>) names.clone();
  }

  /**
   * @return all property values
   */
  public ArrayList<PetalNode> getPropertyList() {
    return  (ArrayList<PetalNode>) values.clone();
  }

  /**
   * @return array of properties, where obj[i][0] == name and obj[i][1] ==
   *         property
   */
  public java.lang.Object[][] getPropertyTuples() {
    java.lang.Object[][] props = new java.lang.Object[names.size()][];

    int k = 0;
    for (Iterator i = names.iterator(), j = values.iterator(); i.hasNext(); k++)
      props[k] = new java.lang.Object[] { i.next(), j.next() };

    return props;
  }

  public ArrayList getParameterList() {
    return params;
  }

  public void setParameterList(ArrayList params) {
    this.params = params;
  }

  /**
   * Add object to some given list and create the list if necessary.
   */
  protected void addToList(String prop_name, String list_name, PetalObject o) {
    List list = (List) getProperty(prop_name);

    if (list == null) {
      list = new List(list_name);
      defineProperty(prop_name, list);
    }

    list.add(o);
  }

  /**
   * Remove object from some given list.
   */
  protected void removeFromList(String prop_name, PetalObject o) {
    List list = (List) getProperty(prop_name);

    if (list != null)
      list.remove(o);
  }

  /**
   * Get fully qualified name for an object that must implement the Named
   * interface
   * and is contained by further Named objects.
   * Typical nesting of an object is (Design .. (ClassCategory ... (Class ...)))
   *
   * @see Named
   * @see Class
   * @see UseCase
   * @return String like "Logical View::University::Professor"
   */
  public String getQualifiedName() {
    PetalNode n = this.getParent();
    String result = ((Named) this).getNameParameter();

    while (!(n instanceof Design) /* && (n instanceof Named) */) {
      result = ((Named) n).getNameParameter() + "::" + result;
      n = ((PetalObject) n).getParent();
    }

    return result;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer("(object " + name);

    for (Iterator i = params.iterator(); i.hasNext();)
      buf.append(" \"" + i.next() + "\"");

    buf.append("\n");

    for (Iterator i = names.iterator(), j = values.iterator(); i.hasNext();) {
      buf.append(i.next() + "\t" + j.next());

      if (i.hasNext())
        buf.append("\n");
    }

    buf.append(")\n");

    return buf.toString();
  }

  public abstract void accept(Visitor v);
}
