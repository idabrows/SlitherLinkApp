package controller;

import model.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class ViewWorker extends JFrame {
    public JTable getTable(Map map){
        String[] columns;
        String[][] data;
        TableModel model;
        columns = map.stringCols();
        data = map.stringCoefficients();
        model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        setSize(50*map.getCols(), 50*map.getRows());
        setLocationRelativeTo(null);
        System.out.println("x");
        return table;
    }

}
