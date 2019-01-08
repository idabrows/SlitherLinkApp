package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


class HelpWindow {

    public HelpWindow() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JTextArea textArea = new JTextArea();
                StringBuilder rulestext = new StringBuilder();
                try {
                    FileReader fileReader = new FileReader("HelpFiles//Rules");
                    int data = fileReader.read();
                    while(data != -1) {
                        rulestext.append((char) data);
                        data = fileReader.read();
                    }
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textArea.setText(rulestext.toString());
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(textArea);
                JFrame frame = new JFrame("SlitherLink - Help");
                frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                frame.setSize(new Dimension(300, 350));
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
