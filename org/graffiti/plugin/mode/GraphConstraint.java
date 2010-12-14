// ==============================================================================
//
// GraphConstraint.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GraphConstraint.java,v 1.4 2010/12/14 07:02:14 morla Exp $

package org.graffiti.plugin.mode;

import org.graffiti.graph.Graph;
import org.graffiti.session.UnsatisfiedConstraintException;

/**
 * A <code>GraphConstraint</code> is a constraint to the graph which can be
 * validated and which is supposed to be satisfied all the time. <code>GraphConstraints</code> can be combined in an arbitrary way.
 * 
 * @see org.graffiti.session.GraphConstraintChecker
 */
public interface GraphConstraint {
	// ~ Methods ================================================================

	/**
	 * Checks whether the specified graph satisfies the defined constraint.
	 * 
	 * @throws UnsatisfiedConstraintException
	 *            if the graph does not satisfy the
	 *            defined constraint.
	 */
	public void validate(Graph g)
						throws UnsatisfiedConstraintException;
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
