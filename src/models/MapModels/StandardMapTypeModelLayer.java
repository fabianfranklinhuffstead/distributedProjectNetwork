package models.MapModels;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import models.EnumsType.ObjectTypeInGame;
import models.ManagementCollision.ObjectOfMap;

//Class for standard map
public class StandardMapTypeModelLayer extends GlobalMapModel
{
    // Starting map angles and texture map load
    public StandardMapTypeModelLayer() {
        Initial_Starting_Boat_Angles = 90;
        Initial_Boat_Starting_XValue_FirstPlayer = 375;
        Initial_Boat_Starting_YValue_FirstPlayer = 500;
        Initial_Boat_Starting_XValue_SecondPlayer = 375;
        Initial_Boat_Starting_YValue_SecondPlayer = 550;
        Map_HighResLowRes_Loader = "/mainImages/mapimages/StandardMap.png";
    }

    // Overrides map objects which can update 
    @Override
    protected void InitiateObjectsForMap()
    {
        _ObjectOfMaps = new ArrayList<>();
        ObjectOfMap temporaryValue;

        // Outer rectangle border
        int LeftValue_Border_X = 50;
        int RightValue_Border_X = 800;
        int TopVaue_Border_Y = 100;
        int BottomVaue_Border_Y = 600;

        // Top racing track line
        temporaryValue = new ObjectOfMap();
        temporaryValue.LineValue = new Line2D.Double();
        temporaryValue.LineValue.setLine(LeftValue_Border_X,TopVaue_Border_Y,RightValue_Border_X,TopVaue_Border_Y);
        temporaryValue.TypeInGame = ObjectTypeInGame.AREA_OUT_OF_BOUNDS;
        _ObjectOfMaps.add(temporaryValue);

        // Right racing track line
        temporaryValue = new ObjectOfMap();
        temporaryValue.LineValue = new Line2D.Double();
        temporaryValue.LineValue.setLine(LeftValue_Border_X,TopVaue_Border_Y,LeftValue_Border_X,BottomVaue_Border_Y);
        temporaryValue.TypeInGame = ObjectTypeInGame.AREA_OUT_OF_BOUNDS;
        _ObjectOfMaps.add(temporaryValue);

        // Bottom racing track line
        temporaryValue = new ObjectOfMap();
        temporaryValue.LineValue = new Line2D.Double();
        temporaryValue.LineValue.setLine(LeftValue_Border_X,BottomVaue_Border_Y,RightValue_Border_X,BottomVaue_Border_Y);
        temporaryValue.TypeInGame = ObjectTypeInGame.AREA_OUT_OF_BOUNDS;
        _ObjectOfMaps.add(temporaryValue);

        // Left racing track line
        temporaryValue = new ObjectOfMap();
        temporaryValue.LineValue = new Line2D.Double();
        temporaryValue.LineValue.setLine(RightValue_Border_X,TopVaue_Border_Y,RightValue_Border_X,BottomVaue_Border_Y);
        temporaryValue.TypeInGame = ObjectTypeInGame.AREA_OUT_OF_BOUNDS;
        _ObjectOfMaps.add(temporaryValue);

        //Inner Island area
        temporaryValue = new ObjectOfMap();
        temporaryValue.Rectangle = new Rectangle(150,200,550,300);
        temporaryValue.TypeInGame = ObjectTypeInGame.AREA_ISLAND;
        _ObjectOfMaps.add(temporaryValue);
    }



}
