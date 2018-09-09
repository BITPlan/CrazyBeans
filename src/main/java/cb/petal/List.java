/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

import java.util.*;

/**
 * Lists (list foo ...) containing other petal nodes.
 *
 * @version $Id: List.java,v 1.11 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class List implements PetalNode {
  static final long serialVersionUID = -9142706599368764080L;

  private ArrayList<PetalNode> list = new ArrayList<PetalNode>();
  private String name;

  /**
   * create a list with the given name
   * @param name
   */
  public List(String name) {
    setName(name);
  }

  public java.lang.Object clone() {
    List list = new List(name);

    list.list = (ArrayList<PetalNode>)this.list.clone();
    return list;
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof List) && ((List)o).name.equals(this.name) &&
      ((List)o).list.equals(this.list);
  }

  public void setName(String n) {
    if(n != null)
      n = n.intern();

    name = n;
  }

  public final String getName()         { return name; }
  public final String getKind()         { return "list"; }
  public final int    getChildCount()   { return list.size(); }

  public final PetalNode get(int i) {
    return (PetalNode)list.get(i);
  }
  
  public final ArrayList<PetalNode> get() {
    return this.list;
  }

  public final void set(int i, PetalNode node) {
    list.set(i, node);
  }
  
  public final void add(PetalNode value) {
    list.add(value);
  }

  public final void remove(PetalNode value) {
    list.remove(value);
  }

  public final int size() {
    return list.size();
  }

  public final java.util.List<PetalNode> getElements() {
    return (ArrayList)list.clone();
  }

  public final String toString() { 
    StringBuffer  buf  = new StringBuffer("(list " + name + "\n");

    for(Iterator i = list.iterator(); i.hasNext(); ) {
      buf.append(i.next());

      if(i.hasNext())
        buf.append("\n");
    }

    buf.append(")\n");
    
    return buf.toString();
  }
  
  public void accept(Visitor v) {
    v.visit(this);
  }
}
