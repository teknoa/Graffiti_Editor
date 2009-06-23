//==============================================================================
//
//   PluginPanel.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: PluginPanel.java,v 1.2 2009/06/23 07:14:49 klukas Exp $

package org.graffiti.plugin.gui;

import java.awt.Component;

/**
 * The panel to which plugins can add bigger view-like components.
 *
 * @version $Revision: 1.2 $
 */
public class PluginPanel
    extends AbstractGraffitiContainer
{
    //~ Constructors ===========================================================

    /**
     * Creates a new PluginPanel object.
     */
    public PluginPanel()
    {
        id = "pluginPanel";
        preferredComponent = "";
    }

    //~ Methods ================================================================

    /**
     * @see java.awt.Container#addImpl(Component, Object, int)
     */
    @Override
	protected void addImpl(Component comp, Object constraints, int index)
    {
        super.addImpl(comp, constraints, index);
        this.setVisible(true);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
