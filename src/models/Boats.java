package models;

import controllers.CurrentRuntimeSession;
import controllers.GlobalResources;
import models.LoadingFiles.FileLoaderImage;
import models.Server.WorkstationClient;
import models.Server.Communication.BoatStatusMessage;
import models.Server.Communication.SystemMessageType;
import views.Displays.DispGame.DisplayInGameBoat;

import java.awt.*;
import java.util.HashSet;

// Class boat, forward and backward movement, collisions and conversions
public class Boats
{
	// Speed ratio conversion
    int _SpeedRatioConversion;

    // current angle based on 360•
    float _AngleBasedOn360;

    //This is the boat JLABEL display/ simple representation
    DisplayInGameBoat _DisplayInGameBoat;
    
    //This is the workstation client
    WorkstationClient _WorkstationClient;
    
    // Boat acceleration sent to the workstation client
    boolean _BoatAcceleration = false;

    // speed value
    private float _SpeedValue;

    // Behaviour Reverse mode
    private boolean _BehaviourReverse;

    // This is when on Island
    private boolean _BehaviourIsland;

    // When hits Rocks speed reduce to 0
    private boolean _BehaviourRocksCollision;
    
    // Collision with other boat
    private boolean _BehaviourCollisionWIthBoat;
    
    // boats image file index 
    private int _BoatIndexedImage = 0;
   
    // Hash set for current for key pressed
    private HashSet<Integer> _CurrentKeyPressed;
    
    // Set to false passed and to the network
    private boolean _ImpactOfBoatAudio = false;

    // On boat checker
    private boolean _OnBoatCrash = false;

    // Set current angle of boat
    public void set_AngleOfBoat(float _AngleOfBoat)
    {
        this._AngleBasedOn360 = _AngleOfBoat;
    }

    // Set current boat image 
    public void set_BoatIndexedImage(int _BoatIndexedImage) {
        this._BoatIndexedImage = _BoatIndexedImage;
    }

    // Loads boat images and returns into image array
    public Image[] ImageLoadersForBoat()
    {
        return FileLoaderImage.ImageLoad(_BoatIndexedImage);
    }

    // Method for handling key presses, no duplicates here
    public void KeyPressedHandlers(int keyCode) {
        // Use case on up pressed then follow this code
        if(keyCode == GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls[0] || keyCode == GlobalResources.ATTRIBUTESFORCONTROL_TwoPlayerControls[0] ) {
            _CurrentKeyPressed.add(keyCode);
            return;
        }
        // Use case on back pressed then follow this code
        if(keyCode == GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls[1] || keyCode == GlobalResources.ATTRIBUTESFORCONTROL_TwoPlayerControls[1] ) {
            _CurrentKeyPressed.add(keyCode); //No duplicates, as this is a hash set
            return;
        }
        // Use case on left pressed then follow this code
        if(keyCode == GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls[2] || keyCode == GlobalResources.ATTRIBUTESFORCONTROL_TwoPlayerControls[2] ) {
            TurningHandlers(keyCode, true);
            return;
        }
        // Use case on right pressed then follow this code
        if(keyCode == GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls[3] || keyCode == GlobalResources.ATTRIBUTESFORCONTROL_TwoPlayerControls[3] ) {
            TurningHandlers(keyCode,false);
        }
    }


    // Handles releases on keys
    public void KeyReleaseHandlers(int keyCode)
    {
        _CurrentKeyPressed.remove(keyCode);
    }

    // Handles turning as long as collision is not true for doing so
    private void TurningHandlers(int key, Boolean isLeft)
    {
        if (!_CurrentKeyPressed.contains(key)) {
            if (!CollisionTurnHandlers(_DisplayInGameBoat.GetBoatImageFromIndex(isLeft)))
            {
                _CurrentKeyPressed.add(key);
                int updatedDegreeIndex = _DisplayInGameBoat.Turn(isLeft);
                _AngleBasedOn360 = GlobalResources.BOATSINGAME_Angles[updatedDegreeIndex];
                _ImpactOfBoatAudio = false;
            }
            else {
                // Else the game engine is notified about collision and plays sound
                GlobalResources.MainControl.get_RuntimeEngine().OnBoatImpact();
                _ImpactOfBoatAudio = true;
            }
        }
    }

