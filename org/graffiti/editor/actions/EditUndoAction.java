//==============================================================================
//
//   EditUndoAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditUndoAction.java,v 1.1 2007/06/14 09:36:43 klukas Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.undo.UndoManager;

import org.graffiti.editor.MainFrame;

import org.graffiti.help.HelpContext;

import org.graffiti.plugin.actions.GraffitiAction;

import org.graffiti.session.EditorSession;

/**
 * Special class for undo capabilities.
 *
 * @version $Revision: 1.1 $
 */
public class EditUndoAction
    extends GraffitiAction {
    //~ Constructors ===========================================================

    /**
     * Creates a new EditUndoAction object.
     *
     * @param mainFrame DOCUMENT ME!
     */
    public EditUndoAction(MainFrame mainFrame) {
        super("edit.undo", mainFrame, "editmenu_undo");
        enabled = false;
    }

    //~ Methods ================================================================

    /**
     * @see javax.swing.Action#isEnabled()
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @see org.graffiti.plugin.actions.GraffitiAction#getHelpContext()
     */
    public HelpContext getHelpContext() {
        return null;
    }

    /**
     * <b>Implementation Note:</b> The status of the GUIComponents has to be
     * updated after actionPerformed was executed.
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e) {
        mainFrame.getActiveEditorSession().getUndoManager().undo();
        mainFrame.updateActions();
    }

    /**
     * Updates the state of this action.
     */
    public void update() {
        if (mainFrame.isSessionActive()) {
           EditorSession session = mainFrame.getActiveEditorSession();
           UndoManager um = session.getUndoManager();
           setEnabled(um.canUndo());
           putValue(NAME, um.getUndoPresentationName());
           putValue(SHORT_DESCRIPTION, um.getUndoPresentationName());
       } else {
           setEnabled(false);
           putValue(NAME, sBundle.getString("menu." + getName()));
           putValue(SHORT_DESCRIPTION, getName());
       }

       putValue(SMALL_ICON,
           iBundle.getImageIcon("toolbar." + getName() + ".icon"));
   }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
