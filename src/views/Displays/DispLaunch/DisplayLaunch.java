package views.Displays.DispLaunch;

import controllers.GlobalResources;
import views.Displays.DispNetwork.SelectionNetworkConsoleDisplay;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Launch screen JPANEL for both map and boat
public class DisplayLaunch extends JPanel implements ActionListener
{

    // This is the background image 
    private final Image _ImageBackground;
    
    // Array list for boat selection panel.
    private SelectionBoatDisplay _SelectionBoatDisplay;
    
    // This is the map selection JPANEL for maps
    private SelectionMapDisplay _SelectionMapDisplay;
    
    // Main menu button
    private JButton _ButtonMainMenu;
   
    // Button for starting game
    private JButton _ButtonStartGame;

    // Network workstation displays
    private WorkstationNetworkDisplay _WorkstationNetworkDisplay;

    // Console message logging panel 
    private SelectionNetworkConsoleDisplay _SelectionNetworkConsoleDisplay;

    // Launch Screen with JPANEL functionality
    public DisplayLaunch (Image backgroundImage)
    {
        this.setLayout(null);
        _ImageBackground = backgroundImage;

        SetHandlerNetworkGameSession();

        AddReturnToMainButton();
        AddButtonForStartingGame();
        this.setVisible(true);
    }

    // Default values for selections game type and boat selections
    public void DefaultValuesSet() {
        _SelectionBoatDisplay.DefaultBoatSelectionDisplay();
        _SelectionMapDisplay.DefaultMapSelection();
        setVisible(true);
        repaint();
    }

    // The start game/Go button in given location and default settings (find in shared resources)
    private void AddButtonForStartingGame()
    {
        if(_ButtonStartGame == null)
        {
           _ButtonStartGame = new JButton(GlobalResources.PREPDISPLAYATTRIBUTES_GoButtonTitle);

            int location_x = _SelectionMapDisplay.getX();
           _ButtonStartGame.setLocation(location_x, GlobalResources.PREPDISPLAYATTRIBUTES_GoButtonYLocation);

            int width = _SelectionMapDisplay.getWidth();
            _ButtonStartGame.setSize(width,GlobalResources.PREPDISPLAYATTRIBUTES_GoButtonHeight);
            _ButtonStartGame.setBackground(GlobalResources.PREPDISPLAYATTRIBUTES_ColorGoButton);
            _ButtonStartGame.setFont(GlobalResources.PREPDISPLAYATTRIBUTES_FontStylingGoButton);

            _ButtonStartGame.setForeground(GlobalResources.PREPDISPLAYATTRIBUTES_ColorFrontGoButton);

            this.add(_ButtonStartGame);
            _ButtonStartGame.setVisible(true);
            _ButtonStartGame.addActionListener(this);
        }

    }

    // The return to main menu button in given location and default settings (find in shared resources)
    private void AddReturnToMainButton()
    {
        if(_ButtonMainMenu == null)
        {
            _ButtonMainMenu = new JButton(GlobalResources.PREPDISPLAYATTRIBUTES_MainMenuButton);
            _ButtonMainMenu.setLocation(GlobalResources.PREPDISPLAYATTRIBUTES_MainMenuXLocation,GlobalResources.PREPDISPLAYATTRIBUTES_MainMenuYLocation);
            _ButtonMainMenu.setSize(GlobalResources.PREPDISPLAYATTRIBUTES_WidthMainMenuButton,GlobalResources.PREPDISPLAYATTRIBUTES_HeightMainMenuButton);

            _ButtonMainMenu.setBackground(GlobalResources.PREPDISPLAYATTRIBUTES_ColorMainMenuButton);
            _ButtonMainMenu.setFont(GlobalResources.PREPDISPLAYATTRIBUTES_FontStylingMainMenuButton);

            this.add(_ButtonMainMenu);
            _ButtonMainMenu.setVisible(true);

            _ButtonMainMenu.addActionListener(this);
        }
    }

    // Draws background image and paints
    protected void  paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try
        {
            g.drawImage(_ImageBackground,0,0,this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Initiate the handler for the network game session when connecting clients
    private void SetHandlerNetworkGameSession()
    {
        // The SelectionboatDisplay
        _SelectionBoatDisplay = new SelectionBoatDisplay();
        this.add(_SelectionBoatDisplay);
        _SelectionBoatDisplay.setLocation(GlobalResources.ATTRIBUTESFORBOAT_Player1XLocation, GlobalResources.ATTRIBUTESFORBOAT_Player1YLocation);
        _SelectionBoatDisplay.setVisible(true);
        _SelectionBoatDisplay.DefaultBoatSelectionDisplay();

        // Keyboard label
        AssignKeyboard keyBoardLayout = new AssignKeyboard();
        int KeyboardHelp_Xaxis = GlobalResources.ATTRIBUTESFORBOAT_Player1XLocation + _SelectionBoatDisplay.getWidth() + GlobalResources.ATTRIBUTESFORHELP_SpacingXLocation;
        int KeyboardHelp_Yaxis = GlobalResources.ATTRIBUTESFORBOAT_Player1YLocation;
        keyBoardLayout.setLocation(KeyboardHelp_Xaxis,KeyboardHelp_Yaxis);
        this.add(keyBoardLayout);

        // Map selection panel
        SelectionMapDisplay selectionMapDisplay = new SelectionMapDisplay();
        int selectionMap_xAxis = KeyboardHelp_Xaxis + keyBoardLayout.getWidth() +  GlobalResources.ATTRIBUTESFORMAP_Spacing;
        selectionMapDisplay.setLocation(selectionMap_xAxis, KeyboardHelp_Yaxis);
        this.add(selectionMapDisplay);
        _SelectionMapDisplay = selectionMapDisplay;

        //Workstation console client/panel 
        _WorkstationNetworkDisplay = new WorkstationNetworkDisplay();
        int WorstationNetworkPanel_yAxis = GlobalResources.ATTRIBUTESFORBOAT_Player1YLocation + GlobalResources.PREPDISPLAYATTRIBUTES_NetworkSpaceBetween + _SelectionBoatDisplay.getHeight();
        _WorkstationNetworkDisplay.setLocation(GlobalResources.ATTRIBUTESFORBOAT_Player1XLocation, WorstationNetworkPanel_yAxis);
        this.add(_WorkstationNetworkDisplay);

        //Network console client/panel
        _SelectionNetworkConsoleDisplay = new SelectionNetworkConsoleDisplay(true);
        int ConsolePanel_yAxis = WorstationNetworkPanel_yAxis + _WorkstationNetworkDisplay.getHeight() + (GlobalResources.PREPDISPLAYATTRIBUTES_NetworkSpaceBetween / 2);
        _SelectionNetworkConsoleDisplay.setLocation(GlobalResources.ATTRIBUTESFORBOAT_Player1XLocation, ConsolePanel_yAxis);
        this.add(_SelectionNetworkConsoleDisplay);
    }


    //Returns SelectionNetworkConsoleDisplay
    public SelectionNetworkConsoleDisplay get_SelectionNetworkConsoleDisplay() {
        return _SelectionNetworkConsoleDisplay;
    }

    	// Returns WorkstationNetworkDisplay
    public WorkstationNetworkDisplay WorkstationNetworkDisplay() {
        return _WorkstationNetworkDisplay;
    }

    // Start game button and return to main menu override events
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // Return to menu
        if(e.getSource() == _ButtonMainMenu)
        {
            GlobalResources.MainControl.NavigatingBackToMenuMainDisplay();
        }

        // Start game
        if(e.getSource() == _ButtonStartGame)
        {
            GlobalResources.MainControl.LaunchGame();
        }
    }
}
