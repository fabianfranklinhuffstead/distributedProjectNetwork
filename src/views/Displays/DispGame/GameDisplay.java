package views.Displays.DispGame;

import javax.swing.*;

import controllers.CurrentRuntimeSession;
import controllers.GlobalResources;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

//In game JPANEL with has action listener and key listener events
public class GameDisplay extends JPanel implements ActionListener, KeyListener
{

    // boats in game display label
    private DisplayInGameBoat[] _BoatImage;
    private DisplayHUD[] _DisplayHUD;
    private JLabel _HighResLowResMapTextures = null;

    // In game screen defaults
    public GameDisplay()
    {
        this.setLayout(null);
        BoatTitles();
        HUDInitiator();
        addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.requestFocus();
        this.setVisible(true);
    }

    // Private method for in game display HUDS
    private void HUDInitiator() {

        _DisplayHUD = new DisplayHUD[2];
        _DisplayHUD[0] = new DisplayHUD(CurrentRuntimeSession.get_PlayerAmount().get(0));
        _DisplayHUD[1] = new DisplayHUD(CurrentRuntimeSession.get_PlayerAmount().get(1));
        _DisplayHUD[0].setLocation(GlobalResources.ATTRIBUTESFORHUD_OnePlayerXPoistion, GlobalResources.ATTRIBUTESFORHUD_OnePlayerYPoistion);
        _DisplayHUD[1].setLocation(GlobalResources.ATTRIBUTESFORHUD_SecondPlayerXPoistion, GlobalResources.ATTRIBUTESFORHUD_SecondPlayerYPoistion);
        _DisplayHUD[0].DisplayHUDAttributes();
        _DisplayHUD[1].DisplayHUDAttributes();
        this.add(_DisplayHUD[0]);
        this.add(_DisplayHUD[1]);
        _DisplayHUD[0].setVisible(true);
        _DisplayHUD[1].setVisible(true);
    }

    // Private method for in game current session boat locations, starter other starter configurations
    private void BoatTitles() {
        _BoatImage = new DisplayInGameBoat[2];
        _BoatImage[0] = new DisplayInGameBoat(CurrentRuntimeSession.get_PlayerAmount().get(0));
        _BoatImage[1] = new DisplayInGameBoat(CurrentRuntimeSession.get_PlayerAmount().get(1));
        
        _BoatImage[0].setLocation(CurrentRuntimeSession.get_ModelOfMap().getInitial_Boat_Starting_XValue_FirstPlayer(), CurrentRuntimeSession.get_ModelOfMap().getInitial_Boat_Starting_YValue_FirstPlayer());
        _BoatImage[0].SetInitialImage(CurrentRuntimeSession.get_ModelOfMap().getInitial_Starting_Boat_Angles());
        _BoatImage[1].setLocation(CurrentRuntimeSession.get_ModelOfMap().getInitial_Boat_Starting_XValue_SecondPlayer(), CurrentRuntimeSession.get_ModelOfMap().getInitial_Boat_Starting_YValue_SecondPlayer());
        _BoatImage[1].SetInitialImage(CurrentRuntimeSession.get_ModelOfMap().getInitial_Starting_Boat_Angles());

        this.add(_BoatImage[0]);
        this.add((_BoatImage[1]));
        _BoatImage[0].setVisible(true);
        _BoatImage[1].setVisible(true);
    }

    
    // Callback to iterate through HUDS and boats
    public void IterateFrame()
    {
        Arrays.stream(_BoatImage).forEach(cl ->
        {
            if(GlobalResources.MainControl.get_RuntimeEngine() != null)
            {
                cl.NewIteratedFrame();
            }
        });

        Arrays.stream(_DisplayHUD).forEach(h1 ->
        {
            if(GlobalResources.MainControl.get_RuntimeEngine() != null) {
                h1.DisplayHUDUpdates();
            }
        });

        if(GlobalResources.MainControl.get_RuntimeEngine() != null)
            repaint();
    }

    // Override method for when drawing map views and textures
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        DrawMapToDisplay(g);
    }
    
    private void DrawMapToDisplay(Graphics g)
    {
        if (GlobalResources.ATTRIBUTESFORDEFAULT_HighResLowResState)
        {
            if (_HighResLowResMapTextures == null)
            {
                _HighResLowResMapTextures = new JLabel();
                Icon i = new ImageIcon(CurrentRuntimeSession.get_GlobalResourceOfMapView().ImageTextureLoader());
                _HighResLowResMapTextures.setIcon(i);
                _HighResLowResMapTextures.setSize(i.getIconWidth(), i.getIconHeight());
                _HighResLowResMapTextures.setLocation(0, 0);
                this.add(_HighResLowResMapTextures);
            }
            else
            {
                if (!_HighResLowResMapTextures.isVisible()) {
                    _HighResLowResMapTextures.setVisible(true);
                }
            }
        } else {
            if (_HighResLowResMapTextures != null) {

                _HighResLowResMapTextures.setVisible(false);
            }
            CurrentRuntimeSession.get_GlobalResourceOfMapView().DrawMap(g);
        }
    }


    // Override method for when key is pressed
    @Override
    public void actionPerformed(ActionEvent e)
    {
    		//Kill process
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
		//Kill process
    }

    
    // Calls the the actual player boat image object
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (Arrays.asList(GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls).contains(e.getKeyCode())) {
            _BoatImage[CurrentRuntimeSession.get_IndexofActualPlayer()].ControlKeyPressed(e.getKeyCode());
        }
    }

    // Calls the the actual player boat image object
    @Override
    public void keyReleased(KeyEvent e)
    {
        if (Arrays.asList(GlobalResources.ATTRIBUTESFORCONTROL_OnePlayerControls).contains(e.getKeyCode())) {
            _BoatImage[CurrentRuntimeSession.get_IndexofActualPlayer()].ControlKeyReleased(e.getKeyCode());
        }
    }

    // Occurs when the boats crash
    public void OnBoatCrashSetImage()
    {
        for (DisplayInGameBoat _boatLabel : _BoatImage) {
            _boatLabel.SetImageCrash();
        }

        repaint();
    }

}
