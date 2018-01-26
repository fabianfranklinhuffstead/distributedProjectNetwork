package views.Displays.DispNetwork;


import javax.swing.*;

import controllers.GlobalResources;

import java.awt.*;


// Selection Network console display
public class SelectionNetworkConsoleDisplay extends JPanel {

    // Title
    private final JLabel _TitleConsole = new JLabel();

    // New console display
    private final NetworkConsoleDisplay _NetworkConsoleDisplay = new NetworkConsoleDisplay();
    
    // Allows scroll
    private JScrollPane _ScrolloverConsole = null;

    // Displays network console display size of workstation
    public SelectionNetworkConsoleDisplay(boolean WorkstationSize) {
        setLayout(null);
        setBackground(new Color(240, 240, 240));


        _TitleConsole.setLocation(0, 10);
        _TitleConsole.setHorizontalAlignment(SwingConstants.CENTER);
        _TitleConsole.setFont(GlobalResources.NETWORKREPORTSCREEN_Heading);

        if (!WorkstationSize) {
            _TitleConsole.setText(GlobalResources.NETWORKCONSOLEDISPLAY_Title);
            ConfigureNetworkDisplaySize();
        } else {
            _TitleConsole.setText(GlobalResources.WORKSTATIONCONSOLEDISPLAY_Title);
            ConfigureLaunchScreenSize();
        }
        add(_TitleConsole);
        add(_ScrolloverConsole);
        setLocation(0, 0);
        setVisible(true);
    }

    // Configuration network screen size
    private void ConfigureNetworkDisplaySize() {
        setSize(830, 450);
        _TitleConsole.setSize(830, 30);

        _NetworkConsoleDisplay.setSize(810, 10000);
        _NetworkConsoleDisplay.setLocation(10, 40);
        _NetworkConsoleDisplay.setPreferredSize(new Dimension(810, 6000));

        _ScrolloverConsole = new JScrollPane(_NetworkConsoleDisplay);
        _ScrolloverConsole.setSize(810, 400);
        _ScrolloverConsole.setLocation(10, 40);
        _ScrolloverConsole.setPreferredSize(new Dimension(830, 6000));
        _ScrolloverConsole.setVisible(true);
    }

    /**
     * Sets the size and header for DisplayLaunch use
     */
    private void ConfigureLaunchScreenSize() {
        setSize(560, 250);
        _TitleConsole.setSize(560, 20);
        _TitleConsole.setFont(GlobalResources.ATTRIBUTESFORBOAT_FontStylingHeader);


        _NetworkConsoleDisplay.setSize(540, 1000);
        _NetworkConsoleDisplay.setLocation(10, 30);
        _NetworkConsoleDisplay.setPreferredSize(new Dimension(540, 1000));

        _ScrolloverConsole = new JScrollPane(_NetworkConsoleDisplay);
        _ScrolloverConsole.setSize(540, 210);
        _ScrolloverConsole.setLocation(10, 30);
        _ScrolloverConsole.setPreferredSize(new Dimension(540, 1000));
        _ScrolloverConsole.setVisible(true);

    }

    // Returns network console display
    public NetworkConsoleDisplay get_NetworkConsoleDisplay() {
        return _NetworkConsoleDisplay;
    }


}
