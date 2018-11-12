import views.LoginFrame;

import java.awt.*;

public class KwikGradeMain {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Start a Login frame.
                    new LoginFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
