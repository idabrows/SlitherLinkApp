package view.Components;

import javax.swing.*;
import java.awt.*;


public class MySpinners {
    private final JSpinner spinner1;
    private final JSpinner spinner2;
    private final JLabel label1;
    private final JLabel label2;


    public MySpinners(Container pane) {
        GridBagConstraints c1 = new GridBagConstraints();
        GridBagConstraints c2 = new GridBagConstraints();
        GridBagConstraints c3 = new GridBagConstraints();
        GridBagConstraints c4 = new GridBagConstraints();

        SpinnerModel model1 = new SpinnerNumberModel(1, 1, 14, 1);
        SpinnerModel model2 = new SpinnerNumberModel(1, 1, 10,1);

        spinner1 = new JSpinner(model1);
        spinner2 = new JSpinner(model2);
        label1 = new JLabel();
        label2 = new JLabel();

        label1.setText("Number of rows");
        label2.setText("Number of columns");

        ((JSpinner.DefaultEditor) spinner1.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spinner2.getEditor()).getTextField().setEditable(false);

        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.ipady = 0;
        c1.weighty = 2.0;
        c1.anchor = GridBagConstraints.CENTER;
        c1.insets = new Insets(55,30,0,30);
        c1.gridx = 2;
        c1.gridwidth = 2;
        c1.gridy = 0;

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.ipady = 0;
        c2.anchor = GridBagConstraints.CENTER;
        c2.insets = new Insets(0,30,55,30);
        c2.gridx = 2;
        c2.gridwidth = 2;
        c2.gridy = 1;

        c3.fill = GridBagConstraints.HORIZONTAL;
        c3.ipady = 0;
        c3.weighty = 2.0;
        c3.anchor = GridBagConstraints.CENTER;
        c3.insets = new Insets(55,20,0,0);
        c3.gridx = 0;
        c3.gridwidth = 3;
        c3.gridy = 0;

        c4.fill = GridBagConstraints.HORIZONTAL;
        c4.ipady = 0;
        c4.anchor = GridBagConstraints.CENTER;
        c4.insets = new Insets(0,20,55,0);
        c4.gridx = 0;
        c4.gridwidth = 3;
        c4.gridy = 1;

        pane.add(spinner1, c1);
        pane.add(spinner2, c2);
        pane.add(label1, c3);
        pane.add(label2, c4);
    }

    public JSpinner getSpinner1() {
        return spinner1;
    }

    public JSpinner getSpinner2() {
        return spinner2;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

}