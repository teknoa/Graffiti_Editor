//==============================================================================
//
//   ToolManager.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ToolManager.java,v 1.3 2010/07/19 14:05:42 morla Exp $

package org.graffiti.managers;

import org.graffiti.managers.pluginmgr.PluginManagerListener;
import org.graffiti.plugin.tool.Tool;

/**
 * An interface for managing a list of tools.
 *
 * @version $Revision: 1.3 $
 *
 * @see org.graffiti.managers.pluginmgr.PluginManagerListener
 */
public interface ToolManager
extends PluginManagerListener
{
	//~ Methods ================================================================

	/**
	 * Adds the specified tool to the list of tools of this manager.
	 *
	 * @param tool the tool to be added.
	 */
	public void addTool(Tool tool);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
