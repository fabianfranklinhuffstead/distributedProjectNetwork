package views.Displays.DispLaunch;

import controllers.GlobalResources;
import models.LoadingFiles.FileLoaderImage;

import javax.swing.*;

import java.awt.*;

//This JLABEL to be used as game control instructional support
class AssignKeyboard extends JLabel
{

    // Keyboard background image
    private final Image _BackGround;

    //  The settings for the keyboard
    public AssignKeyboard()
    {
        _BackGround = FileLoaderImage.ImageForKeyboard();
        this.setSize(GlobalResources.ATTRIBUTESFORHELP_WidthHelp,GlobalResources.ATTRIBUTESFORHELP_HeightHelp);
        this.setBackground(GlobalResources.ATTRIBUTESFORHELP_ColorHeader);
        this.setOpaque(true);
        
        String labelText = GlobalResources.PREPDISPLAYATTRIBUTES_ControlText;
        this.setText(labelText);
        this.setAlignmentY(NORTH);
        this.setVerticalAlignment(TOP);
        this.setHorizontalAlignment(CENTER);
        this.setHorizontalTextPosition(CENTER);
        this.setFont(GlobalResources.ATTRIBUTESFORHELP_FontStylingHeader);
        this.setLocation(0,0);
        this.setVisible(true);
    }

    // Draws the background using graphics g
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        try
        {
            int background_yAxis = GlobalResources.ATTRIBUTESFORHELP_HeightHelp - GlobalResources.ATTRIBUTESFORHELP_HeightImage;
            int background_xAxis = (GlobalResources.ATTRIBUTESFORHELP_WidthHelp - GlobalResources.ATTRIBUTESFORHELP_WidthImage) / 2;

            g.drawImage(_BackGround,background_xAxis,background_yAxis,this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
