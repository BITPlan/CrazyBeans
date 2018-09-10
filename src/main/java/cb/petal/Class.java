/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cb.util.PetalObjectFactory;

/**
 * Represents Class object, there are a lot of convenience methods here for
 * adding super class(es), operations, attributes, etc.
 *
 * @version $Id: Class.java,v 1.24 2001/08/01 14:26:56 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Class extends Inheritable {
  static final long serialVersionUID = -1146331201133928529L;

  private boolean isAssociationClass;

  protected Class(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  public Class(PetalNode parent, Collection params) {
    super(parent, "Class", params);
  }

  public Class() {
    super("Class");
  }

  /**
   * Intialize this class by registering it by its quid and by its qualified
   * name.
   */
  public void init() {
    super.init();
    getRoot().registerClass(this);
  }

  private boolean compareStereotype(String s) {
    String stereo = getStereotype();

    if (stereo != null)
      return stereo.toLowerCase().equals(s);
    else
      return false;
  }

  /**
   * @return true if this class has the stereotype "interface" set.
   */
  public boolean isInterface() {
    return compareStereotype("interface");
  }

  /**
   * @return true if this class has the stereotype "actor" set.
   */
  public boolean isActor() {
    return compareStereotype("actor");
  }

  public void isAssociationClass(boolean i) {
    isAssociationClass = i;
  }

  public boolean isAssociationClass() {
    return isAssociationClass;
  }

  /**
   * @return "Class", "ClassUtility", "InstantiatedClass", etc..
   */
  public String getClassType() {
    String name = getClass().getName();
    int index = name.lastIndexOf('.');

    if (index < 0)
      throw new RuntimeException("What class is this: " + name);

    return name.substring(index + 1);
  }

  /**
   * @return list of super class objects
   */
  public List<Class> getSuperclasses() {
    return getClassList(getSuperclassList());
  }

  /**
   * Add super class of this class, i.e. adds InheritanceRelationship to
   * "superclasses" list.
   * 
   * @return implicitly created relationship object
   */
  public InheritanceRelationship addSuperClass(Class clazz) {
    return addSuperClassifier(clazz);
  }

  /**
   * Add implemented interface to this class, i.e. adds RealizeRelationship to
   * "realized_interfaces" list.
   * 
   * @return implicitly created relationship object
   */
  public RealizeRelationship addImplementedInterface(Class inter) {
    RealizeRelationship rel = PetalObjectFactory.getInstance()
        .createRealizeRelationship(this, inter);
    rel.init(); // Parent is already set

    addToList("realized_interfaces", "realize_rel_list", rel);
    return rel;
  }

  /**
   * Add used to this class, i.e. adds UsesRelationship to "used_nodes" list.
   * 
   * @return implicitly created relationship object
   */
  public UsesRelationship addUsedClass(Class inter) {
    UsesRelationship rel = PetalObjectFactory.getInstance()
        .createUsesRelationship(this, inter);

    rel.init(); // Parent is already set

    addToList("used_nodes", "uses_relationship_list", rel);
    return rel;
  }

  /**
   * @return list of used class objects (uses relationship)
   */
  public List<Class> getUsedClasses() {
    return getClassList(getUsedClassesList());
  }

  /**
   * @return list of implemented ("realized") Class objects (aka interfaces)
   */
  public List<Class> getImplementedInterfaces() {
    return getClassList(getRealizedInterfacesList());
  }

  private List<Class> getClassList(PetalNodeList list) {
    ArrayList<Class> result = new ArrayList<Class>();
    if (list == null)
      return result;
    PetalFile root = getRoot();

    for (Iterator i = list.getElements().iterator(); i.hasNext();) {
      Relationship r = (Relationship) i.next();

      Class obj = root.getClassByQuidu(r);
      result.add(obj);
    }

    return result;
  }

  /**
   * @return (first) super class of this class
   */
  public Class getSuperclass() {
    java.util.List list = getSuperclasses();

    if ((list == null) || (list.size() < 1))
      return null;
    else
      return (Class) list.get(0);
  }

  /**
   * @return list of associations related to this class if any. this requires
   *         that the Association objects have been initialized with "init()".
   * @see Association#init()
   */
  public List<Association> getAssociations() {
    return getRoot().getAssociations(this);
  }

  /**
   * @return list of InheritanceRelationship objects
   */
  public PetalNodeList getSuperclassList() {
    return (PetalNodeList) getProperty("superclasses");
  }

  public void setSuperclassList(PetalNodeList c) {
    defineProperty("superclasses", c);
  }

  /**
   * @return list of UsesRelationship objects
   */
  public PetalNodeList getUsedClassesList() {
    return (PetalNodeList) getProperty("used_nodes");
  }

  public void setUsedClassesList(PetalNodeList c) {
    defineProperty("used_nodes", c);
  }

  /**
   * @return list of RealizeRelationship objects
   */
  public PetalNodeList getRealizedInterfacesList() {
    return (PetalNodeList) getProperty("realized_interfaces");
  }

  public void setRealizedInterfacesList(PetalNodeList c) {
    defineProperty("realized_interfaces", c);
  }

  /**
   * Add an operation to this class.
   */
  public void addOperation(Operation o) {
    addToList("operations", "Operations", o);
  }

  /**
   * Add an operation to this class.
   * 
   * @return implicitly created operation object
   */
  public Operation addOperation(String name, String result, String qualifier,
      String[] param_types, String[] param_names) {
    PetalNodeList list = new PetalNodeList("Parameters");
    Operation op = PetalObjectFactory.getInstance().createOperation(name,
        result, list);
    op.setExportControl(qualifier);

    if (param_types != null) {
      for (int i = 0; i < param_types.length; i++) {
        String type = param_types[i];
        String n = param_names[i];

        Parameter p = new Parameter(op,
            java.util.Arrays.asList(new String[] { n }));
        p.setType(type);
        list.add(p);
      }
    }

    op.setParent(this);
    op.init();
    addOperation(op);

    return op;
  }

  public void removeOperation(Operation o) {
    removeFromList("operations", o);
  }

  /**
   * Add a class attribute aka field to this class.
   * 
   * @return implicitly created class attribute
   */
  public ClassAttribute addClassAttribute(String name, String type,
      String qualifier) {
    ClassAttribute attr = PetalObjectFactory.getInstance()
        .createClassAttribute(name, type);
    attr.setParent(this);
    attr.setExportControl(qualifier);
    attr.init();
    addClassAttribute(attr);
    return attr;
  }

  /**
   * Add a class attribute aka field to this class.
   */
  public ClassAttribute addClassAttribute(String name, String type) {
    return addClassAttribute(name, type, "Private");
  }

  /**
   * Add a class attribute aka field to this class.
   */
  public void addClassAttribute(ClassAttribute o) {
    addToList("class_attributes", "class_attribute_list", o);
  }

  public void removeClassAttribute(ClassAttribute o) {
    removeFromList("class_attributes", o);
  }

  /**
   * @return list of operations of this class
   */
  public java.util.List getOperations() {
    PetalNodeList list = getOperationList();

    if (list != null)
      return list.getElements();
    else
      return Collections.EMPTY_LIST;
  }

  /**
   * @return list of class attributes (aka fields) of this class
   */
  public java.util.List getClassAttributes() {
    PetalNodeList list = getClassAttributeList();

    if (list != null)
      return list.getElements();
    else
      return Collections.EMPTY_LIST;
  }

  /**
   * @return list of Operation objects
   */
  public PetalNodeList getOperationList() {
    return (PetalNodeList) getProperty("operations");
  }

  public void setOperationList(PetalNodeList c) {
    defineProperty("operations", c);
  }

  /**
   * @return list of ClassAttribute objects
   */
  public PetalNodeList getClassAttributeList() {
    return (PetalNodeList) getProperty("class_attributes");
  }

  public void setClassAttributeList(PetalNodeList c) {
    defineProperty("class_attributes", c);
  }

  public String getLanguage() {
    return getPropertyAsString("language");
  }

  public void setLanguage(String c) {
    defineProperty("language", c);
  }

  public PetalNodeList getParameters() {
    return (PetalNodeList) getProperty("parameters");
  }

  public void setParameters(PetalNodeList o) {
    defineProperty("parameters", o);
  }

  public PetalNodeList getAttributes() {
    return (PetalNodeList) getProperty("attributes");
  }

  public void setAttributes(PetalNodeList o) {
    defineProperty("attributes", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
