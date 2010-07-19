//==============================================================================
//
//   PluginPanel.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: PluginPanel.java,v 1.4 2010/07/19 14:05:43 morla Exp $

package org.graffiti.plugin.gui;

import java.awt.Component;

/**
 * The panel to which plugins can add bigger view-like components.
 *
 * @version $Revision: 1.4 $
 */
public class PluginPanel
extends AbstractGraffitiContainer
{
	//~ Constructors ===========================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new PluginPanel object.
	 */
	public PluginPanel()
	{
		id = "pluginPanel";
		preferredComponent = "";
	}

	//~ Methods ================================================================

	/**
	 * @see java.awt.Container#addImpl(Component, Object, int)
	 */
	@Override
	protected void addImpl(Component comp, Object constraints, int index)
	{
		super.addImpl(comp, constraints, index);
		this.setVisible(true);
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
