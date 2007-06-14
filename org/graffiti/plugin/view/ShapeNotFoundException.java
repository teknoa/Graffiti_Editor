//==============================================================================
//
//   ShapeNotFoundException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ShapeNotFoundException.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.plugin.view;

/**
 * DOCUMENT ME!
 *
 * @author schoeffl To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class ShapeNotFoundException
    extends Exception
{
    //~ Constructors ===========================================================

    /**
     * Constructs a ShapeNotFoundException.
     *
     * @param msg the message to set.
     */
    public ShapeNotFoundException(String msg)
    {
        super(msg);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
