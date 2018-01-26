package models.Server.Communication;


import java.io.Serializable;

// System messages between workstations and network
public class SystemMessage implements Serializable {
	// Message type
    protected final int Type;
    // System message type the message type request
    public SystemMessage(int _Type) {
        Type = _Type;
    }

    // get and return Type
    public int getType() {
        return Type;
    }
    private static final long serialVersionUID = 1L;
}
