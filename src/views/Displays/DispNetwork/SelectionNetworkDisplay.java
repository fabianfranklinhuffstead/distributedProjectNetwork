package views.Displays.DispNetwork;

import javax.swing.*;

import controllers.GlobalResources;

import java.awt.*;

// selection network display
class SelectionNetworkDisplay extends JPanel {

	// Displays network IP
    private final JTextArea _NetworkIP = new JTextArea(" ", 1, 1);

    // Port selection
    private final JComboBox<Integer> _Portnumbers = new JComboBox<>(GlobalResources.NETWORKCONTROLPANEL_Ports);

    // Start/stop button 
    private final JButton _NetworkToggleButton = new JButton(GlobalResources.NETWORKREPORTSCREEN_NetworkGoContent);

    // Selection network display interface with start, stop, ports, IP host name and messages
    public SelectionNetworkDisplay() {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
        setSize(450, 80);

        //Title
        JLabel _MainTitle = new JLabel(GlobalResources.NETWORKCONTROLPANEL_NetworkLauncherTitle);
        _MainTitle.setLocation(0, 5);
        _MainTitle.setSize(450, 30);
        _MainTitle.setFont(GlobalResources.NETWORKREPORTSCREEN_Heading);
        _MainTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //NetworkServer main title
        JLabel _NetworkIPTitle = new JLabel(GlobalResources.NETWORKCONTROLPANEL_WorkstationIPName);
        _NetworkIPTitle.setLocation(10, 35);
        _NetworkIPTitle.setSize(150, 20);
        _NetworkIPTitle.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //NetworkIP main title
        _NetworkIP.setLocation(10, 55);
        _NetworkIP.setSize(150, 20);
        _NetworkIP.setEditable(false);
        _NetworkIP.setBackground(new Color(225, 225, 225));
        _NetworkIP.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //Portsnumber main title
        JLabel _Portsnumbers = new JLabel(GlobalResources.NETWORKCONTROLPANEL_PortsTitle);
        _Portsnumbers.setLocation(170, 35);
        _Portsnumbers.setSize(80, 20);
        _Portsnumbers.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //Port selection combo box
        _Portnumbers.setLocation(170, 55);
        _Portnumbers.setSize(80, 20);
        _Portnumbers.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //Network toggle button settings
        _NetworkToggleButton.setLocation(275, 55);
        _NetworkToggleButton.setSize(150, 20);
        _NetworkToggleButton.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);
        _NetworkToggleButton.setBackground(new Color(255, 255, 255));

        //Adds all components to panel
        add(_MainTitle);
        add(_NetworkIP);
        add(_NetworkIPTitle);
        add(_Portnumbers);
        add(_Portsnumbers);
        add(_NetworkToggleButton);

        setLocation(0, 0);
        setVisible(true);
    }

    // Adds the event listener and returns the stop/start toggle network button
    public JButton AddNetworkToggleButton(NetworkDisplay eventHandler) {
        _NetworkToggleButton.addActionListener(eventHandler);
        return _NetworkToggleButton;
    }

    // Returns selected port number
    public int GetSelectedPortnumber() {
        return (int) _Portnumbers.getSelectedItem();
    }

    // Set text to network IP
    public void SetNetworkAddress(String networkip) {
        _NetworkIP.setText(networkip);
    }
}
