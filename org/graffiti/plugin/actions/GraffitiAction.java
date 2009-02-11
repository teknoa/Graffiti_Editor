//==============================================================================
//
//   GraffitiAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiAction.java,v 1.2 2009/02/11 14:27:50 morla Exp $

package org.graffiti.plugin.actions;

import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.graffiti.core.ImageBundle;
import org.graffiti.core.StringBundle;

import org.graffiti.editor.MainFrame;

import org.graffiti.graph.Graph;
import org.graffiti.help.HelpContext;

/**
 * Represents the basic action in the graffiti system.
 *
 * @version $Revision: 1.2 $
 */
public abstract class GraffitiAction
    extends AbstractAction {
    //~ Instance fields ========================================================
	
	private static ArrayList<GraffitiAction> knownActions = new ArrayList<GraffitiAction>();

    /** The <code>ImageBundle</code> instance. */
    protected ImageBundle iBundle = ImageBundle.getInstance();

    /** The main frame. */
    protected MainFrame mainFrame;

    /** The abstract name of the action. */
    protected String name;
    
    protected String helpID;

    /** The <code>StringBundle</code> instance. */
    protected StringBundle sBundle = StringBundle.getInstance();

    //~ Constructors ===========================================================

    /**
     * Constructs a new GraffitiAction from the given name.
     *
     * @param name the name for the action
     * @param mainFrame DOCUMENT ME!
     */
    public GraffitiAction(String name, MainFrame mainFrame, String helpID) {
        super(name);
        this.name = name;
        this.mainFrame = mainFrame;
        this.helpID = helpID;
        knownActions.add(this);
    }
    
    protected Graph getGraph() {
    	if (mainFrame==null || mainFrame.getActiveSession()==null)
    		return null;
    	else
        return mainFrame.getActiveSession().getGraph();
    }

    //~ Methods ================================================================

    /**
     * Returns <code>true</code>, if this action is enabled.
     *
     * @return <code>true</code>, if this action is enabled.
     */
    public abstract boolean isEnabled();

    /**
     * Returns the help context for this action.
     *
     * @return the help context for this action.
     */
    public HelpContext getHelpContext() {
   	 return new HelpContext(helpID);
    }

    /**
     * @see javax.swing.AbstractAction#setEnabled(boolean)
     */
    public void setEnabled(boolean enableNow) {
    	// needs to be called once with opposite value, so that menu items and buttons
    	// are correctly informed about the new state, otherwise optimization kicks in
    	// and buttons and menu items are not informed about call to this method
		if (isEnabled()==enableNow)
			super.setEnabled(!enableNow);
		super.setEnabled(enableNow);
    }

    /**
     * Returns the abstract name of the action.
     *
     * @return the abstract name of the action.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the state of the action.  Calls:
     * <code>setEnabled(isEnabled());</code>.
     */
    public void update() {
        setEnabled(isEnabled());
    }

    /**
     * Shows an error in a modal dialog box.
     *
     * @param msg the message to be shown.
     */
    protected void showError(String msg) {
        JOptionPane.showMessageDialog(mainFrame, msg,
            StringBundle.getInstance().getString("message.dialog.title"),
            JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a warning in a modal dialog box.
     *
     * @param msg the message to be shown.
     */
    protected void showWarning(String msg) {
        JOptionPane.showMessageDialog(mainFrame, msg,
            StringBundle.getInstance().getString("message.dialog.title"),
            JOptionPane.WARNING_MESSAGE);
    }

	public static void updateAllActions() {
		ArrayList<GraffitiAction> ka = new ArrayList<GraffitiAction>();
		ka.addAll(knownActions);
		for (GraffitiAction ga : ka) {
			ga.update();
		}
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
