package models.Server.Communication;

// Communication for the server and client/workstation interaction
// Provides enumerative casting which below has descriptive types of messages 
public class SystemMessageType {

	// First message sent to network when connected
    public static final int GREETINGS = 0;
    // Exit message which is given before closing of socket
    public static final int EXIT = 1;
    // Workstation sends to network to search for opponents 
    public static final int SEARCHINGFOROTHERPLAYER = 2;
    // Workstation sends to network to cancel search for opponents
    public static final int CANCELSEARCHPLAYER = 3;
    // Workstation sends and finds opponents; game initiated
    public static final int PLAYERFOUNDINITIATEGAME = 4;
    // Workstation provides velocity and location
    public static final int BOATMOVEMENTCHANGE = 6;
    // Workstation provides crash state; game complete
    public static final int BOATCRASH = 7;
    // Workstation disconnects from network
    public static final int PLAYERDISCONNECT = 8;
    // Workstation disconnects from network (unknown)
    public static final int GAMEINACTIVE = 9;
    // Workstation disconnects from network, network down and provides messages
    public static final int NETWORKDOWNTIME = 10;
}
