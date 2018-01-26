package views;


import controllers.Network;
import models.LoadingFiles.FileLoaderImage;
import views.Displays.DispGame.GameDisplay;
import views.Displays.DispLaunch.DisplayLaunch;
import views.Displays.DispMainMenu.MenuMainDisplay;
import views.Displays.DispNetwork.NetworkDisplay;
import controllers.GlobalResources;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

//SWINGUI Core is a top level instance of the graphic element of the application. It extends the JFRAME
@SuppressWarnings("serial")
public class CoreSwingUIComponent extends JFrame implements ActionListener {

    // This is the JFRAME which displays the content
    private final Container _ContainerPane;
    // This is the background image for the menu and launch
    private final Image _BackgroundImageForMenus;
    // This array contains the JPANELS
    private final ArrayList<JPanel> _Displays;
    // This has is the main menu JPANEL components
    private MenuMainDisplay _MenuMainDisplay;
    // This JPANEL is for the game configuration display
    private DisplayLaunch _DisplayLaunch;
    // This is in game JPANEL with boats and race track
    private GameDisplay _GameDisplay;
    // This is in game Network Display 
    private NetworkDisplay _NetworkDisplay;
    // This is NEW GAME menu button
    private JMenuItem _ButtonNewGame;
    // This is the HELP and ABOUT button
    private JMenuItem _ButtonAbout;
    // This is the help controls menu
    private JMenuItem _ButtonHowToPlay;
    // This is the help collision button
    private JMenuItem _ButtonGameInfo;
    // This is for the settings and audio
    private JCheckBoxMenuItem _ButtonToggleAudio;
    // This is for the settings and music
    private JCheckBoxMenuItem _ButtonToggleMusic;
    // This is for the settings for map textures
    private JCheckBoxMenuItem _ButtonToggleHighResLowRes;

