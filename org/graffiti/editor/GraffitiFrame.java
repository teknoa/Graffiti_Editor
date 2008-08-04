//==============================================================================
//
//   GraffitiInternalFrame.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiFrame.java,v 1.2 2008/08/04 09:42:41 klukas Exp $

package org.graffiti.editor;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.graffiti.plugin.view.View;
import org.graffiti.session.EditorSession;


/**
 * A specialized internal frame for the graffiti editor. A
 * <code>GraffitiInternalFrame</code> is always resizable, closeable,
 * maximizable and iconifyable.
 *
 * @see javax.swing.JInternalFrame
 * @see MainFrame
 */
public class GraffitiFrame
    extends JFrame //MaximizeFrame
{
    //~ Instance fields ========================================================

	private static final long serialVersionUID = 1L;

	/** The session this frame is in. */
    private EditorSession session;

    /** The view this frame contains. */
    private View view;

	private int frameNumber;

	private String initTitle;

    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>GraffitiInternalFrame</code>.
     */
    public GraffitiFrame(final org.graffiti.editor.GraffitiInternalFrame internalFrame)
    {
        super();
        // Ensure that however the window is closed, it actually causes this
        // detach() method to be fired instead.
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public final void windowClosing(final WindowEvent event) {
            	MainFrame.getInstance().setActiveSession(internalFrame.getSession(), internalFrame.getView());
            	MainFrame.getInstance().fileClose.actionPerformed(new ActionEvent(this, 1, "close"));
            }

 				@Override
 				public void windowActivated(WindowEvent e) {
 					GravistoService.getInstance().framesDeselect();
 					super.windowActivated(e);
 					for (InternalFrameListener ifl : internalFrame.getInternalFrameListeners()) {
 						ifl.internalFrameActivated(new InternalFrameEvent(internalFrame, e.getID()));
 					}
 				}
          });
        
        this.session = internalFrame.getSession();
        this.view = internalFrame.getView();
        super.setTitle(internalFrame.getTitle());
        this.frameNumber = internalFrame.getFrameNumber();
        this.initTitle = internalFrame.getInitTitle();
    }
    
    

    //~ Methods ================================================================

    /**
     * Returns the session this frame is opened in.
     *
     * @return the session this frame is opened in.
     */
    public EditorSession getSession()
    {
        return session;
    }

   /**
     * Returns the view of this frame.
     *
     * @return the view of this frame.
     */
    public View getView()
    {
        return view;
    }


	public int getFrameNumber() {
		return frameNumber;
	}


	public String getInitTitle() {
		return initTitle;
	}
	
	public void setTitle(String title) {
	 this.initTitle = title;
    String frameTitle = title + " - view " + frameNumber;
    super.setTitle(frameTitle);
	}
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
