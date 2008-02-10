/*
 * Created on 24.11.2004
 *
 */
package org.graffiti.editor.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ErrorMsg;

public class ClipboardService {
	private static Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	public static final TransferType READER = new TransferType(Reader.class);
	  public static final TransferType INPUT_STREAM = new TransferType(
			InputStream.class);
	  public static final TransferType CHAR_BUFFER = new TransferType(CharBuffer.class);
	  public static final TransferType BYTE_BUFFER = new TransferType(ByteBuffer.class);

	  public static class TransferType {
		    private Class dataClass;

		    private TransferType(Class streamClass) {
		      this.dataClass = streamClass;
		    }

		    public Class getDataClass() {
		      return dataClass;
		    }

		    public String toString() {
		      return dataClass.getName();
		    }
		  }

	static String mimeType = "text/plain; charset=UTF-8";

	public static void writeToClipboardAsText(String writeMe) {
		Transferable transferableText = new TextTransferable(mimeType, writeMe);
		systemClipboard.setContents(transferableText, null);
	}
	
	private static class TextTransferable implements Transferable, ClipboardOwner {
	    private String data;
	    private DataFlavor flavor;

	    public TextTransferable(String mimeType, String data) {
	      flavor = new DataFlavor(mimeType, "Text");
	      this.data = data;
	    }

	    public DataFlavor[] getTransferDataFlavors() {
	      return new DataFlavor[]{flavor};
	    }

	    public boolean isDataFlavorSupported(DataFlavor flavor) {
	      boolean b = this.flavor.getPrimaryType().equals(flavor.getPrimaryType());
	      return b;
	    }

	    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
	      if (flavor.isRepresentationClassInputStream()) {
	        return new StringBufferInputStream(data);
	      }
	      else if (flavor.isRepresentationClassReader()) {
	        return new StringReader(data);
	      }
	      else if (flavor.isRepresentationClassCharBuffer()) {
	        return CharBuffer.wrap(data);
	      }
	      else if (flavor.isRepresentationClassByteBuffer()) {
	        return ByteBuffer.wrap(data.getBytes());
	      }
	      else 
	    	  throw new UnsupportedFlavorException(flavor);
	    }

	    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard, Transferable contents) {
	    }
	  }

	
	/**
	 * @return The String-Content of the Clipboard or NULL if no
	 * content or content of the wrong type is available. 
	 */
	public static String readFromClipboardAsText() {
		for (DataFlavor df : systemClipboard.getAvailableDataFlavors()) {
			System.out.println("MIME: "+df.getMimeType());
			if (df.getMimeType().startsWith("text/")) {
				Object o;
				try {
					o = systemClipboard.getData(df);
					if (o instanceof StringBufferInputStream) {
						StringBufferInputStream si = (StringBufferInputStream)o;
						String charSet = df.getMimeType().substring(df.getMimeType().indexOf("charset=")+"charset=".length());
						ArrayList<Integer> values = new ArrayList<Integer>();
						int c;
					    while((c = si.read()) != -1)
					        values.add(c);
					    byte[] bytes = new byte[values.size()];
					    for (int i=0; i<bytes.length; i++)
					    	bytes[i] = values.get(i).byteValue();
					    String ress = new String(bytes, charSet);
						return ress;
					}
					if (o instanceof ByteBuffer) {
						ByteBuffer bb = (ByteBuffer)o;
						String charSet = df.getMimeType().substring(df.getMimeType().indexOf("charset=")+"charset=".length());
						ArrayList<Byte> values = new ArrayList<Byte>();
						int c;
					    while(bb.remaining()>0)
					        values.add(bb.get());
					    byte[] bytes = new byte[values.size()];
					    for (int i=0; i<bytes.length; i++)
					    	bytes[i] = values.get(i).byteValue();
					    String ress = new String(bytes, charSet);
						return ress;
					}
				} catch (UnsupportedFlavorException e) {
					ErrorMsg.addErrorMessage(e);
				} catch (IOException e) {
					ErrorMsg.addErrorMessage(e);
				}
			}
		}
		return null;
	}
}