    // Returns true if the boat collides using a new angle
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean CollisionTurnHandlers(int collisionUpdatedDegreeIndex)
    {
        boolean result;
        Rectangle locationCurrent = _DisplayInGameBoat.getBounds();

        // Checks for crashing with other boats
        result = CurrentRuntimeSession.get_ManagementOfCollision().boatCollision(this, locationCurrent, collisionUpdatedDegreeIndex);
        if (result)
            return true;

        //Check for hitting edges/trees with a supposed turn
        result = CurrentRuntimeSession.get_ManagementOfCollision().outsideMapCollision(locationCurrent, collisionUpdatedDegreeIndex);
        return result;
    }

    // Calculates next frame by updating speed and location
    public void FormulationForIteratedFrame()
    {
        SpeedUpdate();
        FormulateUpdateLoation();

        if (!_OnBoatCrash)
            TransferCurrentStatusNetwork();
    }

    /**
     * Sends status updates to the server using the client.
     */
    private void TransferCurrentStatusNetwork() {
        if (_WorkstationClient == null)
            _WorkstationClient = GlobalResources.MainControl.get_WorkstationClient();

        BoatStatusMessage update = new BoatStatusMessage(SystemMessageType.BOATMOVEMENTCHANGE);
        update.set_ActiveAngle(_AngleBasedOn360);
        update.set_Position_Xaxis(_DisplayInGameBoat.getX());
        update.set_Position_Yaxis(_DisplayInGameBoat.getY());
        update.set_HUDvelocityValue(_SpeedRatioConversion);
        update.set_BoatAcceleration(_BoatAcceleration);
        update.set_boatAudioImpact(_ImpactOfBoatAudio);
        _WorkstationClient.StatusMessage(update);
    }

    // Calculates new location
    private void FormulateUpdateLoation()
    {
        Point location = _DisplayInGameBoat.getLocation();

        // Switch statement for calculations on movement
        switch (Math.round(_AngleBasedOn360)) {
        		// boats point up then move up Y+
            case 0:
                location.translate(0,CheckIfReverse(0 - Math.round(_SpeedValue) ));
                break;

             // boats point down then move up Y-
            case 180:
                location.translate(0, CheckIfReverse(Math.round(_SpeedValue)));
                break;

            // boats point left then move up X-
            case 90:
                location.translate(CheckIfReverse(Math.round(_SpeedValue)),0);
                break;

             // boats point right then move up X+
            case 270:
                location.translate(CheckIfReverse(0 - Math.round(_SpeedValue)), 0);
                break;
            default:
                //Use trigonometric functions to calculate the new location
                location = ForumationForNewLocation(location);
                break;
        }
        //Checks for collision on updated values
        CollisionCheckerBehaviourHandlers(location); 
    }

    // Check for collision with Island, boat and edges
    private void CollisionCheckerBehaviourHandlers(Point locationNew) {
        Rectangle r = GetRectangleBoundaries(locationNew);

        //Checking for collision with other boat.
        _BehaviourCollisionWIthBoat = CurrentRuntimeSession.get_ManagementOfCollision().boatCollision(this, r, GetIndexedAngleBasedOn360Index(_AngleBasedOn360));
        if (_BehaviourCollisionWIthBoat) {
            _SpeedValue = 0;
            _OnBoatCrash = true;
            // Pushes this info to workstation client
            _WorkstationClient.SendboatCrashedMessage(); 
            // Pushes this info to game engine
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatCrash();
            return;
        }

        //Check if the boat would hit a rock and the speed needed to be zeroed
        _BehaviourRocksCollision = CurrentRuntimeSession.get_ManagementOfCollision().outsideMapCollision(r, GetIndexedAngleBasedOn360Index(_AngleBasedOn360));
        if (!_BehaviourRocksCollision) {
            _ImpactOfBoatAudio = false;
            // Collision sets location as it is
            _DisplayInGameBoat.setLocation(locationNew);
        } else {
        		// In case of hitting the edges
            _SpeedValue = 0;
            // Call on boat impact
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatImpact(); 
            _ImpactOfBoatAudio = true;
            return;
        }

        // The boat speed is reduce on non driving center area
        _BehaviourIsland = CurrentRuntimeSession.get_ManagementOfCollision().IslandCollision(r, GetIndexedAngleBasedOn360Index(_AngleBasedOn360));
    }

    // Gets current angle and returns
    public int GetIndexedAngle()
    {
        return GetIndexedAngleBasedOn360Index(_AngleBasedOn360);
    }

    // Gets the angle in the array and returns
    int GetIndexedAngleBasedOn360Index(float floatangle) {
        int i;
        for(i = 0; i < GlobalResources.BOATSINGAME_Angles.length; i++)
        {
            if (GlobalResources.BOATSINGAME_Angles[i] == floatangle)
                return i;
        }
        return i;
    }

