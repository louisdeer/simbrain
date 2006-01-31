/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005 Jeff Yoshimi <www.jeffyoshimi.net>
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
package org.simbrain.network.dialog.network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.simbrain.network.NetworkPanel;
import org.simbrain.network.dialog.network.layout.LayoutPanel;
import org.simbrain.util.LabelledItemPanel;
import org.simbrain.util.StandardDialog;

import com.Ostermiller.util.CSVParser;


/**
 * <b>HopfieldDialog</b> is a dialog box for creating hopfield networks.
 */
public class HopfieldDialog extends StandardDialog implements ActionListener {
    /** File system seperator. */
    private static final String FS = System.getProperty("file.separator");
    /** Descrete network type index. */
    public static final int DISCRETE = 0;
    /** Continuous network type index. */
    public static final int CONTINUOUS = 1;
    /** Tabbed pane. */
    private JTabbedPane tabbedPane = new JTabbedPane();
    /** Logic tab panel. */
    private JPanel tabLogic = new JPanel();
    /** Layout tab panel. */
    private JPanel tabLayout = new JPanel();
    /** Logic panel. */
    private LabelledItemPanel logicPanel = new LabelledItemPanel();
    /** Layout panel. */
    private LayoutPanel layoutPanel;
    /** Number of units field. */
    private JTextField numberOfUnits = new JTextField();
    /** Network type combo box. */
    private JComboBox cbType = new JComboBox(new String[] {"Discrete", "Continuous" });
    /** Open training file button. */
    private JButton trainingFile = new JButton("Set");
    /** Array of string values. */
    private String[][] values = null;

    private NetworkPanel networkPanel;

    /**
     * This method is the default constructor.
     */
    public HopfieldDialog(final NetworkPanel net) {
        networkPanel = net;
        init();
    }

    /** @see StandardDialog */
    protected void closeDialogOk() {
        super.closeDialogOk();
        commitChanges();
    }

    private void commitChanges() {
//          if (getType() == HopfieldDialog.DISCRETE) {
//          DiscreteHopfield hop = new DiscreteHopfield(getNumUnits());
//          networkPanel.getNetwork().addNetwork(hop, getCurrentLayout());
//      } else if (getType() == HopfieldDialog.CONTINUOUS) {
//          ContinuousHopfield hop = new ContinuousHopfield(getNumUnits());
//          networkPanel.getNetwork().addNetwork(hop, getCurrentLayout());
//      }
        }
    
    /**
     * This method initialises the components on the panel.
     */
    private void init() {
        //Initialize Dialog
        setTitle("New Hopfield Network");
        fillFieldValues();
        this.setLocation(500, 0); //Sets location of network dialog
//        layoutPanel = new LayoutPanel(new AbstractLayoutPanel[]{new LineLayoutPanel(), new GridLayoutPanel()});

        trainingFile.addActionListener(this);

        //Set up grapics panel
        logicPanel.addItem("Type", cbType);
        logicPanel.addItem("Number of Units", numberOfUnits);
        logicPanel.addItem("Set training file", trainingFile);

        //Set up tab panel
        tabLogic.add(logicPanel);
        tabLayout.add(layoutPanel);
        tabbedPane.addTab("Logic", logicPanel);
        tabbedPane.addTab("Layout", layoutPanel);
        setContentPane(tabbedPane);
    }

    /**
     * Populate fields with current data.
     */
    public void fillFieldValues() {
    }

    /**
     * Set values based on fields.
     */
    public void getValues() {
    }

    /**
     * @return the number of units.
     */
    public int getNumUnits() {
        return Integer.parseInt(numberOfUnits.getText());
    }

    /**
     * @return the network type.
     */
    public int getType() {
        if (cbType.getSelectedIndex() == 0) {
            return DISCRETE;
        } else {
            return CONTINUOUS;
        }
    }

    /**
     * Responds to actions performed within the dialog.
     * @param e Action event
     */
    public void actionPerformed(final ActionEvent e) {
        loadFile();
    }

    /**
     * Opens the dialog for loading the hopfield training file.
     */
    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("." + FS + "simulations" + FS + "networks"));

        int result = chooser.showDialog(this, "Open");

        if (result == JFileChooser.APPROVE_OPTION) {
            readFile(chooser.getSelectedFile());
        }
    }

    /**
     * Reads the hopfield training file.
     * @param theFile The file to be read
     */
    public void readFile(final File theFile) {
        CSVParser theParser = null;

        try {
            theParser = new CSVParser(new FileInputStream(theFile), "", "", "#");
                      // # is a comment delimeter in net files
            values = theParser.getAllValues();
        } catch (java.io.FileNotFoundException e) {
            JOptionPane.showMessageDialog(
                                          null, "Could not find the file \n" + theFile, "Warning",
                                          JOptionPane.ERROR_MESSAGE);

            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                                          null, "There was a problem opening the file \n" + theFile, "Warning",
                                          JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

            return;
        }
    }
}
