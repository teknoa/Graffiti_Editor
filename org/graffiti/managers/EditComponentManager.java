//==============================================================================
//
//   EditComponentManager.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditComponentManager.java,v 1.2 2009/06/23 07:14:48 klukas Exp $

package org.graffiti.managers;

import java.util.HashMap;
import java.util.Map;

import org.graffiti.editor.EditComponentNotFoundException;
import org.graffiti.managers.pluginmgr.PluginDescription;
import org.graffiti.managers.pluginmgr.PluginManagerListener;
import org.graffiti.plugin.EditorPlugin;
import org.graffiti.plugin.GenericPlugin;
import org.graffiti.plugin.editcomponent.ValueEditComponent;
import org.graffiti.util.InstanceCreationException;
import org.graffiti.util.InstanceLoader;

/**
 * Contains the mapping between displayable classes and their representation as
 * <code>AttributeComponent</code> classes.
 *
 * @author ph
 * @version $Revision: 1.2 $
 */
public class EditComponentManager
    implements PluginManagerListener
{
    //~ Instance fields ========================================================

    /** Maps displayable classes to ValueEditComponent classes. */
    private Map valueEditComponents;

    //~ Constructors ===========================================================

    /**
     * Constructs an EditComponentManager.
     */
    public EditComponentManager()
    {
        this.valueEditComponents = new HashMap();
    }

    //~ Methods ================================================================

    /**
     * Returns the map of value edit components.
     *
     * @return DOCUMENT ME!
     */
    public Map getEditComponents()
    {
        return valueEditComponents;
    }

    /**
     * Returns an instance of the ValueEditComponent that is capable of
     * providing a possibility to alter the value of the displayable with type
     * <code>aType</code>.
     *
     * @param aType the class of the displayable to retrieve a component for.
     *
     * @return an instance of an ValueEditComponent.
     *
     * @throws EditComponentNotFoundException DOCUMENT ME!
     */
    public ValueEditComponent getValueEditComponent(Class aType)
        throws EditComponentNotFoundException
    {
        if(!(valueEditComponents.containsKey(aType)))
        {
            throw new EditComponentNotFoundException(
                "No registered ValueEditComponent for displayable type " +
                aType);
        }

        Class ac = (Class) valueEditComponents.get(aType);

        try
        {
            ValueEditComponent component = (ValueEditComponent) InstanceLoader.createInstance(ac,
                    null);

            return component;
        }
        catch(InstanceCreationException ice)
        {
            throw new EditComponentNotFoundException(ice.getMessage());
        }
    }

    /**
     * Called by the plugin manager, iff a plugin has been added.
     *
     * @param plugin the added plugin.
     * @param desc the description of the new plugin.
     */
    public void pluginAdded(GenericPlugin plugin, PluginDescription desc)
    {
        //System.out.println("putting: " + plugin.getAttributeComponents());
        if(plugin instanceof EditorPlugin)
        {
            if (((EditorPlugin) plugin).getValueEditComponents() != null &&
                    ((EditorPlugin) plugin).getValueEditComponents().size()>0) {
            	valueEditComponents.putAll(((EditorPlugin) plugin).getValueEditComponents());
            }
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
