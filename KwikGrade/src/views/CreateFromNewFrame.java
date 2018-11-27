package views;

import models.GraduateStudent;
import models.OverallGrade;
import models.Student;
import models.UndergraduateStudent;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CreateFromNewFrame extends JDialog {

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
	private JPanel panel1;
	
	//attributes to store the Course Category lists
	private JTable ugCourseCategoryTable;
	private int ugTableModelRows = 2;
	private DefaultTableModel ugTableModel = new DefaultTableModel();
	private JTable gradCourseCategoryTable;
	private int gradTableModelRows = 2;
	private DefaultTableModel gradTableModel = new DefaultTableModel();
	
	//initializes the overallgrade objects for undergrads and graduates
	private OverallGrade ugOverallGrade = new OverallGrade();
	private OverallGrade gradOverallGrade = new OverallGrade();

	/**
	 * Create the dialog.
	 */
	public CreateFromNewFrame() {

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

		// Hacky fix to get JLabel to go across multiple lines using HTML.
		JLabel importNowLabel = new JLabel("<html>Add students by importing now.<br/>(Or add them manually later)</html>");
		importNowLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		importNowLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		importNowLabel.setBounds(0, 200, 500, 36);
		contentPanel.add(importNowLabel);

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

		studentFilepathField = new JTextField();
		studentFilepathField.setColumns(10);
		studentFilepathField.setBounds(152, 280, 405, 36);
		studentFilepathField.setEditable(false);
		contentPanel.add(studentFilepathField);

		JLabel filePathLabel = new JLabel("Filepath");
		filePathLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		filePathLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		filePathLabel.setBounds(12, 280, 89, 36);
		contentPanel.add(filePathLabel);
		
		JScrollPane underGraduateScrollPane = new JScrollPane();
		underGraduateScrollPane.setBounds(12, 362, 249, 180);
		contentPanel.add(underGraduateScrollPane);
		
		ugCourseCategoryTable = new JTable();
		ugCourseCategoryTable.setGridColor(Color.BLACK);
		ugCourseCategoryTable.setRowHeight(25);
		underGraduateScrollPane.setViewportView(ugCourseCategoryTable);
		dispCourseCategoryTable( ugTableModel, ugCourseCategoryTable, ugTableModelRows);
		
		JScrollPane graduateScrollPane = new JScrollPane();
		graduateScrollPane.setBounds(304, 361, 246, 181);
		contentPanel.add(graduateScrollPane);
		
		gradCourseCategoryTable = new JTable();
		gradCourseCategoryTable.setGridColor(Color.BLACK);

		gradCourseCategoryTable.setRowHeight(25);
		graduateScrollPane.setViewportView(gradCourseCategoryTable);
		gradCourseCategoryTable.setModel(gradTableModel);
		dispCourseCategoryTable( gradTableModel, gradCourseCategoryTable, gradTableModelRows);

		
		JLabel ugCourseCategoryLabel = new JLabel("Undergraduate Grading Scheme");
		ugCourseCategoryLabel.setBounds(60, 344, 200, 14);
		contentPanel.add(ugCourseCategoryLabel);
		
		JLabel gradCourseCategoryLabel = new JLabel("Graduate Grading Scheme");
		gradCourseCategoryLabel.setBounds(361, 344, 158, 14);
		contentPanel.add(gradCourseCategoryLabel);
		
		//Adds a row to the dynamic undergraduate table
		//TODO: Think of a way so that the values are saved if the table changes
		JButton ugAddRowButton = new JButton("Add Row");
		ugAddRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ugTableModelRows = ugTableModelRows + 1;
				dispCourseCategoryTable( ugTableModel, ugCourseCategoryTable, ugTableModelRows);
				
			}
		});
		ugAddRowButton.setBounds(12, 568, 115, 23);
		contentPanel.add(ugAddRowButton);
		
		
		//Removes a row from the Dynamic UnderGraduate Table
		//TODO: Think of a way so that the values are saved if the table changes
		JButton ugSubRowButton = new JButton("Subtract Row");
		ugSubRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ugTableModelRows > 1) {
					ugTableModelRows = ugTableModelRows - 1;
				}
				else {
					ugTableModelRows = ugTableModelRows;
				}
				dispCourseCategoryTable( ugTableModel, ugCourseCategoryTable, ugTableModelRows);
				
			}
		});
		ugSubRowButton.setBounds(140, 568, 121, 23);
		contentPanel.add(ugSubRowButton);
		
		//Adds a row to the dynamic graduate table
		//TODO: Think of a way so that the values are saved if the table changes
		JButton gradAddRowButton = new JButton("Add Row");
		gradAddRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gradTableModelRows = gradTableModelRows + 1;
				dispCourseCategoryTable( gradTableModel, gradCourseCategoryTable, gradTableModelRows);
			}
		});
		gradAddRowButton.setBounds(304, 568, 115, 23);
		contentPanel.add(gradAddRowButton);
		
		//Removes a row from the Dynamic Graduate Table
		//TODO: Think of a way so that the values are saved if the table changes
		JButton gradSubRowButton = new JButton("Subtract Row");
		gradSubRowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(gradTableModelRows > 1) {
					gradTableModelRows = gradTableModelRows - 1;
				}
				else {
					gradTableModelRows = gradTableModelRows;
				}
				dispCourseCategoryTable( gradTableModel, gradCourseCategoryTable, gradTableModelRows);
			}
		});
		gradSubRowButton.setBounds(429, 568, 121, 23);
		contentPanel.add(gradSubRowButton);

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

				if(!filePath.equals("")) {
					addImportedStudents(filePath);
				}
				
				//Parses through and grabs the name and weight 
				for(int ugIndex = 0; ugIndex < ugTableModelRows; ugIndex++) {
					try {
					String ugCategoryName = ugCourseCategoryTable.getValueAt(ugIndex, 0).toString();
					double ugCategoryWeight = Double.parseDouble(String.valueOf(ugCourseCategoryTable.getValueAt(ugIndex, 1)));
					ugOverallGrade.addCourseCategory(ugCategoryName, ugCategoryWeight);
					}
					//tried to implement logic for a blank table...does not work...need to rethink
					//TODO: Rethink logic for catching blank cells in a table
					catch (Exception ugOverallGradeCreate) {
						JOptionPane.showMessageDialog(null, "Make sure all Undergraduate Grading Scheme tables are filled!");
						return;
					}
				}
				
				//Parses through and grabs the name and weight 
				for(int gradIndex = 0; gradIndex < gradTableModelRows; gradIndex++) {
					try {
					String gradCategoryName = gradCourseCategoryTable.getValueAt(gradIndex, 0).toString();
					double gradCategoryWeight = Double.parseDouble(String.valueOf(ugCourseCategoryTable.getValueAt(gradIndex, 1)));
					gradOverallGrade.addCourseCategory(gradCategoryName, gradCategoryWeight);
					}
					//tried to implement logic for a blank table...does not work...need to rethink
					//TODO: Rethink logic for catching blank cells in a table
					catch (Exception gradOverallGradeCreate) {
						JOptionPane.showMessageDialog(null, "Make sure all Graduate Grading Scheme tables are filled!");
						return;
					}
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
	 * Dynamiclly displays the Course Category Table
	 * Kept generic to allow for both UG and Grad to use
	 * @param tableModel, table, tableRows
	 */
	public void dispCourseCategoryTable(DefaultTableModel tableModel, JTable table, int tableRows) {
		tableModel = new DefaultTableModel(tableRows, 2);
		Object[] gradeSchemeTableTitle = {"Category", "Weight"};
		tableModel.setColumnIdentifiers(gradeSchemeTableTitle);
		table.setModel(tableModel);
	}

	/**
	 * Imports students by parsing a text file into this.importedStudentList.
	 * @param filePath
	 */
	public void addImportedStudents(String filePath) {
		Scanner rawStudentData;

		try {
			System.out.println("Loading Students");
			rawStudentData = new Scanner(new File(filePath));

			while(rawStudentData.hasNext()) {
				String line = rawStudentData.nextLine();
				List<String> splitLine = Arrays.asList(line.split(","));
				if(splitLine.size()==6) { //checks for middle initial, if there's middle initial, there will be 6 items in string
					String fName = splitLine.get(0);
					String middleInitial = splitLine.get(1);
					String lName = splitLine.get(2);
					String buId = splitLine.get(3);
					String email = splitLine.get(4);
					String standing = splitLine.get(5);
					if(standing.equals("Undergraduate")) {
						this.importedStudentList.add(new UndergraduateStudent(fName, middleInitial, lName, buId, email, "Undergraduate"));
					} else if (standing.equals("Graduate")) {
						this.importedStudentList.add(new GraduateStudent(fName, middleInitial, lName, buId, email, "Graduate"));
 					} else {
						this.importedStudentList.add(new Student(fName, middleInitial, lName, buId, email));
					}
				}
				else { //if no middle initial, then 5 items in string
					String fName = splitLine.get(0);
					String middleInitial = "";
					String lName = splitLine.get(1);
					String buId = splitLine.get(2);
					String email = splitLine.get(3);
					String standing = splitLine.get(4);
					if(standing.equals("Undergraduate")) {
						this.importedStudentList.add(new UndergraduateStudent(fName, middleInitial, lName, buId, email, "Undergraduate"));
					} else if (standing.equals("Graduate")) {
						this.importedStudentList.add(new GraduateStudent(fName, middleInitial, lName, buId, email, "Graduate"));
					} else {
						this.importedStudentList.add(new Student(fName, middleInitial, lName, buId, email));
					}
				}
			}

			rawStudentData.close();
			System.out.println("Student Import Complete!");
		}
		//will change this to prompt user for another file
		catch(Exception e) {
			System.out.println("COULD NOT FIND FILE!!!!");
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