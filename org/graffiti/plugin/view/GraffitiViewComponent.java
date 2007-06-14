//==============================================================================
//
//   GraffitiViewComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiViewComponent.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

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
     *
     * @exception ShapeNotFoundException thrown when the shapeclass couldn't be
     *            resolved.
     */
    public void createNewShape()
        throws ShapeNotFoundException;
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
