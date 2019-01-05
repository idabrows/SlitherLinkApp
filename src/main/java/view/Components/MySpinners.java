package view.Components;

import javax.swing.*;
import java.awt.*;


public class MySpinners {
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JLabel label1;
    private JLabel label2;


    public MySpinners(Container pane){
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();
        GridBagConstraints c4 = new GridBagConstraints();


        SpinnerModel model1 =
                new SpinnerNumberModel(1, //initial value
                        1,  //min
                        14, //max
                        1);   //step
        SpinnerModel model2 =
                new SpinnerNumberModel(1, //initial value
                        1, //min
                        10, //max
                        1);  //step


        spinner1 = new JSpinner(model1);
        spinner2 = new JSpinner(model2);
        label1 = new JLabel();
        label2 = new JLabel();

        label1.setText("Number of rows");
        label2.setText("Number of columns");
        ((JSpinner.DefaultEditor) spinner1.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spinner2.getEditor()).getTextField().setEditable(false);

        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.ipady = 0;       //reset to default
        c1.weighty = 2.0;   //request any extra vertical space
        c1.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c1.insets = new Insets(10,30,0,30);  //top padding
        c1.gridx = 2;       //aligned with button 2
        c1.gridwidth = 2;   //2 columns wide
        c1.gridy = 0;

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.ipady = 0;       //reset to default
        //c2.weighty = 1.0;   //request any extra vertical space
        c2.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c2.insets = new Insets(0,30,10,30);  //top padding
        c2.gridx = 2;       //aligned with button 2
        c2.gridwidth = 2;   //2 columns wide
        c2.gridy = 1;

        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.ipady = 0;       //reset to default
        c3.weighty = 2.0;   //request any extra vertical space
        c3.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c3.insets = new Insets(10,20,0,0);  //top padding
        c3.gridx = 0;       //aligned with button 2
        c3.gridwidth = 3;   //2 columns wide
        c3.gridy = 0;       //third row

        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.ipady = 0;       //reset to default
        //c2.weighty = 1.0;   //request any extra vertical space
        c4.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c4.insets = new Insets(0,20,10,0);  //top padding
        c4.gridx = 0;       //aligned with button 2
        c4.gridwidth = 3;   //3 columns wide
        c4.gridy = 1;       //first row


        pane.add(spinner1, c1);
        pane.add(spinner2, c2);
        pane.add(label1, c3);
        pane.add(label2, c4);

    }

    public JSpinner getSpinner1() {
        return spinner1;
    }

    public void setSpinner1(JSpinner spinner1) {
        this.spinner1 = spinner1;
    }

    public JSpinner getSpinner2() {
        return spinner2;
    }

    public void setSpinner2(JSpinner spinner2) {
        this.spinner2 = spinner2;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public void setLabel1(JLabel label1) {
        this.label1 = label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public void setLabel2(JLabel label2) {
        this.label2 = label2;
    }
}


