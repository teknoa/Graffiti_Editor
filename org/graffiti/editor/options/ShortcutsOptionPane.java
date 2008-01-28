//==============================================================================
//
//   ShortcutsOptionPane.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ShortcutsOptionPane.java,v 1.1 2007/06/14 09:36:44 klukas Exp $

package org.graffiti.editor.options;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.graffiti.options.AbstractOptionPane;

/**
 * An option pane for shortcuts.
 *
 * @author flierl
 * @version $Revision: 1.1 $
 */
public class ShortcutsOptionPane
    extends AbstractOptionPane
{
    //~ Instance fields ========================================================

    /** The combobox for the short cut selection. */
    private JComboBox selectModel;

    /** The key table. */
    private JTable keyTable;

    /**
     * The shortcuts models.
     *
     * @see ShortcutsModel
     */
    private List models;

    /** The current shortcuts model. */
    private ShortcutsModel currentModel;

    //~ Constructors ===========================================================

    /**
     * Constructor for ShortcutsOptionPane.
     */
    public ShortcutsOptionPane()
    {
        super("shortcuts");
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.options.AbstractOptionPane#initDefault()
     */
    protected void initDefault()
    {
        // TODO
    }

    /*
     * @see org.graffiti.options.AbstractOptionPane#saveDefault()
     */
    protected void saveDefault()
    {
        // TODO
    }

    /**
     * Initializes the list of shortcuts models.
     */
    private void initShortcutsModels()
    {
        // TODO
    }

    //~ Inner Classes ==========================================================

    /**
     * The table of shortcuts.
     */
    protected class ShortcutsModel
        extends AbstractTableModel
    {
        /** Contains a list of key bindings. */
        private List bindings;

        /** The name of the shortcuts model. */
        private String name;

        /**
         * Constructs a new shortcuts model.
         *
         * @param name the name of the model.
         * @param bindings list of keybindings.
         */
        ShortcutsModel(String name, List bindings)
        {
            // TODO
        }

        /**
         * Return the number of columns for this table (3).
         *
         * @return DOCUMENT ME!
         */
        public int getColumnCount()
        {
            return 3;
        }

        /**
         * Returns the number of rows of this table.
         *
         * @return the number of rows of this table.
         */
        public int getRowCount()
        {
            return bindings.size();
        }

        /**
         * DOCUMENT ME!
         *
         * @param row DOCUMENT ME!
         * @param col DOCUMENT ME!
         *
         * @return DOCUMENT ME!
         */
        public Object getValueAt(int row, int col)
        {
            return null; // TODO
        }
    }

	/* (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#getCategory()
	 */
	public String getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.graffiti.options.OptionPane#getOptionName()
	 */
	public String getOptionName() {
		// TODO Auto-generated method stub
		return null;
	}

    /* (non-Javadoc)
     * @see org.graffiti.options.OptionPane#init(javax.swing.JComponent)
     */
    public void init(JComponent options) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.graffiti.options.OptionPane#save(javax.swing.JComponent)
     */
    public void save(JComponent options) {
        // TODO Auto-generated method stub
        
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------