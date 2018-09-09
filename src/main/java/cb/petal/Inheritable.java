/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

/**
 * Denote that a petal object may be extended, i.e. classes and use cases.
 *
 * @version $Id: Inheritable.java,v 1.2 2001/07/30 15:50:33 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Inheritable extends AccessObject {
  protected Inheritable(PetalNode parent, String name, java.util.Collection params) {
    super(parent, name, params);
  }

  protected Inheritable(String name) {
    super(name);
  }

  /** @return list of InheritanceRelationship objects
   */
  public PetalNodeList getSuperclassList() {
    return (PetalNodeList)getProperty("superclasses");
  }

  /** Set list of InheritanceRelationship objects
   */
  public void setSuperclassList(PetalNodeList c) {
    defineProperty("superclasses", c);
  }

  /** Add super classifier of this use case/class, i.e. add InheritanceRelationship to
   * "superclasses" list.
   * @return implicitly created relationship object
   */
  public InheritanceRelationship addSuperClassifier(Inheritable clazz) {
    InheritanceRelationship rel =
      cb.util.PetalObjectFactory.getInstance().createInheritanceRelationship(this, clazz);

    rel.init(); // Parent is already set

    addToList("superclasses", "inheritance_relationship_list", rel);
    return rel;
  }
}
