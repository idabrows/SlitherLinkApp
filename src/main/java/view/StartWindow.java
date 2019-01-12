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


class StartWindow extends JFrame {
    private MyStartPicture myStartPicture;
    private OkButton okButton;
    private MapWindow mapWindow;
    private MySpinners mySpinners;
    private JFrame frame;
    private MyTable table;
    private MyButton newButton;
    private MyButton openButton;
    private MyButton helpButton;
    private MyButton saveAsButton;
    private MyButton solveButton;
    private MyButton viewButton;
    private MyButton saveButton;
    private File F;
    private Map map;
    private final FileCommunicator FC = FileCommunicator.getInstance();

    private StartWindow(){
        createandshow();
        openButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { OpenActionPerformed(); }
        });
        helpButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpActionPerformed();
            }
        });
        newButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewActionPerformed();
            }
        });
        saveButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed();
            }
        });
        saveAsButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAsActionPerformed();
            }
        });
        viewButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed();
            }
        });
        solveButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    SolveActionPerformed();
                } catch (IloException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void OpenActionPerformed(){
        F = FC.getFR().getUsersFile(this);
        if(F==null) return;
        try {
            JTable jTable=FC.getFR().getUsersTable(F);
            if(myStartPicture!=null)
                frame.getContentPane().remove(myStartPicture.getLabel());
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
            saveAsButton.getButton().setEnabled(true);
            saveButton.getButton().setEnabled(true);
            viewButton.getButton().setEnabled(true);
            solveButton.getButton().setEnabled(true);
            frame.setSize(40+40*map.getCols(),40+40*map.getRows());
            table = new MyTable(jTable,0,0,frame.getContentPane());
            frame.setTitle(F.getPath()+" - Slitherlink");
            frame.repaint();
            frame.setVisible(true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void SaveAsActionPerformed() {
        ViewWorker viewWorker = new ViewWorker();
        map = viewWorker.getMap(table);
        F = FC.getFW().getUsersDir(this);
        if(F==null) return;
        try {
            map.saveUsersMap(F);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setTitle(F.getPath()+" - Slitherlink");
    }

    private void SaveActionPerformed() {
        ViewWorker viewWorker = new ViewWorker();
        map = viewWorker.getMap(table);
        if(F==null){
            SaveAsActionPerformed();
            return;
        }
        try {
            map.saveUsersMap(F);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void HelpActionPerformed(){
        new HelpWindow();
    }

    private void SolveActionPerformed() throws IloException{
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

    private void ViewActionPerformed(){
        ViewWorker viewWorker = new ViewWorker();
        if(mapWindow==null || !mapWindow.getFocusableWindowState()){
            mapWindow = new MapWindow(viewWorker.getMap(table));
            mapWindow.showUnsolved();
        }
        else{
            mapWindow.setMap(viewWorker.getMap(table));
            mapWindow.getFrame().dispose();
            mapWindow.create();
            mapWindow.showUnsolved();
        }

    }

    private void OkActionPerformed(){
        saveAsButton.getButton().setEnabled(true);
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
        frame.setTitle("Untitled - Slitherlink");
        frame.repaint();
        frame.setVisible(true);
    }

    private void NewActionPerformed(){
        frame.setSize(new Dimension(600,220));
        if(myStartPicture!=null)
            frame.getContentPane().remove(myStartPicture.getLabel());
        if(table!=null)
            frame.getContentPane().remove(table.getTable());
        if(okButton!=null){
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
                OkActionPerformed();
            }
        });
    }



    private void createandshow(){
        frame = new JFrame("Slitherlink");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setMinimumSize(new Dimension(600,220));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        newButton = new MyButton("NEW",0,2,frame.getContentPane());
        openButton = new MyButton("OPEN",1,2,frame.getContentPane());
        helpButton = new MyButton("HELP",2,2,frame.getContentPane());
        saveButton = new MyButton("SAVE",3,2,frame.getContentPane());
        saveButton.getButton().setEnabled(false);
        saveAsButton = new MyButton("SAVE AS",4,2,frame.getContentPane());
        saveAsButton.getButton().setEnabled(false);
        solveButton = new MyButton("SOLVE",5,2,frame.getContentPane());
        solveButton.getButton().setEnabled(false);
        viewButton = new MyButton("VIEW",6,2,frame.getContentPane());
        viewButton.getButton().setEnabled(false);
        myStartPicture = new MyStartPicture(frame.getContentPane(),"resources//MapPictures//Start.png");
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StartWindow();
    }

}
