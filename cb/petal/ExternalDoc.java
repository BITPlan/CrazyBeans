package cb.petal;
import java.util.Collection;

/**
 * Refer to external documentation, i.e. a file or URL
 *
 * @version $Id: ExternalDoc.java,v 1.8 2001/07/09 07:48:52 dahm Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ExternalDoc extends PetalObject {
  public ExternalDoc(PetalNode parent, Collection params) {
    super(parent, "external_doc", params);
  }

  public ExternalDoc() {
    super("external_doc");
  }

  public String getExternalDocUrl() {
    return getPropertyAsString("external_doc_url");
  }

  public void setExternalDocUrl(String o) {
    defineProperty("external_doc_url", o);
  }

  public String getExternalDocPath() {
    return getPropertyAsString("external_doc_path");
  }

  public void setExternalDocPath(String o) {
    defineProperty("external_doc_path", o);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
