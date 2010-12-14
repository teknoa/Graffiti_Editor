// ==============================================================================
//
// UnsatisfiedConstraintException.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: UnsatisfiedConstraintException.java,v 1.4 2010/12/14 07:02:13 morla Exp $

package org.graffiti.session;

/**
 * An <code>UnsatisfiedConstraintException</code> is thrown when a constraint
 * to a graph is not satisfied.
 * 
 * @see java.lang.Exception
 */
public class UnsatisfiedConstraintException
					extends Exception {
	// ~ Constructors ===========================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new <code>UnsatisfiedConstraintException</code> with <code>null</code> as its detail message.
	 */
	public UnsatisfiedConstraintException() {
		super();
	}

	/**
	 * Constructs a new <code>UnsatisfiedConstraintException</code> with the
	 * specifiecd detail message.
	 * 
	 * @param msg
	 *           DOCUMENT ME!
	 */
	public UnsatisfiedConstraintException(String msg) {
		super(msg);
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
