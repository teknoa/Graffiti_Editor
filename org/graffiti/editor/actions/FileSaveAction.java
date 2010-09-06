//==============================================================================
//
//   FileSaveAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: FileSaveAction.java,v 1.11 2010/09/06 07:15:44 morla Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;

import org.ErrorMsg;
import org.graffiti.editor.MainFrame;
import org.graffiti.editor.MessageType;
import org.graffiti.help.HelpContext;
import org.graffiti.managers.IOManager;
import org.graffiti.plugin.actions.GraffitiAction;
import org.graffiti.plugin.io.OutputSerializer;
import org.graffiti.session.EditorSession;
import org.graffiti.session.SessionManager;

/**
 * The action for saving a graph.
 *
 * @version $Revision: 1.11 $
 */
public class FileSaveAction
extends GraffitiAction
{
	//~ Instance fields ========================================================

	private static final long serialVersionUID = 1L;

	/** DOCUMENT ME! */
	private IOManager ioManager;

	/** DOCUMENT ME! */
	private SessionManager sessionManager;

	//~ Constructors ===========================================================

	/**
	 * Creates a new FileSaveAction object.
	 *
	 * @param mainFrame DOCUMENT ME!
	 * @param ioManager DOCUMENT ME!
	 * @param sessionManager DOCUMENT ME!
	 */
	public FileSaveAction(MainFrame mainFrame, IOManager ioManager,
			SessionManager sessionManager)
	{
		super("file.save", mainFrame, "filemenu_save");
		this.ioManager = ioManager;
		this.sessionManager = sessionManager;
	}

	//~ Methods ================================================================

	/**
	 * DOCUMENT ME!
	 *
	 * @return <code>true</code>, if the io manager contains a working output
	 *         serializer and if the file is writeable.
	 */
	@Override
	public boolean isEnabled()
	{
		String fullName;

		try
		{
			// these commands fail if the session has not yet been saved to
			// a file
			EditorSession session = (EditorSession) mainFrame.getActiveSession();
			fullName = session.getFileNameFull();
		}
		catch(Exception e)
		{
			return false;
		}

		try
		{
			String ext = getFileExt(fullName);

			File file = new File(fullName);

			if(file.canWrite())
			{
				ioManager.createOutputSerializer("." + ext);

				// runtime error check, if exception, ioManager can not
				// handle current file for saving.
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}

		return (ioManager.hasOutputSerializer() &&
				sessionManager.isSessionActive());
	}

	/**
	 * @see org.graffiti.plugin.actions.GraffitiAction#getHelpContext()
	 */
	@Override
	public HelpContext getHelpContext()
	{
		return null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void actionPerformed(ActionEvent e)
	{
		// CK, 1.Juli.2003 Copied and modified from SaveAsAction
		EditorSession session;
		String fullName;

		try
		{
			session = (EditorSession) mainFrame.getActiveSession();
			fullName = session.getFileNameFull();
			if (session!=null && session.getUndoManager()!=null)
				session.getUndoManager().discardAllEdits();
		}
		catch(Exception err)
		{
			MainFrame.showMessage("Could not save graph to file.", MessageType.ERROR);
			ErrorMsg.addErrorMessage(err);
			return;
		}

		String ext = getFileExt(fullName);

		File file = new File(fullName);

		if(file.canWrite())
		{
			try
			{
				OutputSerializer os = ioManager.createOutputSerializer(ext);
				if (os==null) {
					MainFrame.showMessageDialog("Unknown outputserializer for file extension "+ext, "Error");
				} else {
					os.write(new FileOutputStream(file), getGraph());
					FileHandlingManager.getInstance().throwFileSaved(file, ext, getGraph());
					getGraph().setModified(false);
					long fs = file.length();
					MainFrame.showMessage("Graph saved to file "+file.getAbsolutePath()+" ("+(fs/1024)+"KB)", MessageType.INFO);
					// a recent menu entry will be already built, if a graph is loaded or "saved as"... so we dont need to add a menu entry here
					//MainFrame.getInstance().addNewRecentFileMenuItem(file);
				}
			}
			catch(Exception ioe)
			{
				ErrorMsg.addErrorMessage(ioe);
				MainFrame.getInstance().warnUserAboutFileSaveProblem(ioe);
			}

			mainFrame.fireSessionDataChanged(session);
		}
		else
		{
			MainFrame.showMessage("Error: file not writable.",
					MessageType.ERROR);
			System.err.println("Error: file not writable. (FileSave-Action).");
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param fileName
	 *
	 * @return Returns file extension from a given filename.
	 */
	public static String getFileExt(String fileName)
	{
		String workName;

		int lastSep = fileName.lastIndexOf(File.pathSeparator);

		if(lastSep == -1)
		{
			// no extension
			workName = fileName;
		}
		else
		{
			workName = fileName.substring(lastSep + 1);
		}

		int lastDot = workName.lastIndexOf('.');

		if(lastDot == -1)
		{
			return "";
		}
		else
		{
			boolean gz = false;
			if (workName.toUpperCase().endsWith(".GZ")) {
				workName = workName.substring(0, workName.length()-".gz".length());
				gz = true;
			}
			lastDot = workName.lastIndexOf('.');
			String extension = workName.substring(lastDot);
			if (gz)
				extension = extension+".gz";
			return extension;
		}
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
