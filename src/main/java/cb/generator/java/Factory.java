package cb.generator.java;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import cb.petal.Association;
import cb.petal.ClassAttribute;
import cb.petal.ClassCategory;
import cb.petal.Operation;
import cb.petal.Role;
import cb.petal.UsesRelationship;
import cb.util.Constants;

/**
 * Factory for classes, methods, etc., it also contains methods to add
 * relationships, like uses/realize relationships. If you don't like the way the
 * default factory works just subclass it, and call setInstance() (before
 * creating the generator, of course).
 *
 * @version $Id: Factory.java,v 1.6 2001/06/27 12:56:35 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Factory {
	private static Factory instance = new Factory();

	/**
	 * Register created objects by the quid of the petal object
	 */
	protected HashMap<String, Node> quid_map = new LinkedHashMap<String, Node>(); // Map<quid,
																																								// QuidObject>

	public void addObject(String quid, Node obj) {
		quid_map.put(quid, obj);
	}

	public void removeObject(String quid) {
		quid_map.remove(quid);
	}

	public Node getObject(String quid) {
		return (Node) quid_map.get(quid);
	}

	/**
	 * get the objects
	 * 
	 * @return the objects
	 */
	public Collection<Node> getObjects() {
		return quid_map.values();
	}

	protected Factory() {
	}

	public static Factory getInstance() {
		return instance;
	}

	public static void setInstance(Factory f) {
		instance = f;
	}

	/**
	 * get the class name for the given clazz
	 * 
	 * @param clazz
	 *          - the clazz to get the name for
	 * @return the fullyQualifying class name if a package exists
	 */
	protected String getClassName(Class clazz) {
		if (clazz == null)
			return "?";
		if ("".equals(clazz.getPackage()))
			return clazz.getName();
		else
			return clazz.getPackage() + "." + clazz.getName();
	}

	

	/**
	 * split a fully qualified rose name (with "foo::bar")
	 * 
	 * @return a record / tuple (class name, package name)
	 * @author wf
	 *
	 */
	static class Name {
		String name, pack;
		/**
		 * Convert Rose identifier to normal one.
		 */
		protected static String makeName(String name) {
			char[] chars = name.toCharArray();
			StringBuffer buf = new StringBuffer();

			for (int i = 0; i < chars.length; i++) {
				char ch = chars[i];

				if ((ch == ':') && (chars[i + 1] == ':')) {
					buf.append('.');
					i++;
				} else {
					if (Character.isLetterOrDigit(ch))
						buf.append(ch);
					else
						buf.append('_');
				}
			}

			return buf.toString();
		}

		/**
		 * construct a name from the given qualifier
		 * @param qual
		 */
		public Name(String qual) {
			int index = qual.indexOf("::");

			if (index < 0) { // weird ...
				pack = "";
				name = makeName(qual);
			} else {
				int index2 = qual.lastIndexOf("::");

				if (index == index2)
					pack = "";
				else
					pack = makeName(qual.substring(index + 2, index2));

				name = qual.substring(index2 + 2);
			}
		}
	}

	/**
	 * create a Class
	 * 
	 * @param clazz
	 * @param name
	 * @param pack
	 * @param acc
	 * @param is_interface
	 * @return the class just created
	 */
	public Class createClass(cb.petal.Class clazz, String name, Package pack,
			String acc, boolean is_interface) {
		ClassImpl c = new ClassImpl(clazz);

		c.setName(name);
		c.setPackage(pack);
		c.setAccess(acc);
		c.isInterface(is_interface);
		return c;
	}

	/**
	 * create a Class based on the given petal Clazz
	 * 
	 * @param clazz
	 * @param p 
	 * @return the Class
	 */
	public Class createClass(cb.petal.Class clazz, Package p) {
		Name name=new Name(clazz.getQualifiedName());
		String acc = clazz.getExportControl();

		if (acc == null)
			acc = "public";

		return createClass(clazz, name.name, p, acc, clazz.isInterface());
	}

	/**
	 * create a package for the given category
	 * 
	 * @param category
	 * @return the package
	 */
	public Package createPackage(ClassCategory category) {
		PackageImpl pack = new PackageImpl(category);
		pack.setName(category.getNameParameter());
		String qname=category.getQualifiedName();
		qname=qname.replace("Logical View::", ""); // remove Logical View part
		qname=qname.replace("::", "."); // use java convention
		pack.setQualifiedName(qname);
		return pack;
	}

	/**
	 * create a method
	 * 
	 * @param op
	 * @param name
	 * @param type
	 * @param acc
	 * @param params
	 * @return the method just created
	 */
	public Method createMethod(Operation op, String name, String type,
			String acc, java.util.List params) {
		MethodImpl method = new MethodImpl(op);

		method.setName(name);
		method.setAccess(acc);
		method.setReturnType(type);
		method.setParameters(params);


		return method;
	}

	/**
	 * create a method from the given operation
	 * 
	 * @param op
	 * @return the method
	 */
	public Method createMethod(Operation op) {
		String name = op.getNameParameter();
		String acc = op.getExportControl();

		if (acc == null)
			acc = "public";

		String type = op.getResult();
		if (type == null)
			type = "void";

		ArrayList params = new ArrayList();

		if (op.getParameters() != null) {
			for (Iterator i = op.getParameters().getElements().iterator(); i
					.hasNext();) {
				cb.petal.Parameter p = (cb.petal.Parameter) i.next();

				params.add(createParameter(p));
			}
		}

		return createMethod(op, name, type, acc, params);
	}

	/**
	 * create a parameter
	 * 
	 * @param name
	 * @param type
	 * @return the parameter just created
	 */
	public Parameter createParameter(String name, String type) {
		ParameterImpl param = new ParameterImpl();
		param.setName(name);
		param.setType(type);

		return param;
	}

	/**
	 * create a parameter base on a petal parameter
	 * 
	 * @param p
	 * @return - the parameter
	 */
	public Parameter createParameter(cb.petal.Parameter p) {
		String type = p.getType();
		String name = p.getNameParameter();

		if (type == null) { // Sometimes type and name stick together "AType t"
			int index = name.indexOf(' ');

			if (index < 0)
				type = "Object";
			else {
				type = name.substring(0, index);
				name = name.substring(index + 1);
			}
		}

		return createParameter(name, type);
	}

	/**
	 * create a field based on the given petal attribute
	 * 
	 * @param attr
	 * @param name
	 * @param type
	 * @param acc
	 * @param init
	 * @return the field just created
	 */
	public Field createField(ClassAttribute attr, String name, String type,
			String acc, String init) {
		FieldImpl field = new FieldImpl(attr);

		field.setName(name);
		field.setAccess(acc);
		field.setInitialValue(init);
		field.setType(type);
	
		return field;
	}

	/**
	 * create a field based on a petal attribute
	 * 
	 * @param attr
	 * @return the field
	 */
	public Field createField(ClassAttribute attr) {
		String name = attr.getNameParameter();
		String acc = attr.getExportControl();
		if (acc == null)
			acc = "private";

		if (attr.getStatic())
			acc += " static";

		String type = attr.getType();
		if (type == null)
			type = "Object";

		String init = attr.getInitialValue();

		return createField(attr, name, type, acc, init);
	}

	/**
	 * add the superclass based on the petal equivalents
	 * 
	 * @param c
	 * @param super_class
	 */
	public void addSuperClass(Class c, Class super_class) {
		c.addSuperClass(getClassName(super_class));
	}

	/**
	 * add implemented interfaces based on the petal equivalents
	 * 
	 * @param c
	 * @param super_class
	 */
	public final void addImplementedInterface(Class c, Class super_class) {
		addRealizedClass(c, super_class);
	}

	/**
	 * Alias for addImplementedInterface()
	 */
	public void addRealizedClass(Class c, Class super_class) {
		c.addImplementedInterface(getClassName(super_class));
		// TODO: Add inherited methods
	}

	protected int counter;

	/**
	 * create the setter and getter methods
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public Method[] createSetGetMethods(String name, String type) {
		String mname = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		Method set = createMethod(null, "set" + mname, "void", "public",
				Arrays.asList(new Parameter[] { createParameter(name, type) }));
		set.setCode(Arrays.asList(new String[] { "    this." + name + " = " + name
				+ ";" }));

		Method get = createMethod(null, "get" + mname, type, "public",
				Collections.EMPTY_LIST);
		get.setCode(Arrays.asList(new String[] { "    return this." + name + ";" }));

		return new Method[] { set, get };
	}

	private static String map(String number) {
		if ("n".equals(number.toLowerCase()) || "*".equals(number))
			return "" + Integer.MAX_VALUE;
		else
			return number;
	}

	/**
	 * get the cardinality for a given Role
	 * 
	 * @param role
	 * @return the Cardinality as a Dimension
	 */
	protected Dimension getCardinality(Role role) {
		int from = 1, to = 1;

		if (role.getClientCardinality() != null) {
			String card = role.getClientCardinality().getStringValue();
			StringTokenizer tok = new StringTokenizer(card, ".");

			try {
				from = Integer.parseInt(map(tok.nextToken()));

				if (tok.hasMoreTokens())
					to = Integer.parseInt(map(tok.nextToken()));
				else
					to = from;
			} catch (Exception e) {
				throw new RuntimeException("Invalid cardinality " + card);
			}
		} else {
			Role other = role.getOtherRole();

			if (other.isAggregate()) {
				from = 0;
				to = Integer.MAX_VALUE;
			}
		}

		return new Dimension(from, to);
	}

	/**
	 * create an association for the petal classes given
	 * 
	 * @param class1
	 * @param class2
	 * @param assoc_class
	 * @return the associaton created
	 */
	private Class createAssociationClass(Class class1, Class class2,
			Class assoc_class) {
		String name = class1.getName() + "_" + class2.getName() + "_" + counter++;
		Class clazz = createClass(null, name, class1.getPackage(), "public final",
				false);

		if (assoc_class != null) {
			name = assoc_class.getName();
			clazz = assoc_class;
		}

		String name1 = class1.getQualifiedName();
		String name2 = class2.getQualifiedName();
		boolean equal = name1.equals(name2); // Self association?
		String map1 = class1.getName() + "_" + class2.getName();
		String map2 = class2.getName() + "_" + class1.getName();

		clazz.addPrefixCode("import java.util.*;");

		Field field = createField(null, map1, "HashMap", "private", "new HashMap()");
		clazz.addField(field);

		if (!equal) {
			field = createField(null, map2, "HashMap", "private", "new HashMap()");
			clazz.addField(field);
		}

		field = createField(null, "instance", name, "public static final", "new "
				+ name + "()");
		clazz.addField(field);

		// Constructor
		Method method = createMethod(null, name, "", "private",
				Collections.EMPTY_LIST);
		if (!clazz.getMethods().contains(method))
			clazz.addMethod(method);

		method = createMethod(
				null,
				"lookup",
				"HashSet",
				"private static final",
				Arrays.asList(new Parameter[] { createParameter("map", "HashMap"),
						createParameter("obj", "Object") }));
		method.setCode(Arrays.asList(new String[] {
				"    HashSet set = (HashSet)map.get(obj);\n", "    if(set == null)",
				"      map.put(obj, set = new HashSet());\n", "    return set;" }));
		clazz.addMethod(method);

		method = createConnectionMethod(map1, map2, equal, "add", name1, name2);
		clazz.addMethod(method);

		method = createConnectionMethod(map1, map2, equal, "remove", name1, name2);
		clazz.addMethod(method);

		method = createMethod(null, "getConnections", "Collection", "public final",
				Arrays.asList(new Parameter[] { createParameter("a", name1) }));
		method.setCode(Arrays.asList(new String[] { "    return lookup(" + map1
				+ ", a);" }));
		clazz.addMethod(method);

		if (!equal) {
			method = createMethod(null, "getConnections", "Collection",
					"public final",
					Arrays.asList(new Parameter[] { createParameter("b", name2) }));
			method.setCode(Arrays.asList(new String[] { "    return lookup(" + map2
					+ ", b);" }));
			clazz.addMethod(method);
		}

		return clazz;
	}

	private Method createConnectionMethod(String map1, String map2,
			boolean equal, String call, String name1, String name2) {
		Method method = createMethod(
				null,
				call + "Connection",
				"void",
				"public final",
				Arrays.asList(new Parameter[] { createParameter("a", name1),
						createParameter("b", name2) }));

		ArrayList code = new ArrayList();

		code.add("    HashSet set1 = lookup(" + map1 + ", a);");
		code.add("    set1." + call + "(b);\n");
		if (!equal) {
			code.add("    HashSet set2 = lookup(" + map2 + ", b);");
			code.add("    set2." + call + "(a);");
		}

		method.setCode(code);
		return method;
	}

	/**
	 * Simply maps association to a newly generated class that maintains the
	 * connections. If the association has an association class no new class is
	 * generated. Also adds access methods to the corresponding classes.
	 *
	 * TODO: Does not regard multiple associations between classes, cardinality
	 * currently not checked.
	 */
	public void addAssociation(Class class1, Role role1, Class class2,
			Role role2, Class assoc_class) {
		Dimension dim1 = getCardinality(role1);
		Dimension dim2 = getCardinality(role2);
		Association assoc = (Association) role1.getParent();
		String name1 = class1.getQualifiedName();
		String name2 = class2.getQualifiedName();
		boolean equal = name1.equals(name2); // Self association?
		Class clazz = createAssociationClass(class1, class2, assoc_class);

		if (assoc_class != null) // Don't add twice
			addObject(assoc.getQuid(), clazz);

		Method method = createMethod(null, "add" + class2.getName(), "void",
				"public",
				Arrays.asList(new Parameter[] { createParameter("a", name2) }));
		method.setCode(Arrays.asList(new String[] { "    "
				+ clazz.getQualifiedName() + ".instance.addConnection(this, a);" }));
		class1.addMethod(method);

		method = createMethod(null, "remove" + class2.getName(), "void", "public",
				Arrays.asList(new Parameter[] { createParameter("a", name2) }));
		method.setCode(Arrays.asList(new String[] { "    "
				+ clazz.getQualifiedName() + ".instance.removeConnection(this, a);" }));
		class1.addMethod(method);

		method = createMethod(null, "get" + class2.getName() + "s",
				"java.util.Collection", "public", Collections.EMPTY_LIST);
		method.setCode(Arrays.asList(new String[] { "    return "
				+ clazz.getQualifiedName() + ".instance.getConnections(this);" }));
		class1.addMethod(method);

		method = createMethod(null, "add" + class1.getName(), "void", "public",
				Arrays.asList(new Parameter[] { createParameter("a", name1) }));
		method.setCode(Arrays.asList(new String[] { "    "
				+ clazz.getQualifiedName() + ".instance.addConnection(a, this);" }));
		class2.addMethod(method);

		method = createMethod(null, "remove" + class1.getName(), "void", "public",
				Arrays.asList(new Parameter[] { createParameter("a", name1) }));
		method.setCode(Arrays.asList(new String[] { "    "
				+ clazz.getQualifiedName() + ".instance.removeConnection(a, this);" }));
		class2.addMethod(method);

		method = createMethod(null, "get" + class1.getName() + "s",
				"java.util.Collection", "public", Collections.EMPTY_LIST);
		method.setCode(Arrays.asList(new String[] { "    return "
				+ clazz.getQualifiedName() + ".instance.getConnections(this);" }));
		class2.addMethod(method);
	}

	public void addUsedClass(Class c, Class used_class, UsesRelationship rel) {
		// Simply adds field and set/get methods here
		String type = getClassName(used_class);
		String name = Constants.makeName(rel.getLabel());

		if (name == null)
			name = "uses" + counter++;

		Field f = createField(null, name, type, "private", null);
		c.addField(f);

		Method[] m = createSetGetMethods(name, type);
		c.addMethod(m[0]);
		c.addMethod(m[1]);
	}

	/**
	 * add a Field to the given class c
	 * 
	 * @param c
	 * @param field
	 */
	public void addField(Class c, Field field) {
		// Add set/get methods if the field is private and non-static

		if (c.isInterface()) {
			field.setAccess("public static final");

			if (field.getInitialValue() == null)
				field.setInitialValue(Constants.getValueForType(field.getType()));
		} else {
			if (field.is("private") && !field.is("static")) {
				Method[] m = createSetGetMethods(field.getName(), field.getType());
				c.addMethod(m[0]);
				c.addMethod(m[1]);
			}
		}

		c.addField(field);
	}

	/**
	 * add a method to the given class
	 * 
	 * @param c
	 * @param m
	 */
	public void addMethod(Class c, Method m) {
		if (c.isInterface() && !m.is("abstract"))
			m.setAccess(m.getAccess() + " abstract");
		c.addMethod(m);
	}
}
