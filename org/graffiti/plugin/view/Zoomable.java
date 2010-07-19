//==============================================================================
//
//   Zoomable.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: Zoomable.java,v 1.3 2010/07/19 14:05:42 morla Exp $

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
