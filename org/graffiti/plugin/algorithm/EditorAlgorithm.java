//==============================================================================
//
//   EditorAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditorAlgorithm.java,v 1.1 2007/06/14 09:36:47 klukas Exp $

package org.graffiti.plugin.algorithm;

import org.graffiti.editor.dialog.ParameterDialog;

import org.graffiti.selection.Selection;

/**
 *
 */
public interface EditorAlgorithm
    extends Algorithm
{
    //~ Methods ================================================================

    /**
     * Returns a custom <code>ParameterDialog</code> if the algorithm wants to
     * provide one. If this method returns null, a generic dialog will be
     * generated using standard <code>EditComponent</code>s.
     *
     * @return DOCUMENT ME!
     */
    public ParameterDialog getParameterDialog(Selection sel);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
