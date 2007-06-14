//==============================================================================
//
//   CloseViewAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: CloseViewAction.java,v 1.1 2007/06/14 09:36:43 klukas Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import org.graffiti.help.HelpContext;

import org.graffiti.plugin.actions.GraffitiAction;

/**
 * Represents a clone view action.
 */
public class CloseViewAction
    extends GraffitiAction
{
    //~ Constructors ===========================================================

    /**
     * Constructs a new close view action.
     */
    public CloseViewAction()
    {
        super("action.view.close", null, "filemenu_close"); // TODO
    }

    //~ Methods ================================================================

    /**
     * @see javax.swing.Action#isEnabled()
     */
    public boolean isEnabled()
    {
        return false;
    }

    /**
     * Returns the help context for this action.
     *
     * @return the help context for this action.
     */
    public HelpContext getHelpContext()
    {
        return null;
    }

    /**
     * Returns the name of this action.
     *
     * @return String, the name of this action.
     */
    public String getName()
    {
        return null;
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
