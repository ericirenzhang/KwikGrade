package views.dialogs;

import models.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import helpers.StudentTextImport;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CreateFromNewDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField courseNumField;
	private JTextField courseTermField;
	private JTextField courseTitleField;
	private JTextField studentFilepathField;
	private String courseNum;
	private String courseTerm;
	private String courseTitle;
	private String filePath;

	private boolean hasCreatedNewCourse = false;

	private ArrayList<Student> importedStudentList = new ArrayList<>();

	//attributes to store the Course Category lists
	private JTable ugCourseCategoryTable;
	private int ugTableModelRows = 1;
	private DefaultTableModel ugTableModel = new DefaultTableModel(ugTableModelRows, 2);
	private JTable gradCourseCategoryTable;
	private int gradTableModelRows = 1;
	private DefaultTableModel gradTableModel = new DefaultTableModel(gradTableModelRows, 2);
	
	// initializes the overallgrade objects for undergrads and graduates
	private OverallGrade ugOverallGrade = new OverallGrade();
	private OverallGrade gradOverallGrade = new OverallGrade();

	/**
	 * Create the dialogs.
	 */
	public CreateFromNewDialog() {
		setBounds(100, 100, 585, 687);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// Top half of JFrame for Course information section.
		courseNumField = new JTextField();
		courseNumField.setBounds(250, 29, 300, 36);
		contentPanel.add(courseNumField);
		courseNumField.setColumns(10);

		courseTermField = new JTextField();
		courseTermField.setColumns(10);
		courseTermField.setBounds(250, 78, 300, 36);
		contentPanel.add(courseTermField);

		courseTitleField = new JTextField();
		courseTitleField.setColumns(10);
		courseTitleField.setBounds(250, 127, 300, 36);
		contentPanel.add(courseTitleField);

		studentFilepathField = new JTextField();
		studentFilepathField.setColumns(10);
		studentFilepathField.setBounds(152, 280, 405, 36);
		studentFilepathField.setEditable(false);
		contentPanel.add(studentFilepathField);

		// Labels
		JLabel courseNumberLabel = new JLabel("Course Number (required)");
		courseNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseNumberLabel.setBounds(12, 33, 200, 26);
		contentPanel.add(courseNumberLabel);

		JLabel courseTermLabel = new JLabel("Course Term (required)");
		courseTermLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTermLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTermLabel.setBounds(12, 82, 200, 26);
		contentPanel.add(courseTermLabel);

		JLabel courseTitleLabel = new JLabel("Course Title (required)");
		courseTitleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTitleLabel.setBounds(12, 131, 200, 26);
		contentPanel.add(courseTitleLabel);

		// Display JLabel to across multiple lines using HTML.
		JLabel importNowLabel = new JLabel("<html>Add students by importing now.<br/>(Or add them manually later)</html>");
		importNowLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		importNowLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		importNowLabel.setBounds(0, 200, 500, 36);
		contentPanel.add(importNowLabel);

		JLabel filePathLabel = new JLabel("Filepath");
		filePathLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		filePathLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		filePathLabel.setBounds(12, 280, 89, 36);
		contentPanel.add(filePathLabel);

		JLabel ugCourseCategoryLabel = new JLabel("Undergraduate Grading Scheme");
		ugCourseCategoryLabel.setBounds(60, 344, 200, 14);
		contentPanel.add(ugCourseCategoryLabel);

		JLabel gradCourseCategoryLabel = new JLabel("Graduate Grading Scheme");
		gradCourseCategoryLabel.setBounds(361, 344, 158, 14);
		contentPanel.add(gradCourseCategoryLabel);

		// Bottom half of JFrame for File browsing/importing Students.
		contentPanel.add(new JSeparator());
		JButton browseButton = new JButton("Browse File Path of Student Text File...");
		browseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		browseButton.setBounds(152, 250, 405, 36);
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				// For File
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setAcceptAllFileFilterUsed(false);

				int userChoice = fileChooser.showOpenDialog(null);
				if (userChoice == JFileChooser.APPROVE_OPTION) {
					String studentTextFilePath = fileChooser.getSelectedFile().toString();
					studentFilepathField.setText(studentTextFilePath);
				}
			}
		});
		contentPanel.add(browseButton);
		
		JScrollPane underGraduateScrollPane = new JScrollPane();
		underGraduateScrollPane.setBounds(12, 362, 249, 180);
		contentPanel.add(underGraduateScrollPane);

		ugCourseCategoryTable = new JTable();
		ugCourseCategoryTable.setGridColor(Color.BLACK);
		ugCourseCategoryTable.setRowHeight(25);
		underGraduateScrollPane.setViewportView(ugCourseCategoryTable);
		addTableRow(ugTableModel, ugCourseCategoryTable);
		
		JScrollPane graduateScrollPane = new JScrollPane();
		graduateScrollPane.setBounds(304, 361, 246, 181);
		contentPanel.add(graduateScrollPane);
		
		gradCourseCategoryTable = new JTable();
		gradCourseCategoryTable.setGridColor(Color.BLACK);
		gradCourseCategoryTable.setRowHeight(25);
		graduateScrollPane.setViewportView(gradCourseCategoryTable);
		addTableRow(gradTableModel, gradCourseCategoryTable);

		// Set up Category table columns
		Object[] gradeSchemeTableTitle = {"Category Name", "Weight in % (i.e. 50)"};
		ugTableModel.setColumnIdentifiers(gradeSchemeTableTitle);
		ugCourseCategoryTable.setModel(ugTableModel);
		gradTableModel.setColumnIdentifiers(gradeSchemeTableTitle);
		gradCourseCategoryTable.setModel(gradTableModel);
		
		// Adds a row to the dynamic undergraduate table
		JButton ugAddRowButton = new JButton("Add Category");
		ugAddRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ugTableModelRows = ugTableModelRows + 1;
				if(null != ugCourseCategoryTable.getCellEditor()) {
					ugCourseCategoryTable.getCellEditor().stopCellEditing();
				}
				addTableRow(ugTableModel, ugCourseCategoryTable);
			}
		});
		ugAddRowButton.setBounds(12, 568, 115, 23);
		contentPanel.add(ugAddRowButton);
		
		// Removes a row from the Dynamic UnderGraduate Table
		JButton ugSubRowButton = new JButton("Remove Category");
		ugSubRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ugTableModelRows > 1) {
					ugTableModelRows = ugTableModelRows - 1;
				}
				else {
					ugTableModelRows = ugTableModelRows;
				}
				if(null != ugCourseCategoryTable.getCellEditor()) {
					ugCourseCategoryTable.getCellEditor().stopCellEditing();
				}
				removeTableRow( ugTableModel, ugCourseCategoryTable);
			}
		});
		ugSubRowButton.setBounds(140, 568, 121, 23);
		contentPanel.add(ugSubRowButton);
		
		// Adds a row to the dynamic graduate table
		JButton gradAddRowButton = new JButton("Add Category");
		gradAddRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradTableModelRows = gradTableModelRows + 1;
				//gradTableModel.fireTableRowsInserted(gradTableModelRows-1, gradTableModelRows-1);
				if(null != gradCourseCategoryTable.getCellEditor()) {
					gradCourseCategoryTable.getCellEditor().stopCellEditing();
				}
				addTableRow( gradTableModel, gradCourseCategoryTable);
			}
		});
		gradAddRowButton.setBounds(304, 568, 115, 23);
		contentPanel.add(gradAddRowButton);
		
		// Removes a row from the Dynamic Graduate Table
		JButton gradSubRowButton = new JButton("Remove Category");
		gradSubRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gradTableModelRows > 1) {
					gradTableModelRows = gradTableModelRows - 1;
				}
				else {
					gradTableModelRows = gradTableModelRows;
				}
				if(null != gradCourseCategoryTable.getCellEditor()) {
					gradCourseCategoryTable.getCellEditor().stopCellEditing();
				}
				removeTableRow( gradTableModel, gradCourseCategoryTable);
			}
		});
		gradSubRowButton.setBounds(429, 568, 121, 23);
		contentPanel.add(gradSubRowButton);
		
		JButton btnUseSameScheme = new JButton("Use Same Scheme For Both Categories");
		btnUseSameScheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradTableModel.setRowCount(1);
				for(int i = 1; i < ugCourseCategoryTable.getRowCount(); i++) {
					gradTableModel.addRow(new Object[] {"",""});
				}
				DefaultTableModel original = (DefaultTableModel) ugCourseCategoryTable.getModel();
				gradTableModel = (DefaultTableModel) gradCourseCategoryTable.getModel();
				selectRows(ugCourseCategoryTable, 0, ugCourseCategoryTable.getRowCount());
				int[] selectedRows = ugCourseCategoryTable.getSelectedRows();
				for (int targetRow = 0; targetRow < selectedRows.length; targetRow++) {
				    int row = selectedRows[targetRow];
				    int modelRow = ugCourseCategoryTable.convertRowIndexToModel(row);
				    for (int col = 0; col < original.getColumnCount(); col++) {
				    	gradTableModel.setValueAt(original.getValueAt(modelRow, col), targetRow, col);
				    }
				}
				gradTableModelRows = ugTableModelRows;
				gradCourseCategoryTable.setModel(gradTableModel);
			}
		});
		btnUseSameScheme.setBounds(150, 591, 286, 29);
		contentPanel.add(btnUseSameScheme);
		
		// Add OK and Cancel buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				courseNum = courseNumField.getText();
				courseTerm = courseTermField.getText();
				courseTitle = courseTitleField.getText();
				filePath = studentFilepathField.getText();
				
				//stops the cell edit listener
				if(null != ugCourseCategoryTable.getCellEditor()) {
					ugCourseCategoryTable.getCellEditor().stopCellEditing();
				}
				if(null != gradCourseCategoryTable.getCellEditor()) {
					gradCourseCategoryTable.getCellEditor().stopCellEditing();
				}

				//checks for if required fields are blank
				if(courseNum.equals("") || courseTerm.equals("") || courseTitle.equals("")) {
					JOptionPane.showMessageDialog(null, "Must enter in required information!");
					return;
				}
				
				//check to make sure weights for table add up to 100
 				if (checkAddToHundred(ugCourseCategoryTable) == false || checkAddToHundred(gradCourseCategoryTable) == false) {
 					return;
 				}
 
 				//checks to make sure that all the values for a percentage are actual numbers
 				if (checkForDouble(ugCourseCategoryTable) == false || checkForDouble(gradCourseCategoryTable) == false) {
 					return;
 				}
 				
				//Parses through and grabs the name and weight
				for(int ugIndex = 0; ugIndex < ugTableModelRows; ugIndex++) {
					try {
					String ugCategoryName = ugCourseCategoryTable.getValueAt(ugIndex, 0).toString();
					double ugCategoryWeight = (Double.parseDouble(String.valueOf(ugCourseCategoryTable.getValueAt(ugIndex, 1)))/100);
					CourseCategory categoryToAdd = new CourseCategory(ugCategoryName, ugCategoryWeight);
					ugOverallGrade.addCourseCategory(categoryToAdd);
					}
					catch (Exception ugOverallGradeCreate) {
						JOptionPane.showMessageDialog(null, "Make sure all Grading Scheme values are filled properly!");
						return;
					}
				}
				
				//Parses through and grabs the name and weight 
				for(int gradIndex = 0; gradIndex < gradTableModelRows; gradIndex++) {
					try {
					String gradCategoryName = gradCourseCategoryTable.getValueAt(gradIndex, 0).toString();
					double gradCategoryWeight = (Double.parseDouble(String.valueOf(gradCourseCategoryTable.getValueAt(gradIndex, 1)))/100);
					CourseCategory categoryToAdd = new CourseCategory(gradCategoryName, gradCategoryWeight);
					gradOverallGrade.addCourseCategory(categoryToAdd);
					}
					catch (Exception gradOverallGradeCreate) {
						JOptionPane.showMessageDialog(null, "Make sure all Grading Scheme values are filled properly!");
						return;
					}
				}
				
				if(!filePath.equals("")) {
					importedStudentList = StudentTextImport.addImportedStudents(filePath, ugOverallGrade, gradOverallGrade);
				}

				hasCreatedNewCourse = true;

				dispose();
			}
		});
		buttonPane.add(okButton);

		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(cancelButton);
	}
	
	/**
	 * Dynamically adds rows to the Course Category Table
	 * Kept generic to allow for both UG and Grad to use
	 * @param tableModel, table
	 */
	public void addTableRow(DefaultTableModel tableModel, JTable table) {
			tableModel = (DefaultTableModel) table.getModel();
			tableModel.addRow(new Object[] {"",""});
			table.setModel(tableModel);
	}
	
	/**
	 * Dynamically removes rows to the Course Category Table
	 * Kept generic to allow for both UG and Grad to use
	 * @param tableModel, table
	 */	
	public void removeTableRow(DefaultTableModel tableModel, JTable table) {
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.removeRow(tableModel.getRowCount()-1);
		table.setModel(tableModel);
	}
	
	//method to select all rows in the Undergrad table
	private void selectRows(JTable table, int start, int end) {
		if(null != table.getCellEditor()) {
			table.getCellEditor().stopCellEditing();
		}
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setRowSelectionInterval(start, end - 1);
    }
	
	/**
 	 * Checks that all values where the user should have entered doubles are actually doubles
 	 * @param gradeSchemeTable
 	 * @return boolean
 	 */
 	public boolean checkForDouble(JTable gradeSchemeTable) {
 		for(int tableIndex = 0; tableIndex < gradeSchemeTable.getRowCount(); tableIndex++) {
 			try {
 				double catWeight = (Double.parseDouble(String.valueOf(gradeSchemeTable.getValueAt(tableIndex, 1)))/100);
 			}
 			catch (Exception overallGradeCreate) {
 				JOptionPane.showMessageDialog(null, "Make sure all Grading Scheme values are filled properly!");
 				return false;
 			}
 		}
 		return true;
 	}
 	
	/**
 	 * Checks that all grade values add up to 100
 	 * @param gradeSchemeTable
 	 * @return boolean
 	 */
 	public boolean checkAddToHundred(JTable gradeSchemeTable) {
 		double catWeight = 0;
 		for(int tableIndex = 0; tableIndex < gradeSchemeTable.getRowCount(); tableIndex++) {
 			catWeight = catWeight + (Double.parseDouble(String.valueOf(gradeSchemeTable.getValueAt(tableIndex, 1))));
 		}
 		if (catWeight == 100) {
 			return true;
 		}
 		else {
 			JOptionPane.showMessageDialog(null, "Make sure your category weights add up to 100!");
 			return false;
 		}
 	}

	//=============================
	// Getters
	//=============================

	public String getCourseNum() {
		return courseNum;
	}
	public String getCourseTerm() {
		return courseTerm;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public ArrayList<Student> getImportedStudentsList() {
		return this.importedStudentList;
	}
	public String getFilePath() {
		return filePath;
	}
	public OverallGrade getUGOverallGrade() {
		return ugOverallGrade;
	}
	public OverallGrade getGradOverallGrade() {
		return gradOverallGrade;
	}
	public boolean getHasCreatedNewCourse() {
		return this.hasCreatedNewCourse;
	}

	//=============================
	// Setters
	//=============================

	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void setUGOverallGrade(OverallGrade ugOverallGrade) {
		this.ugOverallGrade = ugOverallGrade;
	}
	public void setGradOverallGrade(OverallGrade gradOverallGrade) {
		this.gradOverallGrade = gradOverallGrade;
	}
}