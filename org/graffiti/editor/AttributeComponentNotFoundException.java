//==============================================================================
//
//   AttributeComponentNotFoundException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AttributeComponentNotFoundException.java,v 1.1 2007/06/14 09:36:45 klukas Exp $

package org.graffiti.editor;

/**
 * DOCUMENT ME!
 *
 * @author schoeffl
 */
public class AttributeComponentNotFoundException
    extends Exception
{
    //~ Constructors ===========================================================

    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor for AttributeComponentNotFoundException.
     *
     * @param message
     */
    public AttributeComponentNotFoundException(String message)
    {
        super(message);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
