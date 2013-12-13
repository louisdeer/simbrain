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
package org.simbrain.network.gui.actions.modelgroups;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import org.simbrain.network.core.Neuron;
import org.simbrain.network.groups.NeuronGroup;
import org.simbrain.network.gui.NetworkPanel;

/**
 * Convert selected neurons to a neuron group.
 */
public final class NewNeuronGroupAction extends AbstractAction {

    /** Network panel. */
    private final NetworkPanel networkPanel;

    /**
     * Create a new new neuron group network action with the specified network
     * panel.
     *
     * @param networkPanel networkPanel, must not be null
     */
    public NewNeuronGroupAction(final NetworkPanel networkPanel) {

        super("Neuron group");

        if (networkPanel == null) {
            throw new IllegalArgumentException("NetworkPanel must not be null");
        }

        this.networkPanel = networkPanel;

    }

    /** @see AbstractAction */
    public void actionPerformed(final ActionEvent event) {
        List<Neuron> neuronList = networkPanel.getSelectedModelNeurons();
        if (neuronList.size() > 0) {
            NeuronGroup group = new NeuronGroup(networkPanel.getNetwork());
            networkPanel.getNetwork().transferNeuronsToGroup(neuronList, group);
            networkPanel.getNetwork().addGroup(group);
        }
    }
}