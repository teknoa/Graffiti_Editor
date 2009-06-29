//==============================================================================
//
//   EditorPlugin.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditorPlugin.java,v 1.3 2009/06/29 21:44:47 klukas Exp $

package org.graffiti.plugin;

import java.util.Map;

import org.graffiti.plugin.editcomponent.ValueEditComponent;
import org.graffiti.plugin.gui.GraffitiComponent;
import org.graffiti.plugin.inspector.InspectorTab;
import org.graffiti.plugin.mode.Mode;
import org.graffiti.plugin.tool.Tool;
import org.graffiti.plugin.view.GraffitiShape;

/**
 *
 */
public interface EditorPlugin
    extends GenericPlugin
{
    //~ Methods ================================================================

    /**
     * Returns a mapping between attribute paths and attributeComponent
     * classes.
     *
     * @return DOCUMENT ME!
     */
    public Map getAttributeComponents();

    /**
     * Returns the array of <code>GraffitiComponent</code>s the plugin
     * contains.
     *
     * @return the array of <code>GraffitiComponent</code>s the plugin
     *         contains.
     */
    public GraffitiComponent[] getGUIComponents();

    /**
     * Returns the array of <code>org.graffiti.plugin.mode.Mode</code>s the
     * plugin contains.
     *
     * @return the array of <code>org.graffiti.plugin.mode.Mode</code>s the
     *         plugin contains.
     */
    public Mode[] getModes();

    /**
     * Returns the array of
     * <code>org.graffiti.plugin.view.GraffitiShape</code>s the plugin
     * contains.
     *
     * @return the array of
     *         <code>org.graffiti.plugin.view.GraffitiShape</code>s the plugin
     *         contains.
     */
    public GraffitiShape[] getShapes();

    /**
     * Returns an array of <code>org.graffiti.plugin.mode.Tool</code>s the
     * plugin provides.
     *
     * @return an array of tools the plugin provides.
     */
    public Tool[] getTools();

    /**
     * Returns a mapping between attribute classnames and attributeComponent
     * classes.
     *
     * @return DOCUMENT ME!
     */
    public Map<Displayable,ValueEditComponent> getValueEditComponents();
    
    public InspectorTab[] getInspectorTabs();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
