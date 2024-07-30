public class HRSApplication {
    public static void main(String[] args) {
        HRS model = new HRS();
        MainView view = new MainView();
        new HRSController(model, view);
        view.setVisible(true);
    }
}