package view.Components;

import javax.swing.*;
import java.awt.*;

public class MyPicture{
    private JLabel label;

    public MyPicture(int x, int y, Container pane,String fileName){
        GridBagConstraints c = new GridBagConstraints();



        label = new JLabel(new ImageIcon(fileName));
        pane.add(label);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx=1;
        c.insets = new Insets(0,0,0,0);
        c.gridx = x;
        c.gridy = y;
        pane.add(label, c);

    }



    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }



}
