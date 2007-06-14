//==============================================================================
//
//   ComboBoxEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ComboBoxEditComponent.java,v 1.1 2007/06/14 09:36:46 klukas Exp $

package org.graffiti.plugin.editcomponent;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.graffiti.plugin.Displayable;

/**
 * Displays a combo box to let the user choose from several possibilities.
 *
 * @version $Revision: 1.1 $
 */
public class ComboBoxEditComponent
    extends AbstractValueEditComponent
{
    //~ Instance fields ========================================================

    /** The combobox component used. */
    protected JComboBox comboBox;

    /** Text that is displayed in the combo box. */
    protected Object[] comboText;

    /** The value that corresponds to the text specified in comboText. */
    protected Object[] comboValue;

    //~ Constructors ===========================================================

    /**
     * Creates a new ComboBoxEditComponent object.
     *
     * @param disp DOCUMENT ME!
     */
    public ComboBoxEditComponent(Displayable disp)
    {
        super(disp);
    }

    //~ Methods ================================================================

    /**
     * Returns the <code>ValueEditComponent</code>'s <code>JComponent</code>.
     *
     * @return DOCUMENT ME!
     */
    public JComponent getComponent()
    {
    	comboBox.setOpaque(false);
        return this.comboBox;
    }

    /**
     * Sets the current value of the <code>Attribute</code> in the
     * corresponding <code>JComponent</code>.
     */
    public void setEditFieldValue()
    {
        Object value = this.displayable.getValue();
        if (value==null)
        	showEmpty = true;
        
        if(showEmpty)
        {
            comboBox.insertItemAt(EMPTY_STRING, 0);
            comboBox.setSelectedIndex(0);
        }
        else
        {
            if(comboBox.getItemCount()>0 && comboBox.getItemAt(0).equals(EMPTY_STRING))
            {
                comboBox.removeItemAt(0);
            }
            for(int i = comboValue.length - 1; i >= 0; i--)
            {
                if(value.equals(comboValue[i]))
                {
                    this.comboBox.setSelectedIndex(i);

                    break;
                }
            }
        }
    }

    /**
     * Sets the value of the displayable specified in the
     * <code>JComponent</code>. Probably not usefull or overwritten by
     * subclasses.
     */
    public void setValue()
    {
        if(this.comboBox.getSelectedItem().equals(EMPTY_STRING) ||
            (displayable.getValue()!=null && this.displayable.getValue().equals(this.comboBox.getSelectedItem())))
        {
            return;
        }

        if(this.comboBox.getItemAt(0).equals(EMPTY_STRING))
        {
            this.displayable.setValue(comboValue[this.comboBox.getSelectedIndex() -
                1]);
        }
        else
        {
            this.displayable.setValue(comboValue[this.comboBox.getSelectedIndex()]);
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
