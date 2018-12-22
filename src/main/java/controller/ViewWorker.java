package controller;

import model.Map;
import view.Components.CenterRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class ViewWorker extends JFrame {
    public JTable getTable(Map map){
        String[] columns;
        String[][] data;
        TableModel model;
        columns = map.stringCols();
        data = map.stringCoefficients();
        model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(45);

        TableColumn column;
        CenterRenderer centerRenderer = new CenterRenderer();
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(25);
            table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }

        // setSize(20*map.getCols(), 20*map.getRows());
        setLocationRelativeTo(null);
        System.out.println("x");
        return table;
    }

}
