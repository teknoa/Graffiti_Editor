// ==============================================================================
//
// GraffitiContainer.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GraffitiContainer.java,v 1.3 2010/12/14 07:02:13 morla Exp $

package org.graffiti.plugin.gui;

/**
 * DOCUMENT ME!
 * 
 * @author $Author: morla $
 * @version $Revision: 1.3 $
 */
public interface GraffitiContainer
					extends GraffitiComponent {
	// ~ Methods ================================================================

	/**
	 * Returns an unique identifier for this <code>GraffitiComponent</code>.
	 * 
	 * @return an unique identifier for this <code>GraffitiComponent</code>.
	 */
	public String getId();
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
