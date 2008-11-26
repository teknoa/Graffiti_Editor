package org.graffiti.plugin.inspector;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.graffiti.event.AttributeEvent;
import org.graffiti.event.AttributeListener;
import org.graffiti.event.TransactionEvent;
import org.graffiti.graph.GraphElement;
import org.graffiti.plugin.view.View;
import org.graffiti.plugin.view.ViewListener;
import org.graffiti.selection.SelectionEvent;
import org.graffiti.selection.SelectionListener;
import org.graffiti.session.Session;
import org.graffiti.session.SessionListener;

/**
 * @author klukas
 */
public class SubtabHostTab extends InspectorTab
	implements SessionListener, ViewListener, ContainsTabbedPane, SelectionListener, AttributeListener {
	private static final long serialVersionUID = -3810951162912767447L;
	
	private Collection<InspectorTab> subtabs;

	JTabbedPane hc = new JTabbedPane();
	
	private LinkedHashSet<InspectorTab> hiddenTabs = new LinkedHashSet<InspectorTab>();


	public SubtabHostTab(String title, Collection<InspectorTab> subtabs) {
		this.title = title;
		this.subtabs = subtabs;
		initComponents();
	}

	public SubtabHostTab(String title, InspectorTab[] inspectorTabs) {
		Collection<InspectorTab> tabs = new ArrayList<InspectorTab>();
		for (InspectorTab it : inspectorTabs)
			tabs.add(it);
		this.title = title;
		this.subtabs = tabs;
		initComponents();
	}

	private void initComponents() {
		double[][] sizeM = { { TableLayoutConstants.FILL }, // Columns
				{ TableLayoutConstants.FILL } }; // Rows
		
		setLayout(new TableLayout(sizeM));
		setBackground(null);
		setOpaque(false);
		

		for (InspectorTab tab : subtabs) {
			hc.addTab(tab.getTitle(), tab);
		}
		hc.validate();
		add(hc, "0,0");

		validate();
	}

	@Override
	public boolean visibleForView(View v) {
		boolean visible = false;
		for (InspectorTab tab : subtabs)
			visible = visible || tab.visibleForView(v); 
		return visible;
	}

	public void sessionChanged(Session s) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof SessionListener) {
				SessionListener sl = (SessionListener) tab;
				sl.sessionChanged(s);
			}
		}
	}
	
	public Collection<InspectorTab> getTabs() {
		return subtabs;
	}

	public void sessionDataChanged(Session s) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof SessionListener) {
				SessionListener sl = (SessionListener) tab;
				sl.sessionDataChanged(s);
			}
		}
	}

	public synchronized void viewChanged(View v) {
		for (InspectorTab tab : subtabs) {
			if (!tab.visibleForView(v)) {
				int idx = hc.indexOfTab(tab.getName());
				if (idx>=0)
					hc.removeTabAt(idx);;
				hiddenTabs.add(tab);
			} else {
				if (hiddenTabs.contains(tab))
					hc.addTab(tab.getTitle(), tab);
			}
		}
		hc.validate();
	}

	public JTabbedPane getTabbedPane() {
		return hc;
	}

	public void selectionChanged(SelectionEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof SelectionListener) {
				SelectionListener sl = (SelectionListener)tab;
				sl.selectionChanged(e);
			}
		}
	}

	public void selectionListChanged(SelectionEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof SelectionListener) {
				SelectionListener sl = (SelectionListener)tab;
				sl.selectionListChanged(e);
			}
		}
	}

	public void postAttributeAdded(AttributeEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.postAttributeAdded(e);
			}
		}
	}

	public void postAttributeChanged(AttributeEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.postAttributeChanged(e);
			}
		}
	}

	public void postAttributeRemoved(AttributeEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.postAttributeRemoved(e);
			}
		}
	}

	public void preAttributeAdded(AttributeEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.preAttributeAdded(e);
			}
		}
	}

	public void preAttributeChanged(AttributeEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.preAttributeChanged(e);
			}
		}
	}

	public void preAttributeRemoved(AttributeEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.preAttributeRemoved(e);
			}
		}
	}

	public void transactionFinished(TransactionEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.transactionFinished(e);
			}
		}
	}

	public void transactionStarted(TransactionEvent e) {
		for (InspectorTab tab : subtabs) {
			if (tab instanceof AttributeListener) {
				AttributeListener l = (AttributeListener)tab;
				l.transactionStarted(e);
			}
		}
	}
	
	public void setEditPanelInformation(
			Map valueEditComponents,
			Map<GraphElement, GraphElement> map) {
		for (InspectorTab tab : subtabs) {
			if (tab.getEditPanel() != null) {
				tab.getEditPanel().setEditComponentMap(valueEditComponents);
				tab.getEditPanel().setGraphElementMap(map);
			}
		}
	}
}