//==============================================================================
//
//   GraffitiViewComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiViewComponent.java,v 1.2 2009/06/16 08:25:05 morla Exp $

package org.graffiti.plugin.view;

import org.graffiti.attributes.Attribute;

/**
 * DOCUMENT ME!
 *
 * @author schoeffl
 */
public interface GraffitiViewComponent
{
    //~ Methods ================================================================

    /**
     * Called when a graphics attribute of the GraphElement represented by this
     * component has changed.
     *
     * @param attr the attribute that has triggered the event.
     */
    public void attributeChanged(Attribute attr)
        throws ShapeNotFoundException;

    /**
     * Called to initialise the shape of the NodeComponent correctly. Also
     * calls <code>repaint()</code>.
     * @param coordSys TODO
     *
     * @exception ShapeNotFoundException thrown when the shapeclass couldn't be
     *            resolved.
     */
    public void createNewShape(CoordinateSystem coordSys)
        throws ShapeNotFoundException;
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
