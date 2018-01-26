package models.ManagementCollision;

import java.awt.*;
import java.awt.geom.Line2D;

import models.EnumsType.ObjectTypeInGame;

//This is the map object class is used for evaluation of collision
public class ObjectOfMap
{
    // This is the line attribute
    public Line2D LineValue;

    // This is the object it cold be the outer, center or other
    public ObjectTypeInGame TypeInGame;

    // This is the attribute of rectangle which are rectangles
    public Rectangle Rectangle;
}
