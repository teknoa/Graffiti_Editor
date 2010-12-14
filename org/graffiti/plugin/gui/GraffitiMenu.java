// ==============================================================================
//
// GraffitiMenu.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GraffitiMenu.java,v 1.4 2010/12/14 07:02:13 morla Exp $

package org.graffiti.plugin.gui;

import javax.swing.JMenu;

import org.graffiti.editor.MainFrame;

/**
 * DOCUMENT ME!
 */
public class GraffitiMenu
					extends JMenu
					implements GraffitiComponent {
	// ~ Methods ================================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see org.graffiti.plugin.gui.GraffitiComponent#setMainFrame(org.graffiti.editor.MainFrame)
	 */
	public void setMainFrame(MainFrame mf) {
	}

	/**
	 * @see org.graffiti.plugin.gui.GraffitiComponent#getPreferredComponent()
	 */
	public String getPreferredComponent() {
		return "menu";
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
