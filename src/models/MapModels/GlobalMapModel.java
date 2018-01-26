package models.MapModels;

import java.awt.*;
import java.util.ArrayList;

import models.LoadingFiles.FileLoaderImage;
import models.ManagementCollision.ObjectOfMap;

//Map model to describe any map generated to be used with children map types
public class GlobalMapModel
{
    // Array list of map objects
    static ArrayList<ObjectOfMap> _ObjectOfMaps;

    // Starting boat angle integer
    int Initial_Starting_Boat_Angles = 0;

    // Starting boat 1 X point 
    int Initial_Boat_Starting_XValue_FirstPlayer = 0;

    // Starting boat 1 Y point 
    int Initial_Boat_Starting_YValue_FirstPlayer = 0;

    // Starting boat 2 Y point 
    int Initial_Boat_Starting_XValue_SecondPlayer = 0;

    // Starting boat 2 Y point 
    int Initial_Boat_Starting_YValue_SecondPlayer = 0;

    // Map texture file loader
    String Map_HighResLowRes_Loader = "";

    // Gets and returns starting angle 
    public int getInitial_Starting_Boat_Angles() {
        return Initial_Starting_Boat_Angles;
    }

    // Get boat 1 X and return
    public int getInitial_Boat_Starting_XValue_FirstPlayer() {
        return Initial_Boat_Starting_XValue_FirstPlayer;
    }

    // Get boat 1 Y and return
    public int getInitial_Boat_Starting_YValue_FirstPlayer() {
        return Initial_Boat_Starting_YValue_FirstPlayer;
    }

    // Get boat 2 X and return
    public int getInitial_Boat_Starting_XValue_SecondPlayer() {
        return Initial_Boat_Starting_XValue_SecondPlayer;
    }

    // Get boat 2 Y and return
    public int getInitial_Boat_Starting_YValue_SecondPlayer() {
        return Initial_Boat_Starting_YValue_SecondPlayer;
    }

    // Get map texture and return
    public Image GetMapHighResLowResMap() {
        return FileLoaderImage.ReaderForImages(Map_HighResLowRes_Loader);
    }

    // Get map objects needed for collision detection and return
    public  ArrayList<ObjectOfMap> GetObjectofMap()
    {
        InitiateObjectsForMap();
        return _ObjectOfMaps;
    }

    // Create map objects
    void InitiateObjectsForMap() {
    	// Implemented from the children types
    }
}
