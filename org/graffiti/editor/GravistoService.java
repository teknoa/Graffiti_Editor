package org.graffiti.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.ErrorMsg;
import org.HelperClass;
import org.Release;
import org.ReleaseInfo;
import org.graffiti.editor.actions.RunAlgorithm;
import org.graffiti.editor.dialog.DefaultParameterDialog;
import org.graffiti.editor.dialog.ParameterDialog;
import org.graffiti.graph.Graph;
import org.graffiti.managers.pluginmgr.DefaultPluginEntry;
import org.graffiti.managers.pluginmgr.PluginEntry;
import org.graffiti.options.OptionPane;
import org.graffiti.plugin.algorithm.Algorithm;
import org.graffiti.plugin.algorithm.AlgorithmWithComponentDescription;
import org.graffiti.plugin.algorithm.CalculatingAlgorithm;
import org.graffiti.plugin.algorithm.EditorAlgorithm;
import org.graffiti.plugin.algorithm.PreconditionException;
import org.graffiti.plugin.algorithm.ProvidesAccessToOtherAlgorithms;
import org.graffiti.plugin.algorithm.ProvidesGeneralContextMenu;
import org.graffiti.plugin.parameter.Parameter;
import org.graffiti.plugin.view.View;
import org.graffiti.selection.Selection;
import org.graffiti.session.EditorSession;
import org.graffiti.session.Session;

import scenario.ScenarioService;

/**
 * Provides access to global variables, needed for various extensions to
 * Graffiti. Plugins can use the Preferences structure to save settings.
 */
public class GravistoService implements HelperClass {

	/**
	 * The only and single instance of this object
	 */
	private static GravistoService instance;

	/**
	 * Contains <code>optionPanelRecord</code> structures
	 * which describe a option window.
	 */
	private ArrayList<OptionPane> optionPane = new ArrayList<OptionPane>();

