//==============================================================================
//
//   FileSaveAsAction.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: FileSaveAsAction.java,v 1.8 2008/06/19 07:25:38 klukas Exp $

package org.graffiti.editor.actions;

import java.awt.event.ActionEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.ErrorMsg;
import org.OpenFileDialogService;
import org.graffiti.core.GenericFileFilter;
import org.graffiti.core.StringBundle;

import org.graffiti.editor.MainFrame;
import org.graffiti.editor.MessageType;

import org.graffiti.graph.Graph;
import org.graffiti.help.HelpContext;

import org.graffiti.managers.IOManager;

import org.graffiti.plugin.actions.GraffitiAction;
import org.graffiti.plugin.io.OutputSerializer;

import org.graffiti.session.EditorSession;
import org.graffiti.session.SessionManager;

/**
 * The action for saving a graph to a named file.
 *
 * @version $Revision: 1.8 $
 */
public class FileSaveAsAction
    extends GraffitiAction
{
    //~ Instance fields ========================================================
	private static final long serialVersionUID = 1L;

	/** DOCUMENT ME! */
    private IOManager ioManager;

    /** DOCUMENT ME! */
    private SessionManager sessionManager;

    /** DOCUMENT ME! */
    private StringBundle sBundle;

    //~ Constructors ===========================================================

    //    private JFileChooser fc;
    public FileSaveAsAction(MainFrame mainFrame, IOManager ioManager,
        SessionManager sessionManager, StringBundle sBundle)
    {
        super("file.saveAs", mainFrame, "filemenu_saveas");
        this.ioManager = ioManager;
        this.sessionManager = sessionManager;
        this.sBundle = sBundle;

        //        fc = new JFileChooser();
    }

    //~ Methods ================================================================

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isEnabled()
    {
        return ioManager.hasOutputSerializer() &&
        sessionManager.isSessionActive();
    }

    /**
     * @see org.graffiti.plugin.actions.GraffitiAction#getHelpContext()
     */
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
        JFileChooser fc = ioManager.createSaveFileChooser();

        
        
        OpenFileDialogService.setActiveDirectoryFor(fc);
        
        try {
        	String n = getGraph().getName(true);
        	String on = n;
        	if (n.endsWith("*"))
        		n = n.substring(0, n.length()-1);
        	n = n.replaceAll("%20", " ");
        	n = ErrorMsg.stringReplace(n, "[not saved]", "new file");
        	n = n.trim();
        	if (n!=null && n.length()>0 && on.equals(n) && new File(n).canWrite())
        		fc.setSelectedFile(new File(n));
        	else
        		fc.setSelectedFile(new File(getGraph().getName(false).trim()));
        } catch(Exception err) {
        	// empty
        }
        
        boolean needFile = true;

        while(needFile)
        {
            int returnVal = fc.showDialog(mainFrame, sBundle.getString("menu.file.saveAs"));

            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                File file = fc.getSelectedFile();
         		String ext = ((GenericFileFilter) fc.getFileFilter()).getExtension();
                needFile = safeFile(file, ext, getGraph());
                if (!needFile) {
	        		 EditorSession session = (EditorSession) mainFrame.getActiveSession();
	        		 
	        		 if (!file.getName().endsWith(ext))
	        			 file = new File(file.getAbsolutePath() + ext);
	      		     session.setFileName(file.toURI());
	      		     
	                 if (session!=null && session.getUndoManager()!=null)
	                 	session.getUndoManager().discardAllEdits();

	      		     
	      		     mainFrame.fireSessionDataChanged(session);
	      		     OpenFileDialogService.setActiveDirectoryFrom(fc.getCurrentDirectory());
                }
            }
            else
            {
                // leave loop
                needFile = false;
            }
        }
    }

	public static boolean safeFile(File file, String ext, Graph graph) {
		String fileName = file.getName();
		boolean needFile = true;
		 //System.err.println(fileName);

		 if(fileName.indexOf(".") == -1)
		 {
		     fileName = file.getName() + ext;
		     file = new File(file.getAbsolutePath() + ext);
		 }
		 else
		 {
		     ext = fileName.substring(fileName.lastIndexOf("."));
		 }

		 //System.err.println(fileName);
		 if(file.exists())
		 {
		     if(JOptionPane.showConfirmDialog(MainFrame.getInstance(),
		             "<html>Do you want to overwrite the existing file <i>" +
		             fileName + "</i>?</html>", "Overwrite File?",
		             JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		     {
		         needFile = false;
		     }
		 }
		 else
		 {
		     needFile = false;
		 }

		 if(!needFile)
		 {
		     try
		     {
		   	  	IOManager ioManager = MainFrame.getInstance().getIoManager();
		        OutputSerializer os = ioManager.createOutputSerializer(ext);
		        if (os==null)
		        	MainFrame.getInstance().showMessageDialog("Output serializer unknown for file extension '"+ext+"'.");
		        else {
		        	MainFrame.showMessage("Save graph to file "+file.getAbsolutePath()+"...", MessageType.PERMANENT_INFO);
			        os.write(new FileOutputStream(file), graph);
			        graph.setModified(false);
			        graph.setName(file.getAbsolutePath());
			        MainFrame.showMessage("Graph saved to file "+file.getAbsolutePath(), MessageType.INFO);
		        }
		     }
		     catch(IOException ioe)
		     {
		         MainFrame.showMessage("Error: Could not save file.",
		             MessageType.ERROR);
		         ErrorMsg.addErrorMessage(ioe);
		     }
		     catch(IllegalAccessException iae)
		     {
		         MainFrame.showMessage("Error: Could not save file.",
		             MessageType.ERROR);
		         ErrorMsg.addErrorMessage(iae);
		     }
		     catch(InstantiationException ie)
		     {
		         MainFrame.showMessage("Error: Could not save file.",
		             MessageType.ERROR);
		         ErrorMsg.addErrorMessage(ie);
		     }
		 }
		return needFile;
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
