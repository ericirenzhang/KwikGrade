package views.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import helpers.KwikGradeUIManager;
import helpers.ModelGenerators;
import models.Course;
import models.KwikGrade;

public class ClosedCourseOverviewDialog extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTable studentDisplayTable;
	private DefaultTableModel studentTableModel = new DefaultTableModel();
	private DefaultTableModel statsTableModel = new DefaultTableModel();
	private Course managedCourse;

	private JTable kwikStatsTable;

	/**
	 * Create the dialogs.
	 */
	public ClosedCourseOverviewDialog(KwikGrade kwikGrade, Course managedCourse) {

		this.managedCourse = managedCourse;
		
		// Sets course title for title bar
		String newTitle = managedCourse.getCourseNum()+" "+managedCourse.getCourseTerm()+" "+managedCourse.getCourseTitle();
		setTitle(newTitle);

		KwikGradeUIManager.setUpUI(this, contentPanel, 746, 586);

		//===================================
		// Creating Tables for KwikStats and Student Display
		//===================================
		JScrollPane studentDisplayTableScrollPane = new JScrollPane();
		studentDisplayTableScrollPane.setBounds(10, 38, 545, 498);
		contentPanel.add(studentDisplayTableScrollPane);

		// Creates the table itself
		studentDisplayTable = new JTable();
		ModelGenerators.setDefaultAttributes(studentDisplayTable);
		studentDisplayTableScrollPane.setViewportView(studentDisplayTable);
		studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(managedCourse.getActiveStudents(), true));
		ModelGenerators.setTableSizing(studentDisplayTable, true);

		// Add KwikStats Table
		JScrollPane kwikStatsTableScrollPane = new JScrollPane();
		kwikStatsTableScrollPane.setBounds(565, 366, 155, 170);
		contentPanel.add(kwikStatsTableScrollPane);
		kwikStatsTable = new JTable();
		ModelGenerators.setDefaultAttributes(kwikStatsTable);
		
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
