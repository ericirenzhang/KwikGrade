package views.dialogs;

import helpers.KwikGradeUIManager;
import models.KwikGrade;
import models.OverallGrade;
import models.Student;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CreateCourseDialog extends JDialog {
	private String courseNum;
	private String courseTerm;
	private String courseTitle;
	private boolean hasCreatedNewCourse;

	private ArrayList<Student> importedStudentsList = new ArrayList<>();
	
	private OverallGrade ugOverallGrade;
	private OverallGrade gradOverallGrade;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialogs.
	 */
	public CreateCourseDialog() {
		KwikGradeUIManager.setUpUI(this, contentPanel, 450, 300);

		JButton createNewButton = new JButton("Create From New");
		createNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateFromNewDialog createFromNew = new CreateFromNewDialog();
				createFromNew.setModal(true);
				createFromNew.setVisible(true);

				courseNum = createFromNew.getCourseNum();
				courseTerm = createFromNew.getCourseTerm();
				courseTitle = createFromNew.getCourseTitle();
				importedStudentsList = createFromNew.getImportedStudentsList();
				hasCreatedNewCourse = createFromNew.getHasCreatedNewCourse();
				ugOverallGrade = createFromNew.getUGOverallGrade();
				gradOverallGrade = createFromNew.getGradOverallGrade();

				dispose();
			}
		});

		createNewButton.setBounds(10, 69, 183, 181);
		contentPanel.add(createNewButton);
		JButton createExistingButton = new JButton("Create From Existing");
		createExistingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateFromExistingDialog createFromExisting = new CreateFromExistingDialog();
				createFromExisting.setModal(true);
				createFromExisting.setVisible(true);

				courseNum = createFromExisting.getCourseNum();
				courseTerm = createFromExisting.getCourseTerm();
				courseTitle = createFromExisting.getCourseTitle();
				importedStudentsList = createFromExisting.getImportedStudentsList();
				hasCreatedNewCourse = createFromExisting.getHasCreatedNewCourse();
				ugOverallGrade = createFromExisting.getUGOverallGrade();
				gradOverallGrade = createFromExisting.getGradOverallGrade();

				dispose();

			}
		});
		createExistingButton.setBounds(241, 69, 183, 181);
		contentPanel.add(createExistingButton);
		JLabel createLabel = new JLabel("How would you like to create your course?");
		createLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		createLabel.setBounds(66, 25, 302, 20);
		contentPanel.add(createLabel);
	}
	
	//==========================
	// Getters
	//==========================
	
	public String getCourseNum() {
		return this.courseNum;
	}
	public String getCourseTerm() {
		return this.courseTerm;
	}
 	public String getCourseTitle() {
		return this.courseTitle;
	}
 	public ArrayList<Student> getImportedStudentsList() {
 		return this.importedStudentsList;
 	}
 	public boolean getHasCreatedNewCourse() {
		return this.hasCreatedNewCourse;
	}
	public OverallGrade getUGOverallGrade() {
		return ugOverallGrade;
	}
	public OverallGrade getGradOverallGrade() {
		return gradOverallGrade;
	}

	//==========================
	// Setters
	//==========================
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}
 	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public void setUGOverallGrade(OverallGrade ugOverallGrade) {
		this.ugOverallGrade = ugOverallGrade;
	}
	public void setGradOverallGrade(OverallGrade gradOverallGrade) {
		this.gradOverallGrade = gradOverallGrade;
	}
}