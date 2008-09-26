package org;

import javax.swing.JButton;
import javax.swing.UIManager;

import org.AttributeHelper;
import org.ErrorMsg;

/**
 * @author klukas
 */
public class JMButton extends JButton {
	private static final long serialVersionUID = 4853578108818002186L;

	private static boolean nativeLookAndFeelActive = UIManager.getLookAndFeel().isNativeLookAndFeel();
	
	public JMButton(String text) {
		super(text);
		mySetText(text);
	}

	private void mySetText(String text) {
		if (ErrorMsg.isMac() && nativeLookAndFeelActive) {
			if (text!=null && (text.contains("<br>") || text.contains("<small>"))) {
				text = ErrorMsg.stringReplace(text, "<br>", " ");
				text = ErrorMsg.stringReplace(text, "  ", " ");
				text = ErrorMsg.removeHTMLtags(text);
				super.setText(text);
				putClientProperty("JComponent.sizeVariant", "mini");
			} else
				if (text!=null && text.contains("<html>")) {
					text = ErrorMsg.removeHTMLtags(text);
					super.setText(text);
				} else
					super.setText(text);

			putClientProperty("JButton.buttonType", "textured");
			// putClientProperty("JButton.buttonType", "gradient");
		} else
			super.setText(text);
	}

	@Override
	public void setText(String text) {
		mySetText(text);
	}

	public JMButton() {
		super();
	}

	
}
