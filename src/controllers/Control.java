package controllers;

import models.Server.WorkstationClient;
import views.AudioEngine;
import views.CoreSwingUIComponent;

/** MAIN CONTROLLER 
 *  This is the main controller of the application 
 *  this controls a lot of the main functionality for models and views "MVC" 
 */
public class Control {

	//Management for Network server
	private Network _NetworkInitiation; 
	
	//Management for workstation/client which is running 
	private WorkstationClient WorkstationClient;
	
    //Sound engine for game
	private AudioEngine _AudioEngine;
	
    //Control game states
	private RuntimeEngine RuntimeEngine;
	
    //This is the SWINGUI JFrame it displays other JPANELS
	private CoreSwingUIComponent _CoreSwingUIComponent; 
    
    // Call back for other player
    private boolean _PromiseCallbackForOtherPlayer = false; //Please note promise is used as a way to describe a process waiting for something to happen 

    //Application start up, this starts the application
    public void StartApplication()
    {
        //Creates new SWINGUICore and initiates
        _CoreSwingUIComponent = new CoreSwingUIComponent();
        _CoreSwingUIComponent.DefaultWindowCreation();

        //This navigates to the main menu.
        _CoreSwingUIComponent.NavigateToMenuMainDisplay();

        //Creates a new sound engine
        _AudioEngine = new AudioEngine();

    }

    // Initiates network server creation
    public void NetworkServerInitiation()
    {
        if (_NetworkInitiation == null) {
            _NetworkInitiation = new Network(_CoreSwingUIComponent);
        }

        _NetworkInitiation.NetworkServerConfigLaunch();
    }

    //Connecting as workstation
    public void ConnectingAsClientInitiation()
    {
        CurrentRuntimeSession.PlayerCreation();
        DefaultsWhichGoToBoatSelectionDisplay();
    }

    //On instance of selecting which type of boat
    private void DefaultsWhichGoToBoatSelectionDisplay() {
        _CoreSwingUIComponent.NavigateToLaunchDisplay();
        _CoreSwingUIComponent.SetDefaultValsOnLaunchDisplay(); //default instance created here

        //Create client and pass the Launch Screen terminal as output.
        if (WorkstationClient == null)
            WorkstationClient = new WorkstationClient(_CoreSwingUIComponent.get_LaunchDisplay().get_SelectionNetworkConsoleDisplay().get_NetworkConsoleDisplay());
    }

    //On instance of either end game or new game selected/server shutdown
    public void NavigatingBackToMenuMainDisplay() {
        //Makes sure RuntimeEngine is shut and set to null before navigation
        if(RuntimeEngine != null)
        {
            RuntimeEngine.ExitApplication();
            RuntimeEngine = null;
        }

        //Makes sure network/server is shut before navigation
        if (_NetworkInitiation != null) {
            _NetworkInitiation.NetworkClose();
        }

        //Makes sure client is shut before navigation
        if (WorkstationClient != null) {
            WorkstationClient.TryShutDownClientConnection(true);
        }

      //This resets game session
        CurrentRuntimeSession.RestartSessionOfGame();
        _CoreSwingUIComponent.NavigateToMenuMainDisplay();
    }

    //On instance of game session sets boat and player starting at 1 for player and 0 for boat
    public void ChangeBoatSession(int AmountOfPlayers, int indexOfBoat)
    {
        CurrentRuntimeSession.get_PlayerAmount().get(AmountOfPlayers-1).get_Boat().set_BoatIndexedImage(indexOfBoat);
        CurrentRuntimeSession.set_PlayerSelectedBoatIndex(indexOfBoat);
    }

    //On instance of selecting map sets map this starts from 0
    public void ChangeMapSession(int MapType)
    {
        CurrentRuntimeSession.set_SelectedMapTypeName(GlobalResources.ATTRIBUTESFORMAP_MapTitlesObject[MapType]);
    }

    //Launch game when player clicks start.
    public void LaunchGame()
    {
        if (!WorkstationClient.is_MapResponseIdle()) {
            String severAddress = _CoreSwingUIComponent.get_LaunchDisplay().WorkstationNetworkDisplay().GetMainSeverIPText();
            int portNumber = _CoreSwingUIComponent.get_LaunchDisplay().WorkstationNetworkDisplay().GetPortNumber();
            boolean isSuccesOnConnection = WorkstationClient.NetworkServerConnecting(severAddress, portNumber);

            if (isSuccesOnConnection) {
                //Promise waiting for callback
                _PromiseCallbackForOtherPlayer = true;
                WorkstationClient.CallOtherPlayer(CurrentRuntimeSession.get_SelectionMapTypeName(), CurrentRuntimeSession.get_PlayerSelectedBoatIndex());
            }
        } else {
            WorkstationClient.PromiseWaitingForOtherPlayer();
        }
    }


    //Waiting for other player to connect
    public void PromiseCallbackForOtherPlayer() {
        
    	if (_PromiseCallbackForOtherPlayer) {
            //Set the boat design (type) for the remote boat
            int DataHandlerBoatTypeIndex = WorkstationClient.get_WaitingMapResponse().get_BoatImageFileIndex();
            CurrentRuntimeSession.get_PlayerAmount().get(1).get_Boat().set_BoatIndexedImage(DataHandlerBoatTypeIndex);

            //If the server decided that this client is not player 1, then player needs to be switched up.
            //Note: A client defaults to Players 1 until the server decides otherwise.
            if (WorkstationClient.get_WaitingMapResponse().get_AssignmentForPlayer() != GlobalResources.In_Game_Player)
                CurrentRuntimeSession.AssignCorrectPlayerIndication();

            _PromiseCallbackForOtherPlayer = false;
            RuntimeEngine = new RuntimeEngine(_CoreSwingUIComponent, _AudioEngine);
            RuntimeEngine.StartGame();

        } else {
            //The user decided to not to wait (e.g. returned to the main menu)
            //Lets notify the server about the client dropped.
            WorkstationClient.PlayerToNetworkCanceledSession();
        }
    }

    //Sets background music state. True = on; False = off.
    public void BoatGameAudioMenuBar(boolean currentState)
    {
        GlobalResources.ATTRIBUTESFORDEFAULT_MusicState = currentState;
        _AudioEngine.SetdMusicAudio(currentState);
    }

    //Sets background audio state. True = on; False = off.
    public void BoatGameSoundMenuBar(boolean currentState)
    {
        GlobalResources.ATTRIBUTESFORDEFAULT_SoundState = currentState;
    }

    //Sets map textures state. True = on; False = off.
    public void BoatGameMapTextureMenuBar(boolean currentState)
    {
        GlobalResources.ATTRIBUTESFORDEFAULT_HighResLowResState = currentState;
    }

    //Simple get method which returns game engine
    public RuntimeEngine get_RuntimeEngine() {
        return RuntimeEngine;
    }

    //Shuts down application using dispose method if user clicks on binded property.
    public void BoatGameUserExit()
    {
        _AudioEngine.SetdMusicAudio(false);
        _CoreSwingUIComponent.dispose();
    }

    //Tries to shut down client and workstation
    public void TryShutdownNetworkClient() {
        if (WorkstationClient != null)
            WorkstationClient.TryShutDownClientConnection(true);
        if (_NetworkInitiation != null)
            _NetworkInitiation.NetworkClose();
    }

    // Returns workstation client
    public WorkstationClient get_WorkstationClient() {
        return WorkstationClient;
    }
}
