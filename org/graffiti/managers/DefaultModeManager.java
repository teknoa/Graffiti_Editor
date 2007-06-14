//==============================================================================
//
//   DefaultModeManager.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DefaultModeManager.java,v 1.1 2007/06/14 09:36:46 klukas Exp $

package org.graffiti.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.graffiti.managers.pluginmgr.PluginDescription;

import org.graffiti.plugin.EditorPlugin;
import org.graffiti.plugin.GenericPlugin;
import org.graffiti.plugin.mode.Mode;

/**
 * Handles the editor's modes.
 *
 * @version $Revision: 1.1 $
 */
public class DefaultModeManager
    implements ModeManager
{
    //~ Static fields/initializers =============================================

    /** The logger for the current class. */
    private static final Logger logger = Logger.getLogger(DefaultModeManager.class.getName());

    //~ Instance fields ========================================================

    /** Maps the id of the mode to the corresponding instance of mode. */
    private Map modes;

    //~ Constructors ===========================================================

    /**
     * Constructs a new mode manager.
     */
    public DefaultModeManager()
    {
        modes = new HashMap();
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.managers.ModeManager#getMode(java.lang.String)
     */
    public Mode getMode(String mode)
    {
        return (Mode) modes.get(mode);
    }

    /**
     * Adds the given mode to the list of modes. <code>mode</code> may not be
     * <code>null</code>.
     *
     * @see org.graffiti.managers.ModeManager#addMode(org.graffiti.plugin.mode.Mode)
     */
    public void addMode(Mode mode)
    {
        assert mode != null;
        modes.put(mode.getId(), mode);
    }

    /*
     * @see org.graffiti.managers.pluginmgr.PluginManagerListener#pluginAdded(org.graffiti.plugin.GenericPlugin, org.graffiti.managers.pluginmgr.PluginDescription)
     */
    public void pluginAdded(GenericPlugin plugin, PluginDescription desc)
    {
        try
        {
            Mode[] modes = ((EditorPlugin) plugin).getModes();

            if(modes != null)
            {
                for(int i = modes.length - 1; i >= 0; i--)
                {
                    addMode(modes[i]);
                    // logger.info("new mode registered: " + modes[i].getId());
                }
            }
        }
        catch(ClassCastException cce)
        {
            // only EditorPlugins provide modes
        }
    }

    /*
     * @see org.graffiti.managers.ModeManager#removeMode(org.graffiti.plugin.mode.Mode)
     */
    public void removeMode(Mode mode)
    {
        throw new UnsupportedOperationException();
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
