package views.dialogs;

import models.CourseCategory;
import models.OverallGrade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteCategoryDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();

    public DeleteCategoryDialog(OverallGrade overallGrade) {
        setBounds(100, 100, 600, 300);
        setLocationRelativeTo ( null );
        contentPanel.setLayout(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Labels
        JLabel titleLabel = new JLabel("Delete a Category");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        titleLabel.setBounds(196, 39, 181, 26);
        contentPanel.add(titleLabel);

        JLabel categoryNameLabel = new JLabel("Category Name:");
        categoryNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryNameLabel.setBounds(164, 102, 100, 14);
        contentPanel.add(categoryNameLabel);

        // Dropdown menu
        ArrayList<String> courseCategoryNames = new ArrayList<>();
        ArrayList<CourseCategory> courseCategories = overallGrade.getCourseCategoryList();
        // Populate dropdown with available CourseCategories to drop
        for (CourseCategory courseCategory : courseCategories) {
            courseCategoryNames.add(courseCategory.getName());
        }
        JComboBox categoryNameDropdown = new JComboBox(courseCategoryNames.toArray());
        categoryNameDropdown.setBounds(276, 97, 160, 27);

        contentPanel.add(categoryNameDropdown);

        // Set action buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save and Close");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameDropdown.getSelectedItem().toString();
                overallGrade.deleteCategory(categoryName);
                dispose();
            }
        });
        saveButton.setActionCommand("OK");
        buttonPane.add(saveButton);
        getRootPane().setDefaultButton(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }
}
