//==============================================================================
//
//   FileSaveAllAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: FileSaveAllAction.java,v 1.3 2009/01/08 14:19:02 morla Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import org.graffiti.editor.MainFrame;

import org.graffiti.help.HelpContext;

import org.graffiti.managers.IOManager;

import org.graffiti.plugin.actions.GraffitiAction;

import org.graffiti.session.EditorSession;

/**
 * The action for saving all open graphs.
 *
 * @version $Revision: 1.3 $
 */
public class FileSaveAllAction
    extends GraffitiAction
{
    //~ Instance fields ========================================================

    /** DOCUMENT ME! */
    private IOManager ioManager;

    //~ Constructors ===========================================================

    /**
     * Creates a new FileSaveAllAction object.
     *
     * @param mainFrame DOCUMENT ME!
     * @param ioManager DOCUMENT ME!
     */
    public FileSaveAllAction(MainFrame mainFrame, IOManager ioManager)
    {
        super("file.saveAll", mainFrame, null);
        this.ioManager = ioManager;
    }

    //~ Methods ================================================================

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isEnabled()
    {
        return false;
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
        String dv = mainFrame.getDefaultView();

        if(dv != null)
        {
            mainFrame.createInternalFrame(dv, "", false, false);
        }
        else
        {
            mainFrame.showViewChooserDialog(new EditorSession(), false, e);
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
