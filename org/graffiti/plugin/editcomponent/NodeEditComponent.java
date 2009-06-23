//==============================================================================
//
//   NodeEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: NodeEditComponent.java,v 1.2 2009/06/23 07:14:48 klukas Exp $

package org.graffiti.plugin.editcomponent;

import org.graffiti.plugin.Displayable;
import org.graffiti.plugin.parameter.NodeParameter;

/**
 * This class provides an edit component for selecting a single node.
 *
 * @see org.graffiti.graph.Node
 * @see SingleGraphElementEditComponent
 */
public class NodeEditComponent
    extends SingleGraphElementEditComponent
{
    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>NodeEditComponent</code>.
     *
     * @param disp DOCUMENT ME!
     */
    public NodeEditComponent(Displayable disp)
    {
        super(disp);
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.plugin.editcomponent.AbstractValueEditComponent#setDisplayable(org.graffiti.plugin.Displayable)
     */
    @Override
	public void setDisplayable(Displayable disp)
    {
        displayable=disp;
    }

    /**
     * @see org.graffiti.plugin.editcomponent.ValueEditComponent#setValue()
     */
    public void setValue()
    {
    }
    
    @Override
	protected Object[] getPossibilities() {
        return ((NodeParameter)displayable).getPossibleNodes();
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
