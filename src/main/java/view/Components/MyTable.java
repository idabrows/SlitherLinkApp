package view.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyTable {
        JTable table=new JTable();


        public MyTable(int nrows,int ncols,int x, int y, Container pane){
            GridBagConstraints c = new GridBagConstraints();
            DefaultTableModel model = new DefaultTableModel(nrows,ncols) ;
            JTable table = new JTable(model);

            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 0;       //reset to default
            c.weighty = 1.0;   //request any extra vertical space
            c.anchor = GridBagConstraints.PAGE_END; //bottom of space
            c.insets = new Insets(10,0,0,0);  //top padding
            c.gridx = x;       //aligned with button 2
            c.gridwidth = 200;   //2 columns wide
            c.gridy = y;       //third row
            pane.add(table, c);


        }


}
