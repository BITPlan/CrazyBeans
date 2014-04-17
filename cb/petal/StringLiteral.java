package cb.petal;

import java.util.*;

/**
 * There are two kinds of string encodings in Rose: Either the usual "foo bar", or
 * a multi line string where each line starts with  a |. 
 *
 * @version $Id: StringLiteral.java,v 1.16 2005/05/09 20:23:11 moroff Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class StringLiteral extends Literal {
  static final long serialVersionUID=-4568943900411619930L;

  private ArrayList values = new ArrayList();
  private boolean   multi  = false;

  public StringLiteral(String v) {
    super("<String>");


    if(v.equals(""))
      addLine("");
    else {
      StringTokenizer tok = new StringTokenizer(v, "\r\n");

      multi = tok.countTokens() > 1;
      while(tok.hasMoreTokens())
	addLine(tok.nextToken());
    }
  }

  public StringLiteral(Collection c) {
    super("<String>");
    values = new ArrayList(c);
    multi  = true; // Initialized as multi line string
  }

  public void addLine(String v) {
    values.add(v);
  }

  public void removeLine(String v) {
    values.remove(v);
  }

  public Collection getLines() {
    return values;
  }

  /** @return first line, if available
   */
  public String getValue() {
    if (values.size() == 1 ) { 
       return (String)values.get(0);
    }
    else if(values.size() > 1) {
       StringBuffer buffer = new StringBuffer(1024);
       
       for (Iterator iter = values.iterator(); iter.hasNext();) {
         buffer.append(iter.next());
         if ( iter.hasNext() )
            buffer.append("\n");         
      }
      return buffer.toString();
    }
    else
      return null;
  }

  public java.lang.Object getLiteralValue() {
    StringBuffer buf = new StringBuffer();

    for(Iterator i = values.iterator(); i.hasNext(); ) {
      buf.append(i.next());
      if(i.hasNext())
	buf.append("\\n");
    }

    return buf.toString();
  }

  public void    setMulti(boolean m) { multi = m; }
  public boolean getMulti()          { return multi; }

  public boolean isMultiLine()          { return (values.size() > 1) || multi; }

  public String toString() {
    if(isMultiLine()) {
      StringBuffer buf = new StringBuffer(cb.util.Constants.getNewLine());

      for(Iterator i = values.iterator(); i.hasNext(); )
	buf.append("|" + i.next() + cb.util.Constants.getNewLine());

      return buf.toString();
    } else
      return '"' + getValue() + '"';
  }

  public void accept(Visitor v) {
    v.visit(this);
  }

  public boolean equals(java.lang.Object o) {
    return (o instanceof StringLiteral) && (((StringLiteral)o).values.equals(this.values));
  }
}
