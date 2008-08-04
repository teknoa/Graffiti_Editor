//==============================================================================
//
//   GraffitiInternalFrame.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiInternalFrame.java,v 1.2 2008/08/04 09:42:41 klukas Exp $

package org.graffiti.editor;

import java.awt.Color;
import java.awt.ContainerOrderFocusTraversalPolicy;

import javax.swing.JInternalFrame;
import javax.swing.JRootPane;
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
public class GraffitiInternalFrame
    extends JInternalFrame //MaximizeFrame
{
    //~ Instance fields ========================================================

    /** The session this frame is in. */
    private EditorSession session;

    /** The view this frame contains. */
    private View view;

    /** The number of this internal frame. */
    private int frameNumber;

	private String initTitle;

    //~ Constructors ===========================================================

    /**
     * Constructs a new <code>GraffitiInternalFrame</code>.
     */
    public GraffitiInternalFrame()
    {
        super();
        this.setMaximizable(true);
        this.setClosable(true);
        this.setResizable(true);
        this.setIconifiable(true);
        
        // this.setFocusable(false);
        setFocusTraversalPolicyProvider(true);
        setFocusTraversalPolicy(new ContainerOrderFocusTraversalPolicy());

        GravistoService.getInstance().addFrame(this);
        
    }
    
    /**
     * Constructor that sets the session, as well as the title.
     *
     * @param session the session this frame is in.
     * @param view DOCUMENT ME!
     * @param title the title of this internal frame.
     */
    public GraffitiInternalFrame(final EditorSession session, final View view, String title)
    {
        this();
        this.session = session;
        this.view = view;
        frameNumber = session.getViews().size() + 1;
        setTitle(title);
        setListener();
        // if (!view.putInScrollPane()) {
	        setOpaque(true);
	        setBackground(Color.WHITE);
        // }
    }

    //~ Methods ================================================================

    private void setListener() {
       addInternalFrameListener(new InternalFrameListener(){
 			public void internalFrameOpened(InternalFrameEvent e) {
 			}

 			public void internalFrameClosing(InternalFrameEvent e) {
 			}

 			public void internalFrameClosed(InternalFrameEvent e) {
 				GravistoService.getInstance().framesDeselect();
 			}

 			public void internalFrameIconified(InternalFrameEvent e) {
 			}

 			public void internalFrameDeiconified(InternalFrameEvent e) {
 			}

 			public void internalFrameActivated(InternalFrameEvent e) {
 				MainFrame.getInstance().setActiveSession(session, view);
 				session.setActiveView(view);
 			}

 			public void internalFrameDeactivated(InternalFrameEvent e) {
 			}});
       }

	public GraffitiInternalFrame(GraffitiFrame frame) {
   	 this();
   	 this.session = frame.getSession();
   	 this.view = frame.getView();
   	 this.frameNumber = frame.getFrameNumber();
   	 setTitle(frame.getInitTitle());
   	 setListener();
	 }

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
     * Sets the title of this frame and its associated button and menu button.
     *
     * @param title the new title of the frame.
     */
    public void setTitle(String title)
    {
   	 this.initTitle = title;
        String frameTitle = title + " - view " + frameNumber;

        super.setTitle(frameTitle);
    }

    @Override
	public String getTitle() {
       return initTitle + " - view " + frameNumber;
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
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------
