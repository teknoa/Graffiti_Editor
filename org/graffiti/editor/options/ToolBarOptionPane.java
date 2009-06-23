//==============================================================================
//
//   ToolBarOptionPane.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: ToolBarOptionPane.java,v 1.2 2009/06/23 07:14:49 klukas Exp $

package org.graffiti.editor.options;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.graffiti.options.AbstractOptionPane;

/**
 * A tool bar editor.
 *
 * @version $Revision: 1.2 $
 */
public class ToolBarOptionPane
    extends AbstractOptionPane
{
    //~ Instance fields ========================================================

    /** DOCUMENT ME! */
    private DefaultComboBoxModel iconList;

    /** DOCUMENT ME! */
    private DefaultListModel listModel;

    /** DOCUMENT ME! */
    private JButton add;

    /** DOCUMENT ME! */
    private JButton moveDown;

    /** DOCUMENT ME! */
    private JButton moveUp;

    /** DOCUMENT ME! */
    private JButton remove;

    /** DOCUMENT ME! */
    private JCheckBox showToolbar;

    /** DOCUMENT ME! */
    private JList list;

    /** The editor's preferences. */
    private Preferences prefs;

    //~ Constructors ===========================================================

    /**
     * Constructor for ToolBarOptionPane.
     *
     * @param prefs DOCUMENT ME!
     */
    public ToolBarOptionPane(Preferences prefs)
    {
        super("toolbar");
        this.prefs = prefs.node("toolbar");
    }

    //~ Methods ================================================================

    /*
     * @see org.graffiti.options.AbstractOptionPane#initDefault()
     */
    @Override
	protected void initDefault()
    {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 1));

        // show tool bar
        showToolbar = new JCheckBox(sBundle.getString(
                    "options.toolbar.showToolBar"));
        showToolbar.setSelected(prefs.getBoolean("view.showToolBar", true));

        panel.add(showToolbar);

        panel.add(new JLabel(sBundle.getString("options.toolbar.caption")));
        add(BorderLayout.NORTH, panel);

        // TODO
    }

    /*
     * @see org.graffiti.options.AbstractOptionPane#saveDefault()
     */
    @Override
	protected void saveDefault()
    {
        // TODO
    }

    //~ Inner Classes ==========================================================

    /**
     * The action handler for this option panel.
     */
    protected class ActionHandler
        implements ActionListener
    {
        /**
         * DOCUMENT ME!
         *
         * @param e DOCUMENT ME!
         */
        public void actionPerformed(ActionEvent e)
        {
        }
    }

    /**
     * The tool bar edit dialog.
     */
    protected class ToolBarEditDialog
        extends JDialog
    {
        /**
         * Constructs a new tool bar editor instance.
         *
         * @param parent the parent frame.
         */
        public ToolBarEditDialog(Frame parent)
        {
        }

        /**
         * The action handler for the edit dialog.
         */
        protected class ActionHandler
            implements ActionListener
        {
            /**
             * DOCUMENT ME!
             *
             * @param e DOCUMENT ME!
             */
            public void actionPerformed(ActionEvent e)
            {
            }
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