    // Public SWING UI Core
    public CoreSwingUIComponent()
    {
        //Initiates the menu bar
    		MenuBarInstantiation();
        setTitle(GlobalResources.MAINAPPLICATIONTITLE);
        setResizable(false);
        setBounds(0,0, GlobalResources.MAINWINDOW_Width, GlobalResources.MAINWINDOW_Height);
        //Positioning for the display's window centre.
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _ContainerPane = getContentPane();
        _Displays = new ArrayList<>();
        _BackgroundImageForMenus = FileLoaderImage.ImageForBackground();

        //Listen to window events
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GlobalResources.MainControl.TryShutdownNetworkClient();
                super.windowClosing(e);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                if (GlobalResources.MainControl.get_RuntimeEngine() != null)
                    GlobalResources.MainControl.get_RuntimeEngine().RemoteForWhenWindowsInactive();
                super.windowDeactivated(e);
            }
        });
    }

    // This creates a default window and sets visible to true
    public void DefaultWindowCreation()
    {
        this.setVisible(true);
    }

    // This displays the main menu and JPANELs
    public void NavigateToMenuMainDisplay()
    {
        HideAllDisplays();
        if(_MenuMainDisplay == null)
        {
            _MenuMainDisplay = new MenuMainDisplay(_BackgroundImageForMenus);
            _Displays.add(_MenuMainDisplay);
            _ContainerPane.add(_MenuMainDisplay);
        }
        _MenuMainDisplay.setVisible(true);
        this.setVisible(true);
    }

    // This is the initiator for the menu bar
    private void MenuBarInstantiation()
    {
        JMenuBar _GameOptionHeader = new JMenuBar();

        //This creates main menu
        JMenu GameOptionHeader = new JMenu(GlobalResources.OPTIONMENU_GameTitle);
        JMenu howToMenu = new JMenu(GlobalResources.OPTIONMENU_InfoTitle);
        JMenu OptionsMenu = new JMenu(GlobalResources.OPTIONMENU_SettingsTitle);
        _GameOptionHeader.add(GameOptionHeader);
        _GameOptionHeader.add(OptionsMenu);
        _GameOptionHeader.add(howToMenu);

        //This creates the sub menu
        _ButtonNewGame = new JMenuItem(GlobalResources.OPTIONMENU_StartNewGameTitle);
        GameOptionHeader.add(_ButtonNewGame);

        //This is for sub menu help
        _ButtonAbout = new JMenuItem(GlobalResources.OPTIONMENU_AboutTitle);
        _ButtonHowToPlay = new JMenuItem(GlobalResources.OPTIONMENU_HowToPlayTitle);
        _ButtonGameInfo = new JMenuItem(GlobalResources.OPTIONMENU_GameInfoTitle);
        howToMenu.add(_ButtonAbout);
        howToMenu.add(_ButtonHowToPlay);
        howToMenu.add(_ButtonGameInfo);

        //This is for the sub menu settingss
        _ButtonToggleMusic = new JCheckBoxMenuItem(GlobalResources.OPTIONMENU_MusicTitle);
        _ButtonToggleMusic.setState(GlobalResources.ATTRIBUTESFORDEFAULT_MusicState);
        _ButtonToggleAudio = new JCheckBoxMenuItem(GlobalResources.OPTIONMENU_AudioTitle);
        _ButtonToggleAudio.setState(GlobalResources.ATTRIBUTESFORDEFAULT_SoundState);
        _ButtonToggleHighResLowRes = new JCheckBoxMenuItem(GlobalResources.OPTIONMENU_HighResLowResTitle);
        _ButtonToggleHighResLowRes.setState(GlobalResources.ATTRIBUTESFORDEFAULT_HighResLowResState);
        OptionsMenu.add(_ButtonToggleMusic);
        OptionsMenu.add(_ButtonToggleAudio);
        OptionsMenu.add(_ButtonToggleHighResLowRes);

        //This adds an event listener to this selected buttons
        _ButtonNewGame.addActionListener(this);
        _ButtonAbout.addActionListener(this);
        _ButtonHowToPlay.addActionListener(this);
        _ButtonGameInfo.addActionListener(this);
        _ButtonToggleMusic.addActionListener(this);
        _ButtonToggleAudio.addActionListener(this);
        _ButtonToggleHighResLowRes.addActionListener(this);

        this.setJMenuBar(_GameOptionHeader);
    }

    // This sets hides the displays as set to false
    private void HideAllDisplays()
    {
        _Displays.stream().forEach(s -> s.setVisible(false));
    }

    //  This displays the launch display as navigated to
    public void NavigateToLaunchDisplay()
    {
        HideAllDisplays();
        if (_DisplayLaunch == null)
        {
            _DisplayLaunch = new DisplayLaunch(_BackgroundImageForMenus);
            _Displays.add(_DisplayLaunch);
        }


        _DisplayLaunch.get_SelectionNetworkConsoleDisplay().get_NetworkConsoleDisplay().ResetLog();
        _ContainerPane.add(_DisplayLaunch);
        this.setVisible(true);
        setVisible(true);
        repaint();
    }

    // This sets default values for boats and maps
    public void SetDefaultValsOnLaunchDisplay() {
        _DisplayLaunch.DefaultValuesSet();
        setVisible(true);
        repaint();
    }

    // This displays Network Navigation, Messages, the controller manages usability
    public void NetworkNavigationDisplay(Network eventHandler) {
        HideAllDisplays();
        if (_NetworkDisplay == null) {
            _NetworkDisplay = new NetworkDisplay(_BackgroundImageForMenus, eventHandler);
            _Displays.add(_NetworkDisplay);
        }

        _NetworkDisplay.setVisible(true);
        _ContainerPane.add(_NetworkDisplay);
        this.setVisible(true);

    }

    // This adds JPANELS to main display for example boats and maps
    public void InGameDisplayNavigation()
    {
        HideAllDisplays();
        if (_Displays.contains(_GameDisplay))
            _Displays.remove(_GameDisplay);

        _GameDisplay = new GameDisplay();
        _Displays.add(_GameDisplay);

        _ContainerPane.add(_GameDisplay);
        this.setVisible(true);
        _GameDisplay.grabFocus();
    }

    // This updates the in game display as long as it is not set to null.
    public void UpdateInGameDisplay()
    {
        if(_GameDisplay != null)
            _GameDisplay.IterateFrame();
    }

    //Pop up game complete
    public void PopupGameComplete()
    {
        JOptionPane.showMessageDialog(this, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedContent, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedTitle, JOptionPane.PLAIN_MESSAGE);
    }

    //Pop up game complete error message
    public void PopupGameCompleteErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedError + errorMessage, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedTitle, JOptionPane.PLAIN_MESSAGE);
    }

    //Pop up game complete when other player DCs/Disconnects
    public void PopupGameCompletePlayerDC() {
        JOptionPane.showMessageDialog(this, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedErrorOpponentMissing, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedTitle, JOptionPane.PLAIN_MESSAGE);
    }

    //Pop up game complete error message Network error
    public void PopupMessageNetworkError() {
        JOptionPane.showMessageDialog(this, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedNetworkIssue, GlobalResources.ATTRIBUTESFORGAMEOVER_GameCompletedTitle, JOptionPane.PLAIN_MESSAGE);
    }


    // This is an action override event which happens which menu is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
    		//If else statement either return to menu or start new game.
        if(e.getSource() == _ButtonNewGame)
        {
            if(_GameDisplay != null)
            {
                _ContainerPane.remove(_GameDisplay);
                _Displays.remove(_GameDisplay);
                _GameDisplay = null;
            }

            GlobalResources.MainControl.NavigatingBackToMenuMainDisplay();
            return;
        }

        //This is the function for when the help button about
        if(e.getSource() == _ButtonAbout)
        {
            JOptionPane.showMessageDialog(this, GlobalResources.OPTIONMENU_InfoContent, GlobalResources.OPTIONMENU_InfoMapTitle,JOptionPane.PLAIN_MESSAGE);
            return;
        }

        //This is the function for when the help button controls
        if (e.getSource() == _ButtonHowToPlay)
        {
            JOptionPane.showMessageDialog(this, GlobalResources.OPTIONMENU_HowToPlayContent, GlobalResources.OPTIONMENU_AboutControlsTitle,JOptionPane.PLAIN_MESSAGE);
            return;
        }

        //This is the function for when the help button about collisions
        if (e.getSource() == _ButtonGameInfo) {
            JOptionPane.showMessageDialog(this, GlobalResources.OPTIONMENU_GameInfoContent, GlobalResources.OPTIONMENU_GameCollisionInfoTitle, JOptionPane.PLAIN_MESSAGE);
            return;
        }

        //This is the function for when the options button music
        if(e.getSource() == _ButtonToggleMusic)
        {
            GlobalResources.MainControl.BoatGameAudioMenuBar(_ButtonToggleMusic.getState());
            return;
        }

        //This is the function for when the options button audios
        if(e.getSource() == _ButtonToggleAudio)
        {
            GlobalResources.MainControl.BoatGameSoundMenuBar((_ButtonToggleAudio.getState()));
            return;
        }

        //This is the function for when the options button maps
        if(e.getSource() == _ButtonToggleHighResLowRes)
        {
            GlobalResources.MainControl.BoatGameMapTextureMenuBar(_ButtonToggleHighResLowRes.getState());
        }
    }

    // This method returns the in game display
    public GameDisplay get_InGameDisplay() {
        return _GameDisplay;
    }

    // This method returns the in Network display
    public NetworkDisplay get_NetworkDisplay() {
        return _NetworkDisplay;
    }

    // This method returns the in launch display
    public DisplayLaunch get_LaunchDisplay() {
        return _DisplayLaunch;
    }

}
