package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;


public class JScrollPaneDemo
{
    public static void main(String[] args)
    {
        new JScrollPaneDemo();
    }

    public JScrollPaneDemo()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                // create a jtextarea
                JTextArea textArea = new JTextArea();

                // add text to it; we want to make it scroll
                String rulestext = "";
                try {
                    FileReader fileReader = new FileReader("HelpFiles//Rules");
                    int data = fileReader.read();
                    while(data != -1) {
                        rulestext=rulestext+(char)data;
                        data = fileReader.read();
                         }
                    fileReader.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textArea.setText(rulestext);
                textArea.setLineWrap(true);
                // create a scrollpane, givin it the textarea as a constructor argument
                JScrollPane scrollPane = new JScrollPane(textArea);

                // now add the scrollpane to the jframe's content pane, specifically
                // placing it in the center of the jframe's borderlayout
                JFrame frame = new JFrame("SlitherLink - Help");
                frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
                textArea.setEditable(false);
                // make it easy to close the application

                // set the frame size (you'll usually want to call frame.pack())
                frame.setSize(new Dimension(300, 350));

                // center the frame
                frame.setLocationRelativeTo(null);

                // make it visible to the user
                frame.setVisible(true);
            }
        });
    }
}
