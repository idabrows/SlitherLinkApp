package view.Components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;

public class MyTable {
        private JTable table;

        public MyTable(int nrows,int ncols,int x, int y, Container pane){
            GridBagConstraints c = new GridBagConstraints();
            DefaultTableModel model = new DefaultTableModel(nrows,ncols);
            table = new JTable(model);
            table.setRowHeight(30);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            DefaultTableCellRenderer centerRenderer = new CenterRenderer();
            for (int i = 0; i < ncols; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(30);
                table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );

            }

            c.fill = GridBagConstraints.CENTER;
            c.ipady = 0;       //reset to default
            c.weighty = 1.0;   //request any extra vertical space
            c.anchor = GridBagConstraints.PAGE_END;
            c.insets = new Insets(0,0,0,0);
            c.gridx = x;
            c.gridwidth = 10;
            c.gridy = y;
            pane.add(table, c);
        }

    public MyTable(JTable jTable,int x, int y, Container pane){
        GridBagConstraints c = new GridBagConstraints();
        table = jTable;
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableCellRenderer centerRenderer = new CenterRenderer();
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(30);
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }

        c.fill = GridBagConstraints.CENTER;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,0,0);  //top padding
        c.gridx = x;       //aligned with button 2
        c.gridwidth = 10;   //2 columns wide
        c.gridy = y;       //third row
        pane.add(table, c);
    }



    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

}
