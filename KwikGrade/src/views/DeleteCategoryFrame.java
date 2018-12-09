package views;

import models.CourseCategory;
import models.OverallGrade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DeleteCategoryFrame extends JDialog {
    private final JPanel contentPanel = new JPanel();

    public DeleteCategoryFrame(OverallGrade overallGrade) {
        setBounds(100, 100, 600, 300);
        setLocationRelativeTo ( null );
        contentPanel.setLayout(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        JLabel categoryNameLabel = new JLabel("Category Name:");
        categoryNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryNameLabel.setBounds(164, 102, 100, 14);
        contentPanel.add(categoryNameLabel);

        JLabel titleLabel = new JLabel("Delete a Category");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        titleLabel.setBounds(196, 39, 181, 26);
        contentPanel.add(titleLabel);

        ArrayList<String> courseCategoryNames = new ArrayList<>();
        ArrayList<CourseCategory> courseCategories = overallGrade.getCourseCategoryList();
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

        JButton okButton = new JButton("Save and Close");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String categoryName = categoryNameDropdown.getSelectedItem().toString();
                overallGrade.deleteCategory(categoryName);
                dispose();
            }
        });

        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

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
