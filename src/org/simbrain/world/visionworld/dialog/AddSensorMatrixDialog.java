/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2006 Jeff Yoshimi <www.jeffyoshimi.net>
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
package org.simbrain.world.visionworld.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import org.simbrain.util.LabelledItemPanel;

import org.simbrain.world.visionworld.Filter;
import org.simbrain.world.visionworld.SensorMatrix;

import org.simbrain.world.visionworld.filter.editor.FilterEditor;
import org.simbrain.world.visionworld.filter.editor.FilterEditorException;

import org.simbrain.world.visionworld.sensormatrix.editor.SensorMatrixEditor;
import org.simbrain.world.visionworld.sensormatrix.editor.SensorMatrixEditorException;

/**
 * Add sensor matrix dialog.
 */
public final class AddSensorMatrixDialog
    extends JDialog {

    /** Filters. */
    private JComboBox filters;

    /** Sensor matrices. */
    private JComboBox sensorMatrices;

    /** Filter editor. */
    private FilterEditor filterEditor;

    /** Sensor matrix editor. */
    private SensorMatrixEditor sensorMatrixEditor;

    /** Filter editor placeholder. */
    private Container filterEditorPlaceholder;

    /** Sensor matrix editor placeholder. */
    private Container sensorMatrixEditorPlaceholder;

    /** OK action. */
    private Action ok;

    /** Cancel action. */
    private Action cancel;

    /** Help action. */
    private Action help;


    /**
     * Create a new add sensor matrix dialog.
     */
    public AddSensorMatrixDialog() {
        super((JDialog) null, "Add Sensor Matrix");

        initComponents();
        layoutComponents();
    }


    /**
     * Initialize components.
     */
    private void initComponents() {
        filters = new JComboBox();
        sensorMatrices = new JComboBox();
        //filters = new JComboBox(new FiltersComboBoxModel());
        //sensorMatrices = new JComboBox(new SensorMatricesComboBoxModel());
        filterEditor = null;
        sensorMatrixEditor = null;
        filterEditorPlaceholder = new JPanel();
        sensorMatrixEditorPlaceholder = new JPanel();

        ok = new AbstractAction("OK") {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event) {
                    ok();
                }
            };

        cancel = new AbstractAction("Cancel") {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event) {
                    setVisible(false);
                }
            };

        help = new AbstractAction("Help") {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event) {
                    // empty
                }
            };
        help.setEnabled(false);
    }

    /**
     * Layout components.
     */
    private void layoutComponents() {
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.add("Center", createMainPanel());
        contentPane.add("South", createButtonPanel());
    }

    /**
     * Create and return the main panel.
     *
     * @return the main panel
     */
    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        LabelledItemPanel filtersPanel = new LabelledItemPanel();
        filtersPanel.addItem("Filter", filters);
        panel.add(filtersPanel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(filterEditorPlaceholder);
        panel.add(Box.createVerticalStrut(6));
        LabelledItemPanel sensorMatricesPanel = new LabelledItemPanel();
        sensorMatricesPanel.addItem("Sensor matrix", sensorMatrices);
        panel.add(sensorMatricesPanel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(sensorMatrixEditorPlaceholder);
        panel.add(Box.createVerticalStrut(6));
        panel.add(Box.createVerticalGlue());
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    /**
     * Create and return the button panel.
     *
     * @return the button panel
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());

        JButton okButton = new JButton(ok);
        JButton cancelButton = new JButton(cancel);
        JButton helpButton = new JButton(help);
        Dimension d = new Dimension(Math.max(cancelButton.getPreferredSize().width, 70),
                                    cancelButton.getPreferredSize().height);
        okButton.setPreferredSize(d);
        cancelButton.setPreferredSize(d);
        helpButton.setPreferredSize(d);
        getRootPane().setDefaultButton(okButton);

        panel.add(okButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(cancelButton);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(helpButton);
        return panel;
    }

    /**
     * Rename me.
     */
    private void ok() {

        Filter filter = null;
        SensorMatrix sensorMatrix = null;

        try {
            filter = filterEditor.createFilter();
        }
        catch (FilterEditorException e) {
            JOptionPane.showInternalMessageDialog(this, "Cannot create filter", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            filters.requestFocus();
        }

        try {
            sensorMatrix = sensorMatrixEditor.createSensorMatrix(filter);
        }
        catch (SensorMatrixEditorException e) {
            JOptionPane.showInternalMessageDialog(this, "Cannot create sensor matrix", e.getMessage(), JOptionPane.ERROR_MESSAGE);
            sensorMatrices.requestFocus();
        }

        // todo:  need a way to return the sensor matrix to the caller
    }
}