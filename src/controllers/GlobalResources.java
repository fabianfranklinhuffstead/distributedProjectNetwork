package controllers;

import java.awt.*;
import java.awt.event.KeyEvent;

import models.ManagementCollision.BoundsOfBoat;

//GLOBAL RESOURCES
//This is a resource for the entire application accessible via public type
public class GlobalResources {

	// Main application name
    public static final String MAINAPPLICATIONTITLE = "Boat Game";
    // First Players
    public static final int In_Game_Player = 1;
    // Second player
    public static final int Second_In_Game_Player = 2; 
    //Application set to 30 FPS 33.3 milliseconds converts to this value
    public static final int GAMEFRAMERATE = 33;
    //Main window width and height
    public static final int MAINWINDOW_Width = 850;
    public static final int MAINWINDOW_Height = 650;
    //Menu bar text and attributes
    public  static  final String OPTIONMENU_GameTitle = "-Game-";
    public  static  final String OPTIONMENU_StartNewGameTitle = "New Game";
    public  static  final String OPTIONMENU_SettingsTitle = "-Options-";
    public  static  final String OPTIONMENU_InfoTitle = "-Info-";
    public  static  final String OPTIONMENU_AboutTitle = "About";
    public  static  final String OPTIONMENU_HowToPlayTitle = "How to play";
    public static final String OPTIONMENU_GameInfoTitle = "Game Info";
    public  static  final String OPTIONMENU_MusicTitle = "Music";
    public  static  final String OPTIONMENU_AudioTitle = "Audio";
    //For map textures
    public static   final String OPTIONMENU_HighResLowResTitle = "High Resolution/Low Resolution";
    public  static  final String OPTIONMENU_InfoMapTitle = "Info";
    public  static  final String OPTIONMENU_AboutControlsTitle = "About";
    public static final String OPTIONMENU_GameCollisionInfoTitle = "Game Info";
    public static final String OPTIONMENU_InfoContent = "This game is a boat game which can be played with two players over the network for the DSP Assignment";
    public static final String OPTIONMENU_HowToPlayContent = "Remember when you select your boat you can view your boat you can simply see help controls beside your selector";
    public static final String OPTIONMENU_GameInfoContent = "If you collide with a rocks your speed is set to 0, if with a boat then the game ends";
    //Start menu attributes
    public static final String MAINSELECTIONMENU_NetworkConnection = "Network connection"; 
    public static final String MAINSELECTIONMENU_StartNewConnection = "Start new network connection";
    public  static  final String MAINSELECTIONMENU_QuitMenu = "Quit";
    public  static  final int MAINSELECTIONMENU_WidthMenu = 300;
    public static final int MAINSELECTIONMENU_HeightMenu = 150;
    public  static  final int MAINSELECTIONMENU_HeightMenuSettings = 50;
    public static final String MAINSELECTIONMENU_ImageBackground = "/mainImages/mainMenuBackground.png";
    public static   final Color MAINSELECTIONMENU_StartMenuBackGroundColor =  new Color(255, 255, 255, 255);
    public static   final Font MAINSELECTIONMENU_FontStylingButton = new Font("Times New Roman", Font.BOLD, 14);
    // Preparation display attributes
    public static final String PREPDISPLAYATTRIBUTES_MainMenuButton = "Main Menu";
    public static final String PREPDISPLAYATTRIBUTES_ControlText = "Help keys";
    public static final int PREPDISPLAYATTRIBUTES_MainMenuXLocation = 30;
    public static final int PREPDISPLAYATTRIBUTES_MainMenuYLocation = 570;
    public static final int PREPDISPLAYATTRIBUTES_WidthMainMenuButton = 150;
    public static final int PREPDISPLAYATTRIBUTES_HeightMainMenuButton = 30;
    public static final Color PREPDISPLAYATTRIBUTES_ColorMainMenuButton = new Color(255, 255, 0);
    public static final Font PREPDISPLAYATTRIBUTES_FontStylingMainMenuButton = new Font("Times New Roman", Font.BOLD, 14);
    public static final String PREPDISPLAYATTRIBUTES_GoButtonTitle = "GO";
    public static final int PREPDISPLAYATTRIBUTES_GoButtonYLocation = 540;
    public static final int PREPDISPLAYATTRIBUTES_GoButtonHeight = 60;
    public static final Color PREPDISPLAYATTRIBUTES_ColorGoButton = PREPDISPLAYATTRIBUTES_ColorMainMenuButton;
    public static final Font PREPDISPLAYATTRIBUTES_FontStylingGoButton = PREPDISPLAYATTRIBUTES_FontStylingMainMenuButton;
    public static final Color PREPDISPLAYATTRIBUTES_ColorFrontGoButton = new Color(0, 0, 0);
    public static final String PREPDISPLAYATTRIBUTES_ArrowLeft = "/mainImages/selectiondisplayimages/ArrowLeft.png";
    public static final String PREPDISPLAYATTRIBUTES_ArrowRight = "/mainImages/selectiondisplayimages/ArrowRight.png";
    public static final int PREPDISPLAYATTRIBUTES_HeightArrow = 20;
    public static final int PREPDISPLAYATTRIBUTES_WidthArrow = 12;
    public static final int PREPDISPLAYATTRIBUTES_NetworkSpaceBetween = 50;
    // boats selection attributes
    public static final int ATTRIBUTESFORBOAT_BoatChoiceAmount = 4;
    public static final String ATTRIBUTESFORBOAT_DefaultFileNamePrefix = "_boatChoice.png";
    public static final String ATTRIBUTESFORBOAT_DefaultFilePathPrefix = "/mainImages/selectiondisplayimages/";
    public static final String ATTRIBUTESFORBOAT_DefaultMessageOnNoImage = "Error";
    public static final int ATTRIBUTESFORBOAT_BoatSize = 50;
    public static final int ATTRIBUTESFORBOAT_SpaceForBoats = 45;
    public static final Color ATTRIBUTESFORBOAT_SpaceForBoatsColor = new Color(255,255,255,255);
    public static final int ATTRIBUTESFORBOAT_Player1XLocation = 30;
    public static final int ATTRIBUTESFORBOAT_Player1YLocation = 50;
    public static final int ATTRIBUTESFORBOAT_HeightHeader = 20;
    public static final Font ATTRIBUTESFORBOAT_FontStylingHeader = new Font("Times New Roman", Font.BOLD, 18);
    public static final Color ATTRIBUTESFORBOAT_ColorHeader = new Color(255, 255, 0);
    public static final String ATTRIBUTESFORBOAT_HeaderPrefixedText = "Choose";
    // Controls help prompt attributes
    public static final int ATTRIBUTESFORHELP_WidthHelp = 160;
    public static final int ATTRIBUTESFORHELP_HeightHelp = 95;
    public static final int ATTRIBUTESFORHELP_WidthImage = 118;
    public static final int ATTRIBUTESFORHELP_HeightImage = 70;
    public static final String ATTRIBUTESFORHELP_PrefixSelctionImagePath = "/mainImages/selectiondisplayimages/";
    public static final String ATTRIBUTESFORHELP_KeyBoardPrefix = "HelpKeyBoardImage_";
    public static final String ATTRIBUTESFORHELP_ImagesPrefixExt = ".png";
    public static final int ATTRIBUTESFORHELP_SpacingXLocation = 30;
    public static final Font ATTRIBUTESFORHELP_FontStylingHeader = ATTRIBUTESFORBOAT_FontStylingHeader;
    public static final Color ATTRIBUTESFORHELP_ColorHeader = new Color(255, 255, 255);
    // Map selection attributes
    public static final Color ATTRIBUTESFORMAP_Color = new Color(255,255,255,255);
    public static final int ATTRIBUTESFORMAP_Spacing = 30;
    public static final int ATTRIBUTESFORMAP_Width = 120;
    public static final int ATTRIBUTESFORMAP_SpacingYLocation = 30;
    public static final int ATTRIBUTESFORMAP_WidthMapSelection = 80;
    public static final int ATTRIBUTESFORMAP_HeightMapSelection = 80;
    public static final String[] ATTRIBUTESFORMAP_MapTitlesObject = { "Standard" , "Alternative" };
    public static final String ATTRIBUTESFORMAP_PrefixMapFilePath = "/mainImages/selectiondisplayimages/";
    public static final String ATTRIBUTESFORMAP_PrefixMapName = "RaceTrack_";
    public static final String ATTRIBUTESFORMAP_PrefixMapsExtension = ".png";
    public static final Color ATTRIBUTESFORMAP_ColorBackground = new Color(100,100,100,192);
    public static final Font ATTRIBUTESFORMAP_FontStyling = ATTRIBUTESFORBOAT_FontStylingHeader;
    public static final int ATTRIBUTESFORMAP_Height = 20;
    public static final int ATTRIBUTESFORMAP_MapYSpacing = 5;
    public static final int ATTRIBUTESFORMAP_MapSpacing = 15;
    public static final String ATTRIBUTESFORMAP_MapTitleText = "Choose";
    public static final Color ATTRIBUTESFORMAP_ColorHeading = ATTRIBUTESFORBOAT_ColorHeader;
    public static final Font ATTRIBUTESFORMAP_FontStylingMapTitle = ATTRIBUTESFORMAP_FontStyling;
    public static final int ATTRIBUTESFORMAP_HeightMapTitle = 20;
    // boats in-game attributes
    public static final String BOATSINGAME_BoatInGameFilePath = "/mainImages/boatanimationimages/";
    public static final String BOATSINGAME_BoatAnimationBoatPrefix = "boat_";
    public static final String BOATSINGAME_BoatImageExtension = ".png";
    public static final String BOATSINGAME_CrashImage = "/mainImages/boatanimationimages/crash.png";
    public static final int BOATSINGAME_SizeWidth = 50;
    public static final int BOATSINGAME_SizeHeight = BOATSINGAME_SizeWidth;
    // The boat image filenames reflecting the angles
    public static final String[] BOATSINGAME_AnglePrefixes = {"0", "22", "45", "67", "90", "112", "135", "157", "180", "202", "225", "247", "270", "292", "315", "337"};
    public static final int[]   BOATSINGAME_Angles = {0,22,45,67,90,112,135,157,180,202,225,247,270,292,315,337};
    
    
    public static final BoundsOfBoat[] BOATSINGAME_BoundsOfBoat = {
    		 // Degree: 0
    	    new BoundsOfBoat(11, 1, 38, 1, 11, 48, 38, 48),
    	 // Degree: 22   
    	    new BoundsOfBoat(22, 0, 42, 8, 30, 46, 8, 39),
    	 // Degree: 45   
    	    new BoundsOfBoat(32, 1, 46, 13, 19, 47, 1, 32),
    	 // Degree: 67
    	    new BoundsOfBoat(40, 8, 49, 26, 11, 44, 0, 21),
    	 // Degree: 90   
    	    new BoundsOfBoat(1, 12, 48, 12, 48, 37, 1, 37),
    	 // Degree: 112   
    	    new BoundsOfBoat(11, 6, 48, 23, 41, 43, 0, 27),
    	 // Degree: 135
    	    new BoundsOfBoat(20, 1, 47, 30, 35, 47, 2, 16),
    	 // Degree: 157   
    	    new BoundsOfBoat(19, 1, 48, 31, 36, 47, 2, 16),
    	 // Degree: 180   
    	    new BoundsOfBoat(11, 1, 38, 1, 11, 48, 38, 48),
    	 // Degree: 202   
    	    new BoundsOfBoat(22, 0, 42, 8, 30, 46, 8, 39),
    	 // Degree: 225   
    	    new BoundsOfBoat(32, 1, 46, 13, 19, 47, 1, 32),
    	 // Degree: 247   
    	    new BoundsOfBoat(40, 8, 49, 26, 11, 44, 0, 21),
    	 // Degree: 270   
    	    new BoundsOfBoat(1, 12, 48, 12, 48, 37, 1, 37),
    	 // Degree: 292   
    	    new BoundsOfBoat(11, 6, 48, 23, 41, 43, 0, 27),
    	 // Degree: 315   
    	    new BoundsOfBoat(20, 1, 47, 30, 35, 47, 2, 16),
    	 // Degree: 337   
    	    new BoundsOfBoat(19, 1, 48, 31, 36, 47, 2, 16), 
        }; // These values are the fine bounds used by the collision detection.

