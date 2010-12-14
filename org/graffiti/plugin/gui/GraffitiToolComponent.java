// ==============================================================================
//
// GraffitiToolComponent.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GraffitiToolComponent.java,v 1.3 2010/12/14 07:02:13 morla Exp $

package org.graffiti.plugin.gui;

import java.awt.event.ActionListener;

import org.graffiti.plugin.tool.Tool;

/**
 *
 */
public interface GraffitiToolComponent
					extends GraffitiContainer {
	// ~ Methods ================================================================

	/**
	 * Returns the tool this button is identified with.
	 * 
	 * @return the tool this button is identified with.
	 */
	public Tool getTool();

	/**
	 * DOCUMENT ME!
	 * 
	 * @param al
	 *           DOCUMENT ME!
	 */
	public void addActionListener(ActionListener al);

	/**
	 * DOCUMENT ME!
	 * 
	 * @param al
	 *           DOCUMENT ME!
	 */
	public void removeActionListener(ActionListener al);
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
