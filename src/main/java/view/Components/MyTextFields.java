package view.Components;

import javax.swing.*;
import java.awt.*;

import java.awt.*;
import javax.swing.*;



public class MyTextFields {
    private TextForm form;

    public MyTextFields(Container pane){
        GridBagConstraints c = new GridBagConstraints();

        String[] labels = { "Number of rows", "Number of columns"};
        char[] mnemonics = { 'R', 'C' };
        int[] widths = { 5, 5};
        String[] descs = { "number of rows of the new map",  "number of columns of the new map" };
        form = new TextForm(labels, mnemonics, widths, descs);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 3;   //2 columns wide
        c.gridy = 0;       //third row
        pane.add(form, c);
    }

    public TextForm getForm() {
        return form;
    }

    public void setForm(TextForm form) {
        this.form = form;
    }

}

