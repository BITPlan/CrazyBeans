/**
 * Copyright (c) 2001 Markus Dahm
 * Copyright (C) 2015-2018 BITPlan GmbH http://www.bitplan.com
 *
 * This source is part of
 * https://github.com/BITPlan/CrazyBeans
 * and the license as outlined there applies
 */
package cb.petal;

import java.util.*;

/**
 * Super class for all view objects used in the diagrams. They all contain a
 * tag.
 *
 * @version $Id: View.java,v 1.10 2001/07/09 07:48:52 dahm Exp $
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class View extends PetalObject implements Named, Tagged {
	private int tag = -1;

	/**
	 * create a View
	 * 
	 * @param parent
	 * @param name
	 * @param params
	 * @param tag
	 */
	protected View(PetalNode parent, String name, Collection params, int tag) {
		super(parent, name, params);
		setTag(tag);
	}

	protected View(String name) {
		super(name);
	}

	public void setTag(int t) {
		tag = t;
		PetalFile root = getRoot();
		if (root != null)
			root.registerView(tag, this);
	}

	public int getTag() {
		return tag;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("(object " + getName());

		for (Iterator i = params.iterator(); i.hasNext();)
			buf.append(" \"" + i.next() + "\"");

		if (tag > 0)
			buf.append(" @" + tag);

		buf.append("\n");

		for (Iterator i = getNames().iterator(), j = getPropertyList().iterator(); i.hasNext();) {
			buf.append(i.next() + "\t" + j.next());

			if (i.hasNext())
				buf.append("\n");
		}

		buf.append(")\n");

		return buf.toString();
	}

	public void setNameParameter(String o) {
		params.set(0, o);
	}

	public String getNameParameter() {
		return (String) params.get(0);
	}

	public Tag getClient() {
		return (Tag) getProperty("client");
	}

	public void setClient(Tag o) {
		defineProperty("client", o);
	}

	/**
	 * get the client view
	 * 
	 * @return the client View
	 */
	public View getClientView() {
		PetalFile root = getRoot();
		View clientView = null;
		if (root != null) {
			Tag client = getClient();
			if (client != null)
				clientView = root.getView(client.getValue());
		}
		return clientView;
	}

	public Tag getSupplier() {
		return (Tag) getProperty("supplier");
	}

	public void setSupplier(Tag o) {
		defineProperty("supplier", o);
	}

	/**
	 * get the supplier view
	 * 
	 * @return the supplier View
	 */
	public View getSupplierView() {
		PetalFile root = getRoot();
		View supplierView = null;
		if (root != null) {
			Tag supplier = getSupplier();
			if (supplier != null)
				supplierView = root.getView(supplier.getValue());
		}
		return supplierView;
	}

	public int getLineColor() {
		return getPropertyAsInteger("line_color");
	}

	public void setLineColor(int o) {
		defineProperty("line_color", o);
	}

	public int getLineStyle() {
		return getPropertyAsInteger("line_style");
	}

	public void setLineStyle(int o) {
		defineProperty("line_style", o);
	}

	public String getIcon() {
		return getPropertyAsString("icon");
	}

	public void setIcon(String o) {
		defineProperty("icon", o);
	}

	public String getIconStyle() {
		return getPropertyAsString("icon_style");
	}

	public void setIconStyle(String o) {
		defineProperty("icon_style", o);
	}

	public int getFillColor() {
		return getPropertyAsInteger("fill_color");
	}

	public void setFillColor(int o) {
		defineProperty("fill_color", o);
	}

	public int getWidth() {
		return getPropertyAsInteger("width");
	}

	public void setWidth(int o) {
		defineProperty("width", o);
	}

	public int getHeight() {
		return getPropertyAsInteger("height");
	}

	public void setHeight(int o) {
		defineProperty("height", o);
	}

	public Location getLocation() {
		return (Location) getProperty("location");
	}

	public void setLocation(Location o) {
		defineProperty("location", o);
	}
}
