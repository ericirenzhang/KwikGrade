package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import models.Course;
import models.OverallGrade;
import models.Student;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class AddGradeFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField assignNameText;
	private JTable studentGradeTable;
	private DefaultTableModel addGradeTableModel;
	private ArrayList<Student> studentList;
	private OverallGrade ugOverallGrade;
	private OverallGrade gradOverallGrade;
	private static Course managedCourse;

	public DefaultTableModel displayStudents(ArrayList<Student> Students) {
		addGradeTableModel = new DefaultTableModel();
		Object[] title = {"Name", "Points"};
		addGradeTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			addGradeTableModel.addRow(new Object[] {Students.get(i).getfName()+" "+Students.get(i).getMiddleInitial()+" "+Students.get(i).getlName()} );
		}
		return addGradeTableModel;
	}
	
	public void updateStudentTable() {
		studentGradeTable.setModel(displayStudents(managedCourse.getActiveStudents()));
	}

	/**
	 * Create the dialog.
	 */
	public AddGradeFrame(Course managedCourse) {
		this.managedCourse = managedCourse;
		setBounds(100, 100, 664, 434);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		assignNameText = new JTextField();
		assignNameText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		assignNameText.setColumns(10);
		
		JLabel assignNameLabel = new JLabel("Assignment Name");
		assignNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox catNameDropdown = new JComboBox();
		
		JLabel lblCategoryName = new JLabel("Category Name");
		lblCategoryName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel gradeSchemeLabel = new JLabel("Grade Scheme");
		gradeSchemeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JComboBox gradeSchemeDropdown = new JComboBox();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(gradeSchemeDropdown, 0, 45, Short.MAX_VALUE)
							.addGap(79))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(assignNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(catNameDropdown, 0, 118, Short.MAX_VALUE)
									.addComponent(assignNameText, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addComponent(gradeSchemeLabel, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
										.addGap(9)))
								.addGap(79))
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblCategoryName, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED))))
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
					.addGap(129))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(12)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(gradeSchemeLabel, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(gradeSchemeDropdown, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
							.addGap(60)
							.addComponent(lblCategoryName, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(catNameDropdown, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
							.addGap(37)
							.addComponent(assignNameLabel, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(assignNameText, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
							.addGap(57))
						.addGroup(Alignment.TRAILING, gl_contentPanel.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		
		studentGradeTable = new JTable();
		scrollPane.setViewportView(studentGradeTable);
		contentPanel.setLayout(gl_contentPanel);
		updateStudentTable();

		
		
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
}
