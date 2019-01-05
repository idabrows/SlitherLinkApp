package view.Components;

import javax.swing.*;
import java.awt.*;


public class OkButton {
    private JButton button;
    GridBagConstraints c = new GridBagConstraints();
    public OkButton(Container pane){
        button = new JButton("OK");
        c.fill = GridBagConstraints.VERTICAL;
        c.ipady = 1;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(40, 20, 15, 0);  //top padding
        c.gridx = 4;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridheight=2;
        c.gridy = 0;       //third row
        pane.add(button, c);

    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public GridBagConstraints getC() {
        return c;
    }

    public void setC(GridBagConstraints c) {
        this.c = c;
    }
}
