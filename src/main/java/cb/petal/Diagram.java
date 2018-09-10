/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashSet;
import java.awt.Dimension;

/**
 * Super class for diagrams
 *
 * @version $Id: Diagram.java,v 1.4 2001/07/19 12:40:40 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Diagram extends NamedQuidObject {
  static final long serialVersionUID = 7903944716610637612L;

  /**
   * construct a diagram from the given parent with the given name and collection of parameters
   * @param parent
   * @param name
   * @param params
   */
  protected Diagram(PetalNode parent, String name, Collection params) {
    super(parent, name, params);
  }

  protected Diagram(String name) {
    super(name);
  }

  public String getTitle() {
    return getPropertyAsString("title");
  }

  public void setTitle(String o) {
    defineProperty("title", o);
  }

  public int getZoom() {
    return getPropertyAsInteger("zoom");
  }

  public void setZoom(int o) {
    defineProperty("zoom", o);
  }

  public int getMaxHeight() {
    return getPropertyAsInteger("max_height");
  }

  public void setMaxHeight(int o) {
    defineProperty("max_height", o);
  }

  public int getMaxWidth() {
    return getPropertyAsInteger("max_width");
  }

  public void setMaxWidth(int o) {
    defineProperty("max_width", o);
  }

  public int getOriginX() {
    return getPropertyAsInteger("origin_x");
  }

  public void setOriginX(int o) {
    defineProperty("origin_x", o);
  }

  public int getOriginY() {
    return getPropertyAsInteger("origin_y");
  }

  public void setOriginY(int o) {
    defineProperty("origin_y", o);
  }

  public PetalNodeList getItems() {
    return (PetalNodeList)getProperty("items");
  }

  public void setItems(PetalNodeList o) {
    defineProperty("items", o);
  }


  private static final int PADDING_X = 400;
  private static final int PADDING_Y = 400;
  private static final int MAX_X     = 10000;

  private int viewx = 0;
  private int viewy = PADDING_Y;

  /** @return next x coordinate for view object
   */
  protected int getX() {
    if(viewx > MAX_X) {
      viewx  = PADDING_X;
      viewy += PADDING_Y;
    } else
      viewx += PADDING_X;

    return viewx;
  }

  /** @return next y coordinate for view object
   */
  protected int getY() {
    return viewy;
  }

  /** @return index in views list
   */
  protected int addToViewsList(View view) {
    getItems().add(view);

    int index = getRoot().getNewTag();
    view.setTag(index);
    view.setParent(this);
    view.init(); // Should be empty, but who knows ...

    return index;
  }

  /** Calculate size for classview by applying some heuristics
   */
  protected Dimension getClassViewSize(ClassView view) {
    cb.petal.Class clazz   = (cb.petal.Class)view.getReferencedObject();
    int   max_columns = clazz.getNameParameter().length();
    int   rows    = 3;

    String stereo = clazz.getStereotype();

    if(stereo != null) {
      rows++;

      int columns = 4 + stereo.length();
      if(columns > max_columns)
        max_columns = columns;
    }

    for(Iterator i=clazz.getOperations().iterator(); i.hasNext(); ) {
      Operation op = (Operation)i.next();

      int columns = op.getNameParameter().length() + 2;

      stereo = op.getStereotype();
      if(stereo != null)
        columns += stereo.length() + 4;

      if(columns > max_columns)
        max_columns = columns;

      rows++;
    }

    for(Iterator i=clazz.getClassAttributes().iterator(); i.hasNext(); ) {
      ClassAttribute attr = (ClassAttribute)i.next();

      int columns = attr.getNameParameter().length() + 2 + attr.getType().length();

      stereo = attr.getStereotype();
      if(stereo != null)
        columns += stereo.length() + 4;

      if(columns > max_columns)
        max_columns = columns;

      rows++;
    }

    return new Dimension(max_columns * 30, rows * 50);
  }

  /** Adds a class view to the presentation view, sets location and tags
   * and calls init().
   */
  public void addToView(ClassView view) {
    int index = addToViewsList(view);

    view.setLocation(new Location(getX(), getY()));

    cb.petal.Class clazz = (cb.petal.Class)view.getReferencedObject();

    // This is just heuristic!
    if(!clazz.isActor()) {
      Dimension dim = getClassViewSize(view);
      view.setWidth(dim.width);
      view.setHeight(dim.height);
    }

    // Don't set location on labels ...
    ItemLabel label = view.getLabel();

    if(label != null)
      label.setParentView(new Tag(index));

    label = view.getStereotype();

    if(label != null)
      label.setParentView(new Tag(index));
  }

  protected void addRelationship(RelationshipView view, Relationship rel) {
    int    index       = addToViewsList(view);
    String clazz       = ((PetalObject)rel.getParent()).getQualifiedName();
    String super_class = ((PetalObject)rel.getReferencedObject()).getQualifiedName();
    int client         = searchView(clazz).getTag();
    int supplier       = searchView(super_class).getTag();

    view.setClient(new Tag(client));
    view.setSupplier(new Tag(supplier));

    ItemLabel label = view.getLabel();
    if(label != null) {
      label.setParent(view);
      label.setParentView(new Tag(((Tagged)view).getTag()));
    }

    SegLabel stereo = view.getStereotype();
    if(stereo != null) {
      stereo.setParent(view);
      stereo.setTag(getRoot().getNewTag());
      stereo.setParentView(new Tag(((Tagged)view).getTag()));
    }
  }

  protected abstract View searchView(String qual_name);

  protected View searchView(String qual_name, HashSet classes) {
    for(java.util.Iterator i = getItems().getElements().iterator(); i.hasNext();) {
      View view = (View)i.next();

      if(classes.contains(view.getClass())) {
	Qualified view2 = (Qualified)view;

	if(view2.getQualifiedNameParameter().equals(qual_name))
	  return view;
      }
    }

    throw new RuntimeException("No view found for " + qual_name);
  }

  /** Add a association view to the presentation view, set location and tags
   * and call init(). Automatically adds AssocAttachView, if the association has
   * an association class.
   */
  public void addToView(AssociationViewNew view) {
    int index = addToViewsList(view);

    //view.defineProperty("location", new Location(getX(), getY()));
    setupLabels(view);

    RoleView first  = view.getFirstRoleView();
    RoleView second = view.getSecondRoleView();

    first.setTag(getRoot().getNewTag());
    second.setTag(getRoot().getNewTag());

    Tag tag = new Tag(index);
    first.setParentView(tag);
    first.setClient(tag);
    second.setParentView(tag);
    second.setClient(tag);

    Association assoc = (Association)view.getReferencedObject();
    Role role1 = assoc.getFirstRole();
    Role role2 = assoc.getSecondRole();

    View clazz1 = searchView(role1.getSupplier());
    View clazz2 = searchView(role2.getSupplier());

    first.setSupplier(new Tag(clazz1.getTag()));
    second.setSupplier(new Tag(clazz2.getTag()));

    setupLabels(first);
    setupLabels(second);

    if(assoc.getAssociationClass() != null) {
      AssocAttachView view2 = cb.util.PetalObjectFactory.getInstance().createAssocAttachView();
      addToViewsList(view2);
      view2.setClient(tag);
      View clazz = searchView(assoc.getAssociationClass().getQualifiedName());
      view2.setSupplier(new Tag(clazz.getTag()));
    }
  }

  private void setupLabels(SegLabeled view) {
    ((View)view).setLocation(new Location(getX(), getY()));

    for(Iterator i = ((PetalObject)view).getProperties("label").iterator();
    	i.hasNext(); ) {
      SegLabel label = (SegLabel)i.next();

      label.setParent((PetalObject)view);
      label.setTag(getRoot().getNewTag());
      label.setParentView(new Tag(((Tagged)view).getTag()));
    }
  }
}
