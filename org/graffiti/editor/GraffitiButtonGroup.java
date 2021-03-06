// ==============================================================================
//
// GraffitiButtonGroup.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: GraffitiButtonGroup.java,v 1.5 2010/12/22 13:05:53 klukas Exp $

package org.graffiti.editor;

import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import org.graffiti.plugin.gui.ToolButton;

/**
 * Represents a button group.
 * 
 * @version $Revision: 1.5 $
 */
public class GraffitiButtonGroup {
	// ~ Instance fields ========================================================
	
	/** The internal representation of the button group. */
	private ButtonGroup buttonGroup;
	
	// ~ Constructors ===========================================================
	
	/**
	 * Creates a new GraffitiButtonGroup object.
	 */
	public GraffitiButtonGroup() {
		this.buttonGroup = new ButtonGroup();
	}
	
	// ~ Methods ================================================================
	
	/**
	 * Returns the number of buttons in the group.
	 * 
	 * @return the button count
	 */
	public int getButtonCount() {
		return buttonGroup.getButtonCount();
	}
	
	/**
	 * Returns all the buttons that are participating in this <code>GraffitiButtonGroup</code>.
	 * 
	 * @return an <code>Enumeration</code> of the buttons in this group
	 */
	public Enumeration<?> getElements() {
		return buttonGroup.getElements();
	}
	
	/**
	 * Sets the selected value for the <code>ButtonModel</code>. Only one
	 * button in the group may be selected at a time.
	 * 
	 * @param m
	 *           <code>ButtonModel</code>
	 * @param b
	 *           <code>true</code> if this button is to be selected, otherwise <code>false</code>
	 */
	public void setSelected(ButtonModel m, boolean b) {
		buttonGroup.setSelected(m, b);
	}
	
	/**
	 * Returns whether a <code>ButtonModel</code> is selected.
	 * 
	 * @param m
	 *           DOCUMENT ME!
	 * @return <code>true</code> if the button is selected, otherwise returns <code>false</code>
	 */
	public boolean isSelected(ButtonModel m) {
		return buttonGroup.isSelected(m);
	}
	
	/**
	 * Returns the model of the selected button.
	 * 
	 * @return the selected button model
	 */
	public ButtonModel getSelection() {
		return buttonGroup.getSelection();
	}
	
	/**
	 * Adds the given button to the button group.
	 * 
	 * @param button
	 *           the button to add to the group.
	 */
	public void addButton(ToolButton button) {
		buttonGroup.add(button);
	}
	
	/**
	 * Removes the button from the group.
	 * 
	 * @param button
	 *           the button to be removed
	 */
	public void remove(ToolButton button) {
		buttonGroup.remove(button);
	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
