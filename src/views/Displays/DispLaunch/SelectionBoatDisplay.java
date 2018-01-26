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

//boats selection panel 
@SuppressWarnings("serial")
class SelectionBoatDisplay extends JPanel implements ActionListener, MouseListener
{
    // An array of the boats
    private ArrayList<JLabel> _boats;

    //boats selection arrow left
    private JLabel _BoatLeftSelectionArrow;
    
    //boats selection arrow right
    private JLabel _BoatRightSelectionArrow;

    // Displays player number and boat images
    public SelectionBoatDisplay()
    {
        PlayerTitleCreation();
        this.setBounds(GetSelectionPositionBoats());
        this.setBackground(GlobalResources.ATTRIBUTESFORBOAT_SpaceForBoatsColor);
        DisplaySelectionForBoat();

        this.setVisible(true);
    }


    // Sets default states
    public void DefaultBoatSelectionDisplay() {
        		GlobalResources.MainControl.ChangeBoatSession(GlobalResources.In_Game_Player, 0);
            setVisible(true);
            repaint();
    }

    // Select boat index starts at 0
    private void BoatSelectIndex(int i)
    {
        if(_BoatLeftSelectionArrow == null || _BoatRightSelectionArrow == null)
        {
           LoadboatSelectorLabels();
        }

        int left_xAxis = _boats.get(i).getX() - GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow;
        int left_yAxis = _boats.get(i).getY() + (GlobalResources.ATTRIBUTESFORBOAT_BoatSize / 2);
        _BoatLeftSelectionArrow.setLocation(left_xAxis, left_yAxis);
        _BoatLeftSelectionArrow.setVisible(true);

        int right_xAxis = left_xAxis + GlobalResources.ATTRIBUTESFORBOAT_BoatSize + GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow;
        _BoatRightSelectionArrow.setLocation(right_xAxis, left_yAxis);
        _BoatRightSelectionArrow.setVisible(true);
    }


    // Loads the selected boat selection arrows
    private void LoadboatSelectorLabels()
    {
        _BoatLeftSelectionArrow = new JLabel();
        _BoatRightSelectionArrow = new JLabel();

        ImageIcon iconLtR =  new ImageIcon(FileLoaderImage.ImageForArrow(true));
        ImageIcon iconRtL = new ImageIcon(FileLoaderImage.ImageForArrow(false));

        _BoatLeftSelectionArrow.setIcon(iconLtR);
        _BoatRightSelectionArrow.setIcon(iconRtL);

        _BoatLeftSelectionArrow.setSize(GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow, GlobalResources.PREPDISPLAYATTRIBUTES_HeightArrow);
        _BoatRightSelectionArrow.setSize(GlobalResources.PREPDISPLAYATTRIBUTES_WidthArrow, GlobalResources.PREPDISPLAYATTRIBUTES_HeightArrow);

        this.add(_BoatLeftSelectionArrow);
        this.add(_BoatRightSelectionArrow);
    }


    // Creates player header label with dimensions, position and Players number.
    private void PlayerTitleCreation()
    {
        Rectangle measures = GetSelectionPositionBoats();
        Dimension sizing = new Dimension(measures.width,GlobalResources.ATTRIBUTESFORBOAT_HeightHeader);
        JLabel t = new JLabel(GlobalResources.ATTRIBUTESFORBOAT_HeaderPrefixedText);

        t.setLocation(0,0);
        t.setSize(sizing);
        t.setMinimumSize(sizing);
        t.setPreferredSize(sizing);

        t.setFont(GlobalResources.ATTRIBUTESFORBOAT_FontStylingHeader);
        t.setHorizontalTextPosition(SwingConstants.CENTER);
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setOpaque(true);
        t.setBackground(GlobalResources.ATTRIBUTESFORBOAT_ColorHeader);

        this.add(t);
    }

    // Get boats selection holder position and returns each
    private Rectangle GetSelectionPositionBoats()
    {
        int spacing = GlobalResources.ATTRIBUTESFORBOAT_SpaceForBoats;
        int width = GlobalResources.ATTRIBUTESFORBOAT_BoatChoiceAmount * (GlobalResources.ATTRIBUTESFORBOAT_BoatSize + spacing);
        int height = GlobalResources.ATTRIBUTESFORBOAT_BoatSize + spacing;
        int x = (GlobalResources.MAINWINDOW_Width - width) / 2;
        int y = (GlobalResources.MAINWINDOW_Height - height) / 2;
        return new Rectangle(x,y,width,height);
    }

    // Displays boat selections
    private void DisplaySelectionForBoat()
    {
        _boats = new ArrayList<>();

        for(int i = 0; i<GlobalResources.ATTRIBUTESFORBOAT_BoatChoiceAmount; i++)
        {
            // Creates new JLABEL
        		JLabel label = new JLabel();
        		
            // Load boats image from file and set to image
            Image image = FileLoaderImage.ImageForLaunchDisplay(i);

            // If image is not set to null which meant that image loading is successful
            if(image != null)
            {
                ImageIcon icon = new ImageIcon(image);
                label.setIcon(icon);
            }
            else
            {
                label.setText(GlobalResources.ATTRIBUTESFORBOAT_DefaultMessageOnNoImage);
            }

            label.setSize(GlobalResources.ATTRIBUTESFORBOAT_BoatSize,GlobalResources.ATTRIBUTESFORBOAT_BoatSize);
            this.add(label);
            label.setVisible(true);
            _boats.add(label);
            label.addMouseListener(this);

            //Add empty spacer labels between the boat labels
            if(i != GlobalResources.ATTRIBUTESFORBOAT_BoatChoiceAmount -1) {
                JLabel spacer = new JLabel("        "); //This is a workaround as the SetSize() is getting ignored by the layout manager.
                spacer.setVisible(true);
                this.add(spacer);
            }
        }
    }

    // This is an override for when the mouse is clicked
    @Override
    public void mouseClicked(MouseEvent e)
    {
        try
        {
            JLabel sender = (JLabel)e.getSource();

            int i;
            for(i =0; i < _boats.size(); i++)
            {
                if(_boats.get(i) == sender)
                {
                    break;
                }
            }

            if(i < _boats.size())
            {
                BoatSelectIndex(i);
                GlobalResources.MainControl.ChangeBoatSession(GlobalResources.In_Game_Player, i);
            }

        } catch (Exception x)
        {
            x.printStackTrace();
        }
    }


    // override mousePressed with mouseClicked
    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseClicked(e);
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

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
