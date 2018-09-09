/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

import java.util.StringTokenizer;

/**
 * Represents operation object, i.e. method.
 *
 * @version $Id: Operation.java,v 1.17 2005/05/09 20:15:05 moroff Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Operation extends AccessObject {
  static final long serialVersionUID=8462139492522368436L;

  public Operation(PetalNode parent, java.util.Collection params) {
    super(parent, "Operation", params);
  }

  public Operation() {
    super("Operation");
  }

  public List getParameters() {
    return (List)getProperty("parameters");
  }

  public void setParameters(List o) {
    defineProperty("parameters", o);
  }

  public List getExceptions() {
     PetalNode node = getProperty("exceptions");
     if ( node instanceof List )
        return (List)node;
     else if ( node instanceof StringLiteral ){
        List list = new List("exceptions");
        
        StringTokenizer parse = new StringTokenizer(((StringLiteral)node).getValue(), ",");
        while (parse.hasMoreTokens()) {
           list.add(new StringLiteral(parse.nextToken()));
        }
        return list;
     }
     else
        return null;
  }
  
  public void setExceptions(List o) {
     defineProperty("exceptions", o);
  }
  
  public String getConcurrency() {
    return getPropertyAsString("concurrency");
  }

  public void setConcurrency(String c) {
    defineProperty("concurrency", c);
  }

  public String getResult() {
    return getPropertyAsString("result");
  }

  public void setResult(String c) {
    defineProperty("result", c);
  }

  public void setPostCondition(SemanticInfo c) {
    defineProperty("post_condition", c);
  }

  public SemanticInfo getPostCondition() {
    return (SemanticInfo)getProperty("post_condition");
  }

  public void setSemantics(SemanticInfo c) {
    defineProperty("semantics", c);
  }

  public SemanticInfo getSemantics() {
    return (SemanticInfo)getProperty("semantics");
  }

  public int getUid() {
    return getPropertyAsInteger("uid");
  }

  public void setUid(int uid) {
    defineProperty("uid", uid);
  }

  // Overridden, has different name for some reason
  public String getExportControl() {
    return getPropertyAsString("opExportControl");
  }

  public void setExportControl(String o) {
    defineProperty("opExportControl", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
