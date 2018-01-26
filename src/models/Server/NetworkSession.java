package models.Server;

import controllers.GlobalResources;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import models.Server.Communication.SystemMessage;
import models.Server.Communication.NetworkMessageRequest;
import models.Server.Communication.SystemMessageType;
import views.Displays.DispNetwork.NetworkConsoleDisplay;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;


// Session with network and client with multi-threading
class NetworkSession implements Runnable {

	// Workstation/client socket
    private final Socket _WorkstationSocket;

    // Network console display
    private final NetworkConsoleDisplay _NetworkConsoleDisplay;
    
    // This is the waiting area for players to choose specific map 
    private final ObservableMap<Socket, String> _WaitingArea;

    // The active games
    private final ObservableList<WorkingSocket> ActiveGames;
				
    // The output stream of sockets for players
    private volatile ObjectOutputStream _OutputStream;

    // The input stream of sockets for players
    private volatile ObjectInputStream _InputStream;

    // When fatal error occurs kills network
    private boolean FatalErrorKillNetwork = false;

    // Active game player is in
    private WorkingSocket _ActiveGame = null;

//    Previous map message from network
    private NetworkMessageRequest _NetworkPreviousMapMessage = null;
    
    //Exit message set to false 
    private boolean _ExitMessage = false;

    // Checks whether the network has been shut
    private boolean _NetworkShutdown = false;
    
    // Listens to the exceptions and close session  
    private int _FaultToleranceExceptions = 0;
    private int _SetFaultToleranceExceptions = 0;

    //Represents in game assignment of player
    private int _PlayerInGameAssignment = -1;

    	//Prompts socket game is preparing
    private final Runnable PrepareGame = () ->
            _ActiveGame.GamePrepared(_PlayerInGameAssignment, _NetworkPreviousMapMessage.get_BoatImageFileIndex());


    // Workstation socket, Network console display, waiting area and active games between network and workstation/client
    public NetworkSession(Socket _ClientSocket, NetworkConsoleDisplay _Terminal, ObservableMap<Socket, String> _WaitingArea, ObservableList<WorkingSocket> _ActiveGames) {
        this._WorkstationSocket = _ClientSocket;
        this._NetworkConsoleDisplay = _Terminal;
        this._WaitingArea = _WaitingArea;
        this.ActiveGames = _ActiveGames;

        //Adding listener / event handler to the ActiveGames observable list
        _ActiveGames.addListener(this::RemoteActiveGameChange);
    }

    // Active games list which is being observed
    private void RemoteActiveGameChange(ListChangeListener.Change<? extends WorkingSocket> changes) {

        while (changes.next()) {
            //A new match added here
            if (changes.wasAdded()) {
                WorkingSocket newActiveGame = changes.getAddedSubList().get(0);
                if (newActiveGame.PlayerSocketsMatch(_WorkstationSocket)) {
                    //Get player assignment
                    _PlayerInGameAssignment = newActiveGame.PlayerAsignedToThisSocket(_WorkstationSocket);
                    //This working socket gets session
                    newActiveGame.SessionAssignmentSocket(_PlayerInGameAssignment, this);
                    // Set active game
                    _ActiveGame = newActiveGame;
                    //Active game slot for player
                    SessionLogMessages("An active game slot exist.");

                    //Send message on a individual thread to the active match
                    Thread startGamePrompt = new Thread(PrepareGame);
                    startGamePrompt.start();

                }
            }

            if (changes.wasRemoved()) {
                //This thread for in game session
                if (_ActiveGame != null) {
                		// Remove match that session is in; checks and prints
                    if (changes.getRemoved().get(0).PlayerSocketsMatch(_WorkstationSocket)) {
                        SessionLogMessages("Active game, now in-active.");

                        //Error message with inactive game
                        SystemMessage systemMessage = new SystemMessage(SystemMessageType.GAMEINACTIVE);
                        if (!SendAnnouncement(systemMessage))
                            SessionLogMessages("ERROR: game has been closed");
                    }
                }
            }
        }
    }

    //!Sent message Workstation/Client exit, The socket close, sever down, fault tolerance exceeded; expected = true, pass or false, fail. 
    public synchronized boolean SendAnnouncement(SystemMessage systemMessage) {
        //Returns false if message sent
        if (_ExitMessage)
            return false;

        //Returns false if socket close
        if (_WorkstationSocket.isClosed())
            return false;

        //Returns false if Network shutdown
        if (_NetworkShutdown)
            return false;

        //Returns false if fault tolerance exceeded
        if (_SetFaultToleranceExceptions > GlobalResources.NETWORKTIMEOUT_Value) {
            FatalErrorKillNetwork = true;
            return false;
        }

        boolean result = true;
        try {
            _OutputStream.writeObject(systemMessage);
            _OutputStream.flush();
            _SetFaultToleranceExceptions = 0;
        } catch (Exception e) {
            _SetFaultToleranceExceptions++;
            SessionLogMessages("ERROR: " + e.getMessage() +
                    " [SystemMessage type: " + Integer.toString(systemMessage.getType()) +
                    "]. Threshold status: " + Integer.toString(_SetFaultToleranceExceptions) +
                    "/" + Integer.toString(GlobalResources.NETWORKTIMEOUT_Value));
            result = false;
        }
        return result;
    }

