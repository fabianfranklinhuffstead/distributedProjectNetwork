package models;


import controllers.GlobalResources;
import models.Server.Communication.BoatStatusMessage;
import views.Displays.DispGame.DisplayInGameBoat;

//Boat data handle for another workstation class (using boat) 
public class BoatDataHandle extends Boats {

    // Recursive call back to check for new changes to also connects to workstation client listening to server  
    public void StartRecursiveUpdates() {
        if (_WorkstationClient == null)
            _WorkstationClient = GlobalResources.MainControl.get_WorkstationClient();

        _WorkstationClient.NotifyMeOfboatUpdates(this);
        _WorkstationClient.ListenToBoatGameChanges();
    }


    // Boat update calls this when sending information on player updates for ingame
    public void boatUpdateCallback(BoatStatusMessage message) {
        _SpeedRatioConversion = message.get_InGameSpeed();
        _DisplayInGameBoat.setLocation(message.get_Position_Xaxis(), message.get_Position_Yaxis());

        // Updates change in the boat rotation
        if (_AngleBasedOn360 != message.get_AngleBasedOn360()) {
            _AngleBasedOn360 = message.get_AngleBasedOn360();
            int angleBasedOn360Index = super.GetIndexedAngleBasedOn360Index(message.get_AngleBasedOn360());
            _DisplayInGameBoat.SetImageOnangleBasedOn360Index(angleBasedOn360Index);
        }

        //Checks for boat acceleration
        _BoatAcceleration = message.is_BoatAcceleration();
        if (_BoatAcceleration)
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatAcceleration();
        else
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatAccelerationStop();

        // Start/Stop audio boat on impact
        if (message.is_onBoatCrash())
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatImpact();

    }

    // On crash call back 
    public void boatCrashCallback() {
        GlobalResources.MainControl.get_RuntimeEngine().OnBoatCrash();
    }

    // Sets in game display for Boat 
    @Override
    public void set_ImagesForBoat(DisplayInGameBoat _DisplayInGameBoat) {
        this._DisplayInGameBoat = _DisplayInGameBoat;
    }

    // Get in game speed and ping back to user from network
    @Override
    public int GetInGameSpeed() {
        return _SpeedRatioConversion;
    }

    // Killed below process with override as this is handled by network
    @Override
    public void set_AngleOfBoat(float _CurrentAngle) {
        //Kill process with override
    }

    @Override
    public void KeyPressedHandlers(int keyCode) {
        //Kill process with override
    }

    @Override
    public void KeyReleaseHandlers(int keyCode) {
        //Kill process with override
    }

    @Override
    public void FormulationForIteratedFrame() {
        //Kill process with override
    }


}
