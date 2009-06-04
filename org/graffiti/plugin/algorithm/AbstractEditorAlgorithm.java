//==============================================================================
//
//   AbstractEditorAlgorithm.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: AbstractEditorAlgorithm.java,v 1.5 2009/06/04 18:49:32 klukas Exp $

package org.graffiti.plugin.algorithm;

import org.graffiti.editor.MainFrame;
import org.graffiti.editor.dialog.ParameterDialog;
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

	protected MainFrame getMainFrame() {
		return MainFrame.getInstance();
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
