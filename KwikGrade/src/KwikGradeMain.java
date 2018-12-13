import views.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class KwikGradeMain {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set the theme and launch the Login frame.
                    try {
                        UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    new LoginFrame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
