package controllers;

import models.Server.NetworkServer;
import views.CoreSwingUIComponent;

// Network class which starts and stops the current runtime server
public class Network {

	//  Creation of the JFRAME which loads of JPANELS (Network)
    private final CoreSwingUIComponent _CoreSwingUIComponent;

    // Network functionality
    private NetworkServer _NetworkServer;

    // Constructor for Network engine UICore for JFFRAME and CoreSwingUIComponent
    public Network(CoreSwingUIComponent UIComponent) {
        _CoreSwingUIComponent = UIComponent;
    }


    /**
     * Asks the top level JFrame to load the the server configuration screen.
     */
    public void NetworkServerConfigLaunch() {
        _CoreSwingUIComponent.NetworkNavigationDisplay(this);
        _CoreSwingUIComponent.get_NetworkDisplay().ResetConsoleTerminalView();

        NetworkInstantiation();
        _CoreSwingUIComponent.get_NetworkDisplay().SetIPAddress(_NetworkServer.GetHostNameAddress());
    }


    /**
     * Instantiates the NetworkServer (but does not launch it).
     */
    private void NetworkInstantiation() {
        //Instantiating the server if needed
        if (_NetworkServer == null) {
            _NetworkServer = new NetworkServer(_CoreSwingUIComponent.get_NetworkDisplay().GetMessageFromConsole(), this);
        }
    }

    // Runs or stops the Network
    public void NetworkToggleButton() {

        if (!_NetworkServer.IsNetworkSessionCurrent()) {
            _NetworkServer.NetworkStart(_CoreSwingUIComponent.get_NetworkDisplay().GetPortSelected());
        } else {
            _NetworkServer.KillNetwork();
        }
    }

    //Closes Network if it is currently still in session
    public void NetworkClose() {
        if (_NetworkServer != null) {
            if (_NetworkServer.IsNetworkSessionCurrent()) {
                _NetworkServer.KillNetwork();
            }
        }

        _CoreSwingUIComponent.get_NetworkDisplay().SetNetworkGoButton(false);
    }

    // Closes Network when client returns to workstation mode
    public void NetworkMainMenuButtonToggle() {
        NetworkClose();
        GlobalResources.MainControl.NavigatingBackToMenuMainDisplay();
    }

    // Sets button true start button when Network is running 
    public void NetworkIsRunning() {
        _CoreSwingUIComponent.get_NetworkDisplay().SetNetworkGoButton(true);
    }

    // Sets button false start button when Network is running
    public void NetworkIsNotActive() {
        _CoreSwingUIComponent.get_NetworkDisplay().SetNetworkGoButton(false);
    }
}
