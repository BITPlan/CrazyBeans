/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.generator.java;

import java.util.Collection;

/**
 * Represents a class consisting of fields and methods, should be useful
 * for Java and C++.
 *
 * @version $Id: Class.java,v 1.4 2001/06/27 10:26:03 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Class extends Node {
  public void   addPrefixCode(String c); // Inserted before class itself, e.g. imports
  public void   addSuperClass(String c);
  public void   removeSuperClass(String f);
  public void   addImplementedInterface(String c);
  public void   removeImplementedInterface(String f);
  public void   addField(Field f);
  public void   removeField(Field f);
  public void   addMethod(Method m);
  public void   removeMethod(Method m);
  public void setPackage(Package p);
  public Package getPackage();
  public boolean isInterface();
  public Collection<Field> getFields();
  public Collection<Method> getMethods();
  public String getQualifiedName();
}
