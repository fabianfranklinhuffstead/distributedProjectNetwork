package views.Displays.DispNetwork;

import javax.swing.*;

import controllers.GlobalResources;

import java.awt.*;

// Text area which displays console log
public class NetworkConsoleDisplay extends JTextArea {

    // For the network display
    public NetworkConsoleDisplay() {
        setBackground(Color.black);
        setForeground(Color.WHITE);
        setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);
        setEditable(false);
        setLineWrap(true);
        setWrapStyleWord(true);

    }

    // Console log messages
    public void LogMessages(String message) {
        append(message);
        append("\n");
    }

    // Resets current console log
    public void ResetLog() {
        setText("");
    }
}
