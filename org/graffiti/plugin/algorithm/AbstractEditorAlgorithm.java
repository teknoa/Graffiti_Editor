//==============================================================================
//
//   AbstractEditorAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AbstractEditorAlgorithm.java,v 1.1 2007/06/14 09:36:47 klukas Exp $

package org.graffiti.plugin.algorithm;

import org.graffiti.editor.dialog.ParameterDialog;

import org.graffiti.selection.Selection;

/**
 * DOCUMENT ME!
 *
 * @author $Author: klukas $
 * @version $Revision: 1.1 $ $Date: 2007/06/14 09:36:47 $
 */
public abstract class AbstractEditorAlgorithm
    extends AbstractAlgorithm
    implements EditorAlgorithm
{
    //~ Methods ================================================================

    /*
     * @see org.graffiti.plugin.algorithm.EditorAlgorithm#getParameterDialog(org.graffiti.selection.Selection)
     */
    public ParameterDialog getParameterDialog(Selection s)
    {
        return null;
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
