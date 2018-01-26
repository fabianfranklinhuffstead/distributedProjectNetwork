package models.Server.Communication;

import java.io.Serializable;

// The network server puts workstation in waiting area
public class NetworkMessageRequest extends SystemMessage implements Serializable {

    // Name of map for all players to play on network
    protected String _MapName;

    // boat image file index to display correct images for all players
    protected int _boatImageFileIndex;

    // A message between workstation and network
    public NetworkMessageRequest(int messageType) {
        super(messageType);
    }

    public String get_MapName() {
        return _MapName;
    }

    public void set_MapName(String _MapName) {
        this._MapName = _MapName;
    }

    public int get_BoatImageFileIndex() {
        return _boatImageFileIndex;
    }

    public void set_boatImageFileIndex(int _boatImageFileIndex) {
        this._boatImageFileIndex = _boatImageFileIndex;
    }
}
