package models.Server;

import controllers.GlobalResources;
import models.Server.Communication.SystemMessage;
import models.Server.Communication.SystemMessageResponse;
import models.Server.Communication.SystemMessageType;
import views.Displays.DispNetwork.NetworkConsoleDisplay;

import java.net.Socket;
import java.time.LocalDateTime;

// This is the working socket for the two workstations/sessions; pushes when it has ended to network
class WorkingSocket {

	// Game specific identifier scope is for the network 
    private final int _GameIdentifier;

    //Network console log display
    private final NetworkConsoleDisplay _NetworkConsoleDisplay;

    // Player one socket
    private final Socket _PlayerOneSocket;
    
    //Player two socket
    private final Socket _PlayerTwoSocket;
    
    // Network server called back when game complete
    private final NetworkServer _NetworkServer;
    
    //Standard/Alternative
    private final String _MapType;
    
    // When both sessions are set to true, game starts
    private boolean _PlayerOneReady = false;
    private boolean _PlayerTwoReady = false;
    
    // Socket one player session
    private NetworkSession _PlayerOneSession;
    
    // Image file index
    private int _PlayerOneBoatIndex = 0;
    
    // Player one inactive
    private boolean _PlayerOneInactive = false;

    // Socket two player session
    private NetworkSession _PlayerTwoSession;

    // Image file index
    private int _PlayerTwoBoatIndex = 0;

    // Player one inactive
    private boolean _PlayerTwoInactive = false;
    
    // error checker allows only to have crash message sent out one time
    private volatile int _messageBoatCrashCounter = 0;

    // Allows both players to share data when this is set to true
    private volatile boolean _gameInitiationSentOutX2 = false;


    // Ready, this is called by other objects as both sessions prepare
    private final Runnable MessagGameInitiated = () ->
    {
        SystemMessageResponse messagePlayerOne = new SystemMessageResponse(SystemMessageType.PLAYERFOUNDINITIATEGAME);
        SystemMessageResponse messagePlayerTwo = new SystemMessageResponse(SystemMessageType.PLAYERFOUNDINITIATEGAME);

        //Network assigns players with IDs/Number
        messagePlayerOne.set_PlayerAsssignment(GlobalResources.In_Game_Player);
        messagePlayerTwo.set_PlayerAsssignment(GlobalResources.Second_In_Game_Player);

        //Inverses player information for each player
        messagePlayerOne.set_boatImageFileIndex(_PlayerTwoBoatIndex);
        messagePlayerTwo.set_boatImageFileIndex(_PlayerOneBoatIndex);

        _PlayerOneSession.SendAnnouncement(messagePlayerOne);
        _PlayerTwoSession.SendAnnouncement(messagePlayerTwo);
        _gameInitiationSentOutX2 = true;
    };

    
    // Watches the workstations watches for end and notifies when game complete
    public WorkingSocket(Socket _PlayerOneSocket, Socket _PlayerTwoSocket, String _MapType, int gameIdentifier, NetworkServer networkInstance) {
        this._PlayerOneSocket = _PlayerOneSocket;
        this._PlayerTwoSocket = _PlayerTwoSocket;
        this._MapType = _MapType;
        _GameIdentifier = gameIdentifier;
        _NetworkServer = networkInstance;
        _NetworkConsoleDisplay = networkInstance.geNetworkConsoleDisplay();
    }

    // Returns true if one argument is true
    public boolean PlayerSocketsMatch(Socket player) {
        return (_PlayerOneSocket.equals(player) || _PlayerTwoSocket.equals(player));
    }

    // Returns comparison against each players asigned sockets
    public int PlayerAsignedToThisSocket(Socket socket) {
        if (socket.equals(_PlayerOneSocket))
            return GlobalResources.In_Game_Player;

        if (socket.equals(_PlayerTwoSocket))
            return GlobalResources.Second_In_Game_Player;

        return -1;
    }

    // Assigns a number to a player
    public void SessionAssignmentSocket(int playerAssignmentNumer, NetworkSession networkSession) {
        if (playerAssignmentNumer == GlobalResources.In_Game_Player)
            _PlayerOneSession = networkSession;

        if (playerAssignmentNumer == GlobalResources.Second_In_Game_Player)
            _PlayerTwoSession = networkSession;
    }