	private ArrayList<Object> optionPaneIdentifiers = new ArrayList<Object>();

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
	private List<Session> patternSessions;

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
	public ArrayList<OptionPane> getKnownOptionPanes() {
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
	public Vector<Session> getMainSessions() {
		Vector<Session> result = new Vector<Session>();

		Set<Session> sessions = MainFrame.getSessions();

		for (Iterator<Session> it = sessions.iterator(); it.hasNext();) {
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
	public Vector<Graph> getMainGraphs() {
		Vector<Graph> result = new Vector<Graph>();

		for (Iterator<Session> it = getMainFrame().getSessionsIterator(); it.hasNext();) {
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
	public ArrayList<Graph> getPatternGraphs() {
		ArrayList<Graph> result = new ArrayList<Graph>();
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
	 * for the decision, whether a given frame is a editor frame or
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
			patternSessions = new ArrayList<Session>();
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
			for (Iterator<JInternalFrame> it = frames.iterator(); it.hasNext();) {
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
		Collection<PluginEntry> plugins = getMainFrame().getPluginManager().getPluginEntries();

		for (Iterator<PluginEntry> pi = plugins.iterator(); pi.hasNext();) {
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

					if (algo.getName() != null && algo.getName().equalsIgnoreCase(name)) {
						return myAlgos[i];
					}
					
					if (algo instanceof ProvidesAccessToOtherAlgorithms) {
						ProvidesAccessToOtherAlgorithms pa = (ProvidesAccessToOtherAlgorithms)algo;
						if (pa.getAlgorithmList()!=null)
							for (Algorithm a : pa.getAlgorithmList()) {
								if (a.getName() != null && a.getName().equalsIgnoreCase(name)) {
									return a;
								}
							}
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
		Collection<PluginEntry> plugins = getMainFrame().getPluginManager().getPluginEntries();

		for (Iterator<PluginEntry> pi = plugins.iterator(); pi.hasNext();) {
			DefaultPluginEntry curPluginEntry = (DefaultPluginEntry) pi.next();
			if (curPluginEntry.getDescription().getName().toUpperCase().indexOf(
					pluginDescription.toUpperCase()) >= 0) {
				return curPluginEntry;
			}
		}

		return null;
	}
	
	public static void run(String pluginNameOrClassName) {
		Graph g = MainFrame.getInstance().getActiveSession().getGraph();
		getInstance().runPlugin(pluginNameOrClassName, g);
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
		runAlgorithm(algorithm, false);
	}
	
	public void runAlgorithm(Algorithm algorithm, boolean enableMultipleSessionProcessing) {
		Selection activeSel = null;
		try {
			activeSel = getMainFrame().getActiveEditorSession().getSelectionModel().getActiveSelection();
		} catch(NullPointerException npe) {
			// ignore here, the algorithm should make correct error handling for null graph or selection
		}
		Graph graph = null;;
		try {
			graph = getMainFrame().getActiveSession().getGraph();
		} catch(NullPointerException npe) {
			// ignore here, the algorithm should make correct error handling for null graph or selection
		}

		runAlgorithm(algorithm, graph, activeSel, enableMultipleSessionProcessing);
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
	
	public static void attachData(Algorithm algorithm) {
		getInstance().algorithmAttachData(algorithm);
	}

	public void runAlgorithm(Algorithm algorithm, Graph graph,Selection selection) {
		runAlgorithm(algorithm, graph, selection,false);
	}
	
	/**
	 * @param algorithm
	 * @param nonInteractiveGraph
	 * @param nonInteractiveSelection
	 */
	public void runAlgorithm(Algorithm algorithm, Graph graph,
			Selection selection, boolean enableMultipleSessionProcessing) {
		algorithm.attach(graph, selection);

		try {
			algorithm.check();
		} catch (PreconditionException e) {
			StringBuilder sb = new StringBuilder();
			processError(algorithm, graph, sb, e);
			MainFrame.showMessageDialog("<html>"+sb.toString(), "Execution Error");
			return;
		}
		Parameter[] parameters = algorithm.getParameters();
		ParameterDialog paramDialog = null;

		if ((parameters != null) && (parameters.length != 0) || (algorithm instanceof AlgorithmWithComponentDescription)) {
			if (algorithm instanceof EditorAlgorithm) {
				paramDialog = ((EditorAlgorithm) algorithm).getParameterDialog(selection);
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
				String algName = algorithm.getName();
				if (algorithm instanceof EditorAlgorithm) {
					algName = ((EditorAlgorithm)algorithm).getShortName();
				}
				paramDialog = new DefaultParameterDialog(getMainFrame().getEditComponentManager(), 
						getMainFrame(), parameters,
						selection, ErrorMsg.removeHTMLtags(algName), algorithm.getDescription(), desc, checkRelease(algorithm.mayWorkOnMultipleGraphs() && enableMultipleSessionProcessing));
			}

			if (!paramDialog.isOkSelected()) {
				return;
			}
		}
		if (parameters == null && algorithm instanceof EditorAlgorithm) {
			paramDialog = ((EditorAlgorithm) algorithm)
					.getParameterDialog(selection);
		}

		Parameter[] params = (paramDialog == null) ? new Parameter[] {}
				: paramDialog.getEditedParameters();

		boolean stop = false;
		if (!(algorithm instanceof AlgorithmWithComponentDescription)
				&& algorithm.getDescription()!=null && algorithm.getDescription().trim().length()>0
				&& (parameters==null || parameters.length<=0)
				&& SwingUtilities.isEventDispatchThread()) {
			int res = JOptionPane.showConfirmDialog(MainFrame.getInstance(),
					algorithm.getDescription(), 
					ErrorMsg.removeHTMLtags(algorithm.getName()), 
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
			if (res==JOptionPane.CANCEL_OPTION) {
				stop = true;
				MainFrame.showMessage(algorithm.getName()+" not started", MessageType.INFO);
			}
		}
		if (!stop) {
			StringBuilder errors = new StringBuilder();
			Collection<Session> sessions = new ArrayList<Session>();

			if(enableMultipleSessionProcessing) {
				if (paramDialog!=null)
					sessions = paramDialog.getTargetSessions();
				else {
					sessions = new ArrayList<Session>();
					if (MainFrame.getSessions().size()==1 || !algorithm.mayWorkOnMultipleGraphs() ) {
						if (MainFrame.getInstance().isSessionActive())
							sessions.add(MainFrame.getInstance().getActiveSession());
					} else
						if (MainFrame.getSessions().size()>1) {
							Object[] options = {
									"Active Graph", 
									"Open Graphs ("+MainFrame.getSessions().size()+")" };
							int res = JOptionPane.showOptionDialog(MainFrame.getInstance(),
									"Please select the working set.", 
									ErrorMsg.removeHTMLtags(algorithm.getName()), 
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0] );
							if (res==JOptionPane.YES_OPTION) {
								sessions.add(MainFrame.getInstance().getActiveSession());
							} else
								sessions.addAll(MainFrame.getSessions());
						}
				}
				boolean startLater = sessions.size()==0;
				for (Session s : sessions) {
					Graph g = s.getGraph();
					Selection sel;
					if (g==graph) {
						startLater = true;
						continue;
					}
					if (s instanceof EditorSession) {
						EditorSession es = (EditorSession)s;
						sel = es.getSelectionModel().getActiveSelection();
					} else
						sel = new Selection("empty");
					algorithm.attach(g, sel);
					algorithm.setParameters(params);
					try {
						algorithm.check();
						algorithm.execute();
						if (algorithm instanceof CalculatingAlgorithm) {
							JOptionPane.showMessageDialog(null, "<html>Result of algorithm:<p>"
									+ ((CalculatingAlgorithm) algorithm).getResult().toString());
						}
						algorithm.reset();
					} catch (PreconditionException e) {
						processError(algorithm, g, errors, e);
					}
				}
				if (startLater) {
					algorithm.attach(graph, selection);
					algorithm.setParameters(params);
					try {
						algorithm.check();
						algorithm.execute();
						ScenarioService.postWorkflowStep(algorithm, params);
						if (algorithm instanceof CalculatingAlgorithm) {
							JOptionPane.showMessageDialog(null, "<html>Result of algorithm:<p>"
									+ ((CalculatingAlgorithm) algorithm).getResult().toString());
						}
						algorithm.reset();
					} catch (PreconditionException e) {
						processError(algorithm, graph, errors, e);
					}
				}
				if (errors.length()>0) {
					MainFrame.showMessageDialogWithScrollBars("<html>"+errors.toString(), 
							"Execution Error");
				}
			} else {
				algorithm.setParameters(params);
				algorithm.execute();
				ScenarioService.postWorkflowStep(algorithm, params);
				if (algorithm instanceof CalculatingAlgorithm) {
					JOptionPane.showMessageDialog(null, "<html>Result of algorithm:<p>"
							+ ((CalculatingAlgorithm) algorithm).getResult().toString());
				}
				algorithm.reset();
			}

		}
	}

	private boolean checkRelease(boolean mayWorkOnMultipleGraphs) {
		if (ReleaseInfo.getRunningReleaseStatus()==Release.KGML_EDITOR)
			return false;
		else
			return mayWorkOnMultipleGraphs;
	}

	private void processError(Algorithm algorithm, Graph graph,
			StringBuilder errors, PreconditionException e) {
		String name = algorithm.getName();
		if (name==null) {
			name = algorithm.getClass().getSimpleName();
		}
		if(graph!=null)
			errors.append("Can not start <i>"+name+"</i> on graph "+
					graph.getName()
					+":<br><br>"+e.getMessage()+"<br><br>");
		else
			errors.append("Can not start <i>"+name+"</i>" 
					+":<br><br>"+e.getMessage()+"<br><br>");
	}

	/**
	 * @param frame
	 */
	public void removeFrame(GraffitiInternalFrame frame) {
		frames.remove(frame);
	}
	
	private static ArrayList<String> fileNames = new ArrayList<String>();
	
	public void loadFile(String fileName) {
		synchronized (fileNames) {
			fileNames.add(fileName);
		}
		if (ErrorMsg.isAppLoadingCompleted()) {
			loadFiles();
		}
	}
	
	public static void loadFiles() {
		ArrayList<File> toDo = new ArrayList<File>();
		synchronized (fileNames) {
			for (String f : fileNames) {
				File ft = new File(f);
				if (ft.exists() && ft.canRead())
					toDo.add(ft);
			}
			fileNames.clear();
		}
		try {
			MainFrame.getInstance().loadGraphInBackground(toDo.toArray(new File[] {}), null, true);
		} catch (Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
	}

	public void loadFiles(File[] files) {
		if (files!=null)
		for (File f : files)
			loadFile(f.getAbsolutePath());
	}

	public Session getSessionFromView(View thisView) {
		for (Session s : MainFrame.getSessions())
			if (s.getViews().contains(thisView))
				return s;
		return null;
	}

	public static void addActionOnKeystroke(JDialog comp, ActionListener action, KeyStroke key) {
		comp.getRootPane().registerKeyboardAction(action,key,JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}
	
	@SuppressWarnings("unchecked")
	public static BufferedImage blurImage(BufferedImage image, int blurRadius) {
	   	float[] matrix = new float[blurRadius*blurRadius]; 
		for (int i = 0; i < blurRadius*blurRadius; i++)
			matrix[i] = 1.0f/(float)blurRadius/(float)blurRadius;

		Map map = new HashMap();

		map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		RenderingHints hints = new RenderingHints(map);
		BufferedImageOp op = new ConvolveOp(new Kernel(blurRadius, blurRadius, matrix), ConvolveOp.EDGE_NO_OP, hints);
		try {
			BufferedImage bi = op.filter(image, null);
			return bi;
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
			return image;
		}
	}
	
	public static BufferedImage getImage(URL fileUrl) throws IOException {
		return ImageIO.read(fileUrl);
	}
	
	public static BufferedImage getScaledImage(Image icon, int w, int h) {
		 	BufferedImage destImage = new BufferedImage(icon.getWidth(null), icon.getHeight(null), BufferedImage.TYPE_INT_ARGB); 
		          Graphics2D graphics = destImage.createGraphics();
		    graphics.drawImage(icon, 0, 0, null);
		    return getScaledImage(destImage, w, h);
	}

	public static BufferedImage getScaledImage(BufferedImage icon, int w, int h) {
		if (icon.getWidth() <= w && icon.getHeight() <= h)
			return icon;
		try {
			double srcWidth = icon.getWidth();
			double srcHeight = icon.getHeight();

			double longSideForSource = (double) Math.max(srcWidth, srcHeight);
			double longSideForDest = (double) Math.max(w, h);
			double multiplier = longSideForDest / longSideForSource;
			int destWidth = (int) (srcWidth * multiplier);
			int destHeight = (int) (srcHeight * multiplier);

			int blur = (int) (0.6d / multiplier) + 1;

			icon = blurImage(icon, blur);

			BufferedImage destImage = new BufferedImage(destWidth, destHeight,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = destImage.createGraphics();
			AffineTransform affineTransform = AffineTransform.getScaleInstance(
					multiplier, multiplier);
			graphics.drawImage(icon, affineTransform, null);

			return destImage;
		} catch (Exception e) {
			ErrorMsg.addErrorMessage(e);
			return icon;
		}
	}

	public static void showImage(BufferedImage img, String title) {
	    JFrame frame = new JFrame(title);
	    Panel panel = new ShowImage(img);
	    frame.getContentPane().add(panel);
	    frame.setSize(500, 500);
	    frame.setVisible(true);
	}
	
	private static HashSet<MemoryHog> memoryHogs = new HashSet<MemoryHog>();

	public static JLabel getMemoryInfoLabel(final boolean shortInfo) {
		final JLabel memLabel = new JLabel(getCurrentMemoryInfo(false));
		memLabel.setToolTipText("Click for memory garbage collection");
		memLabel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				for (MemoryHog mh : memoryHogs) {
					mh.freeMemory();
				}
				System.gc();
			}

			public void mouseEntered(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
			public void mousePressed(MouseEvent e) { }
			public void mouseReleased(MouseEvent e) {}
			});
		Timer t = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				memLabel.setText(getCurrentMemoryInfo(shortInfo));
				if (shortInfo)
					memLabel.setToolTipText(getCurrentMemoryInfo(false).
							replaceFirst(":", " (click to garbage-collect):").
							replaceFirst("<font color='gray'>", ""));
				memLabel.repaint(1000);
			}});
		t.start();
		return memLabel;
	}
	
	private static String getCurrentMemoryInfo(boolean shortInfo) {
		Runtime r = Runtime.getRuntime();
      int divisor=1024;
      String memoryConfig;
      if (shortInfo) {
      	memoryConfig = ((r.totalMemory()/divisor/divisor)-(r.freeMemory()/divisor/divisor)) +""+ 
	  		"&nbsp;MB";
			return "<html>" +
			 "<font color='gray'><small>"+memoryConfig+"<br>"+MainFrame.getInstance().getNumberOfOpenSessions()+"&nbsp;ES";
			
      } else {
	      memoryConfig = ((r.totalMemory()/divisor/divisor)-(r.freeMemory()/divisor/divisor)) +""+ 
			  		" MB, " + (r.totalMemory()/divisor/divisor)+" MB, "+(r.maxMemory()/divisor/divisor)+" MB";
			return "<html>" +
					 "<font color='gray'><small>Memory info:<br>&nbsp;&nbsp;&nbsp;active, alloc, max memory: "+memoryConfig;
      }
	}

	public static URL getResource(Class location, String filename, String optExt) {
		ClassLoader cl = location.getClassLoader();
	    String path = location.getPackage().getName().replace('.', '/');
		if(optExt==null)
			return cl.getResource(path+"/"+filename);
		else
			return cl.getResource(path+"/"+filename+"."+optExt);
	}

	public static void addKnownMemoryHog(MemoryHog memoryHog) {
		memoryHogs.add(memoryHog);
	}

	public static void ensureActiveViewAndSession(MouseEvent e) {
		try {
			View v = (View) ErrorMsg.findParentComponent(e.getComponent(), View.class);
			
			Iterator<Session> itSessions = MainFrame.getSessions().iterator();
			boolean found = false;
			while (itSessions.hasNext() && !found) {
				Session mySession = (Session) itSessions.next();
				Iterator<View> itViews = mySession.getViews().iterator();
				while (itViews.hasNext() && !found) {
					View myView = (View) itViews.next();
					if (myView == v) {
						mySession.setActiveView(myView);
						if (GravistoService.getInstance().getMainFrame().getActiveEditorSession()!=mySession)
							GravistoService.getInstance().getMainFrame().setActiveSession(mySession, myView);
						found = true;
					}
				}
			}
		} finally {
			
		}
	}	
}


class ShowImage extends Panel {
	private static final long serialVersionUID = 2163700797926226041L;
	BufferedImage  image;

	public ShowImage(BufferedImage img) {
		image = img;
	}

		public void paint(Graphics g) {
    g.drawImage( image, 0, 0, null);
  }
}
//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
