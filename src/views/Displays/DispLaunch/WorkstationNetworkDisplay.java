package views.Displays.DispLaunch;

import javax.swing.*;

import controllers.GlobalResources;

import java.awt.*;

// WorkstationNetworkDisplay lets user interact with the network settings
public class WorkstationNetworkDisplay extends JPanel {

	// IP Address entry
    private final JTextField _MainServerIPText = new JTextField(GlobalResources.WORKSTATIONCONFIGURATION_IPDefault);

    // Port entry
    private final JComboBox<Integer> _PortNumbers = new JComboBox<>(GlobalResources.NETWORKCONTROLPANEL_Ports);

    // Workstation Network display set up  
    public WorkstationNetworkDisplay() {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
        setSize(300, 80);

        //Main title settings
        JLabel _MainNetworkTitle = new JLabel(GlobalResources.WORKSTATIONCONFIGURATION_Title);
        _MainNetworkTitle.setLocation(0, 5);
        _MainNetworkTitle.setSize(300, 20);
        _MainNetworkTitle.setFont(GlobalResources.ATTRIBUTESFORBOAT_FontStylingHeader);
        _MainNetworkTitle.setHorizontalAlignment(SwingConstants.CENTER);
        _MainNetworkTitle.setBackground(GlobalResources.ATTRIBUTESFORBOAT_ColorHeader);
        _MainNetworkTitle.setOpaque(true);


        //NetworkServer IP title settings
        JLabel _MainIPAdressTitle = new JLabel(GlobalResources.WORKSTATIONCONFIGURATION__IPTitle);
        _MainIPAdressTitle.setLocation(10, 35);
        _MainIPAdressTitle.setSize(180, 20);
        _MainIPAdressTitle.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //NetworkServer IP text settings
        _MainServerIPText.setLocation(10, 55);
        _MainServerIPText.setSize(180, 20);
        _MainServerIPText.setEditable(true);
        _MainServerIPText.setBackground(new Color(225, 225, 225));
        _MainServerIPText.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //Ports title label settings
        JLabel _MainPortTitle = new JLabel(GlobalResources.WORKSTATIONCONFIGURATION_PortTitle);
        _MainPortTitle.setLocation(200, 35);
        _MainPortTitle.setSize(80, 20);
        _MainPortTitle.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //Ports combobox settings
        _PortNumbers.setLocation(200, 55);
        _PortNumbers.setSize(80, 20);
        _PortNumbers.setFont(GlobalResources.NETWORKREPORTSCREEN_FontNormal);

        //Adding components to the panel
        add(_MainNetworkTitle);
        add(_MainServerIPText);
        add(_MainIPAdressTitle);
        add(_PortNumbers);
        add(_MainPortTitle);

        setLocation(0, 0);
        setVisible(true);
    }

    // Returns port number selected
    public int GetPortNumber() {
        return (int) _PortNumbers.getSelectedItem();
    }

    // Returns main server ip text
    public String GetMainSeverIPText() {
        return _MainServerIPText.getText();
    }


}
