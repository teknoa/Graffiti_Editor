//==============================================================================
//
//   MainFrame.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: MainFrame.java,v 1.51 2008/11/27 20:12:06 klukas Exp $

package org.graffiti.editor;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.IllegalComponentStateException;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEditSupport;

import org.ErrorMsg;
import org.Java_1_5_compatibility;
import org.Release;
import org.ReleaseInfo;
import org.graffiti.core.ImageBundle;
import org.graffiti.core.StringBundle;
import org.graffiti.editor.actions.CopyAction;
import org.graffiti.editor.actions.CutAction;
import org.graffiti.editor.actions.DeleteAction;
import org.graffiti.editor.actions.EditRedoAction;
import org.graffiti.editor.actions.EditUndoAction;
import org.graffiti.editor.actions.ExitAction;
import org.graffiti.editor.actions.FileCloseAction;
import org.graffiti.editor.actions.FileNewAction;
import org.graffiti.editor.actions.FileOpenAction;
import org.graffiti.editor.actions.FileSaveAction;
import org.graffiti.editor.actions.FileSaveAllAction;
import org.graffiti.editor.actions.FileSaveAsAction;
import org.graffiti.editor.actions.PasteAction;
import org.graffiti.editor.actions.PluginManagerEditAction;
import org.graffiti.editor.actions.RedrawViewAction;
import org.graffiti.editor.actions.RunAlgorithm;
import org.graffiti.editor.actions.SelectAllAction;
import org.graffiti.editor.actions.ViewNewAction;
import org.graffiti.event.ListenerManager;
import org.graffiti.graph.AdjListGraph;
import org.graffiti.graph.Graph;
import org.graffiti.managers.AlgorithmManager;
import org.graffiti.managers.AttributeComponentManager;
import org.graffiti.managers.DefaultAlgorithmManager;
import org.graffiti.managers.DefaultIOManager;
import org.graffiti.managers.DefaultModeManager;
import org.graffiti.managers.DefaultToolManager;
import org.graffiti.managers.DefaultURLattributeActionManager;
import org.graffiti.managers.DefaultViewManager;
import org.graffiti.managers.EditComponentManager;
import org.graffiti.managers.IOManager;
import org.graffiti.managers.ModeManager;
import org.graffiti.managers.MyInputStreamCreator;
import org.graffiti.managers.ToolManager;
import org.graffiti.managers.URLattributeActionManager;
import org.graffiti.managers.ViewManager;
import org.graffiti.managers.pluginmgr.PluginDescription;
import org.graffiti.managers.pluginmgr.PluginEntry;
import org.graffiti.managers.pluginmgr.PluginManager;
import org.graffiti.managers.pluginmgr.PluginManagerException;
import org.graffiti.managers.pluginmgr.PluginManagerListener;
import org.graffiti.options.GravistoPreferences;
import org.graffiti.plugin.EditorPlugin;
import org.graffiti.plugin.GenericPlugin;
import org.graffiti.plugin.actions.GraffitiAction;
import org.graffiti.plugin.algorithm.Algorithm;
import org.graffiti.plugin.editcomponent.NeedEditComponents;
import org.graffiti.plugin.extension.Extension;
import org.graffiti.plugin.gui.GraffitiComponent;
import org.graffiti.plugin.gui.GraffitiContainer;
import org.graffiti.plugin.gui.ModeToolbar;
import org.graffiti.plugin.gui.PluginPanel;
import org.graffiti.plugin.gui.ToolButton;
import org.graffiti.plugin.inspector.ContainsTabbedPane;
import org.graffiti.plugin.inspector.InspectorPlugin;
import org.graffiti.plugin.inspector.InspectorTab;
import org.graffiti.plugin.inspector.SubtabHostTab;
import org.graffiti.plugin.io.InputSerializer;
import org.graffiti.plugin.io.OutputSerializer;
import org.graffiti.plugin.mode.Mode;
import org.graffiti.plugin.tool.AbstractTool;
import org.graffiti.plugin.tool.Tool;
import org.graffiti.plugin.view.View;
import org.graffiti.plugin.view.ViewListener;
import org.graffiti.plugin.view.ZoomListener;
import org.graffiti.selection.Selection;
import org.graffiti.selection.SelectionEvent;
import org.graffiti.selection.SelectionListener;
import org.graffiti.selection.SelectionModel;
import org.graffiti.session.EditorSession;
import org.graffiti.session.Session;
import org.graffiti.session.SessionListener;
import org.graffiti.session.SessionManager;
import org.graffiti.undo.Undoable;
import org.graffiti.util.DesktopMenuManager;
import org.graffiti.util.InstanceCreationException;


/**
 * Constructs a new graffiti frame, which contains the main gui components.
 *
 * @version $Revision: 1.51 $
 */
public class MainFrame extends JFrame implements SessionManager,
			SessionListener, PluginManagerListener, ComponentListener,
			UndoableEditListener, EditorDefaultValues,
			IOManager.IOManagerListener, ViewManager.ViewManagerListener,
			SelectionListener, DropTargetListener

{

	/**
	 * The only and single instance of this object
	 */
	private static MainFrame instance;

	public void setTitle(String title) {
		super.setTitle(title);
	}

	//~ Static fields/initializers =============================================

	private static final long serialVersionUID = 1L;

	/** The size of an internal frame for first displaying. */
	public static final Dimension PREFERRED_INTERNALFRAME_SIZE = new Dimension(
				1000, 1000);

	//~ Instance fields ========================================================

	/**
	 * The preferences of the editor's main frame. (e.g.: position and size of
	 * the main frame.
	 */
	protected GravistoPreferences uiPrefs;

	/** The current active session. */
	EditorSession activeSession;

	/** Holds all active frames. */
	List<GraffitiInternalFrame> activeFrames = new LinkedList<GraffitiInternalFrame>();

	/** The list of registered <code>ZoomListener</code>s. */
	HashSet<ZoomListener> zoomListeners;

	/** Maps from views to internal frames. */
	Map<View,GraffitiInternalFrame> viewFrameMapper;

	/** Contains all <code>Session</code>s. */
	Set<Session> sessions = new HashSet<Session>();

	/** Handles the algorithms. */
	private AlgorithmManager algorithmManager;

	/** Handles the list of attribute components. */
	private AttributeComponentManager attributeComponentManager;

	/** Handles the list of value edit components. */
	private EditComponentManager editComponentManager;

	/** The main frame's static actions */
	private GraffitiAction editCopy;

	/** The main frame's static actions */
	private GraffitiAction editCut;

	/** The main frame's static actions */
	private GraffitiAction editDelete;

	/** The main frame's static actions */
	private GraffitiAction editPaste;

	/** The main frame's static actions */
	private GraffitiAction editRedo;

	/** The main frame's static actions */
	private GraffitiAction editSelectAll;

	/** The main frame's static actions */
	private GraffitiAction editUndo;

	/** The main frame's static actions */
	public GraffitiAction fileClose;

	/** The main frame's static actions */
	private GraffitiAction fileExit;

	/** The main frame's static actions */
	private GraffitiAction fileOpen;

	/** The main frame's static actions */
	private GraffitiAction fileSave;

	/** The main frame's static actions */
	private GraffitiAction fileSaveAll;

	/** The main frame's static actions */
	GraffitiAction fileSaveAs;

	/** The main frame's static actions */
	private GraffitiAction newGraph;

	/** The main frame's static actions */
	private GraffitiAction pluginManagerEdit;

	/** The main frame's static actions */
	private GraffitiAction redrawView;

	/** The main frame's static actions */
	private GraffitiAction viewNew;

	/** The listener for the internal frames. */
	private GraffitiFrameListener graffitiFrameListener;

	/** The manager for IO serializers. */
	private IOManager ioManager;
	
	/** The manager for URL attribute actions (load map, view URL, ...). */
	private URLattributeActionManager urlAttributeActionManager;

	/** The <code>ImageBundle</code> of the main frame. */
	private ImageBundle iBundle = ImageBundle.getInstance();

	/** The desktop pane for the internal frames. */
	private JDesktopPane desktop;
	
	public JComponent sidepanel;
	


	/** The main frame's menu entries. */
	private JMenu pluginMenu;

	/** Each algorithm menu item is sorted into a category. This
	 * structure holds the names of the known categories and its menu items.
	 */
	private HashMap<String,JMenuItem> categoriesForAlgorithms = new HashMap<String,JMenuItem>();

//	/** Container for toolbars at the left of the main frame. */
//	private JPanel leftToolBarPanel;
//
//	/** Container for toolbars at the top of the main frame. */
//	private JPanel topToolBarPanel;
	
	/** The split pane between the center and the pluginPanel. */
	private JSplitPane vertSplitter;

	/**
	 * The list of algorithm actions.
	 *
	 * @see org.graffiti.editor.actions.RunAlgorithm
	 */
	private HashSet<Action> algorithmActions;

	/** The list of registered <code>SelectionListener</code>s. */
	private HashSet<SelectionListener> selectionListeners;

	/** The list of registered <code>SessionListener</code>s. */
	private HashSet<SessionListener> sessionListeners;

	/**
	 * Contains a mapping between the identifiers of gui-components and the
	 * corresponding gui-component.
	 */
	private Map<String,JComponent> guiMap = new HashMap<String,JComponent>();

	/** The mode manager. */
	private ModeManager modeManager;

	/** A reference to the graffiti plugin manager. */
	private PluginManager pluginmgr;

	/** The panel for the plugins. */
	private PluginPanel pluginPanel;

	/** The main frame's status bar. */
	private StatusBar statusBar;

	/**
	 * The default view type, that will be always displayed if the user
	 * deactivates the view chooser dialog. This variable is initialized with
	 * null per default. for setting the default view this member variable
	 * have to be initialized with a valid view type by the method
	 * <code>getDefaultView()</code>.
	 */
	private String defaultView = null;

	/** The <code>StringBundle</code> of the main frame. */
	StringBundle sBundle = StringBundle.getInstance();

	/** The tool manager. */
	private ToolManager toolManager;

	/** This object is listener of all undoable actions. */
	private UndoableEditSupport undoSupport;

	/** The manager, which maps view type names to view types. */
	private ViewManager viewManager;

	private JPanel progressPanel;

	// private JSplitPane jSplitPane_pluginPanelAndProgressView;
	private JComponent jSplitPane_pluginPanelAndProgressView;

	private ArrayList<JPanel> activeProgressPanels;

	private Timer timerCheckActiveProgressPanels;

	private DesktopMenuManager desktopMenuManager;

	private JMenuBar storedMenuBar;

	//~ Constructors ===========================================================

	/**
	 * Constructs a new <code>MainFrame</code>.
	 *
	 * @param pluginmgr DOCUMENT ME!
	 * @param prefs DOCUMENT ME!
	 */
	public MainFrame(PluginManager pluginmgr, GravistoPreferences prefs) {
		this(pluginmgr, prefs, null, false);
	}

	/**
	 * Constructs a new <code>MainFrame</code>.
	 *
	 * @param pluginmgr DOCUMENT ME!
	 * @param prefs DOCUMENT ME!
	 */
	public MainFrame(PluginManager pluginmgr, GravistoPreferences prefs,
				JPanel progressPanel, boolean showVantedHelp) {
		super();
//		if (instance != null) {
//			System.err.println("Only one MainFrame instance is allowed. Application is shut down.");
//			System.exit(1);
//		}
		instance = this;

		this.setTitle(getDefaultFrameTitle());
		GraffitiInternalFrame.startTitle = getDefaultFrameTitle(); 
		this.sessionListeners = new HashSet<SessionListener>();
		this.selectionListeners = new HashSet<SelectionListener>();
		this.zoomListeners = new HashSet<ZoomListener>();
		this.viewFrameMapper = new HashMap<View,GraffitiInternalFrame>();
		this.algorithmActions = new HashSet<Action>();
		this.addSessionListener(this);

		viewManager = new DefaultViewManager();
		algorithmManager = new DefaultAlgorithmManager();
		modeManager = new DefaultModeManager();
		toolManager = new DefaultToolManager(modeManager);
		ioManager = new DefaultIOManager();
		attributeComponentManager = new AttributeComponentManager();
		editComponentManager = new EditComponentManager();
		urlAttributeActionManager = new DefaultURLattributeActionManager();

		pluginmgr.addPluginManagerListener(this);
		pluginmgr.addPluginManagerListener(viewManager);
		pluginmgr.addPluginManagerListener(toolManager);
		pluginmgr.addPluginManagerListener(algorithmManager);
		pluginmgr.addPluginManagerListener(modeManager);
		pluginmgr.addPluginManagerListener(ioManager);
		pluginmgr.addPluginManagerListener(attributeComponentManager);
		pluginmgr.addPluginManagerListener(editComponentManager);
		pluginmgr.addPluginManagerListener(urlAttributeActionManager);

		ioManager.addListener(this);
		viewManager.addListener(this);

		undoSupport = new UndoableEditSupport();

		//undoSupport.addUndoableEditListener(this);
		graffitiFrameListener = new GraffitiFrameListener(this);

		this.pluginmgr = pluginmgr;

		this.uiPrefs = prefs;

		createActions();

		// initialize map of gui components and create menu bar
		guiMap = new Hashtable<String,JComponent>();

		// the editor's status bar
		statusBar = new StatusBar(sBundle);
		statusBar.setBorder(null);
		addSessionListener(statusBar);
		
		selectionListeners.add(statusBar);
		selectionListeners.add(this);
		if (showVantedHelp) {
			setHelpIntroduction();
		} 
		getContentPane().add(statusBar, BorderLayout.SOUTH);
//		getContentPane().setBackground(null);
		

		// create the desktop
		// desktop = new JDesktopPane();
		desktop = new JDesktopPane();
		if (!ErrorMsg.isMac()) {
			desktop.setBackground(Color.LIGHT_GRAY);
			desktop.setOpaque(true);
//			desktop.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		} else {
			desktop.setBackground(new JPanel().getBackground().darker());
		}
		// create a panel, which will contain the views for plugins
		pluginPanel = new PluginPanel();
		pluginPanel.setBorder(null);
		pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.Y_AXIS));
		guiMap.put(pluginPanel.getId(), pluginPanel);
		
		UIManager.put("SplitPaneDivider.border",new EmptyBorder(0,0,0,0));
		
		if (progressPanel != null) {
			jSplitPane_pluginPanelAndProgressView = TableLayout.getSplitVertical(pluginPanel, progressPanel, TableLayout.FILL, TableLayout.PREFERRED);
			jSplitPane_pluginPanelAndProgressView.setMinimumSize(new Dimension(0,0));
			sidepanel = jSplitPane_pluginPanelAndProgressView;
		} else {
			sidepanel = pluginPanel;
		}
		
		vertSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, desktop, sidepanel);
		this.progressPanel = progressPanel;

		vertSplitter.setContinuousLayout(true);
		
		//vertSplitter.setOneTouchExpandable(true);

		//vertSplitter.setBorder(null);
		//vertSplitter.setDividerSize(4);
		
		vertSplitter.setDividerLocation(uiPrefs.getInt("vertSplitter", VERT_SPLITTER));
		
		// vertSplitter.setDividerSize(5);
		// vertSplitter.setBackground(null);
		// vertSplitter.setOpaque(false);
		if (ReleaseInfo.isRunningAsApplet())
			getContentPane().add(desktop, BorderLayout.CENTER);
		else
			getContentPane().add(vertSplitter, BorderLayout.CENTER);

		JToolBar toolBar = createToolBar();

		guiMap.put("toolbarPanel", toolBar);
		guiMap.put("defaultToolbar", toolBar);
		
		// create and set the menu bar
		
		JMenu windowMenu = createMenu("window");
		if (ReleaseInfo.isRunningAsApplet()) {
			JMenuBar jmb = createMenuBar(windowMenu);
			storedMenuBar = jmb;
//			getContentPane().add(TableLayout.getSplitVertical(jmb, toolBar, TableLayout.PREFERRED, TableLayout.PREFERRED),
//					BorderLayout.NORTH);
			getContentPane().add(toolBar, BorderLayout.PAGE_START);
		} else {
			setJMenuBar(createMenuBar(windowMenu));
			desktopMenuManager = new DesktopMenuManager(desktop, windowMenu);
			getContentPane().add(toolBar, BorderLayout.PAGE_START);
		}

