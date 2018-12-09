package views;

import helpers.FileManager;
import models.*;
import views.components.GradingSchemeGrid;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManageStudentFrame extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField buIdField;
	private JTextField emailField;
	private JTextArea commentTextArea;
	private JTextField mInitialField;
	private JLabel fNameLabel;
	private JLabel lNameLabel;
	private JLabel buIdLabel;
	private JLabel emailLabel;
	private JLabel statusLabel;
	private JLabel mInitialLabel;
	private JLabel commentsLabel;
	private JLabel titleLabel;

	private GradingSchemeGrid gradingSchemeGrid;
	private OverallGrade managedOverallGradeScheme;
	private JScrollPane gradingSchemeScrollPane;

	/**
	 * Create the dialog.
	 */
	public ManageStudentFrame(Student managedStudent, Course managedCourse) {
		//TODO: add if-else logic on which overall grade scheme to use. By default we will use the Undergraduate schema
		managedOverallGradeScheme = managedStudent.getOverallGradeObject();

		setBounds(100, 100, 1000, 600);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		GridBagConstraints frameConstraints = new GridBagConstraints();

		// ===========================
		// Student information/Fields
		// ===========================
		titleLabel = new JLabel("Manage a Student");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(52, 31, 194, 26);
		contentPanel.add(titleLabel);

		fNameField = new JTextField(managedStudent.getfName());
		fNameField.setBounds(51, 81, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);

		mInitialField = new JTextField(managedStudent.getMiddleInitial());
		mInitialField.setBounds(215, 81, 130, 26);
		mInitialField.setColumns(10);
		contentPanel.add(mInitialField);

		lNameField = new JTextField(managedStudent.getlName());
		lNameField.setBounds(380, 81, 130, 26);
		lNameField.setColumns(10);
		contentPanel.add(lNameField);

		buIdField = new JTextField(managedStudent.getBuId());
		buIdField.setBounds(51, 145, 130, 26);
		buIdField.setColumns(10);
		contentPanel.add(buIdField);

		emailField = new JTextField(managedStudent.getEmail());
		emailField.setBounds(215, 145, 130, 26);
		emailField.setColumns(10);
		contentPanel.add(emailField);
		
		commentTextArea = new JTextArea(managedStudent.getComment());
		//added key listener for tab so user can tab through
		commentTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (e.getModifiers() > 0) {
                    	commentTextArea.transferFocusBackward();
                    } else {
                    	commentTextArea.transferFocus();
                    }
                    e.consume();
				}
			}
		});
		commentTextArea.setBounds(567, 82, 218, 94);
		contentPanel.add(commentTextArea);

		fNameLabel = new JLabel("First Name (Required)");
		fNameLabel.setBounds(51, 66, 152, 16);
		contentPanel.add(fNameLabel);

		lNameLabel = new JLabel("Last Name (Required)");
		lNameLabel.setBounds(380, 66, 144, 16);
		contentPanel.add(lNameLabel);

		buIdLabel = new JLabel("BU ID (Required)");
		buIdLabel.setBounds(51, 130, 130, 16);
		contentPanel.add(buIdLabel);

		emailLabel = new JLabel("Email (Required)");
		emailLabel.setBounds(215, 130, 130, 16);
		contentPanel.add(emailLabel);

		statusLabel = new JLabel("Status (Required)");
		statusLabel.setBounds(380, 130, 130, 16);
		contentPanel.add(statusLabel);

		mInitialLabel = new JLabel("Middle Initial (Optional)");
		mInitialLabel.setBounds(215, 66, 153, 16);
		contentPanel.add(mInitialLabel);
		
		commentsLabel = new JLabel("Notes:");
		commentsLabel.setBounds(567, 67, 46, 14);
		contentPanel.add(commentsLabel);

		JComboBox studentStatusDropdown = new JComboBox();
		studentStatusDropdown.addItem("Undergraduate");
		studentStatusDropdown.addItem("Graduate");
		// Dropdown defaults to Undergraduate. If student is graduate, set the dropdown to Graduate instead.
		if(managedStudent.getStatus().equals("Graduate")) {
			studentStatusDropdown.setSelectedItem("Graduate");
		}

		studentStatusDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentStatusDropdown.getSelectedItem().equals("Undergraduate")) {
					managedOverallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();
				}
				else {
					managedOverallGradeScheme = managedCourse.getCourseGradDefaultGradeScheme();
				}
				// Rerenders a the grading scheme by removing/adding to the content panel.
				gradingSchemeGrid = new GradingSchemeGrid(managedOverallGradeScheme);
				gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_STUDENT);
				contentPanel.remove(gradingSchemeScrollPane);
				gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
				contentPanel.add(gradingSchemeScrollPane);
				contentPanel.revalidate();
				contentPanel.repaint();
			}
		});

		studentStatusDropdown.setBounds(380, 148, 130, 26);
		contentPanel.add(studentStatusDropdown);

		// Add panel to frame
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		frameConstraints.weighty = 1;

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save and Close");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create a new student from the TextFields.

				//pulls data into variables to make code easier to read/follow, and to allow for a check of required fields
				String fName = fNameField.getText();
				String middleInitial;
				String lName = lNameField.getText();
				String buId = buIdField.getText();
				String email = emailField.getText();
				String comment;
				String gradUndergradStatus = (String) studentStatusDropdown.getSelectedItem();

				if (fName.equals("")||lName.equals("")||buId.equals("")||email.equals("")||studentStatusDropdown.getSelectedIndex()==-1) {
					JOptionPane.showMessageDialog(null, "Please make sure all required fields are filled!");
					return;
				}

				//sets middle initial, if it is not specified
				if (mInitialField.getText() == null) {
					middleInitial = "";
				}
				else {
					middleInitial = mInitialField.getText();
				}
				
				//sets comment field, if it is not specified
				if (commentTextArea.getText() == null) {
					comment = "";
				}
				else {
					comment = commentTextArea.getText();
				}

				// Update this student object. Have to use setters because this is in an inner function call
				OverallGrade gradeFromFields = gradingSchemeGrid.getOverallGradeFromFields();
				managedStudent.setfName(fName);
				managedStudent.setMiddleInitial(middleInitial);
				managedStudent.setlName(lName);
				managedStudent.setBuId(buId);
				managedStudent.setEmail(email);
				managedStudent.setStatus(gradUndergradStatus);
				managedStudent.setOverallGrade(gradeFromFields);
				managedStudent.setComment(comment);

				// Save the changes.
				FileManager.saveFile(MainDashboard.getKwikGrade().getActiveCourses(), MainDashboard.getActiveSaveFileName());
				FileManager.saveFile(MainDashboard.getKwikGrade().getClosedCourses(), MainDashboard.getClosedSaveFileName());

				dispose();
			}
		});
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

		// ======================================
		// Build Grading Scheme Grid Layout
		// ======================================
		gradingSchemeGrid = new GradingSchemeGrid(managedOverallGradeScheme);
		gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.MANAGE_STUDENT);
		gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
		contentPanel.add(gradingSchemeScrollPane);
	}
}
