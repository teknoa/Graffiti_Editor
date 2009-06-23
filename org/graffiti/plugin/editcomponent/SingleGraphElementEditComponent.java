//==============================================================================
//
//   SingleGraphElementEditComponent.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: SingleGraphElementEditComponent.java,v 1.2 2009/06/23 07:14:48 klukas Exp $

/* Generated by Together */
package org.graffiti.plugin.editcomponent;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.AttributeHelper;
import org.graffiti.graph.GraphElement;
import org.graffiti.graph.Node;
import org.graffiti.plugin.Displayable;

/**
 * This class provides an edit component for graph elements.
 *
 * @see javax.swing.JComboBox
 * @see NodeEditComponent
 * @see EdgeEditComponent
 * @see org.graffiti.graph.GraphElement
 */
public abstract class SingleGraphElementEditComponent
    extends GraphComponentEditComponent {
    
    class MyGraphElementForComboBox {
        String title;
        GraphElement value;
        
        MyGraphElementForComboBox(String title, GraphElement value) {
            this.title=title;
            this.value=value;
        }
        
        @Override
		public String toString() {
            return title;
        }
    }

    //~ Instance fields ========================================================

    /** The <code>GraphElement</code> to be edited. */
    protected GraphElement ge;

    /** The <code>CombobBox</code> for selecting a graph element. */
    protected JComboBox comboBox;

    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>SingleGraphElementEditComponent</code>.
     *
     * @param disp DOCUMENT ME!
     */
    protected SingleGraphElementEditComponent(Displayable disp)
    {
        super(disp);
        
        final SingleGraphElementEditComponent thisComp = this;
        
        comboBox=new JComboBox();
        comboBox.setOpaque(false);
        Object[] possibilities = getPossibilities();
        
     
        
        for (int i=0; i<possibilities.length; i++) {
            GraphElement gElem = (GraphElement) possibilities[i];
            String title;
            if (gElem instanceof Node) {
                String lbl = AttributeHelper.getLabel(gElem, "unlabeled");
                title = "Node #"+(i+1)+": "+lbl;
            } else
                title = "Element #"+(i+1)+": "+AttributeHelper.getLabel(gElem, "unlabeled");
            
            MyGraphElementForComboBox comp = new MyGraphElementForComboBox(title, gElem);
            comboBox.addItem(comp);
        }
        int pw = comboBox.getPreferredSize().width;
        if (pw>100)
        	pw = 100;
        comboBox.setPreferredSize(new Dimension(pw, comboBox.getPreferredSize().height));
        comboBox.setSelectedItem(disp.getValue());
        
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent src) {
                JComboBox jc = (JComboBox) src.getSource();
                Object obj = jc.getSelectedItem();
                if (obj!=null) {
	                MyGraphElementForComboBox item = (MyGraphElementForComboBox) obj;
	                thisComp.setGraphElement(item.value);
                }
            }});
        ge=(GraphElement) disp.getValue();
        setEditFieldValue();
    }

    //~ Methods ================================================================
    /**
     * Returns a String suitable for the name column in the name value table.
     *
     * @return a String suitable for the name column in the name value table.
     */
    public String getCaption()
    {
        return ge.getAttribute("graphics.label").getId();
    }

    /**
     * Returns the <code>ValueEditComponent</code>'s <code>JComponent</code>.
     *
     * @return DOCUMENT ME!
     *
     * @throws RuntimeException DOCUMENT ME!
     */
    public JComponent getComponent()
    {
        return comboBox;
    }

    /**
     * Sets the current value of the <code>Attribute</code> in the
     * corresponding <code>JComponent</code>.
     */
    public void setEditFieldValue()
    {
        for (int i=0; i<comboBox.getItemCount(); i++) {
            MyGraphElementForComboBox mc = (MyGraphElementForComboBox) comboBox.getItemAt(i);
            if (ge==mc.value) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Sets the graph component selectable by this edit component.
     */
    @Override
	public void setGraphElement(GraphElement ge)
    {
        this.ge=ge;
        displayable.setValue(ge);
    }
    
    protected Object[] getPossibilities() {
        throw new RuntimeException("implement me"); // this is overridden by NodeEditComponent
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
