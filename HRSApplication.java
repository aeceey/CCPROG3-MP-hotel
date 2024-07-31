/**
 * The HRSApplication class is where the Hotel Reservation System will start.
 * This class is the one who initiates the model, view, and controller of the system.
 */
public class HRSApplication {
    /**
     * The main method that will initialize the HRS program. It creates the MV and
     * sets it to be visible.
     * 
     * @param args - default parameter
     */    
    public static void main(String[] args) {
        HRS model = new HRS();
        MainView view = new MainView();
        new HRSController(model, view);
        view.setVisible(true);
        
    }
}