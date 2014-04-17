package test;

import cb.petal.PetalFile;
import cb.parser.PetalParser;
import cb.xmi.XMIGenerator;

/**
 * Convert Rose file into XMI format. You'll need to install
 * <a href="http://nsuml.sourceforge.net/">NSUML</a> to run this example.
 * It also shows how to reduce memory consumption by omitting certain nodes
 * when parsing the petal file.
 *
 * @version $Id: Test4.java,v 1.5 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Test4 {
  public static void main(String[] args) throws Exception {
    try {
      Class.forName("ru.novosoft.uml.MBase");
    } catch(ClassNotFoundException e) {
      System.err.println("Please install NSUML first: http://nsuml.sourceforge.net/");
      return;
    }

    PetalParser parser = PetalParser.createParser(args[0]);

    // Abandon all diagrams since they're not used by the generator anyway
    // This saves us a lot of memory
    parser.setIgnoredNodes(new Class[] { cb.petal.Diagram.class,
      cb.petal.View.class });

    PetalFile    tree = parser.parse();
    XMIGenerator gen  = new XMIGenerator(tree, args[1]);
    
    gen.start();
    gen.dump();
  }
}

