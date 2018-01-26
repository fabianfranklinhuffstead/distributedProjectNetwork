package models.ManagementCollision;

import controllers.GlobalResources;
import models.Boats;
import models.Players;
import models.EnumsType.ObjectTypeInGame;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.stream.Stream;

//Class collision for map objects and boats
public class ManagementOfCollision
{
    // List array of players
    private final ArrayList<Players> _Players;
    // List of map objects
    private final ArrayList<ObjectOfMap> _ObjectOfMap;

    // Array list of players and array list of map objects
    public ManagementOfCollision(ArrayList<Players> Players, ArrayList<ObjectOfMap> ObjectOfMaps)
    {
        this._Players = Players;
        this._ObjectOfMap = ObjectOfMaps;
    }

    // This is a collision checker between map objected i.e. edges and others and returns results
    public boolean outsideMapCollision(Rectangle rect, int IndexOfAngle)
    {
        boolean reaction;
        Stream<ObjectOfMap> notallowedarea =  _ObjectOfMap.stream().filter(m -> (m.TypeInGame == ObjectTypeInGame.AREA_OUT_OF_BOUNDS || m.TypeInGame == ObjectTypeInGame.AREA_OTHER) );
        reaction = notallowedarea.anyMatch(i -> (intersectingObjects(rect, IndexOfAngle, i.Rectangle) || intersectingObjects(rect, IndexOfAngle, i.LineValue)));
        return  reaction;
    }

    // This is a collision checker between center area and returns result
    public boolean IslandCollision(Rectangle rect, int IndexOfAngle)
    {
        boolean reaction;
        Stream<ObjectOfMap> IslandArea =  _ObjectOfMap.stream().filter(m -> (m.TypeInGame == ObjectTypeInGame.AREA_ISLAND) );
        reaction = IslandArea.anyMatch(i -> (intersectingObjects(rect, IndexOfAngle, i.Rectangle) || intersectingObjects(rect, IndexOfAngle, i.LineValue)));
        return  reaction;
    }

    // This is a collision checker between two boats and returns the result
    public boolean boatCollision(Boats checker, Rectangle positionOnDisplay, int IndexOfAngle)
    {
        boolean reaction;
        Stream<Players> otherPlayers = _Players.stream().filter(p -> p.get_Boat() != checker);
        reaction = otherPlayers.anyMatch(p -> intersectingObjects(positionOnDisplay, IndexOfAngle, p.get_Boat().GetDisplayInGameBoat(), p.get_Boat().GetIndexedAngle()));
        return reaction;
    }

    // This is a intersection checker for the two boat and returns the result
    private boolean intersectingObjects(Rectangle firstBoat, int firstBoatAngle, Rectangle secondBoat, int secondBoatAngle)
    {
        boolean reaction = false;
        Line2D[] boatLines1 = GlobalResources.BOATSINGAME_BoundsOfBoat[firstBoatAngle].GetBoundaryLinesForBoats(firstBoat);
        Line2D[] boatLines2 = GlobalResources.BOATSINGAME_BoundsOfBoat[secondBoatAngle].GetBoundaryLinesForBoats(secondBoat);

        // Takes all lines around each vehicle /rectangle and markers comparison, then returns result
        for (Line2D firstBoatLine1 : boatLines1) {
            for (Line2D secondBoatLine2 : boatLines2) {
                reaction = firstBoatLine1.intersectsLine(secondBoatLine2);
                if (reaction)
                    return reaction;
            }
        }
        return reaction;
    }


    // This is a collision checker to see if a boat intersects with with another boat/rectangle returns true on collision
    private boolean intersectingObjects(Rectangle boats, int AngleIndexForBoat, Rectangle rect2) {
        return !(rect2 == null || boats == null) && intersectingObjects(boats, AngleIndexForBoat, rect2, true, false);
    }

    // This is a collision checker the boat with a line as before returns true on collision with line
    private boolean intersectingObjects(Rectangle boats, int AngleIndexForBoat, Line2D l) {
        return !(l == null || boats == null) && intersectingObjects(boats, AngleIndexForBoat, l, false, true);
    }

    // This checks is a boolean operation to provide returns on true collision with objects
    private boolean intersectingObjects(Rectangle boats, int AngleIndexForBoat, Object o, Boolean rectangleObject, Boolean lineObject)
    {
        Line2D temporaryLine = null;
        Rectangle temporaryRectangle = null;
        if (rectangleObject)
            temporaryRectangle = ((Rectangle) o);
        if (lineObject)
            temporaryLine = ((Line2D) o);

        boolean reaction = false;
        Line2D[] boatIntersectionLines = GlobalResources.BOATSINGAME_BoundsOfBoat[AngleIndexForBoat].GetBoundaryLinesForBoats(boats);

        for (Line2D boatIntersectionLine : boatIntersectionLines) {
            if (rectangleObject) {
                reaction = temporaryRectangle.intersectsLine(boatIntersectionLine);
                if (reaction)
                    return reaction;
            }

            if (lineObject) {
                reaction = temporaryLine.intersectsLine(boatIntersectionLine);
                if (reaction)
                    return reaction;
            }
        }
        return reaction;
    }



}
