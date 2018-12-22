package view.Components;

import java.awt.*;
import javax.swing.*;



public class MyButton {
    final static boolean shouldFill = true;
    private JButton button;

    public MyButton(String s,int x,int y,Container pane){
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        button = new JButton(s);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = x;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = y;       //third row
        pane.add(button, c);



    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Slitherlink");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        //Set up the content pane.
        MyButton myButton1 = new MyButton("BUTTON1",0,2,frame.getContentPane());
        MyButton myButton2 = new MyButton("BUTTON2",1,2,frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

                createAndShowGUI();

    }
}
