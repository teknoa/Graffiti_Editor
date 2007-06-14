//==============================================================================
//
//   GraffitiComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiComponent.java,v 1.1 2007/06/14 09:36:48 klukas Exp $

package org.graffiti.plugin.gui;


/**
 * Interface for all GUIComponents used in the editor. Provides the
 * <code>getPreferredComponent()</code> method.
 *
 * @version $Revision: 1.1 $
 */
public interface GraffitiComponent
{
    //~ Methods ================================================================

    /**
     * Returns the id of the component this component should be placed in.
     *
     * @return the id of the component this component should be placed in.
     */
    public String getPreferredComponent();
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
