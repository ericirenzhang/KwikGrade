package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import models.Course;
import models.CourseCategory;
import models.OverallGrade;
import models.Student;
import models.SubCategory;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class AddStudentFrame extends JDialog {
	
	int n;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldFname;
	private JTextField textFieldLname;
	private JTextField textFieldID;
	private JTextField textFieldEmail;
	private JTextField textFieldStatus;
	private JLabel lblLastName;
	private JLabel lblBuId;
	private JLabel lblEmail;
	private JLabel lblStatus;
	private JTextField textFieldMiddle;
	private JLabel lblMiddleInitial;
	private JButton btnSave;
	private JButton btnBack;
	private JButton btnLoadGradingScheme;
	private Student newStudent = new Student("","","","","");
	
	Course c = new Course("CS591",  "F18", "Java Intro", new OverallGrade(), new OverallGrade());
	SubCategory subCat1, subCat2;
	ArrayList<SubCategory> subCatList1 = new ArrayList<SubCategory>();
	ArrayList<SubCategory> subCatList2 = new ArrayList<SubCategory>();
	CourseCategory courseCat1, courseCat2;
	ArrayList<CourseCategory> courseCatList = new ArrayList<CourseCategory>();
	OverallGrade og;
	
	static JPanel panel = new JPanel();
	static JPanel panel1 = new JPanel();
    static Integer indexer = 1;
    static List<JLabel> listOfLabels = new ArrayList<JLabel>();
    static List<JTextField> listOfTextFields = new ArrayList<JTextField>();
    private JPanel panel_2;
    private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddStudentFrame dialog = new AddStudentFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddStudentFrame() {

		//*** Dummy data with 2 categories. One with 2 sub categories and the other with no sub category (handled as having one sub category) ***
		
		n = 1; //To keep track of number of rows to add in the grading scheme panel
		
		subCat1 = new SubCategory("hw1", 0.4, 0.0, 40.0, 50.0);
		subCat2 = new SubCategory("hw2", 0.6, 0.0, 30.0, 50.0);
		subCatList1.add(subCat1);
		subCatList1.add(subCat2);
		courseCat1 = new CourseCategory("HW", 0.3, subCatList1);
		
		subCat1 = new SubCategory("final1", 0.7, 0.0, 80.0, 100.0); //In future, have to handle Categories with no sub categories
		subCatList2.add(subCat1);
		courseCat2 = new CourseCategory("Final", 0.7, subCatList2);
		courseCatList.add(courseCat1);
		courseCatList.add(courseCat2);
		og  = new OverallGrade(95.0, 2, courseCatList);
		n += courseCatList.size();
		n += subCatList1.size();
		n += subCatList2.size();
		
		setBounds(100, 100, 813, 559);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//setLayout(new GridBagLayout());
		
		GridBagConstraints frameConstraints = new GridBagConstraints();
		
		{
			textFieldFname = new JTextField();
			textFieldFname.setBounds(53, 60, 130, 26);
			contentPanel.add(textFieldFname);
			textFieldFname.setColumns(10);
		}
		{
			textFieldLname = new JTextField();
			textFieldLname.setBounds(382, 60, 130, 26);
			textFieldLname.setColumns(10);
			contentPanel.add(textFieldLname);
		}
		{
			textFieldID = new JTextField();
			textFieldID.setBounds(53, 124, 130, 26);
			textFieldID.setColumns(10);
			contentPanel.add(textFieldID);
		}
		{
			textFieldEmail = new JTextField();
			textFieldEmail.setBounds(217, 124, 130, 26);
			textFieldEmail.setColumns(10);
			contentPanel.add(textFieldEmail);
		}
		{
			textFieldStatus = new JTextField();
			textFieldStatus.setBounds(382, 124, 130, 26);
			textFieldStatus.setColumns(10);
			contentPanel.add(textFieldStatus);
		}
		{
			JLabel lblFirstName = new JLabel("First Name");
			lblFirstName.setBounds(53, 45, 98, 16);
			contentPanel.add(lblFirstName);
		}
		{
			lblLastName = new JLabel("Last Name");
			lblLastName.setBounds(382, 45, 81, 16);
			contentPanel.add(lblLastName);
		}
		{
			lblBuId = new JLabel("BU ID");
			lblBuId.setBounds(53, 109, 61, 16);
			contentPanel.add(lblBuId);
		}
		{
			lblEmail = new JLabel("Email");
			lblEmail.setBounds(217, 109, 61, 16);
			contentPanel.add(lblEmail);
		}
		{
			lblStatus = new JLabel("Status");
			lblStatus.setBounds(382, 109, 61, 16);
			contentPanel.add(lblStatus);
		}
		{
			textFieldMiddle = new JTextField();
			textFieldMiddle.setBounds(217, 60, 130, 26);
			textFieldMiddle.setColumns(10);
			contentPanel.add(textFieldMiddle);
		}
		{
			lblMiddleInitial = new JLabel("Middle Initial");
			lblMiddleInitial.setBounds(217, 45, 98, 16);
			contentPanel.add(lblMiddleInitial);
		}
		{
			btnSave = new JButton("Save");
			btnSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Student newStudent;
					if(textFieldMiddle.getText() != null)
						newStudent = new Student(textFieldFname.getText(),textFieldMiddle.getText(),textFieldLname.getText(),textFieldID.getText(),textFieldEmail.getText());
					else
						newStudent = new Student(textFieldFname.getText(),"",textFieldLname.getText(),textFieldID.getText(),textFieldEmail.getText());
					
					c.setActiveStudents(newStudent);
					
				}
			});
			btnSave.setBounds(597, 85, 117, 29);
			contentPanel.add(btnSave);
		}
		{
			btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 //new MainDashboard().setVisible(true); //This actually has to go back to Course Management page 
					dispose();
				}
			});
			btnBack.setBounds(597, 124, 117, 29);
			contentPanel.add(btnBack);
		}
		{
			
			//===============
			btnLoadGradingScheme = new JButton("Load Grading Scheme");
			btnLoadGradingScheme.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					panel_2.removeAll();
					listOfTextFields.clear();
					listOfLabels.clear();
					
					// Create constraints
		            GridBagConstraints textFieldConstraints = new GridBagConstraints();
		            GridBagConstraints labelConstraints = new GridBagConstraints();
		            	ArrayList<ArrayList<SubCategory>> listOfLists = new ArrayList<ArrayList<SubCategory>>();
		            	listOfLists.add(subCatList1);
		            	listOfLists.add(subCatList2);
		            	ArrayList<String> inputText = new ArrayList<String>();
		            	inputText.add("FinalGrade");
		            	//for each CourseCategory
		            	//add each subCategory name
		            	for(int i=0;i<courseCatList.size();i++) {
		            		inputText.add(courseCatList.get(i).getName());
		            		for(int j=0;j<listOfLists.get(i).size();j++) {
		            			inputText.add(listOfLists.get(i).get(j).getName());
		            		}
		            	}
		            	
		            	// Create label and text field
						for(int i=0;i<n;i++) {
			            JTextField jTextField = new JTextField();
			            jTextField.setSize(100, 200);
			            listOfTextFields.add(jTextField);
			            listOfLabels.add(new JLabel(inputText.get(i)));
			            
			            // Text field constraints
		                textFieldConstraints.gridx = 1;
		                textFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
		                textFieldConstraints.weightx = 0.5;
		                textFieldConstraints.insets = new Insets(10, 10, 10, 10);
		                textFieldConstraints.gridy = i;

		                // Label constraints
		                labelConstraints.gridx = 0;
		                labelConstraints.gridy = i;
		                labelConstraints.insets = new Insets(10, 10, 10, 10);

		                // Add them to panel
		                panel_2.add(listOfLabels.get(i), labelConstraints);
		                panel_2.add(listOfTextFields.get(i), textFieldConstraints);
						}

						// Align components top-to-bottom
		                /*GridBagConstraints c = new GridBagConstraints();
		                c.gridx = 0;
		                c.gridy = indexer;
		                c.weighty = 1;
		                panel_2.add(new JLabel(), c);*/
		                panel_2.updateUI();
			}});
			btnLoadGradingScheme.setBounds(573, 165, 169, 29);
			contentPanel.add(btnLoadGradingScheme);
			//===============
			
	        // Add panel to frame
	        frameConstraints.gridx = 0;
	        frameConstraints.gridy = 1;
	        frameConstraints.weighty = 1;
			{
				scrollPane = new JScrollPane();
				scrollPane.setBounds(60, 209, 654, 253);
				contentPanel.add(scrollPane);
				panel_2 = new JPanel();
				scrollPane.setViewportView(panel_2);
				panel_2.setPreferredSize(new Dimension(300, 300));
				panel_2.setLayout(new GridBagLayout());
				panel_2.setBorder(LineBorder.createBlackLineBorder());
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	//==========================
	// Getters
	//==========================
	public Student getNewSudent() {
		return this.newStudent;
	}
}
