package views;

import models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageStudentFrame extends JDialog {
	private static int GRADING_SCHEME_ROW_COUNT = 5;
	private static int GRADING_SCHEME_WIDTH = 900;
	private static int GRADING_SCHEME_HEIGHT = 300;
	private static int GRADING_SCHEME_COL_OFFSET = 2;

	private static Color LIGHT_GRAY_COLOR = new Color(0xF0F0F0);
	private static Color DARK_GRAY_COLOR = new Color(0xE0E0E0);
	private static Color LIGHT_GREEN_COLOR = new Color(0x97FFBF);

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

	private Student newStudent;

	private boolean didSave;

	/**
	 * Create the dialog.
	 */
	public ManageStudentFrame(Student managedStudent, Course managedCourse) {
		OverallGrade overallGradeScheme;

		//TODO: add if-else logic on which overall grade scheme to use. By default we will use the Undergraduate schema
		overallGradeScheme = managedCourse.getCourseUnderGradDefaultGradeScheme();
		
		setBounds(100, 100, 1000, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		GridBagConstraints frameConstraints = new GridBagConstraints();
		
		fNameField = new JTextField(managedStudent.getfName());
		fNameField.setBounds(53, 60, 130, 26);
		contentPanel.add(fNameField);
		fNameField.setColumns(10);

		lNameField = new JTextField(managedStudent.getlName());
		lNameField.setBounds(382, 60, 130, 26);
		lNameField.setColumns(10);
		contentPanel.add(lNameField);

		buIdField = new JTextField(managedStudent.getBuId());
		buIdField.setBounds(53, 124, 130, 26);
		buIdField.setColumns(10);
		contentPanel.add(buIdField);

		emailField = new JTextField(managedStudent.getEmail());
		emailField.setBounds(217, 124, 130, 26);
		emailField.setColumns(10);
		contentPanel.add(emailField);

		// TODO: update this with a dropdown menu instead.
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

				managedCourse.addActiveStudents(newStudent);
				didSave = true;
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

		// ======================================
		// Build Grading Scheme Grid Layout
		// ======================================
		JScrollPane gradingSchemeScrollPane = generateGradingSchemeTable(overallGradeScheme);
		contentPanel.add(gradingSchemeScrollPane);
	}

	/**
     * Generates the grading scheme table, which is built using GridLayout.
	 *
	 * We build this using a nested structure as follows:
	 * - JScrollPane that we return
	 * 	- Parent JPanel that uses a GridLayout and holds...
	 * 		- 2D array of JPanels, where each JPanel represents a "cell" in the table
	 * 			- Each array cell can then contain JLabels, JLabels + JTextField, etc
	 *
	 * @param overallGradeScheme
     * @return JScrollPane containing the entire grading scheme table
	 */
	private JScrollPane generateGradingSchemeTable(OverallGrade overallGradeScheme) {
		JPanel parentPanel = new JPanel();
		parentPanel.setBackground(Color.WHITE);

		// Number of columns in our GridLayout will be equivalent to number of categories + subcategories.
		int numCols = getNumCategoryAndSubCategory(overallGradeScheme);

		// Define GridLayout.
		parentPanel.setLayout(new GridLayout(GRADING_SCHEME_ROW_COUNT, numCols));
		parentPanel.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

		// Create grid of JPanels
		JPanel[][] schemeGrid = new JPanel[GRADING_SCHEME_ROW_COUNT][numCols];
		for(int i = 0; i < GRADING_SCHEME_ROW_COUNT; i++) {
			for(int j = 0; j < numCols; j++) {
				schemeGrid[i][j] = new JPanel();
				JPanel currPanel = schemeGrid[i][j];
				currPanel.setPreferredSize(new Dimension(150, 50));

				// Styling to make the table look like the mockup.
				if(i == 0) {
					currPanel.setBackground(LIGHT_GRAY_COLOR);
				} else if (i == 1) {
					currPanel.setBackground(DARK_GRAY_COLOR);
				} else if (i == 3){
					currPanel.setBackground(LIGHT_GREEN_COLOR);
				} else {
					currPanel.setBackground(Color.WHITE);
				}

				// Only add borders on vertical sides.
				currPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(0xD1D0D1)));

				parentPanel.add(currPanel);
			}
		}

		// Set up static cells since these will be hardcoded.
		schemeGrid[0][1].add(new JLabel("Final Grade"));
		schemeGrid[2][0].add(new JLabel("Total Percentage"));

		// TODO: once we add in the toggle for points gained and points subtracted, this string will have to change dynamically
		schemeGrid[3][0].add(new JLabel("Points Gained"));
		schemeGrid[4][0].add(new JLabel("Total Points"));

		// The first two columns are static with text. When we build the JPanels in the columns, they need to be offset by these initial columns with static text.
		int colOffset = 2;
		for(int i = 0; i < overallGradeScheme.getCourseCategoryList().size(); i++) {
			CourseCategory currCategory = overallGradeScheme.getCourseCategoryList().get(i);
			String categoryWeightPercentage = String.format("%.2f%%%n", 100 * currCategory.getWeight());

			schemeGrid[0][i+colOffset].add(new JLabel(currCategory.getName()));
			schemeGrid[0][i+colOffset].add(new JTextField(categoryWeightPercentage));

			for (int j = 0; j < currCategory.getSubCategoryList().size(); j++) {
				SubCategory currSubCategory = currCategory.getSubCategoryList().get(j);
				String subCategoryWeightPercentage = String.format("%.2f%%%n", 100 * currSubCategory.getWeight());

				// Show name and weight percentage
				schemeGrid[1][j+i+colOffset+1].add(new JLabel(currSubCategory.getName()));
				schemeGrid[1][j+i+colOffset+1].add(new JTextField(subCategoryWeightPercentage));

				// TODO: show total percentage

				// show points gained

				// show total points
			}
			colOffset += currCategory.getSubCategoryList().size();
		}

		// Add everything we just built into a JScrollPane.
		JScrollPane scrollPane = new JScrollPane(parentPanel);
		scrollPane.setBounds(75, 200, GRADING_SCHEME_WIDTH, GRADING_SCHEME_HEIGHT);

		return scrollPane;
	}

	public int getNumCategoryAndSubCategory(OverallGrade overallGradeScheme) {
		int numCols = 2; // the first two columns are allocated to static text
		for(int i = 0; i < overallGradeScheme.getCourseCategoryList().size(); i++) {
			CourseCategory currCategory = overallGradeScheme.getCourseCategoryList().get(i);
			numCols += 1;

			// TODO: delete this once we're able to have SubCategories and test this
			SubCategory tmpSubCategory = new SubCategory(currCategory.getName() + "1", 0.4, 40.0, 50.0);
			currCategory.addSubCategory(tmpSubCategory);
			tmpSubCategory = new SubCategory(currCategory.getName() + "2", 0.4, 40.0, 50.0);
			currCategory.addSubCategory(tmpSubCategory);

			for (int j = 0; j < currCategory.getSubCategoryList().size(); j++) {
				numCols += 1;
			}
		}

		return numCols;
	}

	public boolean didSave() {
		return this.didSave;
	}
}
