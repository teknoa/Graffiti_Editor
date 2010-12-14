// ==============================================================================
//
// NeedEditComponents.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: NeedEditComponents.java,v 1.4 2010/12/14 07:02:13 morla Exp $

package org.graffiti.plugin.editcomponent;

import java.util.Map;

/**
 *
 */
public interface NeedEditComponents {
	// ~ Methods ================================================================

	/**
	 * Set the map that connects attributes and parameters with editcomponents.
	 * 
	 * @param ecMap
	 */
	public void setEditComponentMap(Map<?, ?> ecMap);
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
