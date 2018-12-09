package views;

import models.OverallGrade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

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

        JLabel categoryNameLabel = new JLabel("Category Name:");
        categoryNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryNameLabel.setBounds(164, 102, 100, 14);
        contentPanel.add(categoryNameLabel);
        
        categoryNameTextField = new JTextField();
        categoryNameTextField.setBounds(276, 96, 130, 26);
        contentPanel.add(categoryNameTextField);
        categoryNameTextField.setColumns(10);
        
        JLabel lblAddANew = new JLabel("Add a new Category");
        lblAddANew.setHorizontalAlignment(SwingConstants.CENTER);
        lblAddANew.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        lblAddANew.setBounds(196, 39, 181, 26);
        contentPanel.add(lblAddANew);
        
        JLabel categoryWeightLabel = new JLabel("Category Weight:");
        categoryWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryWeightLabel.setBounds(141, 128, 122, 16);
        contentPanel.add(categoryWeightLabel);
        
        categoryWeightTextField = new JTextField();
        categoryWeightTextField.setBounds(276, 123, 130, 26);
        contentPanel.add(categoryWeightTextField);
        categoryWeightTextField.setColumns(10);

        // Set action buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Save and Close");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

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
