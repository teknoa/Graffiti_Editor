//==============================================================================
//
//   ShapeNotFoundException.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ShapeNotFoundException.java,v 1.3 2010/07/19 14:05:42 morla Exp $

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
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
