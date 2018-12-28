package view;

import controller.FileCommunicator;
import controller.SolverController;
import controller.ViewWorker;
import ilog.concert.IloException;
import model.Calculator;
import model.Map;
import view.Components.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class StartWindow extends JFrame {
    private MapWindow mapWindow;
    private MyTextFields myTextFields;
    private JFrame frame;
    private MyTable table;
    private MyButton okButton;
    private MyButton newButton;
    private MyButton openButton;
    private MyButton helpButton;
    private MyButton saveButton;
    private MyButton solveButton;
    private MyButton viewButton;
    private File F;
    private Map map;
    protected FileCommunicator FC = FileCommunicator.getInstance();
    protected ViewWorker VW;
    private JPanel panel1;

    public StartWindow(){
        createandshow();
        VW = new ViewWorker();
        openButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { OpenActionPerformed(evt); }
        });
        helpButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpActionPerformed(evt);
            }
        });
        newButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewActionPerformed(evt);
            }
        });
        saveButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });
        viewButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed(evt);
            }
        });
        solveButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    SolveActionPerformed(evt);
                } catch (IloException e) {
                    e.printStackTrace();
                }
            }
        });
        frame.setMinimumSize(new Dimension(430,150));

    }

    private void OpenActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_FileOpenActionPerformed
        saveButton.getButton().setEnabled(true);
        F = FC.getFR().getUsersFile(this);
        try {
            JTable jTable=FC.getFR().getUsersTable(F);
            if(table!=null)
               frame.getContentPane().remove(table.getTable());
            if(okButton!=null) {
                frame.getContentPane().remove(myTextFields.getForm());
                frame.getContentPane().remove(okButton.getButton());
            }
            map = FC.getFR().getUsersMap(F);
            frame.setSize(40+40*map.getCols(),40+40*map.getRows());
            table = new MyTable(jTable,0,0,frame.getContentPane());
            SwingUtilities.updateComponentTreeUI(this);
            viewButton.getButton().setEnabled(true);
            solveButton.getButton().setEnabled(true);
            frame.repaint();
            frame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void SaveActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_FileOpenActionPerformed
        ViewWorker viewWorker = new ViewWorker();
        map = viewWorker.getMap(table);
        F = FC.getFW().getUsersDir(this);
        try {
            map.saveUsersMap(F);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void HelpActionPerformed(java.awt.event.ActionEvent evt) {
        new HelpWindow();
    }

    private void SolveActionPerformed(java.awt.event.ActionEvent evt) throws IloException {
        ViewWorker viewWorker = new ViewWorker();
        map = viewWorker.getMap(table);
        SolverController solverController = new SolverController(map);
        if(mapWindow==null || !mapWindow.getFocusableWindowState()){
            mapWindow = new MapWindow(map);
            mapWindow.showSolved(solverController);
        }
        else{
            mapWindow.setMap(viewWorker.getMap(table));
            mapWindow.getFrame().dispose();
            mapWindow.create();
            mapWindow.showSolved(solverController);
        }

    }


    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {
        ViewWorker viewWorker = new ViewWorker();
        if(mapWindow==null || !mapWindow.getFocusableWindowState()){
                mapWindow = new MapWindow(viewWorker.getMap(table));
        }
        else{
            mapWindow.setMap(viewWorker.getMap(table));
            mapWindow.getFrame().dispose();
            mapWindow.create();
            mapWindow.showUnsolved();
        }

    }

    private void OkActionPerformed(java.awt.event.ActionEvent evt) {
        saveButton.getButton().setEnabled(true);
        viewButton.getButton().setEnabled(true);
        solveButton.getButton().setEnabled(true);
        frame.setSize(40+40*Integer.parseInt(myTextFields.getForm().getFields()[1].getText()),40+40*Integer.parseInt(myTextFields.getForm().getFields()[0].getText()));
        table = new MyTable(Integer.parseInt(myTextFields.getForm().getFields()[0].getText()),Integer.parseInt(myTextFields.getForm().getFields()[1].getText()),0,0,frame.getContentPane());
        frame.getContentPane().remove(myTextFields.getForm());
        frame.getContentPane().remove(okButton.getButton());
        frame.repaint();
        frame.setVisible(true);

    }


    private void NewActionPerformed(java.awt.event.ActionEvent evt) {
        if(table!=null)
            frame.getContentPane().remove(table.getTable());
        if(okButton!=null) {
            frame.getContentPane().remove(myTextFields.getForm());
            frame.getContentPane().remove(okButton.getButton());
        }
        frame.repaint();
        myTextFields = new MyTextFields(frame.getContentPane());
        okButton = new MyButton("OK",3,0,frame.getContentPane());
        frame.setVisible(true);
        okButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkActionPerformed(evt);
            }
        });
    }



    public void createandshow(){
        frame = new JFrame("Slitherlink");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        //Set up the content pane.
        frame.setSize(430,150);
        newButton = new MyButton("NEW",0,1,frame.getContentPane());
        openButton = new MyButton("OPEN",1,1,frame.getContentPane());
        helpButton = new MyButton("HELP",2,1,frame.getContentPane());
        saveButton = new MyButton("SAVE",3,1,frame.getContentPane());
        saveButton.getButton().setEnabled(false);
        solveButton = new MyButton("SOLVE",4,1,frame.getContentPane());
        solveButton.getButton().setEnabled(false);
        viewButton = new MyButton("VIEW",5,1,frame.getContentPane());
        viewButton.getButton().setEnabled(false);

        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new StartWindow();
    }

}
