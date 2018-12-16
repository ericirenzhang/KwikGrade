package views.dialogs;

import helpers.KwikGradeUIManager;
import models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddCurveDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField curveAmountTextField;

    /**
     * Add a curve to all students in the course.
     * @param managedCourse
     */
    public AddCurveDialog(Course managedCourse) {
        KwikGradeUIManager.setUpUI(this, contentPanel, 600, 300);

        JLabel titleLabel = new JLabel("Add a curve:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        titleLabel.setBounds(196, 39, 181, 26);
        contentPanel.add(titleLabel);

        // ============================================
        // Select students who will receive a curve
        // ============================================
        JLabel categoryNameLabel = new JLabel("Select students who will receive a curve:");
        categoryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryNameLabel.setBounds(144, 79, 297, 14);
        contentPanel.add(categoryNameLabel);

        JComboBox curveDropdown = new JComboBox();
        curveDropdown.addItem("All Students");
        curveDropdown.addItem("Undergraduate");
        curveDropdown.addItem("Graduate");
        curveDropdown.setBounds(225, 98, 128, 27);
        contentPanel.add(curveDropdown);

        // ============================================
        // Specify Curve amount
        // ============================================
        JLabel curveLabel = new JLabel("Curve amount");
        curveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        curveLabel.setBounds(196, 137, 181, 16);
        contentPanel.add(curveLabel);

        JLabel curveExampleLabel = new JLabel("(e.g. 10 to add 10% to each student grade)");
        curveExampleLabel.setBounds(159, 153, 265, 30);
        contentPanel.add(curveExampleLabel);

        curveAmountTextField = new JTextField();
        curveAmountTextField.setBounds(253, 181, 66, 26);
        contentPanel.add(curveAmountTextField);
        curveAmountTextField.setColumns(10);

        JLabel percentLabel = new JLabel("%");
        percentLabel.setBounds(324, 186, 61, 16);
        contentPanel.add(percentLabel);

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

                Double curveAmount = Double.parseDouble(curveAmountTextField.getText());

                // Iterate through and add curves to each Student's OverallGrade
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

    /**
     * Given a curveAmount, studentList, and studentStatus, this method iterates through studentList, filters on studentStatus,
     * and adds a curve to each Student's OverallGrade
     * @param curveAmount
     * @param studentList
     * @param studentStatus
     */
    public void addCurve(Double curveAmount, ArrayList<Student> studentList, String studentStatus) {
        for(int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);

            // Filter on studentStatus
            if(studentStatus.equals("All Students")) {
                OverallGrade currStudentGrade = student.getOverallGradeObject();
                if((currStudentGrade.getOverallGradeValue() + curveAmount)>100) {
                    curveAmount = 100 - currStudentGrade.getOverallGradeValue();
                }
                currStudentGrade.setOverallGrade(currStudentGrade.getOverallGradeValue() + curveAmount);
            } else if (student.getStatus().equals(studentStatus)) {
                OverallGrade currStudentGrade = student.getOverallGradeObject();
                if((currStudentGrade.getOverallGradeValue() + curveAmount)>100) {
                    curveAmount = 100 - currStudentGrade.getOverallGradeValue();
                }
                currStudentGrade.setOverallGrade(currStudentGrade.getOverallGradeValue() + curveAmount);
            }
        }
    }
}
