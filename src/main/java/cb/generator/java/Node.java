package cb.generator.java;

import java.io.PrintWriter;
import java.io.PrintStream;

/**
 * Node that can be dumped to a file.
 *
 * @version $Id: Node.java,v 1.3 2001/06/25 15:51:11 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Node {
  public void   setName(String n);
  public String getName();
  public void   setAccess(String a);
  public String getAccess();

  /** E.g., if(is("public")) ...
   */
  public boolean is(String s);

  public void dump(PrintWriter stream);
}
