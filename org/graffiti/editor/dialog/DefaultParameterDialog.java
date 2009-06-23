//==============================================================================
//
//   DefaultParameterDialog.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: DefaultParameterDialog.java,v 1.5 2009/06/23 07:14:49 klukas Exp $

package org.graffiti.editor.dialog;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.ErrorMsg;
import org.graffiti.core.ImageBundle;
import org.graffiti.core.StringBundle;
import org.graffiti.editor.MainFrame;
import org.graffiti.editor.MessageType;
import org.graffiti.graph.Edge;
import org.graffiti.graph.Node;
import org.graffiti.managers.EditComponentManager;
import org.graffiti.plugin.parameter.BooleanParameter;
import org.graffiti.plugin.parameter.ColorParameter;
import org.graffiti.plugin.parameter.DoubleParameter;
import org.graffiti.plugin.parameter.EdgeParameter;
import org.graffiti.plugin.parameter.IntegerParameter;
import org.graffiti.plugin.parameter.JComponentParameter;
import org.graffiti.plugin.parameter.NodeParameter;
import org.graffiti.plugin.parameter.ObjectListParameter;
import org.graffiti.plugin.parameter.Parameter;
import org.graffiti.plugin.parameter.StringParameter;
import org.graffiti.selection.Selection;

/**
 * The default implementation of a parameter dialog.
 *
 * @version $Revision: 1.5 $
 */
