package views.MapViews;

import java.awt.*;

import controllers.GlobalResources;

//This is for the initial default map
public class StandardMapTypeView extends GlobalResourceOfMapView
{

    //Default draw map parameters given by modulator (not including map textures)
    @Override
    public void DrawMap(Graphics g)
    {
        if (!GlobalResources.ATTRIBUTESFORDEFAULT_HighResLowResState)
        {
            Color c1 = Color.green;
            g.setColor( c1 );
            g.fillRect( 150, 200, 550, 300 ); //Island
            Color c2 = Color.black;
            g.setColor( c2 );
            g.drawRect(50, 100, 750, 500); // outer edge
            g.drawRect(150, 200, 550, 300); // inner edge
            Color c3 = Color.yellow;
            g.setColor( c3 );
            g.drawRect( 100, 150, 650, 400 ); // mid-lane bouys
            Color c4 = Color.white;
            g.setColor( c4 );
            g.drawLine( 425, 500, 425, 600 ); // start line
        }
    }

}
