package views;

import helpers.FileManager;
import models.Course;
import models.KwikGrade;
import models.Student;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.JScrollPane;

public class CourseOverviewFrame extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTable studentDisplayTable;
	private DefaultTableModel studentTableModel = new DefaultTableModel();
	private DefaultTableModel statsTableModel;
	private static Course managedCourse;
	private int selectedRow;
	private JTable kwikStatsTable;

	public DefaultTableModel displayStudents(ArrayList<Student> Students) {
		studentTableModel = new DefaultTableModel();
		Object[] title = {"First Name", "Middle Initial", "Last Name", "Grade"};
		studentTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			studentTableModel.addRow(new Object[] {Students.get(i).getfName(),Students.get(i).getMiddleInitial(), Students.get(i).getlName()} );
		}
		return studentTableModel;
	}
	
	public void updateStudentTable() {
		studentDisplayTable.setModel(displayStudents(managedCourse.getActiveStudents()));
	}
	
	
	
	public DefaultTableModel displayKwikStats(Course course) {
//		statsTableModel.addRow(new Object[] {"Mean", course.calcMean()});
//		statsTableModel.addRow(new Object[] {"Median", course.calcMedian()});
//		statsTableModel.addRow(new Object[] {"Standard Deviation", course.calcStandardDeviation()});
		statsTableModel.addRow(new Object[] {"Mean"});
		statsTableModel.addRow(new Object[] {"Mean"});
		statsTableModel.addRow(new Object[] {"Median"});
		statsTableModel.addRow(new Object[] {"Median"});
		statsTableModel.addRow(new Object[] {"StDev"});
		statsTableModel.addRow(new Object[] {"StDev"});

		return statsTableModel;
	}


	/**
	 * Create the dialog.
	 */
	public CourseOverviewFrame(KwikGrade kwikGrade, Course managedCourse) {
		this.managedCourse = managedCourse;

		setBounds(100, 100, 746, 586);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 38, 545, 498);
		contentPanel.add(scrollPane);

		//Creates the titles for the table model
		//studentTableModel = new DefaultTableModel();

		//Creates the table itself
		studentDisplayTable = new JTable();
		studentDisplayTable.setGridColor(Color.BLACK); // set lines to black for Mac
		studentDisplayTable.setRowHeight(25);
		scrollPane.setViewportView(studentDisplayTable);
		studentDisplayTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		studentDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel listModel = studentDisplayTable.getSelectionModel();
		//this is check to make sure I actually selected something
		listModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ListSelectionModel lsm=(ListSelectionModel) e.getSource();
				if(lsm.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(null, "No selection");
				}
				else {
					selectedRow=lsm.getMinSelectionIndex();
				}
			}
		});
		
		updateStudentTable();

		statsTableModel = new DefaultTableModel();
		Object[] statsTableTitle = {"Kwikstats"};
		statsTableModel.setColumnIdentifiers(statsTableTitle);

		kwikStatsTable = new JTable();
		kwikStatsTable.setGridColor(Color.BLACK); // set lines to black for Mac
		kwikStatsTable.setRowHeight(30);
		kwikStatsTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		kwikStatsTable.setBounds(565, 315, 155, 221);
		contentPanel.add(kwikStatsTable);
		kwikStatsTable.setModel(displayKwikStats(managedCourse));

		// button to add student
		JButton addStudentButton = new JButton("Add Student");
		addStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddStudentFrame addStudent = new AddStudentFrame(managedCourse);
				addStudent.setModal(true);
				addStudent.setVisible(true);
//				Student studentToAdd = addStudent.getNewStudent();
//				managedCourse.addStudent(studentToAdd);
				updateStudentTable();
			}
		});
		addStudentButton.setBounds(565, 11, 155, 40);
		contentPanel.add(addStudentButton);
		
		JButton manageStudentButton = new JButton("Manage Student");
		manageStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Student student = managedCourse.getActiveStudents().get(selectedRow);
				ManageStudentFrame manageStudentFrame = new ManageStudentFrame(student, managedCourse);
				manageStudentFrame.setModal(true);
				manageStudentFrame.setVisible(true);

				if(manageStudentFrame.didSave()) {
					FileManager.saveFile(kwikGrade.getActiveCourses(), MainDashboard.getActiveSaveFileName());
				}
			}
		});
		manageStudentButton.setBounds(565, 62, 155, 40);
		contentPanel.add(manageStudentButton);
		
		JButton addGradeButton = new JButton("Enter New Grades");
		addGradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		addGradeButton.setBounds(565, 110, 155, 40);
		contentPanel.add(addGradeButton);
		
		JButton manageCategoryButton = new JButton("Manage Categories");
		manageCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: add support later for undergraduate vs. graduate scheme on Manage Categories page. Default to Undergrad for now.
				ManageCategoriesFrame manageCategoriesFrame = new ManageCategoriesFrame(managedCourse.getCourseUnderGradDefaultGradeScheme());
				manageCategoriesFrame.setModal(true);
				manageCategoriesFrame.setVisible(true);

				// TODO: delete this later, temp code just to have it shown that it works
//				System.out.println(managedCourse.getCourseGradDefaultGradeScheme().getCourseCategoryList().get(0).getName());
//				System.out.println(managedCourse.getCourseGradDefaultGradeScheme().getCourseCategoryList().get(1).getName());
			}
		});
		manageCategoryButton.setBounds(565, 161, 155, 40);
		contentPanel.add(manageCategoryButton);
		
		JButton saveCloseButton = new JButton("Save and Close");
		saveCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager.saveFile(MainDashboard.getKwikGrade().getActiveCourses(), MainDashboard.getActiveSaveFileName());
				FileManager.saveFile(MainDashboard.getKwikGrade().getClosedCourses(), MainDashboard.getClosedSaveFileName());
				JOptionPane.showMessageDialog(null, "Successfully Saved!");
				dispose();
			}
		});
		saveCloseButton.setBounds(565, 212, 155, 40);
		contentPanel.add(saveCloseButton);
	}
}
