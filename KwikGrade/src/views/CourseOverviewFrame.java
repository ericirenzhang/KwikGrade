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
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.JScrollPane;

public class CourseOverviewFrame extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTable studentDisplayTable;
	private Course managedCourse;
	private DefaultTableModel statsTableModel;

	private JTable kwikStatsTable;

	public DefaultTableModel generateStudentTableModel(ArrayList<Student> Students) {
		DefaultTableModel studentTableModel;
		studentTableModel = new DefaultTableModel();
		Object[] title = {"First Name", "Middle Initial", "Last Name", "Grade"};
		studentTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			studentTableModel.addRow(new Object[] {Students.get(i).getfName(),Students.get(i).getMiddleInitial(), Students.get(i).getlName(), Students.get(i).getOverallGradeObject().getOverallGrade()} );
		}
		return studentTableModel;
	}

	public void setManagedCourse(Course managedCourse) {
		this.managedCourse = managedCourse;
	}

	public Course getManagedCourse() {
		return this.managedCourse;
	}

	// Updates the Stats Model with the values from course object
	public void updateStatsModel(Course course, DefaultTableModel statsTableModel) {
		statsTableModel.setValueAt(String.format("Mean: %.2f%%", course.calcMean()), 0, 0);
		statsTableModel.setValueAt(String.format("Median: %.2f%%", course.calcMedian()), 1, 0);
		statsTableModel.setValueAt(String.format("Std Dev: %.2f%%", course.calcStandardDeviation()), 2, 0);
	}

	/**
	 * Create the dialog.
	 */
	public CourseOverviewFrame(KwikGrade kwikGrade, Course managedCourse) {
		this.managedCourse = managedCourse;
		
		//Sets course title for title bar
		String newTitle = managedCourse.getCourseNum()+" "+managedCourse.getCourseTerm()+" "+managedCourse.getCourseTitle();
		setTitle(newTitle);

		setBounds(100, 100, 746, 586);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		//===================================
		//Creating Tables for Kwikstats and Student Display
		//===================================

		// Add Student Table
		JScrollPane studentDisplayTableScrollPane = new JScrollPane();
		studentDisplayTableScrollPane.setBounds(10, 38, 545, 498);
		contentPanel.add(studentDisplayTableScrollPane);
		studentDisplayTable = new JTable();
		studentDisplayTable.setGridColor(Color.BLACK); // set lines to black for Mac
		studentDisplayTable.setRowHeight(25);
		studentDisplayTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		studentDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
		studentDisplayTableScrollPane.setViewportView(studentDisplayTable);

		// Add KwikStats Table
		JScrollPane kwikStatsTableScrollPane = new JScrollPane();
		kwikStatsTableScrollPane.setBounds(565, 315, 155, 221);
		contentPanel.add(kwikStatsTableScrollPane);
		kwikStatsTable = new JTable();
		kwikStatsTable.setGridColor(Color.BLACK); // set lines to black for Mac
		kwikStatsTable.setRowHeight(30);
		kwikStatsTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		// Initialize values for stats table
		statsTableModel = new DefaultTableModel(3, 1);
		statsTableModel.setColumnIdentifiers(new Object[]{"KwikStats"});
		updateStatsModel(managedCourse, statsTableModel);
		kwikStatsTable.setModel(statsTableModel);
		kwikStatsTableScrollPane.setViewportView(kwikStatsTable);

		//===================================
		//Creating and Displaying buttons
		//===================================
		JButton addStudentButton = new JButton("Add Student");
		addStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddStudentFrame addStudent = new AddStudentFrame(getManagedCourse());
				addStudent.setModal(true);
				addStudent.setVisible(true);

				// Update our models for the current frame
				studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
				updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
			}
		});
		addStudentButton.setBounds(565, 11, 155, 40);
		contentPanel.add(addStudentButton);
		
		JButton manageStudentButton = new JButton("Manage Student");
		manageStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checks to see if you actually selected anything from the table
				if(studentDisplayTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "No selection");
					return;
				}
				else {
					Student student = getManagedCourse().getActiveStudents().get(studentDisplayTable.getSelectedRow());
					ManageStudentFrame manageStudentFrame = new ManageStudentFrame(student, getManagedCourse());
					manageStudentFrame.setModal(true);
					manageStudentFrame.setVisible(true);

					studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
					updateStatsModel(managedCourse, statsTableModel);
					kwikStatsTable.setModel(statsTableModel);
				}

			}
		});
		manageStudentButton.setBounds(565, 62, 155, 40);
		contentPanel.add(manageStudentButton);
		
		// Double click on a student to manage
		studentDisplayTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	manageStudentButton.doClick(); 
		        }
		    }
		});		
		
		JButton addGradeButton = new JButton("Enter New Grades");
		addGradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddGradeFrame addGrade = new AddGradeFrame(getManagedCourse());
				addGrade.setModal(true);
				addGrade.setVisible(true);

				studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
				updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
			}
		});

		addGradeButton.setBounds(565, 110, 155, 40);
		contentPanel.add(addGradeButton);
		
		JButton manageCategoryButton = new JButton("Manage Categories");
		manageCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageCategoriesFrame manageCategoriesFrame = new ManageCategoriesFrame(managedCourse);
				manageCategoriesFrame.setModal(true);
				manageCategoriesFrame.setVisible(true);

				studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
				updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
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
