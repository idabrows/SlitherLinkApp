package view;

import controller.FileCommunicator;
import controller.SolverController;
import controller.ViewWorker;
import ilog.concert.IloException;
import model.Map;
import view.Components.MyPicture;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class MapWindow extends JFrame {
    private JFrame frame;
    private JPanel panel1 = new JPanel();
    private MyPicture picture;
    private Map map;

    public MapWindow(Map map) {
        this.map=map;
        create();
    }


    public void create(){
        panel1.setLayout(new FlowLayout());
        frame = new JFrame("Slitherlink");
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setSize(40*(map.getCols()+2),20+43 *(map.getRows()+2));
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
    }


    public void showUnsolved(){
        //j==0
        picture = new MyPicture(0,0,frame.getContentPane(),"MapPictures//CornerUpLeft.png");
        for (int i = 1; i < map.getCols()+1; i++)
            picture = new MyPicture(i,0,frame.getContentPane(),"MapPictures//BorderUp0.png");
        picture = new MyPicture(map.getCols()+1,0,frame.getContentPane(),"MapPictures//CornerUpRight.png");
        //j==1...map.getRows
        for (int j = 1; j < map.getRows()+1; j++) {
            picture = new MyPicture(0,j,frame.getContentPane(),"MapPictures//BorderLeft0.png");
            for (int i = 1; i < map.getCols()+1; i++)
                picture = new MyPicture(i,j,frame.getContentPane(),"MapPictures//"+map.getCoefficients()[j-1][i-1]+".png");
            picture = new MyPicture(map.getCols()+1,j,frame.getContentPane(),"MapPictures//BorderRight0.png");
        }
        //j==map.getRows+1
        picture = new MyPicture(0,map.getRows()+1,frame.getContentPane(),"MapPictures//CornerDownLeft.png");
        for (int i = 1; i < map.getCols()+1; i++)
            picture = new MyPicture(i,map.getRows()+1,frame.getContentPane(),"MapPictures//BorderDown0.png");
        picture = new MyPicture(map.getCols()+1,map.getRows()+1,frame.getContentPane(),"MapPictures//CornerDownRight.png");

        frame.setVisible(true);
    }

    public void showSolved(SolverController solverController) throws IloException {
        if(!solverController.isToSolve()){
            JOptionPane.showMessageDialog(frame, "No solution exists.");
            return;
        }
        //j==0, i==0
        picture = new MyPicture(0,0,frame.getContentPane(),"MapPictures//CornerUpLeft.png");
        //j==0,i==1...map.getCols
        for (int i = 1; i < map.getCols()+1; i++) {
            if (solverController.getGameVariables().getHorizontalLineInt()[0][i - 1] == 1)
                picture = new MyPicture(i, 0, frame.getContentPane(), "MapPictures//BorderUp1.png");
            else
                picture = new MyPicture(i, 0, frame.getContentPane(), "MapPictures//BorderUp0.png");
        }
        picture = new MyPicture(map.getCols()+1,0,frame.getContentPane(),"MapPictures//CornerUpRight.png");
        //j==1...map.getRows, i==0
        for (int j = 1; j < map.getRows()+1; j++) {
            if(solverController.getGameVariables().getVerticalLineInt()[j-1][0]==1)
                picture = new MyPicture(0,j,frame.getContentPane(),"MapPictures//BorderLeft1.png");
            else
                picture = new MyPicture(0,j,frame.getContentPane(),"MapPictures//BorderLeft0.png");
            //j==1...map.getRows, i==1...map.getCols
            for (int i = 1; i < map.getCols()+1; i++) {
                String str = "MapPictures//";
                if(solverController.getGameVariables().getHorizontalLineInt()[j-1][i-1]==1) str += "Up";
                if(solverController.getGameVariables().getHorizontalLineInt()[j][i-1]==1) str += "Down";
                if(solverController.getGameVariables().getVerticalLineInt()[j-1][i-1]==1) str += "Left";
                if(solverController.getGameVariables().getVerticalLineInt()[j-1][i]==1) str += "Right";
                str += map.getCoefficients()[j-1][i-1];
                str += ".png";
                 picture = new MyPicture(i,j,frame.getContentPane(),str);
            }
            //j==1...map.getRows, i==map.getCols+1
            if(solverController.getGameVariables().getVerticalLineInt()[j-1][map.getCols()]==1)
                picture = new MyPicture(map.getCols()+1,j,frame.getContentPane(),"MapPictures//BorderRight1.png");
            else
                picture = new MyPicture(map.getCols()+1,j,frame.getContentPane(),"MapPictures//BorderRight0.png");
        }

        //j==map.getRows+1, i==0
        picture = new MyPicture(0,map.getRows()+1,frame.getContentPane(),"MapPictures//CornerDownLeft.png");
        //j==map.getRows+1, i==1...map.getCols
        for (int i = 1; i < map.getCols()+1; i++)
            if (solverController.getGameVariables().getHorizontalLineInt()[map.getRows()][i - 1] == 1)
                picture = new MyPicture(i, map.getRows()+1, frame.getContentPane(), "MapPictures//BorderDown1.png");
            else
                picture = new MyPicture(i, map.getRows()+1, frame.getContentPane(), "MapPictures//BorderDown0.png");
        //j==map.getRows+1, i==1...map.getCols
        picture = new MyPicture(map.getCols()+1,map.getRows()+1,frame.getContentPane(),"MapPictures//CornerDownRight.png");
        frame.setVisible(true);
    }



    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public MyPicture getPicture() {
        return picture;
    }

    public void setPicture(MyPicture picture) {
        this.picture = picture;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}
