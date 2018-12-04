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
	private DefaultTableModel studentTableModel = new DefaultTableModel();
	private DefaultTableModel statsTableModel = new DefaultTableModel();
	private Course managedCourse;

	private JTable kwikStatsTable;

	public DefaultTableModel generateStudentTableModel(ArrayList<Student> Students) {
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
	
	
	//so this broke. I dont know how to get it to refresh, cause when you add new grades, it somehow adds a new table below the current table
	//i tried doing statsTableModel = new DefaultTableModel() but that didnt work, it left the table blank
	//TODO: fix this
	public DefaultTableModel displayKwikStats(Course course) {
		statsTableModel.addRow(new Object[] {"Mean"});
		statsTableModel.addRow(new Object[] {course.calcMean()});
		statsTableModel.addRow(new Object[] {"Median"});
		statsTableModel.addRow(new Object[] {course.calcMedian()});
		statsTableModel.addRow(new Object[] {"StDev"});
		statsTableModel.addRow(new Object[] {course.calcStandardDeviation()});

		return statsTableModel;
	}
	
	public DefaultTableModel updateKwikStats(Course course) {
		//statsTableModel= (DefaultTableModel) kwikStatsTable.getModel();
		statsTableModel.setValueAt(course.calcMean(), 2, 1);
		statsTableModel.setValueAt(course.calcMedian(), 4, 1);
		statsTableModel.setValueAt(course.calcStandardDeviation(), 6, 1);
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

		//===================================
		//Creating Tables for Kwikstats and Student Display
		//===================================
		
		JScrollPane studentDisplayTableScrollPane = new JScrollPane();
		studentDisplayTableScrollPane.setBounds(10, 38, 545, 498);
		contentPanel.add(studentDisplayTableScrollPane);

		//Creates the table itself
		studentDisplayTable = new JTable();
		studentDisplayTable.setGridColor(Color.BLACK); // set lines to black for Mac
		studentDisplayTable.setRowHeight(25);
		studentDisplayTableScrollPane.setViewportView(studentDisplayTable);
		studentDisplayTable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		studentDisplayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));

		kwikStatsTable = new JTable();
		kwikStatsTable.setGridColor(Color.BLACK); // set lines to black for Mac
		kwikStatsTable.setRowHeight(30);
		kwikStatsTable.setFont(new Font("Tahoma", Font.PLAIN, 24));
		kwikStatsTable.setBounds(565, 315, 155, 221);
		contentPanel.add(kwikStatsTable);
		kwikStatsTable.setModel(displayKwikStats(managedCourse));

		//===================================
		//Creating and Displaying buttons
		//===================================
		
		JButton addStudentButton = new JButton("Add Student");
		addStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddStudentFrame addStudent = new AddStudentFrame(getManagedCourse());
				addStudent.setModal(true);
				addStudent.setVisible(true);
				studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
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

					if(manageStudentFrame.didSave()) {
						FileManager.saveFile(kwikGrade.getActiveCourses(), MainDashboard.getActiveSaveFileName());
					}
				}
				kwikStatsTable.setModel(updateKwikStats(managedCourse));
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

				setManagedCourse(addGrade.getManagedCourse());
				studentDisplayTable.setModel(generateStudentTableModel(managedCourse.getActiveStudents()));
				kwikStatsTable.setModel(updateKwikStats(managedCourse));
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
				kwikStatsTable.setModel(updateKwikStats(managedCourse));
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
