/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005,2007 The Authors.  See http://www.simbrain.net/credits
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.workspace.gui;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.apache.log4j.Logger;
import org.simbrain.util.SFileChooser;
import org.simbrain.util.Utils;
import org.simbrain.workspace.WorkspaceComponent;
import org.simbrain.workspace.WorkspaceComponentListener;
import org.simbrain.workspace.WorkspacePreferences;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Represents a window in the Simbrain desktop.   Services relating to
 * couplings and relations between are handled
 */
public abstract class GuiComponent<E extends WorkspaceComponent<?>> extends JPanel {

    /** Logger. */
    private static final Logger LOGGER = Logger.getLogger(GuiComponent.class);
    
    /** Reference to workspace component. */
    private final E workspaceComponent;
    
    /** Reference to  parent frame. */
    private GenericFrame parentFrame;

    /** Log4j logger. */
    private Logger logger = Logger.getLogger(GuiComponent.class);
   
    /**
     * Construct a workspace component.
     */
    public GuiComponent(GenericFrame frame, E workspaceComponent) {
        super();
        this.parentFrame = frame;
        this.workspaceComponent = workspaceComponent;
        logger.trace(this.getClass().getCanonicalName() + " created");
    }

    /**
     * If any initialization is needed after adding this component to workspace.
     *
     */
    public void postAddInit() {
        /* no implementation */
    }

   /**
    * Perform cleanup after closing.
    * TODO: Move to model?
    */
    public abstract void close();

    /**
     * Update that goes beyond updating couplings.
     * Called when global workspace update is called.
     */
    protected void update() {
        repaint();
    }

    /**
     * Calls up a dialog for opening a workspace component.
     */
    public void showOpenFileDialog() {
        SFileChooser chooser = new SFileChooser(workspaceComponent.getCurrentDirectory(), workspaceComponent.getFileExtension());
        File theFile = chooser.showOpenDialog();
        if (theFile != null) {
            workspaceComponent.open(theFile);
            workspaceComponent.setName(theFile.getName());
            getParentFrame().setTitle(workspaceComponent.getName());
            postAddInit();
        }
    }

    /**
     * Show the dialog for saving a workspace component.
     */
    public void showSaveFileDialog() {
        SFileChooser chooser = new SFileChooser(workspaceComponent.getCurrentDirectory(), workspaceComponent.getFileExtension());
        
        if (workspaceComponent.getCurrentFile() != null) {
            chooser.setSelectedFile(workspaceComponent.getCurrentFile());
        } else {
            chooser.setSelectedFile(new File(getName()));
        }
        
        File theFile = chooser.showSaveDialog();
        
        if (theFile != null) {
            workspaceComponent.save(theFile);
            workspaceComponent.setCurrentDirectory(chooser.getCurrentLocation());
            workspaceComponent.setName(theFile.getName());
            getParentFrame().setTitle(workspaceComponent.getName());
        }
    }

    /**
     * Save vs. save-as.  Saves the currentfile.
     */
    public void save() {
        if (workspaceComponent.getCurrentFile() == null) {
            showSaveFileDialog();
        } else {
            workspaceComponent.save(workspaceComponent.getCurrentFile());
        }
    }
    
    /**
     * Writes the bounds of this desktop component to the provided stream.
     * 
     * @param ostream the stream to write to
     * @throws IOException if an IO error occurs
     */
    public void save(final OutputStream ostream) throws IOException {
        new XStream(new DomDriver()).toXML(getBounds(), ostream);
    }
    
    /**
     * Creates a new desktop component from the provided stream.
     * 
     * @param component the component to create the desktop component for.
     * @param istream the inputstream containing the serialized data.
     * @param name the name of the desktop component.
     * @return a new component.
     */
    public static GuiComponent<?> open(final WorkspaceComponent<?> component,
            final InputStream istream, final String name) {
        SimbrainDesktop desktop = SimbrainDesktop.getDesktop(component.getWorkspace());
        GuiComponent<?> dc = desktop.createDesktopComponent(null, component);
        Rectangle bounds = (Rectangle) new XStream(new DomDriver()).fromXML(istream);
        
        dc.setName(name);
        dc.setBounds(bounds);
        
        return dc;
    }
    
    /**
     * Checks to see if anything has changed and then offers to save if true.
     */
    public void showHasChangedDialog() {
        Object[] options = {"Save", "Don't Save", "Cancel" };
        int s = JOptionPane
                .showInternalOptionDialog(this,
                 "This component has changed since last save,\nWould you like to save these changes?",
                 "Component Has Changed", JOptionPane.YES_NO_OPTION,
                 JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        if (s == JOptionPane.OK_OPTION) {
            this.save();
            workspaceComponent.close();
        } else if (s == JOptionPane.NO_OPTION) {
            workspaceComponent.close();
        } else if (s == JOptionPane.CANCEL_OPTION) {
            return;
        }
    }

    public String getName() {
        return (workspaceComponent == null) ? "null" : workspaceComponent.getName();
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        workspaceComponent.setName(name);
        getParentFrame().setTitle(name);
    }




    /**
     * Retrieves a simple version of a component name from its class,
     * e.g. "Network" from "org.simbrain.network.NetworkComponent"/
     *
     * @return the simple name.
     */
    public String getSimpleName() {
        String simpleName = getClass().getSimpleName();
        if (simpleName.endsWith("Component")) {
            simpleName = simpleName.replaceFirst("Component", "");
        }
        return simpleName;
    }
    
    public E getWorkspaceComponent() {
        return workspaceComponent;
    }
    
    protected class BasicComponentListener implements WorkspaceComponentListener {
        public BasicComponentListener() {
            /* need a public constructor for subclasses */
        }
        
        public void componentUpdated() {
            update();
        }
    }
            

    public void setParentFrame(GenericFrame parentFrame) {
        this.parentFrame = parentFrame;
    }
    
    public GenericFrame getParentFrame() {
        return this.parentFrame;
    }

    

    
    

}
