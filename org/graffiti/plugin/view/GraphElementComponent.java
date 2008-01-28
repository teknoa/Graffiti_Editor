//==============================================================================
//
//   GraphElementComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraphElementComponent.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.plugin.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.graffiti.graphics.GraphicAttributeConstants;

/**
 * Class that shares common members for all GraphElementComponents.
 *
 * @version $Revision: 1.1 $
 */
public abstract class GraphElementComponent
    extends JPanel
    implements GraffitiViewComponent, GraphicAttributeConstants,
        GraphElementComponentInterface
{
    //~ Methods ================================================================

    /**
     * Returns whether the given coordinates lie within this component and
     * within its encapsulated shape. The coordinates are assumed to be
     * relative to  the coordinate system of this component.
     *
     * @see java.awt.Component#contains(int, int)
     */
    public boolean contains(int x, int y)
    {
        return super.contains(x, y);
    }

    /**
     * Paints the graph element contained in this component.
     *
     * @param g the graphics context in which to paint.
     *
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------