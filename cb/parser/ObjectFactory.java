package cb.parser;

import cb.petal.*;
import cb.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

/**
 * This factory is used by the parser to create PetalNode objects . Subclass it and
 * overwrite the "instance" if you don't like the way this is done.
 *
 * @version $Id: ObjectFactory.java,v 1.16 2001/07/09 07:48:52 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ObjectFactory {
  private static ObjectFactory instance = new ObjectFactory();

  public static ObjectFactory getInstance() {
    return instance;
  }

  public static void setInstance(ObjectFactory o) {
    instance = o;
  }

  /*private*/ protected ObjectFactory() {}

  /** Create PetalObject like (object Class "Professor" ...). In case there is no
   * corresponding class (yet), return SimpleObject, or SimpleViewObject respectively.
   *
   * @param parent the parent of the object in the tree, either another PetalObject or the
   * root node (PetalFile)
   * @param id the Rose identifier for the node like "Class_Category" which is mapped
   * to a name like "ClassCategory"
   * @param params the parameter list for the object
   * @param tag some objects have a "tag", like @12
   */
  public PetalObject createObject(PetalNode parent, String id,
				  ArrayList params, String tag) {
    int l = (tag != null)? Integer.parseInt(tag.substring(1)) : -1;

    ArrayList params2 = new ArrayList();

    for(Iterator i = params.iterator(); i.hasNext(); )
      params2.add(unstringify((String)i.next()));

    try {
      PetalObject obj = (PetalObject)java.lang.Class.forName
	("cb.petal." + Constants.makeName(id, params2, parent)).newInstance();
      obj.setParent(parent);
      
      if(!params2.isEmpty())
	obj.setParameterList(params2);

      if(obj instanceof Tagged)
	((Tagged)obj).setTag(l);
      else if(l > 0)
	throw new RuntimeException("Not instance of Tagged but has tag");
      return obj;
    } catch(Exception e) { // Failed
      System.err.println("Not found: " + id + e);
    }

    // Don't know this object
    if(l > 0)
      return new SimpleViewObject(parent, id, params2, l);
    else
      return new SimpleObject(parent, id, params2);
  }

  static void assert(boolean assertion, String mesg) {
    if(!assertion)
      throw new RuntimeException("Assertion failed: " + mesg);
  }

  /** @return list like (list RoleViews ...) with given name
   */
  public List createList(String name) {
    return new List(name);
  }

  /***************************** Literals **********************************/

  /** @return value literal like (value cardinality "1..n")
   */
  public Value createValue(String name, StringLiteral value) {
    return new Value(name, value);
  }

  /** @return tuple literal like ("DataBaseSet" 800)
   */
  public Tuple createTuple(String first, String second) {
    return new Tuple(unstringify(first), Integer.parseInt(second));
  }

  /** @return location literal which is a tuple of integers like (1520, 96)
   */
  public Location createLocation(String value1, String value2) {
    return new Location(Integer.parseInt(value1), Integer.parseInt(value2));
  }

  /**
   * There are two kinds of string encodings in Rose: Either the usual "foo bar", or
   * a multi line string where each line starts with  a |. 
   * @return string literal which may contain multiple lines
   */
  public StringLiteral createString(String value, boolean multi) {
    if(multi) {
      ArrayList list = new ArrayList();

      char[] ch = value.substring(1).toCharArray();
      StringBuffer buf = new StringBuffer();
      boolean new_line = false;

      for(int i=0; i < ch.length; i++) {
	switch(ch[i]) {
	case '\r' :
	  break;

	case '\n':
	  new_line = true;
	  break;

	case '|':
	  if(!new_line)
	    buf.append('|');
	  else {
	    list.add(buf.toString());
	    buf.setLength(0);
	  }

	  new_line = false;
	  break;

	default:
	  new_line = false;
	  buf.append(ch[i]);
	  break;
	}
      }

      list.add(buf.toString());

      return new StringLiteral(list);
    } else
      return new StringLiteral(unstringify(value));
  }

  // Remove " "
  private static String unstringify(String value) {
    return value.substring(1, value.length() - 1);
  }

  /** @return new BooleanLiteral for either "TRUE" or "FALSE"
   */
  public BooleanLiteral createBoolean(String value) {
    if(value.equals("TRUE"))
      return new BooleanLiteral(true);
    else
      return new BooleanLiteral(false);
  }

  /** @return new IntegerLiteral
   */
  public IntegerLiteral createInteger(String value) {
    return new IntegerLiteral(Integer.parseInt(value));
  }

  /** @return new FloatLiteral (which contains in fact a double)
   */
  public FloatLiteral createFloat(String value) {
    return new FloatLiteral(Double.parseDouble(value));
  }

  /** @return new tag attached to view objects
   */
  public Tag createTag(String ref) {
    return new Tag(Integer.parseInt(ref.substring(1)));
  }
}
