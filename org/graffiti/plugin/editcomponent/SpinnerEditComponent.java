//==============================================================================
//
//   SpinnerEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: SpinnerEditComponent.java,v 1.1 2007/06/14 09:36:46 klukas Exp $

package org.graffiti.plugin.editcomponent;

import java.awt.Dimension;
import java.awt.event.FocusListener;

import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.event.ChangeEvent;

import org.ErrorMsg;
import org.graffiti.attributes.ByteAttribute;
import org.graffiti.attributes.IntegerAttribute;
import org.graffiti.attributes.LongAttribute;
import org.graffiti.attributes.ShortAttribute;

import org.graffiti.plugin.Displayable;
import org.graffiti.plugin.parameter.IntegerParameter;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $
 */
public class SpinnerEditComponent
    extends AbstractValueEditComponent
{
    //~ Instance fields ========================================================

    /** The default step witdh for floating point numbers. */
    private final Double DEFAULT_STEP = new Double(0.5d);

    /** The spinner component used. */
    private JSpinner spinner;

    //~ Constructors ===========================================================

    /**
     * Constructor for SpinnerEditComponent.
     *
     * @param disp DOCUMENT ME!
     */
    public SpinnerEditComponent(Displayable disp)
    {
        super(disp);

        SpinnerNumberModel model;

        if(disp instanceof IntegerAttribute || disp instanceof ByteAttribute ||
            disp instanceof LongAttribute || disp instanceof ShortAttribute ||
            disp instanceof IntegerParameter)
        {
            model = new SpinnerNumberModel(new Integer(0), null, null,
                    new Integer(1));
        }
        else
        {
            model = new SpinnerNumberModel(new Double(0d), null, null,
                    DEFAULT_STEP);
        }

        this.spinner = new JSpinner(model);

        //        this.spinner = new JSpinner();
         // this.spinner.setBorder(BorderFactory.createEmptyBorder());
        
        spinner.setOpaque(false);

        //this.spinner.setSize(100, 40);
        //this.spinner.setMinimumSize(new Dimension(40, 10));
        //this.spinner.setPreferredSize(new Dimension(100, 40));
        displayable = null; // ensure setDisplayable really does sth 
        this.setDisplayable(disp);
    }

    //~ Methods ================================================================

    /**
     * Returns the <code>ValueEditComponent</code>'s <code>JComponent</code>.
     *
     * @return DOCUMENT ME!
     */
    public JComponent getComponent()
    {
        //				JPanel panel = new JPanel();
        //				panel.add(this.spinner);
        //				panel.setSize(100, 40);
        //				panel.setMinimumSize(new Dimension(40, 10));
        //				panel.setPreferredSize(new Dimension(100, 40));
        //				this.spinner.setSize(100, 40);
        this.spinner.setMinimumSize(new Dimension(0, 30));
        this.spinner.setPreferredSize(new Dimension(50, 30));
        this.spinner.setMaximumSize(new Dimension(2000, 30));

        //        this.spinner.setSize(new Dimension(100, 30));
        return this.spinner;

        //				return panel;
    }

    /**
     * Sets the displayable.
     *
     * @param disp
     */
    public void setDisplayable(Displayable disp)
    {
        ////		if (this.displayable != attr) {
        this.displayable = disp;

        ////	        SpinnerNumberModel model = new SpinnerNumberModel();
        //			SpinnerNumberModel model = (SpinnerNumberModel)this.spinner.getModel();
        //
        ////			Object oldValue = spinner.getValue();
        ////			SpinnerNumberModel model; 
        ////	        // if attr contains a whole number use step width 1
        //	        if (attr instanceof IntegerAttribute
        //	            || attr instanceof ByteAttribute
        //	            || attr instanceof LongAttribute
        //	            || attr instanceof ShortAttribute
        //			    || attr instanceof IntegerParameter) {
        //
        //				model.setStepSize(new Integer(1));
        ////				model = new SpinnerNumberModel
        ////					(new Integer(0), null, null, new Integer(1));
        //////	            model.setStepSize(new Integer(1));
        //	        } else {
        ////				model = new SpinnerNumberModel
        ////					(new Double(0d), null, null, DEFAULT_STEP);
        //	   	        model.setStepSize(DEFAULT_STEP);
        //	        }
        //////	        model.setValue(attr.getValue());
        ////			this.spinner.setModel(model);
        ////			model.setValue(oldValue);
        ////		}
        //		this.spinner.setValue(disp.getValue());
    }

    /**
     * Sets the current value of the <code>Attribute</code> in the
     * corresponding <code>JComponent</code>.
     */
    public void setEditFieldValue()
    {
        //		((JTextField)this.spinner.getEditor()).setText("");
        if(showEmpty)
        {
            ((JSpinner.DefaultEditor) this.spinner.getEditor()).getTextField()
             .setText(EMPTY_STRING);
        }
        else
        {
            spinner.setValue(this.displayable.getValue());

            ChangeEvent ce = new ChangeEvent(spinner);

            for(int i = 0; i < spinner.getChangeListeners().length; i++)
            {
            	// System.out.println("LISTENER INFORMED: "+ce.toString());
                spinner.getChangeListeners()[i].stateChanged(ce);
            }
        }

        //		this.setEnabled(!this.showEmpty);
    }

    /*
     * @see org.graffiti.plugin.editcomponent.AbstractValueEditComponent#setShowEmpty(boolean)
     */
    public void setShowEmpty(boolean showEmpty)
    {
        if(this.showEmpty != showEmpty)
        {
            super.setShowEmpty(showEmpty);

            //			this.setEditFieldValue();
        }

        this.setEditFieldValue();
    }

    /**
     * Sets the value of the displayable specified in the
     * <code>JComponent</code>. But only if it is different.
     */
    public void setValue()
    {
        //System.out.println("attrvalue = " + this.displayable.getValue() + "spinnervalue = " + this.spinner.getValue());
        try
        {
        	if (spinner.getEditor()!=null && spinner.getEditor() instanceof NumberEditor) {
	        	NumberEditor ne = (NumberEditor) spinner.getEditor();
	        	String txt = ne.getTextField().getText();
	        	try {
		        	if (txt.startsWith("*")) {
		        		Double p = Double.parseDouble(txt.substring("*".length()));
		        		if (this.displayable.getValue() instanceof Double) {
		        			this.displayable.setValue((Double)this.displayable.getValue()*p);
			        		return;
		        		} else
		        		if (this.displayable.getValue() instanceof Integer) {
		        			this.displayable.setValue((int)((Integer)this.displayable.getValue()*p));
			        		return;
		        		}
		        	} else
		        	if (txt.startsWith("/")) {
		        		Double p = Double.parseDouble(txt.substring("/".length()));
		        		if (this.displayable.getValue() instanceof Double) {
		        			this.displayable.setValue((Double)this.displayable.getValue()/p);
			        		return;
		        		} else
		        		if (this.displayable.getValue() instanceof Integer) {
		        			this.displayable.setValue((int)((Integer)this.displayable.getValue()/p));
			        		return;
		        		}
		        	} else
		        	if (txt.startsWith("+")) {
		        		Double p = Double.parseDouble(txt.substring("+".length()));
		        		if (this.displayable.getValue() instanceof Double) {
		        			this.displayable.setValue((Double)this.displayable.getValue()+p);
			        		return;
		        		} else
		        		if (this.displayable.getValue() instanceof Integer) {
		        			this.displayable.setValue((int)((Integer)this.displayable.getValue()+p));
			        		return;
		        		}
		        	}
		        		
	        	} catch(NumberFormatException nfe) {
	        		//
	        	}
        	}
            spinner.commitEdit();

            if(!this.displayable.getValue().equals(this.spinner.getValue()))
                this.displayable.setValue(this.spinner.getValue());
        }
        catch(ParseException e)
        {
            // this is by the way an other way to say:
            // if spinner.getValue().equals("---") { don't change anything }
        }
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
