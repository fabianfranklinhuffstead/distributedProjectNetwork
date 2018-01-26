package controllers;

import models.BoatDataHandle;
import models.Players;
import models.ManagementCollision.ManagementOfCollision;
import models.MapModels.GlobalMapModel;
import views.MapViews.GlobalResourceOfMapView;

import java.util.ArrayList;

//This is the current game session with all objects private and public relating
public class CurrentRuntimeSession
{
    //This represents players 1, 2
    private static ArrayList<Players> _PlayerAmount; 
    //The string name for map; Standard, Alternative (please refer to GlobalResources.ATTRIBUTESFORMAP_MapTitlesObject)
    private static String _MapTypeName; 
    //The collision manager
    private static ManagementOfCollision _ManagementOfCollision; 
    //This is the view for the map
    private static GlobalResourceOfMapView _GlobalResourceOfMapView; 
    //This is the map model for further collision detection
    private static GlobalMapModel _GlobalMapModel; 
    //Actual player index (logical player index for current player)
    private static int _IndexofActualPlayer; 
    //Actual player index of boat chosen
    private static int _PlayerSelectedBoatIndex; 
    //This sets the map view and is CALLED by GAME ENGINE
    public static GlobalResourceOfMapView get_GlobalResourceOfMapView() {
        return _GlobalResourceOfMapView;
    }

    //This sets the map view and is CALLED by GAME ENGINE
    public static void set_GlobalResourceOfMapView(GlobalResourceOfMapView _GlobalResourceOfMapView) {
        CurrentRuntimeSession._GlobalResourceOfMapView = _GlobalResourceOfMapView;
    }

    // Get model of map
    public static GlobalMapModel get_ModelOfMap() {
        return _GlobalMapModel;
    }

    //Set model of map
    public static void set_GlobalMapModel(GlobalMapModel _GlobalMapModel) {
        CurrentRuntimeSession._GlobalMapModel = _GlobalMapModel;
    }

    // Initiates player object
    public static void PlayerCreation() {
        _PlayerAmount = new ArrayList<>();
        _PlayerAmount.add(new Players("Player 1", false));
        _PlayerAmount.add(new Players("Player 2", true));
        _IndexofActualPlayer = 0;
    }

    //Get and return player amount list 1 or 2
    public static ArrayList<Players> get_PlayerAmount() {
        return _PlayerAmount;
    }

    //Returns map type name
    public static String get_SelectionMapTypeName() {
        return _MapTypeName;
    }

    //Sets the selected map type from global resources
    public static void set_SelectedMapTypeName(String _SelectedMapTypeName)
    {
        CurrentRuntimeSession._MapTypeName = _SelectedMapTypeName;
    }

    // Returns the management of the collision
    public static ManagementOfCollision get_ManagementOfCollision() {
        return _ManagementOfCollision;
    }

    //Sets the Management of the collision for the runtime engine
    public static void set_ManagementOfCollision(ManagementOfCollision _ManagementOfCollision) {
        CurrentRuntimeSession._ManagementOfCollision = _ManagementOfCollision;
    }

    //Restarts the session of the game 
    public static void RestartSessionOfGame()
    {
        _MapTypeName = "";
        _PlayerAmount = null;
        _ManagementOfCollision = null;
        _GlobalMapModel = null;
        _GlobalResourceOfMapView = null;
    }

    //Switch the players numbers to make sure they are in the corresponding order
    public static void AssignCorrectPlayerIndication() {
        Players t0 = _PlayerAmount.get(0);
        Players t1 = _PlayerAmount.get(1);
        _PlayerAmount.clear();
        _PlayerAmount.add(t1);
        _PlayerAmount.add(t0);
        _IndexofActualPlayer = 1;
    }

    //Get actual player index (local)
    public static int get_IndexofActualPlayer() {
        return _IndexofActualPlayer;
    }

    // Return player selected boat index  
    public static int get_PlayerSelectedBoatIndex() {
        return _PlayerSelectedBoatIndex;
    }
    
    // Set boat type index selected on launch
    public static void set_PlayerSelectedBoatIndex(int selectedBoatIndex) {
        _PlayerSelectedBoatIndex = selectedBoatIndex;
    }

    // Returns BoatDataHanle for management of external player 
    public static BoatDataHandle GetDataHandlerBoat() {
        BoatDataHandle result = null;
        if (_IndexofActualPlayer == 0) {
            result = (BoatDataHandle) _PlayerAmount.get(1).get_Boat();
        }

        if (_IndexofActualPlayer == 1) {
            result = (BoatDataHandle) _PlayerAmount.get(0).get_Boat();
        }

        return result;
    }
}
