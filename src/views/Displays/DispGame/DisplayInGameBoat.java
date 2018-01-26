package views.Displays.DispGame;

import controllers.GlobalResources;
import models.Boats;
import models.Players;

import javax.swing.*;

import java.awt.*;

// This is the label for the in Boats JLABEL
public class DisplayInGameBoat extends JLabel
{
    // This is the array for boats images
    private Image[] _ImagesOfBoat;

    // The boat crash image
    private Image _ImageOfCrash;

    // The current boat image index
    private int _CurrentBoatImageIndex;

    // The current boat image
    private ImageIcon _CurrentImageIcon;

    // Functions for boat including location and position
    private Boats _FunctionsForBoat;

    // Initiate the boat display label
    public DisplayInGameBoat(Players players)
    {
        _FunctionsForBoat = players.get_Boat();
        _FunctionsForBoat.set_ImagesForBoat(this);

        GetboatImages();
        _CurrentBoatImageIndex = 0;
        setSize(GlobalResources.BOATSINGAME_SizeWidth, GlobalResources.BOATSINGAME_SizeHeight);

        _CurrentImageIcon = new ImageIcon(_ImagesOfBoat[_CurrentBoatImageIndex]);
        setIcon(_CurrentImageIcon);
        setLocation(0,0);

        setVisible(true);
    }

    // Gets boat images using this methods
    private void GetboatImages()
    {
         _ImagesOfBoat = _FunctionsForBoat.ImageLoadersForBoat();
         _ImageOfCrash = _FunctionsForBoat.ImageCrashedLoaders();
    }


    // Correlating key presses handles by handle key presser logic
    public void ControlKeyPressed(int keyCode)
    {
        _FunctionsForBoat.KeyPressedHandlers(keyCode);
    }

    // Correlating key releases handles by handle key presser logic
    public void ControlKeyReleased(int keyCode)
    {
        _FunctionsForBoat.KeyReleaseHandlers(keyCode);
    }

    // Direction method returning correct current image
    public int Turn(boolean TheImageDirectionLeft)
    {
        _CurrentBoatImageIndex = GetBoatImageFromIndex(TheImageDirectionLeft);
        _CurrentImageIcon.setImage(_ImagesOfBoat[_CurrentBoatImageIndex]);
        this.setVisible(true);

        return _CurrentBoatImageIndex;
    }

    // Gets and returns new boat image which correlates with shared resources image
    public int GetBoatImageFromIndex(boolean TheImageDirectionLeft)
    {
        int NewIndexForImage;
        if(TheImageDirectionLeft)
        {
            NewIndexForImage = _CurrentBoatImageIndex - 1;
            if(NewIndexForImage < 0)
            {
                NewIndexForImage += _ImagesOfBoat.length;
            }
        }
        else
        {
            NewIndexForImage = _CurrentBoatImageIndex + 1;
            if(NewIndexForImage >= _ImagesOfBoat.length)
            {
                NewIndexForImage -= _ImagesOfBoat.length;
            }
        }
        return NewIndexForImage;
    }


    // Refresh boat logic and calculates next frame
    public void NewIteratedFrame()
    {
        _FunctionsForBoat.FormulationForIteratedFrame();
    }

    // Set starting image
    public void SetInitialImage(int angle)
    {
        int i;
        for (i = 0; i < GlobalResources.BOATSINGAME_Angles.length; i++)
        {
            if(GlobalResources.BOATSINGAME_Angles[i] == angle)
            {
                break;
            }
        }

        if(i < GlobalResources.BOATSINGAME_Angles.length)
        {
            _CurrentBoatImageIndex = i;
            _CurrentImageIcon.setImage(_ImagesOfBoat[_CurrentBoatImageIndex]);
            _FunctionsForBoat.set_AngleOfBoat(GlobalResources.BOATSINGAME_Angles[i]);
            this.setVisible(true);
        }

    }

    // Get first image if not and and return for main display
    public Image GetInitialImageForBoatHUD()
    {
        if(_ImagesOfBoat != null )
        {
            return _ImagesOfBoat[0];
        }
        else
        {
            return null;
        }
    }

    // Setter image for when boats crash
    public void SetImageCrash()
    {
        _CurrentImageIcon.setImage(_ImageOfCrash);
        setIcon(_CurrentImageIcon);
        setVisible(true);
    }

    //Set the image to specific 360 angle
    public void SetImageOnangleBasedOn360Index(int indexDegree) {
        _CurrentBoatImageIndex = indexDegree;
        _CurrentImageIcon.setImage(_ImagesOfBoat[_CurrentBoatImageIndex]);
        this.setVisible(true);
    }
}
