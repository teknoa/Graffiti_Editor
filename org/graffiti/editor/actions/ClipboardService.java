/*
 * Created on 24.11.2004
 *
 */
package org.graffiti.editor.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.ErrorMsg;

public class ClipboardService {
	private static Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	public static void writeToClipboardAsText(String writeMe) {
		Transferable transferableText = new StringSelection(writeMe);
		systemClipboard.setContents(transferableText, null);
	}
	
	/**
	 * @return The String-Content of the Clipboard or NULL if no
	 * content or content of the wrong type is available. 
	 */
	public static String readFromClipboardAsText() {
		Object cnt;
		try {
			if (!ErrorMsg.isAppLoadingCompleted())
				return null;
			cnt = systemClipboard.getData(DataFlavor.stringFlavor);
			return (String)cnt;
		} catch (UnsupportedFlavorException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
