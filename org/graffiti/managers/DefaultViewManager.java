//==============================================================================
//
//   DefaultViewManager.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DefaultViewManager.java,v 1.2 2008/06/18 08:57:35 klukas Exp $

package org.graffiti.managers;

import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.graffiti.managers.pluginmgr.PluginDescription;

import org.graffiti.plugin.GenericPlugin;
import org.graffiti.plugin.view.View;
import org.graffiti.plugin.view.ViewListener;

import org.graffiti.util.InstanceCreationException;
import org.graffiti.util.InstanceLoader;

/**
 * Manages a list of view types.
 *
 * @version $Revision: 1.2 $
 */
public class DefaultViewManager
    implements ViewManager
{
    //~ Static fields/initializers =============================================

    /** The logger for the current class. */
    private static final Logger logger = Logger.getLogger(DefaultViewManager.class.getName());

    //~ Instance fields ========================================================

    /** Contains the list of listeners. */
    private List listeners;

    /** Contains the list of listeners. */
    private List viewListeners;

    /** Contains the class names of the available views. */
    private List<String> views;

    //~ Constructors ===========================================================

    /**
     * Constructs a new view manager.
     */
    public DefaultViewManager()
    {
        views = new LinkedList<String>();
        listeners = new LinkedList();
        viewListeners = new LinkedList();
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.managers.ViewManager#getViewNames()
     */
    public String[] getViewNames()
    {
        Object[] names = views.toArray();
        String[] stringNames = new String[names.length];

        for(int i = 0; i < stringNames.length; i++)
        {
        	stringNames[i] = (String) names[i];
        }

        return stringNames;
    }
    
    public String[] getViewDescriptions()
    {
        Object[] names = views.toArray();
        String[] stringNames = new String[names.length];

        for(int i = 0; i < stringNames.length; i++)
        {
        	View v;
			try {
				v = createView((String)names[i]);
	        	stringNames[i] = v.getViewName();
			} catch (InstanceCreationException e) {
	        	stringNames[i] = (String) names[i] +" (invalid)";
			}
            // stringNames[i] = (String) names[i];
        }

        return stringNames;
    }

    /*
     * @see org.graffiti.managers.ViewManager#addListener(org.graffiti.managers.ViewManager.ViewManagerListener)
     */
    public void addListener(ViewManagerListener viewManagerListener)
    {
        listeners.add(viewManagerListener);
    }

    /*
     * @see org.graffiti.managers.ViewManager#addView(java.lang.String)
     */
    public void addView(String viewType)
    {
        views.add(viewType);
        // logger.info("new view registered: " + viewType);

        fireViewTypeAdded(viewType);
    }

    /*
     * @see org.graffiti.managers.ViewManager#addViewListener(org.graffiti.plugin.view.ViewListener)
     */
    public void addViewListener(ViewListener viewListener)
    {
        viewListeners.add(viewListener);
    }

    /*
     * @see org.graffiti.managers.ViewManager#addViews(java.lang.String[])
     */
    public void addViews(String[] views)
    {
        for(int i = 0; i < views.length; i++)
        {
            addView(views[i]);
        }
    }

    /*
     * @see org.graffiti.managers.ViewManager#createView(java.lang.String)
     */
    public View createView(String name)
        throws InstanceCreationException
    {
    	//System.err.println("Create view "+name);
   	 //System.err.flush();
        if(views.contains(name))
        {
      	  //logger.info("creating view: " + name);

            return (View) InstanceLoader.createInstance(name);
        }
        else
        {
            return null;
        }
    }

    /*
     * @see org.graffiti.managers.ViewManager#hasViews()
     */
    public boolean hasViews()
    {
        return !views.isEmpty();
    }

    /*
     * @see org.graffiti.managers.pluginmgr.PluginManagerListener#pluginAdded(org.graffiti.plugin.GenericPlugin, org.graffiti.managers.pluginmgr.PluginDescription)
     */
    public void pluginAdded(GenericPlugin plugin, PluginDescription desc)
    {
        addViews(plugin.getViews());
        if (plugin.getDefaultView()!=null)
        	setDefaultView(plugin.getDefaultView());
    }

    /*
     * @see org.graffiti.managers.ViewManager#removeListener(org.graffiti.managers.ViewManager.ViewManagerListener)
     */
    public boolean removeListener(ViewManagerListener l)
    {
        return listeners.remove(l);
    }

    /*
     * @see org.graffiti.managers.ViewManager#removeViewListener(org.graffiti.plugin.view.ViewListener)
     */
    public boolean removeViewListener(ViewListener l)
    {
        return viewListeners.remove(l);
    }

    /*
     * @see org.graffiti.plugin.view.ViewListener#viewChanged(org.graffiti.plugin.view.View)
     */
    public void viewChanged(View newView)
    {
        for(Iterator it = viewListeners.iterator(); it.hasNext();)
        {
            ((ViewListener) it.next()).viewChanged(newView);
        }
    }

    /**
     * Informs all view manager listeners, that the given view type is
     * available.
     *
     * @param viewType the new view type.
     */
    private void fireViewTypeAdded(String viewType)
    {
        for(Iterator i = listeners.iterator(); i.hasNext();)
        {
            ViewManagerListener l = (ViewManagerListener) i.next();

            l.viewTypeAdded(viewType);
        }
    }

	/* (non-Javadoc)
	 * @see org.graffiti.managers.ViewManager#removeViews()
	 */
	public void removeViews() {
		views.clear();
	}

	String defaultView;
	
	public String getDefaultView() {
		return defaultView;
	}

	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
