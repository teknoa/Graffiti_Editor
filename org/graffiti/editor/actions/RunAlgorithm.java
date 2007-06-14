//==============================================================================
//
//   RunAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: RunAlgorithm.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import org.graffiti.editor.GravistoService;
import org.graffiti.editor.MainFrame;

import org.graffiti.help.HelpContext;

import org.graffiti.managers.EditComponentManager;

import org.graffiti.plugin.actions.GraffitiAction;
import org.graffiti.plugin.algorithm.Algorithm;

/**
 * Runs an algorithm.
 *
 * @version $Revision: 1.1 $
 */
public class RunAlgorithm
    extends GraffitiAction
{
    //~ Instance fields ========================================================

    private static final long serialVersionUID = 1L;

    /** The edit component manager for the parameter edit panel. */
    private EditComponentManager editComponentManager;

    /** The class name of the algorithm to run. */
    private String algorithmClassName;
    
	private Algorithm algorithm;    

    //~ Constructors ===========================================================

    /**
     * Constructor for RunAlgorithm.
     *
     * @param algorithmClassName DOCUMENT ME!
     * @param name
     * @param mainFrame
     * @param editComponentManager DOCUMENT ME!
     */
    public RunAlgorithm(String algorithmClassName, String name,
        MainFrame mainFrame, EditComponentManager editComponentManager, Algorithm instance)
    {
        super(name, mainFrame, null);
        this.algorithmClassName = algorithmClassName;
        this.editComponentManager = editComponentManager;
		this.algorithm = instance;
    }

    //~ Methods ================================================================

    /**
     * Get the name of the algorithm that this action will run.
     *
     * @return the name of the algorithm that this action will run.
     */
    public String getAlgorithmClassName()
    {
        return algorithmClassName;
    }

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
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent a)
    {
    	algorithm.setActionEvent(a);
        GravistoService.getInstance().runAlgorithm(algorithm);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
