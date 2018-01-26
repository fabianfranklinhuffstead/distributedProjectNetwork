package views.Displays.DispGame;

import controllers.GlobalResources;
import models.Players;

import javax.swing.*;

//Heads up display including small boat image, titles and speed info
@SuppressWarnings("serial")
class DisplayHUD extends JPanel {

    // The player
    private final Players _Player;

    // Small boat icon
    private JLabel _IconsForPlayers;

    // The player name
    private JLabel _PayerNames;

    // Current speed text
    private JLabel _TitlesForSpeed;

    // Speed bar
    private JProgressBar _SpeedBar;

    //Speed HUD associated to players
    public DisplayHUD(Players players) {
        _Player = players;
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setSize(GlobalResources.ATTRIBUTESFORHUD_XValue, GlobalResources.ATTRIBUTESFORHUD_YValue);
        this.setBackground(GlobalResources.ATTRIBUTESFORHUD_Color);
        this.setVisible(true);
    }

    // Creates HUD
    public void DisplayHUDAttributes() {
        PlayerIcon();
        PlayerTitle();
        SpeedTitle();
        SpeedBar();

        this.setVisible(true);
    }

    // Method for providing updates
    public void DisplayHUDUpdates() {
        int speed = _Player.GetLatestboatSpeedForHUD();
        _TitlesForSpeed.setText(GlobalResources.ATTRIBUTESFORHUD_SpeedTitle + Integer.toString(speed) + GlobalResources.ATTRIBUTESFORHUD_MetricTitle);
        _SpeedBar.setValue(speed);
    }


    // Creates speed bar starting from 0 maximum 100 from shared resources
    private void SpeedBar() {
        _SpeedBar = new JProgressBar(SwingConstants.HORIZONTAL, 0, GlobalResources.ATTRIBUTESFORCONTROL_InGameMaxSpeed);
        _SpeedBar.setValue(0);
        _SpeedBar.setSize(GlobalResources.ATTRIBUTESFORHUD_XValueSpeedProgessBar, GlobalResources.ATTRIBUTESFORHUD_YValueSpeedProgessBar);
        _SpeedBar.setBackground(GlobalResources.ATTRIBUTESFORHUD_ProgressBarColor);
        
        int x = _TitlesForSpeed.getX();
        int y = _TitlesForSpeed.getY() + _TitlesForSpeed.getHeight() + GlobalResources.ATTRIBUTESFORHUD_SpacingSpeedHeight;
        _SpeedBar.setLocation(x, y);
        
        this.add(_SpeedBar);
        _SpeedBar.setVisible(true);
    }

    // Create actual text to display to user
    private void SpeedTitle() {
        _TitlesForSpeed = new JLabel(GlobalResources.ATTRIBUTESFORHUD_SpeedTitle + "0" + GlobalResources.ATTRIBUTESFORHUD_MetricTitle);
        _TitlesForSpeed.setFont(GlobalResources.ATTRIBUTESFORHUD_FontStylingSpeed);

        int x = _PayerNames.getX();
        int y = _PayerNames.getY() + _PayerNames.getHeight() + GlobalResources.ATTRIBUTESFORHUD_PlayerTitleHeightSpacing;
        _TitlesForSpeed.setLocation(x, y);
        _TitlesForSpeed.setSize(GlobalResources.ATTRIBUTESFORHUD_XValueSpeed, GlobalResources.ATTRIBUTESFORHUD_YValueSpeed);

        this.add(_TitlesForSpeed);
        _TitlesForSpeed.setVisible(true);
    }

    // Contains player title
    private void PlayerTitle() {
        _PayerNames = new JLabel(_Player.get_PlayerTitles());
        _PayerNames.setFont(GlobalResources.ATTRIBUTESFORHUD_FontStyling);

        int x = _IconsForPlayers.getX() + _IconsForPlayers.getWidth() + GlobalResources.ATTRIBUTESFORHUD_SpacingNegativeY;
        int y = _IconsForPlayers.getY();
        _PayerNames.setLocation(x, y);
        _PayerNames.setSize(GlobalResources.ATTRIBUTESFORHUD_XValuePlayerTitle, GlobalResources.ATTRIBUTESFORHUD_YValuePlayerTitle);

        this.add(_PayerNames);
        _PayerNames.setVisible(true);
    }

    // Contains actual player icon
    private void PlayerIcon() {
        _IconsForPlayers = new JLabel();
        _IconsForPlayers.setSize(GlobalResources.BOATSINGAME_SizeWidth, GlobalResources.BOATSINGAME_SizeHeight);

        //Centres the icon
        int y = (GlobalResources.ATTRIBUTESFORHUD_YValue - GlobalResources.BOATSINGAME_SizeHeight) / 2;
        _IconsForPlayers.setLocation(0, y);

        ImageIcon icon = new ImageIcon(_Player.GetHUDForPlayers());
        _IconsForPlayers.setIcon(icon);

        this.add(_IconsForPlayers);
        _IconsForPlayers.setVisible(true);
    }


}
