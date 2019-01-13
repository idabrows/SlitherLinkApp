package view.Components;

import javax.swing.*;
import java.awt.*;

public class MyStartPicture {
    private final JLabel label;

    public MyStartPicture(Container pane){
        GridBagConstraints c = new GridBagConstraints();
        label = new JLabel(new ImageIcon(MyStartPicture.class.getResource("/Start.png")));
        pane.add(label);
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx=0;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth=7;
        c.gridheight=2;
        pane.add(label, c);
    }

    public JLabel getLabel() {
        return label;
    }

}
