package org.graffiti.editor;

import java.awt.Component;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.ErrorMsg;
import org.graffiti.editor.actions.RunAlgorithm;
import org.graffiti.editor.dialog.DefaultParameterDialog;
import org.graffiti.editor.dialog.ParameterDialog;
import org.graffiti.graph.Graph;
import org.graffiti.managers.pluginmgr.DefaultPluginEntry;
import org.graffiti.options.OptionPane;
import org.graffiti.plugin.algorithm.Algorithm;
import org.graffiti.plugin.algorithm.AlgorithmWithComponentDescription;
import org.graffiti.plugin.algorithm.CalculatingAlgorithm;
import org.graffiti.plugin.algorithm.EditorAlgorithm;
import org.graffiti.plugin.algorithm.PreconditionException;
import org.graffiti.plugin.algorithm.ProvidesGeneralContextMenu;
import org.graffiti.plugin.parameter.Parameter;
import org.graffiti.plugin.view.MessageListener;
import org.graffiti.selection.Selection;
import org.graffiti.session.EditorSession;
import org.graffiti.session.Session;

import scenario.ScenarioService;

/**
 * Provides access to global variables, needed for various extensions to
 * Graffiti. Plugins can use the Preferences structure to save settings.
 */
public class GravistoService {

	/**
	 * The only and single instance of this object
	 */
	private static GravistoService instance;

	/**
	 * Contains <code>optionPanelRecord</code> structures
	 * which describe a option window.
	 */
	private ArrayList optionPane = new ArrayList();

	private ArrayList optionPaneIdentifiers = new ArrayList();

	/**
	 * DOCUMENT ME!
	 */
	private volatile boolean plugins_MoveSelectionsAllowed = true;

	/**
	 * DOCUMENT ME!
	 */
	public volatile Object selectionSyncObject = new Object();

	/**
	 * A list of frames, which are used by the pattern editor.
	 */
	private List<JInternalFrame> frames;

	/**
	 * DOCUMENT ME!
	 */
	private List patternSessions;

	/**
	 * Returns the single instance of this class. 
	 *
	 * @return The single instance of this "Singleton".
	 */
	public static synchronized GravistoService getInstance() {
		if (instance == null) {
			instance = new GravistoService();
		}

		return instance;
	}

	/**
	 * Adds a optionPane to the list of known options-panes.
	 * The list of known option-panes can be retrieved with
	 * <code>getKnownOptionPanes</code>.
	 * @param optionPane A new known optionPane. If already known,
	 * not added again.
	 */
	public void addKnownOptionPane(Object identifyer, OptionPane optionPane) {
		if (!this.optionPaneIdentifiers.contains(identifyer)) {
			this.optionPaneIdentifiers.add(identifyer);
			this.optionPane.add(optionPane);
		}
	}

	/**
	 * @return Returns a list of known optionPanes. (type <code>OptionPane</code>)
	 */
	public ArrayList getKnownOptionPanes() {
		return optionPane;
	}

	/**
	 * A global variable, for communication between the IPK Editing Tools
	 * and some IPK Layouter. This will be later eventually be removed.
	 *
	 * @return If value is true, the selection should not be moved by the
	 * layouter algorithms.
	 */
	public synchronized boolean pluginSelectionMoveAllowed() {
		return plugins_MoveSelectionsAllowed;
	}

	/**
	 * A global variable, for communication between the IPK Editing Tools
	 * and some IPK Layouter. This will be later eventually be removed.
	 *
	 * @param value set to true, if the selection should not be moved by the
	 * layouter algorithms.
	 */
	public synchronized void pluginSetMoveAllowed(boolean value) {
		plugins_MoveSelectionsAllowed = value;
	}

	/**
	 * Returns a <code>Vector</code> which contains a list of sessions,
	 * loaded in the main view.
	 *
	 * @return <code>Vector</code> with elements of the type
	 *         <code>EditorSession</code>. Returns empty Vector, if no
	 *         sessions are loaded.
	 */
	public Vector getMainSessions() {
		Vector result = new Vector();

		Set sessions = MainFrame.getSessions();

		for (Iterator it = sessions.iterator(); it.hasNext();) {
			Session curS = (Session) it.next();

			if ((patternSessions == null) || (patternSessions.indexOf(curS) < 0)) {
				result.add(curS);
			}
		}

		return result;
	}

