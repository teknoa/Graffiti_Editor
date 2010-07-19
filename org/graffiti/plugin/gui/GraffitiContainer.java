//==============================================================================
//
//   GraffitiContainer.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiContainer.java,v 1.2 2010/07/19 14:05:43 morla Exp $

package org.graffiti.plugin.gui;

/**
 * DOCUMENT ME!
 *
 * @author $Author: morla $
 * @version $Revision: 1.2 $
 */
public interface GraffitiContainer
extends GraffitiComponent
{
	//~ Methods ================================================================

	/**
	 * Returns an unique identifier for this <code>GraffitiComponent</code>.
	 *
	 * @return an unique identifier for this <code>GraffitiComponent</code>.
	 */
	public String getId();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
