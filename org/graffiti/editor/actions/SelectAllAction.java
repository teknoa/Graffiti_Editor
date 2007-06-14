/* Copyright (c) 2003 IPK Gatersleben
 * $Id: SelectAllAction.java,v 1.1 2007/06/14 09:36:43 klukas Exp $
 */

package org.graffiti.editor.actions;

import org.graffiti.editor.MainFrame;

import org.graffiti.help.HelpContext;

import org.graffiti.plugin.actions.SelectionAction;

import org.graffiti.selection.Selection;

import java.awt.event.ActionEvent;

import java.util.List;

/**
 * Represents a &quot;select all graph elements&quot; action.
 *
 * @version $Revision: 1.1 $
 */
public class SelectAllAction
    extends SelectionAction
{

    /**
     * Constructs a new copy action.
     *
     * @param mainFrame DOCUMENT ME!
     */
    public SelectAllAction(MainFrame mainFrame)
    {
        super("edit.selectAll", mainFrame);
    }

    /**
     * Returns the help context for the action.
     *
     * @return HelpContext, the help context for the action
     */
    public HelpContext getHelpContext()
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
        // this check can be discarded when the isEnabled-function works correctly 
        if (!mainFrame.isSessionActive()) {
            return;
        }
        getGraph().getListenerManager().transactionStarted(this);
        mainFrame.getActiveEditorSession().getActiveView().getViewComponent().repaint();
        Selection selection =
            mainFrame.getActiveEditorSession().getSelectionModel()
                .getActiveSelection();

        selection.clear();
        selection.addAll(getGraph().getGraphElements());

        mainFrame.getActiveEditorSession().getSelectionModel()
            .selectionChanged();
        getGraph().getListenerManager().transactionFinished(this);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isEnabled()
    {
    	if (!mainFrame.isSessionActive()) {
    		return false;
    	}
 
    	return true; 
    }

    /**
     * Sets the internal <code>enable</code> flag, which depends on the given
     * list of selected items.
     *
     * @param items the items, which determine the internal state of the
     *        <code>enable</code> flag.
     */
    protected void enable(List items)
    {
    }
}


//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