	/**
	 * A <code>List</code> of the pattern sessions.
	 * PatternSessions are sessions, which are loaded in the pattern tab.
	 *
	 * @return The pattern sessions.
	 */
	public List<Session> getPatternSessionList() {
		return patternSessions;
	}

	/**
	 * Returns the main frame (application window).
	 *
	 * @return The main frame.
	 */
	public MainFrame getMainFrame() {
		return MainFrame.getInstance();
	}

	/**
	 * Returns a <code>Vector</code> which contains a list of graphs from the
	 * main view.
	 *
	 * @return <code>Vector</code> with elements of the type
	 *         <code>Graph</code>.
	 */
	public Vector getMainGraphs() {
		Vector result = new Vector();

		for (Iterator it = getMainFrame().getSessionsIterator(); it.hasNext();) {
			Session curS = (Session) it.next();

			if ((patternSessions == null) || (patternSessions.indexOf(curS) < 0)) {
				result.add(curS.getGraph());
			}
		}

		return result;
	}

	/**
	 * Returns a <code>Vector</code> which contains a list of pattern graphs.
	 *
	 * @return <code>Vector</code> with elements of the type
	 *         <code>Graph</code>. If no patterns are loaded or available,
	 *         this method returns an empty <code>Vector</code>.
	 */
	public Vector<Graph> getPatternGraphs() {
		Vector<Graph> result = new Vector<Graph>();
		if (patternSessions != null) {
			for (int i = 0; i < patternSessions.size(); i++) {
				if (patternSessions.get(i) != null) {
					result.add(((Session) patternSessions.get(i)).getGraph());
				}
			}
		}
		return result;
	}

	/**
	 * Adds a new internal frame to the list of pattern editor frames.
	 * Can be used by the method <code>isEditorFrameSelected</code> 
	 * for the decission, whether a given frame is a editor frame or
	 * a pattern editor frame.  
	 *
	 * @param frame New pattern editor frame.
	 */
	public void addFrame(GraffitiInternalFrame frame) {
		if (frames == null) {
			frames = new ArrayList<JInternalFrame>();
		}

		frames.add(frame);
	}

	/**
	 * Adds a Session to the list of patternSessions. This method is called by
	 * the patternInspector in the  action handler for the load and new
	 * button action.
	 *
	 * @param session The new session, which should be known as a session,
	 *        containing a pattern graph.
	 */
	public void addPatternSession(Session session) {
		if (patternSessions == null) {
			patternSessions = new ArrayList();
		}

		patternSessions.add(session);
	}

