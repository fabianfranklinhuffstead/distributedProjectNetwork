package models.Server;

import controllers.Network;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import models.Server.Communication.SystemMessage;
import models.Server.Communication.SystemMessageType;
import views.Displays.DispNetwork.NetworkConsoleDisplay;

import javax.swing.*;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// As mentioned in the assignment the transmission connection protocol, is used managing 
// incoming and outgoing sessions. A WorkSocket is used to represent one match 
public class NetworkServer {

	// Private Network
    private final Network _Network;

    //
    private final ArrayList<Socket> _WorkStationSocket = new ArrayList<>();
    
    //Game Lobby which uses a HashMap
    private final Map<Socket, String> _WorkstationClientLobby = new HashMap<>();
    
    // Map promise like functions which allow for data collation and notifications of thread changes 
    private final ObservableMap<Socket, String> _ObservableWorkstationClientLobby = FXCollections.observableMap(_WorkstationClientLobby);

    // Array of all workstation sessions
    private final ArrayList<NetworkSession> _WorkstationSessions = new ArrayList<>();
    
    // Running socket for active game
    private final ArrayList<WorkingSocket> _ActiveGames = new ArrayList<>();
    
    //  Much like above observable active games 
    private final ObservableList<WorkingSocket> _ObservableActiveGames = FXCollections.observableArrayList(_ActiveGames);

    // Currently network running; true or false
    private volatile boolean _NetworkSessionCurrent = false;
    
    // Terminal style Network system tracker
    private NetworkConsoleDisplay _NetworkConsoleDisplay;
    
    // The network socket which workstations connect to
    private ServerSocket _NetworkSocket;
    
    // Manages asynchronous threading for workstations
    private ExecutorService _WorkStationThreads;

    // Main thread for network
    private Thread _NetworkThread;
    
    // Allows incoming; true or false if accepted.
    private boolean _NetworkThreadAllowsConnection = false;
    
    // Allows workstation connections and creates new Network session object. Running on the _NetworkThread
    private final Runnable AllowWorkstationConnections = () ->
    {
        while (_NetworkThreadAllowsConnection && _NetworkSessionCurrent) {
            try {
                Socket aWorkstation = _NetworkSocket.accept();
                gameWaitingAreaLog("Workstation connection allowed: " + aWorkstation.getInetAddress().toString());
                _WorkStationSocket.add(aWorkstation);

                // Create new Network session check if the session has i/o streams for the socket else error message and close socket remove from list
                NetworkSession networkSession = new NetworkSession(aWorkstation, _NetworkConsoleDisplay, _ObservableWorkstationClientLobby, _ObservableActiveGames);
                if (networkSession.StartStreams()) {
                    _WorkstationSessions.add(networkSession);
                    _WorkStationThreads.submit(networkSession);
                } else {
                    gameWaitingAreaLog("Input/Output stream is INVALID");
                    aWorkstation.close();
                    _WorkStationSocket.remove(aWorkstation);
                }

            } catch (SocketException socketerror) {
            
            // Check if the thread is accepting connections if not simply catch the error/as is dealt with by main thread
                if (_NetworkThreadAllowsConnection && _NetworkSessionCurrent) {
                    gameWaitingArea("ERROR WITH SOCKET: " + socketerror.getMessage());
                }
            } 
            // catches other errors
            catch (Exception e) { 
                if (_NetworkThreadAllowsConnection && _NetworkSessionCurrent) {
                    gameWaitingArea("ERROR WITH WORSTATION CONNECTION: " + e.getMessage());
                }
            }

            if ((!_NetworkThreadAllowsConnection) || (!_NetworkSessionCurrent)) {
                gameWaitingArea("Main socket thread with network is no longer listening.");
            }
        }

    };

    // Active game identifier
    private int _ActiveGameIdentifier = 1;

    
    // Constructors for the Network server with TCP connections. 
    public NetworkServer(NetworkConsoleDisplay _MessagesTerminal, Network _Network) {
        this._NetworkConsoleDisplay = _MessagesTerminal;
        this._Network = _Network;

        //Add listener and event handler for the Observable Lobby changes
        _ObservableWorkstationClientLobby.addListener(this::RemoteLobbyChange);
    }

    // Remote for controlling how the lobby changes are manages i.e. a player leaving/joining 
    private void RemoteLobbyChange(MapChangeListener.Change<? extends Socket, ? extends String> changes) {
        if (changes.wasAdded()) {
            gameWaitingAreaLog("Player connected waiting... \"" + changes.getValueAdded() + "\" map.");

            //Check if there is a matching player.
            CheckForActiveGameInLobby(changes.getValueAdded(), changes.getKey());
        }

        if (changes.wasRemoved()) {
            gameWaitingAreaLog("Player disconnected: " + changes.getKey().getInetAddress().toString());
        }

    }

