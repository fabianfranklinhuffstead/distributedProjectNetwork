package models.ManagementCollision;

import java.awt.*;
import java.awt.geom.Line2D;

//boats bounds class possible angles and line precision for rectangle shape
public class BoundsOfBoat
{
    private final Line2D[] _BoundaryLinesForBoats;

    // boats bounds contains the rectangle shape for edge point
    public BoundsOfBoat(int point1x, int point1y, int point2x, int point2y, int point3x, int point3y, int point4x, int point4y)
    {
        // point1x upper left point1y upper left
        Point _Point1 = new Point(point1x, point1y);
        // point2x upper right point2y upper right
        Point _Point2 = new Point(point2x, point2y);
        // point3x lower right point3y Top lower right
        Point _Point3 = new Point(point3x, point3y);
        // point4x lower left point1y lower left
        Point _Point4 = new Point(point4x, point4y);

        Line2D _Line1 = new Line2D.Double();
        Line2D _Line2 = new Line2D.Double();
        Line2D _Line3 = new Line2D.Double();
        Line2D _Line4 = new Line2D.Double();

        _Line1.setLine(_Point1, _Point2);
        _Line2.setLine(_Point2, _Point3);
        _Line3.setLine(_Point3, _Point4);
        _Line4.setLine(_Point4, _Point1);

        _BoundaryLinesForBoats = new Line2D[4];
        _BoundaryLinesForBoats[0] = _Line1;
        _BoundaryLinesForBoats[1] = _Line2;
        _BoundaryLinesForBoats[2] = _Line3;
        _BoundaryLinesForBoats[3] = _Line4;
    }


    // This returns the array of 4 lines forming a rectangle for the boat and returns the result of changed positions
    public Line2D[] GetBoundaryLinesForBoats(Rectangle boatPositionsChanged)
    {
        Line2D[] returnedPositionChanges = new Line2D[4];

        for(int i = 0; i<4; i++)
        {
            returnedPositionChanges[i] = new Line2D.Double();
            returnedPositionChanges[i].setLine(_BoundaryLinesForBoats[i].getX1() + boatPositionsChanged.getX(),
                    _BoundaryLinesForBoats[i].getY1() + boatPositionsChanged.getY(),
                    _BoundaryLinesForBoats[i].getX2() + boatPositionsChanged.getX(),
                    _BoundaryLinesForBoats[i].getY2() + boatPositionsChanged.getY());
        }
        return returnedPositionChanges;
    }
}