    // Creates a rectangle to match the image size
    public Rectangle GetDisplayInGameBoat() { 
    		return _DisplayInGameBoat.getBounds();
    }
    
    // Creates a rectangle to match the image size
    private Rectangle GetRectangleBoundaries(Point initialLocation)
    {
        return new Rectangle(initialLocation.x, initialLocation.y, GlobalResources.BOATSINGAME_SizeWidth ,GlobalResources.BOATSINGAME_SizeHeight);
    }


    // This checks for reverse speed and returns invert value if true
    private int CheckIfReverse(int updatedSpeed)
    {
        if (!_BehaviourReverse)
        {
            return updatedSpeed;
        }
        else
        {
            return (0 - updatedSpeed);
        }
    }

    /// New location basing the the logic of formulation which is similar to how a X - Y graph is represented
    private Point ForumationForNewLocation(Point locationCurrent)
    {
    		// This is for the angle radius
        double radianAngle = Math.toRadians(_AngleBasedOn360);
        // for the X axis formulation = SIN * speed is used
        double sine = Math.sin(radianAngle);
        double doubleX = _SpeedValue * sine;
        // for the Y axis formulation = COS * speed is used
        double cosine = Math.cos(radianAngle);
        double doubleY = 0- (_SpeedValue * cosine);

        locationCurrent.translate(CheckIfReverse((int) Math.round(doubleX)), CheckIfReverse((int) Math.round(doubleY)));
        return locationCurrent;
    }

    // Update speed method with if else statement for each corresponding method
    private void SpeedUpdate()
    {
        boolean onForwardButtonPress = _CurrentKeyPressed.contains(GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls[0]) || _CurrentKeyPressed.contains(GlobalResources.ATTRIBUTESFORCONTROL_TwoPlayerControls[0]);
        boolean onBackButtonPress = _CurrentKeyPressed.contains(GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls[1]) || _CurrentKeyPressed.contains(GlobalResources.ATTRIBUTESFORCONTROL_TwoPlayerControls[1]);
        boolean onForwardBackButtonPress = onForwardButtonPress && onBackButtonPress;
        boolean onForwardBackButtonNotPress = !(onForwardButtonPress || onBackButtonPress);
        boolean onStop = (_SpeedValue == 0);

        if(onForwardBackButtonPress || onForwardBackButtonNotPress)
        {
            BoatSlowMovement();
            return;
        }

        if (onForwardButtonPress && !_BehaviourReverse)
        {
            BoatAcceleration();
            return;
        }

        if (onForwardButtonPress && _BehaviourReverse)
        {
            if (onStop)
            {
                _BehaviourReverse = false;
                BoatAcceleration();
                return;
            }
            else
            {
                BoatSlowMovement();
                return;
            }
        }

        if (onBackButtonPress && _BehaviourReverse)
        {
            Acceleration(true, _BehaviourIsland);
            return;
        }

        if (onBackButtonPress && !_BehaviourReverse)
        {
            if (onStop)
            {
                _BehaviourReverse = true;
                Acceleration(true, _BehaviourIsland);
            }
            else
            {
                BoatSlowMovement();
            }
        }
    }

