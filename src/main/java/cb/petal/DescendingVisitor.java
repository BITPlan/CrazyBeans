package cb.petal;
import java.util.Iterator;

/**
 * By default visits petal tree with DFS.
 *
 * @version $Id: DescendingVisitor.java,v 1.11 2002/07/23 19:56:25 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class DescendingVisitor implements Visitor {
  public DescendingVisitor() {}

  public void visit(PetalFile obj) {
    obj.getPetal().accept(this);
    obj.getDesign().accept(this);
  }

  public void visit(List list) {
    for(Iterator i = list.getElements().iterator(); i.hasNext(); )
      ((PetalNode)i.next()).accept(this);
  }

  public void visit(Value value) {
    value.getValue().accept(this);
  }

  public void visitObject(PetalObject obj) {
    for(Iterator i = obj.getPropertyList().iterator(); i.hasNext(); )
      ((PetalNode)i.next()).accept(this);
  }

  public void visit(Class obj) { visitObject(obj); }
  public void visit(SemanticInfo obj) { visitObject(obj); }

  public void visit(Font obj) { visitObject(obj); }

  public void visit(Design obj) { visitObject(obj); }

  public void visit(Petal obj) { visitObject(obj); }

  public void visit(ClassAttribute obj) { visitObject(obj); }

  public void visit(LogicalCategory obj) { visitObject(obj); }
  public void visit(ClassCategory obj) { visitObject(obj); }
  public void visit(UseCaseCategory obj) { visitObject(obj); }

  public void visit(Operation obj) { visitObject(obj); }

  public void visit(Defaults obj) { visitObject(obj); }

  public void visit(Attribute obj) { visitObject(obj); }

  public void visit(Processes obj) {  visitObject(obj); }

  public void visit(Properties obj) { visitObject(obj); }

  public void visit(SubSystem obj) { visitObject(obj); }

  public void visit(UseCase obj) { visitObject(obj); }

  public void visit(UseCaseDiagram obj) { visitObject(obj); }

  public void visit(Compartment obj) { visitObject(obj); }

  public void visit(ItemLabel obj) { visitObject(obj); }

  public void visit(Label obj) { visitObject(obj); }

  public void visit(SimpleViewObject obj) { visitObject(obj); }

  public void visit(ClassView obj) { visitObject(obj); }

  public void visit(CategoryView obj) { visitObject(obj); }

  public void visit(Parameter obj) { visitObject(obj); }

  public void visit(Association obj) { visitObject(obj); }

  public void visit(Role obj) { visitObject(obj); }

  public void visit(ModView obj) { visitObject(obj); }

  public void visit(UsesRelationship obj) { visitObject(obj); }

  public void visit(ClassDiagram obj) { visitObject(obj); }

  public void visit(DependencyRelationship obj) { visitObject(obj); }

  public void visit(ImportView obj) { visitObject(obj); }

  public void visit(InheritView obj) { visitObject(obj); }

  public void visit(InheritanceRelationship obj) { visitObject(obj); }

  public void visit(InstantiateView obj) { visitObject(obj); }

  public void visit(InstantiationRelationship obj) { visitObject(obj); }

  public void visit(ModVisView obj) { visitObject(obj); }

  public void visit(ModuleDiagram obj) { visitObject(obj); }

  public void visit(ProcessDiagram obj) { visitObject(obj); }

  public void visit(SubSysView obj) { visitObject(obj); }

  public void visit(VisibilityRelationship obj) { visitObject(obj); }

  public void visit(Module obj) { visitObject(obj); }

  public void visit(RoleView obj) { visitObject(obj); }

  public void visit(SegLabel obj) { visitObject(obj); }

  public void visit(AssociationViewNew obj) { visitObject(obj); }

  public void visit(NoteView obj) { visitObject(obj); }

  public void visit(InheritTreeView obj) { visitObject(obj); }

  public void visit(AttachView obj) { visitObject(obj); }

  public void visit(AssocAttachView obj) { visitObject(obj); }

  public void visit(Mechanism obj) { visitObject(obj); }

  public void visit(cb.petal.Object obj) { visitObject(obj); }

  public void visit(Message obj) { visitObject(obj); }

  public void visit(Event obj) { visitObject(obj); }

  public void visit(StateView obj) { visitObject(obj); }

  public void visit(TransView obj) { visitObject(obj); }

  public void visit(ActionTime obj) { visitObject(obj); }

  public void visit(StateDiagram obj) { visitObject(obj); }

  public void visit(Link obj) { visitObject(obj); }

  public void visit(State obj) { visitObject(obj); }

  public void visit(StateTransition obj) { visitObject(obj); }

  public void visit(SendEvent obj) { visitObject(obj); }

  public void visit(Action obj) { visitObject(obj); }

  public void visit(StateMachine obj) { visitObject(obj); }

  public void visit(UsesView obj) { visitObject(obj); }

  public void visit(InteractionDiagram obj) { visitObject(obj); }

  public void visit(InterObjView obj) { visitObject(obj); }

  public void visit(MessView obj) { visitObject(obj); }

  public void visit(UseCaseView obj) { visitObject(obj); }

  public void visit(LinkSelfView obj) { visitObject(obj); }

  public void visit(LinkView obj) { visitObject(obj); }

  public void visit(DataFlowView obj) { visitObject(obj); }

  public void visit(ObjectView obj) { visitObject(obj); }

  public void visit(FocusOfControl obj) { visitObject(obj); }

  public void visit(SelfMessView obj) { visitObject(obj); }

  public void visit(InterMessView obj) { visitObject(obj); }

  public void visit(ObjectDiagram obj) { visitObject(obj); }

  public void visit(RealizeView obj) { visitObject(obj); }

  public void visit(ExternalDoc obj) { visitObject(obj); }

  public void visit(ClassInstanceView obj) { visitObject(obj); }

  public void visit(Processor obj) { visitObject(obj); }

  public void visit(ConnectionRelationship obj) { visitObject(obj); }

  public void visit(RealizeRelationship obj) { visitObject(obj); }

  public void visit(Process obj) { visitObject(obj); }

  public void visit(Device obj) { visitObject(obj); }

  public void visit(ProcessorView obj) { visitObject(obj); }

  public void visit(DeviceView obj) { visitObject(obj); }

  public void visit(ConnectionView obj) { visitObject(obj); }

  public void visit(DependencyView obj) { visitObject(obj); }

  public void visit(InterfaceView obj) { visitObject(obj); }

  public void visit(ModuleVisibilityRelationship obj) { visitObject(obj); }

  public void visit(Swimlane obj) { visitObject(obj); }

  public void visit(Partition obj) { visitObject(obj); }

  public void visit(ActivityStateView obj) { visitObject(obj); }

  public void visit(DecisionView obj) { visitObject(obj); }

  public void visit(SynchronizationView obj) { visitObject(obj); }

  public void visit(ActivityDiagram obj) { visitObject(obj); }

  public void visit(ActivityState obj) { visitObject(obj); }

  public void visit(Decision obj) { visitObject(obj); }

  public void visit(SynchronizationState obj) { visitObject(obj); }

  // Plain stuff

  public void visit(SimpleObject obj) { visitObject(obj); }

  public void visit(StringLiteral obj) { }

  public void visit(BooleanLiteral obj) { }

  public void visit(FloatLiteral obj) { }

  public void visit(IntegerLiteral obj) { }

  public void visit(Tag tag) { }

  public void visit(Location loc) { }

  public void visit(Tuple tuple) { }
}

