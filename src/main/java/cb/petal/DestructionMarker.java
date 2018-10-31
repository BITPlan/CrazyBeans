package cb.petal;

import java.util.Collection;

public class DestructionMarker extends View{

  protected DestructionMarker(PetalNode parent, String name, Collection params,
      int tag) {
    super(parent, "DestructionMarker", params, tag);
  }
  
  public DestructionMarker() {
    super("DestructionMarker");
  }

  @Override
  public void accept(Visitor v) {
    v.visit(this);
  }

}
