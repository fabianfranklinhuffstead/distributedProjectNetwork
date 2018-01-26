package views.Displays.DispMainMenu;

import javax.swing.*;

import controllers.GlobalResources;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//The Main menu screen
public class MenuMainDisplay extends JPanel implements ActionListener
{

    // This is the background image for the background screen
    private final Image _ImageBackground;
    
    // This contains the main menu buttons
    private JPanel _MainMenuChoices;
    
    // An array of the buttons
    private ArrayList<JButton> _MainMenuButtons;

    // The public main menu screen with menu buttons 
    public MenuMainDisplay(Image ImageBackground)
    {
        _ImageBackground = ImageBackground;
        this.setLayout(null);
        
        CreateMenuButtons();
        AddMainMenuChoices();
        this.add(_MainMenuChoices);

        this.setVisible(true);
    }

    // Draws the background menu
    protected void  paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try
        {
            g.drawImage(_ImageBackground,0,0,this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Creates new JPANEL for the buttons within the main menu
    private void AddMainMenuChoices()
    {
        _MainMenuChoices = new JPanel();
        _MainMenuChoices.setBounds(GetMainMenuParameters());
        _MainMenuChoices.setLayout(null); 
        // For loop to add each menu button and sets visible to true
        _MainMenuButtons.stream().forEach(i -> _MainMenuChoices.add(i));
        _MainMenuChoices.setVisible(true);
    }

    // Gets the centre coordinates for the main menu then returns this
    private Rectangle GetMainMenuParameters()
    {
        int x = (GlobalResources.MAINWINDOW_Width - GlobalResources.MAINSELECTIONMENU_WidthMenu) / 2;
        int y = (GlobalResources.MAINWINDOW_Height - GlobalResources.MAINSELECTIONMENU_HeightMenu) /2;

        return new Rectangle(x,y,GlobalResources.MAINSELECTIONMENU_WidthMenu, GlobalResources.MAINSELECTIONMENU_HeightMenu);
    }

    // Creates the buttons and adds for the main menu
    private void CreateMenuButtons()
    {
        //Create new buttons
        _MainMenuButtons = new ArrayList<>();
        _MainMenuButtons.add(new JButton(GlobalResources.MAINSELECTIONMENU_NetworkConnection));
        _MainMenuButtons.add(new JButton(GlobalResources.MAINSELECTIONMENU_StartNewConnection));
        _MainMenuButtons.add(new JButton(GlobalResources.MAINSELECTIONMENU_QuitMenu));

        // Sizing for each button 
        _MainMenuButtons.stream().forEach(i -> i.setSize(new Dimension(GlobalResources.MAINSELECTIONMENU_WidthMenu,GlobalResources.MAINSELECTIONMENU_HeightMenuSettings)));

        // Location for teach button
        for(int i = 1; i<_MainMenuButtons.size(); i++)
        {
            _MainMenuButtons.get(i).setLocation(0,i*GlobalResources.MAINSELECTIONMENU_HeightMenuSettings);
        }

        // Location for teach button
        _MainMenuButtons.stream().forEach(i ->
        {
            i.setBackground(GlobalResources.MAINSELECTIONMENU_StartMenuBackGroundColor);
            i.setFont(GlobalResources.MAINSELECTIONMENU_FontStylingButton);
        });

        // For each loop to add event to each button
        _MainMenuButtons.stream().forEach(i -> i.addActionListener(this));
    }


    // This is an action event override for main menu
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // If statement with or argument in case of button event
        if (e.getSource().equals(_MainMenuButtons.get(0)) || e.getSource().equals(_MainMenuButtons.get(1)) || e.getSource().equals(_MainMenuButtons.get(2)))
        {
            // Binding to temporary button
            JButton temp = (JButton)e.getSource();

            // Switch casement on action performed dependent on text
            switch (temp.getText())
            {
                case GlobalResources.MAINSELECTIONMENU_NetworkConnection:
                    GlobalResources.MainControl.ConnectingAsClientInitiation();
                    break;
                case GlobalResources.MAINSELECTIONMENU_StartNewConnection:
                    GlobalResources.MainControl.NetworkServerInitiation();
                    break;
                case GlobalResources.MAINSELECTIONMENU_QuitMenu:
                    GlobalResources.MainControl.BoatGameUserExit();
                    break;
            }
        }
    }
}
