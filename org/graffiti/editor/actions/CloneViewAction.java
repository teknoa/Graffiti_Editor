//==============================================================================
//
//   CloneViewAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: CloneViewAction.java,v 1.3 2010/07/19 14:05:42 morla Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import org.graffiti.help.HelpContext;
import org.graffiti.plugin.actions.GraffitiAction;

/**
 * Represents an action, which can clone the current view.
 *
 * @version $Revision: 1.3 $
 */
public class CloneViewAction
extends GraffitiAction
{
	//~ Constructors ===========================================================

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new clone view action.
	 */
	public CloneViewAction()
	{
		super("action.view.clone", null, null); // TODO
	}

	//~ Methods ================================================================

	/**
	 * @see javax.swing.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled()
	{
		return false;
	}

	/**
	 * Returns the help context for this action.
	 *
	 * @return the help context for this action.
	 */
	@Override
	public HelpContext getHelpContext()
	{
		return null; // TODO
	}

	/**
	 * Returns the name of this action.
	 *
	 * @return String, the name of this action.
	 */
	@Override
	public String getName()
	{
		return null; // TODO
	}

	/**
	 * Executes this action.
	 *
	 * @param e DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e)
	{
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
