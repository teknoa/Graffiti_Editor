//==============================================================================
//
//   Zoomable.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: Zoomable.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.plugin.view;

import java.awt.geom.AffineTransform;

/**
 * DOCUMENT ME!
 *
 * @author Paul
 */
public interface Zoomable
{
    //~ Methods ================================================================

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public AffineTransform getZoom();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
