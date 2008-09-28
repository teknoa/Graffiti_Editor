//==============================================================================
//
//   ViewNewAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ViewNewAction.java,v 1.2 2008/09/28 16:45:25 klukas Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import org.ErrorMsg;
import org.graffiti.core.StringBundle;
import org.graffiti.editor.MainFrame;
import org.graffiti.help.HelpContext;
import org.graffiti.plugin.actions.GraffitiAction;

/**
 * The action for creating a new view.
 */
public class ViewNewAction
    extends GraffitiAction
{
    //~ Instance fields ========================================================

    /** DOCUMENT ME! */
    private StringBundle sBundle;

    //~ Constructors ===========================================================

    /**
     * Creates a new ViewNewAction object.
     *
     * @param mainFrame DOCUMENT ME!
     * @param sBundle DOCUMENT ME!
     */
    public ViewNewAction(MainFrame mainFrame, StringBundle sBundle)
    {
        super("file.newView", mainFrame, "filemenu_new");
        this.sBundle = sBundle;
    }

    //~ Methods ================================================================

    /**
     * @see javax.swing.Action#isEnabled()
     */
    public boolean isEnabled()
    {
        return mainFrame.isSessionActive();
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
        if(mainFrame.isSessionActive())
        {
            String dv = mainFrame.getDefaultView();

            if(dv != null)
            {
                mainFrame.createInternalFrame(dv, "", false, false);
            }
            else
            {
                mainFrame.showViewChooserDialog(false, false, e);
            }
        }
        else
        {
        	ErrorMsg.addErrorMessage("A new view could not be created. "+sBundle.getString("menu.view.new.error"));
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
