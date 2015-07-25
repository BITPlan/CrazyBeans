package cb.generator.java;

import java.util.List;

/**
 * Represents a Method containing code.
 *
 * @version $Id: Method.java,v 1.3 2001/06/25 15:51:11 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Method extends Node {
  public void setParameters(List<Parameter> p);
  public List<Parameter> getParameters();

  public void   setCode(List<String> c);
  public List<String>   getCode();
  public void   setReturnType(String p);
  public String getReturnType();
}
