package view.Components;

import javax.swing.*;
import java.awt.*;


public class OkButton {
    private final JButton button;

    public OkButton(Container pane){
        button = new JButton("OK");
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 1;
        c.insets = new Insets(75, 20, 65, 0);
        c.gridx = 4;
        c.gridwidth = 1;
        c.gridheight=2;
        c.gridy = 0;
        pane.add(button, c);
    }

    public JButton getButton() {
        return button;
    }

}
