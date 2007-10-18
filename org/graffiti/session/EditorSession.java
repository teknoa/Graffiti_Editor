//==============================================================================
//
//   EditorSession.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: EditorSession.java,v 1.2 2007/10/18 11:28:48 klukas Exp $

package org.graffiti.session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.URI;

import java.util.HashMap;
import java.util.Map;

import javax.swing.undo.UndoManager;

import org.graffiti.graph.AdjListGraph;
import org.graffiti.graph.Graph;
import org.graffiti.graph.GraphElement;

import org.graffiti.selection.SelectionModel;

/**
 * Contains an editor session.  An editor session contains a list of views,
 * which can manipulate the graph object. It also contains the current editor
 * mode and the selection model.
 *
 * @version $Revision: 1.2 $
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

    /**
     * The file name of the graph object, if available. Else <code>null</code>.
     */
    private URI fileName;
    
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
    public void setFileName(URI fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Returns the fileName of this session's graph.
     *
     * @return the fileName of this session's graph.
     */
    public URI getFileName()
    {
        return fileName;
    }

    /**
     * An auxiliary method for querying for the string name of graph file of
     * this session.
     *
     * @return a name of the graph file as string
     */
    public String getFileNameAsString()
    {
        String name;

        if(fileName != null && fileName.getPath() != null && fileName.getPath().lastIndexOf('/')>0)
        {
            String path = fileName.getPath();
            int idx = path.lastIndexOf('/');
            name = path.substring(idx + 1);
        } else {
        	if (title==null) {
        		name = "Unnamed "+newCounter;
        		newCounter=newCounter+1;
        	} else name=title;
        }
        
        title=name;

        return name;
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
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
