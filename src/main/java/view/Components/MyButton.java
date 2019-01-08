package view.Components;

import java.awt.*;
import javax.swing.*;

public class MyButton{
    private final JButton button;

    public MyButton(String s,int x,int y,Container pane){
        GridBagConstraints c = new GridBagConstraints();
        button = new JButton(s);
        button.setMinimumSize(new Dimension(65+5*s.length(),25));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0,0,0,0);
        c.gridx = x;
        c.gridwidth = 1;
        c.gridy = y;
        pane.add(button, c);
    }

    public JButton getButton() {
        return button;
    }

}