//		// left toolbars
//		leftToolBarPanel = new JPanel();
//		// leftToolBarPanel.setLayout(new BoxLayout(leftToolBarPanel, BoxLayout.Y_AXIS));
//		leftToolBarPanel.setLayout(new SingleFiledLayout(SingleFiledLayout.COLUMN, SingleFiledLayout.CENTER, 2));
//		getContentPane().add(leftToolBarPanel, BorderLayout.WEST);
		
		// window settings like position and size
		setSize(uiPrefs.getInt("sizeWidth", SIZE_WIDTH), uiPrefs.getInt(
					"sizeHeight", SIZE_HEIGHT));
		
		setSize(900, 700);
		
		if (!ReleaseInfo.isRunningAsApplet())
			setLocationByPlatform(true);


		addComponentListener(this);

		if (!ReleaseInfo.isRunningAsApplet())
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		getContentPane().validate();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				addStatusPanel(null);
			}});
		
	}
	
	public void hideSidePanel() {
		getContentPane().remove(vertSplitter);
		getContentPane().add(desktop, BorderLayout.CENTER);
		validate();
	}

	public String getDefaultFrameTitle() {
		return sBundle.getString("name") + " "
					+ sBundle.getString("version") + " "
					+ sBundle.getString("version.Release") + "."
					+ sBundle.getString("version.Major") + "."
					+ sBundle.getString("version.Minor");
	}

	//~ Methods ================================================================

	private void setHelpIntroduction() {
		String s = ReleaseInfo.getHelpIntroductionText();
		if (s!=null && s.length()>0)
			showMessage(s, MessageType.PERMANENT_INFO);
	}

	/**
	 * Returns the current active editor session.
	 *
	 * @return the current active editor session.
	 */
	public EditorSession getActiveEditorSession() {
		return activeSession;
	}
	
	public DesktopMenuManager getDesktopMenuManager() {
		return desktopMenuManager;
	}
	
	public JDesktopPane getJDesktopPane() {
		return desktop;
	}

	public List<GraffitiInternalFrame> getActiveFrames() {
		return activeFrames;
	}

	/**
	 * Returns the current active session.
	 *
	 * @return the current active session.
	 */
	public Session getActiveSession() {
		return activeSession;
	}

	/**
	 * Sets the current active session.
	 * @param s The session to be activated.
	 */
	public void setActiveSession(Session s, View targetView) {
		for (GraffitiInternalFrame gif : activeFrames) {
			if (!gif.isVisible())
				continue;
			try {
				if (gif.getSession()!=s)
					gif.setSelected(false);
				else if (targetView!=null && gif.getView()!=targetView)
					gif.setSelected(false);
			} catch (PropertyVetoException e) {
				ErrorMsg.addErrorMessage(e);
			}
		}
		fireSessionChanged(s);
		activeSession = (EditorSession) s;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Collection getAlgorithmActions() {
		return algorithmActions;
	}

	/**
	 * Sets the defaultView.
	 *
	 * @param defaultView The defaultView to set
	 */
	public void setDefaultView(String defaultView) {
		this.defaultView = defaultView;
	}

	/**
	 * Returns the defaultView.
	 *
	 * @return String
	 */
	public String getDefaultView() {
		return defaultView;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public EditComponentManager getEditComponentManager() {
		return editComponentManager;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public IOManager getIoManager() {
		return ioManager;
	}

	/**
	 * Get the algorithm manager.
	 * 
	 * @return the algorithm manager.
	 */
	public AlgorithmManager getAlgorithmManager() {
		return algorithmManager;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public PluginManager getPluginManager() {
		return pluginmgr;
	}

	/**
	 * Returns <code>true</code>, if a session is active.
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isSessionActive() {
		return getActiveSession() != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public SessionManager getSessionManager() {
		return this;
	}

	public static Set<Session> getSessions() {
		return instance.sessions;
	}
	
	public static Set<EditorSession> getEditorSessions() {
		HashSet<EditorSession> result = new HashSet<EditorSession>();
		for (Session s : getSessions()) {
			if (s instanceof EditorSession)
				result.add((EditorSession)s);
		}
		return result;
	}

	/**
	 * Returns an iterator over all sessions.
	 *
	 * @return an iterator over all sessions.
	 *
	 * @see org.graffiti.session.Session
	 */
	public Iterator getSessionsIterator() {
		return sessions.iterator();
	}

	//    /**
	//     * DOCUMENT ME!
	//     *
	//     * @return DOCUMENT ME!
	//     */
	//    public Preferences getUiPrefs()
	//    {
	//        return uiPrefs;
	//    }

	/**
	 * Returns the class for undoSupport.
	 *
	 * @return the class for undoSupport.
	 */
	public UndoableEditSupport getUndoSupport() {
		return undoSupport;
	}

	/**
	 * Returns the viewManager.
	 *
	 * @return ViewManager
	 */
	public ViewManager getViewManager() {
		return viewManager;
	}

	/**
	 * Returns the zoomListeners.
	 *
	 * @return List
	 */
	public Collection getZoomListeners() {
		return zoomListeners;
	}

	/**
	 * Adds the <code>JComponent</code> component to the gui-component
	 * specified by id. If the specified gui-component does not exist a new
	 * one will be created with id id and the newly created gui-component will
	 * be added to the gui of the editor.
	 *
	 * @param id the id of the gui-component where the component shall be
	 *        added.
	 * @param component the <code>JComponent</code> which shall be added to the
	 *        specified gui-component.
	 */
	public void addGUIComponent(String id, JComponent component) {
		// all GraffitiContainers should be JComponents
		JComponent container = (JComponent) guiMap.get(id);

		if (container != null) {

			if (component instanceof Undoable) {
				((Undoable) component).setUndoSupport(undoSupport);
			}

			// if the component is itself a container, then add it to the guiMap
			if (component instanceof GraffitiContainer) {
				GraffitiContainer con = (GraffitiContainer) component;
				guiMap.put(con.getId(), (JComponent)con);
			}

			// if the component is a ToolButton, the represented tool has to be
			// added to the mode this ToolButton likes to be in
			if (component instanceof ToolButton) {
				ToolButton tb = (ToolButton) component;
				modeManager.getMode(tb.getPreferredComponent()).addTool(tb.getTool());

				// if the tool provides undo information the undoSupport has 
				// to be set.
				if (tb.getTool() instanceof Undoable) {
					((Undoable) tb.getTool()).setUndoSupport(undoSupport);
				}
			} else if (component instanceof GraffitiComponent) {
				if (component instanceof ViewListener) {
					this.viewManager.addViewListener((ViewListener) component);
				}

				if (component instanceof SessionListener) {
					this.sessionListeners.add((SessionListener)component);
				}
			}

			if (component instanceof JToolBar) {
				JToolBar jt = (JToolBar)component;
				try {
					JToolBar toolbar = (JToolBar) getGUIcomponentFromMap("defaultToolbar");
					toolbar.addSeparator();
					for (Component jc : jt.getComponents()) {
						if (jc instanceof JButton) {
							JButton jjjbbb = (JButton)jc;
							if (jjjbbb.getIcon()!=null) {
								jjjbbb.setBorderPainted(false);
								jjjbbb.setOpaque(false);
								jjjbbb.setBackground(null);
							}
						}
						toolbar.add(jc);
					}
					toolbar.validate();
				} catch(Exception e) {
					container.add(component);
				}
			} else 
				container.add(component);

			if (container.getParent() instanceof JSplitPane) {
				// adjust divider location
				JSplitPane pane = (JSplitPane) container.getParent();
				pane.setResizeWeight(1.0);

				if (pane.getWidth() != 0)
					pane.setDividerLocation(1 - ((container.getPreferredSize()
								.getWidth() + 10) / pane.getWidth()));

				if (container.getPreferredSize().getWidth() < 20) {
					container.setMinimumSize(new Dimension(160, pane.getHeight()));
				} else {
					container.setMinimumSize(container.getPreferredSize());
				}
			}
			container.revalidate();
		} else {
			ErrorMsg.addErrorMessage("Don't know where to put component "+component+" with target id "+id);
		}
	}

	/**
	 * Adds a <code>SelectionListener</code>.
	 *
	 * @param sl DOCUMENT ME!
	 */
	public void addSelectionListener(SelectionListener sl) {
		this.selectionListeners.add(sl);

		for (Iterator it = getSessions().iterator(); it.hasNext();) {
			try {
				EditorSession eSession = (EditorSession) it.next();
				eSession.getSelectionModel().addSelectionListener(sl);
			} catch (ClassCastException cce) {
				// ok: non-editor sessions have no selection model
			}
		}
	}
	
	/**
	 * Adds the given session to the list of sessions.
	 *
	 * @param s the new session to add.
	 */
	public void addSession(Session s) {
		sessions.add(s);

		if (s instanceof EditorSession) {
			SelectionModel selModel = new SelectionModel();
			((EditorSession) s).setSelectionModel(selModel);

			for (Iterator it = selectionListeners.iterator(); it.hasNext();) {
				selModel.addSelectionListener((SelectionListener) it.next());
			}

			selModel.add(new Selection(sBundle.getString("activeSelection")));
			selModel.setActiveSelection(sBundle.getString("activeSelection"));
		}
	}

	/**
	 * Adds a <code>SessionListener</code>.
	 *
	 * @param sl DOCUMENT ME!
	 */
	public void addSessionListener(SessionListener sl) {
		this.sessionListeners.add(sl);
	}
	
	public void addViewListener(ViewListener vl) {
		this.getViewManager().addViewListener(vl);
	}

	/**
	 * Removes any messages displayed by calls to <code>showMessage</code> or
	 * <code>showError</code>.
	 */
	public void clearMessages() {
		statusBar.clear();
	}

	/**
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(ComponentEvent e) {
	}

	/**
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(ComponentEvent e) {
	}

	/**
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent e) {
		for (GraffitiInternalFrame frame : activeFrames) {
			if (frame.isMaximum()) {
//				frame.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
//				pack();

				//                try {
				//                    frame.setMaximum(false);
				//                  frame.setMaximum(true);
				//                } catch (PropertyVetoException pve) {
				//                    // does not matter if not allowed
				//                }
			}
		}
	}

	/**
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(ComponentEvent e) {
	}

	/**
	 * Creates and adds a new internal frame to the desktop within an existing
	 * session.
	 *
	 * @param viewName a name of the new view
	 * @param newFrameTitle the title for the frame, if <code>null</code> or
	 *        the empty String no title will be set.
	 * @param returnScrollpane DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public JScrollPane createInternalFrame(String viewName,
				String newFrameTitle, boolean returnScrollpane, boolean otherViewWillBeClosed) {
		return (JScrollPane) createInternalFrame(viewName, newFrameTitle, activeSession,
					returnScrollpane, false, otherViewWillBeClosed);
	}
	

	/**
	 * Creates and adds a new internal frame to the desktop within a new
	 * session.
	 *
	 * @param viewName a name of the new view
	 * @param newFrameTitle the title for the frame, if <code>null</code> or
	 *        the empty String no title will be set.
	 * @param session a new session.
	 * @param returnScrollPane A scrollpane or a graffitiinternalframe
	 *
	 * @return DOCUMENT ME!
	 *
	 * @throws RuntimeException DOCUMENT ME!
	 */
	public Object createInternalFrame(String viewName,
				String newFrameTitle, EditorSession session,
				boolean returnScrollPane, boolean returnGraffitiFrame,
				boolean otherViewWillBeClosed) {
		
		View view;
		try {
			if (viewManager == null) {
				ErrorMsg.addErrorMessage("Could not create frame for graph. viewManager is NULL");
				return null;
			}
			view = viewManager.createView(viewName);
		} catch (InstanceCreationException e) {
			ErrorMsg.addErrorMessage("Could not create view " + viewName
						+ ". Error: " + e.getLocalizedMessage());
			return null;
		}
		
		if (session == null) {
			ErrorMsg.addErrorMessage("Could not create frame for graph. Session is NULL");
			return null;
		} else {
			if (session.getGraph() == null)
				return null;
		}

		view.setAttributeComponentManager(this.attributeComponentManager);

		String modeName = "org.graffiti.plugins.modes.defaultEditMode";
		session.changeActiveMode(modeManager.getMode(modeName));

		GraffitiInternalFrame frame = null;

		if (!returnScrollPane) {
			frame = new GraffitiInternalFrame(session, view, newFrameTitle, otherViewWillBeClosed);
			frame.addInternalFrameListener(graffitiFrameListener);
		}

		ListenerManager lm = session.getGraph().getListenerManager();
		lm.addDelayedAttributeListener(view); 
		lm.addDelayedEdgeListener(view);
		lm.addDelayedNodeListener(view);
		lm.addDelayedGraphListener(view);
		lm.addDelayedGraphListener(statusBar); 

		view.setGraph(session.getGraph());
		
		session.addView(view);
		session.setActiveView(view);

		this.activeSession = session;

		sessions.add(session);

		if (session != null) {
			SelectionModel selModel = new SelectionModel();
			session.setSelectionModel(selModel);

			this.fireSessionChanged(session);

			for (Iterator it = selectionListeners.iterator(); it.hasNext();) {
				selModel.addSelectionListener((SelectionListener) it.next());
			}

			selModel.add(new Selection(sBundle.getString("activeSelection")));
			selModel.setActiveSelection(sBundle.getString("activeSelection"));
		}

		//        this.addSession(session);
		JScrollPane scrollPane = new JScrollPane(view.getViewComponent(),
					ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setWheelScrollingEnabled(false);

		if (!returnScrollPane) {
			if (view.putInScrollPane())
				frame.getContentPane().add(scrollPane);
			else
				frame.getContentPane().add(view.getViewComponent());

			frame.pack();
			
			boolean maxx = false;

			JInternalFrame currentFrame = desktop.getSelectedFrame();
			
			if (!returnGraffitiFrame && (currentFrame == null || currentFrame.isMaximum())) {
				maxx = true;
			}
			
			GravistoService.getInstance().framesDeselect();

			if (!returnGraffitiFrame) {
				frame.setVisible(true);
				desktop.add(frame);
				Java_1_5_compatibility.setComponentZorder(desktop, frame);
			}
			
			try {
				frame.setSelected(true);
				
			} catch (PropertyVetoException e1) {
				ErrorMsg.addErrorMessage(e1);
			}
			// maximize view at beginning

			if (maxx) {
				try {
					frame.setMaximum(true);
				} catch (PropertyVetoException pve) {
					// should not happen
					ErrorMsg.addErrorMessage(pve);
				}
			}
			viewFrameMapper.put(view, frame);
			activeFrames.add(frame);
		}

		this.zoomListeners.add(view);

		if (scrollPane == null) {
			ErrorMsg.addErrorMessage("Error: ScrollPane for graph is NULL");
		}
		if (returnGraffitiFrame)
			return frame;
		else
			return scrollPane;
	}

	/**
	 * Creates and returns a new editor session.
	 *
	 * @return New session
	 */
	public Session createNewSession() {
		return new EditorSession();
	}

	/**
	 * Informs all <code>SessionListener</code>s that the active session has
	 * changed.
	 *
	 * @param session DOCUMENT ME!
	 */
	public void fireSessionChanged(Session session) {
		for (Iterator it = this.sessionListeners.iterator(); it.hasNext();) {
			((SessionListener) it.next()).sessionChanged(session);
		}
	}
	
	public void fireSelectionChanged(Session session) {
		for (Iterator it = this.selectionListeners.iterator(); it.hasNext();) {
			((SelectionListener) it.next()).selectionChanged(
					new SelectionEvent(((EditorSession)session).getSelectionModel().getActiveSelection()));
		}
	}

	/**
	 * Called, if the session or data (except graph data) in the session have
	 * been changed.
	 *
	 * @param session DOCUMENT ME!
	 */
	public void fireSessionDataChanged(Session session) {
		for (Iterator it = this.sessionListeners.iterator(); it.hasNext();) {
			((SessionListener) it.next()).sessionDataChanged(session);
		}
	}

	/*
	 * @see org.graffiti.managers.IOManager.IOManagerListener#inputSerializerAdded(org.graffiti.plugin.io.InputSerializer)
	 */
	public void inputSerializerAdded(InputSerializer is) {
		updateActions();
	}

	/**
	 * Loads a graph from a file;
	 *
	 * @param file File containing the graph;
	 */
	public Graph loadGraph(String fileName, URL url) {
		String ext = fileName.substring(fileName.lastIndexOf("."));
		try {
			MyInputStreamCreator ic = new MyInputStreamCreator(url);
			InputSerializer is = ioManager.createInputSerializer(ic, ext);
			if (is == null) {
				ErrorMsg.addErrorMessage("Graph " + fileName
							+ " could not be loaded. InputSerializer is NULL.");
				return null;
			}
			Graph g = is.read(ic.getNewInputStream());
			if (g == null) {
				ErrorMsg.addErrorMessage("Graph " + fileName
							+ " could not be loaded. File loader result is NULL.");
				return null;
			}
			g.setName(fileName);
			g.setModified(false);
			EditorSession es = new EditorSession(g);
			if (es == null) {
				ErrorMsg.addErrorMessage("Graph "
										+ fileName
										+ " could not be loaded. Editor session could not be created from graph (EditorSession=NULL).");
				return null;
			}
			try {
				es.setFileName(url.toURI());
			} catch(Exception e) {
				ErrorMsg.addErrorMessage(e);
			}
			showViewChooserDialog(es, false, true, null);
			
			return g;
		} catch (org.graffiti.plugin.io.ParserException e1) {
			JOptionPane
						.showMessageDialog(null, sBundle.getString("fileFormatError")
									.replaceAll("\\[err\\]", e1.getLocalizedMessage()),
									sBundle.getString("fileFormatErrorTitle"),
									JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			ErrorMsg.addErrorMessage("Graph " + fileName
						+ " could not be loaded. IO Exception<br>"
						+ "Exception: <code>" + e.getLocalizedMessage() + "</code>");
		} catch (IllegalAccessException e) {
			ErrorMsg.addErrorMessage("Graph " + fileName
						+ " could not be loaded. IllegalAccessException<br>"
						+ "Exception: <code>" + e.getLocalizedMessage() + "</code>");
		} catch (InstantiationException e) {
			ErrorMsg.addErrorMessage("Graph " + fileName
						+ " could not be loaded. InstantiationException<br>"
						+ "Exception: <code>" + e.getLocalizedMessage() + "</code>");
		}
		return null;
	}
	
	public Graph getGraph(String fileName, URL url) {
		String ext = fileName.substring(fileName.lastIndexOf("."));
		try {
			MyInputStreamCreator ic = new MyInputStreamCreator(url);
			InputSerializer is = ioManager.createInputSerializer(ic, ext);
			if (is == null) {
				ErrorMsg.addErrorMessage("Graph " + fileName
							+ " could not be loaded. InputSerializer is NULL.");
				return null;
			}
			Graph g = is.read(ic.getNewInputStream());
			if (g == null) {
				ErrorMsg.addErrorMessage("Graph " + fileName
							+ " could not be loaded. File loader result is NULL.");
				return null;
			}
			g.setName(fileName);
			g.setModified(false);
			return g;
		} catch (org.graffiti.plugin.io.ParserException e1) {
			ErrorMsg.addErrorMessage(e1);
		} catch (IOException e) {
			ErrorMsg.addErrorMessage(e);
		} catch (IllegalAccessException e) {
			ErrorMsg.addErrorMessage(e);
		} catch (InstantiationException e) {
			ErrorMsg.addErrorMessage(e);
		}
		return null;
	}


	/**
	 * Loads a graph in the background (using a background thread).
	 * During loading a progress bar is shown.
	 * Do not use this command if you want to work with a given graph after
	 * loading a file. The graph will not be completely loaded or shown after this
	 * method returns. Instead the graph will be shown as soon as the file loading is
	 * finished.
	 * @param file
	 */
	public void loadGraphInBackground(final File file, ActionEvent ae, boolean autoSwitch)
				throws IllegalAccessException, InstantiationException {
		loadGraphInBackground(new File[] { file }, ae, autoSwitch);
	}
	
	final ExecutorService loader = Executors.newFixedThreadPool(1);
	
	public void loadGraphInBackground(final File[] proposedFiles, final ActionEvent ae, boolean autoSwitch)
	throws IllegalAccessException, InstantiationException {
		final ArrayList<File> files = new ArrayList<File>();
		
		HashSet<File> filesToBeIgnored = new HashSet<File>();
		for (File file : proposedFiles) {
			EditorSession esf = null;
			for (Session s : getSessions()) {
				if (s instanceof EditorSession) {
					EditorSession es = (EditorSession)s;
					if (es.getFileName()==null)
						continue;
					if (es.getFileName().equals(file.toURI())) {
						esf = es;
						break;
					}
				}
			}
			final EditorSession fesf = esf;
			if (!windowCheck(fesf, file.getAbsolutePath(), autoSwitch))
				filesToBeIgnored.add(file);
		}
		
		for (File f : proposedFiles)
			if (!filesToBeIgnored.contains(f))
				files.add(f);
		
		if (files.size()>0)
		loader.submit(new Runnable() {
			public void run() {
				int i = 1;
				StringBuilder errors = new StringBuilder();
				int errcnt = 0;
				errors.append("<html><h2>File Load - Errors: </h2>");
				for (final File file : files)  {
					try {
						System.out.println("Read file: "+file.getAbsolutePath());
						final String fileName = file.getName();
						showMessage("Loading graph file ("+fileName+")... ["+i+"/"+files.size()+"]", MessageType.PERMANENT_INFO);
						i++;
						final Graph newGraph = getGraph(file);
						System.out.println("File reading finished: "+file.getAbsolutePath());
						SwingUtilities.invokeAndWait(new Runnable() {
							public void run() {
								showMessage("Graph file is loaded. Create view... (please wait)", MessageType.PERMANENT_INFO);
								System.out.println("Create view for file: "+file.getAbsolutePath());
								EditorSession es = new EditorSession(newGraph);
								es.setFileName(file.toURI());
								showViewChooserDialog(es, false, true, ae);
								showMessage("Finished graph file loading", MessageType.INFO);
							}
						});
					} catch (Exception e) {
						errcnt++;
						errors.append("<p>File <b>"+file.getName()+"</b> could not be loaded (" + e.getLocalizedMessage() + ")!");
						ErrorMsg.addErrorMessage(e);
					} catch(AssertionError e) {
						errcnt++;
						errors.append("<p>File <b>"+file.getName()+"</b> could not be loaded (" + e.getLocalizedMessage() + ")!");
						ErrorMsg.addErrorMessage(e.getMessage());
					}
				}
				if (errcnt>0)
					showMessageDialogWithScrollBars(errors.toString(), "File(s) could not be loaded");
				}
			});
	}

	/**
	 * @return true, if file should be loaded, false if file is already loaded
	 */
	private boolean windowCheck(EditorSession fesf, String fileName, boolean autoSwitch) {
		if (fesf!=null) {
			 if(autoSwitch || JOptionPane.showConfirmDialog(this,
		             "<html>The graph file <i>" + fileName + "</i> is already loaded!<br><br>" +
		             "Click 'Yes' to activate the existing graph view.<br>" +
		             "Click 'No' to load the graph another time.</html>", "Activate existing view?",
		             JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		     {
		         for (GraffitiInternalFrame f : getActiveFrames()) {
		        	 if (f.getSession()==fesf) {
		        		 desktop.getDesktopManager().deiconifyFrame(f);
		        		 desktop.getDesktopManager().activateFrame(f);
		        		 MainFrame.showMessage("Existing view for graph file "+fileName+" has been activated", MessageType.INFO);
		        		 return false;
		        	 }
		         }
		         return true;
		     } else
		    	 return true;
		} else
			return true;
	}

	/**
	 * Loads a graph from a file;
	 *
	 * @param file File containing the graph;
	 */
	public void loadGraph(File file) {
		Graph g;
		try {
			g = getGraph(file);
			showGraph(g, null);
		} catch (Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
	}
	
	public Graph getGraph(File file) throws Exception {
		String fileName = file.getName();
		Graph newGraph = null;
		String ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
		boolean gz = false;
		if (fileName.toLowerCase().endsWith(".gz")) {
			fileName = fileName.substring(0, fileName.length()-".gz".length());
			ext = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : "";
			gz = true;
		}
		if (ext.equalsIgnoreCase(".net")) {
			Graph tempGraph = new AdjListGraph(new ListenerManager());
			InputSerializer is = ioManager.createInputSerializer(null, ext);
			is.read(file.getAbsolutePath(), tempGraph);
			newGraph = tempGraph;
		} else {
			InputSerializer is;
			MyInputStreamCreator inputStream = new MyInputStreamCreator(gz, file.getAbsolutePath());
			is = ioManager.createInputSerializer(inputStream, ext);
			if (is!=null) {
				newGraph = is.read(inputStream.getNewInputStream());
			} else {
				showMessageDialog("No known input serializer for file extension "+ext+"!", "Error");
			}
		}
		if (newGraph!=null) {
			newGraph.setName(file.getAbsolutePath());
			newGraph.setModified(false);
		}
		return newGraph;
	}
	
	public void saveGraphAs(Graph graph, String fileName) throws Exception {
		String ext = FileSaveAction.getFileExt(fileName);
		OutputSerializer os = ioManager.createOutputSerializer(ext);
		OutputStream outpS = new FileOutputStream(fileName);
		os.write(outpS, graph);
		outpS.close();
	}

	public void showGraph(final Graph g, final ActionEvent e) {
		if (SwingUtilities.isEventDispatchThread()) {
			EditorSession es = new EditorSession(g);
			try {
				es.setFileName(g.getName(true));
			} catch(Exception err) {
				ErrorMsg.addErrorMessage(err);
			}
			showViewChooserDialog(es, false, true, e);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					EditorSession es = new EditorSession(g);
					try {
						es.setFileName(g.getName(true));
					} catch(Exception err) {
						ErrorMsg.addErrorMessage(err);
					}
					showViewChooserDialog(es, false, true, e);
				}});
		}
	}
	
	/*
	 * @see org.graffiti.managers.IOManager.IOManagerListener#outputSerializerAdded(org.graffiti.plugin.io.OutputSerializer)
	 */
	public void outputSerializerAdded(OutputSerializer os) {
		updateActions();
	}

	InspectorPlugin inspectorPlugin = null;
	
	/**
	 * Called by the plugin manager, iff a plugin has been added.
	 *
	 * @param plugin the added plugin.
	 * @param desc the description of the new plugin.
	 */
	public void pluginAdded(GenericPlugin plugin, PluginDescription desc) {
		
		// System.out.println("Plugin added: "+desc.getMain());
		
		processEditorPlugin(plugin);

		addAlgorithmMenuItems(plugin);
		addExtensionMenuItems(plugin);

		if (plugin.isViewListener())
			viewManager.addViewListener((ViewListener)plugin);
		
		// Registers all plugins that are session listeners.
		checkSelectionListener(plugin);

		if (plugin.needsEditComponents()) {
			((NeedEditComponents) plugin).setEditComponentMap(editComponentManager.getEditComponents());
		}
		
		if (plugin instanceof InspectorPlugin) {
			if (inspectorPlugin!=null) {
				ErrorMsg.addErrorMessage("Tried to load more than one InpsectorPlugin!");
			} else {
				inspectorPlugin = (InspectorPlugin) plugin;
				for (PluginEntry p : pluginmgr.getPluginEntries()) {
					if (p.getPlugin()!=null && p.getPlugin() instanceof EditorPlugin) {
						EditorPlugin ep = (EditorPlugin) p.getPlugin();
						if (ep!=plugin) {
							processTabs(ep);
						}
					}
				}
			}
		}
		if (plugin instanceof EditorPlugin) {
			EditorPlugin ep = (EditorPlugin) plugin;
			processTabs(ep);
		}

		updateActions();
	}

	private void processTabs(EditorPlugin ep) {
		if (ep.getInspectorTabs()!=null) {
			for (InspectorTab it : ep.getInspectorTabs()) {
				if (inspectorPlugin==null) {
					// ErrorMsg.addErrorMessage("Inspector Plugin not available. Can't add side-panel tabs.");
				} else {
					if (it==null)
						ErrorMsg.addErrorMessage("Plugin "+ep.getClass().getCanonicalName()+" contains InspectorTab with value NULL!");
					else {
						if (isAddon(ep))
							it.setIcon(ep.getIcon());
						inspectorPlugin.addTab(it);
					}
				}
			}
		}
	}
	
	public InspectorPlugin getInspectorPlugin() {
		return inspectorPlugin;
	}

	/**
	 * @param plugin
	 */
	private void checkSelectionListener(GenericPlugin plugin) {
		if (plugin.isSessionListener()) {
			addSessionListener((SessionListener) plugin);
		}

		if (plugin.isViewListener()) {
			getViewManager().addViewListener((ViewListener) plugin);
		}

		if (plugin.isSelectionListener()) {
			selectionListeners.add((SelectionListener)plugin);

			for (Iterator it = sessions.iterator(); it.hasNext();) {
				Object sess = it.next();

				if (sess instanceof EditorSession) {
					((EditorSession) sess).getSelectionModel().addSelectionListener(
								(SelectionListener) plugin);
				}

				// TODO: check what todo if non-EditorSession ...
			}
		}
	}

	/**
	 * @param plugin
	 */
	private void processEditorPlugin(GenericPlugin plugin) {

		if (!(plugin instanceof EditorPlugin)) return;

		EditorPlugin eplugin = (EditorPlugin) plugin;

		Mode[] modes = eplugin.getModes();

		if (modes != null) {
			// for every mode create a ModeToolbar
			for (int i = modes.length - 1; i >= 0; i--) {
				ModeToolbar mtb = new ModeToolbar(modes[i]);

				guiMap.put(mtb.getId(), mtb);
				getContentPane().add(mtb, BorderLayout.WEST);
//				leftToolBarPanel.add(mtb);
//				mtb.setVisible(false);
			}
		}

		// add gui component to gui and if necessary update the mapping
		GraffitiComponent[] gc = eplugin.getGUIComponents();

		if (gc != null) {
			for (int i = 0; i < gc.length; i++) {
				addGUIComponent(gc[i].getPreferredComponent(), (JComponent) gc[i]);
			}
		}

		Tool[] tools = eplugin.getTools();
		if (tools != null) {
			for (int i = tools.length - 1; i >= 0; i--) {
				if (tools[i].isSessionListener())
					addSessionListener((SessionListener) tools[i]);

				if (tools[i].isViewListener())
					viewManager.addViewListener((ViewListener) tools[i]);

				if (tools[i].isSelectionListener()) {
					selectionListeners.add((SelectionListener)tools[i]);
					for (Iterator it = sessions.iterator(); it.hasNext();) {
						Object sess = it.next();
						if (sess instanceof EditorSession) {
							if (((EditorSession) sess).getSelectionModel() != null)
								((EditorSession) sess).getSelectionModel()
											.addSelectionListener(
														(SelectionListener) tools[i]);
						}
						// TODO: check what todo if non-EditorSession ...
					}
				}
			}
		}
	}

	/**
	 * @param algorithms
	 * @param plugin
	 */
	private void addAlgorithmMenuItems(GenericPlugin plugin) {
		// for every algorithm: add a menu entry
		Algorithm[] algorithms = plugin.getAlgorithms();
		for (int i = algorithms.length - 1; i >= 0; i--) {
			Algorithm a = algorithms[i];
			if (a != null && a.getName() != null) {
				if (a.isLayoutAlgorithm()) {
					// System.out.println("Skip Layouter: "+a.getName());
					continue; // skip layout algorithms
				}
				Action action = new RunAlgorithm(a.getClass().getName(), a
							.getName(), this, editComponentManager, a);

				algorithmActions.add(action);
				String cat = a.getCategory();
				JMenuItem menu = new JMenuItem(action);
				if (isAddon(plugin) || a.showMenuIcon())
					menu.setIcon(plugin.getIcon());
				if (a.getAcceleratorKeyStroke()!=null)
					menu.setAccelerator(a.getAcceleratorKeyStroke());
				
				addMenuItemForAlgorithmOrExtension(menu, cat);
				// menu items are now in alphabetic list
			}
		}
	}

	private boolean isAddon(GenericPlugin plugin) {
		for (PluginEntry pe : pluginmgr.getPluginEntries()) {
			if (pe.getPlugin()==plugin) {
				if (pe.getDescription().isAddon()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param algorithms
	 * @param plugin
	 */
	private void addExtensionMenuItems(GenericPlugin plugin) {
		Extension[] extensions = plugin.getExtensions();
		// for every algorithm: add a menu entry
		if (extensions != null) for (int i = extensions.length - 1; i >= 0; i--) {
			Extension e = extensions[i];
			if (e != null && e.getName() != null) {
				String cat = e.getCategory();
				for (Iterator it = e.getMenuItems().iterator(); it.hasNext();) {
					JMenuItem mi = (JMenuItem) it.next();
					addMenuItemForAlgorithmOrExtension(mi, cat);
				}
			}
		}
	}

	/**
	 * @param plugin
	 * @param action
	 * @param cat
	 */
	public void addMenuItemForAlgorithmOrExtension(JMenuItem item, String cat) {
		if (cat == null) {
			cat = "menu.plugin";
		}
		
		// System.out.println("Adding "+item.getText()+" to "+cat);

		if (guiMap.containsKey(cat) && guiMap.get(cat) instanceof JMenu) {
			JMenu targetNativeMenu = (JMenu) guiMap.get(cat);
			Boolean pluginMenuAddEmptySpaceInFrontOfMenuItem = (Boolean) targetNativeMenu
						.getClientProperty("pluginMenuAddEmptySpaceInFrontOfMenuItem");
			if (pluginMenuAddEmptySpaceInFrontOfMenuItem != null
						&& pluginMenuAddEmptySpaceInFrontOfMenuItem.booleanValue() == true) {
				if (item.getIcon() == null)
					item.setIcon(iBundle.getImageIcon("menu.file.exit.icon"));
			}
			int addAfter = targetNativeMenu.getItemCount();
			Integer pmp = (Integer) targetNativeMenu
						.getClientProperty("pluginMenuPosition");
			if (pmp != null) addAfter = pmp.intValue();
			targetNativeMenu.add(item, addAfter);
		} else {
			JMenu targetMenu;

			if (categoriesForAlgorithms.get(cat) == null) {
				JMenu newCatMenu = new JMenu(cat);
				// PLUGIN MENUS
				// pluginMenu.add(newCatMenu); // add the new category menu to the plugin menu

				getJMenuBar()
							.add(
										newCatMenu,
										getTargetMenuPosition(getJMenuBar(), newCatMenu
													.getText())); // add the new category as a top level menu item

				categoriesForAlgorithms.put(cat, newCatMenu);
			}

			targetMenu = (JMenu) categoriesForAlgorithms.get(cat);

			targetMenu.add(item);
			sortMenuItems(targetMenu, 0);
		}
		int sortFrom = 0;
		Integer pmp = (Integer) pluginMenu
					.getClientProperty("pluginMenuPosition");
		if (pmp != null) sortFrom = pmp.intValue();
		sortMenuItems(pluginMenu, sortFrom);
		validate();
	}

	public JMenuBar getJMenuBar() {
		JMenuBar res = super.getJMenuBar();
		if (res==null)
			res = storedMenuBar;
		return res;
	}

	/**
	 * Determines the target menu position of a new category sub menu.
	 * This menu will be placed inside the menu (given with the parameter <code>menu</code>).
	 * While looking for the target position the client property "pluginMenuAddAfter"
	 * will be checked. If this is <code>true</code>, then the menu item will be ignored
	 * and the first starting point is increased. If the property changes to <code>false</code>,
	 * then the target menu must be inserted before this menu, with the property set to <code>true</code>.
	 * If the property is not available. (The newly added menu items should NOT have
	 * this property set). If no immeadiatly change from true to false is found, then
	 * the new position should be set in a way so that a resulting order is achieved where
	 * the titles are sorted alphabetically.  
	 * @param menu
	 * @param title Title of the new menu to be added to the target menu (<code>menu</code>). 
	 * @return The target position where the menu should be added to.
	 */
	private int getTargetMenuPosition(JMenuBar menu, String title) {
		for (int i = 0; i < menu.getMenuCount(); i++) {
			JMenu testMenu = menu.getMenu(i);
			Boolean addAfter = (Boolean) testMenu
						.getClientProperty("pluginMenuAddAfter");
			if (addAfter != null) {
				if (addAfter.booleanValue() == true) continue; // jump ahead
				if (addAfter.booleanValue() == false) return i; // or i-1 ? add before this item
			}
			// else... add in alphabetic order (insertion sort)
			if (testMenu.getText().compareTo(title) > 0) return i;
		}
		return 0;
	}

	private void sortMenuItems(JMenu menuToSort, int startPoint) {
		if (startPoint<0)
			return;
		// sort menu items, beginning from the 3rd entry (after the ---)
		// author: c. klukas
		Vector<JMenuItem> menuItems = new Vector<JMenuItem>();
		// remove all entries and memorize them
		if (menuToSort.getItemCount()>1) {
			try {
				while (menuToSort.getItemCount() > startPoint && menuToSort.getItemCount()>0) {
						menuItems.add(menuToSort.getItem(startPoint));
						menuToSort.remove(menuToSort.getItem(startPoint));
				}
			} catch(Exception e) {
				// ErrorMsg.addErrorMessage(e);
			}
		}
		// search the first one in the remaining list 
		while (menuItems.size() > 0) {
			JMenuItem firstItem = menuItems.get(0);
			for (int im = 0; im < menuItems.size(); im++) {
				if (getText(menuItems.get(im)).compareToIgnoreCase(
							getText(firstItem)) <= 0) {
					firstItem = menuItems.get(im);
				}
			}
			// add the smallest from the memo-list
			menuToSort.add(firstItem);
			// remove from memo-list
			menuItems.remove(firstItem);
		}
	}

	private String getText(JMenuItem menuItem) {
		if (menuItem.getIcon()!=null) {
			String lbl = menuItem.getText();
			lbl = "ZZZ"+menuItem.getIcon().toString()+lbl;
			return lbl;
		} else
			return menuItem.getText();
	}

	/**
	 * Removes a <code>SelectionListener</code>.
	 *
	 * @param sl DOCUMENT ME!
	 */
	public void removeSelectionListener(SelectionListener sl) {
		this.selectionListeners.remove(sl);

		for (Iterator it = getSessions().iterator(); it.hasNext();) {
			try {
				EditorSession eSession = (EditorSession) it.next();
				eSession.getSelectionModel().removeSelectionListener(sl);
			} catch (ClassCastException cce) {
				// ok: non-editor sessions have no selection model
			}
		}
	}

	/**
	 * Closes all views of the given session and removes the session from the
	 * list of sessions.
	 *
	 * @param session the session to be removed.
	 */
	public boolean removeSession(Session session) { 
		if (session == null) return false;
		// check if changes have been made

		boolean askForSave = true;
		if (askForSave && session.getGraph().isModified()) {
			String graphName = session.getGraph().getName();
			if (graphName == null)
				graphName = "["+session.getGraph().getName()+"]";
			int res = JOptionPane.showConfirmDialog(this,
					"<html>"+sBundle.getString("frame.close_save")+"<p>"+
					"Graph "+graphName+" contains<br>"+
					session.getGraph().getNodes().size()+" node(s) and "+
					session.getGraph().getEdges().size()+" edge(s)!", 
					sBundle.getString("frame.close_save_title"),
						JOptionPane.YES_NO_CANCEL_OPTION);
			if (res == JOptionPane.YES_OPTION) {
				// save current graph
				fileSaveAs.actionPerformed(new ActionEvent(this, 0, null));
			}
			if (res == JOptionPane.CANCEL_OPTION) {
				final Graph gg = new AdjListGraph(new ListenerManager());
				gg.addGraph(session.getGraph());
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						MainFrame.getInstance().showGraph(gg, null);
					}});
			}
			// continue, close view/session
		}
		
		session.getGraph().getListenerManager().transactionStarted(this);
		session.getGraph().clear();
		session.getGraph().getListenerManager().transactionFinished(this);
		activeSession = null;
		
		List<View> views = new LinkedList<View>();

		// close all views and remove this session
		// clone the views list of this session, because it is modified
		// during the iteration and we do not want
		// ConcurrentModificationExceptions
		views.addAll(session.getViews());
		for (GraffitiFrame gf : getDetachedFrames()) {
			if (gf.getSession()==session) {
				removeDetachedFrame(gf);
				gf.setVisible(false);
				// gf.dispose();
				gf = null;
			}
		}
		for (View view : views) {
			GraffitiInternalFrame frame = viewFrameMapper.get(view);

			viewFrameMapper.remove(view);
			activeFrames.remove(frame);
			GravistoService.getInstance().removeFrame(frame);

			if (frame != null) {
				frame.setVisible(false);
				// frame.dispose();
				frame = null;
				// doDefaultCloseAction();
			}

			this.zoomListeners.remove(view);
		}
		sessions.remove(session);
		updateActions();
		return true;
	}

	/**
	 * Removes a <code>SessionListener</code>.
	 *
	 * @param sl DOCUMENT ME!
	 */
	public void removeSessionListener(SessionListener sl) {
		this.sessionListeners.remove(sl);
	}

	/**
	 * Saves the preferences of the main frame.
	 */
	public void savePreferences() {
		try {
			int screenX = (int) (getLocationOnScreen().getX());
			int screenY = (int) (getLocationOnScreen().getY());

			/*            uiPrefs.putInt("sizeWidth", getWidth());
			 uiPrefs.putInt("sizeHeight", getHeight());
			 uiPrefs.putInt("positionX", screenX);
			 uiPrefs.putInt("positionY", screenY);

			 uiPrefs.putInt("vertSplitter", vertSplitter.getDividerLocation());

			 uiPrefs.sync(); */
		} catch (IllegalComponentStateException icse) {
			ErrorMsg.addErrorMessage(icse);
		}
		//        catch(BackingStoreException bse)
		//        {
		//            // bse.printStackTrace(System.out);
		//        	ErrorMsg.addErrorMessage(bse.getLocalizedMessage());
		//        }
	}

	/**
	 * Invoked when the session changed.
	 *
	 * @param s the new session.
	 */
	public void sessionChanged(Session s) {
		Tool lastActive = AbstractTool.getActiveTool();
		if (lastActive!=null)
			lastActive.deactivateAll();
		
		if (isSessionActive()) {
			Mode oldMode = activeSession.getActiveMode();

			if (oldMode != null) {
				ModeToolbar oldtb = (ModeToolbar) guiMap.get(oldMode.getId());
//				oldtb.setVisible(false);
				getContentPane().validate();
			}

			// removing the old session from undoSupport
			undoSupport.removeUndoableEditListener(activeSession.getUndoManager());

			// removing the MainFrame from undoSupport
			undoSupport.removeUndoableEditListener(this);
		}

		if (s != null) {
			// registering the new session at undoSupport
			undoSupport.addUndoableEditListener(((EditorSession) s)
						.getUndoManager());

			// registering the MainFrame at undoSupport 
			undoSupport.addUndoableEditListener(this);

			// changing the graph in the tools of the new mode
			Mode newMode = s.getActiveMode();
			Tool t = null;

			if (newMode != null) {
				List newTools = newMode.getTools();

				for (Iterator it = newTools.iterator(); it.hasNext();) {
					Tool tl = (Tool) it.next();
					tl.setGraph(s.getGraph());
				}

				ModeToolbar newtb = ((ModeToolbar) (guiMap.get(newMode.getId())));
//				newtb.setVisible(true);
				getContentPane().validate();
				// registering the new activeTool with the views of the new session
				t = newtb.getActiveTool();
			}

			List<View> views = s.getViews();

			for (View view : views) {
				this.zoomListeners.add(view);

				MouseListener[] ml = view.getViewComponent().getMouseListeners();

				//                System.out.println("#MouseListeners: " + ml.length);
				for (int i = ml.length - 1; i >= 0; i--) {
					view.getViewComponent().removeMouseListener(ml[i]);

					//                    System.out.println("deleting listeners");
				}

				MouseMotionListener[] mml = view.getViewComponent()
							.getMouseMotionListeners();

				//                System.out.println("#MouseListeners: " + mml.length);
				for (int i = mml.length; --i >= 0;) {
					view.getViewComponent().removeMouseMotionListener(mml[i]);

					//                    System.out.println("deleting listeners");
				}

				if (t != null) {
					view.getViewComponent().addMouseListener(t);
					view.getViewComponent().addMouseMotionListener(t);
				}
			}

			this.activeSession = (EditorSession) s;
			updateActions();
		} else {
			//            System.out.println("Deactivating tools");
			// TODO what do we have to do here? (jf)
		}
		if (lastActive != null) {
			lastActive.activate();
			ToolButton.checkStatusForAllToolButtons();
		}
		boolean oneModified = false;
		for (GraffitiInternalFrame frame : getActiveFrames()) {
			frame.setTitle(frame.getSession().getGraph().getName());
			boolean mod = frame.getSession().getGraph().isModified();
			if (frame.getBorder()!=null)
			frame.putClientProperty("windowModified", mod);
			if (mod)
				oneModified = true;
		}
		getRootPane().putClientProperty("windowModified", oneModified);
	}

	/**
	 * Invoked when the session data changed.
	 *
	 * @param s DOCUMENT ME!
	 */
	public void sessionDataChanged(Session s) {
		EditorSession es = (EditorSession) s;
		boolean oneModified = false;
		for (Iterator i = es.getViews().iterator(); i.hasNext();) {
			View view = (View) i.next();

			this.zoomListeners.add(view);

			GraffitiInternalFrame frame = viewFrameMapper.get(view);

			if (es != null) {
				if (frame != null) {
					frame.setTitle(es.getGraph().getName());
					boolean mod = frame.getSession().getGraph().isModified();
					frame.putClientProperty("windowModified", mod);
					if (mod)
						oneModified = true;
				}
			}
		}
		getRootPane().putClientProperty("windowModified", oneModified);
		for (GraffitiFrame jf : getDetachedFrames()) {
			if (jf.getSession()==s || s==null)
				jf.setTitle(jf.getSession().getGraph().getName());
		}

		updateActions();
	}
	
	static String syncStatus = "sync with this";
	static String lastStatusMessage = null;

	/**
	 * Method <code>showMesssage</code> displays a message on GUI components
	 * according to the specified type. The message will be displayed for some
	 * defined number of seconds.
	 * This method can be called from a background thread. 
	 *  
	 *
	 * @param message a message string to be displayed
	 * @param type a type of the message (e.g. MessageType.INFO)
	 */
	public static void showMessage(final String message, final MessageType type) {
		int time;
		if (type == MessageType.PERMANENT_INFO)
			time = Integer.MAX_VALUE;
		else
			time = 10000;
		synchronized(syncStatus) {
			if (message==null && lastStatusMessage==null) return;
			if (message!=null && message.equals(lastStatusMessage)) return;
			lastStatusMessage=message;
			if (SwingUtilities.isEventDispatchThread()) {
				showMessageDirect(message, type, time);
			} else {
				final int finalTime = time;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						showMessageDirect(message, type, finalTime);
					}
				});
			}
		}
	}

	/**
	 * Use <code>showMessage</code> instead of this method. This method
	 * is used by showMessage, but showMessage is thread-safe.
	 * @param message
	 * @param type
	 */
	private static void showMessageDirect(String message, MessageType type, int timeMillis) {
		if (getInstance()==null) {
			System.out.println(type.toString()+": "+message);
			return;
		}
		if (type == MessageType.ERROR) {
			getInstance().statusBar.showError(message, timeMillis);
		} else if (type == MessageType.INFO) {
			getInstance().statusBar.showInfo(message, timeMillis);
		} else if (type == MessageType.PERMANENT_INFO) {
			getInstance().statusBar.showInfo(message, Integer.MAX_VALUE);
		}
	}

	/**
	 * Method <code>showMesssage</code> displays a message on GUI components
	 * according to the specified type for the given interval.
	 * This method is thread safe.
	 *
	 * @param message a message string to be displayed
	 * @param type a type of the message (e.g. ERROR)
	 * @param timeMillis number of milliseconds the message should be displayed
	 */
	public static void showMessage(final String message, final MessageType type,
				final int timeMillis) {
		if (SwingUtilities.isEventDispatchThread()) {
			showMessageDirect(message, type, timeMillis);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					showMessageDirect(message, type, timeMillis);
				}
			});
		}
	}

	/**
	 * Method <code>showViewChooserDialog </code> invokes a view chooser dialog
	 * for choosing view types. The parameter withNewSession  specifies
	 * whether the new view starts within an existing session or within a new
	 * session.
	 *
	 * @param returnScrollpane DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public JScrollPane showViewChooserDialog(boolean returnScrollpane, boolean alwaysUseDefaultView, ActionEvent ae) {
		return showViewChooserDialog(activeSession, returnScrollpane, alwaysUseDefaultView, ae);
	}

	/**
	 * Method <code>showViewChooserDialog </code> invokes a view chooser dialog
	 * for choosing view types. The parameter withNewSession  specifies
	 * whether the new view starts within an existing session or within a new
	 * session.
	 *
	 * @param session the session in which to open the new view.
	 * @param returnScrollPane DOCUMENT ME!
	 * @param e 
	 *
	 * @return DOCUMENT ME!
	 */
	public JScrollPane showViewChooserDialog(EditorSession session,
				boolean returnScrollPane, boolean alwaysUseDefaultView, ActionEvent e) {
		if (viewManager == null) {
			ErrorMsg.addErrorMessage("Error: View Manager is NULL");
		}
		String[] views = viewManager.getViewNames();
		if (views.length == 0) {
			JOptionPane.showMessageDialog(this, sBundle
						.getString("viewchooser.pluginNotAdded"), sBundle
						.getString("viewchooser.errorDialog.title"),
						JOptionPane.ERROR_MESSAGE);
		} else if (viewManager.getViewNames().length == 1) {
			if (sessions.contains(session)) {
				return createInternalFrame(views[0], session.getGraph().getName(),
							returnScrollPane, false);
			} else {
				JScrollPane jsp = (JScrollPane) createInternalFrame(views[0], 
						session.getGraph().getName(), session, returnScrollPane, false, false);
				return jsp;
			}
		} else {
			if (ReleaseInfo.getRunningReleaseStatus()!=Release.KGML_EDITOR) {
				if (alwaysUseDefaultView && session.getGraph()!=null &&
						( (session.getGraph().getNumberOfNodes()+session.getGraph().getNumberOfEdges()>1000)
								|| ((e!=null && (e.getModifiers() & ActionEvent.SHIFT_MASK)==ActionEvent.SHIFT_MASK)) 
						)
								)
					alwaysUseDefaultView = false; // show view chooser dialog in case the graph size is large
			}
			if (alwaysUseDefaultView) {
				String defaultView = viewManager.getDefaultView();
				if (sessions.contains(session)) {
					return createInternalFrame(defaultView, session.getGraph().getName(), 
							returnScrollPane, false);
				} else {
					Graph g = session.getGraph();
					String name = null;
					if (g!=null)
						name = g.getName();
					if (name==null)
						name = "[NULL]";
					JScrollPane jsp = (JScrollPane)createInternalFrame(defaultView, name, 
							session, returnScrollPane, false, false);
					return jsp;
				}
			} else {
				ViewTypeChooser viewChooser = new ViewTypeChooser(this, 
						sBundle.getString("viewchooser.title")+
						" ("+session.getGraph().getNumberOfNodes()+
						" nodes, "+session.getGraph().getNumberOfEdges()+
						" edges)", viewManager.getViewDescriptions());
	
				// The user did not select a view.
				if (viewChooser.getSelectedView() == -1) { return null; }
	
				String selectedView = views[viewChooser.getSelectedView()];
	
				if (viewChooser.createInternalFrame()) {
					if (selectedView != null) {
						if (sessions.contains(session)) {
							return createInternalFrame(selectedView, session.getGraph().getName(),
									returnScrollPane, false);
						} else {
							return (JScrollPane)createInternalFrame(selectedView, 
									session.getGraph().getName(), session, returnScrollPane, 
									false, false);
						}
					} 
				} else {
					GraffitiInternalFrame gif = (GraffitiInternalFrame) createInternalFrame(
							selectedView, session.getGraph().getName(), session, 
							false, true, false); 
					GraffitiFrame gf = new GraffitiFrame(gif);
					gf.setExtendedState(Frame.MAXIMIZED_BOTH);
					gf.setVisible(true);
					addDetachedFrame(gf);
				}
				
			}
		}

		return null;
	}

	/**
	 * This method is called when an undoableEdit happened.
	 *
	 * @see javax.swing.event.UndoableEditListener
	 */
	public void undoableEditHappened(UndoableEditEvent e) {
		editUndo.update();
		editRedo.update();
	}

	/**
	 * Updates the state of the actions.
	 */
	public void updateActions() {
		GraffitiAction.updateAllActions();
	}

	/*
	 * @see org.graffiti.managers.ViewManager.ViewManagerListener#viewTypeAdded(java.lang.String)
	 */
	public void viewTypeAdded(String viewType) {
		updateActions();
	}

	/**
	 * Shows an error in a modal dialog box. (thread safe)
	 *
	 * @param msg the message to be shown.
	 */
	protected void showError(final String msg) {
		if (SwingUtilities.isEventDispatchThread()) {
			JOptionPane
						.showMessageDialog(this, msg, StringBundle.getInstance()
									.getString("message.dialog.title"),
									JOptionPane.ERROR_MESSAGE);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					showError(msg);
				}
			});
		}
	}

	/**
	 * Returns the active <code>Tool</code>.
	 *
	 * @return the active <code>Tool</code>.
	 */
	Tool getActiveTool() {
		if (activeSession != null) { 
			return ((ModeToolbar) guiMap.get(activeSession.getActiveMode().getId())).getActiveTool(); 
		} else
			return null;
	}

	/**
	 * Method fireViewChanged.
	 *
	 * @param newView
	 */
	void fireViewChanged(View newView) {
		activeSession.setActiveView(newView);
		viewManager.viewChanged(newView);
	}
	


	/**
	 * Sets the accel key of the given item.  The accel key information is
	 * gathered from the <code>sBundle</code>.
	 *
	 * @param item DOCUMENT ME!
	 * @param action DOCUMENT ME!
	 */
	private void setAccelKey(JMenuItem item, GraffitiAction action) {
		String accel = sBundle.getString("menu." + action.getName() + ".accel");

		if (accel != null) {
			try {
				int mask = 0;
				String vers = System.getProperty("os.name").toLowerCase();
			    if (vers.indexOf("mac") >= 0) {
			       accel = ErrorMsg.stringReplace(accel,"CTRL", "META");
			       if (accel.equalsIgnoreCase("ALT_F4"))
			    	   accel = "META_Q";
			    }

				if (accel.startsWith("META")) {
					mask += ActionEvent.META_MASK;
					accel = accel.substring(5);
				}

				if (accel.startsWith("CTRL")) {
					mask += ActionEvent.CTRL_MASK;
					accel = accel.substring(5);
				}

				if (accel.startsWith("SHIFT")) {
					mask += ActionEvent.SHIFT_MASK;
					accel = accel.substring(6);
				}

				if (accel.startsWith("ALT")) {
					mask += ActionEvent.ALT_MASK;
					accel = accel.substring(4);
				}

				int key = KeyEvent.class.getField("VK_" + accel).getInt(null);
				item.setAccelerator(KeyStroke.getKeyStroke(key, mask));
			} catch (Exception e) {
				ErrorMsg.addErrorMessage(e);
			}
		}
	}

	/**
	 * Creates the action instances.
	 */
	private void createActions() {
		newGraph = new FileNewAction(this, viewManager);
		fileOpen = new FileOpenAction(this, ioManager, viewManager, sBundle);
		fileSave = new FileSaveAction(this, ioManager, this);
		fileClose = new FileCloseAction(this);

		fileSaveAs = new FileSaveAsAction(this, ioManager, this, sBundle);
		fileSaveAll = new FileSaveAllAction(this, ioManager);

		fileExit = new ExitAction(this);

		viewNew = new ViewNewAction(this, sBundle);

		pluginManagerEdit = new PluginManagerEditAction(this, pluginmgr);

		editUndo = new EditUndoAction(this);
		editRedo = new EditRedoAction(this);

		editCut = new CutAction(this);
		editCopy = new CopyAction(this);
		editPaste = new PasteAction(this);

		editDelete = new DeleteAction(this);
		editSelectAll = new SelectAllAction(this);

		redrawView = new RedrawViewAction(this);
	}

	/**
	 * Constructs a menu, and returns the menu.  &quot;menu.&quot;<tt>name</tt>
	 * is read from the string bundle.
	 * &uqot;menu.&quot;<tt>name</tt>&quot;.icon&quot; is read from the image
	 * bundle.
	 *
	 * @param name the name of the menu item.
	 *
	 * @return DOCUMENT ME!
	 */
	private JMenu createMenu(String name) {
		String title = sBundle.getString("menu." + name);
		JMenu menu = new JMenu(title);

		guiMap.put("menu." + name, menu);

		try {
			String mnem = sBundle.getString("menu." + name + ".mnemonic");

			if (mnem != null) {
				menu.setMnemonic(Class.forName("java.awt.event.KeyEvent").getField(
							mnem).getInt(null));
			}
		} catch (Exception e) {
			ErrorMsg.addErrorMessage("Create menu error: "
						+ e.getLocalizedMessage());
		}

		return menu;
	}

	/**
	 * Creates and returns the menu bar.
	 *
	 * @return the menu bar.
	 */
	private JMenuBar createMenuBar(JMenu windowMenu) {
		JMenuBar menuBar = new JMenuBar();
		
//		menuBar.putClientProperty(Options.HEADER_STYLE_KEY, HeaderStyle.SINGLE);
		guiMap.put("menu", menuBar);

		// menu for file operations
		JMenu fileMenu = createMenu("file");
		menuBar.add(fileMenu);

		fileMenu.add(createMenuItem(newGraph));
		fileMenu.add(createMenuItem(viewNew));
		fileMenu.add(createMenuItem(fileOpen));
		fileMenu.addSeparator();
		fileMenu.add(createMenuItem(fileSave));
		fileMenu.add(createMenuItem(fileSaveAs));

		fileMenu.addSeparator();
		fileMenu.addSeparator();

		fileMenu.add(createMenuItem(fileClose));
		fileMenu.add(createMenuItem(fileExit));

		// plugin menu entries will be added after position 7
		// the default commands like open file, save file etc. should be the first
		// menu items
		fileMenu.putClientProperty("pluginMenuPosition", new Integer(7));

		// top level plugin menu entries (category menus) should be added
		// after the file menu, and also after the edit menu, but
		// before the window menu, this property controls this behavior
		// the plugin menu entries will be added alphabetically sorted between
		// the menu items which have first the value true and later the value false
		fileMenu.putClientProperty("pluginMenuAddAfter", new Boolean(true));

		// plugin menu entries should have empty space, where a icon could be displayed, this
		// way the plugin menu items will be inline with the other menu items, which have a menu icon
		fileMenu.putClientProperty("pluginMenuAddEmptySpaceInFrontOfMenuItem",
					new Boolean(true));

		JMenu editMenu = createMenu("edit");
		editMenu.putClientProperty("pluginMenuAddEmptySpaceInFrontOfMenuItem",
					new Boolean(true));
		menuBar.add(editMenu);

		editMenu.add(createMenuItem(editUndo));
		editMenu.add(createMenuItem(editRedo));
		editMenu.addSeparator();
		editMenu.add(createMenuItem(editCut));
		editMenu.add(createMenuItem(editCopy));
		editMenu.add(createMenuItem(editPaste));
		editMenu.addSeparator();
		editMenu.add(createMenuItem(editDelete));
		JMenuItem selectCmd = createMenuItem(editSelectAll); 
		selectCmd.setIcon(iBundle.getImageIcon("menu.file.exit.icon"));
		editMenu.add(selectCmd);
		// editMenu.addSeparator();
		// JMenuItem redrawCmd = createMenuItem(redrawView); 
		// redrawCmd.setIcon(iBundle.getImageIcon("menu.file.exit.icon"));
		// editMenu.add(redrawCmd);

		editMenu.putClientProperty("pluginMenuPosition", new Integer(9));
		editMenu.putClientProperty("pluginMenuAddAfter", new Boolean(true));

		pluginMenu = createMenu("plugin");
		pluginMenu.putClientProperty("pluginMenuAddAfter", new Boolean(true));

		if (uiPrefs.get("showPluginMenu", "true").equalsIgnoreCase("true"))
			menuBar.add(pluginMenu);

		if (uiPrefs.get("showPluginManagerMenuOptions", "true").equals("true")) {
			pluginMenu.add(createMenuItem(pluginManagerEdit));

			// NEW SAVE / LOmenuBarAD PREFRENCES ***************************************
			JMenuItem pluginPrefsSave = new JMenuItem("Save Preferences...");
			JMenuItem pluginPrefsLoad = new JMenuItem("Load Preferences...");
			pluginPrefsLoad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					fc.showOpenDialog(null);
					File selFile = fc.getSelectedFile();
					String err = null;
					try {
						Preferences.importPreferences(new FileInputStream(selFile));
					} catch (FileNotFoundException e1) {
						err = e1.getLocalizedMessage();
					} catch (IOException e1) {
						err = e1.getLocalizedMessage();
					} catch (InvalidPreferencesFormatException e1) {
						err = e1.getLocalizedMessage();
					}
					if (err != null)
						JOptionPane.showMessageDialog(null,
									"Error while reading preferences: " + err, "Error",
									JOptionPane.ERROR_MESSAGE);
					try {
						getPluginManager().loadStartupPlugins();
					} catch (PluginManagerException e2) {
						String errm = e2.getLocalizedMessage();
						JOptionPane.showMessageDialog(null,
									"Error while loading plugins: " + errm, "Error",
									JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			pluginPrefsSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					fc.showSaveDialog(null);
					File selFile = fc.getSelectedFile();
					String err = null;
					try {
						Preferences prefs = Preferences
									.userNodeForPackage(GraffitiEditor.class);
						prefs.exportSubtree(new FileOutputStream(selFile));
					} catch (FileNotFoundException e1) {
						err = e1.getLocalizedMessage();
					} catch (IOException e1) {
						err = e1.getLocalizedMessage();
					} catch (BackingStoreException e1) {
						err = e1.getLocalizedMessage();
					}
					if (err != null)
						JOptionPane.showMessageDialog(null,
									"Error while saving preferences: " + err, "Error",
									JOptionPane.ERROR_MESSAGE);
				}
			});
			pluginMenu.add(pluginPrefsSave);
			pluginMenu.add(pluginPrefsLoad);

			// ******************************************************************
			pluginMenu.addSeparator();
			pluginMenu.putClientProperty("pluginMenuPosition", new Integer(4));
		} else
			pluginMenu.putClientProperty("pluginMenuPosition", new Integer(0));

		windowMenu.putClientProperty("pluginMenuAddEmptySpaceInFrontOfMenuItem",
				new Boolean(false));
		windowMenu.putClientProperty("pluginMenuAddAfter", new Boolean(false));
		// windowMenu.add(redrawCmd);
		menuBar.add(windowMenu);
		
		// menuBar.setBorderPainted(false);

		return menuBar;
	}

	/**
	 * Shows an arbitrary message dialog.
	 *
	 * @param msg the message to be shown.
	 */
	public void showMessageDialog(String msg) {
		showMessageDialog(msg, StringBundle.getInstance().getString(
					"message.dialog.title"));
	}
	
	public JComponent getGUIcomponentFromMap(String id) {
		return guiMap.get(id);
	}
	
	public void setGUIcomponent(String id, JComponent o)
	{
		if (guiMap.containsKey(id))
			guiMap.remove(id);
		guiMap.put(id, o);
	}

	/**
	 * Shows an arbitrary message dialog.
	 *
	 * @param msg the message to be shown.
	 */
	public static void showMessageDialog(final String msg, final String title) {
		if (SwingUtilities.isEventDispatchThread()) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), new JLabel(msg), title,
						JOptionPane.INFORMATION_MESSAGE);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(MainFrame.getInstance(), new JLabel(msg), title,
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}
	
	public static void showMessageDialogWithScrollBars(final String msg, final String title) {
		if (SwingUtilities.isEventDispatchThread()) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), new MyScrollLabel(msg), title,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						showMessageDialogWithScrollBars(msg, title);
					} catch(Exception e) {
						
					}
				}
			});
		}
	}
	
	public static void showMessageDialogWithScrollBars2(final String msg, final String title) {
		if (SwingUtilities.isEventDispatchThread()) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), new MyScrollLabel(msg, 1000, 700), title,
					JOptionPane.PLAIN_MESSAGE);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						showMessageDialogWithScrollBars2(msg, title);
					} catch(Exception e) {
						
					}
				}
			});
		}
	}

	public static MainFrame getInstance() {
		return instance;
	}

	/**
	 * Constructs a menu item, registers this class as action listener and
	 * returns the menu item.  &quot;menu.&quot;<tt>name</tt> is read from the
	 * string bundle. &uqot;menu.&quot;<tt>name</tt>&quot;.icon&quot; is read
	 * from the image bundle.
	 *
	 * @param action the action, which should be executed by this menu item.
	 *
	 * @return DOCUMENT ME!
	 */
	private JMenuItem createMenuItem(final GraffitiAction action) {
		String actionName = action.getName();
		
		JMenuItem item = new JMenuItem(action);
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						GraffitiAction.updateAllActions();
					}});
			}});
		
		item.setText(sBundle.getString("menu." + actionName));
		item.setIcon(iBundle.getImageIcon("menu." + actionName + ".icon"));

		try {
			String mnem = sBundle.getString("menu." + actionName + ".mnemonic");

			if (mnem != null) {
				item.setMnemonic(Class.forName("java.awt.event.KeyEvent").getField(
							mnem).getInt(null));
			}
		} catch (Exception e) {
			ErrorMsg.addErrorMessage(e.getLocalizedMessage());
		}

		setAccelKey(item, action);

		return item;
	}

	/**
	 * Creates the editor's tool bar.
	 *
	 * @return the toolbar for the editor.
	 */
	private JToolBar createToolBar() {
		final JToolBar toolBar = new JToolBar();
		
		toolBar.add(createToolBarButton(newGraph));
		toolBar.add(createToolBarButton(fileOpen));
		toolBar.addSeparator();
		toolBar.add(createToolBarButton(fileSave));
		toolBar.add(createToolBarButton(fileSaveAs));

		toolBar.addSeparator();
		if (!ReleaseInfo.isRunningAsApplet()) {
			toolBar.add(createToolBarButton(editCut));
			toolBar.add(createToolBarButton(editCopy));
			toolBar.add(createToolBarButton(editPaste));
			toolBar.addSeparator();
		}
		toolBar.add(createToolBarButton(editUndo));
		toolBar.add(createToolBarButton(editRedo));
		
		toolBar.setFloatable(true);
		
		return toolBar;
	}

	/**
	 * Constructs and returns a button.
	 *
	 * @param action the action, which is associated with this button.
	 *
	 * @return DOCUMENT ME!
	 */
	private JButton createToolBarButton(GraffitiAction action) {
		JButton button = new JButton(action);
		button.setBorderPainted(false);
		
		button.setOpaque(false);
		button.setBackground(null);

		button.setText(sBundle.getString("toolbar." + action.getName()));
		button.setToolTipText(sBundle.getString("toolbar." + action.getName()
					+ ".tooltip"));
		button.setIcon(iBundle.getImageIcon("toolbar." + action.getName()
					+ ".icon"));

		//a little bag of tricks: java developers use a string value for this property
		// instead of a constant, moreover is this string value not documented.ww 

		button.putClientProperty("hideActionText", new Boolean(true));

		try {
			String mnem = sBundle.getString("toolbar." + action.getName()
						+ ".mnemonic");

			if (mnem != null) {
				button.setMnemonic(Class.forName("java.awt.event.KeyEvent")
							.getField(mnem).getInt(null));
			}
		} catch (Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
		// button.setSize(button.getIcon().getIconWidth(), button.getHeight());
		// int s = 0;
		// button.setMargin(new Insets(s, 2, s, 2));

		return button;
	}

	//~ Inner Classes ==========================================================

	/**
	 * Listener for the internal frames.
	 */
	class GraffitiFrameListener extends InternalFrameAdapter implements WindowListener {

		MainFrame mainFrame;

		public GraffitiFrameListener(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}

		/**
		 * @see javax.swing.event.InternalFrameListener#internalFrameActivated(InternalFrameEvent)
		 */
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);

			GraffitiInternalFrame iframe = (GraffitiInternalFrame) e.getInternalFrame();
			graffitiFrameActivated(iframe.getSession(), iframe.getView());
		}

		/**
		 * @see javax.swing.event.InternalFrameListener#internalFrameClosed(InternalFrameEvent)
		 */
		public void internalFrameClosed(InternalFrameEvent e) {
			GraffitiInternalFrame f = (GraffitiInternalFrame) e.getInternalFrame();

			EditorSession session = ((GraffitiInternalFrame) e.getInternalFrame())
						.getSession();

			if (session.getViews().size() >= 2) {
				activeFrames.remove(f);
			}
			frameClosing(session, f.getView());

			setTitle(GraffitiInternalFrame.startTitle);
			
			super.internalFrameClosed(e);
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.InternalFrameListener#internalFrameClosing(javax.swing.event.InternalFrameEvent)
		 */
		public void internalFrameClosing(InternalFrameEvent e) {
			mainFrame.updateActions();
		}

		public void windowActivated(WindowEvent e) {
			GraffitiFrame iframe = (GraffitiFrame) e.getWindow();
			graffitiFrameActivated(iframe.getSession(), iframe.getView());
		}

		private void graffitiFrameActivated(EditorSession session, View view) {
			if (session != activeSession) {
				fireSessionChanged(session);
				fireViewChanged(view);
				fireSelectionChanged(session);
			} else {
				fireViewChanged(view);
			}
		}

		public void windowClosed(WindowEvent e) {
			GraffitiFrame f = (GraffitiFrame) e.getWindow();
			EditorSession session = f.getSession();

			if (session.getViews().size() >= 2) {
				detachedFrames.remove(f);
			}
			frameClosing(session, f.getView());

			setTitle(GraffitiInternalFrame.startTitle);
		}

		public void windowClosing(WindowEvent e) {
			mainFrame.updateActions();
		}

		public void windowDeactivated(WindowEvent e) { }
		public void windowDeiconified(WindowEvent e) { }
		public void windowIconified(WindowEvent e) { }
		public void windowOpened(WindowEvent e) { }
	}

	public void closeGravisto() {
		ArrayList<Graph> unsavedGraphs = new ArrayList<Graph>();
		List<Session> l = new LinkedList<Session>();
		for (Iterator i = getSessionsIterator(); i.hasNext();) {
			Session s = (Session) i.next();
			if (s.getGraph().isModified())
				if (!unsavedGraphs.contains(s.getGraph()))
						unsavedGraphs.add(s.getGraph());
			l.add(s);
		}
		if (unsavedGraphs.size()>0) {
			String names = "";
			for (Graph g : unsavedGraphs)
				names+="<li>"+g.getName()+(unsavedGraphs.indexOf(g)<unsavedGraphs.size()-1 ? "<br>":"");
			int res = JOptionPane.showConfirmDialog(this,
					"<html><b>Do you really want to close the application?</b><p><p>"+
					"The following graph(s) have not been saved, yet:<br><ol>"+
					names, 
					unsavedGraphs.size()+" graph(s) not saved",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (res == JOptionPane.YES_OPTION) {
				/*for (Iterator it = l.iterator(); it.hasNext();) {
					removeSession((Session) it.next());
				}
				savePreferences();*/
				System.exit(0);
			}
		} else
			System.exit(0);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#processEvent(java.awt.AWTEvent)
	 */
	protected void processEvent(AWTEvent e) {
		super.processEvent(e);
		if (e.getID() == 201) closeGravisto();
	}

	/**
	 * Adds a JComponent to a JFrame which is shown in the Desktop
	 * @param component
	 */
	public void addFrame(JComponent component, String title) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(700, 500));
		panel.add(component, BorderLayout.CENTER);

		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * @param panel A status panel that will be shown in the progess area.
	 * A timer calls isVisible to this panel. If it is not visible any more,
	 * it will be removed from the status area.
	 */
	public void addStatusPanel(JPanel panel) {
		if (jSplitPane_pluginPanelAndProgressView != null
					&& progressPanel != null) {
			if (activeProgressPanels == null)
				activeProgressPanels = new ArrayList<JPanel>();

			// create and install status check for the panels
			if (timerCheckActiveProgressPanels == null) initProgressGuiTimer();

			synchronized(activeProgressPanels) {
				if (panel!=null)
					activeProgressPanels.add(panel);
			}
		}
	}
	
	public List<JPanel> getStatusPanels() {
		ArrayList<JPanel> result = new ArrayList<JPanel>();	
		synchronized(activeProgressPanels) {
			if (activeProgressPanels!=null)
				result.addAll(activeProgressPanels);
		}
		return result;
	}


	private boolean firstGuiTimerCall = true;
	private long redrawGUIcount = 0;
	
	private void initProgressGuiTimer() {
		timerCheckActiveProgressPanels = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				redrawGUIcount++;
				ArrayList<JPanel> toBeDeleted = new ArrayList<JPanel>();
				boolean foundSomething = false;
				synchronized(activeProgressPanels) {
					for (JPanel jp : activeProgressPanels) {
						if (!jp.isVisible()) {
							toBeDeleted.add(jp);
							foundSomething = true;
						}
					}
					if (foundSomething) activeProgressPanels.removeAll(toBeDeleted);
	
					if (firstGuiTimerCall
							|| (redrawGUIcount % 10 == 0)
							|| (progressPanel.getComponentCount() != activeProgressPanels.size())) {
						if (jSplitPane_pluginPanelAndProgressView.getHeight()>0)
							firstGuiTimerCall = false;
						updatePanelGUI();
					}
				}
			}
		});
		timerCheckActiveProgressPanels.start();
	}

	private void updatePanelGUI() {
		synchronized(activeProgressPanels) {
//			int height = 0;
//			for (JPanel jp : activeProgressPanels) {
//				height += jp.getPreferredSize().height; // ??? -15 ????
//			}
//			jSplitPane_pluginPanelAndProgressView
//						.setDividerLocation(jSplitPane_pluginPanelAndProgressView
//									.getHeight()
//									- height);
			
			progressPanel.removeAll();
			// progressPanel.validate();
			double border = 0;
			double[][] size = new double[2][];
			size[0] = new double[] { border, TableLayoutConstants.FILL, border }; // Columns
			size[1] = new double[activeProgressPanels.size() + 2];
			size[1][0] = border;
			int i;
			for (i = 1; i < activeProgressPanels.size() + 2 - 1; i++) {
				size[1][i] = TableLayoutConstants.PREFERRED;
			}
			size[1][i] = border;
			progressPanel.setLayout(new TableLayout(size));
			for (i = 0; i < activeProgressPanels.size(); i++) {
				progressPanel.add(activeProgressPanels.get(i), "1,"
							+ (activeProgressPanels.size() - i));
				((Component) activeProgressPanels.get(i)).setVisible(true);
			}
			// progressPanel.validate();
			jSplitPane_pluginPanelAndProgressView.validate();
		}
	}

	/* (non-Javadoc)
	 * @see org.graffiti.selection.SelectionListener#selectionChanged(org.graffiti.selection.SelectionEvent)
	 */
	public void selectionChanged(SelectionEvent e) {
		if (SwingUtilities.isEventDispatchThread()) {
			repaint(100);
			GraffitiAction.updateAllActions();
			boolean oneModified = false;
			for (GraffitiInternalFrame frame : getActiveFrames()) {
				frame.setTitle(frame.getSession().getGraph().getName());
				boolean mod = frame.getSession().getGraph().isModified();
				if (frame.getBorder()!=null)
					frame.putClientProperty("windowModified", mod);
				if (mod)
					oneModified = true;
			}
			getRootPane().putClientProperty("windowModified", oneModified);
			for (GraffitiFrame gf : getDetachedFrames()) {
				gf.setTitle(gf.getSession().getGraph().getName());
			}
		} else
			repaint(100);
	}

	/* (non-Javadoc)
	 * @see org.graffiti.selection.SelectionListener#selectionListChanged(org.graffiti.selection.SelectionEvent)
	 */
	public void selectionListChanged(SelectionEvent e) {
		if (SwingUtilities.isEventDispatchThread()) {
			GraffitiAction.updateAllActions();
		}
	}

	ArrayList<GraffitiFrame> detachedFrames = new ArrayList<GraffitiFrame>();
	
	
