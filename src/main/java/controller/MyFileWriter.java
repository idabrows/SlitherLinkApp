package controller;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFileWriter {
    private static MyFileWriter f;

    private MyFileWriter(){
        new JFileChooser();
    }

    static MyFileWriter getInstance(){
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
            if (!filename.endsWith(".txt"))
                filename += ".txt";
            return new File(filename);
        }
        return null;
    }

}

