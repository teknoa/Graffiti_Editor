//==============================================================================
//
//   Zoomable.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: Zoomable.java,v 1.2 2009/12/02 10:00:02 klukas Exp $

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

	/**
	 * @return
	 */
	public boolean redrawActive();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
