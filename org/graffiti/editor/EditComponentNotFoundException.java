//==============================================================================
//
//   EditComponentNotFoundException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditComponentNotFoundException.java,v 1.1 2007/06/14 09:36:45 klukas Exp $

package org.graffiti.editor;

/**
 * Thrown if no EditComponent could be found.
 */
public class EditComponentNotFoundException
    extends Exception
{
    //~ Constructors ===========================================================

    /**
     * Constructor for AttributeComponentNotFoundException.
     *
     * @param message
     */
    public EditComponentNotFoundException(String message)
    {
        super(message);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