    // Starts the input and output streams; returns true if success and false if not 
    public boolean StartStreams() {
        boolean result = true;
        try {
            _InputStream = new ObjectInputStream(_WorkstationSocket.getInputStream());
            _OutputStream = new ObjectOutputStream(_WorkstationSocket.getOutputStream());
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override    
    // Stops Listening when Network server shuts down, workstation exit, socket exit, fault tolerance exceeded
    public void run() {
        //Make sure streams are not null
        if (_InputStream == null || _OutputStream == null)
            return;

        //Prepared stream
        SessionLogMessages("Network Session is open. Waiting..."); //For messages
        SystemMessage systemMessage; //Previous message here


        while (!_WorkstationSocket.isClosed() && !_NetworkShutdown && !FatalErrorKillNetwork) {
            try {
                //SystemMessage taken from input stream object
                systemMessage = (SystemMessage) _InputStream.readObject();

                //0 exceptions so far
                _FaultToleranceExceptions = 0;

                //If workstation greetings log message
                if (systemMessage.getType() == SystemMessageType.GREETINGS) {
                    SessionLogMessages("Greetings from workstation.");
                    continue;
                }

                //Searching for other player
                if (systemMessage.getType() == SystemMessageType.SEARCHINGFOROTHERPLAYER) {
                    _NetworkPreviousMapMessage = (NetworkMessageRequest) systemMessage;
                    SessionLogMessages("Map request received from the client.");
                    //Check if this is not a duplicate request. If not, add to the lobby.
                    if (!_WaitingArea.containsKey(_WorkstationSocket)) {
                        _WaitingArea.put(_WorkstationSocket, _NetworkPreviousMapMessage.get_MapName());
                    }
                    continue;
                }

                //Push update of in game crash or boat movement change
                if (systemMessage.getType() == SystemMessageType.BOATMOVEMENTCHANGE || systemMessage.getType() == SystemMessageType.BOATCRASH) {
                    if (_ActiveGame != null) {
                        _ActiveGame.AnnouncementMessage(_PlayerInGameAssignment, systemMessage);
                    }
                    continue;
                }


                //Remove session if player dropped and set next message ot null
                if (systemMessage.getType() == SystemMessageType.PLAYERDISCONNECT) {
                    SessionLogMessages("Players dropped message received from the client.");
                    //Check if the player was in a lobby
                    if (_WaitingArea.containsKey(_WorkstationSocket)) {
                        _WaitingArea.remove(_WorkstationSocket);
                        _NetworkPreviousMapMessage = null;
                    }
                    continue;
                }

                // If exit message true workstation close socket
                if (systemMessage.getType() == SystemMessageType.EXIT) {
                    _ExitMessage = true;

                    //Check active player in waiting area
                    if (_WaitingArea.containsKey(_WorkstationSocket)) {
                        _WaitingArea.remove(_WorkstationSocket);
                        _NetworkPreviousMapMessage = null;
                    }
                    if (_ActiveGame != null) {
                        _ActiveGame.PlayerIsInactive(_PlayerInGameAssignment);
                    }

                    //Workstation socket close and exit
                    _WorkstationSocket.close();
                    SessionLogMessages("EXIT CAUGHT.");
                }


            } catch (Exception e) {
                // Error message given when closing down
                boolean expectedResult = false;
                expectedResult = (expectedResult || _NetworkShutdown);
                expectedResult = (expectedResult || _ExitMessage);
                expectedResult = (expectedResult || _WorkstationSocket.isClosed());
                if (expectedResult) {
                    SessionLogMessages("Catch ERROR when listening to workstation " +
                            "SystemMessage for this is: " + e.getMessage());
                }

                //Threshold limit expectations 
                if (!expectedResult && _FaultToleranceExceptions < GlobalResources.NETWORKTIMEOUT_Value) {
                    _FaultToleranceExceptions++;
                    SessionLogMessages("Fault tollerance is: (" +
                            Integer.toString(_FaultToleranceExceptions) + "/"
                            + Integer.toString(GlobalResources.NETWORKTIMEOUT_Value) + "). " +
                            "ERROR SystemMessage: " + e.getMessage());
                }

                //Fatal error when over threshold and player is waiting.
                if (!expectedResult && _FaultToleranceExceptions >= GlobalResources.NETWORKTIMEOUT_Value) {
                    FatalErrorKillNetwork = true;
                    e.printStackTrace();
                    SessionLogMessages("ERROR too many request. SystemMessage: " + e.getMessage());
                    if (_ActiveGame != null)
                        _ActiveGame.PlayerIsInactive(_PlayerInGameAssignment);
                }

            }
        }

        //Workstation socket close if not done
        if (_WorkstationSocket != null)
            if (!_WorkstationSocket.isClosed()) {
                SessionLogMessages("Attemping to shutdown socket.");
                try {
                    _WorkstationSocket.close();
                    SessionLogMessages("WorkstationClient socket is closed.");
                } catch (IOException e) {
                    SessionLogMessages("ERROR socket is open: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        SessionLogMessages("Network Session is closed.");
    }


    // Show time stamp of text in terminal
    private void SessionLogMessages(String contentText) {
        final LocalDateTime now = LocalDateTime.now();
        String currentTime = now.toLocalTime().toString();

        String sentMessage = "(SESSION " + _WorkstationSocket.getInetAddress().toString();
        if (_PlayerInGameAssignment != -1)
            sentMessage += " - Players " + Integer.toString(_PlayerInGameAssignment) + ". - ): ";
        else
            sentMessage += "): ";

        String message = currentTime + ", " + sentMessage + contentText;
        _NetworkConsoleDisplay.LogMessages(message);
    }

    // Network closed announcement = shutdown true
    public void NetoworkClosedAnnouncement() {
        _NetworkShutdown = true;
    }


}
