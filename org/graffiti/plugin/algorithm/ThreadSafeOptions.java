// ==============================================================================
//
// ThreadSafeOptions.java
//
// Copyright (c) 2001-2004 Gravisto Team, University of Passau
//
// ==============================================================================

package org.graffiti.plugin.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.SwingUtilities;

import org.ErrorMsg;
import org.HelperClass;
import org.graffiti.graph.Graph;
import org.graffiti.selection.Selection;

/**
 * This class can be used for thread safe communication between user interfaces
 * and plugins.
 * 
 * @author Christian Klukas, IPK Gatersleben
 * @version $Revision: 1.14 $
 */
public class ThreadSafeOptions implements HelperClass {
	
	/**
	 * Init parameter. This parameter needs to be removed later to make this
	 * class more general.
	 */
	public boolean doRandomInit = false;
	
	/**
	 * Init parameter. This parameter needs to be removed later to make this
	 * class more general.
	 */
	public boolean doCopyPatternLayout = false;
	
	public boolean doMultiplyByNodeDegree = true;
	
	public boolean doRemoveAllBends = true;
	
	/**
	 * Parameter which is evaluated by the springEmbedder layouter at the end of
	 * the run. This parameter needs to be removed later to make this class more
	 * general.
	 */
	public boolean doFinishMoveToTop = true;
	
	public boolean doFinishRemoveOverlapp = true;
	
	/**
	 * Rand-Abstoßung links und oben
	 */
	public double borderWidth = 250;
	
	/**
	 * Abstoßungs-Kraft direkt am Rand
	 */
	public double maxBorderForce = 100;
	
	/**
	 * Run parameter. This parameter needs to be removed later to make this class
	 * more general.
	 */
	public double temperature_max_move = 300; // Double.MAX_VALUE;
	
	/**
	 * Run parameter. This parameter needs to be removed later to make this class
	 * more general.
	 */
	public double temp_alpha = 0.998;
	
	/**
	 * Maximale Anzahl an Threads, die vom Algorithmus verwendet werden sollen
	 */
	public int maxThreads = 0;
	
	/**
	 * If true, distances are calculated with respect to node dimensions.
	 * Intersections of Nodes in Layout should then be impossible.
	 */
	public boolean respectNodeDimensionsForLayout = false;
	
	/**
	 * Rand-Abstoßung verwenden, ja/nein
	 */
	public boolean borderForce = false;
	
	/**
	 * If set to true, a redraw should be done.
	 */
	public boolean redraw = false;
	
	/**
	 * If true, the algorithm should auto-update the view. If false, the
	 * algorithm should only redraw, if the value <code>redraw</code> is true.
	 */
	public boolean autoRedraw = false;
	
	/**
	 * Vecor node array, contains <code>patternNodeStruct</code> objects, which
	 * saves/caches the information about pattern type and number for all nodes
	 * of a graph
	 */
	@SuppressWarnings("unchecked")
	public ArrayList nodeArray;
	
	/**
	 * Used for search from Node to NodeCacheEntry (which includes the Node)
	 */
	@SuppressWarnings("unchecked")
	public HashMap nodeSearch;
	
	/**
	 * Can be interpreted by the algorithm like desired. 0 should be
	 * "not started", 1 should be "running", 2 should be "idle", 3 should be
	 * "finished". Check or overwrite <code>getRunStatus</code> so that the
	 * descriptions match.
	 */
	public int runStatus = 0;
	
	// ////////////////////////////////////////////////////////////////
	
	// /**
	// * DOCUMENT ME!
	// */
	// protected volatile boolean redrawNeeded = false;
	
	/**
	 * Stores the graph reference.
	 */
	private Graph g;
	
	/**
	 * Stores the selection.
	 */
	private Selection sel;
	
	/**
	 * If true, the settigns should be re-read by the plugin.
	 */
	protected volatile boolean settingsChanged = false; // if set to true
	
	/**
	 * If true, the plugin should stop its run.
	 */
	private volatile boolean abortWanted = false;
	
	/**
	 * A second variable for changed settigns. ToDo (CK): check purpose
	 */
	public volatile boolean threadSettingsChanged = false;
	
	/**
	 * Vector of parameter objects.
	 */
	private Vector<Object> paramObjects;
	
	/**
	 * Use this call to indicate that the plugin should stop it work
	 * 
	 * @param value
	 *           Set to True to indicate a wanted stop
	 */
	public void setAbortWanted(boolean value) {
		abortWanted = value;
	}
	
