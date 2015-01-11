package cb.generator;
import cb.petal.Documented;
import cb.petal.PetalObject;
import cb.petal.StringLiteral;


import java.util.*;
/**
 * Simple representation of a node.
 *
 * @version $Id: NodeImpl.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class NodeImpl implements Node {
  protected String name;
  protected String access;

  public void   setName(String n)    { name = n; }
  public String getName()            { return name; }
  public void   setAccess(String a)  { access = a; }
  public String getAccess()          { return access; }

  public boolean is(String s) {
    return access.toLowerCase().indexOf(s.toLowerCase()) >= 0;
  }

  protected static void print(java.io.PrintWriter stream,
			      String pre, String o, String post) {
    if((o != null) && !"".equals(o))
      stream.print(pre + o + post);
  }

  protected static void printDocumentation(java.io.PrintWriter stream, Documented d) {
    if(d != null) {
      StringLiteral str =
	(StringLiteral)((PetalObject)d).getProperty("documentation");
      
      if(str != null) {
	stream.print("  /**");
	Collection lines = str.getLines();
	
	for(Iterator i=lines.iterator(); i.hasNext(); ) {
	  stream.println(" * " + i.next());
	}

	stream.println("  */");
      }
    }
  }
}
