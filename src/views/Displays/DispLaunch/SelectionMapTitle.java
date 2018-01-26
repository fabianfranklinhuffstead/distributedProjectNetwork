package views.Displays.DispLaunch;

import javax.swing.*;

import controllers.GlobalResources;

import java.awt.*;

//Select small image for map on game configuration
class SelectionMapTitle extends JLabel
{
	// Small image for map
    private final Image _MapImage;

    // Maps selection with default values some stored on the shared resources.
    public SelectionMapTitle(Image image)
    {
        _MapImage = image;
        this.setSize(GlobalResources.ATTRIBUTESFORMAP_WidthMapSelection, GlobalResources.ATTRIBUTESFORMAP_HeightMapSelection);
        this.setLocation(0,0);
        this.setOpaque(true);
        this.setBackground(GlobalResources.ATTRIBUTESFORMAP_ColorBackground);
        this.setVisible(true);
    }

    // Override function for drawing small images
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        try {
            g.drawImage(_MapImage,0,0,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
