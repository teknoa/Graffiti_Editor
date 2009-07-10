//==============================================================================
//
//   GraphComponentEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraphComponentEditComponent.java,v 1.3 2009/07/10 08:17:39 klukas Exp $

/* Generated by Together */
package org.graffiti.plugin.editcomponent;

import org.BackgroundTaskStatusProviderSupportingExternalCall;
import org.graffiti.event.AttributeEvent;
import org.graffiti.event.TransactionEvent;
import org.graffiti.graph.GraphElement;
import org.graffiti.plugin.Displayable;

/**
 * This class provides an edit component for parts of a graph.
 *
 * @see SingleGraphElementEditComponent
 * @see SelectionEditComponent
 */
public abstract class GraphComponentEditComponent
    extends AbstractValueEditComponent
{
    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>GraphElementEditComponent</code>.
     *
     * @param disp DOCUMENT ME!
     */
    protected GraphComponentEditComponent(Displayable disp)
    {
        super(disp);
    }

    //~ Methods ================================================================

    /**
     * Sets the graph component selectable by this edit component.
     */
    public abstract void setGraphElement(GraphElement graphElement);

    /**
     * Called after an attribute as been added.
     *
     * @param e the AttributeEvent detailing changes.
     */
    @Override
	public void postAttributeAdded(AttributeEvent e)
    {
    }

    /**
     * Called after a change of an attribute took place.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    @Override
	public void postAttributeChanged(AttributeEvent e)
    {
    }

    /**
     * Called just before an attribute is removed.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    @Override
	public void postAttributeRemoved(AttributeEvent e)
    {
    }

    /**
     * Called just before an attribute is added.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    @Override
	public void preAttributeAdded(AttributeEvent e)
    {
    }

    /**
     * Called before a change of an attribute takes place.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    @Override
	public void preAttributeChanged(AttributeEvent e)
    {
    }

    /**
     * Called just before an attribute is removed.
     *
     * @param e the AttributeEvent detailing the changes.
     */
    @Override
	public void preAttributeRemoved(AttributeEvent e)
    {
    }

    /**
     * Called if a transaction got finished.
     *
     * @param t the transaction event.
     */
    @Override
	public void transactionFinished(TransactionEvent t, BackgroundTaskStatusProviderSupportingExternalCall status)
    {
    }

    /**
     * Called if a transaction got started.
     *
     * @param t the transaction event.
     */
    @Override
	public void transactionStarted(TransactionEvent t)
    {
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
