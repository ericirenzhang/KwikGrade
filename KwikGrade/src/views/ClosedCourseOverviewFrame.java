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
import helpers.ModelGenerators;
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
		ModelGenerators.defaultTableStarter(studentDisplayTable);
		studentDisplayTableScrollPane.setViewportView(studentDisplayTable);
		studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(managedCourse.getActiveStudents(), true));
		ModelGenerators.studentTableSizing(studentDisplayTable, true);

		// Add KwikStats Table
		JScrollPane kwikStatsTableScrollPane = new JScrollPane();
		kwikStatsTableScrollPane.setBounds(565, 366, 155, 170);
		contentPanel.add(kwikStatsTableScrollPane);
		kwikStatsTable = new JTable();
		ModelGenerators.defaultTableStarter(kwikStatsTable);
		
		// Initialize values for stats table
		statsTableModel = new DefaultTableModel(3, 1);
		statsTableModel.setColumnIdentifiers(new Object[]{"KwikStats"});
		ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
		kwikStatsTable.setModel(statsTableModel);
		kwikStatsTableScrollPane.setViewportView(kwikStatsTable);
		
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
