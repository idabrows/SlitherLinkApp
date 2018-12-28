package controller;

import model.Map;
import view.Components.MyTable;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFileWriter {
    private final JFileChooser FileChooser;
    private static MyFileWriter f;

    private MyFileWriter(){
        FileChooser=new JFileChooser();
    }

    public static MyFileWriter getInstance(){
        if(f==null){
            f=new MyFileWriter();
        }
        return f;
    }

    public File getUsersDir(Component parent){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt", "text");
        fileChooser.setFileFilter(filter);
        int flag=fileChooser.showSaveDialog(parent);
        if(flag==JFileChooser.APPROVE_OPTION){
            String filename = fileChooser.getSelectedFile().toString();
            if (!filename .endsWith(".txt"))
                filename += ".txt";
            return new File(filename);
        }

        return null;
    }







}

