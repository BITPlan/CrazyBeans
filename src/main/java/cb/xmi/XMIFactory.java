/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015 BITPlan GmbH
 *

 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 * 
 */
package cb.xmi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.novosoft.uml.behavior.common_behavior.MSignal;
import ru.novosoft.uml.behavior.common_behavior.MSignalImpl;
import ru.novosoft.uml.behavior.use_cases.MActorImpl;
import ru.novosoft.uml.behavior.use_cases.MUseCaseImpl;
import ru.novosoft.uml.foundation.core.MAbstraction;
import ru.novosoft.uml.foundation.core.MAbstractionImpl;
import ru.novosoft.uml.foundation.core.MAssociation;
import ru.novosoft.uml.foundation.core.MAssociationClassImpl;
import ru.novosoft.uml.foundation.core.MAssociationEnd;
import ru.novosoft.uml.foundation.core.MAssociationEndImpl;
import ru.novosoft.uml.foundation.core.MAssociationImpl;
import ru.novosoft.uml.foundation.core.MAttribute;
import ru.novosoft.uml.foundation.core.MAttributeImpl;
import ru.novosoft.uml.foundation.core.MClassImpl;
import ru.novosoft.uml.foundation.core.MClassifier;
import ru.novosoft.uml.foundation.core.MComponentImpl;
import ru.novosoft.uml.foundation.core.MConstraint;
import ru.novosoft.uml.foundation.core.MConstraintImpl;
import ru.novosoft.uml.foundation.core.MDataTypeImpl;
import ru.novosoft.uml.foundation.core.MDependency;
import ru.novosoft.uml.foundation.core.MDependencyImpl;
import ru.novosoft.uml.foundation.core.MGeneralization;
import ru.novosoft.uml.foundation.core.MGeneralizationImpl;
import ru.novosoft.uml.foundation.core.MInterfaceImpl;
import ru.novosoft.uml.foundation.core.MModelElement;
import ru.novosoft.uml.foundation.core.MOperation;
import ru.novosoft.uml.foundation.core.MOperationImpl;
import ru.novosoft.uml.foundation.core.MParameter;
import ru.novosoft.uml.foundation.core.MParameterImpl;
import ru.novosoft.uml.foundation.core.MUsage;
import ru.novosoft.uml.foundation.core.MUsageImpl;
import ru.novosoft.uml.foundation.data_types.MAggregationKind;
import ru.novosoft.uml.foundation.data_types.MBooleanExpression;
import ru.novosoft.uml.foundation.data_types.MCallConcurrencyKind;
import ru.novosoft.uml.foundation.data_types.MExpression;
import ru.novosoft.uml.foundation.data_types.MMultiplicity;
import ru.novosoft.uml.foundation.data_types.MParameterDirectionKind;
import ru.novosoft.uml.foundation.data_types.MScopeKind;
import ru.novosoft.uml.foundation.data_types.MVisibilityKind;
import ru.novosoft.uml.foundation.extension_mechanisms.MStereotype;
import ru.novosoft.uml.foundation.extension_mechanisms.MStereotypeImpl;
import ru.novosoft.uml.foundation.extension_mechanisms.MTaggedValue;
import ru.novosoft.uml.foundation.extension_mechanisms.MTaggedValueImpl;
import ru.novosoft.uml.model_management.MModel;
import ru.novosoft.uml.model_management.MModelImpl;
import ru.novosoft.uml.model_management.MPackage;
import ru.novosoft.uml.model_management.MPackageImpl;
import ru.novosoft.uml.model_management.MSubsystemImpl;
import cb.petal.AccessQualified;
import cb.petal.Association;
import cb.petal.ClassAttribute;
import cb.petal.ClassCategory;
import cb.petal.ClassUtility;
import cb.petal.DependencyRelationship;
import cb.petal.Documented;
import cb.petal.InheritanceRelationship;
import cb.petal.List;
import cb.petal.Module;
import cb.petal.Operation;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.RealizeRelationship;
import cb.petal.Relationship;
import cb.petal.Role;
import cb.petal.StereoTyped;
import cb.petal.StringLiteral;
import cb.petal.SubSystem;
import cb.petal.UseCase;
import cb.petal.UsesRelationship;