    // In game CONTROL attributes
    // Follow the given array convention for player 2
    public static final Integer[] ATTRIBUTESFORCONTROL_OnePlayerControls =  { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    // UP, DOWN, LEFT, RIGHT
    public static final Integer[] ATTRIBUTESFORCONTROL_TwoPlayerControls = {87, 83, 65, 68};
    public static final float ATTRIBUTESFORCONTROL_MaxSpeed = 8; 
    public static final float ATTRIBUTESFORCONTROL_MaxReverseSpeed = 4;
    public static final float ATTRIBUTESFORCONTROL_MaxIslandSpeed = 2; 
    // Default preset speed parameters
    // 50% of max speed
    public static final float ATTRIBUTESFORCONTROL_ProgressiveStart50 = 0.05f;
    // 25% of max speed
     public static final float ATTRIBUTESFORCONTROL_ProgressiveStart25 = 0.25f;
     // Increases by 50%
    public static final float ATTRIBUTESFORCONTROL_MajorAcceleration150 = 1.5f;
    //60% of max speed
    public static final float ATTRIBUTESFORCONTROL_NeutralAcceleration60 = 0.60f;
    // Increase by 20%
    public static final float ATTRIBUTESFORCONTROL_NeutralAcceleration120 = 1.2f;
    // Increase by 10%
    public static final float ATTRIBUTESFORCONTROL_MajorAcceleration110 = 1.1f;
    // At 90% slow down ratio
    public static final float ATTRIBUTESFORCONTROL_DecreaseAcceleration90 = 0.9f;
    // At 5% Stop threshold
    public static final float ATTRIBUTESFORCONTROL_DecreaseAcceleration5 = 0.05f;
    // Represented as MPH max speed
    public static final int ATTRIBUTESFORCONTROL_InGameMaxSpeed = 100;
    // boats in-game HUDS i.e. speed progress bar
    public static final int ATTRIBUTESFORHUD_XValue = 200;
    public static final int ATTRIBUTESFORHUD_YValue = 75;
    public static final Color ATTRIBUTESFORHUD_Color = new Color(255,255,255, 0);
    public static final Font ATTRIBUTESFORHUD_FontStyling = ATTRIBUTESFORBOAT_FontStylingHeader;
    public static final Font ATTRIBUTESFORHUD_FontStylingSpeed = new Font("Times New Roman", Font.BOLD, 14);
    public static final int ATTRIBUTESFORHUD_SpacingNegativeY = 10;
    public static final String ATTRIBUTESFORHUD_SpeedTitle = "Velocity: ";
    public static final String ATTRIBUTESFORHUD_MetricTitle = " Mph";
    public static final int ATTRIBUTESFORHUD_PlayerTitleHeightSpacing = 10;
    public static final int ATTRIBUTESFORHUD_XValuePlayerTitle = 140;
    public static final int ATTRIBUTESFORHUD_YValuePlayerTitle = 20;
    public static final int ATTRIBUTESFORHUD_SpacingSpeedHeight = 5;
    public static final int ATTRIBUTESFORHUD_XValueSpeed = 140;
    public static final int ATTRIBUTESFORHUD_YValueSpeed = 15;
    public static final int ATTRIBUTESFORHUD_XValueSpeedProgessBar = 100;
    public static final int ATTRIBUTESFORHUD_YValueSpeedProgessBar = 10;
    public static final Color ATTRIBUTESFORHUD_ProgressBarColor = new Color(128,191,255,100);
    public static final int ATTRIBUTESFORHUD_OnePlayerXPoistion = 0;
    public static final int ATTRIBUTESFORHUD_OnePlayerYPoistion = 0;
    public static final int ATTRIBUTESFORHUD_SecondPlayerXPoistion = 650;
    public static final int ATTRIBUTESFORHUD_SecondPlayerYPoistion = ATTRIBUTESFORHUD_OnePlayerXPoistion;
    public static final float ATTRIBUTESFORHUD_SpeedMask60 = 0.6f;
    // Default settings for audio and background music
    public static final String ATTRIBUTESFORAUDIO_MusicFilePath = "/audios/audioBackgroundMusic.wav";
    public static final String ATTRIBUTESFORAUDIO_SoundFilePath = "/audios/audioRockHit.wav";
    public static final String ATTRIBUTESFORAUDIO_CrashFilePath = "/audios/audioBoatCrash.wav";
    public static final String ATTRIBUTESFORAUDIO_BoatRevFilePath = "/audios/audioBoatRev.wav";
    // Default attributes for game over
    public  static final String ATTRIBUTESFORGAMEOVER_GameCompletedContent = "Game completed, please start new game to play again";
    public  static final String ATTRIBUTESFORGAMEOVER_GameCompletedTitle = "Game Completed.";
    public static final String ATTRIBUTESFORGAMEOVER_GameCompletedError = "Attention! error occurred.";  
    public static final String ATTRIBUTESFORGAMEOVER_GameCompletedErrorOpponentMissing = "Attention! Opponent has left the game.";
    public static final String ATTRIBUTESFORGAMEOVER_GameCompletedNetworkIssue = "Attention! Servers are currently down.";
    // Network report display
    //Font follows MS conventions
    public static final Font NETWORKREPORTSCREEN_FontNormal = new Font("Times New Roman", Font.BOLD, 12);
    public static final Font NETWORKREPORTSCREEN_Heading = new Font("Times New Roman", Font.BOLD, 18);
    public static final String NETWORKCONTROLPANEL_WorkstationIPName = "Workstation:";
    public static final Integer[] NETWORKCONTROLPANEL_Ports = {24816, 48162, 16248};
    public static final String NETWORKREPORTSCREEN_MainMenu = "Main Menu";
    public static final String NETWORKREPORTSCREEN_KillNetwork = "Kill network";
    public static final String NETWORKREPORTSCREEN_NetworkGoContent = "GO";
    public static final String NETWORKCONTROLPANEL_NetworkLauncherTitle = "Network Launcher"; 
    public static final String NETWORKCONTROLPANEL_PortsTitle = "Select Port:";
    //Network Workstation display
    public static final String NETWORKCONSOLEDISPLAY_Title = "Connection";
    public static final String WORKSTATIONCONSOLEDISPLAY_Title = "Terminal/Console";
    public static final String WORKSTATIONCONFIGURATION_IPDefault = "Example ip: 192.0.2.1";
    public static final String WORKSTATIONCONFIGURATION_Title = "NetworkServer Configuration";
    public static final String WORKSTATIONCONFIGURATION__IPTitle = "Please enter IP address:";
    public static final String WORKSTATIONCONFIGURATION_PortTitle = "Port:";
    //	Sever failed Exceptions
    public static final int NETWORKTIMEOUT_Value = 10; 
    // Main application controller
    public static Control MainControl; 
    // Starting default values for in game
    public static boolean ATTRIBUTESFORDEFAULT_MusicState = false;
    public static boolean ATTRIBUTESFORDEFAULT_SoundState = false;
    public static boolean ATTRIBUTESFORDEFAULT_HighResLowResState = true;


}
