package views;

import models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddCurveFrame extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField curveAmountTextField;

    public AddCurveFrame(Course managedCourse) {
        setBounds(100, 100, 600, 300);
        setLocationRelativeTo ( null );
        contentPanel.setLayout(null);

        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // Labels
        JLabel titleLabel = new JLabel("Add a curve:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        titleLabel.setBounds(196, 39, 181, 26);
        contentPanel.add(titleLabel);

        JLabel categoryNameLabel = new JLabel("Select students who will receive a curve:");
        categoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryNameLabel.setBounds(144, 79, 297, 14);
        contentPanel.add(categoryNameLabel);

        JLabel curveLabel = new JLabel("Curve amount");
        curveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        curveLabel.setBounds(196, 137, 181, 16);
        contentPanel.add(curveLabel);

        JLabel percentLabel = new JLabel("%");
        percentLabel.setBounds(324, 186, 61, 16);
        contentPanel.add(percentLabel);

        curveAmountTextField = new JTextField();
        curveAmountTextField.setBounds(253, 181, 66, 26);
        contentPanel.add(curveAmountTextField);
        curveAmountTextField.setColumns(10);
        
        JLabel curveExampleLabel = new JLabel("(e.g. 10 to add 10% to each student grade)");
        curveExampleLabel.setBounds(159, 153, 265, 30);
        contentPanel.add(curveExampleLabel);
        
        JComboBox curveDropdown = new JComboBox();
        curveDropdown.addItem("All Students");
        curveDropdown.addItem("Undergraduate");
        curveDropdown.addItem("Graduate");

        curveDropdown.setBounds(225, 98, 128, 27);
        contentPanel.add(curveDropdown);


        // Set action buttons
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save and Close");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(curveAmountTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please make sure all fields are filled!");
                    return;
                }

//                String categoryName = curveAmountTextField.getText();
                Double curveAmount = Double.parseDouble(curveAmountTextField.getText());

                ArrayList<Student> studentList = managedCourse.getActiveStudents();
                if(curveDropdown.getSelectedItem().equals("Undergraduate")) {
                    addCurve(curveAmount, studentList, "Undergraduate");
                } else if(curveDropdown.getSelectedItem().equals("Graduate")) {
                    addCurve(curveAmount, studentList, "Graduate");
                } else {
                    addCurve(curveAmount, studentList, "All Students");
                }

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

    // Iterate through and add a curve to each student's grade
    public void addCurve(Double curveAmount, ArrayList<Student> studentList, String studentStatus) {
        for(int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            if(studentStatus.equals("All Students")) {
                OverallGrade currStudentGrade = student.getOverallGrade();
                currStudentGrade.setOverallGrade(currStudentGrade.getOverallGrade() + curveAmount);
            } else if(student.getStatus().equals(studentStatus)) {
                OverallGrade currStudentGrade = student.getOverallGrade();
                currStudentGrade.setOverallGrade(currStudentGrade.getOverallGrade() + curveAmount);
            }
        }
    }
}
