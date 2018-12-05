package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import helpers.FileManager;
import models.Course;
import models.KwikGrade;
import models.Student;

public class ClosedCourseOverviewFrame extends JDialog {
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
	public ClosedCourseOverviewFrame(KwikGrade kwikGrade, Course managedCourse) {

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
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeButton.setBounds(565, 212, 155, 40);
		contentPanel.add(closeButton);
	}
}
