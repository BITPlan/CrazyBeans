/** Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * @since 2015-01
 */
package cb.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import cb.generator.java.Class;
import cb.generator.java.Factory;
import cb.generator.java.Field;
import cb.generator.java.Method;
import cb.generator.java.NodeImpl;
import cb.petal.Association;
import cb.petal.Attribute;
import cb.petal.ClassAttribute;
import cb.petal.ClassCategory;
import cb.petal.DescendingVisitor;
import cb.petal.HasQuidu;
import cb.petal.Operation;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.PetalObject;
import cb.petal.QuidObject;
import cb.petal.RealizeRelationship;
import cb.petal.Role;
import cb.petal.UsesRelationship;
import cb.util.PiggybackVisitor;

/**
 * a Visitor that is used for generation
 * 
 * @author wf
 *
 */
public abstract class GeneratorVisitor extends DescendingVisitor implements
		Generator {

	protected static Logger LOGGER = Logger.getLogger("cb.generator");
	public static final boolean debug = false;

	protected Factory factory = Factory.getInstance();
	private PiggybackVisitor visitor;
	private Map<String, List<Attribute>> taggedValueMap = new LinkedHashMap<String, List<Attribute>>();
	public Map<String, List<Class>> classesByPackage;
	public Map<String, cb.generator.java.Package> packagesByName = new HashMap<String, cb.generator.java.Package>();

	/**
	 * Where to dump the result of this generator
	 */
	private String dump;

	/**
	 * The Rose Petal file to convert
	 */
	private PetalFile tree;

	/**
	 * @return class given by quid or null if it isn't a class
	 */
	protected Class getClass(String quid) {
		Class clazz = (Class) factory.getObject(quid);

		if (clazz == null) {
			QuidObject obj = getTree().getQuidObject(quid);

			if (obj instanceof cb.petal.Class) {
				visit((cb.petal.Class) obj);
				clazz = (Class) factory.getObject(quid);
			}
		}

		return clazz;
	}

	protected Class getClass(HasQuidu obj) {
		return getClass(obj.getQuidu());
	}

	/**
	 * @return containing class or null if it isn't a class
	 */
	protected Class getParentClass(PetalObject obj) {
		return getClass(((QuidObject) obj.getParent()).getQuid());
	}

	@Override
	public void visit(cb.petal.InheritanceRelationship rel) {
		Class c = getParentClass(rel);
		if (c != null)
			factory.addSuperClass(c, getClass(rel));
	}

	@Override
	public void visit(RealizeRelationship rel) {
		Class c = getParentClass(rel);
		if (c != null)
			factory.addImplementedInterface(c, getClass(rel));
	}

	@Override
	public void visit(UsesRelationship rel) {
		Class c = getParentClass(rel);
		if (c != null)
			factory.addUsedClass(c, getClass(rel), rel);
	}

	/**
	 * visit an association
	 */
	@Override
	public void visit(Association assoc) {
		Role first = assoc.getFirstRole();
		Role second = assoc.getSecondRole();
		Class class1 = getClass(first);
		Class class2 = getClass(second);

		if (class1 != null && class2 != null) {
			cb.petal.Class assc = assoc.getAssociationClass();
			Class ac = null;
			if (assc != null)
				ac = getClass(assc.getQuid());

			factory.addAssociation(class1, first, class2, second, ac);
		}
	}

	/**
	 * visit the given petal class
	 */
	public void visit(cb.petal.Class clazz) {
		String quid = clazz.getQuid();

		if (factory.getObject(quid) == null) {
			PetalNode categoryNode = clazz.getParent();
			if (categoryNode instanceof ClassCategory) {
				cb.generator.java.Package p = addPackage((ClassCategory) categoryNode);
				Class cl = factory.createClass(clazz, p);
				factory.addObject(quid, cl);
			} else {
				LOGGER.log(Level.WARNING,
						"wrong type of parent for class " + clazz.getQualifiedName()
								+ " is " + categoryNode.getClass().getName()
								+ " but should be ClassCategory");
			}

		}
	}

	/**
	 * visit the given attribute
	 */
	@Override
	public void visit(ClassAttribute attr) {
		Field f = factory.createField(attr);
		factory.addObject(attr.getQuid(), f);
		Class c = getParentClass(attr);
		if (c != null) {
			factory.addField(c, f);
		} else {
			LOGGER.log(Level.WARNING, "missing parent class for " + attr.toString());
		}
	}

	/**
	 * visit the given operation
	 */
	@Override
	public void visit(Operation op) {
		Method m = factory.createMethod(op);
		factory.addObject(op.getQuid(), m);
		Class c = getParentClass(op);
		if (c != null) {
			factory.addMethod(c, m);
		} else {
			LOGGER.log(Level.WARNING, "");
		}
	}

	/**
	 * add a package for the given category
	 * 
	 * @param category
	 * @return the package added or retrieved
	 */
	public cb.generator.java.Package addPackage(ClassCategory category) {
		String quid = category.getQuid();
		cb.generator.java.Package p = (cb.generator.java.Package) factory
				.getObject(quid);
		// does the factory already contain this package?
		if (p == null) {
			p = factory.createPackage(category);
			this.packagesByName.put(p.getQualifiedName(), p);
			factory.addObject(category.getQuid(), p);
		}
		return p;
	}

	@Override
	public void visit(ClassCategory category) {
		addPackage(category);
	}

	/**
	 * tagged Value handling
	 */
	@Override
	public void visit(Attribute attribute) {
		// go two steps up the hierarchy - the attribute is part of a set
		// PetalObject attributeSet = (PetalObject) attribute.getParent();
		// the parent of the set is the owner ...
		PetalNode parent = attribute.getParent();
		if (parent instanceof QuidObject) {
			QuidObject parentquidObj = (QuidObject) parent;
			String quid = parentquidObj.getQuid();
			if (debug)
				LOGGER.log(Level.INFO, "parent for attribute is "
						+ parent.getClass().getName() + "(" + quid + ")");
			List<Attribute> attrs = null;
			if (taggedValueMap.containsKey(quid)) {
				attrs = taggedValueMap.get(quid);
			} else {
				attrs = new ArrayList<Attribute>();
				taggedValueMap.put(quid, attrs);
			}
			attrs.add(attribute);
		} else {
			if (debug)
				LOGGER.log(Level.INFO, "parent for attribute is "
						+ parent.getClass().getName());
		}
	}

	/**
	 * @return the dump
	 */
	@Override
	public String getDumpPath() {
		return dump;
	}

	/**
	 * @param dump
	 *          the dump to set
	 */
	@Override
	public void setDumpPath(String dump) {
		this.dump = dump;
	}

	/**
	 * @return the tree
	 */
	@Override
	public PetalFile getTree() {
		return tree;
	}

	/**
	 * @param tree
	 *          the tree to set
	 */
	@Override
	public void setTree(PetalFile tree) {
		this.tree = tree;
	}

	@Override
	public void init() {
		visitor = new PiggybackVisitor(this);
	}

	@Override
	public void start() throws Exception {
		getTree().accept(visitor);
		// pass2 tagged Value handling
		for (Entry<String, List<Attribute>> taggedValueEntry : taggedValueMap
				.entrySet()) {
			String quid = taggedValueEntry.getKey();
			NodeImpl node = (NodeImpl) factory.getObject(quid);
			if (node != null)
				node.addTaggedValues(taggedValueEntry.getValue());
		}
	}

	/**
	 * default run method
	 */
	@Override
	public void run() throws Exception {
		init();
		start();
		dump();
	}

}
