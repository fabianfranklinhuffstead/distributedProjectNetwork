package views.Displays.DispNetwork;

import javax.swing.*;

import controllers.Network;
import controllers.GlobalResources;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// View for network display
public class NetworkDisplay extends JPanel implements ActionListener {


    // Background image
    private final Image _BackgroundImage;

    // Selection network console display new set to false
    private final SelectionNetworkConsoleDisplay _SelectionNetworkConsoleDisplay = new SelectionNetworkConsoleDisplay(false);

    // Main menu button to main menu display
    private final JButton _MainMenuButton = new JButton(GlobalResources.NETWORKREPORTSCREEN_MainMenu);

    // Selection network main display with all components  
    private final SelectionNetworkDisplay _SelectionNetworkDisplay = new SelectionNetworkDisplay();

    //Pass when user interface is interacted with
    private final Network _Network;

    	//Start network button
    private final JButton _StartNetwork;


    //Network display with background image
    public NetworkDisplay(Image backgroundImage, Network eventHandler) {
        this.setLayout(null);
        _BackgroundImage = backgroundImage;
        _Network = eventHandler;

        //Selection network display added
        add(_SelectionNetworkDisplay);
        _SelectionNetworkDisplay.setLocation(200, 10);
        _SelectionNetworkDisplay.setVisible(true);
        _StartNetwork = _SelectionNetworkDisplay.AddNetworkToggleButton(this);

        //Selection network console display added
        add(_SelectionNetworkConsoleDisplay);
        _SelectionNetworkConsoleDisplay.setLocation(10, 100);
        _SelectionNetworkConsoleDisplay.setVisible(true);

        //Main menu button added
        add(_MainMenuButton);
        _MainMenuButton.setSize(300, 30);
        _MainMenuButton.setLocation(275, 560);
        _MainMenuButton.setBackground(Color.WHITE);
        _MainMenuButton.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);
        _MainMenuButton.setVisible(true);
        _MainMenuButton.addActionListener(this);

        setVisible(true);
    }

    // draws image background
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(_BackgroundImage, 0, 0, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // returns selection network console display
    public NetworkConsoleDisplay GetMessageFromConsole() {
        return _SelectionNetworkConsoleDisplay.get_NetworkConsoleDisplay();
    }


    // Gets the port number which is selected
    public int GetPortSelected() {
        return _SelectionNetworkDisplay.GetSelectedPortnumber();
    }

    // Resets terminal
    public void ResetConsoleTerminalView() {
        _SelectionNetworkConsoleDisplay.get_NetworkConsoleDisplay().ResetLog();
    }

    // Toggles between button toggle text
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _StartNetwork) {
            _Network.NetworkToggleButton();
            return;
        }

        if (e.getSource() == _MainMenuButton) {
            _Network.NetworkMainMenuButtonToggle();
        }
    }


    // Toggle view for network button
    public void SetNetworkGoButton(boolean isNetworkGoButton) {
        if (isNetworkGoButton)
            _StartNetwork.setText(GlobalResources.NETWORKREPORTSCREEN_KillNetwork);
        else
            _StartNetwork.setText(GlobalResources.NETWORKREPORTSCREEN_NetworkGoContent);

    }

    // Set IP address to network address
    public void SetIPAddress(String ipaddress) {
        _SelectionNetworkDisplay.SetNetworkAddress(ipaddress);
    }
}
