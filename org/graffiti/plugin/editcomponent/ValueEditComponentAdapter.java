// ==============================================================================
//
// ValueEditComponentAdapter.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: ValueEditComponentAdapter.java,v 1.9 2010/12/22 13:05:54 klukas Exp $

/* Generated by Together */
package org.graffiti.plugin.editcomponent;

import java.util.Collection;

import org.BackgroundTaskStatusProviderSupportingExternalCall;
import org.graffiti.event.AttributeEvent;
import org.graffiti.event.EdgeEvent;
import org.graffiti.event.GraphEvent;
import org.graffiti.event.NodeEvent;
import org.graffiti.event.TransactionEvent;
import org.graffiti.plugin.Displayable;

/**
 * Provides an adapter class implementing all listener methods.
 * 
 * @see ValueEditComponent
 * @see org.graffiti.event.NodeListener
 * @see org.graffiti.event.EdgeListener
 * @see org.graffiti.event.GraphListener
 * @see org.graffiti.event.AttributeListener
 * @see org.graffiti.event.TransactionListener
 */
public abstract class ValueEditComponentAdapter
					implements ValueEditComponent {
	// ~ Instance fields ========================================================
	
	/** DOCUMENT ME! */
	protected Displayable displayable;
	
	// ~ Constructors ===========================================================
	
	/**
	 * Constructs a new <code>ValueEditComponentAdapter</code>.
	 * 
	 * @param disp
	 *           DOCUMENT ME!
	 */
	protected ValueEditComponentAdapter(Displayable disp) {
		this.displayable = disp;
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Called after an attribute has been added.
	 * 
	 * @param e
	 *           the AttributeEvent detailing the changes.
	 */
	public void postAttributeAdded(AttributeEvent e) {
	}
	
	/**
	 * Called after an attribute has been changed.
	 * 
	 * @param e
	 *           the AttributeEvent detailing the changes.
	 */
	public void postAttributeChanged(AttributeEvent e) {
	}
	
	/**
	 * Called after an attribute has been removed.
	 * 
	 * @param e
	 *           the AttributeEvent detailing the changes.
	 */
	public void postAttributeRemoved(AttributeEvent e) {
	}
	
	/**
	 * Called after the edge was set directed or undirected.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postDirectedChanged(EdgeEvent e) {
	}
	
	/**
	 * Called after an edge has been added to the graph.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void postEdgeAdded(GraphEvent e) {
	}
	
	/**
	 * Called after an edge has been removed from the graph.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void postEdgeRemoved(GraphEvent e) {
	}
	
	/**
	 * Called after the edge has been reversed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postEdgeReversed(EdgeEvent e) {
	}
	
	/**
	 * Called after method <code>clear()</code> has been called on a graph. No
	 * other events (like remove events) are generated.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void postGraphCleared(GraphEvent e) {
	}
	
	/**
	 * Called just after an incoming edge has been added to the node. (For
	 * undirected edges postUndirectedEdgeAdded is called instead.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void postInEdgeAdded(NodeEvent e) {
	}
	
	/**
	 * Called after an incoming edge has been removed from the node. (For
	 * undirected edges postUndirectedEdgeRemoved is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void postInEdgeRemoved(NodeEvent e) {
	}
	
	/**
	 * Called after an edge has been added to the graph.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void postNodeAdded(GraphEvent e) {
	}
	
	/**
	 * Called after a node has been removed from the graph. All edges incident
	 * to this node have already been removed (preEdgeRemoved and
	 * postEdgeRemoved have been called).
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void postNodeRemoved(GraphEvent e) {
	}
	
	/**
	 * Called after an outgoing edge has been added to the node. (For
	 * undirected edges postUndirectedEdgeAdded is called instead.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void postOutEdgeAdded(NodeEvent e) {
	}
	
	/**
	 * Called after an outgoing edge has been removed from the node. (For
	 * undirected edges postUndirectedEdgeRemoved is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void postOutEdgeRemoved(NodeEvent e) {
	}
	
	/**
	 * Called after the source node of an edge has changed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postSourceNodeChanged(EdgeEvent e) {
	}
	
	/**
	 * Called after the target node of an edge has changed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void postTargetNodeChanged(EdgeEvent e) {
	}
	
	/**
	 * Called after an (undirected) edge has been added to the node. (For
	 * directed edges pre- In/Out- EdgeAdded is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void postUndirectedEdgeAdded(NodeEvent e) {
	}
	
	/**
	 * Called after an (undirected) edge has been removed from the node. (For
	 * directed edges pre- In/Out- EdgeRemoved is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void postUndirectedEdgeRemoved(NodeEvent e) {
	}
	
	/**
	 * Called just before an attribute is added.
	 * 
	 * @param e
	 *           the AttributeEvent detailing the changes.
	 */
	public void preAttributeAdded(AttributeEvent e) {
	}
	
	/**
	 * Called before a change of an attribute takes place.
	 * 
	 * @param e
	 *           the AttributeEvent detailing the changes.
	 */
	public void preAttributeChanged(AttributeEvent e) {
	}
	
	/**
	 * Called just before an attribute is removed.
	 * 
	 * @param e
	 *           the AttributeEvent detailing the changes.
	 */
	public void preAttributeRemoved(AttributeEvent e) {
	}
	
	/**
	 * Called before the edge is set directed or undirected.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preDirectedChanged(EdgeEvent e) {
	}
	
	/**
	 * Called just before an edge is added to the graph.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void preEdgeAdded(GraphEvent e) {
	}
	
	/**
	 * Called just before an edge is removed from the graph.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void preEdgeRemoved(GraphEvent e) {
	}
	
	/**
	 * Called before the edge is going to be reversed.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preEdgeReversed(EdgeEvent e) {
	}
	
	/**
	 * Called before method <code>clear()</code> is called on a graph. No other
	 * events (like remove events) are generated.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void preGraphCleared(GraphEvent e) {
	}
	
	/**
	 * Called just before an incoming edge is added to the node. (For
	 * undirected edges preUndirectedEdgeAdded is called instead.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void preInEdgeAdded(NodeEvent e) {
	}
	
	/**
	 * Called just before an incoming edge is removed from the node. (For
	 * undirected edges preUndirectedEdgeRemoved is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void preInEdgeRemoved(NodeEvent e) {
	}
	
	/**
	 * Called just before a node is added to the graph.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void preNodeAdded(GraphEvent e) {
	}
	
	/**
	 * Called just before a node is removed from the graph. This method is
	 * called before the incident edges are deleted.
	 * 
	 * @param e
	 *           the GraphEvent detailing the changes.
	 */
	public void preNodeRemoved(GraphEvent e) {
	}
	
	/**
	 * Called just before an outgoing edge is added to the node. (For
	 * undirected edges preUndirectedEdgeAdded is called instead.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void preOutEdgeAdded(NodeEvent e) {
	}
	
	/**
	 * Called just before an outgoing edge is removed from the node. (For
	 * undirected edges preUndirectedEdgeRemoved is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void preOutEdgeRemoved(NodeEvent e) {
	}
	
	/**
	 * Called before a change of the source node of an edge takes place.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preSourceNodeChanged(EdgeEvent e) {
	}
	
	/**
	 * Called before a change of the target node of an edge takes place.
	 * 
	 * @param e
	 *           the EdgeEvent detailing the changes.
	 */
	public void preTargetNodeChanged(EdgeEvent e) {
	}
	
	/**
	 * Called just before an (undirected) edge is added to the node. (For
	 * directed edges pre- In/Out- EdgeAdded is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void preUndirectedEdgeAdded(NodeEvent e) {
	}
	
	/**
	 * Called just before an (undirected) edge is removed from the node. (For
	 * directed edges pre- In/Out- EdgeRemoved is called.)
	 * 
	 * @param e
	 *           The NodeEvent detailing the changes.
	 */
	public void preUndirectedEdgeRemoved(NodeEvent e) {
	}
	
	/**
	 * Called if a transaction got finished.
	 * 
	 * @param t
	 *           the transaction event.
	 */
	public void transactionFinished(TransactionEvent t, BackgroundTaskStatusProviderSupportingExternalCall status) {
	}
	
	/**
	 * Called if a transaction got started.
	 * 
	 * @param t
	 *           the transaction event.
	 */
	public void transactionStarted(TransactionEvent t) {
	}
	
	public void setValue(Collection<Displayable> attributes) {
		for (Displayable d : attributes) {
			setDisplayable(d);
			setValue();
		}
	}
	
	public void setParameter(String setting, Object value) {
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
