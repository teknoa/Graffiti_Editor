//==============================================================================
//
//   NumberEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: NumberEditComponent.java,v 1.1 2007/06/14 09:36:46 klukas Exp $

package org.graffiti.plugin.editcomponent;

import info.clearthought.layout.TableLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;


import org.graffiti.plugin.Displayable;

/**
 * <code>NumberEditComponent</code> provides an abstract implementation for
 * editing numerical attributes.
 *
 * @see AbstractValueEditComponent
 * @see Number
 * @see javax.swing.JTextField
 */
public abstract class NumberEditComponent
    extends AbstractValueEditComponent
{
    //~ Instance fields ========================================================

    /** The gui element of this component. */
    protected SpinnerEditComponent spinner;

    //~ Constructors ===========================================================

    /**
     * Constructs a new integer edit component.
     *
     * @param disp DOCUMENT ME!
     */
    protected NumberEditComponent(Displayable disp)
    {
        super(disp);
        spinner = new SpinnerEditComponent(disp);
    }

    //~ Methods ================================================================

    /**
     * Returns the <code>JComponent</code> associated with this value edit
     * component. In this case a JSpinner.
     *
     * @return the <code>JComponent</code> associated with this value edit
     *         component.
     */
    public JComponent getComponent()
    {
   	  // System.out.println(displayable.getName());
        JComponent defaultResult = spinner.getComponent();
        if (displayable.getIcon()!=null) {
       	 JComponent jc = displayable.getIcon();
       	 JPanel jp = (JPanel) TableLayout.getSplit(jc, defaultResult, TableLayout.PREFERRED, TableLayout.FILL);
       	 jp.setOpaque(false);
       	 return jp;
         } else
       	 return defaultResult;

    }

    /**
     * Sets the displayable.
     *
     * @param attr DOCUMENT ME!
     */
    public void setDisplayable(Displayable attr)
    {
        this.displayable = attr;
        spinner.setDisplayable(attr);
    }

    /**
     * Sets the current value of the <code>Attribute</code> in the
     * corresponding <code>JComponent</code>.
     */
    public void setEditFieldValue()
    {
        spinner.setEditFieldValue();
    }

    /*
     * @see org.graffiti.plugin.editcomponent.AbstractValueEditComponent#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        spinner.setEnabled(enabled);
    }

    /*
     * @see org.graffiti.plugin.editcomponent.AbstractValueEditComponent#setShowEmpty(boolean)
     */
    public void setShowEmpty(boolean showEmpty)
    {
        super.setShowEmpty(showEmpty);
        this.showEmpty = showEmpty;
        spinner.setShowEmpty(showEmpty);
    }

    /**
     * Sets the value of the displayable specified in the
     * <code>JComponent</code>. Calls setAttribute in the associated spinner,
     * i.e. it only changes the  value if it is different.
     */
    public void setValue()
    {
        spinner.setValue();
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
