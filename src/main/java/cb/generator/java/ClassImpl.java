package cb.generator.java;

import java.io.PrintWriter;
import java.util.*;

/**
 * Simple representation of a Java Class.
 *
 * @version $Id: ClassImpl.java,v 1.5 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassImpl extends NodeImpl implements Class {
  private String pack;
  private ArrayList<Field> fields = new ArrayList<Field>();
  private ArrayList<Method> methods = new ArrayList<Method>();
  private ArrayList<String> super_classes = new ArrayList<String>();
  private ArrayList<String> interfaces = new ArrayList<String>();
  private ArrayList<String> prefix = new ArrayList<String>();
  private boolean isInterface;
  protected cb.petal.Class clazz;

  public ClassImpl() {
  }

  public void setClazz(cb.petal.Class c) {
    clazz = c;
  }

  public cb.petal.Class getClazz() {
    return clazz;
  }

  public void setPackage(String p) {
    pack = p;
  }

  public String getPackage() {
    return pack;
  }

  public void isInterface(boolean i) {
    isInterface = i;
  }

  public boolean isInterface() {
    return isInterface;
  }

  public void addField(Field f) {
    fields.add(f);
  }

  public void removeField(Field f) {
    fields.remove(f);
  }

  public void addMethod(Method f) {
    methods.add(f);
  }

  public void removeMethod(Method f) {
    methods.remove(f);
  }

  public void addSuperClass(String s) {
    super_classes.add(s);
  }

  public void removeSuperClass(String s) {
    super_classes.remove(s);
  }

  public void addImplementedInterface(String s) {
    interfaces.add(s);
  }

  public void removeImplementedInterface(String s) {
    interfaces.remove(s);
  }

  public String getQualifiedName() {
    if (pack != null && !"".equals(pack))
      return pack + "." + name;
    else
      return name;
  }

  public void addPrefixCode(String c) {
    prefix.add(c);
  }

  public Collection<Method> getMethods() {
    return methods;
  }

  public Collection<Field> getFields() {
    return fields;
  }

  public Collection<String> getSuperClasses() {
    return super_classes;
  }

  public Collection<String> getImplementedInterfaces() {
    return interfaces;
  }

  protected static void print(PrintWriter stream, String pre, String o,
      String post) {
    if ((o != null) && !"".equals(o))
      stream.print(pre + o + post);
  }

  /**
   * get the documentation
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public List<String> getDocumentation() {
    List<String> result = new ArrayList<String>();
    cb.petal.StringLiteral str = (cb.petal.StringLiteral) clazz
        .getProperty("documentation");

    if (str != null) {
      result = new ArrayList<String>(str.getLines());
    }
    return result;
  }

  /**
   * Default implementation prints Java code
   */
  public void dump(PrintWriter stream) {
    print(stream, "package ", pack, ";\n");

    for (Iterator i = prefix.iterator(); i.hasNext();)
      stream.println(i.next());

    stream.println("\n/** Created with Generator/"
        + "<a href=\"http://crazybeans.sourceforge.net/\">"
        + "\n * CrazyBeans</a> " + new Date() + "\n *");

    // Print documentation if any
    if (clazz != null) {
      for (String line : this.getDocumentation()) {
        stream.println(" * " + line);
      }
    }

    stream.println(" * @cbversion " + cb.util.Constants.VERSION + "\n */");
    print(stream, "", getAccess(), " ");

    if (isInterface())
      stream.print("interface " + getName() + " ");
    else
      stream.print("class " + getName() + " ");

    if (!super_classes.isEmpty()) {
      stream.print("extends ");

      for (Iterator i = super_classes.iterator(); i.hasNext();) {
        stream.print(i.next());

        if (i.hasNext())
          stream.print(", ");
      }

      stream.print(" ");
    }

    if (!interfaces.isEmpty() && !isInterface()) {
      stream.print("implements ");

      for (Iterator i = interfaces.iterator(); i.hasNext();) {
        stream.print(i.next());
        if (i.hasNext())
          stream.print(", ");
      }

      stream.print(" ");
    }

    stream.println("{");

    for (Iterator i = getFields().iterator(); i.hasNext();)
      ((Field) i.next()).dump(stream);

    stream.println();

    for (Iterator i = getMethods().iterator(); i.hasNext();) {
      ((Method) i.next()).dump(stream);

      if (i.hasNext())
        stream.println();
    }

    stream.println("}");
  }

  public boolean equals(Object o) {
    if (o instanceof Class) {
      Class c = (Class) o;

      return getQualifiedName().equals(c.getQualifiedName());
    } else
      return false;
  }
}
