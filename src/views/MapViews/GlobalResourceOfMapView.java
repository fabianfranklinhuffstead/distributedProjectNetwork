package views.MapViews;


import java.awt.*;

import controllers.CurrentRuntimeSession;

//This is the map view which is used to draw out dynamic map textures, types
public class GlobalResourceOfMapView {

    // g is the element which the map is drawn to
    public void DrawMap(Graphics g) {
    }

    // returns a given map texture from default
    public Image ImageTextureLoader() {
        return CurrentRuntimeSession.get_ModelOfMap().GetMapHighResLowResMap();
    }
}
