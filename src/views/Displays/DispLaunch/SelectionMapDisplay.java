package views.Displays.DispLaunch;

import controllers.GlobalResources;
import models.LoadingFiles.FileLoaderImage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


//JPANEL with action listener and mouse listener events
class SelectionMapDisplay extends JPanel implements ActionListener, MouseListener {

    // Array of map labels 
    private final ArrayList<SelectionMapTitle> _Maps;
    
    // Array of map titles
    private final ArrayList<JLabel> _TitleForMap;

    // Map selector arrows left
    private JLabel _MapLeftSelectionArrow;
    
    // Map selector arrows right
    private JLabel __MapRightSelectionArrow;

    // Map selection JPANEL with default values
    public SelectionMapDisplay() {
        _Maps = new ArrayList<>();
        _TitleForMap = new ArrayList<>();
        this.setLayout(null);
        this.setBounds(GetMapSelectionBoundSize());
        this.setBackground(GlobalResources.ATTRIBUTESFORMAP_Color);

        DisplayHeader();
        DisplayMapSelections();

        setLocation(0, 0);
        this.setVisible(true);
    }

    // Display the title text at top of selection panel
    private void DisplayHeader() {
        JLabel header = new JLabel(GlobalResources.ATTRIBUTESFORMAP_MapTitleText);
        header.setFont(GlobalResources.ATTRIBUTESFORMAP_FontStyling);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setVerticalAlignment(SwingConstants.TOP);
        header.setBackground(GlobalResources.ATTRIBUTESFORMAP_ColorHeading);
        header.setSize(GlobalResources.ATTRIBUTESFORMAP_Width, GlobalResources.ATTRIBUTESFORMAP_Height);
        header.setLocation(0, GlobalResources.ATTRIBUTESFORMAP_MapYSpacing);
        header.setOpaque(true);
        header.setVisible(true);
        this.add(header);
    }

    // Default map selection
    public void DefaultMapSelection() {
        MapSelectIndex(0);
        GlobalResources.MainControl.ChangeMapSession(0);
    }

    // Select Map index starts at 0
    private void MapSelectIndex(int i)
    {
        if (_MapLeftSelectionArrow == null || __MapRightSelectionArrow == null)
        {
            LoadMapSelectionsImages();
        }

        int left_x = _Maps.get(i).getX() - GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow;
        int left_y = _Maps.get(i).getY() + (GlobalResources.ATTRIBUTESFORMAP_HeightMapSelection / 2);
        _MapLeftSelectionArrow.setLocation(left_x, left_y);

        int right_x = left_x + GlobalResources.ATTRIBUTESFORMAP_WidthMapSelection + GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow;
        __MapRightSelectionArrow.setLocation(right_x, left_y);
    }

    // Configurations for map selection arrows
    private void LoadMapSelectionsImages()
    {
        _MapLeftSelectionArrow = new JLabel();
        __MapRightSelectionArrow = new JLabel();

        ImageIcon iconLtR =  new ImageIcon(FileLoaderImage.ImageForArrow(true));
        ImageIcon iconRtL = new ImageIcon(FileLoaderImage.ImageForArrow(false));

        _MapLeftSelectionArrow.setIcon(iconLtR);
        __MapRightSelectionArrow.setIcon(iconRtL);

        _MapLeftSelectionArrow.setSize(GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow, GlobalResources.PREPDISPLAYATTRIBUTES_HeightArrow);
        __MapRightSelectionArrow.setSize(GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow, GlobalResources.PREPDISPLAYATTRIBUTES_HeightArrow);

        this.add(_MapLeftSelectionArrow);
        this.add(__MapRightSelectionArrow);

        _MapLeftSelectionArrow.setVisible(true);
        __MapRightSelectionArrow.setVisible(true);
    }

    // Displays the map selections including small image and other shared resources
    private void DisplayMapSelections()
    {
        for(int i =0; i < GlobalResources.ATTRIBUTESFORMAP_MapTitlesObject.length; i++)
        {
            // Creation of map selection label
            Image mapImage = FileLoaderImage.ImageForMapSelection(GlobalResources.ATTRIBUTESFORMAP_MapTitlesObject[i]);
            SelectionMapTitle mapLabel = new SelectionMapTitle(mapImage);
            _Maps.add(mapLabel);

            int location_y = (i * (GlobalResources.ATTRIBUTESFORMAP_HeightMapSelection + GlobalResources.ATTRIBUTESFORMAP_SpacingYLocation));
            location_y += GlobalResources.ATTRIBUTESFORMAP_Height;
            location_y += GlobalResources.ATTRIBUTESFORMAP_MapYSpacing;
            location_y += GlobalResources.ATTRIBUTESFORMAP_MapSpacing;

            int location_x = (GlobalResources.ATTRIBUTESFORMAP_Width - GlobalResources.ATTRIBUTESFORMAP_WidthMapSelection) / 2;
            mapLabel.setLocation(location_x,location_y);

            this.add(mapLabel);
            mapLabel.setVisible(true);
            mapLabel.addMouseListener(this);

            //Adding description label
            JLabel mapTitle = new JLabel(GlobalResources.ATTRIBUTESFORMAP_MapTitlesObject[i]);
            mapTitle.setFont(GlobalResources.ATTRIBUTESFORMAP_FontStylingMapTitle);
            mapTitle.setSize(GlobalResources.ATTRIBUTESFORMAP_Width,GlobalResources.ATTRIBUTESFORMAP_HeightMapTitle);
            mapTitle.setHorizontalAlignment(SwingConstants.CENTER);
            mapTitle.setHorizontalTextPosition(SwingConstants.CENTER);
            int titleLocationY = location_y + GlobalResources.ATTRIBUTESFORMAP_HeightMapSelection;
            mapTitle.setLocation(0,titleLocationY);

            _TitleForMap.add(mapTitle);
            mapTitle.addMouseListener(this);
            this.add(mapTitle);
            mapTitle.setVisible(true);
        }
    }

    // Get and returns the Panel bounds for maps selection panel
    private Rectangle GetMapSelectionBoundSize()
    {
        int height = (GlobalResources.ATTRIBUTESFORMAP_MapTitlesObject.length * (GlobalResources.ATTRIBUTESFORMAP_HeightMapSelection  + GlobalResources.ATTRIBUTESFORMAP_SpacingYLocation));
        height += GlobalResources.ATTRIBUTESFORMAP_Height;
        height += GlobalResources.ATTRIBUTESFORMAP_MapYSpacing;
        height += GlobalResources.ATTRIBUTESFORMAP_MapSpacing;

        return new Rectangle(0,0,GlobalResources.ATTRIBUTESFORMAP_Width,height);
    }

    // This is an override method for mouse events for map selects
    @Override
    public void mouseClicked(MouseEvent e) {
        // Try catch method, catching potential bugs
        try {
            JLabel sender = (JLabel) e.getSource();
            int i;

            //For loop iterating through maps size and titles
            for (i = 0; i < _Maps.size(); i++) {
                if (_Maps.get(i) == sender || _TitleForMap.get(i) == sender) {
                    break;
                }
            }

            // Renders map selection to current game session
            if (i < _Maps.size()) {
                MapSelectIndex(i);
                GlobalResources.MainControl.ChangeMapSession(i);
            }

        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    // override mousePressed with mouseClicked
    @Override
    public void mousePressed(MouseEvent e) {
        mouseClicked(e);
    }

    // No action selected overrides
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
