package cb.generator;

import java.io.PrintWriter;
import java.util.*;
import cb.petal.Operation;

/**
 * Simple representation of a Java method.
 *
 * @version $Id: MethodImpl.java,v 1.3 2001/06/27 12:56:35 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class MethodImpl extends NodeImpl implements Method {
  private   String    type;
  private   List      parameters;
  private   List      code;
  protected Operation operation;

  public MethodImpl() {  }
  public void      setOperation(Operation o) { operation = o; }
  public Operation getOperation()            { return operation; }

  public void   setReturnType(String p) { type = p; }
  public String getReturnType() { return type; }
  public void setParameters(List p) { parameters = p; }
  public List getParameters()       { return parameters; }
  public void setCode(List c) { code = c; }
  public List getCode()       { return code; }

  /** Default implementation prints Java code
   */
  public void dump(PrintWriter stream) {
    printDocumentation(stream, operation);

    stream.print("  ");
    String acc = getAccess();

    if(acc != null)
      stream.print(acc.toLowerCase() + " ");

    stream.print(getReturnType() + " " + getName());
    stream.print("(");

    for(Iterator i = parameters.iterator(); i.hasNext();) {
      Parameter p = (Parameter)i.next();
      p.dump(stream);

      if(i.hasNext())
	stream.print(", ");
    }
      
    stream.print(")");

    if(is("abstract"))
      stream.println(";");
    else {
      stream.println(" {");

      if(code != null) {
	for(Iterator i = code.iterator(); i.hasNext();)
	  stream.println(i.next());
      } else {
	String t = getReturnType().toLowerCase();

	if(!(t.equals("void") || t.equals(""))) {
	  stream.println("  return " + cb.util.Constants.getValueForType(t));
	}
      }
      
      stream.println("  }");
    }
  }

  public boolean equals(Object o) {
    if(o instanceof Method) {
      Method m = (Method)o;

      return getParameters().equals(m.getParameters()) &&
	getName().equals(m.getName()) && getReturnType().equals(m.getReturnType());
    } else
      return false;
  }
}
