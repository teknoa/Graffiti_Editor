//==============================================================================
//
//   AbstractEditorAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AbstractEditorAlgorithm.java,v 1.3 2009/02/24 11:41:53 morla Exp $

package org.graffiti.plugin.algorithm;

import org.graffiti.editor.dialog.ParameterDialog;

import org.graffiti.plugin.view.View;
import org.graffiti.plugin.view.View3D;
import org.graffiti.selection.Selection;

/**
 * DOCUMENT ME!
 *
 * @author $Author: morla $
 * @version $Revision: 1.3 $ $Date: 2009/02/24 11:41:53 $
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
