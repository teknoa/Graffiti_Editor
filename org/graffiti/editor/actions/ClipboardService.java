/*
 * Created on 24.11.2004
 *
 */
package org.graffiti.editor.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.ErrorMsg;


public class ClipboardService {

	public static void writeToClipboardAsText(CharSequence writeMe) {
		StringSelection copy = new StringSelection(writeMe.toString());
		getSystemClipboard().setContents(copy, copy);
	}
	
	public static String readFromClipboardAsText() {
		Transferable clipboardContents = getSystemClipboard().getContents(null);
		if (clipboardContents==null)
			return null;
		if (clipboardContents.isDataFlavorSupported(DataFlavor.stringFlavor))
			try {
				return (String) clipboardContents.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException e) {
				ErrorMsg.addErrorMessage(e);
			} catch (IOException e) {
				ErrorMsg.addErrorMessage(e);
			}
		return null;
	}
	
	private static java.awt.datatransfer.Clipboard getSystemClipboard() {
	    return Toolkit.getDefaultToolkit().getSystemClipboard();
	}
}
