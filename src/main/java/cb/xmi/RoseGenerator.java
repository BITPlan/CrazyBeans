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
package cb.xmi;

import cb.parser.*;

import cb.util.*;

import java.io.*;

import cb.petal.ClassCategory;
import cb.petal.PetalFile;

import java.util.*;

import ru.novosoft.uml.*;
import ru.novosoft.uml.foundation.core.*;
import ru.novosoft.uml.model_management.*;
import ru.novosoft.uml.behavior.state_machines.*;
import ru.novosoft.uml.behavior.activity_graphs.*;
import ru.novosoft.uml.behavior.collaborations.*;
import ru.novosoft.uml.behavior.common_behavior.*;
import ru.novosoft.uml.behavior.use_cases.*;
import ru.novosoft.uml.foundation.extension_mechanisms.*;

/**
 * Convert an  <a href="http://xml.coverpages.org/xmi.html">XMI</a>
 * file into a Rose Petal file.
 *
 * @version $Id: RoseGenerator.java,v 1.1 2001/11/30 12:19:29 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class RoseGenerator extends GeneratorVisitor implements Generator {
  

  /** Which factory to use
   */
  protected static PetalObjectFactory factory = PetalObjectFactory.getInstance();

  /** The XMI model being set up
   */
  protected MModel     model;

  /**  Stack<ClassCategory>
   */
  private Stack packages = new Stack();

  /** The current package level (may be nested)
   */
  private ClassCategory pack;

  /** Use that until NSUML has visitors.
   */
  private Dispatcher d = new Dispatcher(this);

  /** Register created objects by the quid of the petal object.
   */
  protected HashMap quid_map = new HashMap(); // Map<quid, MClassifier>

  protected HashMap package_map = new HashMap(); // Map<ClassCategory, MPackage>

  /**
   * @param model the XMI model to convert
   * @param dump where to dump the generated petal file
   */
  public RoseGenerator(MModel model, String dump) {
    this.setDump(dump);
    this.model = model;

    setTree(factory.createModel());
  }

  /** Start generation of Petal file.
   */
  public void start() {
    d.accept(model); // Should read: model.accept(this) ...
  }

  public void dump() throws IOException {
    PrintStream os = new PrintStream(new FileOutputStream(getDump()));
    getTree().accept(new PrintVisitor(os));
    os.close();
  }

  /** 
   * alias for getTree - just delegates this call
   * @return generated model
   */
  public PetalFile getModel() {
    return getTree();
  }

  public void visit(MExtension obj) {}
  public void visit(MFactory obj) {}
  public void visit(MActionState obj) {}
  public void visit(MActivityGraph obj) {}
  public void visit(MCallState obj) {}
  public void visit(MClassifierInState obj) {}
  public void visit(MObjectFlowState obj) {}
  public void visit(MPartition obj) {}
  public void visit(MSubactivityState obj) {}
  public void visit(MAssociationEndRole obj) {}
  public void visit(MAssociationRole obj) {}
  public void visit(MClassifierRole obj) {}
  public void visit(MCollaboration obj) {}
  public void visit(MInteraction obj) {}
  public void visit(MMessage obj) {}
  public void visit(MAction obj) {}
  public void visit(MActionSequence obj) {}
  public void visit(MArgument obj) {}
  public void visit(MAttributeLink obj) {}
  public void visit(MCallAction obj) {}
  public void visit(MComponentInstance obj) {}
  public void visit(MCreateAction obj) {}
  public void visit(MDataValue obj) {}
  public void visit(MDestroyAction obj) {}
  public void visit(MException obj) {}
  public void visit(MInstance obj) {}
  public void visit(MLinkEnd obj) {}
  public void visit(MLink obj) {}
  public void visit(MLinkObject obj) {}
  public void visit(MNodeInstance obj) {}
  public void visit(MObject obj) {}
  public void visit(MReception obj) {}
  public void visit(MReturnAction obj) {}
  public void visit(MSendAction obj) {}
  public void visit(MSignal obj) {}
  public void visit(MStimulus obj) {}
  public void visit(MTerminateAction obj) {}
  public void visit(MUninterpretedAction obj) {}
  public void visit(MCallEvent obj) {}
  public void visit(MChangeEvent obj) {}
  public void visit(MCompositeState obj) {}
  public void visit(MFinalState obj) {}
  public void visit(MGuard obj) {}
  public void visit(MPseudostate obj) {}
  public void visit(MSignalEvent obj) {}
  public void visit(MSimpleState obj) {}
  public void visit(MState obj) {}
  public void visit(MStateMachine obj) {}
  public void visit(MStubState obj) {}
  public void visit(MSubmachineState obj) {}
  public void visit(MSynchState obj) {}
  public void visit(MTimeEvent obj) {}
  public void visit(MTransition obj) {}
  public void visit(MActor obj) {}
  public void visit(MExtend obj) {}
  public void visit(MExtensionPoint obj) {}
  public void visit(MInclude obj) {}
  public void visit(MUseCase obj) {}
  public void visit(MUseCaseInstance obj) {}
  public void visit(MAbstraction obj) {}
  public void visit(MAssociationClass obj) {}
  public void visit(MAssociationEnd obj) {}
  public void visit(MAssociation obj) {}
  public void visit(MAttribute obj) {}
  public void visit(MBinding obj) {}
  public void visit(MClass obj) {}
  public void visit(MClassifier obj) {}
  public void visit(MComment obj) {}
  public void visit(MComponent obj) {}
  public void visit(MConstraint obj) {}
  public void visit(MDataType obj) {}
  public void visit(MDependency obj) {}
  public void visit(MElementResidence obj) {}
  public void visit(MFlow obj) {}
  public void visit(MGeneralization obj) {}
  public void visit(MInterface obj) {}
  public void visit(MMethod obj) {}
  public void visit(MNamespace obj) {}
  public void visit(MNode obj) {}
  public void visit(MOperation obj) {}
  public void visit(MParameter obj) {}
  public void visit(MPermission obj) {}
  public void visit(MPresentationElement obj) {}
  public void visit(MRelationship obj) {}
  public void visit(MTemplateParameter obj) {}
  public void visit(MUsage obj) {}
  public void visit(MElementImport obj) {}

  public void visit(MModel obj) {
    getTree().setModelName(obj.getName());

    for(Iterator i = obj.getOwnedElements().iterator(); i.hasNext(); )
      d.accept((MModelElement)i.next());
  }

  public void visit(MPackage obj) {}
  public void visit(MSubsystem obj) {}
  public void visit(MStereotype obj) {}
  public void visit(MTaggedValue obj) {}

}