//	public JSplitPane getAttributePanel() {
//		return jSplitPane_pluginPanelAndProgressView;
//	}
	
	public GraffitiFrame[] getDetachedFrames() {
		return detachedFrames.toArray(new GraffitiFrame[] {});
	}

	public void addDetachedFrame(GraffitiFrame frame) {
		if (!detachedFrames.contains(frame))
			detachedFrames.add(frame);		
	}

	public void removeDetachedFrame(GraffitiFrame frame) {
		detachedFrames.remove(frame);
	}

	public GraffitiFrame getActiveDetachedFrame() {
		EditorSession es = getActiveEditorSession();
		for (GraffitiFrame gf : getDetachedFrames()) {
			if (gf.getSession()==es) {
				return gf;
			}
		}
		return null;
	}

	public static Rectangle getRelativeCenterPosition(JDialog md) {
		Rectangle r = MainFrame.getInstance().getBounds();
		int w = md.getWidth();
		int h = md.getHeight();
		int x = r.x+r.width/2-w/2;
		int y = r.y+r.height/2-h/2;
		return new Rectangle(x,y,w,h);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		System.out.println("Drag-Enter");
		showMessage("Drag-Enter", MessageType.INFO);
	}

	public void dragOver(DropTargetDragEvent dtde) {
		System.out.println("Drag-Over");
		showMessage("Drag-Over", MessageType.INFO);
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		System.out.println("Drag-Changed");
		showMessage("Drag-Changed", MessageType.INFO);
	}

	public void dragExit(DropTargetEvent dte) {
		System.out.println("Drag-Exit");
		showMessage("Drag-Exit", MessageType.INFO);
	}

	public void drop(DropTargetDropEvent e) {
		showMessage("Drop", MessageType.INFO);
		System.out.println("Drop");
		e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        
        String s0=null;
        if (e.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        	try {
				s0 = (String) e.getTransferable().getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException e1) {
				ErrorMsg.addErrorMessage(e1);
			} catch (IOException e1) {
				ErrorMsg.addErrorMessage(e1);
			}
			s0=ErrorMsg.stringReplace(s0, ""+"\n", ""+"\r");
        }
        final String s=s0;
        
        File f = new File(s);
        if (f.exists() && f.canRead()) {
      	  loadGraph(f);
        }
        
        Object data0=null;
        if (e.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
        {
	        try {
	            data0 = e.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
	        } catch (UnsupportedFlavorException e1) {
	        	ErrorMsg.addErrorMessage(e1);
	        } catch (IOException e1) {
	      	  ErrorMsg.addErrorMessage(e1);
	        }
        }
        Object data=data0;
        
        if (data!=null)
            for (int i = 0; i < ((java.util.List) data).size(); i++)
            {
                final File file = (File) ((java.util.List) data).get(i);
				
                if (file.isDirectory())
			       	MainFrame.showMessageDialog("Drag & Drop is only supported for files, not folders!", "Error");
                else {
				      if (file.exists() && file.canRead()) 
				      	loadGraph(file);
                }
            }
	}

	public static void showMessageDialog(String title, JComponent comp) {
		comp.setPreferredSize(new Dimension(640, 480));
		JOptionPane.showMessageDialog(MainFrame.getInstance(), comp, title,
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showMessageDialogPlain(String title, JComponent comp) {
		JOptionPane.showMessageDialog(MainFrame.getInstance(), comp, title,
				JOptionPane.PLAIN_MESSAGE);
	}

	public static void showMessageWindow(String title, JComponent jc) {
		JFrame jf = new JFrame(title);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationByPlatform(true);
		jf.setSize(480, 480);
		jf.setLayout(TableLayout.getLayout(TableLayout.FILL, TableLayout.FILL));
		jf.add(jc, "0,0");
		jf.validate();
		jf.pack();
		jf.setVisible(true);
	}

	public boolean isTaskPanelVisible(String string) {
		synchronized(activeProgressPanels) {
			for (JPanel jp : activeProgressPanels) {
				String title = (String)jp.getClientProperty("title");
				if (title!=null && title.toUpperCase().contains(string.toUpperCase()))
					return true;
			}
		}
		return false;
	}

	public boolean lookUpAndSwitchToNamedSession(String fileName) {
		if (fileName.indexOf("/")>0) {
			fileName = fileName.substring(fileName.lastIndexOf("/")+"/".length());
		}
		Set<EditorSession> validSessions = new HashSet<EditorSession>();
		for (EditorSession es : getEditorSessions()) {
			if (es.getFileName()!=null && (es.getFileName().toString().endsWith(fileName) ||
				es.getFileName().toString().replaceAll("%20", " ").endsWith(fileName)))
				validSessions.add(es);
		}
		if (validSessions.size()>=1) {
			EditorSession es = validSessions.iterator().next();
			 for (GraffitiInternalFrame f : getActiveFrames()) {
	        	 if (f.getSession()==es) {
	        		 desktop.getDesktopManager().deiconifyFrame(f);
	        		 desktop.getDesktopManager().activateFrame(f);
	        		 MainFrame.showMessage("Existing view for graph file "+fileName+" has been activated", MessageType.INFO);
	        		 return true;
	        	 }
	         }
		}
		return false;
	}

	public URLattributeActionManager getActionManager() {
		return urlAttributeActionManager;
	}

	public void frameClosing(EditorSession session, View view) {
		// remove this view only if there are other open views
		if (session.getViews().size() >= 2) {
			// System.out.println("CLOSE VIEW");
			view.close();
			session.removeView(view);
			viewFrameMapper.remove(view);
			zoomListeners.remove(view);
		} else {
			// System.out.println("CLOSE SESSION");
			// remove the session if we are closing the last view
			view.close();

			MainFrame.getInstance().removeSession(session);

			fireSessionChanged(null);
			activeSession = null;
		}
		// the update mechanism after the close of the last session
		// still has some bugs...
		updateActions();
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		
		try {
			Runtime r = Runtime.getRuntime();
			if (r.maxMemory()/1024/1024<400) {
	            int divisor=1024;
	            String memoryConfig = "Used/free/max memory: " + 
	            	((r.totalMemory()/divisor/divisor)-(r.freeMemory()/divisor/divisor)) +""+ 
				  		"/" + (r.freeMemory()/divisor/divisor) +"/<u>"+(r.maxMemory()/divisor/divisor)+"</u> MB &lt;-- possible problem detected";
	            MainFrame.showMessageDialog("<html>" +
	            		"Low memory configuration detected!<br><br>" +
	            		"The current memory configuration (see bottom of this dialog window)<br>" +
	            		"may cause severe performance problems and yield to unrecoverable<br>" +
	            		"out of memory exceptions and thus to unexpected program failures.<br>" +
	            		"Please check developer information on how to modify Java memory<br>" +
	            		"configuration. If you are not a software developer, please inform the<br>" +
	            		"main developer of the VANTED system about this problem.<br>" +
	            		"This message should not appear in case the program is started using<br>" +
	            		"the provided launch configurations (Java WebStart or command line<br>" +
	            		"based launch scripts).<br><br>" +
	            		"Please close the application and fix this problem before proceeding.<br><br>" +
	            		memoryConfig, ReleaseInfo.getRunningReleaseStatus()+" Information");
	        }
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
		try {
			if (b)
				fireSessionChanged(activeSession);
		} catch(Exception e) {
			ErrorMsg.addErrorMessage(e);
		}
	}
	
	public View createExternalFrame(String viewClassName, EditorSession session,
			boolean otherViewWillBeClosed) {
		GraffitiInternalFrame gif = (GraffitiInternalFrame) createInternalFrame(viewClassName,
				session.getGraph().getName(), session, false, true, otherViewWillBeClosed);
		GraffitiFrame gf = new GraffitiFrame(gif);
		gf.addWindowListener(graffitiFrameListener);
		gf.setVisible(true);
		MainFrame.getInstance().addDetachedFrame(gf);
		return gif.getView();
	}

	public View createInternalFrame(String viewClassName, EditorSession session, boolean otherViewWillBeClosed) {
		createInternalFrame(viewClassName, session.getGraph().getName(), session, false, false, otherViewWillBeClosed);
		return activeSession.getActiveView();
	}

	public void showAndHighlightSidePanelTab(String title, boolean cycle) {
		boolean found = false;
		for (InspectorTab it : getInspectorPlugin().getTabs()) {
			if (it.getTitle().equals(title)) {
				it.focusAndHighlight(null, true, cycle);
				found = true;
			} else {
				if (it instanceof SubtabHostTab) {
					SubtabHostTab sh = (SubtabHostTab)it;
					for (InspectorTab it2 : sh.getTabs()) {
						if (it2.getTitle().equals(title)) {
							it.focusAndHighlight(it2, true, cycle);
							found = true;
						}
					}
				} else {
					if (it instanceof ContainsTabbedPane) {
						ContainsTabbedPane sh = (ContainsTabbedPane)it;
						for (int idx = 0; idx<sh.getTabbedPane().getTabCount(); idx++) {
							String t = sh.getTabbedPane().getTitleAt(idx);
							if (t.equals(title)) {
								JComponent c = (JComponent)sh.getTabbedPane().getComponentAt(idx);
								InspectorTab.focusAndHighlightComponent(c, title, 
										null, true, cycle);
								found = true;
							}
						}
					} 
				}
			}
		}
		if (!found)
			System.err.println("Internal Error: side panel "+title+" not found!");
	}

	public void setSidePanel(JToolBar component, int width) {
		vertSplitter.setRightComponent(component);
		vertSplitter.validate();
		vertSplitter.setDividerLocation(vertSplitter.getWidth()-width); // uiPrefs.getInt("vertSplitter", VERT_SPLITTER));
	}
}


//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
