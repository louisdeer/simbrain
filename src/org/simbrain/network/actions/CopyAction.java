
package org.simbrain.network.actions;

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import org.simbrain.network.NetworkPanel;
import org.simbrain.network.NetworkSelectionEvent;
import org.simbrain.network.NetworkSelectionListener;

import org.simbrain.resource.ResourceManager;

/**
 * Copy action.
 */
public final class CopyAction
    extends AbstractAction {

    /** Network panel. */
    private final NetworkPanel networkPanel;


    /**
     * Create a new copy action with the specified network panel.
     *
     * @param networkPanel network panel, must not be null
     */
    public CopyAction(final NetworkPanel networkPanel) {
        super("Copy");

        if (networkPanel == null) {
            throw new IllegalArgumentException("networkPanel must not be null");
        }

        this.networkPanel = networkPanel;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, toolkit.getMenuShortcutKeyMask());

        putValue(ACCELERATOR_KEY, keyStroke);
        putValue(SMALL_ICON, ResourceManager.getImageIcon("Copy.gif"));

        updateAction();
        // add a selection listener to update state based on selection
        networkPanel.addSelectionListener(new NetworkSelectionListener() {

                /** @see NetworkSelectionListener */
                public void selectionChanged(final NetworkSelectionEvent event) {
                    updateAction();
                }
            });
    }

    /**
     * Set action text based on number of selected neurons.
     */
    private void updateAction() {
        int numSelected = networkPanel.getSelectedModelElements().size();
        if (numSelected > 0) {
            setEnabled(true);
        } else {
            setEnabled(false);
        }
    }

    /** @see AbstractAction */
    public void actionPerformed(final ActionEvent event) {
        networkPanel.copy();
    }
}