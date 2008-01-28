//==============================================================================
//
//   GraffitiMenuItem.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiMenuItem.java,v 1.1 2007/06/14 09:36:48 klukas Exp $

package org.graffiti.plugin.gui;

import javax.swing.Action;
import javax.swing.JMenuItem;

import org.graffiti.editor.MainFrame;

/**
 * TODO
 */
public class GraffitiMenuItem
    extends JMenuItem
    implements GraffitiComponent
{
    //~ Instance fields ========================================================

    /** The id of the component the menu item prefers to be inserted in. */
    protected String preferredComponent;

    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>GraffitiMenuItem</code>.
     *
     * @param prefComp DOCUMENT ME!
     * @param a DOCUMENT ME!
     */
    public GraffitiMenuItem(String prefComp, Action a)
    {
        super(a);
        this.preferredComponent = prefComp;
    }

    //~ Methods ================================================================

    /**
     * @see org.graffiti.plugin.gui.GraffitiComponent#setMainFrame(org.graffiti.editor.MainFrame)
     */
    public void setMainFrame(MainFrame mf)
    {
    }

    /**
     * Returns the id of the component the menu item prefers to be inserted in.
     *
     * @return the id of the component the menu item prefers to be inserted in.
     */
    public String getPreferredComponent()
    {
        return this.preferredComponent;
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------