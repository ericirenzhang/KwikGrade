package helpers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KwikGradeUIManager {

    /**
     * Creates a default dialog of static height and width with BorderLayout and sets
     * the contentPanel in the center of the BorderLayout.
     * @param dialog
     * @param contentPanel
     */
    public static void setUpUI(JDialog dialog, JPanel contentPanel, int width, int height) {
        dialog.setBounds(100, 100, width, height);
        dialog.getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
    }
}
