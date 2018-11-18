package view;

import controller.FileCommunicator;
import controller.ViewWorker;
import model.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Form2 extends JFrame {
    private JPanel panel1;
    private JButton openButton;
    private JTable table;
    private File F;
    private Map map;
    protected FileCommunicator FC = FileCommunicator.getInstance();
    protected ViewWorker VW;

    public Form2() {
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileOpenActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(openButton, BorderLayout.SOUTH);
        setVisible(true);
        VW = new ViewWorker();
    }

    private void FileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileOpenActionPerformed
        F = FC.getFR().getUsersFile(this);
        try {
            map=FC.getFR().getUserMap(F);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(VW.getTable(map), BorderLayout.NORTH);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        Form2 j = new Form2();
    }
}
