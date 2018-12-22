package view;

import controller.FileCommunicator;
import controller.ViewWorker;
import model.Map;
import view.Components.MyPicture;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class MapWindow extends JFrame {
    private JFrame frame;
    private JPanel panel1 = new JPanel();
    private MyPicture picture;
    private File F;
    private Map map;
    protected FileCommunicator FC = FileCommunicator.getInstance();
    protected ViewWorker VW;

    public MapWindow(Map map) {
        this.map=map;
        createandshow();
    }


    public void createandshow(){
        panel1.setLayout(new FlowLayout());
        frame = new JFrame("Slitherlink");
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        //Set up the content pane.
        frame.setSize(40*(map.getCols()+2),43*(map.getRows()+2));
        frame.setResizable(false);

        //j==0
        picture = new MyPicture(0,0,frame.getContentPane(),"MapPictures//CornerUpLeft.png");
        for (int i = 1; i < map.getCols()+1; i++)
                picture = new MyPicture(i,0,frame.getContentPane(),"MapPictures//BorderUp0.png");
        picture = new MyPicture(map.getCols()+1,0,frame.getContentPane(),"MapPictures//CornerUpRight.png");
        //j==1...map.getRows
        for (int j = 1; j < map.getRows()+1; j++) {
            picture = new MyPicture(0,j,frame.getContentPane(),"MapPictures//BorderLeft0.png");
            for (int i = 1; i < map.getCols()+1; i++)
                picture = new MyPicture(i,j,frame.getContentPane(),"MapPictures//Empty"+map.getCoefficients()[j-1][i-1]+".png");
            picture = new MyPicture(map.getCols()+1,j,frame.getContentPane(),"MapPictures//BorderRight0.png");
        }
        //j==map.getRows+1
        picture = new MyPicture(0,map.getRows()+1,frame.getContentPane(),"MapPictures//CornerDownLeft.png");
        for (int i = 1; i < map.getCols()+1; i++)
            picture = new MyPicture(i,map.getRows()+1,frame.getContentPane(),"MapPictures//BorderDown0.png");
        picture = new MyPicture(map.getCols()+1,map.getRows()+1,frame.getContentPane(),"MapPictures//CornerDownRight.png");


        frame.setVisible(true);
    }


    public static void main(String[] args) {
       // MapWindow form1 = new MapWindow();
    }


}
