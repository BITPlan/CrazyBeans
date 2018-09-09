/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.xmi;

import ru.novosoft.uml.*;
import ru.novosoft.uml.foundation.core.*;
import ru.novosoft.uml.model_management.*;
import ru.novosoft.uml.behavior.state_machines.*;
import ru.novosoft.uml.behavior.activity_graphs.*;
import ru.novosoft.uml.behavior.collaborations.*;
import ru.novosoft.uml.behavior.common_behavior.*;
import ru.novosoft.uml.behavior.use_cases.*;
import ru.novosoft.uml.foundation.extension_mechanisms.*;

class Dispatcher {
  private RoseGenerator gen;

  Dispatcher(RoseGenerator gen) {
    this.gen = gen;
  }

  void accept(MBase obj) {
    if(obj instanceof MExtension)
      gen.visit((MExtension)obj);
    else if(obj instanceof MActionState)
      gen.visit((MActionState)obj);
    else if(obj instanceof MActivityGraph)
      gen.visit((MActivityGraph)obj);
    else if(obj instanceof MCallState)
      gen.visit((MCallState)obj);
    else if(obj instanceof MClassifierInState)
      gen.visit((MClassifierInState)obj);
    else if(obj instanceof MObjectFlowState)
      gen.visit((MObjectFlowState)obj);
    else if(obj instanceof MPartition)
      gen.visit((MPartition)obj);
    else if(obj instanceof MSubactivityState)
      gen.visit((MSubactivityState)obj);
    else if(obj instanceof MAssociationEndRole)
      gen.visit((MAssociationEndRole)obj);
    else if(obj instanceof MAssociationRole)
      gen.visit((MAssociationRole)obj);
    else if(obj instanceof MClassifierRole)
      gen.visit((MClassifierRole)obj);
    else if(obj instanceof MCollaboration)
      gen.visit((MCollaboration)obj);
    else if(obj instanceof MInteraction)
      gen.visit((MInteraction)obj);
    else if(obj instanceof MMessage)
      gen.visit((MMessage)obj);
    else if(obj instanceof MAction)
      gen.visit((MAction)obj);
    else if(obj instanceof MActionSequence)
      gen.visit((MActionSequence)obj);
    else if(obj instanceof MArgument)
      gen.visit((MArgument)obj);
    else if(obj instanceof MAttributeLink)
      gen.visit((MAttributeLink)obj);
    else if(obj instanceof MCallAction)
      gen.visit((MCallAction)obj);
    else if(obj instanceof MComponentInstance)
      gen.visit((MComponentInstance)obj);
    else if(obj instanceof MCreateAction)
      gen.visit((MCreateAction)obj);
    else if(obj instanceof MDataValue)
      gen.visit((MDataValue)obj);
    else if(obj instanceof MDestroyAction)
      gen.visit((MDestroyAction)obj);
    else if(obj instanceof MException)
      gen.visit((MException)obj);
    else if(obj instanceof MInstance)
      gen.visit((MInstance)obj);
    else if(obj instanceof MLinkEnd)
      gen.visit((MLinkEnd)obj);
    else if(obj instanceof MLink)
      gen.visit((MLink)obj);
    else if(obj instanceof MLinkObject)
      gen.visit((MLinkObject)obj);
    else if(obj instanceof MNodeInstance)
      gen.visit((MNodeInstance)obj);
    else if(obj instanceof MObject)
      gen.visit((MObject)obj);
    else if(obj instanceof MReception)
      gen.visit((MReception)obj);
    else if(obj instanceof MReturnAction)
      gen.visit((MReturnAction)obj);
    else if(obj instanceof MSendAction)
      gen.visit((MSendAction)obj);
    else if(obj instanceof MSignal)
      gen.visit((MSignal)obj);
    else if(obj instanceof MStimulus)
      gen.visit((MStimulus)obj);
    else if(obj instanceof MTerminateAction)
      gen.visit((MTerminateAction)obj);
    else if(obj instanceof MUninterpretedAction)
      gen.visit((MUninterpretedAction)obj);
    else if(obj instanceof MChangeEvent)
      gen.visit((MChangeEvent)obj);
    else if(obj instanceof MCallEvent)
      gen.visit((MCallEvent)obj);
    else if(obj instanceof MCompositeState)
      gen.visit((MCompositeState)obj);
    else if(obj instanceof MFinalState)
      gen.visit((MFinalState)obj);
    else if(obj instanceof MGuard)
      gen.visit((MGuard)obj);
    else if(obj instanceof MPseudostate)
      gen.visit((MPseudostate)obj);
    else if(obj instanceof MSignalEvent)
      gen.visit((MSignalEvent)obj);
    else if(obj instanceof MSimpleState)
      gen.visit((MSimpleState)obj);
    else if(obj instanceof MState)
      gen.visit((MState)obj);
    else if(obj instanceof MStateMachine)
      gen.visit((MStateMachine)obj);
    else if(obj instanceof MStubState)
      gen.visit((MStubState)obj);
    else if(obj instanceof MSubmachineState)
      gen.visit((MSubmachineState)obj);
    else if(obj instanceof MSynchState)
      gen.visit((MSynchState)obj);
    else if(obj instanceof MTimeEvent)
      gen.visit((MTimeEvent)obj);
    else if(obj instanceof MTransition)
      gen.visit((MTransition)obj);
    else if(obj instanceof MActor)
      gen.visit((MActor)obj);
    else if(obj instanceof MExtend)
      gen.visit((MExtend)obj);
    else if(obj instanceof MExtensionPoint)
      gen.visit((MExtensionPoint)obj);
    else if(obj instanceof MInclude)
      gen.visit((MInclude)obj);
    else if(obj instanceof MUseCase)
      gen.visit((MUseCase)obj);
    else if(obj instanceof MUseCaseInstance)
      gen.visit((MUseCaseInstance)obj);
    else if(obj instanceof MAbstraction)
      gen.visit((MAbstraction)obj);
    else if(obj instanceof MAssociationClass)
      gen.visit((MAssociationClass)obj);
    else if(obj instanceof MAssociationEnd)
      gen.visit((MAssociationEnd)obj);
    else if(obj instanceof MAssociation)
      gen.visit((MAssociation)obj);
    else if(obj instanceof MAttribute)
      gen.visit((MAttribute)obj);
    else if(obj instanceof MBinding)
      gen.visit((MBinding)obj);
    else if(obj instanceof MClass)
      gen.visit((MClass)obj);
    else if(obj instanceof MClassifier)
      gen.visit((MClassifier)obj);
    else if(obj instanceof MComment)
      gen.visit((MComment)obj);
    else if(obj instanceof MComponent)
      gen.visit((MComponent)obj);
    else if(obj instanceof MConstraint)
      gen.visit((MConstraint)obj);
    else if(obj instanceof MDataType)
      gen.visit((MDataType)obj);
    else if(obj instanceof MDependency)
      gen.visit((MDependency)obj);
    else if(obj instanceof MElementResidence)
      gen.visit((MElementResidence)obj);
    else if(obj instanceof MFlow)
      gen.visit((MFlow)obj);
    else if(obj instanceof MGeneralization)
      gen.visit((MGeneralization)obj);
    else if(obj instanceof MInterface)
      gen.visit((MInterface)obj);
    else if(obj instanceof MMethod)
      gen.visit((MMethod)obj);
    else if(obj instanceof MNamespace)
      gen.visit((MNamespace)obj);
    else if(obj instanceof MNode)
      gen.visit((MNode)obj);
    else if(obj instanceof MOperation)
      gen.visit((MOperation)obj);
    else if(obj instanceof MParameter)
      gen.visit((MParameter)obj);
    else if(obj instanceof MPermission)
      gen.visit((MPermission)obj);
    else if(obj instanceof MRelationship)
      gen.visit((MRelationship)obj);
    else if(obj instanceof MTemplateParameter)
      gen.visit((MTemplateParameter)obj);
    else if(obj instanceof MUsage)
      gen.visit((MUsage)obj);
    else if(obj instanceof MElementImport)
      gen.visit((MElementImport)obj);
    else if(obj instanceof MModel)
      gen.visit((MModel)obj);
    else if(obj instanceof MPackage)
      gen.visit((MPackage)obj);
    else if(obj instanceof MSubsystem)
      gen.visit((MSubsystem)obj);
    else if(obj instanceof MTaggedValue)
      gen.visit((MTaggedValue)obj);
    else if(obj instanceof MStereotype)
      gen.visit((MStereotype)obj);
  }
}
