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
package org.simbrain.world.visionworld.sensormatrix.editor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simbrain.util.LabelledItemPanel;

import org.simbrain.world.visionworld.Filter;
import org.simbrain.world.visionworld.SensorMatrix;

import org.simbrain.world.visionworld.sensormatrix.DenseSensorMatrix;

/**
 * Dense sensor matrix editor.
 */
public final class DenseSensorMatrixEditor
    extends JPanel
    implements SensorMatrixEditor {

    /** Rows. */
    private JTextField rows;

    /** Columns. */
    private JTextField columns;

    /** Receptive field height. */
    private JTextField receptiveFieldHeight;

    /** Receptive field width. */
    private JTextField receptiveFieldWidth;

    /** Default rows. */
    private static final int DEFAULT_ROWS = 10;

    /** Default columns. */
    private static final int DEFAULT_COLUMNS = 10;

    /** Default receptive field height. */
    private static final int DEFAULT_RECEPTIVE_FIELD_HEIGHT = 10;

    /** Default receptive field width. */
    private static final int DEFAULT_RECEPTIVE_FIELD_WIDTH = 10;


    /**
     * Create a new dense sensor matrix editor.
     */
    public DenseSensorMatrixEditor() {
        super();

        initComponents();
        layoutComponents();
    }


    /**
     * Initialize components.
     */
    private void initComponents() {
        rows = new JTextField();
        columns = new JTextField();
        receptiveFieldHeight = new JTextField();
        receptiveFieldWidth = new JTextField();
    }

    /**
     * Layout components.
     */
    private void layoutComponents() {
        setLayout(new BorderLayout());
        LabelledItemPanel dimensionsPanel = new LabelledItemPanel();
        dimensionsPanel.addItem("Rows", rows);
        dimensionsPanel.addItem("Columns", columns);
        dimensionsPanel.addItem("Receptive field height", receptiveFieldHeight);
        dimensionsPanel.addItem("Receptive field width", receptiveFieldWidth);
        add("Center", dimensionsPanel);
    }

    /** {@inheritDoc} */
    public Component getEditorComponent() {
        rows.setText(String.valueOf(DEFAULT_ROWS));
        columns.setText(String.valueOf(DEFAULT_COLUMNS));
        receptiveFieldHeight.setText(String.valueOf(DEFAULT_RECEPTIVE_FIELD_HEIGHT));
        receptiveFieldWidth.setText(String.valueOf(DEFAULT_RECEPTIVE_FIELD_WIDTH));
        return this;
    }

    /** {@inheritDoc} */
    public SensorMatrix createSensorMatrix(final Filter filter) throws SensorMatrixEditorException {
        if (filter == null) {
            throw new SensorMatrixEditorException("filter must not be null");
        }
        try {
            int r = Integer.valueOf(rows.getText());
            int c = Integer.valueOf(columns.getText());
            int h = Integer.valueOf(receptiveFieldHeight.getText());
            int w = Integer.valueOf(receptiveFieldWidth.getText());
            return new DenseSensorMatrix(r, c, w, h, filter);
        }
        catch (NumberFormatException e) {
            throw new SensorMatrixEditorException(e);
        }
        catch (IllegalArgumentException e) {
            throw new SensorMatrixEditorException(e);
        }
    }
}