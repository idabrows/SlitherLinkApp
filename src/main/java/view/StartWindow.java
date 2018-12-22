package view;

import controller.FileCommunicator;
import controller.ViewWorker;
import model.Map;
import view.Components.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class StartWindow extends JFrame {
    private MyTextFields myTextFields;
    private JLabel jLabel = new JLabel();
    private JFrame frame;
    private JPanel panel1 = new JPanel();
    private MyTable table;
    private MyPicture picture;
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
    private  int indToDel;

    public StartWindow() {
//        setSize(300,300);
        createandshow();
        VW = new ViewWorker();
        openButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) { FileOpenActionPerformed(evt); }
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
                NewActionPerformed(evt);
            }
        });
        viewButton.getButton().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed(evt);
            }
        });

    }

    private void FileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileOpenActionPerformed
        F = FC.getFR().getUsersFile(this);
        try {
            map=FC.getFR().getUserMap(F);
            SwingUtilities.updateComponentTreeUI(this);
            frame.setVisible(true);
            new MapWindow(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void HelpActionPerformed(java.awt.event.ActionEvent evt) {
        new HelpWindow();
    }



    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {
        Map newMap = new Map();
        String[][] s = new String[8][6];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                s[i][j] = "";
            }
        }
        newMap.setCoefficients(s);
        new MapWindow(newMap);
    }

    private void OkActionPerformed(java.awt.event.ActionEvent evt) {
        frame.setSize(60*6,30*8);
        table = new MyTable(8,6,0,0,frame.getContentPane());
        frame.getContentPane().remove(indToDel-1);
        frame.getContentPane().remove(indToDel-2);
        frame.setVisible(true);

    }

    private void NewActionPerformed(java.awt.event.ActionEvent evt) {
        myTextFields = new MyTextFields(frame.getContentPane());
        okButton = new MyButton("OK",3,0,frame.getContentPane());
        indToDel=frame.getContentPane().getComponentCount();
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
        solveButton = new MyButton("SOLVE",4,1,frame.getContentPane());
        viewButton = new MyButton("VIEW",5,1,frame.getContentPane());


        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new StartWindow();
    }

}
