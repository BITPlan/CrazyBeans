/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.util;

import cb.petal.AssocAttachView;
import cb.petal.Association;
import cb.petal.AssociationViewNew;
import cb.petal.AttachView;
import cb.petal.ClassView;
import cb.petal.DescendingVisitor;
import cb.petal.InheritView;
import cb.petal.ItemLabel;
import cb.petal.NoteView;
import cb.petal.Operation;
import cb.petal.PetalFile;
import cb.petal.PetalNode;
import cb.petal.RealizeView;
import cb.petal.SegLabel;
import cb.petal.UseCaseView;
import cb.petal.UsesView;
import cb.parser.*;

import java.io.*;

/**
 * Create serialized templates.
 *
 * @version $Id: Dump.java,v 1.10 2001/11/01 15:56:49 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class Dump {
  private static class Traverser extends DescendingVisitor {
    public void visit(cb.petal.Class obj) {
      if (obj.getNameParameter().equals("SomeClass")) {
        obj.setParent(null);

        dump("Class", obj);
      }

      visitObject(obj);
    }

    public void visit(ClassView obj) {
      if (obj.getQualifiedNameParameter().equals(
          "Logical View::templates::SomeClass")) {
        obj.setParent(null);
        dump("ClassView", obj);
      } else if (obj.getQualifiedNameParameter().equals(
          "Logical View::templates::Actor")) {
        obj.setParent(null);
        dump("ActorView", obj);
      } else if (obj.getQualifiedNameParameter().equals(
          "Logical View::templates::Interface")) {
        obj.setParent(null);
        dump("StereotypeView", obj);
      }

      visitObject(obj);
    }

    public void visit(UseCaseView obj) {
      if (obj.getQualifiedNameParameter().equals("Use Case View::Examination")) {
        obj.setParent(null);
        dump("UseCaseView", obj);
      } else if (obj.getQualifiedNameParameter().equals(
          "Use Case View::Lecture")) {
        obj.setParent(null);
        dump("UseCaseStereotypeView", obj);
      }
    }

    public void visit(InheritView obj) {
      if (obj.getQuidu().equals("3B29C6640186")) {
        obj.setParent(null);
        dump("InheritView", obj);
      }
    }

    public void visit(RealizeView obj) {
      if (obj.getQuidu().equals("3B29C6F203D4")) {
        obj.setParent(null);
        dump("RealizeView", obj);
      }

      visitObject(obj);
    }

    public void visit(UsesView obj) {
      if (obj.getQuidu().equals("3B29C88B0014")) {
        obj.setParent(null);
        dump("UsesView", obj);
      }

      visitObject(obj);
    }

    public void visit(Association obj) {
      if (obj.getQuid().equals("3B29CAD600E6")) {
        obj.setParent(null);
        dump("Association", obj);
      }

      visitObject(obj);
    }

    public void visit(AssociationViewNew obj) {
      if (obj.getQuidu().equals("3B29CAD600E6")) {
        obj.setParent(null);
        dump("AssociationViewNew", obj);
      }

      visitObject(obj);
    }

    public void visit(ItemLabel obj) {
      if (obj.getLabel().equals("SomeClass")) {
        obj.setParent(null);
        dump("ItemLabel", obj);
      }

      visitObject(obj);
    }

    public void visit(NoteView obj) {
      if (obj.getTag() == 25) {
        obj.setParent(null);
        dump("NoteView", obj);
      }

      visitObject(obj);
    }

    public void visit(AttachView obj) {
      if (obj.getTag() == 30) {
        obj.setParent(null);
        dump("AttachView", obj);
      }

      visitObject(obj);
    }

    public void visit(AssocAttachView obj) {
      if (obj.getTag() == 27) {
        obj.setParent(null);
        dump("AssocAttachView", obj);
      }

      visitObject(obj);
    }

    public void visit(SegLabel obj) {
      if (obj.getLabel().equals("1..*")) {
        obj.setParent(null);
        obj.removeProperty("anchor");
        obj.removeProperty("anchor_loc");
        dump("SegLabel", obj);
      }

      visitObject(obj);
    }

    public void visit(Operation obj) {
      if (obj.getNameParameter().equals("setAddress")) {
        obj.setParent(null);
        dump("Operation", obj);
      }

      visitObject(obj);
    }
  }

  /**
   * dump the given node to a file with the given name in the template directory
   * 
   * @param name
   * @param node
   */
  static void dump(String name, PetalNode node) {
    try {
      File file = new File("src/main/resources/templates" + File.separatorChar + name + ".ser");
      ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file));
      s.writeObject(node);
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }
  }

  /**
   * create the template files
   * @param args
   */
  public static void main(String[] args) {
    PetalFile tree = null;

    try {
      tree = PetalParser.createParser(
          "examples" + File.separatorChar + "empty.mdl").parse();

      dump("PetalFile", tree);
      dump("LogicalCategory", tree.getLogicalCategory());
      dump("UseCaseCategory", tree.getUseCaseCategory());

      tree = PetalParser.createParser(
          "examples" + File.separatorChar + "uni.mdl").parse();

      Traverser t = new Traverser();
      tree.accept(t);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
