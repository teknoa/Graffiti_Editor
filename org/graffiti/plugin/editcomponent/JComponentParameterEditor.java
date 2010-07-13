package org.graffiti.plugin.editcomponent;

import java.util.Collection;

import javax.swing.JComponent;

import org.BackgroundTaskStatusProviderSupportingExternalCall;
import org.graffiti.event.AttributeEvent;
import org.graffiti.event.EdgeEvent;
import org.graffiti.event.GraphEvent;
import org.graffiti.event.NodeEvent;
import org.graffiti.event.TransactionEvent;
import org.graffiti.plugin.Displayable;

public class JComponentParameterEditor implements ValueEditComponent {

	private Displayable disp;

	public JComponentParameterEditor(Displayable disp) {
		this.disp = disp;
	}
	
	public JComponent getComponent() {
		return (JComponent) disp.getValue();
	}

	public Displayable getDisplayable() {
		return disp;
	}

	public boolean getShowEmpty() {
		return false;
	}

	public boolean isEnabled() {
		return true;
	}

	public void setDisplayable(Displayable disp) {
		this.disp = disp;
	}

	public void setEditFieldValue() {
		// empty
	}

	public void setEnabled(boolean enabled) {
		// empty
	}

	public void setShowEmpty(boolean showEmpty) {
		// empty
	}

	public void setValue() {
		// empty
	}

	public void postAttributeAdded(AttributeEvent e) {
		// empty
	}

	public void postAttributeChanged(AttributeEvent e) {
		// empty
	}

	public void postAttributeRemoved(AttributeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preAttributeAdded(AttributeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preAttributeChanged(AttributeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preAttributeRemoved(AttributeEvent e) {
		// TODO Auto-generated method stub

	}

	public void transactionFinished(TransactionEvent e, BackgroundTaskStatusProviderSupportingExternalCall status) {
		// TODO Auto-generated method stub

	}

	public void transactionStarted(TransactionEvent e) {
		// TODO Auto-generated method stub

	}

	public void postDirectedChanged(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postEdgeReversed(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postSourceNodeChanged(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postTargetNodeChanged(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preDirectedChanged(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preEdgeReversed(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preSourceNodeChanged(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preTargetNodeChanged(EdgeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postEdgeAdded(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void postEdgeRemoved(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void postGraphCleared(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void postNodeAdded(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void postNodeRemoved(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void preEdgeAdded(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void preEdgeRemoved(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void preGraphCleared(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void preNodeAdded(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void preNodeRemoved(GraphEvent e) {
		// TODO Auto-generated method stub

	}

	public void postInEdgeAdded(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postInEdgeRemoved(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postOutEdgeAdded(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postOutEdgeRemoved(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postUndirectedEdgeAdded(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void postUndirectedEdgeRemoved(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preInEdgeAdded(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preInEdgeRemoved(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preOutEdgeAdded(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preOutEdgeRemoved(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preUndirectedEdgeAdded(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void preUndirectedEdgeRemoved(NodeEvent e) {
		// TODO Auto-generated method stub

	}

	public void setValue(Collection<Displayable> attributes) {
		for (Displayable d : attributes) {
			setDisplayable(d);
			setValue();
		}
	}

	@Override
	public void setParameter(String setting, Object value) 
	{
	}
}
