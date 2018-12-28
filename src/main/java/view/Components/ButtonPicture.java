package view.Components;

import javax.swing.*;
import java.awt.*;



public class ButtonPicture {

    private JButton picture;

    final static boolean shouldFill = false;

        public ButtonPicture(int x, int y, Container pane){
            GridBagConstraints c = new GridBagConstraints();

            picture = new JButton(new ImageIcon("MapPictures//Empty.png"));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx=1;
            c.gridx = x;       //aligned with button 2
            c.gridy = y;       //third row
            pane.add(picture, c);


        }


        private static void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("Slitherlink");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setLayout(new GridBagLayout());
            //Set up the content pane.
            view.Components.ButtonPicture picture = new view.Components.ButtonPicture(0,0,frame.getContentPane());
            picture.picture.setIcon(new ImageIcon("Untitled.ico"));


            frame.setVisible(true);
        }

        public static void main(String[] args) {

            createAndShowGUI();

        }



}
