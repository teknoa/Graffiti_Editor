//==============================================================================
//
//   AttributeComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AttributeComponent.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.plugin.view;

import java.awt.Point;

import javax.swing.JPanel;

import org.graffiti.attributes.Attribute;

/**
 * This component represents a <code>org.graffiti.attributes.Attribute</code>.
 *
 * @version $Revision: 1.1 $
 */
public abstract class AttributeComponent
    extends JPanel
    implements GraffitiViewComponent
{
    //~ Methods ================================================================

    /**
     * Sets an instance of attribute which this component displays.
     *
     * @param attr
     */
    public abstract void setAttribute(Attribute attr);

    /**
     * Returns the attribute that is displayed by this component.
     *
     * @return the attribute that is displayed by this component.
     */
    public abstract Attribute getAttribute();

    /**
     * Sets shape of graph element to which the attribute of this component
     * belongs.
     *
     * @param geShape
     */
    public abstract void setGraphElementShape(GraphElementShape geShape);
    
    public abstract void adjustComponentSize();

    /**
     * DOCUMENT ME!
     *
     * @param shift DOCUMENT ME!
     */
    public abstract void setShift(Point shift);

    /**
     * Called when a graphics attribute of the attribute represented by this
     * component has changed.
     *
     * @param attr the attribute that has triggered the event.
     */
    public abstract void attributeChanged(Attribute attr)
        throws ShapeNotFoundException;

    /**
     * Used when the shape changed in the datastructure. Makes the painter to
     * create a new shape.
     */
    public abstract void recreate()
        throws ShapeNotFoundException;
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
