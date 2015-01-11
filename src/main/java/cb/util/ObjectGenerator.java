package cb.util;

import cb.petal.BooleanLiteral;
import cb.petal.DescendingVisitor;
import cb.petal.FloatLiteral;
import cb.petal.IntegerLiteral;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.StringLiteral;
import cb.parser.*;


import java.util.*;

/**
 * Generate class derived from petal object for given type.
 *
 * @version $Id: ObjectGenerator.java,v 1.9 2001/11/01 15:56:49 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ObjectGenerator extends DescendingVisitor {
  private String ident;
  private boolean first = false;
  private HashSet found = new HashSet();

  private ObjectGenerator(String id) {
    this.ident = id;
  }

  public void visitObject(PetalObject obj) {
    if(obj.getName().equals(ident)) {
      if(!first) {
	String class_name = Constants.makeName(obj.getName());

	System.out.println("package cb.petal;\nimport java.util.Collection;\n\n" +
			   "/**\n * Represents " + obj.getName() + " object\n" + " *\n" +
			   " * @version $Id: ObjectGenerator.java,v 1.9 2001/11/01 15:56:49 dahm Exp $\n" +
			   " * @author  <A HREF=\"http://www.berlin.de/~markus.dahm/\">" +
			   "M. Dahm</A>\n */");

	System.out.println("public class " + class_name + " extends PetalObject {");
	System.out.println("  public " + class_name +
			   "(PetalObject parent, Collection params) {");
	System.out.println("    super(parent, \"" + ident + "\", params);");
	System.out.println("  }\n");


	System.out.println("  public " + class_name + "() {");
	System.out.println("    super(\"" + ident + "\");");
	System.out.println("  }\n");

	int k = 0;
	for(Iterator i = obj.getParameterList().iterator(); i.hasNext(); k++) {
	  System.out.println("  public void setViewParameter(String o) {");
	  System.out.println("    params.set(" + k + ", o);\n  }\n");
	  
	  System.out.println("  public String getViewParameter() {");
	  System.out.println("    return (String)params.get(" + k + ");\n  }\n");
	  i.next();
	}

	// for DefaultVisitor and Visitor
	System.err.println("  public void visit(" + class_name + " obj) { visitObject(obj); }");
      }

      for(Iterator i = obj.getNames().iterator(), j = obj.getPropertyList().iterator();
	  i.hasNext(); ) {
	String    name = (String)i.next();
	PetalNode node = (PetalNode)j.next();
	String    type = getShortType(node);


	if(!found.contains(name) && (type != null) && !name.equals("quid") && !name.equals("quidu")) {
	  String type2  = getLongType(type);
	  String method = Constants.makeName(name);
	  boolean prim = true;

	  if(name.equals("name"))
	    method = obj.getName() + "Name";

	  if(name.equals("value"))
	    type = type2 = "PetalNode";

	  try { // Hack warning
	    java.lang.Class cl = java.lang.Class.forName("java.lang." + type2);
	  } catch(ClassNotFoundException e) {
	    prim = false;
	  }

	  if(prim) {
	    System.out.println("  public " + type + " get" + method + "() {\n" +
			       "    return getPropertyAs" + type2 +
			       "(\"" + name + "\");\n" +
			       "  }\n");

	    System.out.println("  public void set" + method + "(" + type + " o) {\n" +
			       "    defineProperty(\"" + name + "\", o);\n" +
			       "  }\n");
	  } else {
	    System.out.println("  public " + type + " get" + method + "() {\n" +
			       "    return (" + type +
			       ")getProperty(\"" + name + "\");\n  }\n");

	    System.out.println("  public void set" + method + "(" + type + " o) {\n" +
			       "    defineProperty(\"" + name + "\", o);\n" +
			       "  }\n");
	  }
	}

	found.add(name);
      }

      first = true;
    }// else
    // System.err.println("Unknown object type " + obj.getName());

    super.visitObject(obj);
  }

  private static String getShortType(PetalNode node) {
    if(node instanceof StringLiteral)
      return "String";
    else if(node instanceof IntegerLiteral)
      return "int";
    else if(node instanceof BooleanLiteral)
      return "boolean";
    else if(node instanceof FloatLiteral)
      return "double";
    else {
      String name = node.getClass().getName();
      int index = name.lastIndexOf('.');

      return name.substring(index + 1);
    }
  }

  private static String getLongType(String type) {
    if(type.equals("double"))
      return "Float";
    else if(type.equals("int"))
      return "Integer";
    else if(type.equals("boolean"))
      return "Boolean";
    else 
      return type;
  }

  public static void main(String[] args) {
    PetalFile tree = cb.parser.PetalParser.parse(args);

    tree.accept(new ObjectGenerator(args[1]));

    System.out.println("  public void accept(Visitor v) {\n    v.visit(this);\n  }");
    System.out.println("}");
  }
}
