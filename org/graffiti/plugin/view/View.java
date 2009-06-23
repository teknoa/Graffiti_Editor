//==============================================================================
//
//   View.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: View.java,v 1.3 2009/06/23 07:14:49 klukas Exp $

package org.graffiti.plugin.view;

import java.awt.dnd.Autoscroll;
import java.awt.geom.AffineTransform;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.graffiti.event.AttributeListener;
import org.graffiti.event.EdgeListener;
import org.graffiti.event.GraphListener;
import org.graffiti.event.NodeListener;
import org.graffiti.graph.Graph;
import org.graffiti.graph.GraphElement;
import org.graffiti.managers.AttributeComponentManager;

/**
 * Represents a view of a plugin.
 *
 * @version $Revision: 1.3 $
 */
public interface View
    extends GraphListener, NodeListener, EdgeListener, AttributeListener,
        Autoscroll, ZoomListener, Zoomable // AttributeConsumer
{
    //~ Static fields/initializers =============================================

    /** Standard zoom value. */
    public static final AffineTransform NO_ZOOM = new AffineTransform();

    //~ Methods ================================================================

    /**
     * Sets the AttributeComponentManager used by this view.
     */
    public void setAttributeComponentManager(AttributeComponentManager acm);

    /**
     * Returns the map mapping <code>GraphElement</code>s with
     * <code>GraphElementComponent</code>s.
     *
     * @return DOCUMENT ME!
     */
    public Map getComponentElementMap();

    /**
     * Returns the main <code>GraphElementComponent</code> associated with the
     * given <code>GraphElement</code>.
     *
     * @param ge <code>GraphElement</code> for which the component is wanted.
     *
     * @return the <code>GraphElementComponent</code> used to display the given
     *         <code>GraphELement</code>.
     */
    public GraphElementComponent getComponentForElement(GraphElement ge);
    
    public Set<AttributeComponent> getAttributeComponentsForElement(GraphElement ge);
    

    /**
     * Sets the graph of the view to the specified value.
     *
     * @param graph the new value of the graph.
     */
    public void setGraph(Graph graph);
    
    public Graph getGraph();

    /**
     * Returns the main component of the view.
     *
     * @return the main component of the view.
     */
    public JComponent getViewComponent();

    /**
     * Returns the viewName.
     *
     * @return String
     */
    public String getViewName();
    
    public boolean putInScrollPane();

    //    /**
    //     * Returns the values for horizontal and vertical zoom encapsulated in a
    //     * Point2D object. A value of 1.0 means no zoom is applied.
    //     *
    //     * @return Point2D see method description
    //     */
    //    public Point2D getZoom();

    /**
     * Adds a message listener to the view. If the view have been started
     * without editor instance, this method may be empty.
     *
     * @param ml a message listener
     */
    public void addMessageListener(MessageListener ml);

    /**
     * Closes the current view.
     */
    public void close();

    /**
     * Instructs the view to do completely refresh its contents.
     */
    public void completeRedraw();

    /**
     * Removes a message listener from the view.If the view have been started
     * without editor instance, this method may be empty.
     *
     * @param ml a message listener
     */
    public void removeMessageListener(MessageListener ml);

    /**
     * Repaints the given graph element
     *
     * @param ge the <code>GraphElement</code> to repaint.
     */
    public void repaint(GraphElement ge);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
