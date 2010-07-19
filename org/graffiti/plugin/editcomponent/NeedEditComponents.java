//==============================================================================
//
//   NeedEditComponents.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: NeedEditComponents.java,v 1.3 2010/07/19 14:05:43 morla Exp $

package org.graffiti.plugin.editcomponent;

import java.util.Map;

/**
 *
 */
public interface NeedEditComponents
{
	//~ Methods ================================================================

	/**
	 * Set the map that connects attributes and parameters with editcomponents.
	 *
	 * @param ecMap
	 */
	public void setEditComponentMap(Map<?, ?> ecMap);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
