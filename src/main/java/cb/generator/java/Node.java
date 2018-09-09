/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.generator.java;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import cb.petal.Documented;

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
  /**
   * get my unique id
   * @return - the original petal id
   */
  public String getId();
  
  /**
   * @return the documentedObject
   */
  public Documented getDocumentedObject();

  /**
   * @param documentedObject the Documented to set
   */
  public void setDocumentedObject(Documented documentedObject);

  /** E.g., if(is("public")) ...
   */
  public boolean is(String s);

  public void dump(PrintWriter stream);
  /**
   * get the documentation of the node
   * @return a list of strings - one per line
   */
  public List<String> getDocumentation();
  
  /**
   * @return the taggedValues
   */
  public Map<String, String> getTaggedValues();
 
}
