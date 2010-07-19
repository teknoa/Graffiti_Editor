//==============================================================================
//
//   ZoomListener.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ZoomListener.java,v 1.2 2010/07/19 14:05:42 morla Exp $

package org.graffiti.plugin.view;

import java.awt.geom.AffineTransform;

/**
 * DOCUMENT ME!
 *
 * @author Paul
 */
public interface ZoomListener
{
	//~ Methods ================================================================

	/**
	 * Indicates that a zoom value has changed.
	 */
	public void zoomChanged(AffineTransform newZoom);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