	/**
	 * Check if the plugin should stop.
	 * 
	 * @return True, in case the plugin should stop its run. False, if the plugin
	 *         should continue its work.
	 */
	public boolean isAbortWanted() {
		return abortWanted;
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param newG
	 *           DOCUMENT ME!
	 */
	public synchronized void setGraphInstance(Graph newG) {
		g = newG;
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public synchronized Graph getGraphInstance() {
		return g;
	}
	
	/**
	 * Get a parameter object
	 * 
	 * @param index
	 *           index number (GUI and Plugin should use the same indicies ;-)
	 * @param defaultValue
	 *           In case the parameter is not yet set, this value will be
	 *           returned.
	 * @return The parameter object which was set before or if not yet set the
	 *         defaultValue.
	 */
	public synchronized Object getParam(int index, Object defaultValue) {
		if (paramObjects == null) {
			paramObjects = new Vector<Object>();
		}
		
		try {
			return paramObjects.get(index);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param index
	 *           DOCUMENT ME!
	 * @param setValue
	 *           DOCUMENT ME!
	 * @return DOCUMENT ME!
	 */
	public synchronized Object setParam(int index, Object setValue) {
		if (paramObjects == null) {
			paramObjects = new Vector<Object>();
		}
		
		threadSettingsChanged = true;
		
		if (paramObjects.size() <= index) {
			paramObjects.setSize(index + 1);
		}
		
		paramObjects.setElementAt(setValue, index);
		
		return setValue;
	}
	
	/**
	 * Array for storing Double parameters
	 */
	public Double[] dValues = new Double[1];
	
	/**
	 * Array for storing Boolean parameters
	 */
	public Boolean[] bValues = new Boolean[0];
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param index
	 *           DOCUMENT ME!
	 * @param value
	 *           DOCUMENT ME!
	 */
	public void setDval(int index, double value) {
		synchronized (dValues) {
			if (index >= dValues.length) {
				Double[] newDvalues = new Double[index + 1];
				
				System.arraycopy(dValues, 0, newDvalues, 0, dValues.length);
				dValues = newDvalues;
			}
			
			dValues[index] = new Double(value);
		}
	}
	
	/**
	 * DOCUMENT ME!
	 * 
	 * @param index
	 *           DOCUMENT ME!
	 * @param defaultValue
	 *           DOCUMENT ME!
	 * @return DOCUMENT ME!
	 */
	public double getDval(int index, double defaultValue) {
		synchronized (dValues) {
			if (index >= dValues.length) {
				return defaultValue;
			}
			
			if (dValues[index] == null) {
				return defaultValue;
			}
			
			return dValues[index].doubleValue();
		}
	}
	
	/**
	 * Sets a boolean Parameter with a given Index <code>index</code> and a given
	 * value. It is up to the plugin that uses the options to make sure, that
	 * each time the right index for storing and restoring the parameter value is
	 * used.
	 * 
	 * @param index
	 *           Index of the property. (self choosen from 0...x)
	 * @param value
	 *           The new setting stored at index <code>index</code>.
	 */
	public void setBval(int index, boolean value) {
		synchronized (bValues) {
			if (index >= bValues.length) {
				Boolean[] newBvalues = new Boolean[index + 1];
				
				System.arraycopy(bValues, 0, newBvalues, 0, bValues.length);
				bValues = newBvalues;
			}
			
			bValues[index] = new Boolean(value);
		}
	}
	
	/**
	 * Returns a stored boolean parameter value. If the parameter has not yet
	 * been stored the <code>defaultValue</code> is returned.
	 * 
	 * @param index
	 *           Index of the parameter to be retrieved.
	 * @param defaultValue
	 *           The default value that is returned in case the parameter is yet
	 *           unknown.
	 * @return A boolean parameter value.
	 */
	public boolean getBval(int index, boolean defaultValue) {
		synchronized (bValues) {
			if (index >= bValues.length) {
				return defaultValue;
			}
			
			if (bValues[index] == null) {
				return defaultValue;
			}
			
			return bValues[index].booleanValue();
		}
	}
	
	/**
	 * @return The selected nodes/edges for the graph.
	 */
	public Selection getSelection() {
		return sel;
	}
	
	/**
	 * Set selection for graph.
	 * 
	 * @param selection
	 *           The selection.
	 */
	public void setSelection(Selection selection) {
		sel = selection;
	}
	
	public String getRunStatus() {
		if (runStatus == 0)
			return "init, not started";
		if (runStatus == 1)
			return "running";
		if (runStatus == 2)
			return "idle";
		if (runStatus == 3)
			return "finished";
		return "status error";
	}
	
	private int iv = 0;
	
	public synchronized void addInt(int i) {
		iv += i;
	}
	
	public synchronized void setInt(int i) {
		iv = i;
	}
	
	public synchronized int getInt() {
		return iv;
	}
	
	private double dv = 0;
	private final Double dl = 0d;
	
	public void addDouble(double d) {
		synchronized (dl) {
			dv += d;
		}
	}
	
	public void setDouble(double d) {
		synchronized (dl) {
			dv = d;
		}
	}
	
	public double getDouble() {
		synchronized (dl) {
			return dv;
		}
	}
	
	public void executeThreadSafe(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread())
			runnable.run();
		else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (InterruptedException e) {
				ErrorMsg.addErrorMessage(e);
			} catch (InvocationTargetException e) {
				ErrorMsg.addErrorMessage(e);
			}
		}
	}
	
	private long l = 0;
	
	public synchronized void setLong(long l) {
		this.l = l;
	}
	
	public synchronized void addLong(long l) {
		this.l += l;
	}
	
	public synchronized long getLong() {
		return l;
	}
	
	public synchronized long getNextLong() {
		l++;
		return l;
	}
	
}
