package views;

import models.CourseCategory;
import models.SubCategory;
import models.OverallGrade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddCategoryFrame extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField categoryNameTextField;
    private JTextField categoryWeightTextField;

    public AddCategoryFrame(OverallGrade overallGrade) {
        setBounds(100, 100, 600, 300);
        setLocationRelativeTo ( null );
        contentPanel.setLayout(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Labels
        JLabel titleLabel = new JLabel("Add a new Category");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        titleLabel.setBounds(196, 39, 181, 26);
        contentPanel.add(titleLabel);

        JLabel categoryNameLabel = new JLabel("Category Name:");
        categoryNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryNameLabel.setBounds(164, 102, 100, 14);
        contentPanel.add(categoryNameLabel);

        JLabel categoryWeightLabel = new JLabel("Category Weight (e.g. 50):");
        categoryWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryWeightLabel.setBounds(85, 128, 179, 16);
        contentPanel.add(categoryWeightLabel);

        JLabel percentLabel = new JLabel("%");
        percentLabel.setBounds(345, 128, 61, 16);
        contentPanel.add(percentLabel);

        // TextFields
        categoryNameTextField = new JTextField();
        categoryNameTextField.setBounds(276, 96, 130, 26);
        contentPanel.add(categoryNameTextField);
        categoryNameTextField.setColumns(10);

        categoryWeightTextField = new JTextField();
        categoryWeightTextField.setBounds(276, 123, 66, 26);
        contentPanel.add(categoryWeightTextField);
        categoryWeightTextField.setColumns(10);


        // Set action buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save and Close");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(categoryNameTextField.getText().equals("") || categoryWeightTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please make sure all fields are filled!");
                    return;
                }

                String categoryName = categoryNameTextField.getText();
                Double categoryWeight = Double.parseDouble(categoryWeightTextField.getText());

                CourseCategory newCategory = new CourseCategory(categoryName, categoryWeight/100, new ArrayList<SubCategory>());
                overallGrade.getCourseCategoryList().add(newCategory);
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
