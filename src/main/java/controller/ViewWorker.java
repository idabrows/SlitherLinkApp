package controller;

import model.Map;
import view.Components.MyTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ViewWorker extends JFrame {
    JTable getTable(Map map) {
        String[] columns;
        String[][] data;
        TableModel model;
        columns = map.stringCols();
        data = map.stringCoefficients();
        model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        setLocationRelativeTo(null);
        return table;
    }

    public Map getMap(MyTable table) {
        Map map = new Map();
        String[][] s = new String[table.getTable().getRowCount()][table.getTable().getColumnCount()];
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[0].length; j++) {
                s[i][j] = (String) table.getTable().getModel().getValueAt(i,j);
            }
        }
        map.setCoefficients(s);
        return map;
    }

}
