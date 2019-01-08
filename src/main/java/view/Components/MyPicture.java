package view.Components;

import javax.swing.*;
import java.awt.*;

public class MyPicture{

    public MyPicture(int x, int y, Container pane,String fileName){
        GridBagConstraints c = new GridBagConstraints();
        JLabel label = new JLabel(new ImageIcon(fileName));
        pane.add(label);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx=1;
        c.insets = new Insets(0,0,0,0);
        c.gridx = x;
        c.gridy = y;
        pane.add(label, c);
    }

}