    // Sends message to workstations when game is prepared
    public synchronized void GamePrepared(int playerNumber, int selectedboatIndex) {
        if (playerNumber == GlobalResources.In_Game_Player) {
            _PlayerOneReady = true;
            _PlayerOneBoatIndex = selectedboatIndex;
        }

        if (playerNumber == GlobalResources.Second_In_Game_Player) {
            _PlayerTwoReady = true;
            _PlayerTwoBoatIndex = selectedboatIndex;
        }

        //Send seperate thread stating game is about to start
        if (_PlayerOneReady && _PlayerTwoReady) {
            GameLog("Game is Preparing...");
            Thread message = new Thread(MessagGameInitiated);
            message.start();
        }
    }

    // Announcement crash, send while using a new thread; inform of new game
    public void AnnouncementMessage(int PlayerNumberAssignment, SystemMessage systemMessage) {
        if (_gameInitiationSentOutX2) {
            if (_messageBoatCrashCounter < 1) {
                NetworkTransmit transmit = null;
                if (PlayerNumberAssignment == GlobalResources.In_Game_Player) {
                    transmit = new NetworkTransmit(systemMessage, _PlayerTwoSession);
                }

                if (PlayerNumberAssignment == GlobalResources.Second_In_Game_Player) {
                    transmit = new NetworkTransmit(systemMessage, _PlayerOneSession);
                }

                if (transmit != null)
                    transmit.start();
            }

            if (systemMessage.getType() == SystemMessageType.BOATCRASH) {
                _messageBoatCrashCounter++;
                GameLog("Crash occured: " + Integer.toString(PlayerNumberAssignment));
            }
        }
    }

    // Game complete when both players leave, one player leave then notify
    public synchronized void PlayerIsInactive(int PlayerNumberAssignment) {

        //Player one leave complete
        if (PlayerNumberAssignment == GlobalResources.In_Game_Player) {
            _PlayerOneInactive = true;
            //Announce to player two
            if (!_PlayerTwoInactive) {
                //Unnecessary if boat crash announcement fail
                if (_messageBoatCrashCounter < 1) {
                    GameLog("Players One has disconnect from game...");
                    SystemMessage systemMessage = new SystemMessage(SystemMessageType.PLAYERDISCONNECT);
                    Thread killThread = new Thread(new NetworkTransmit(systemMessage, _PlayerTwoSession));
                    killThread.start();
                }
            }
        }

        //Player two leave complete
        if (PlayerNumberAssignment == GlobalResources.Second_In_Game_Player) {
            _PlayerTwoInactive = true;

            //Announce to player one
            if (!_PlayerOneInactive) {
                //Unnecessary if boat crash announcement fail
                if (_messageBoatCrashCounter < 1) {
                    GameLog("Players Two has disconnect from the game...");
                    SystemMessage systemMessage = new SystemMessage(SystemMessageType.PLAYERDISCONNECT);
                    Thread killThread = new Thread(new NetworkTransmit(systemMessage, _PlayerOneSession));
                    killThread.start();
                }
            }
        }

        //Display message and kill process when game is complete/ players disconnected
        if (_PlayerOneInactive && _PlayerTwoInactive) {
            GameLog("All players disconnected");
            _NetworkServer.endedGameAnnouncement(this, _GameIdentifier);
        }
    }

    // Game displays on game log 
    private void GameLog(String text) {
        final LocalDateTime now = LocalDateTime.now();
        String currentTime = now.toLocalTime().toString();

        final String template = "GAME " + _GameIdentifier + ": ";

        String message = currentTime + ", " + template + text;
        _NetworkConsoleDisplay.LogMessages(message);
    }

    // Separate thread for one message
    private class NetworkTransmit extends Thread {
        final SystemMessage _Send;
        final NetworkSession _deliverer;

     // Separate thread for one message
        public NetworkTransmit(SystemMessage systemMessage, NetworkSession deliverer) {
            _Send = systemMessage;
            _deliverer = deliverer;
        }

        public void run() {
            _deliverer.SendAnnouncement(_Send);
        }
    }

}
