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
import java.util.*;

/**
 * Simple representation of a Java Class.
 *
 * @version $Id: ClassImpl.java,v 1.5 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ClassImpl extends NodeImpl implements Class {
  private Package pack;
  private ArrayList<Field> fields = new ArrayList<Field>();
  private ArrayList<Method> methods = new ArrayList<Method>();
  private ArrayList<String> super_classes = new ArrayList<String>();
  private ArrayList<String> interfaces = new ArrayList<String>();
  private ArrayList<String> prefix = new ArrayList<String>();
  private boolean isInterface;
  protected cb.petal.Class clazz;

  /**
   * construct me from the given clazz
   * @param clazz
   */
  public ClassImpl(cb.petal.Class clazz) {
  		super(clazz);
  		setClazz(clazz);
  }

  public void setClazz(cb.petal.Class c) {
    super.setDocumentedObject(c);;
    clazz = c;
  }

  public cb.petal.Class getClazz() {
    return clazz;
  }

  /**
   * set my package
   */
  public void setPackage(Package p) {
    pack = p;
  }

  /**
   * get my package
   */
  public Package getPackage() {
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
   * Default implementation prints Java code
   */
  public void dump(PrintWriter stream) {
    print(stream, "package ", pack.getQualifiedName(), ";\n");

    for (Iterator<String> i = prefix.iterator(); i.hasNext();)
      stream.println(i.next());

    stream.println("\n/** Created with Generator/"
        + "<a href=\"https://github.com/BITPlan/CrazyBeans/\">"
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

      for (Iterator<String> i = super_classes.iterator(); i.hasNext();) {
        stream.print(i.next());

        if (i.hasNext())
          stream.print(", ");
      }

      stream.print(" ");
    }

    if (!interfaces.isEmpty() && !isInterface()) {
      stream.print("implements ");

      for (Iterator<String> i = interfaces.iterator(); i.hasNext();) {
        stream.print(i.next());
        if (i.hasNext())
          stream.print(", ");
      }

      stream.print(" ");
    }

    stream.println("{");

    for (Iterator<Field> i = getFields().iterator(); i.hasNext();)
      (i.next()).dump(stream);

    stream.println();

    for (Iterator<Method> i = getMethods().iterator(); i.hasNext();) {
      (i.next()).dump(stream);

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
