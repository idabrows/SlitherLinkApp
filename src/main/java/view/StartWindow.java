package view;

import controller.FileCommunicator;
import controller.SolverController;
import controller.ViewWorker;
import ilog.concert.IloException;
import model.Map;
import view.Components.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class StartWindow extends JFrame {
    private OkButton okButton;
    private MapWindow mapWindow;
    private MySpinners mySpinners;
    private JFrame frame;
    private MyTable table;
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
                frame.getContentPane().remove(mySpinners.getSpinner1());
                frame.getContentPane().remove(mySpinners.getSpinner2());
                frame.getContentPane().remove(mySpinners.getLabel1());
                frame.getContentPane().remove(mySpinners.getLabel2());
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
        frame.getContentPane().remove(mySpinners.getSpinner1());
        frame.getContentPane().remove(mySpinners.getSpinner2());
        frame.getContentPane().remove(mySpinners.getLabel1());
        frame.getContentPane().remove(mySpinners.getLabel2());
        frame.getContentPane().remove(okButton.getButton());
        frame.setSize(40+40*(Integer)mySpinners.getSpinner2().getValue(),40+40*(Integer)mySpinners.getSpinner1().getValue());
        table = new MyTable((Integer) mySpinners.getSpinner1().getValue(),(Integer) mySpinners.getSpinner2().getValue(),0,0,frame.getContentPane());
        frame.repaint();
        frame.setVisible(true);

    }


    private void NewActionPerformed(java.awt.event.ActionEvent evt) {
        frame.setSize(0,0);
        if(table!=null)
            frame.getContentPane().remove(table.getTable());
        if(okButton!=null) {
            frame.getContentPane().remove(mySpinners.getSpinner1());
            frame.getContentPane().remove(mySpinners.getSpinner2());
            frame.getContentPane().remove(mySpinners.getLabel1());
            frame.getContentPane().remove(mySpinners.getLabel2());
            frame.getContentPane().remove(okButton.getButton());
        }
        mySpinners = new MySpinners(frame.getContentPane());
        okButton = new OkButton(frame.getContentPane());
        frame.repaint();
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
        newButton = new MyButton("NEW",0,2,frame.getContentPane());
        openButton = new MyButton("OPEN",1,2,frame.getContentPane());
        helpButton = new MyButton("HELP",2,2,frame.getContentPane());
        saveButton = new MyButton("SAVE",3,2,frame.getContentPane());
        saveButton.getButton().setEnabled(false);
        solveButton = new MyButton("SOLVE",4,2,frame.getContentPane());
        solveButton.getButton().setEnabled(false);
        viewButton = new MyButton("VIEW",5,2,frame.getContentPane());
        viewButton.getButton().setEnabled(false);

        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new StartWindow();
    }

}
