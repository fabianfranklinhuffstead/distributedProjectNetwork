package views.MapViews;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import controllers.GlobalResources;

//This is for the alternate map type
public class AltMapTypeView extends GlobalResourceOfMapView {

    // Racing track outer lines
    private final ArrayList<Line2D> _InnerEdges;

    // Racing track inner lines
    private final ArrayList<Line2D> _OuterEdges;

    // Starting point
    private final Point TopPrimaryLine = new Point(425, 500);
    private final Point BottomSecondaryLine = new Point(425, 600);

    // The parameters for the alternate map type
    public AltMapTypeView() {
        //Outer racing track points
        Point TopLeftOuter = new Point(50, 100);
        Point BottomLeftOuter = new Point(50, 600);
        Point BottomRightOuter = new Point(800, 600);
        Point MiddleRightOuter = new Point(800, 300);
        Point MiddleCenterOuter = new Point(375, 300);
        Point TopCenterOuter = new Point(375, 100);

        //Inner racing track points
        Point TopLeftInner = new Point(150, 200);
        Point BottomLeftInner = new Point(150, 500);
        Point BottomRightInner = new Point(700, 500);
        Point MiddleRightInner = new Point(700, 400);
        Point MiddleCenterInner = new Point(275, 400);
        Point TopCenterInner = new Point(275, 200);


        _InnerEdges = new ArrayList<>();
        _OuterEdges = new ArrayList<>();

        //Connecting the outer racing track points to be lines
        _InnerEdges.add(new Line2D.Double(TopLeftInner.x, TopLeftInner.y, BottomLeftInner.x, BottomLeftInner.y));
        _InnerEdges.add(new Line2D.Double(BottomLeftInner.x, BottomLeftInner.y, BottomRightInner.x, BottomRightInner.y));
        _InnerEdges.add(new Line2D.Double(BottomRightInner.x, BottomRightInner.y, MiddleRightInner.x, MiddleRightInner.y));
        _InnerEdges.add(new Line2D.Double(MiddleRightInner.x, MiddleRightInner.y, MiddleCenterInner.x, MiddleCenterInner.y));
        _InnerEdges.add(new Line2D.Double(MiddleCenterInner.x, MiddleCenterInner.y, TopCenterInner.x, TopCenterInner.y));
        _InnerEdges.add(new Line2D.Double(TopCenterInner.x, TopCenterInner.y, TopLeftInner.x, TopLeftInner.y));

        //Connecting the inner racing track points to be lines
        _OuterEdges.add(new Line2D.Double(TopLeftOuter.x, TopLeftOuter.y, BottomLeftOuter.x, BottomLeftOuter.y));
        _OuterEdges.add(new Line2D.Double(BottomLeftOuter.x, BottomLeftOuter.y, BottomRightOuter.x, BottomRightOuter.y));
        _OuterEdges.add(new Line2D.Double(BottomRightOuter.x, BottomRightOuter.y, MiddleRightOuter.x, MiddleRightOuter.y));
        _OuterEdges.add(new Line2D.Double(MiddleRightOuter.x, MiddleRightOuter.y, MiddleCenterOuter.x, MiddleCenterOuter.y));
        _OuterEdges.add(new Line2D.Double(MiddleCenterOuter.x, MiddleCenterOuter.y, TopCenterOuter.x, TopCenterOuter.y));
        _OuterEdges.add(new Line2D.Double(TopCenterOuter.x, TopCenterOuter.y, TopLeftOuter.x, TopLeftOuter.y));
    }

    // Draws map to screen similar to other drawing method
    @Override
    public void DrawMap(Graphics g) {

        if (!GlobalResources.ATTRIBUTESFORDEFAULT_HighResLowResState) {

            g.setColor(Color.black);
            //Inner and outer edges iterated using for loop
            for (int i = 0; i < _InnerEdges.size(); i++) {
                g.drawLine((int) _InnerEdges.get(i).getX1(), (int) _InnerEdges.get(i).getY1(), (int) _InnerEdges.get(i).getX2(), (int) _InnerEdges.get(i).getY2());
                g.drawLine((int) _OuterEdges.get(i).getX1(), (int) _OuterEdges.get(i).getY1(), (int) _OuterEdges.get(i).getX2(), (int) _OuterEdges.get(i).getY2());
            }

            //White line for start line
            g.setColor(Color.white);
            g.drawLine(TopPrimaryLine.x, TopPrimaryLine.y, BottomSecondaryLine.x, BottomSecondaryLine.y);
        }
    }


    // An array which which generated a list of returning outer and inner edges/lines
    public ArrayList<Line2D> GetLinesForGameTrack(boolean outerEdge) {
        if (outerEdge) {
            return _OuterEdges;
        } else {
            return _InnerEdges;
        }
    }

}
