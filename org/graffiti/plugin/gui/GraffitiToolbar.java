//==============================================================================
//
//   GraffitiToolbar.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiToolbar.java,v 1.4 2010/07/19 14:05:43 morla Exp $

package org.graffiti.plugin.gui;

import javax.swing.JToolBar;

/**
 * TODO
 */
public class GraffitiToolbar
extends JToolBar
implements GraffitiContainer
{
	//~ Instance fields ========================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id of the toolbar. */
	protected String id;

	/** The id of the component the toolbar prefers to be inserted in. */
	protected String preferredComponent;

	//~ Constructors ===========================================================

	/**
	 * Standardconstructor for <code>GraffitiToolbar</code>.
	 */
	public GraffitiToolbar()
	{
		this("[not named toolbar]");
	}

	/**
	 * Constructor that sets the id of this <code>GraffitiToolbar</code>.
	 *
	 * @param name DOCUMENT ME!
	 */
	public GraffitiToolbar(String name)
	{
		super(name);
		this.id = name;
		this.preferredComponent = "toolbarPanel";

	}

	//~ Methods ================================================================

	/**
	 * Returns the id of this toolbar.
	 *
	 * @return the id of this toolbar.
	 */
	public String getId()
	{
		return this.id;
	}

	/**
	 * Returns the id of the component the toolbar prefers to be inserted.
	 *
	 * @return the id of the component the toolbar prefers to be inserted.
	 */
	public String getPreferredComponent()
	{
		return this.preferredComponent;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
