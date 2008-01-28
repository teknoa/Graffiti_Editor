//==============================================================================
//
//   NodeBorder.java
//
//   Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
//==============================================================================
// $Id: NodeBorder.java,v 1.1 2007/06/14 09:36:48 klukas Exp $

package org.graffiti.plugin.tool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

import javax.swing.border.AbstractBorder;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ Provides a border used to mark selected nodes.
 */
public class NodeBorder
    extends AbstractBorder
{
    //~ Instance fields ========================================================

    /** DOCUMENT ME! */
    private final AffineTransform IDENTITY = new AffineTransform();

    /** Color used to paint border. */
    private Color color;

    /** DOCUMENT ME! */
    private Graphics graphics = null;

    /** Width of the border. */
    private int borderWidth;

    //~ Constructors ===========================================================

    /**
     * Constructor for NodeBorder.
     *
     * @param color DOCUMENT ME!
     * @param width DOCUMENT ME!
     */
    public NodeBorder(Color color, int width)
    {
        super();
        this.color = color;
        this.borderWidth = width;
    }

    //~ Methods ================================================================

    /**
     * Sets the insets to the value of <code>width</code>.
     *
     * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component,
     *      java.awt.Insets)
     */
    public Insets getBorderInsets(Component c, Insets insets)
    {
        //        if (graphics == null) {
        insets.top = this.borderWidth;
        insets.left = this.borderWidth;
        insets.bottom = this.borderWidth;
        insets.right = this.borderWidth;

        //        } else {
        //            AffineTransform trafo = ((Graphics2D)graphics).getTransform();
        //            int horSize = (int)Math.ceil(this.borderWidth * trafo.getScaleX());
        //            int vertSize = (int)Math.ceil(this.borderWidth * trafo.getScaleY());
        //
        //            insets.top = vertSize;
        //            insets.left = horSize;
        //            insets.bottom = vertSize;
        //            insets.right = horSize;
        //        }
        return insets;
    }

    /**
     * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component)
     */
    public Insets getBorderInsets(Component c)
    {
        return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }

    /**
     * DOCUMENT ME!
     *
     * @return true.
     *
     * @see javax.swing.border.AbstractBorder#isBorderOpaque()  Returns true.
     */
    public boolean isBorderOpaque()
    {
        return true;
    }

    /**
     * Paints the border.
     *
     * @param c DOCUMENT ME!
     * @param g DOCUMENT ME!
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     * @param width DOCUMENT ME!
     * @param height DOCUMENT ME!
     */
    public void paintBorder(Component c, Graphics g, int x, int y, int width,
        int height)
    {
        this.graphics = g;

        if((c.getX() % 2) == 1)
        {
            AffineTransform at = ((Graphics2D) c.getParent().getGraphics()).getTransform();
            ((Graphics2D) g).translate(at.getTranslateX() - 1,
                at.getTranslateY());
            width += 1;
        }

        if((c.getY() % 2) == 1)
        {
            AffineTransform at = ((Graphics2D) c.getParent().getGraphics()).getTransform();
            ((Graphics2D) g).translate(at.getTranslateX(),
                at.getTranslateY() - 1);
            height += 1;
        }

        Insets insets = getBorderInsets(c);
        Color oldColor = g.getColor();

        g.translate(x, y);
        g.setColor(this.color);

        // Paint top left and right
        Graphics cg;
        cg = g.create();

        //        cg.setClip(0, 0, 2*width, insets.top);
        cg.fillRect(0, 0, this.borderWidth, this.borderWidth);

        //        Point p = new Point((int)Math.ceil(width/((Graphics2D)cg).getTransform().getScaleX() - insets.right), 0);
        //        cg.fillRect(p.x, p.y, this.borderWidth, this.borderWidth);
        cg.fillRect(width - insets.right, 0, this.borderWidth, this.borderWidth);

        cg.dispose();

        // Paint bottom left and right
        cg = g.create();

        //        int h_ib = (int)Math.ceil(height/((Graphics2D)cg).getTransform().getScaleY() - insets.bottom);
        //        cg.setClip(0, h_ib, width, insets.bottom);
        //        Point p = new Point(0, h_ib);
        cg.fillRect(0, height - insets.bottom, this.borderWidth,
            this.borderWidth);

        //        p = new Point((int)Math.ceil(width/((Graphics2D)cg).getTransform().getScaleX() - insets.right), 
        //            (int)Math.ceil(height/((Graphics2D)cg).getTransform().getScaleY() - insets.bottom));
        cg.fillRect(width - insets.right, height - insets.bottom,
            this.borderWidth, this.borderWidth);

        cg.dispose();

        g.translate(-x, -y);
        g.setColor(oldColor);
    }
}

//------------------------------------------------------------------------------
//   end of file
//------------------------------------------------------------------------------