	/**
	 * Checks if an editor frame in the main view is selected.
	 *
	 * @return True, if an editor frame is selected.
	 */
	public boolean isEditorFrameSelected() {
		boolean result = false;

		if (frames != null) {
			//MainFrame mf=getInstance().getMainFrame();
			//Session currentSession=mf.getActiveSession();
			for (Iterator it = frames.iterator(); it.hasNext();) {
				JInternalFrame frame = (JInternalFrame) it.next();

				// TODO check: a saved frame should never be null!
				if (frame != null) {
					if (frame instanceof GraffitiInternalFrame) {
						if (frame.isSelected()) {
							result = true;
							break;
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Unselects all editor frames in the main view.
	 */
	public void framesDeselect() {
		if (frames != null) {
			//MainFrame mf=getInstance().getMainFrame();
			//Session currentSession=mf.getActiveSession();
			for (JInternalFrame frame  : frames) {
				try {
					if (frame instanceof GraffitiInternalFrame) {
						frame.setSelected(false);
					}
				} catch (PropertyVetoException e) {
					// ignore, no problem
				}
			}
		}
	}

	/**
	 * Returns a algorithm instance, defined by its name (e.g. menu item text)
	 *
	 * @param name The menu item text.
	 *
	 * @return The algorithm instance.
	 */
	public Algorithm getAlgorithmInstanceFromFriendlyName(String name) {
		Collection plugins = getMainFrame().getPluginManager().getPluginEntries();

		for (Iterator pi = plugins.iterator(); pi.hasNext();) {
			DefaultPluginEntry curPluginEntry = (DefaultPluginEntry) pi.next();
			Algorithm[] myAlgos = curPluginEntry.getPlugin().getAlgorithms();

			if (myAlgos.length > 0) {
				for (int i = 0; i < myAlgos.length; i++) {
					if (myAlgos[i] instanceof ProvidesGeneralContextMenu
							&& myAlgos[i] instanceof RunAlgorithm) {
						ProvidesGeneralContextMenu acm = (ProvidesGeneralContextMenu) myAlgos[i];

						if (acm.getCurrentContextMenuItem().toString()
								.equalsIgnoreCase(name)
								|| myAlgos[i].getName().equalsIgnoreCase(name)) {
							return myAlgos[i];
						}
					}
					Algorithm algo = myAlgos[i];

					if (algo.getName() != null
							&& algo.getName().equalsIgnoreCase(name)) {
						return myAlgos[i];
					}
				}
			}
		}

		return null;
	}

	/**
	 * Returns a plugin instance, given by its classname.
	 * @param pluginDescription
	 * @return The plugin instance, if the plugin is loaded.
	 */
	public DefaultPluginEntry getPluginInstanceFromPluginDescription(
			String pluginDescription) {
		Collection plugins = getMainFrame().getPluginManager().getPluginEntries();

		for (Iterator pi = plugins.iterator(); pi.hasNext();) {
			DefaultPluginEntry curPluginEntry = (DefaultPluginEntry) pi.next();
			if (curPluginEntry.getDescription().getName().toUpperCase().indexOf(
					pluginDescription.toUpperCase()) >= 0) {
				return curPluginEntry;
			}
		}

		return null;
	}

	/**
	 * Starts a plugin and returns, as soon as the plugin execution has
	 * finished.
	 *
	 * @param pluginNameOrClassName of Algorithm to execute or Menu Item Text
	 *        (from PluginMenu or Context Menu) or Classname of Plugin.
	 * @param g Graph instance the plugin should work with.
	 *
	 */
	public void runPlugin(final String pluginNameOrClassName, final Graph g) {
		try {
			if (SwingUtilities.isEventDispatchThread()) {
				Algorithm algo = new GravistoService().getAlgorithmInstanceFromFriendlyName(pluginNameOrClassName);
				MainFrame.showMessage(
						"Execute plugin " + pluginNameOrClassName
						+ "", 
						MessageType.INFO);
				if (algo==null)
					MainFrame.showMessageDialog("Unknown Algorithm: "+pluginNameOrClassName, "Internal Error");
				else
					runAlgorithm(algo);
			} else
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					Algorithm algo = new GravistoService().getAlgorithmInstanceFromFriendlyName(pluginNameOrClassName);
					MainFrame.showMessage(
							"Execute plugin " + pluginNameOrClassName
							+ "", 
							MessageType.INFO);
					if (algo==null)
						MainFrame.showMessageDialog("Unknown Algorithm: "+pluginNameOrClassName, "Internal Error");
					else
						runAlgorithm(algo);
				}});
//		class Exec {
//			public void myRun(GravistoService gs) {
//				Algorithm algo = gs.getAlgorithmInstanceFromFriendlyName(pluginNameOrClassName);
//
//				MainFrame.showMesssage(
//						"Execute plugin " + pluginNameOrClassName
//						+ "", 
//						MessageType.INFO);
//				runAlgorithm(algo);
//			}
//		}
//		(new Exec()).myRun(this);
		} catch (InterruptedException e) {
			ErrorMsg.addErrorMessage(e);
		} catch (InvocationTargetException e) {
			ErrorMsg.addErrorMessage(e);
		}
	}

	public void runAlgorithm(Algorithm algorithm) {
		Selection activeSel = null;
		try {
			activeSel = getMainFrame().getActiveEditorSession()
				.getSelectionModel().getActiveSelection();
		} catch(NullPointerException npe) {
			// ignore here, the algorithm should make correct error handling for null graph or selection
		}
		Graph graph = null;;
		try {
			graph = getMainFrame().getActiveSession().getGraph();
		} catch(NullPointerException npe) {
			// ignore here, the algorithm should make correct error handling for null graph or selection
		}

		runAlgorithm(algorithm, graph, activeSel);
	}

	/**
	 * @param listener
	 */
	public void algorithmAttachData(Algorithm algorithm) {
		Graph graph = getMainFrame().getActiveSession().getGraph();

		EditorSession session = getMainFrame().getActiveEditorSession();
		Selection selection = session.getSelectionModel().getActiveSelection();
		algorithm.attach(graph, selection);
	}

	/**
	 * @param algorithm
	 * @param nonInteractiveGraph
	 * @param nonInteractiveSelection
	 */
	public void runAlgorithm(Algorithm algorithm, Graph graph,
			Selection selection) {
		if (ScenarioService.isRecording()) {
			System.out.println("Start algorithm: "+algorithm.getName());
			System.out.println("	category: "+algorithm.getCategory());
			System.out.println("	description: "+ErrorMsg.removeHTMLtags(algorithm.getDescription()));
			if (graph==null) {
				System.out.println("Graph: null");
			} else {
				System.out.println("Graph: "+graph.getName(true));
				System.out.println("	Nodes: "+graph.getNumberOfNodes());
				System.out.println("	Edges: "+graph.getNumberOfEdges());
			}
			if (selection==null) {
				System.out.println("Selection: null");
			} else {
				System.out.println("Selection: "+selection.getName());
				System.out.println("	Nodes: "+selection.getNodes().size());
				System.out.println("	Edges: "+selection.getEdges().size());
			}
		}
		
		algorithm.attach(graph, selection);
		try {
			algorithm.check();
			Parameter[] parameters = algorithm.getParameters();
			ParameterDialog paramDialog = null;

			if ((parameters != null) && (parameters.length != 0) || (algorithm instanceof AlgorithmWithComponentDescription)) {
				if (algorithm instanceof EditorAlgorithm) {
					paramDialog = ((EditorAlgorithm) algorithm)
							.getParameterDialog(selection);
				}

				if (paramDialog == null) {
					JComponent desc = null;
					if (algorithm instanceof AlgorithmWithComponentDescription) {
						try {
							desc = ((AlgorithmWithComponentDescription)algorithm).getDescriptionComponent();
						} catch(Exception e) {
							ErrorMsg.addErrorMessage(e);
							desc = null;
						}
					}
					paramDialog = new DefaultParameterDialog(getMainFrame()
							.getEditComponentManager(), getMainFrame(), parameters,
							selection, algorithm.getName(), algorithm.getDescription(), desc);
				}

				// TODO load and save the preferences for this algorithm
				// TODO validate edited values
				if (!paramDialog.isOkSelected()) {
					return;
				}
			}
			if (parameters == null && algorithm instanceof EditorAlgorithm) {
				paramDialog = ((EditorAlgorithm) algorithm)
						.getParameterDialog(selection);
			}

			/*try
			 {*/
			Parameter[] params = (paramDialog == null) ? new Parameter[] {}
					: paramDialog.getEditedParameters();

			if (ScenarioService.isRecording()) {
				if (params==null)
					System.out.println("Algorithm does not use parameters!");
				else {
					System.out.println(params.length+" Parameter(s):");
					for (int i = 0; i<params.length; i++) {
						Parameter p = params[i];
						if (p!=null) {
							System.out.println("Parameter: "+p.getName());
							System.out.println("	Description: "+p.getDescription());
							System.out.println("	Value: "+p.getValue());
						}
					}
				}
			}
			
			algorithm.setParameters(params);

			algorithm.check();
			boolean stop = false;
			if (!(algorithm instanceof AlgorithmWithComponentDescription)
					&& algorithm.getDescription()!=null && algorithm.getDescription().trim().length()>0
					&& (parameters==null || parameters.length<=0)
					&& SwingUtilities.isEventDispatchThread()) {
				int res = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
						algorithm.getDescription(), 
						algorithm.getName(), 
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
				if (res==JOptionPane.CANCEL_OPTION) {
					stop = true;
					MainFrame.showMessage(algorithm.getName()+" not started", MessageType.INFO);
				}
			}
			if (!stop) {
				algorithm.execute();

				ScenarioService.postWorkflowStep(algorithm, params);
				
				if (algorithm instanceof CalculatingAlgorithm) {
					JOptionPane.showMessageDialog(null, "<html>Result of algorithm:<p>"
							+ ((CalculatingAlgorithm) algorithm).getResult().toString());
				}
				algorithm.reset();
			}
		} catch (PreconditionException e1) {
			String name = algorithm.getName();
			if (name==null) {
				name = algorithm.getClass().getSimpleName();
			}
			MainFrame.showMessageDialog(
					"<html>Can not start <i>"+name+"</i>:<br><br>"+e1.getMessage(), 
					"Command can't be executed");
		} catch (Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
	}

	/**
	 * @param frame
	 */
	public void removeFrame(GraffitiInternalFrame frame) {
		frames.remove(frame);
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
