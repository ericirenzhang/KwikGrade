package views;

import helpers.FileManager;
import views.components.GradingSchemeGrid;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.Course;
import models.CourseCategory;
import models.GraduateStudent;
import models.OverallGrade;
import models.Student;
import models.SubCategory;
import models.UndergraduateStudent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddStudentFrame extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField buIdField;
	private JTextField emailField;
	private JTextField mInitialField;
	private JTextArea commentTextArea;
	private JLabel fNameLabel;
	private JLabel lNameLabel;
	private JLabel buIdLabel;
	private JLabel emailLabel;
	private JLabel statusLabel;
	private JLabel mInitialLabel;
	private JLabel commentsLabel;
	private JScrollPane gradingSchemeScrollPane;
	private JLabel titleLabel;

	private Student newStudent;
	private OverallGrade studentOverallGrade;
	private OverallGrade overallGradeScheme;
	private GradingSchemeGrid gradingSchemeGrid;
	//private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
	public static final String VALID_EMAIL_ADDRESS_REGEX = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	Pattern pattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX);

	/**
	 * Create the dialog.
	 */
	public AddStudentFrame(Course managedCourse) {
		overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();

		setBounds(100, 100, 1000, 600);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		GridBagConstraints frameConstraints = new GridBagConstraints();

		titleLabel = new JLabel("Add a new Student");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		titleLabel.setBounds(54, 31, 194, 21);
		contentPanel.add(titleLabel);

		fNameField = new JTextField();
		fNameField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent evt) {
	         if(!(Character.isLetter(evt.getKeyChar()))){
	                evt.consume();
	            }
	        }
	    });
		fNameField.setBounds(54, 79, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);
		
		mInitialField = new JTextField();
		mInitialField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent evt) {
	         if(!(Character.isLetter(evt.getKeyChar()))){
	                evt.consume();
	            }
	        }
	    });	
		mInitialField.setBounds(218, 79, 130, 26);
		mInitialField.setColumns(10);
		contentPanel.add(mInitialField);

		lNameField = new JTextField();
		lNameField.addKeyListener(new KeyAdapter() {
	        public void keyTyped(KeyEvent evt) {
	         if(!(Character.isLetter(evt.getKeyChar()))){
	                evt.consume();
	            }
	        }
	    });		
		lNameField.setBounds(383, 79, 130, 26);
		lNameField.setColumns(10);
		contentPanel.add(lNameField);

		buIdField = new JTextField();
		buIdField.setBounds(54, 143, 130, 26);
		buIdField.setColumns(10);
		contentPanel.add(buIdField);

		emailField = new JTextField();
		emailField.setBounds(218, 143, 130, 26);
		emailField.setColumns(10);
		contentPanel.add(emailField);
		
		commentTextArea = new JTextArea();
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
		commentTextArea.setBounds(570, 80, 218, 94);
		contentPanel.add(commentTextArea);

		fNameLabel = new JLabel("First Name (Required)");
		fNameLabel.setBounds(54, 64, 148, 16);
		contentPanel.add(fNameLabel);

		lNameLabel = new JLabel("Last Name (Required)");
		lNameLabel.setBounds(385, 63, 153, 16);
		contentPanel.add(lNameLabel);

		buIdLabel = new JLabel("BU ID (Required)");
		buIdLabel.setBounds(54, 128, 130, 16);
		contentPanel.add(buIdLabel);

		emailLabel = new JLabel("Email (Required)");
		emailLabel.setBounds(218, 128, 130, 16);
		contentPanel.add(emailLabel);

		statusLabel = new JLabel("Status (Required)");
		statusLabel.setBounds(383, 128, 130, 16);
		contentPanel.add(statusLabel);

		mInitialLabel = new JLabel("Middle Initial (Optional)");
		mInitialLabel.setBounds(218, 64, 154, 16);
		contentPanel.add(mInitialLabel);
		
		commentsLabel = new JLabel("Notes:");
		commentsLabel.setBounds(570, 65, 46, 14);
		contentPanel.add(commentsLabel);
		
		JComboBox studentStatusDropdown = new JComboBox();
		studentStatusDropdown.addItem("Undergraduate");
		studentStatusDropdown.addItem("Graduate");

		studentStatusDropdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (studentStatusDropdown.getSelectedItem().equals("Undergraduate")) {
					overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();
				}
				else {
					overallGradeScheme = managedCourse.getCourseGradDefaultGradeScheme();
				}
				// Rerenders a the grading scheme by removing/adding to the content panel.
				gradingSchemeGrid = new GradingSchemeGrid(overallGradeScheme);
				gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.ADD_STUDENT);
				contentPanel.remove(gradingSchemeScrollPane);
				gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
				contentPanel.add(gradingSchemeScrollPane);
				contentPanel.revalidate();
				contentPanel.repaint();
			}
		});

		studentStatusDropdown.setBounds(383, 146, 130, 26);
		contentPanel.add(studentStatusDropdown);
		
		// Add panel to frame
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		frameConstraints.weighty = 1;

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton saveButton = new JButton("Save and Add");
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
				Matcher matcher = pattern.matcher(email);
		        System.out.println("EMAIL: "+buId+" "+matcher.matches());
				if (!matcher.matches()) {
					JOptionPane.showMessageDialog(null, "Please enter valid email");
					emailField.setText("");
				}
				else {
				String gradUndergrad = (String) studentStatusDropdown.getSelectedItem();

				
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
								
				// Creates undergraduate or graduate students, and creates copies of default grading schemes.
				if (gradUndergrad.equals("Undergraduate")) {
					studentOverallGrade = gradingSchemeGrid.getOverallGradeFromFields();
					newStudent = new UndergraduateStudent(fName, middleInitial, lName, buId, email, gradUndergrad, studentOverallGrade, comment);
				}
				else {
					studentOverallGrade = gradingSchemeGrid.getOverallGradeFromFields();
					newStudent = new GraduateStudent(fName, middleInitial, lName, buId, email, gradUndergrad, studentOverallGrade, comment);
				}
				// Add the student to the course.
				managedCourse.addActiveStudents(newStudent);

				// Save the changes.
				FileManager.saveFile(MainDashboard.getKwikGrade().getActiveCourses(), MainDashboard.getActiveSaveFileName());
				FileManager.saveFile(MainDashboard.getKwikGrade().getClosedCourses(), MainDashboard.getClosedSaveFileName());
				dispose();
				}
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
		gradingSchemeGrid = new GradingSchemeGrid(overallGradeScheme);
		gradingSchemeGrid.configureGradingSchemeGrid(GradingSchemeGrid.GradingSchemeType.ADD_STUDENT);
		gradingSchemeScrollPane = gradingSchemeGrid.buildGradingSchemeGrid();
		contentPanel.add(gradingSchemeScrollPane);

	}
}
