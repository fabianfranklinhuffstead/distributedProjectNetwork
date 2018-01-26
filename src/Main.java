/** @author Fabian Franklin-Huffstead
  * @version 2.0.0
  * Distributed Systems Programming (DSP) | Assignment Boats Game - Network
  */

import controllers.GlobalResources;
import controllers.Control;


//Main class Run this is where the program is run from
class Main {
    // Main class where boat game starts
    public static void main(String[] args) {
    	
        // Creates new controller named the main controller
        GlobalResources.MainControl = new Control();
        // Starts main application
        GlobalResources.MainControl.StartApplication();
    }
}