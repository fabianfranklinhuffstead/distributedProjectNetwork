package models.Server.Communication;

import java.io.Serializable;

// Boat status message of what the boats behaviour is on display
public class BoatStatusMessage extends SystemMessage implements Serializable {

    //Angle of boat on display
    private float _BoatAngle;

    //Position x_axis on display
    private int _position_Xaxis;

    //Position Y_axis on display
    private int _position_yAxis;

    // HUD velocity value
    private int _HUDvelocityValue;

    // On boat acceleration
    private boolean _onAcceleration;

    // On boat crash
    private boolean _onBoatCrash;

    // Boat status message used by network
    public BoatStatusMessage(int messaegType) {
        super(messaegType);
    }

    // returns boat angle
    public float get_AngleBasedOn360() {
        return _BoatAngle;
    }

    public void set_ActiveAngle(float ActiveAngle) {
        this._BoatAngle = ActiveAngle;
    }
    
    //returns position xAxis
    public int get_Position_Xaxis() {
        return _position_Xaxis;
    }

    public void set_Position_Xaxis(int Position_Xaxis) {
        this._position_Xaxis = Position_Xaxis;
    }
    	
    //returns position YAxis
    public int get_Position_Yaxis() {
        return _position_yAxis;
    }

    public void set_Position_Yaxis(int Position_Yaxis) {
        this._position_yAxis = Position_Yaxis;
    }

    // returns HUD velocity
    public int get_InGameSpeed() {
        return _HUDvelocityValue;
    }

    public void set_HUDvelocityValue(int _SpeedValue) {
        this._HUDvelocityValue = _SpeedValue;
    }

    //returns boat acceleration
    public boolean is_BoatAcceleration() {
        return _onAcceleration;
    }

    public void set_BoatAcceleration(boolean _BoatAcceleration) {
        this._onAcceleration = _BoatAcceleration;
    }

    // returns boat crash
    public boolean is_onBoatCrash() {
        return _onBoatCrash;
    }

    public void set_boatAudioImpact(boolean _boatImpactSoundToPlay) {
        this._onBoatCrash = _boatImpactSoundToPlay;
    }


}
