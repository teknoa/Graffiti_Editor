package org.graffiti.editor;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.JMenuItem;

public class RecentEntry extends JMenuItem {

	public RecentEntry(String data, boolean visible) {
		super();
		if(!data.equalsIgnoreCase("")) {
			setAction(generateNewAction(new File(data)));
			setText(data.substring(data.lastIndexOf(File.separator)+1));
			setToolTipText(data);
			setVisible(visible);
		} else setVisible(false);
	}
	
	public RecentEntry(File data, boolean visible) {
		super();
		setAction(generateNewAction(data));
		setText(data.getName());
		setToolTipText(data.getAbsolutePath());
		setVisible(visible);
	}
	
	public RecentEntry(RecentEntry from) {
		super();
		setNewData(from);
	}
	
	public void setNewData(RecentEntry from) {
		setAction(from.getAction());
		setText(from.getText());
		setToolTipText(from.getToolTipText());
		setVisible(from.isVisible());
	}
	
	private Action generateNewAction(final File file) {
		return new Action() {
			public void actionPerformed(ActionEvent e) {
				try {
						MainFrame.getInstance().loadGraphInBackground(file, null, false);
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					}
			}
			public void setEnabled(boolean b) {}
			public void removePropertyChangeListener(PropertyChangeListener listener) {}
			public void putValue(String key, Object value) {}
			public boolean isEnabled() {return true;}
			public Object getValue(String key) {return null;}
			public void addPropertyChangeListener(PropertyChangeListener listener) {}
		};
	}
	
}
