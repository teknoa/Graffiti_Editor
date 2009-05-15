//==============================================================================
//
//   AbstractEditorAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AbstractEditorAlgorithm.java,v 1.4 2009/05/15 13:08:28 morla Exp $

package org.graffiti.plugin.algorithm;

import org.graffiti.editor.dialog.ParameterDialog;

import org.graffiti.plugin.view.View;
import org.graffiti.plugin.view.View3D;
import org.graffiti.selection.Selection;

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
