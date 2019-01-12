package view;

import java.awt.Dimension;
import javax.swing.*;


class HelpWindow extends JFrame{

    HelpWindow() {
        JEditorPane editor = new JEditorPane();
        JScrollPane jScrollPane;
        editor.setContentType("text/html");
        editor.setText(
                "<font size=\"24\">Game rules</font><br>" +
                "<ol><li>Connect adjacent dots with vertical or horizontal lines to make a single loop. " +
                    "<li> The numbers indicate how many lines surround it, while empty cells may be surrounded by any number of lines. " +
                    "<li>The loop never crosses itself and never branches off. </ol><br>" +
                     "<div style=\"text-indent: 0.5cm\">  <font size=\"+2\">Example:</font><br><br></div>" +
                "<img src=\"file:resources/HelpFiles/help1.png\" hspace=\"20\"></img>" +
                "<img src=\"file:resources/HelpFiles/help2.png\"></img>");
        editor.setEditable(false);
        JFrame frame;
        jScrollPane = new JScrollPane(editor);
        frame=new JFrame("Slitherlink - Help");
        frame.add(jScrollPane);
        frame.setSize(new Dimension(400,500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


