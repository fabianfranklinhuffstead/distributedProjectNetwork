package models.Server.Communication;

import java.io.Serializable;

// Network system message response for workstation/player request
public class SystemMessageResponse extends NetworkMessageRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// Gives users a number given via network in global resources
    private int _AssignmentForPlayer;

    // Network/workstation response  
    public SystemMessageResponse(int _Type) {
        super(_Type);
    }
    //retusn the assignment
    public int get_AssignmentForPlayer() {
        return _AssignmentForPlayer;
    }

    public void set_PlayerAsssignment(int _AssignmentForPlayer) {
        this._AssignmentForPlayer = _AssignmentForPlayer;
    }
}
