package controller;

import model.Map;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class MyFileReader {
    private final JFileChooser FileChooser;
    private static MyFileReader f;

    private MyFileReader(){
        FileChooser=new JFileChooser();
    }

    static MyFileReader getInstance(){
        if(f==null){
            f=new MyFileReader();
        }
        return f;
    }

    public File getUsersFile(Component parent){
        int flag=FileChooser.showOpenDialog(parent);
        if(flag==JFileChooser.APPROVE_OPTION){
            return FileChooser.getSelectedFile();
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
        return viewWorker.getTable(m);
    }

}
