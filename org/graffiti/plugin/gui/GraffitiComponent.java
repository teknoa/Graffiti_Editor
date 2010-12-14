// ==============================================================================
//
// GraffitiComponent.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GraffitiComponent.java,v 1.3 2010/12/14 07:02:13 morla Exp $

package org.graffiti.plugin.gui;

/**
 * Interface for all GUIComponents used in the editor. Provides the <code>getPreferredComponent()</code> method.
 * 
 * @version $Revision: 1.3 $
 */
public interface GraffitiComponent {
	// ~ Methods ================================================================

	/**
	 * Returns the id of the component this component should be placed in.
	 * 
	 * @return the id of the component this component should be placed in.
	 */
	public String getPreferredComponent();
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