public class DefaultParameterDialog extends AbstractParameterDialog implements
		ActionListener, WindowListener {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	//~ Instance fields ========================================================

	/** The <code>ImageBundle</code> of the view type chooser. */
	protected ImageBundle iBundle = ImageBundle.getInstance();

	/** The panel used to display and change parameter values. */
	protected ParameterEditPanel paramEditPanel;

	/** The <code>StringBundle</code> of the view type chooser. */
	protected StringBundle sBundle = StringBundle.getInstance();

	/** The list of parameters, the user is editing. */
	protected Parameter[] params;

	/** The value edit component manager, the edit panel needs. */
	private EditComponentManager editComponentManager;

	/** The dialog's buttons. */
	private JButton cancel;

	/** The dialog's buttons. */
	private JButton ok;

	/** The panel, which contains the parameters. */
	private JPanel paramsPanel;

	/** <code>true</code>, if the user selected the ok button in this dialog. */
	private boolean selectedOk = false;

	private String algorithmName;

	//~ Constructors ===========================================================

	/**
	 * Constructor for DefaultParameterDialog.
	 *
	 * @param editComponentManager DOCUMENT ME!
	 * @param parent the parent of this dialog.
	 * @param parameters the array of parameters to edit in this dialog.
	 * @param selection DOCUMENT ME!
	 * @param algorithmName the name of the algorithm, to edit the parameters
	 *        for.
	 */
	public DefaultParameterDialog(EditComponentManager editComponentManager,
			MainFrame parent, Parameter[] parameters, Selection selection,
			String algorithmName, Object description) {
		this(editComponentManager, parent, parameters, selection, algorithmName, description, null);
	}
	
	public DefaultParameterDialog(EditComponentManager editComponentManager,
			MainFrame parent, Parameter[] parameters, Selection selection,
			String algorithmName, Object description, boolean okOnly, boolean noButton) {
		this(editComponentManager, parent, parameters, selection, algorithmName, description, null, okOnly, noButton);
	}
	public DefaultParameterDialog(EditComponentManager editComponentManager,
			MainFrame parent, Parameter[] parameters, Selection selection,
			String algorithmName, Object descriptionOrComponent, JComponent descComponent) {
		this(editComponentManager, parent, parameters, selection, algorithmName, descriptionOrComponent, descComponent, false, false);
	}	
	public DefaultParameterDialog(EditComponentManager editComponentManager,
			MainFrame parent, Parameter[] parameters, Selection selection,
			String algorithmName, Object descriptionOrComponent, JComponent descComponent, boolean okOnly, boolean noButton) {
		super(parent, true);
		String description = "";
		if (descriptionOrComponent instanceof String)
			description = (String) descriptionOrComponent;
		else
			descComponent = (JComponent) descriptionOrComponent;
		this.algorithmName = algorithmName;

		this.editComponentManager = editComponentManager;

		this.params = parameters;

		getContentPane().setLayout(new BorderLayout());

		// setTitle("Set algorithm parameters");
		setTitle(algorithmName);
		
		setSize(420, 320);
		setResizable(true);//false
		setLocationRelativeTo(parent);

		ok = new JButton(sBundle.getString("run.dialog.button.run"));
		cancel = new JButton(sBundle.getString("run.dialog.button.cancel"));

//		JPanel buttonsPanel = new JPanel();
//
//		buttonsPanel.add(ok);
//		buttonsPanel.add(cancel);
		
		paramsPanel = createValueEditContainer(params, selection, 
				(description!=null && description.length()>0) ? description : "", algorithmName, descComponent); // sBundle.getString("run.dialog.desc")
		
		// algorithmName + " parameters"

		ok.setEnabled(true);

		getRootPane().setDefaultButton(ok);

		defineLayout(okOnly, noButton);
		addListeners();

		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	//~ Methods ================================================================

	/**
	 * @see org.graffiti.editor.dialog.ParameterDialog#getEditedParameters()
	 */
	public Parameter[] getEditedParameters() {
		return this.paramEditPanel.getUpdatedParameters();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isOkSelected() {
		return selectedOk;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == cancel) {
			MainFrame.showMessage(algorithmName + " not started",
					MessageType.INFO, 3000);
			dispose();
		} else if (src == ok) {
			okSelected();
		}
	}

	/**
	 * @see java.awt.event.WindowListener#windowActivated(WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0) {
	}

	/**
	 * @see java.awt.event.WindowListener#windowClosed(WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0) {
	}

	/**
	 * @see java.awt.event.WindowListener#windowClosing(WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0) {
		dispose();
	}

	/**
	 * @see java.awt.event.WindowListener#windowDeactivated(WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0) {
	}

	/**
	 * @see java.awt.event.WindowListener#windowDeiconified(WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0) {
	}

	/**
	 * @see java.awt.event.WindowListener#windowIconified(WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0) {
	}

	/**
	 * @see java.awt.event.WindowListener#windowOpened(WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0) {
	}

	/**
	 * Adds the listeners to the dialog.
	 */
	private void addListeners() {
		cancel.addActionListener(this);
		ok.addActionListener(this);
		addWindowListener(this);
	}

	/**
	 * Creates and returns a value edit container for the given parameters.
	 *
	 * @param parameters the list of parameters, the user wants to edit.
	 * @param selection DOCUMENT ME!
	 * @param descComponent 
	 *
	 * @return DOCUMENT ME!
	 */
	private JPanel createValueEditContainer(Parameter[] parameters,
			Selection selection, String title, String heading, JComponent descComponent) {
		this.paramEditPanel = new ParameterEditPanel(parameters,
				editComponentManager.getEditComponents(), selection, title, true, heading, descComponent);

		return this.paramEditPanel;
	}

	/**
	 * Defines the layout of this dialog.
	 */
	private void defineLayout(boolean okOnly, boolean noButton) {
		double border = 8d;
		double[][] size = {
			new double[] { border, TableLayoutConstants.FILL, border },
			new double[] { border, TableLayoutConstants.FILL, border, TableLayoutConstants.PREFERRED, border },
		};
		getContentPane().setLayout(new TableLayout(size));

//		paramsPanel.setBorder(BorderFactory.createEtchedBorder());
//		
		getContentPane().add(paramsPanel, "1,1");
		if (!noButton)
		getContentPane().add(
			TableLayout.get3Split(
				ok, new JLabel(), okOnly ? null:cancel, TableLayoutConstants.PREFERRED, border, TableLayoutConstants.PREFERRED),
				"1,3"
			);
	}

	/**
	 * DOCUMENT ME!
	 */
	private void okSelected() {
		selectedOk = true;
		dispose();
	}
	
	
	public static Object[] getInput(Object description, String title,
			Object... parameters) {
		
		title = ErrorMsg.removeHTMLtags(title);
		
		// Buttons: OK => close and return input values
		// Cancel => close and return null
		// Reset => set to initial values
		Parameter[] p = new Parameter[parameters.length / 2];
		for (int i=0; i<p.length; i++) {
			Object desc = parameters[i*2];
			Object param = parameters[i*2+1];
			if (param instanceof JComponent) {
				JComponent val = (JComponent) param;
				String name = (String) desc;
				JComponentParameter sp = new JComponentParameter(val, name, name);
				p[i] = sp;
			} else
			if (param instanceof String) {
				String val = (String) param;
				String name = (String) desc;
				StringParameter sp = new StringParameter(val, name, name);
				p[i] = sp;
			} else
			if (param instanceof Double) {
				Double val = (Double) param;
				String name = (String) desc;
				DoubleParameter dp = new DoubleParameter(val, name, name);
				p[i] = dp;
			} else
			if (param instanceof Node) {
					Node val = (Node) param;
					String name = (String) desc;
					NodeParameter ip = new NodeParameter(val.getGraph(), val, name, name);
					p[i] = ip;
			} else
			if (param instanceof Edge) {
					Edge val = (Edge) param;
					String name = (String) desc;
					EdgeParameter ip = new EdgeParameter(val, name, name);
					p[i] = ip;
			} else	
			if (param instanceof Integer) {
				Integer val = (Integer) param;
				String name = (String) desc;
				IntegerParameter ip = new IntegerParameter(val, name, name);
				p[i] = ip;
			} else
			if (param instanceof List) {
				List val = (List) param;
				String name = (String) desc;
				ObjectListParameter ip = new ObjectListParameter(val.size()>0 ? val.get(0) : null, name, name, val);
				p[i] = ip;
			} else	
			if (param instanceof Set) {
				Set val = (Set) param;
				String name = (String) desc;
				ObjectListParameter ip = new ObjectListParameter(val.size()>0 ? val.iterator().next() : null, name, name, val);
				p[i] = ip;
			} else	
			if (param instanceof Color) {
				Color c = (Color)param;
				String name = (String) desc;
				ColorParameter sp = new ColorParameter(c, name, name);
				p[i] = sp;
			} else
			if (param instanceof Boolean) {
				Boolean val = (Boolean) param;
				String name = (String) desc;
				BooleanParameter ip = new BooleanParameter(val, name, name);
				p[i] = ip;
			}
		}
		boolean okOnly = (description!=null && description instanceof String && ((String)description).startsWith("[OK]"));
		if (okOnly) {
			description = ((String)description).substring("[OK]".length());
			if (((String)description).length()<=0)
				description = null;
		}
		boolean noButton = (description!=null && description instanceof String && ((String)description).startsWith("[]"));
		if (noButton) {
			description = ((String)description).substring("[]".length());
			if (((String)description).length()<=0)
				description = null;
		}
		if (description!=null && description instanceof String) {
			if (((String)description).indexOf("<")>=0
					&& ((String)description).indexOf(">")>=0
					&& !((String)description).toUpperCase().startsWith("<HTML>")) 
					description = "<html>"+(String)description;
		}
		DefaultParameterDialog paramDialog = new DefaultParameterDialog(
					MainFrame.getInstance().getEditComponentManager(), 
					MainFrame.getInstance(), 
					p,
					( MainFrame.getInstance().getActiveEditorSession() != null ?
							MainFrame.getInstance().getActiveEditorSession().
								getSelectionModel().getActiveSelection() : null
					), 
					title, description, okOnly, noButton);
		if (paramDialog.isOkSelected()) {
			Parameter[] pe = paramDialog.getEditedParameters();
			Object[] result = new Object[pe.length];
			for (int i=0; i<result.length; i++) {
				if (pe[i]!=null)
					result[i] = pe[i].getValue();
			}
			return result;
		} else
			return null;
	}
	
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