    // Check for game exist for both players if so then allow creation of game and remove from waiting area
    private void CheckForActiveGameInLobby(String val, Socket key) {
        boolean RemoveFromGameLobby = false;
        Socket keyToDelete = null;

        //Check that more than one workstation 
        if (_ObservableWorkstationClientLobby.size() > 1) {
            // Search for different keys
            for (Map.Entry<Socket, String> i : _ObservableWorkstationClientLobby.entrySet()) {
                if (i.getValue().equals(val) && !i.getKey().equals(key)) {
                    //SystemMessage printed
                    gameWaitingAreaLog("Active game session:  \"" + Integer.toString(_ActiveGameIdentifier) + "\" created.");

                    //Instantiating a new match and adding to the list.
                    // Note: the first requesting player becomes player 1
                    WorkingSocket ActiveGame = new WorkingSocket(i.getKey(), key, val, _ActiveGameIdentifier, this);
                    _ObservableActiveGames.add(ActiveGame);
                    _ActiveGameIdentifier++;

                    //Mark to read the matched keys
                    RemoveFromGameLobby = true;
                    keyToDelete = i.getKey();

                    break;
                }
            }

            //As match found, delete the players from the waiting lobby.
            if (RemoveFromGameLobby) {
                //Removing matched players from the lobby.
                _ObservableWorkstationClientLobby.remove(key);
                _ObservableWorkstationClientLobby.remove(keyToDelete);
            }

        }
    }

    // Is sever session current returns active server
    public boolean IsNetworkSessionCurrent() {
        return _NetworkSessionCurrent;
    }

    // Start the network with port number
    public void NetworkStart(int portNumber) {
        if (!_NetworkSessionCurrent) {
            gameWaitingAreaLog("Starting network, please wait...");

            //Create socket with port number
            if (NetworkSocketCreation(portNumber)) {
                gameWaitingAreaLog("NetworkServer socket created on port " + Integer.toString(portNumber) + ".");
                InitiateWorkStationThreads();
                _NetworkThreadAllowsConnection = true;

                _NetworkThread = new Thread(AllowWorkstationConnections);
                _NetworkThread.start();

                _NetworkSessionCurrent = true;
                _Network.NetworkIsRunning();
            } else {
                _NetworkSessionCurrent = false;
                gameWaitingAreaLog("ERROR. There is an issue with starting the server.");
            }

        } else {
            _NetworkSessionCurrent = false;
            gameWaitingAreaLog("ERROR. NetworkServer an instance is already running.");
        }
    }

    // Kills network and sends it out notification
    public void KillNetwork() {
        if (_NetworkSessionCurrent) {
            gameWaitingAreaLog("Network is shutting down...");
            GlobalNetworkShutdownMessage();
            _NetworkSessionCurrent = false;
            _NetworkThreadAllowsConnection = false;


            if (NetowrkSocketTryShutdown()) {
                gameWaitingAreaLog("Network socket is now shutdown");
            } else {
                gameWaitingAreaLog("Can not close socket at this time");
            }

            _ActiveGames.clear();
            _WorkstationSessions.clear();
            _WorkStationSocket.clear();
            _WorkstationClientLobby.clear();
            _NetworkSessionCurrent = false;
            _Network.NetworkIsNotActive();
            gameWaitingAreaLog("Network has stopped.");

        } else {
            gameWaitingAreaLog("Network isn't active.");
        }
    }

    // Gloabl network shutdown message to open sockets
    private void GlobalNetworkShutdownMessage() {
        SystemMessage systemMessage = new SystemMessage(SystemMessageType.NETWORKDOWNTIME);
        if (_WorkStationSocket != null) {
            for (NetworkSession _NetworkSession : _WorkstationSessions) {

                if (_NetworkSession.SendAnnouncement(systemMessage)) {
                    gameWaitingAreaLog("Network: SystemMessage down sent");
                } else {
                    gameWaitingAreaLog("Network: Attemp to sent down message, workstation down");
                }
                _NetworkSession.NetoworkClosedAnnouncement();
            }
        }
    }

    // Management of all threads
    private void InitiateWorkStationThreads() {
        _WorkStationThreads = Executors.newCachedThreadPool();
    }
   
    // Network socket try to shutdown if it is not null; true if close, false if not or non existent
    private boolean NetowrkSocketTryShutdown() {
        boolean result = true;

        if (_NetworkSocket != null) {
            if (!_NetworkSocket.isClosed()) {
                try {
                    _NetworkSocket.close();
                } catch (IOException e) {
                    result = false;
                }
            }
        }
        return result;
    }

    
    // Creation of the network socket on port
    private boolean NetworkSocketCreation(int port) {
        boolean result = true;
        try {
            _NetworkSocket = new ServerSocket(port);
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    // Game waiting area which displays text of the current server
    private void gameWaitingAreaLog(String text) {
        final LocalDateTime now = LocalDateTime.now();
        String time = now.toLocalTime().toString();

        final String sender = "SERVER: ";

        String message = time + ", " + sender + text;
        _NetworkConsoleDisplay.LogMessages(message);

    }


    // This a synchronous version of the main thread which displays general message 
    private synchronized void gameWaitingArea(String text) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                gameWaitingAreaLog(text);
            }
        };
        SwingUtilities.invokeLater(runnable);
    }

    //Gets the host name from the workstation
    public String GetHostNameAddress() {
        String HostNameAddress = "";
        try {
            HostNameAddress = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            HostNameAddress = "INVALID.";
        }
        return HostNameAddress;
    }

    
    // Announces ended game
    public void endedGameAnnouncement(WorkingSocket workingSocket, int gameIdentfier) {
        gameWaitingArea("Game: \"" + Integer.toString(gameIdentfier) + "\" complete.");
        _ActiveGames.remove(workingSocket);
    }

    // Returns Network console display
    public NetworkConsoleDisplay geNetworkConsoleDisplay() {
        return _NetworkConsoleDisplay;
    }
}
