// ==============================================================================
//
// ShortcutsOptionPane.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================
// $Id: ShortcutsOptionPane.java,v 1.5 2010/12/14 07:02:13 morla Exp $

package org.graffiti.editor.options;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.table.AbstractTableModel;

import org.graffiti.options.AbstractOptionPane;

/**
 * An option pane for shortcuts.
 * 
 * @author flierl
 * @version $Revision: 1.5 $
 */
public class ShortcutsOptionPane
					extends AbstractOptionPane {
	// ~ Instance fields ========================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for ShortcutsOptionPane.
	 */
	public ShortcutsOptionPane() {
		super("shortcuts");
	}

	// ~ Methods ================================================================

	/*
	 * @see org.graffiti.options.AbstractOptionPane#initDefault()
	 */
	@Override
	protected void initDefault() {
		// TODO
	}

	/*
	 * @see org.graffiti.options.AbstractOptionPane#saveDefault()
	 */
	@Override
	protected void saveDefault() {
		// TODO
	}

	// ~ Inner Classes ==========================================================

	/**
	 * The table of shortcuts.
	 */
	protected class ShortcutsModel
						extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/** Contains a list of key bindings. */
		private List<?> bindings;

		/**
		 * Constructs a new shortcuts model.
		 * 
		 * @param name
		 *           the name of the model.
		 * @param bindings
		 *           list of keybindings.
		 */
		ShortcutsModel(String name, List<?> bindings) {
			// TODO
		}

		/**
		 * Return the number of columns for this table (3).
		 * 
		 * @return DOCUMENT ME!
		 */
		public int getColumnCount() {
			return 3;
		}

		/**
		 * Returns the number of rows of this table.
		 * 
		 * @return the number of rows of this table.
		 */
		public int getRowCount() {
			return bindings.size();
		}

		/**
		 * DOCUMENT ME!
		 * 
		 * @param row
		 *           DOCUMENT ME!
		 * @param col
		 *           DOCUMENT ME!
		 * @return DOCUMENT ME!
		 */
		public Object getValueAt(int row, int col) {
			return null; // TODO
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#getCategory()
	 */
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#getOptionName()
	 */
	public String getOptionName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#init(javax.swing.JComponent)
	 */
	public void init(JComponent options) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#save(javax.swing.JComponent)
	 */
	public void save(JComponent options) {
		// TODO Auto-generated method stub

	}
}

// ------------------------------------------------------------------------------
// end of file
// ------------------------------------------------------------------------------
