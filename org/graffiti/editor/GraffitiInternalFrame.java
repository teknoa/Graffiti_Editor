//==============================================================================
//
//   GraffitiInternalFrame.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: GraffitiInternalFrame.java,v 1.6 2008/09/26 16:17:37 klukas Exp $

package org.graffiti.editor;

import java.awt.Color;
import java.awt.ContainerOrderFocusTraversalPolicy;
import java.awt.event.ContainerEvent;

import javax.swing.JInternalFrame;
import javax.swing.JRootPane;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.ErrorMsg;
import org.ReleaseInfo;
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
	
	Border b = null;

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

        b = getBorder();
     //   setBorder(null);
        
        GravistoService.getInstance().addFrame(this);
    }
    
    
    
    @Override
	public void doLayout() {
		super.doLayout();
		if (isMaximum() && getBorder()!=null) {
			setBorder(null);
			doLayout();
		}
		if (!isMaximum && getBorder()==null) {
			setBorder(b);
			doLayout();
		}
	}



	@Override
	protected void processContainerEvent(ContainerEvent e) {
		super.processContainerEvent(e);
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

//	        setOpaque(true);
//	        setBackground(Color.WHITE);
	        
	    // if (ReleaseInfo.isRunningAsApplet())
	    	
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

//	public GraffitiInternalFrame(GraffitiFrame frame) {
//   	 this();
//   	 this.session = frame.getSession();
//   	 this.view = frame.getView();
//   	 this.frameNumber = frame.getFrameNumber();
//   	 setTitle(frame.getInitTitle());
//   	 setListener();
//	 }

	/**
     * Returns the session this frame is opened in.
     *
     * @return the session this frame is opened in.
     */
    public EditorSession getSession()
    {
        return session;
    }
    
    public static String startTitle = null;

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
        if (startTitle==null)
        	startTitle = MainFrame.getInstance().getTitle();
        
        if (isSelected())
        if (getBorder()==null) {
        	MainFrame.getInstance().setTitle(startTitle+" - "+frameTitle);
        } else
        	MainFrame.getInstance().setTitle(startTitle);
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
