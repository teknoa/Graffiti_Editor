//==============================================================================
//
//   PluginManagerEditAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: PluginManagerEditAction.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import org.graffiti.editor.MainFrame;

import org.graffiti.help.HelpContext;

import org.graffiti.managers.pluginmgr.PluginManager;
import org.graffiti.managers.pluginmgr.PluginManagerDialog;

import org.graffiti.plugin.actions.GraffitiAction;

/**
 * Called, if the plugin manager dialog should be shown.
 *
 * @version $Revision: 1.1 $
 */
public class PluginManagerEditAction
    extends GraffitiAction
{
    //~ Instance fields ========================================================

    /** DOCUMENT ME! */
    PluginManager pluginmgr;

    //~ Constructors ===========================================================

    /**
     * Creates a new PluginManagerEditAction object.
     *
     * @param mainFrame DOCUMENT ME!
     * @param pluginmgr DOCUMENT ME!
     */
    public PluginManagerEditAction(MainFrame mainFrame, PluginManager pluginmgr)
    {
        super("pluginmgr.edit", mainFrame, null);
        this.pluginmgr = pluginmgr;
    }

    //~ Methods ================================================================

    /**
     * @see javax.swing.Action#isEnabled()
     */
    public boolean isEnabled()
    {
        return true;
    }

    /**
     * @see org.graffiti.plugin.actions.GraffitiAction#getHelpContext()
     */
    public HelpContext getHelpContext()
    {
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e)
    {
        PluginManagerDialog pmd = new PluginManagerDialog(mainFrame, pluginmgr);
        pmd.show();
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