    // Accelerate method and rates of increase
    private void Acceleration(boolean reverseDirectionSpeed, boolean onIslandSpeed)
    {
        // If else depending on boat speed reverse or forward
        float MaxSpeed;
        if (!reverseDirectionSpeed)
            MaxSpeed = GlobalResources.ATTRIBUTESFORCONTROL_MaxSpeed;
        else
            MaxSpeed = GlobalResources.ATTRIBUTESFORCONTROL_MaxReverseSpeed;

        // Overrides speed when on Islands
        if (onIslandSpeed)
            MaxSpeed = GlobalResources.ATTRIBUTESFORCONTROL_MaxIslandSpeed;

        // Returns no speed if boat acceleration is not needed
        if (_SpeedValue == MaxSpeed)
        {
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatAccelerationStop();
            _BoatAcceleration = false;
            return;
        }

        // Handles for speed quotas seen in shared resources
        if(_SpeedValue < MaxSpeed)
        {
            // boats initial start will give fixed boost
            if (_SpeedValue == 0) {
                _SpeedValue = GlobalResources.ATTRIBUTESFORCONTROL_MaxSpeed * GlobalResources.ATTRIBUTESFORCONTROL_ProgressiveStart50;
                GlobalResources.MainControl.get_RuntimeEngine().OnBoatAcceleration();
                _BoatAcceleration = true;
            }
            // Continue behaviour on lower speeds
            else if (_SpeedValue < (GlobalResources.ATTRIBUTESFORCONTROL_MaxSpeed * GlobalResources.ATTRIBUTESFORCONTROL_ProgressiveStart25)) {
                _SpeedValue *= GlobalResources.ATTRIBUTESFORCONTROL_MajorAcceleration150;
                GlobalResources.MainControl.get_RuntimeEngine().OnBoatAcceleration();
                _BoatAcceleration = true;
            }
            // Speeds are met in the middle at medium speeds
            else if (_SpeedValue < (GlobalResources.ATTRIBUTESFORCONTROL_MaxSpeed * GlobalResources.ATTRIBUTESFORCONTROL_NeutralAcceleration60)) {
                _SpeedValue *= GlobalResources.ATTRIBUTESFORCONTROL_NeutralAcceleration120;
                GlobalResources.MainControl.get_RuntimeEngine().OnBoatAcceleration();
                _BoatAcceleration = true;
            }
            // On higher speeds speeds no longer increase and have limit
            else {
                _SpeedValue *= GlobalResources.ATTRIBUTESFORCONTROL_MajorAcceleration110;
                GlobalResources.MainControl.get_RuntimeEngine().OnBoatAcceleration();
                _BoatAcceleration = true;
            }

            // This prevents it from going over a great way of managing the version of 1 - 10 speed ratio
            if(_SpeedValue > MaxSpeed)
            {
                _SpeedValue = MaxSpeed;
                GlobalResources.MainControl.get_RuntimeEngine().OnBoatAccelerationStop();
                _BoatAcceleration = false;
            }
        }

     // If speed is greater than speed allowed then it is slowed calling slow down
        if(_SpeedValue > MaxSpeed)
        {
            GlobalResources.MainControl.get_RuntimeEngine().OnBoatAccelerationStop();
            _BoatAcceleration = false;
            BoatSlowMovement();
        }
    }

    // Acceleration set to false on Islands
    private void BoatAcceleration()
    {
        Acceleration(false, _BehaviourIsland);
    }

    // Speed of boat is reduced if it is not = 0 and is multiplied by ratio
    private void BoatSlowMovement()
    {
        _BoatAcceleration = false;
        //Lower the speed, based on current speed.
        if(_SpeedValue <= GlobalResources.ATTRIBUTESFORCONTROL_DecreaseAcceleration5)
        {
            _SpeedValue = 0;
            _BehaviourReverse = false;
        }
        else
        {
            _SpeedValue *= GlobalResources.ATTRIBUTESFORCONTROL_DecreaseAcceleration90;
        }
    }

    // Gets boat display and returns this value
    public DisplayInGameBoat get_DisplayInGameBoat()
    {
        return _DisplayInGameBoat;
    }


    // Sets boat display
    public void set_ImagesForBoat(DisplayInGameBoat _boatDisplay) {
        this._DisplayInGameBoat = _boatDisplay;
        _BehaviourReverse = false;
        _BehaviourIsland = false;
        _BehaviourRocksCollision = false;
        _BehaviourCollisionWIthBoat = false;
        _SpeedValue = 0;
        _AngleBasedOn360 = 0;
        _CurrentKeyPressed = new HashSet<>(20);
    }

    // Gets the speed which is displayed for the HUD
    public int GetInGameSpeed()
    {
        float _GetInGameSpeed = GlobalResources.ATTRIBUTESFORCONTROL_InGameMaxSpeed / GlobalResources.ATTRIBUTESFORCONTROL_MaxSpeed;
        int returnedRatio = (int)(_SpeedValue * _GetInGameSpeed);

        // Shows maximum speed
        if(_SpeedValue >= GlobalResources.ATTRIBUTESFORCONTROL_MaxSpeed)
            returnedRatio = GlobalResources.ATTRIBUTESFORCONTROL_InGameMaxSpeed;

        // If boat collides with Rocks then result is 0
        if (_BehaviourRocksCollision)
            returnedRatio = 0;
        
        // If speed <= to threshold/showing limit then result is 0 on display
        if (_SpeedValue <= GlobalResources.ATTRIBUTESFORHUD_SpeedMask60)
            returnedRatio = 0;

        _SpeedRatioConversion = returnedRatio;
        return returnedRatio;
    }

    // Gets and returns crash boat image
    public Image ImageCrashedLoaders()
    {
        return FileLoaderImage.ReturnCrashImage();
    }
}