/**
 * Factory for classes, methods, etc., it also contains methods to add
 * relationships, like uses/realize relationships.
 *
 * @version $Id: XMIFactory.java,v 1.12 2006/02/14 14:59:49 moroff Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class XMIFactory {
	protected PetalFile tree;
	protected XMIGenerator gen;
	private static MTaggedValue derivedTaggedValue = new MTaggedValueImpl();

	static {
		derivedTaggedValue.setTag("derived");
		derivedTaggedValue.setValue("true");
	}

	protected XMIFactory() {
	}

	/**
	 * create me with the given PetalFile
	 * 
	 * @param tree
	 * @param gen
	 */
	protected XMIFactory(PetalFile tree, XMIGenerator gen) {
		this.tree = tree;
		this.gen = gen;
	}

	public MModel createModel() {
		MModel model = new MModelImpl();
		model.setName(tree.getModelName());
		return model;
	}

	private HashMap<String, MStereotype> stereo_types = new HashMap<String, MStereotype>(); // Map
																																													// <String,
																																													// stereotype>

	protected final MStereotype getStereotype(String stereo) {
		MStereotype s = (MStereotype) stereo_types.get(stereo);

		if (s == null) {
			s = new MStereotypeImpl();
			s.setName(stereo);
			gen.getPackage().addOwnedElement(s);
			stereo_types.put(stereo, s);
		}

		return s;
	}

	public MPackage createPackage(ClassCategory cat) {
		MPackage pack = new MPackageImpl();
		pack.setName(cat.getNameParameter());
		pack.setUUID(cat.getQuid());
		setDocumentation(cat, pack);
		setStereotype(cat, pack);
		return pack;
	}

	public MPackage createPackage(SubSystem sys) {
		MPackage pack = new MSubsystemImpl();
		pack.setName(sys.getNameParameter());
		pack.setUUID(sys.getQuid());

		return pack;
	}

	protected void setStereotype(StereoTyped s, MModelElement e) {
		String stereo = s.getStereotype();

		if (stereo != null)
			e.setStereotype(getStereotype(stereo));
	}

	protected void setVisibility(AccessQualified a, MModelElement e) {
		String acc = a.getExportControl();

		if (acc == null)
			acc = "public";
		else
			acc = acc.toLowerCase();

		MVisibilityKind kind = MVisibilityKind.forName(acc);

		if (kind == null)
			throw new RuntimeException("Can't map access qualifier: " + acc);

		e.setVisibility(kind);
	}

	protected void setConcurrency(Operation op, MOperation o) {
		String acc = op.getConcurrency();

		if (acc == null)
			acc = "sequential";
		else
			acc = acc.toLowerCase();

		MCallConcurrencyKind kind = MCallConcurrencyKind.forName(acc);

		if (kind == null)
			throw new RuntimeException("Can't map concurrency: " + acc);

		o.setConcurrency(kind);
	}

	/**
	 * @return MInterface, MClass, MAssociationClass or MActor
	 */
	public MClassifier createClass(cb.petal.Class clazz) {
		MClassifier cl;

		/*
		 * ArgoUML/Poseidon can't display/use Actors/AssociationClass correctly
		 */
		if (clazz.isInterface())
			cl = new MInterfaceImpl();
		else if (clazz.isActor())
			cl = new MActorImpl();
		else if (clazz.isAssociationClass())
			cl = new MAssociationClassImpl();
		else
			cl = new MClassImpl();

		cl.setName(clazz.getNameParameter());
		cl.setUUID(clazz.getQuid());

		setDocumentation(clazz, cl);
		setVisibility(clazz, cl);
		if (clazz.getPropertyAsBoolean("abstract"))
			cl.setAbstract(true);
		if (clazz instanceof ClassUtility)
			cl.setStereotype(getStereotype("utility"));
		else if (!(clazz.isInterface() || clazz.isActor()))
			setStereotype(clazz, cl);

		return cl;
	}

	private void setDocumentation(Documented object, MModelElement cl) {
		MTaggedValue doc = new MTaggedValueImpl();
		doc.setTag("documentation");
		doc.setValue(object.getDocumentation());
		cl.addTaggedValue(doc);
	}

	public MClassifier createUseCase(UseCase caze) {
		MClassifier cl = new MUseCaseImpl();

		cl.setName(caze.getNameParameter());
		cl.setUUID(caze.getQuid());

		setStereotype(caze, cl);

		return cl;
	}

	public MClassifier createComponent(Module module) {
		MClassifier cl = new MComponentImpl();

		cl.setName(module.getNameParameter());
		cl.setUUID(module.getQuid());

		setStereotype(module, cl);

		return cl;
	}

	public MClassifier createSubSystem(SubSystem sys) {
		MClassifier cl = new MSubsystemImpl();

		cl.setName(sys.getNameParameter());
		cl.setUUID(sys.getQuid());

		setStereotype(sys, cl);

		return cl;
	}

	/**
	 * Create classifier for data type not directly contained in the model such as
	 * attribute types like "int".
	 */
	public MClassifier createBasicType(String name) {
		MClassifier clazz = new MDataTypeImpl();
		clazz.setName(name);
		clazz.setStereotype(getStereotype("utility"));

		return clazz;
	}

	/**
	 * Look for classifier in model or create a new data type.
	 */
	protected MClassifier getClassifierFor(String type) {
		MClassifier clazz = (MClassifier) gen
				.searchElement(type, MClassifier.class);

		if (clazz == null) {
			clazz = createBasicType(type);
			gen.getModel().addOwnedElement(clazz);
		}

		return clazz;
	}

	public MAttribute createAttribute(ClassAttribute attr) {
		MAttribute a = new MAttributeImpl();
		a.setName(attr.getNameParameter());
		a.setUUID(attr.getQuid());
		setVisibility(attr, a);
		setStereotype(attr, a);
		setDocumentation(attr, a);
		a.setOwnerScope(attr.getStatic() ? MScopeKind.CLASSIFIER
				: MScopeKind.INSTANCE);

		if (attr.getType() != null) {
			String type = attr.getType();

			a.setType(getClassifierFor(type));
		}

		String init = attr.getInitialValue();

		if (init != null)
			a.setInitialValue(new MExpression("Java", init));

		if (attr.getPropertyAsBoolean("derived") == true) {
			addTaggedValue(a, "derived", "true");
		}

		return a;
	}

	protected void addTaggedValue(MModelElement element, String tag, String value) {
		MTaggedValue taggedValue = new MTaggedValueImpl();
		taggedValue.setTag(tag);
		taggedValue.setValue(value);
		element.addTaggedValue(taggedValue);
	}

	public MOperation createOperation(Operation op) {
		MOperation m = new MOperationImpl();

		m.setName(op.getNameParameter());
		m.setUUID(op.getQuid());

		setVisibility(op, m);
		setStereotype(op, m);
		setConcurrency(op, m);
		setDocumentation(op, m);

		String type = (op.getResult() == null) ? "void" : op.getResult();

		MParameter ret = new MParameterImpl();
		ret.setName("return");
		ret.setType(getClassifierFor(type));
		ret.setKind(MParameterDirectionKind.RETURN);
		m.addParameter(ret);

		ArrayList params = new ArrayList();

		if (op.getParameters() != null) {
			for (Iterator i = op.getParameters().getElements().iterator(); i
					.hasNext();) {
				cb.petal.Parameter p = (cb.petal.Parameter) i.next();

				String name = p.getNameParameter();
				type = p.getType();

				// Typical erroneous "Address a", needs to be split

				if (type == null) {
					int index = name.indexOf(' ');

					if (index > 0) {
						type = name.substring(0, index);
						name = name.substring(index + 1).trim();
					} else
						type = "int";
				}

				MParameter param = new MParameterImpl();
				param.setName(name);
				param.setType(getClassifierFor(type));
				param.setKind(MParameterDirectionKind.IN);
				m.addParameter(param);

			}
		}
		// Create Exceptions
		if (op.getExceptions() != null) {
			for (Iterator ie = op.getExceptions().getElements().iterator(); ie
					.hasNext();) {
				StringLiteral expName = (StringLiteral) ie.next();
				Pattern pattern = Pattern.compile("([^\\[]+)\\[(\\w+)\\]");
				Matcher matcher = pattern.matcher(expName.getValue());
				if (matcher.matches()) {
					String exp = matcher.group(1);
					MClassifier expClass = (MClassifier) getClassifierFor(exp);
					MSignal signal = new MSignalImpl();
					signal.setNamespace(expClass.getNamespace());
					signal.setName(expClass.getName());
					m.addRaisedSignal(signal);
				}
			}
		}

		return m;
	}

	private void setupRelationship(MDependency g, Relationship rel) {
		MModelElement client = gen.getContainingClassifier(rel);
		MClassifier supplier = gen.getClassifier(rel.getQuidu());

		// if client not found as class try as package
		if (client == null)
			client = gen.getContainingPackage(rel);
		g.addSupplier(supplier);
		g.addClient(client);
		g.setUUID(rel.getQuid());

		setStereotype(rel, g);

		if (rel.getLabel() != null)
			g.setName(rel.getLabel());
	}

	public MGeneralization createGeneralization(InheritanceRelationship rel) {
		MGeneralization g = new MGeneralizationImpl();
		MClassifier client = gen.getContainingClassifier(rel);
		MClassifier supplier = gen.getClassifier(rel.getQuidu());

		g.setParent(supplier);
		g.setChild(client);
		g.setUUID(rel.getQuid());

		return g;
	}

	public MAbstraction createRealization(RealizeRelationship rel) {
		MAbstraction g = new MAbstractionImpl();
		setupRelationship(g, rel);
		g.setStereotype(getStereotype("realize"));
		return g;
	}

	public MUsage createUsage(UsesRelationship rel) {
		MUsage g = new MUsageImpl();
		setupRelationship(g, rel);
		return g;
	}

	public MDependency createDependency(DependencyRelationship rel) {
		MDependency g = new MDependencyImpl();
		setupRelationship(g, rel);
		return g;
	}

	private static String map(String number) {
		if ("n".equals(number.toLowerCase()) || "*".equals(number))
			return "" + MMultiplicity.N;
		else
			return number;
	}

	protected MMultiplicity getMultiplicityFor(Role role) {
		int from = 1, to = 1;

		if (role.getClientCardinality() != null) {
			String card = role.getClientCardinality().getStringValue();
			StringTokenizer tok = new StringTokenizer(card, ".,");

			try {
				from = Integer.parseInt(map(tok.nextToken()));

				if (tok.hasMoreTokens())
					to = Integer.parseInt(map(tok.nextToken()));
				else
					to = from;
			} catch (Exception e) {
				throw new RuntimeException("Invalid cardinality: " + card);
			}
		} else {
			Role other = role.getOtherRole();

			if (other.isAggregate()) {
				from = 0;
				to = MMultiplicity.N;
			}
		}

		if (from == MMultiplicity.N)
			from = 0;

		return new MMultiplicity(from, to);
	}

	protected MAssociationEnd getRoleFor(Role role) {
		MClassifier clazz = gen.getClassifier(role.getQuidu());
		MAssociationEnd ae = createAssociationEnd(role);

		ae.setType(clazz);
		return ae;
	}

	/**
	 * @param role
	 * @return the association end which was just created
	 */
	public MAssociationEnd createAssociationEnd(Role role) {
		MAssociationEnd ae = new MAssociationEndImpl();
		ae.setNavigable(role.isNavigable());
		ae.setMultiplicity(getMultiplicityFor(role));
		if (!role.getRoleName().startsWith("$")) // Anonymous name
			ae.setName(role.getRoleName());
		if (role.isAggregation())
			ae.setAggregation(MAggregationKind.AGGREGATE);
		else if (role.isComposition())
			ae.setAggregation(MAggregationKind.COMPOSITE);
		else
			ae.setAggregation(MAggregationKind.NONE);
		String c = role.getConstraints();
		if (c != null) {
			MConstraint constr = new MConstraintImpl();
			MBooleanExpression body = new MBooleanExpression("Java", c);
			constr.setBody(body);
			ae.addConstraint(constr);
			gen.getPackage().addOwnedElement(constr);
		}

		PetalNode qualifierNode = role.getProperty("keys");
		if (qualifierNode != null) {
			List qualifierList = (List) qualifierNode;
			for (Iterator iq = qualifierList.getElements().iterator(); iq.hasNext();) {
				ClassAttribute attribute = (ClassAttribute) iq.next();

				MAttribute attr = createAttribute(attribute);
				ae.addQualifier(attr);
			}
		}
		return ae;
	}

	protected void setupAssociation(Association assoc, MAssociation a) {
		Role first = assoc.getFirstRole();
		Role second = assoc.getSecondRole();

		a.addConnection(getRoleFor(first));
		a.addConnection(getRoleFor(second));
	}

	public MAssociation createAssociation(Association assoc) {
		MAssociation a = new MAssociationImpl();

		if (!assoc.getNameParameter().startsWith("$")) // Anonymous name
			a.setName(assoc.getNameParameter());

		setupAssociation(assoc, a);
		setDocumentation(assoc, a);
		setStereotype(assoc, a);

		if (assoc.getPropertyAsBoolean("derived") == true) {
			addTaggedValue(a, "derived", "true");
		}

		return a;
	}

	/*************************** Utility method *******************************/

	/**
	 * Convert Rose identifier to normal one.
	 */
	protected String makeName(String name) {
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
	 * Convert fully qualified rose name (with "foo::bar")
	 * 
	 * @return tuple (class name, package name)
	 */
	protected String[] makeNames(String qual) {
		String name, pack;
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

		return new String[] { name, pack };
	}
}
