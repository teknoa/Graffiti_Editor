//==============================================================================
//
//   InspectorTab.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: InspectorTab.java,v 1.2 2008/08/28 09:51:46 klukas Exp $

package org.graffiti.plugin.inspector;

import javax.swing.JComponent;

import org.graffiti.event.AttributeEvent;
import org.graffiti.event.AttributeListener;
import org.graffiti.event.TransactionEvent;
import org.graffiti.plugin.view.View;
import org.graffiti.session.Session;
import org.graffiti.session.SessionListener;

/**
 * An <code>InspectorTab</code> is a generic component for an
 * <code>InspectorPlugin</code>.
 *
 * @see JComponent
 * @see InspectorPlugin
 */
public abstract class InspectorTab
    extends JComponent
{
    //~ Instance fields ========================================================

    /**
     * The panel that holds the table of the attributes and the buttons for
     * adding and removing attributes as well as the "apply" button.
     */
    public EditPanel editPanel;

    /**
     * The title of the <code>InspectorTab</code> which will appear as the
     * title of the tab.
     */
    protected String title;

    //~ Methods ================================================================

    /**
     * Returns the EditPanel of this tab.
     *
     * @return DOCUMENT ME!
     */
    public EditPanel getEditPanel()
    {
        return this.editPanel;
    }

    /**
     * Returns the title of the current <code>InspectorTab</code>.
     *
     * @return the title of the current <code>InspectorTab</code>.
     */
    public String getTitle()
    {
        return this.title;
    }

	@Override
	public String getName() {
		return getTitle();
	}

	public abstract boolean visibleForView(View v);
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
