//==============================================================================
//
//   LoadSaveOptionsPane.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: LoadSaveOptionsPane.java,v 1.4 2010/07/19 14:05:42 morla Exp $

package org.graffiti.editor.options;

import javax.swing.JComponent;

import org.graffiti.options.AbstractOptionPane;

/**
 * Handles some loading and saving stuff, e.g.: autosave, backups,
 * backupDirectory, backupSuffix.
 *
 * @version $Revision: 1.4 $
 */
public class LoadSaveOptionsPane
extends AbstractOptionPane
{
	//~ Constructors ===========================================================

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for LoadSaveOptionsPane.
	 */
	public LoadSaveOptionsPane()
	{
		super("loadsave");

		// TODO
	}

	//~ Methods ================================================================

	/*
	 * @see org.graffiti.options.AbstractOptionPane#initDefault()
	 */
	@Override
	protected void initDefault()
	{
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
