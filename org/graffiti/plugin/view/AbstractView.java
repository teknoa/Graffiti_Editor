//==============================================================================
//
//   AbstractView.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AbstractView.java,v 1.15 2010/01/26 14:15:44 morla Exp $

package org.graffiti.plugin.view;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.BackgroundTaskStatusProviderSupportingExternalCall;
import org.graffiti.core.StringBundle;
import org.graffiti.editor.MessageType;
import org.graffiti.event.AttributeEvent;
import org.graffiti.event.EdgeEvent;
import org.graffiti.event.GraphEvent;
import org.graffiti.event.NodeEvent;
import org.graffiti.event.TransactionEvent;
import org.graffiti.graph.Graph;
import org.graffiti.graph.GraphElement;
import org.graffiti.managers.AttributeComponentManager;
import org.graffiti.plugin.inspector.InspectorTab;

/**
 * enclosing_type
 *
 * @version $Revision: 1.15 $
 */
public abstract class AbstractView
    extends JComponent
    implements View
{
    //~ Static fields/initializers =============================================
		private static final long serialVersionUID = 1L;

	/** The autoscroll margin. */
    protected static final int autoscrollMargin = 20;

    /** The autoresize margin. */
    protected static final int autoresizeMargin = 50;

    /** The insets of the autoscroll. */
    protected static final Insets autoscrollInsets = new Insets(0, 0, 0, 0);

    //~ Instance fields ========================================================

    /** The current zoom for this view. */
    protected AffineTransform zoom = View.NO_ZOOM;

    /**
     * The <code>AttributeComponentManager</code> that tells the view what
     * class to use as <code>AttributeComponent</code>s for attributes
     */
    protected AttributeComponentManager acm;

    /** The <code>Graph</code> with which this view is associated. */
    protected Graph currentGraph;

    /**
     * Contains a mapping from <code>GraphElement</code> to its
     * <code>GraphElementComponent</code>.
     */
    protected HashMap<GraphElement, GraphElementComponent> graphElementComponents;
    
    protected HashMap<GraphElement, Set<AttributeComponent>> graphElementAttributeComponents = new HashMap<GraphElement,Set<AttributeComponent>>();

    /**
     * Contains MessageListeners which are interesting for messages  generated
     * by the view i.e. if the painting of graph elements failed.
     */
    protected Set<MessageListener> messageListeners; 

    /** The name of the this view. */
    protected String viewName = "";

    /** The <code>StringBundle</code> of the exceptions. */
    protected StringBundle sBundle = StringBundle.getInstance();

    //~ Constructors ===========================================================

    //    protected Object zoom = new Point2D.Double(2, 2);

    /**
     * Constructs a new <code>GraffitiView</code>. The graph is initialized
     * with an instance of the default implementation.
     */
    public AbstractView()
    {
        this.graphElementComponents = new HashMap<GraphElement, GraphElementComponent>();
        this.messageListeners = new HashSet<MessageListener>();
        this.viewName = extractName();
    }
    
    public GraphElementComponent getGraphElementComponent(GraphElement ge) {
    		return graphElementComponents.get(ge);
    }
    
    public void clearGraphElementComponentMap() {
    	if (graphElementComponents!=null)	
    		graphElementComponents.clear();
    	if (graphElementAttributeComponents!=null)
    		graphElementAttributeComponents.clear();
    }
    
    public Map<GraphElement, GraphElementComponent> getGraphElementComponentMap() {
    		return graphElementComponents;
    }
    
    public void removeGraphElementComponent(GraphElement ge) {
    		graphElementComponents.remove(ge);
    		graphElementAttributeComponents.remove(ge);
    }
    
    public Set<AttributeComponent> getAttributeComponentsForElement(GraphElement ge) {
    		return graphElementAttributeComponents.get(ge);
    }
    


    /**
     * Constructs a new <code>GraffitiView</code> for the specified
     * <code>Graph</code>.
     *
     * @param currentGraph the <code>Graph</code> for which to construct the
     *        new <code>GraffitiView</code>.
     */
    public AbstractView(Graph currentGraph)
    {
        this.currentGraph = currentGraph;
        this.graphElementComponents = new HashMap<GraphElement, GraphElementComponent>(); 
        this.messageListeners = new HashSet<MessageListener>();
        this.viewName = extractName();
    }

    //~ Methods ================================================================

    /**
     * Sets the AttributeComponentManager used by this view.
     *
     * @param acm DOCUMENT ME!
     */
    public void setAttributeComponentManager(AttributeComponentManager acm)
    {
        this.acm = acm;
    }

    /**
     * Sets the graph this view displays.
     *
     * @param g the graph this view should display.
     */
    public abstract void setGraph(Graph g);
    
    public Graph getGraph() {
    		return currentGraph;
    }

    /**
     * @see java.awt.dnd.Autoscroll#getAutoscrollInsets()
     */
    public Insets getAutoscrollInsets()
    {
        Dimension size = getSize();
        Rectangle rect = getVisibleRect();

        autoscrollInsets.top = rect.y + autoscrollMargin;
        autoscrollInsets.left = rect.x + autoscrollMargin;
        autoscrollInsets.bottom = size.height - (rect.y + rect.height) +
            autoscrollMargin;
        autoscrollInsets.right = size.width - (rect.x + rect.width) +
            autoscrollMargin;

        return autoscrollInsets;
    }

    /**
     * @see org.graffiti.plugin.view.View#getComponentElementMap()
     */
    public Map<GraphElement, GraphElementComponent> getComponentElementMap()
    {
        return this.graphElementComponents;
    }

    /**
     * Returns the main <code>GraphElementComponent</code> associated with the
     * given <code>GraphELement</code>.
     *
     * @param ge <code>GraphElement</code> for which the component is wanted.
     *
     * @return the <code>GraphElementComponent</code> used to display the given
     *         <code>GraphElement</code>. <code>null</code> if no component is
     *         registered for the element (or it has been mapped to
     *         <code>null</code> explicitly).
     */
    public GraphElementComponent getComponentForElement(GraphElement ge)
    {
        return this.graphElementComponents.get(ge);
    }

    /**
     * @see org.graffiti.plugin.view.View#getViewComponent()
     */
    public JComponent getViewComponent()
    {
        return this;
    }

    /**
     * Returns the viewName.
     *
     * @return String
     */
    public String getViewName()
    {
        return viewName;
    }

    /**
     * @see org.graffiti.plugin.view.Zoomable#getZoom()
     */
    public AffineTransform getZoom()
    {
        return this.zoom;
    }

    /**
     * Adds a message listener to the view.
     *
     * @param ml a message listener
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    public void addMessageListener(MessageListener ml)
    {
        if(ml == null)
            throw new IllegalArgumentException("The argument may not be null");

        this.messageListeners.add(ml);
    }

    /**
     * Resizes the panel dynamically so that enough drawing space is available.
     *
     * @param location the point that should be checked for border conflicts
     */
    public void autoresize(Point location)
    {
        boolean resize = false;
        Dimension size = getSize();

        zoom.transform(location, location);

        if((location.y + autoresizeMargin) > size.height)
        {
            size.height = Math.max(location.y, size.height) + 100;
            resize = true;
        }

        if((location.x + autoresizeMargin) > size.width)
        {
            size.width = Math.max(location.x, size.width) + 100;
            resize = true;
        }

        if(resize)
        {
            this.setSize(size);
            this.setPreferredSize(size);
            // revalidate();
            invalidate();
        }
    }

    /**
     * @see java.awt.dnd.Autoscroll#autoscroll(Point)
     */
    public void autoscroll(Point location)
    {
        int top = 0;
        int left = 0;
        int bottom = 0;
        int right = 0;

        Point origLoc = (Point) location.clone();
        zoom.transform(location, location);

        //        location.setLocation(location.getX() * ((Point2D) zoom).getX(),
        //            location.getY() * ((Point2D) zoom).getY());
        Dimension size = getSize();
        Rectangle rect = getVisibleRect();

        int bottomEdge = rect.y + rect.height;
        int rightEdge = rect.x + rect.width;

        if(((location.y - rect.y) <= autoscrollMargin) && (rect.y > 0))
        {
            top = autoscrollMargin;
        }

        if(((location.x - rect.x) <= autoscrollMargin) && (rect.x > 0))
        {
            left = autoscrollMargin;
        }

        autoresize(origLoc);

        if(((bottomEdge - location.y) <= autoscrollMargin) &&
            (bottomEdge < size.height))
        {
            bottom = autoscrollMargin;
        }

        if(((rightEdge - location.x) <= autoscrollMargin) &&
            (rightEdge < size.width))
        {
            right = autoscrollMargin;
        }

        rect.x += (right - left);
        rect.y += (bottom - top);

        scrollRectToVisible(rect);
    }

    /**
     * Closes the current view.
     */
    public void close()
    {
       // setVisible(false);
   	 
       graphElementComponents.clear();
       graphElementAttributeComponents.clear();
       
       currentGraph = null;
       acm = null;
       graphElementComponents = null;
       graphElementAttributeComponents = null;

    }

    /**
     * Called after an attribute has been added.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    public void postAttributeAdded(AttributeEvent e)
    {
    }

    /**
     * Called after an attribute has been changed.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    public void postAttributeChanged(AttributeEvent e)
    {
    }

    /**
     * Called after an attribute has been removed.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    public void postAttributeRemoved(AttributeEvent e)
    {
    }

    /**
     * Called after the edge was set directed or undirected.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void postDirectedChanged(EdgeEvent e)
    {
    }

    /**
     * Called after an edge has been added to the graph.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void postEdgeAdded(GraphEvent e)
    {
    }

    /**
     * Called after an edge has been removed from the graph.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void postEdgeRemoved(GraphEvent e)
    {
    }

    /**
     * Called after the edge has been reversed.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void postEdgeReversed(EdgeEvent e)
    {
    }

    /**
     * Called after method <code>clear()</code> has been called on a graph. No
     * other events (like remove events) are generated.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void postGraphCleared(GraphEvent e)
    {
    }

    /**
     * Called just after an incoming edge has been added to the node. (For
     * undirected edges postUndirectedEdgeAdded is called instead.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void postInEdgeAdded(NodeEvent e)
    {
    }

    /**
     * Called after an incoming edge has been removed from the node. (For
     * undirected edges postUndirectedEdgeRemoved is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void postInEdgeRemoved(NodeEvent e)
    {
    }

    /**
     * Called after an edge has been added to the graph.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void postNodeAdded(GraphEvent e)
    {
    }

    /**
     * Called after a node has been removed from the graph. All edges incident
     * to this node have already been removed (preEdgeRemoved and
     * postEdgeRemoved have been called).
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void postNodeRemoved(GraphEvent e)
    {
    }

    /**
     * Called after an outgoing edge has been added to the node. (For
     * undirected edges postUndirectedEdgeAdded is called instead.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void postOutEdgeAdded(NodeEvent e)
    {
    }

    /**
     * Called after an outgoing edge has been removed from the node. (For
     * undirected edges postUndirectedEdgeRemoved is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void postOutEdgeRemoved(NodeEvent e)
    {
    }

    /**
     * Called after the source node of an edge has changed.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void postSourceNodeChanged(EdgeEvent e)
    {
    }

    /**
     * Called after the target node of an edge has changed.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void postTargetNodeChanged(EdgeEvent e)
    {
    }

    /**
     * Called after an (undirected) edge has been added to the node. (For
     * directed edges pre- In/Out- EdgeAdded is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void postUndirectedEdgeAdded(NodeEvent e)
    {
    }

    /**
     * Called after an (undirected) edge has been removed from the node. (For
     * directed edges pre- In/Out- EdgeRemoved is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void postUndirectedEdgeRemoved(NodeEvent e)
    {
    }

    /**
     * Called just before an attribute is added.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    public void preAttributeAdded(AttributeEvent e)
    {
    }

    /**
     * Called before a change of an attribute takes place.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    public void preAttributeChanged(AttributeEvent e)
    {
    }

    /**
     * Called just before an attribute is removed.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    public void preAttributeRemoved(AttributeEvent e)
    {
    }

    /**
     * Called before the edge is set directed or undirected.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void preDirectedChanged(EdgeEvent e)
    {
    }

    /**
     * Called just before an edge is added to the graph.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void preEdgeAdded(GraphEvent e)
    {
    }

    /**
     * Called just before an edge is removed from the graph.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void preEdgeRemoved(GraphEvent e)
    {
    }

    /**
     * Called before the edge is going to be reversed.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void preEdgeReversed(EdgeEvent e)
    {
    }

    /**
     * Called before method <code>clear()</code> is called on a graph. No other
     * events (like remove events) are generated.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void preGraphCleared(GraphEvent e)
    {
    }

    /**
     * Called just before an incoming edge is added to the node. (For
     * undirected edges preUndirectedEdgeAdded is called instead.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void preInEdgeAdded(NodeEvent e)
    {
    }

    /**
     * Called just before an incoming edge is removed from the node. (For
     * undirected edges preUndirectedEdgeRemoved is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void preInEdgeRemoved(NodeEvent e)
    {
    }

    /**
     * Called just before a node is added to the graph.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void preNodeAdded(GraphEvent e)
    {
    }

    /**
     * Called just before a node is removed from the graph. This method is
     * called before the incident edges are deleted.
     *
     * @param e the GraphEvent detailing the changes.
     */
    public void preNodeRemoved(GraphEvent e)
    {
    }

    /**
     * Called just before an outgoing edge is added to the node. (For
     * undirected edges preUndirectedEdgeAdded is called instead.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void preOutEdgeAdded(NodeEvent e)
    {
    }

    /**
     * Called just before an outgoing edge is removed from the node. (For
     * undirected edges preUndirectedEdgeRemoved is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void preOutEdgeRemoved(NodeEvent e)
    {
    }

    /**
     * Called before a change of the source node of an edge takes place.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void preSourceNodeChanged(EdgeEvent e)
    {
    }

    /**
     * Called before a change of the target node of an edge takes place.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void preTargetNodeChanged(EdgeEvent e)
    {
    }

    /**
     * Called just before an (undirected) edge is added to the node. (For
     * directed edges pre- In/Out- EdgeAdded is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void preUndirectedEdgeAdded(NodeEvent e)
    {
    }

    /**
     * Called just before an (undirected) edge is removed from the node. (For
     * directed edges pre- In/Out- EdgeRemoved is called.)
     *
     * @param e The NodeEvent detailing the changes.
     */
    public void preUndirectedEdgeRemoved(NodeEvent e)
    {
    }

    /**
     * Removes a message listener from the view.
     *
     * @param ml a message listener
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    public void removeMessageListener(MessageListener ml)
    {
        if(ml == null)
            throw new IllegalArgumentException("The argument may not be null");

        this.messageListeners.remove(ml);
    }

    /**
     * Called when a transaction has stopped.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void transactionFinished(TransactionEvent e, BackgroundTaskStatusProviderSupportingExternalCall status)
    {
    }

    /**
     * Called when a transaction has started.
     *
     * @param e the EdgeEvent detailing the changes.
     */
    public void transactionStarted(TransactionEvent e)
    {
    }

    /*
     * @see org.graffiti.plugin.view.ZoomListener#zoomChanged(java.awt.geom.AffineTransform)
     */
    public void zoomChanged(AffineTransform newZoom)
    {
        this.zoom = newZoom;
        repaint();
    }

    /**
     * Extracts the name of the view class. It has to be overridden by all
     * extended subclasses of this class.
     *
     * @return DOCUMENT ME!
     */
    protected abstract String extractName();

    /**
     * Method <code>informMessageListener</code> informs all registered message
     * listener about a new message having to be displayed.
     *
     * @param message a new message having to be displayed
     * @param type a type of the new message (e.g. ERROR or INFO)
     */
    protected void informMessageListener(String message, MessageType type)
    {
        for(MessageListener ml : messageListeners)
        {
        	ml.showMesssage(sBundle.getString(message), type);
        }
    }
    
    public Object getViewToolbarComponentTop() {
    	return null;
    }

	public JComponent getViewToolbarComponentBackground() {
		return null;
	}

	public Object getViewToolbarComponentBottom() {
		return null;
	}

	public Object getViewToolbarComponentLeft() {
		return null;
	}

	public Object getViewToolbarComponentRight() {
		return null;
	}

	public void closing(AWTEvent e) {
		// empty
	}

	public boolean worksWithTab(InspectorTab tab) {
		return true;
	}
	
	public boolean redrawActive() {
		return false;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
