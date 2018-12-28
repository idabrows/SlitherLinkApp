package controller;

import model.Map;
import view.Components.MyTable;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class MyFileReader {

    private final JFileChooser FileChooser;
    private static MyFileReader f;
    private MyFileReader(){
        FileChooser=new JFileChooser();
    }
    public static MyFileReader getInstance(){
        if(f==null){
            f=new MyFileReader();
        }
        return f;
    }

    public File getUsersFile(Component parent){
        int flag=FileChooser.showOpenDialog(parent);
        if(flag==JFileChooser.APPROVE_OPTION){
            File file=FileChooser.getSelectedFile();
            return file;
        }
        return null;
    }

    public Map getUsersMap(File file) throws IOException {
        Map map=new Map();
        map.readMapFromFile(file);
        return map;
    }

    public JTable getUsersTable(File file) throws IOException {
        Map m = new Map();
        m.readMapFromFile(file);
        ViewWorker viewWorker = new ViewWorker();
        JTable table = viewWorker.getTable(m);
        return table;
    }



}
