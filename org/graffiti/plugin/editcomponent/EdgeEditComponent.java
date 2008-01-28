//==============================================================================
//
//   EdgeEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EdgeEditComponent.java,v 1.1 2007/06/14 09:36:46 klukas Exp $

/* Generated by Together */
package org.graffiti.plugin.editcomponent;

import javax.swing.JComponent;

import org.graffiti.plugin.Displayable;

/**
 * This class provides an edit component for selecting a single edge.
 *
 * @see org.graffiti.graph.Edge
 * @see SingleGraphElementEditComponent
 */
public class EdgeEditComponent
    extends SingleGraphElementEditComponent
{
    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>EdgeEditComponent</code>.
     *
     * @param disp DOCUMENT ME!
     */
    public EdgeEditComponent(Displayable disp)
    {
        super(disp);
    }

    //~ Methods ================================================================

    /**
     * @see org.graffiti.plugin.editcomponent.ValueEditComponent#getComponent()
     */
    public JComponent getComponent()
    {
        return super.getComponent();
    }

    /*
     * @see org.graffiti.plugin.editcomponent.AbstractValueEditComponent#setDisplayable(org.graffiti.plugin.Displayable)
     */
    public void setDisplayable(Displayable attr)
    {
    }

    /**
     * Sets the graph component selectable by this edit component.
     */
    public void setGraphComponent()
    {
    }

    /**
     * @see org.graffiti.plugin.editcomponent.ValueEditComponent#setValue()
     */
    public void setValue()
    {
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------