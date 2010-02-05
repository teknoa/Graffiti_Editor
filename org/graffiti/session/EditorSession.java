//==============================================================================
//
//   EditorSession.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditorSession.java,v 1.12 2010/02/05 15:04:51 morla Exp $

package org.graffiti.session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.undo.UndoManager;

import org.AttributeHelper;
import org.ErrorMsg;
import org.graffiti.graph.AdjListGraph;
import org.graffiti.graph.Graph;
import org.graffiti.graph.GraphElement;
import org.graffiti.selection.SelectionModel;

/**
 * Contains an editor session.  An editor session contains a list of views,
 * which can manipulate the graph object. It also contains the current editor
 * mode and the selection model.
 *
 * @version $Revision: 1.12 $
 *
 * @see org.graffiti.session.Session
 */
public class EditorSession
    extends Session
    implements ActionListener
{
    //~ Instance fields ========================================================

    /**
     * The map between new and old graph elements for proper undoing of their
     * deleting
     */
    private Map<GraphElement, GraphElement> graphElementsMap;

    /**
     * The selectionModel in this session.
     *
     * @link aggregation
     * @clientCardinality 1
     */
    private SelectionModel selectionModel;

//    /**
//     * The file name of the graph object, if available. Else <code>null</code>.
//     */
//    private URI fileName;
    
    /**
     * This counter is used for new, unnamed sessions. Each new session now gets a name
     * like "Unnamed 1", "Unnamed 2", ... instead of only "New graph". 
     */
    private static int newCounter=1;
    
    /**
     * Used for returning the correct name in case a new view is created for a unnamed window.
     * Then no new name like "Unnamed 1" should be returned. 
     */
    private String title=null;

    //    /**
    //     * The list of views.
    //     * 
    //     * @see org.graffiti.view.GraffitiView
    //     */
    //    private ArrayList views;

    /** The undoManager for this session. */
    private UndoManager um;

    /**
     * The &quot;closing&quot; state of this session.  <code>true</code>, if
     * this session is currently closing.
     */
    private boolean closing = false;

    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>EditorSession</code> with an empty graph
     * instance.
     */
    public EditorSession()
    {
        this(new AdjListGraph());

        //this.selectionModel = new SelectionModel();
    }

    /**
     * Constructs a new <code>EditorSession</code>.
     *
     * @param graph the <code>Graph</code> object for this session.
     */
    public EditorSession(Graph graph)
    {
        super(graph);
        um = new UndoManager();
        um.setLimit(5);
        graphElementsMap = new HashMap<GraphElement, GraphElement>();

        //        this.selectionModel = new SelectionModel();
        //        this.selectionModel.add(new Selection(ACTIVE));
        //        this.selectionModel.setActiveSelection(ACTIVE);
    }

    //~ Methods ================================================================

    /**
     * Sets the closing state of this session.  This may only be done once.
     *
     * @throws RuntimeException DOCUMENT ME!
     */
    public void setClosing()
    {
        if(closing)
        {
            throw new RuntimeException("The session \"" + this.toString() +
                "\" is already in the closing state.");
        }
        else
        {
            closing = true;
        }
    }

    /**
     * Returns <code>true</code>, if the session is currently closing.
     *
     * @return DOCUMENT ME!
     */
    public boolean isClosing()
    {
        return closing;
    }

    /**
     * Sets the fileName.
     *
     * @param fileName The fileName to set
     */
    public void setFileName(String fileName)
    {
    	graph.setName(fileName);
    }

    /**
     * Returns the full fileName including path of this session's graph.
     *
     * @return the fileName of this session's graph.
     */
    public String getFileNameFull()
    {
		return graph.getName(true);
    }

    /**
     * Get just the file name excluding the path
     *
     * @return a name of the graph file as string
     */
    public String getFileName()
    {
    	return graph.getName(false);
    }

    /**
     * Returns the graphElementMap.
     *
     * @return Map
     */
    public Map<GraphElement, GraphElement> getGraphElementsMap()
    {
        return graphElementsMap;
    }

    /**
     * Sets the selectionModel.
     *
     * @param selectionModel The selectionModel to set
     */
    public void setSelectionModel(SelectionModel selectionModel)
    {
        this.selectionModel = selectionModel;
    }

    /**
     * Returns the selectionModel.
     *
     * @return DOCUMENT ME!
     */
    public SelectionModel getSelectionModel()
    {
        return this.selectionModel;
    }

    /**
     * Returns the undoManager for this session.
     *
     * @return the undoManager for this session.
     */
    public UndoManager getUndoManager()
    {
        return um;
    }

    /**
     * Registrates the selected <code>Tool</code> as an
     * <code>MouseInputListener</code> at the view.
     *
     * @param e DOCUMENT ME!
     */
    public void actionPerformed(ActionEvent e)
    {
    }

	public String getWorkSessionFilePath() {
		String path = getFileNameFull();
		if (path.startsWith("http://"))
			return "";
		else
			return new File(path).getParent()+"/";
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
