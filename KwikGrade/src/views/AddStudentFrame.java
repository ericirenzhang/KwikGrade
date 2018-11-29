package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.Course;
import models.CourseCategory;
import models.OverallGrade;
import models.Student;
import models.SubCategory;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class AddStudentFrame extends JDialog {
	
	int n;
	private final JPanel contentPanel = new JPanel();
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField buIdField;
	private JTextField emailField;
	private JTextField statusField;
	private JTextField mInitialField;
	private JLabel lNameLabel;
	private JLabel buIdLabel;
	private JLabel emailLabel;
	private JLabel statusLabel;
	private JLabel mInitialLabel;
	private JButton saveButton;
	private JButton backButton;

	private Student newStudent = new Student("","","","","");

	// TODO: replace this with actual course information
	Course dummyCourse = new Course("CS591",  "F18", "Java Intro", new OverallGrade(), new OverallGrade());

	SubCategory subCat1, subCat2;
	ArrayList<SubCategory> subCatList1 = new ArrayList<SubCategory>();
	ArrayList<SubCategory> subCatList2 = new ArrayList<SubCategory>();
	CourseCategory courseCat1, courseCat2;
	ArrayList<CourseCategory> courseCatList = new ArrayList<CourseCategory>();
	OverallGrade og;

    static List<JLabel> gradingSchemeLabels = new ArrayList<JLabel>();
    static List<JTextField> gradingSchemeFields = new ArrayList<JTextField>();
    private JPanel gradingSchemePanel;
    private JScrollPane gradingSchemeScrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddStudentFrame dialog = new AddStudentFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddStudentFrame() {

		//TODO: remove this later. Dummy data with 2 categories. One with 2 sub categories and the other with no sub category (handled as having one sub category) ***
		n = 1; //To keep track of number of rows to add in the grading scheme panel
		
		subCat1 = new SubCategory("hw1", 0.4, 0.0, 40.0, 50.0);
		subCat2 = new SubCategory("hw2", 0.6, 0.0, 30.0, 50.0);
		subCatList1.add(subCat1);
		subCatList1.add(subCat2);
		courseCat1 = new CourseCategory("HW", 0.3, subCatList1);
		
		subCat1 = new SubCategory("final1", 0.7, 0.0, 80.0, 100.0); //In future, have to handle Categories with no sub categories
		subCatList2.add(subCat1);
		courseCat2 = new CourseCategory("Final", 0.7, subCatList2);
		courseCatList.add(courseCat1);
		courseCatList.add(courseCat2);
		og  = new OverallGrade(95.0, 2, courseCatList);
		n += courseCatList.size();
		n += subCatList1.size();
		n += subCatList2.size();
		
		setBounds(100, 100, 813, 559);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//setLayout(new GridBagLayout());
		
		GridBagConstraints frameConstraints = new GridBagConstraints();
		
		fNameField = new JTextField();
		fNameField.setBounds(53, 60, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);

		lNameField = new JTextField();
		lNameField.setBounds(382, 60, 130, 26);
		lNameField.setColumns(10);
		contentPanel.add(lNameField);

		buIdField = new JTextField();
		buIdField.setBounds(53, 124, 130, 26);
		buIdField.setColumns(10);
		contentPanel.add(buIdField);

		emailField = new JTextField();
		emailField.setBounds(217, 124, 130, 26);
		emailField.setColumns(10);
		contentPanel.add(emailField);

		statusField = new JTextField();
		statusField.setBounds(382, 124, 130, 26);
		statusField.setColumns(10);
		contentPanel.add(statusField);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(53, 45, 98, 16);
		contentPanel.add(lblFirstName);

		lNameLabel = new JLabel("Last Name");
		lNameLabel.setBounds(382, 45, 81, 16);
		contentPanel.add(lNameLabel);

		buIdLabel = new JLabel("BU ID");
		buIdLabel.setBounds(53, 109, 61, 16);
		contentPanel.add(buIdLabel);

		emailLabel = new JLabel("Email");
		emailLabel.setBounds(217, 109, 61, 16);
		contentPanel.add(emailLabel);

		statusLabel = new JLabel("Status");
		statusLabel.setBounds(382, 109, 61, 16);
		contentPanel.add(statusLabel);

		mInitialField = new JTextField();
		mInitialField.setBounds(217, 60, 130, 26);
		mInitialField.setColumns(10);
		contentPanel.add(mInitialField);

		mInitialLabel = new JLabel("Middle Initial");
		mInitialLabel.setBounds(217, 45, 98, 16);
		contentPanel.add(mInitialLabel);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Student newStudent;
				if(mInitialField.getText() != null)
					newStudent = new Student(fNameField.getText(), mInitialField.getText(), lNameField.getText(), buIdField.getText(), emailField.getText());
				else
					newStudent = new Student(fNameField.getText(),"", lNameField.getText(), buIdField.getText(), emailField.getText());

				dummyCourse.addActiveStudents(newStudent);
			}
		});
		
		saveButton.setBounds(597, 85, 117, 29);
		contentPanel.add(saveButton);

		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 //new MainDashboard().setVisible(true); //This actually has to go back to Course Management page
				dispose();
			}
		});
		backButton.setBounds(597, 124, 117, 29);
		contentPanel.add(backButton);

		// Add panel to frame
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		frameConstraints.weighty = 1;

		gradingSchemeScrollPane = new JScrollPane();
		gradingSchemeScrollPane.setBounds(60, 209, 654, 253);
		contentPanel.add(gradingSchemeScrollPane);
		gradingSchemePanel = new JPanel();
		gradingSchemeScrollPane.setViewportView(gradingSchemePanel);
		gradingSchemePanel.setPreferredSize(new Dimension(300, 300));
		gradingSchemePanel.setLayout(new GridBagLayout());
		gradingSchemePanel.setBorder(LineBorder.createBlackLineBorder());

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		gradingSchemePanel.removeAll();
		gradingSchemeFields.clear();
		gradingSchemeLabels.clear();

		// Create constraints
		GridBagConstraints textFieldConstraints = new GridBagConstraints();
		GridBagConstraints labelConstraints = new GridBagConstraints();
		ArrayList<ArrayList<SubCategory>> listOfLists = new ArrayList<ArrayList<SubCategory>>();
		listOfLists.add(subCatList1);
		listOfLists.add(subCatList2);
		ArrayList<String> inputText = new ArrayList<String>();
		inputText.add("FinalGrade");
		//for each CourseCategory
		//add each subCategory name
		for(int i=0;i<courseCatList.size();i++) {
			inputText.add(courseCatList.get(i).getName());
			for(int j=0;j<listOfLists.get(i).size();j++) {
				inputText.add(listOfLists.get(i).get(j).getName());
			}
		}

		// Create label and text field
		for(int i=0;i<n;i++) {
			JTextField jTextField = new JTextField();
			jTextField.setSize(100, 200);
			gradingSchemeFields.add(jTextField);
			gradingSchemeLabels.add(new JLabel(inputText.get(i)));

			// Text field constraints
			textFieldConstraints.gridx = 1;
			textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
			textFieldConstraints.weightx = 0.5;
			textFieldConstraints.insets = new Insets(10, 10, 10, 10);
			textFieldConstraints.gridy = i;

			// Label constraints
			labelConstraints.gridx = 0;
			labelConstraints.gridy = i;
			labelConstraints.insets = new Insets(10, 10, 10, 10);

			// Add them to panel
			gradingSchemePanel.add(gradingSchemeLabels.get(i), labelConstraints);
			gradingSchemePanel.add(gradingSchemeFields.get(i), textFieldConstraints);
		}

		// Align components top-to-bottom
					/*GridBagConstraints dummyCourse = new GridBagConstraints();
					dummyCourse.gridx = 0;
					dummyCourse.gridy = indexer;
					dummyCourse.weighty = 1;
					gradingSchemePanel.add(new JLabel(), dummyCourse);*/
		gradingSchemePanel.updateUI();
	}

	//==========================
	// Getters
	//==========================
	public Student getNewSudent() {
		return this.newStudent;
	}
}
