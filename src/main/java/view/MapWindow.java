package view;

import controller.SolverController;
import model.Map;
import view.Components.MyPicture;

import javax.swing.*;
import java.awt.*;


class MapWindow extends JFrame{
    private JFrame frame;
    private JPanel panel1;
    private Map map;

    MapWindow(Map map){
        this.map = map;
        create();
    }

    void create(){
        panel1.setLayout(new FlowLayout());
        frame = new JFrame("Slitherlink");
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setSize(40*(map.getCols()+2),20+43 *(map.getRows()+2));
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
    }

    void showUnsolved(){
//j==0
        new MyPicture(0,0,frame.getContentPane(),"/CornerUpLeft.png");
        for (int i = 1; i < map.getCols()+1; i++)
            new MyPicture(i,0,frame.getContentPane(),"/BorderUp0.png");
        new MyPicture(map.getCols()+1,0,frame.getContentPane(),"/CornerUpRight.png");
//j==1...map.getRows
        for (int j = 1; j < map.getRows()+1; j++) {
            new MyPicture(0,j,frame.getContentPane(),"/BorderLeft0.png");
            for (int i = 1; i < map.getCols()+1; i++)
                new MyPicture(i,j,frame.getContentPane(),"/"+map.getCoefficients()[j-1][i-1]+".png");
            new MyPicture(map.getCols()+1,j,frame.getContentPane(),"/BorderRight0.png");
        }
//j==map.getRows+1
        new MyPicture(0,map.getRows()+1,frame.getContentPane(),"/CornerDownLeft.png");
        for (int i = 1; i < map.getCols()+1; i++)
            new MyPicture(i,map.getRows()+1,frame.getContentPane(),"/BorderDown0.png");
        new MyPicture(map.getCols()+1,map.getRows()+1,frame.getContentPane(),"/CornerDownRight.png");

        frame.setVisible(true);
    }

    void showSolved(SolverController solverController) {
        if(!solverController.isToSolve()){
            JOptionPane.showMessageDialog(frame, "No solution exists.");
            return;
        }
//j==0, i==0
        new MyPicture(0,0,frame.getContentPane(),"/CornerUpLeft.png");
//j==0,i==1...map.getCols
        for (int i = 1; i < map.getCols()+1; i++) {
            if (solverController.getGameVariables().getHorizontalLineInt()[0][i - 1] == 1)
                new MyPicture(i, 0, frame.getContentPane(), "/BorderUp1.png");
            else
                new MyPicture(i, 0, frame.getContentPane(), "/BorderUp0.png");
        }
        new MyPicture(map.getCols()+1,0,frame.getContentPane(),"/CornerUpRight.png");
//j==1...map.getRows, i==0
        for (int j = 1; j < map.getRows()+1; j++) {
            if(solverController.getGameVariables().getVerticalLineInt()[j-1][0]==1)
                new MyPicture(0,j,frame.getContentPane(),"/BorderLeft1.png");
            else
                new MyPicture(0,j,frame.getContentPane(),"/BorderLeft0.png");
//j==1...map.getRows, i==1...map.getCols
            for (int i = 1; i < map.getCols()+1; i++) {
                String str = "/";
                if(solverController.getGameVariables().getHorizontalLineInt()[j-1][i-1]==1) str += "Up";
                if(solverController.getGameVariables().getHorizontalLineInt()[j][i-1]==1) str += "Down";
                if(solverController.getGameVariables().getVerticalLineInt()[j-1][i-1]==1) str += "Left";
                if(solverController.getGameVariables().getVerticalLineInt()[j-1][i]==1) str += "Right";
                str += map.getCoefficients()[j-1][i-1];
                str += ".png";
                new MyPicture(i,j,frame.getContentPane(),str);
            }
//j==1...map.getRows, i==map.getCols+1
            if(solverController.getGameVariables().getVerticalLineInt()[j-1][map.getCols()]==1)
                new MyPicture(map.getCols()+1,j,frame.getContentPane(),"/BorderRight1.png");
            else
                new MyPicture(map.getCols()+1,j,frame.getContentPane(),"/BorderRight0.png");
        }
//j==map.getRows+1, i==0
        new MyPicture(0,map.getRows()+1,frame.getContentPane(),"/CornerDownLeft.png");
//j==map.getRows+1, i==1...map.getCols
        for (int i = 1; i < map.getCols()+1; i++)
            if (solverController.getGameVariables().getHorizontalLineInt()[map.getRows()][i - 1] == 1)
                new MyPicture(i, map.getRows()+1, frame.getContentPane(), "/BorderDown1.png");
            else
                new MyPicture(i, map.getRows()+1, frame.getContentPane(), "/BorderDown0.png");
//j==map.getRows+1, i==1...map.getCols
        new MyPicture(map.getCols()+1,map.getRows()+1,frame.getContentPane(),"/CornerDownRight.png");
        frame.setVisible(true);
    }

    JFrame getFrame() {
        return frame;
    }

    void setMap(Map map) {
        this.map = map;
    }

}
