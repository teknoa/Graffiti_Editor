//==============================================================================
//
//   GraphElementShape.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraphElementShape.java,v 1.2 2009/06/09 09:06:57 klukas Exp $

package org.graffiti.plugin.view;

import java.awt.geom.Rectangle2D;

/**
 * Interface combining <code>NodeShape</code> and <code>EdgeShape</code>.
 */
public interface GraphElementShape
    extends GraffitiShape
{
    //~ Methods ================================================================

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Rectangle2D getRealBounds2D();
    
    public double getXexcess();
    public double getYexcess();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
