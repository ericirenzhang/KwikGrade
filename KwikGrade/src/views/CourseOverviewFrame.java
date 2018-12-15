package views;

import helpers.FileManager;
import helpers.ModelGenerators;
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
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class CourseOverviewFrame extends JDialog {
	private final JPanel contentPanel = new JPanel();
	private JTable studentDisplayTable;
	private Course managedCourse;
	private DefaultTableModel statsTableModel;
	private boolean detailedView = false;

	private JTable kwikStatsTable;

	public void setManagedCourse(Course managedCourse) {
		this.managedCourse = managedCourse;
	}

	public Course getManagedCourse() {
		return this.managedCourse;
	}

	/**
	 * Create the dialog.
	 */
	public CourseOverviewFrame(KwikGrade kwikGrade, Course managedCourse) {
		this.managedCourse = managedCourse;
		
		//Sets course title for title bar
		String newTitle = managedCourse.getCourseNum()+" "+managedCourse.getCourseTerm()+" "+managedCourse.getCourseTitle();
		setTitle(newTitle);

		setBounds(100, 100, 746, 700);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		//===================================
		//Creating Tables for Kwikstats and Student Display
		//===================================

		// Add Student Table
		JScrollPane studentDisplayTableScrollPane = new JScrollPane();
		studentDisplayTableScrollPane.setBounds(10, 38, 545, 620);
		contentPanel.add(studentDisplayTableScrollPane);
		studentDisplayTable = new JTable();
		ModelGenerators.setDefaultAttributes(studentDisplayTable);
		studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(managedCourse.getActiveStudents(), detailedView));
		studentDisplayTableScrollPane.setViewportView(studentDisplayTable);
		
		JComboBox viewOption = new JComboBox();
		viewOption.addItem("Streamlined View");
		viewOption.addItem("Detailed View");
		viewOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (viewOption.getSelectedItem().equals("Detailed View")) {
					
					//sets column widths for detailed table
					detailedView = true;
					studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(managedCourse.getActiveStudents(), detailedView));
					ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
				}
				else {
					detailedView = false;
					studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(managedCourse.getActiveStudents(), detailedView));
					ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
				}
			}
		});
		viewOption.setBounds(10, 7, 143, 20);
		contentPanel.add(viewOption);
		
		// Add KwikStats Table
		JScrollPane kwikStatsTableScrollPane = new JScrollPane();
		kwikStatsTableScrollPane.setBounds(567, 488, 155, 170);
		contentPanel.add(kwikStatsTableScrollPane);
		kwikStatsTable = new JTable();
		ModelGenerators.setDefaultAttributes(kwikStatsTable);
		
		// Initialize values for stats table
		statsTableModel = new DefaultTableModel(3, 1);
		statsTableModel.setColumnIdentifiers(new Object[]{"KwikStats"});
		ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
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
				studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(getManagedCourse().getActiveStudents(), detailedView));
				ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
				ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
			}
		});
		addStudentButton.setBounds(565, 36, 155, 40);
		contentPanel.add(addStudentButton);
		
		JButton dropStudentButton = new JButton("Drop Student");
		dropStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(studentDisplayTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "No student selected!");
					return;
				}
				else {
					Student studentToDrop = managedCourse.getActiveStudents().get(studentDisplayTable.getSelectedRow());
					managedCourse.removeStudent(studentToDrop);
					studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(managedCourse.getActiveStudents(), detailedView));
					ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
					ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
					kwikStatsTable.setModel(statsTableModel);
					FileManager.saveFile(MainDashboard.getKwikGrade().getActiveCourses(), MainDashboard.getActiveSaveFileName());
					FileManager.saveFile(MainDashboard.getKwikGrade().getClosedCourses(), MainDashboard.getClosedSaveFileName());
				}
			}
		});
		dropStudentButton.setBounds(565, 85, 155, 40);
		contentPanel.add(dropStudentButton);
		
		JButton manageStudentButton = new JButton("Manage Student");
		manageStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//checks to see if you actually selected anything from the table
				if(studentDisplayTable.getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "No student selected!");
					return;
				}
				else {
					Student student = getManagedCourse().getActiveStudents().get(studentDisplayTable.getSelectedRow());
					ManageStudentFrame manageStudentFrame = new ManageStudentFrame(student, getManagedCourse());
					manageStudentFrame.setModal(true);
					manageStudentFrame.setVisible(true);

					studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(getManagedCourse().getActiveStudents(), detailedView));
					ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
					ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
					kwikStatsTable.setModel(statsTableModel);
				}

			}
		});
		manageStudentButton.setBounds(565, 136, 155, 40);
		contentPanel.add(manageStudentButton);
		
		JButton inactiveStudentsButton = new JButton("Inactive Students");
		inactiveStudentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewDroppedStudents viewDroppedStudents = new ViewDroppedStudents(getManagedCourse().getInactiveStudents());
				viewDroppedStudents.setModal(true);
				viewDroppedStudents.setVisible(true);
			}
		});
		inactiveStudentsButton.setBounds(565, 187, 155, 40);
		contentPanel.add(inactiveStudentsButton);
		
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

				studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(getManagedCourse().getActiveStudents(), detailedView));
				ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
				ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
			}
		});

		addGradeButton.setBounds(565, 238, 155, 40);
		contentPanel.add(addGradeButton);
		
		JButton manageCategoryButton = new JButton("Manage Categories");
		manageCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageCategoriesFrame manageCategoriesFrame = new ManageCategoriesFrame(getManagedCourse());
				manageCategoriesFrame.setModal(true);
				manageCategoriesFrame.setVisible(true);

				studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(getManagedCourse().getActiveStudents(), detailedView));
				ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
				ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
			}
		});


		JButton addCurveButton = new JButton("Add Curve");
		addCurveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCurveFrame addCurveFrame = new AddCurveFrame(getManagedCourse());
				addCurveFrame.setModal(true);
				addCurveFrame.setVisible(true);

				studentDisplayTable.setModel(ModelGenerators.generateStudentTableModel(getManagedCourse().getActiveStudents(), detailedView));
				ModelGenerators.setTableSizing(studentDisplayTable, detailedView);
				ModelGenerators.updateStatsModel(managedCourse, statsTableModel);
				kwikStatsTable.setModel(statsTableModel);
			}
		});
		addCurveButton.setBounds(567, 341, 155, 40);
		contentPanel.add(addCurveButton);


		manageCategoryButton.setBounds(565, 289, 155, 40);
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
		saveCloseButton.setBounds(567, 436, 155, 40);
		contentPanel.add(saveCloseButton);

	}
}
