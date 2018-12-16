package views.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import helpers.KwikGradeUIManager;
import models.Student;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewDroppedStudentsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable inactiveStudentTable;

	/**
	 * Create the Dialog to view dropped students.
	 *
	 * @param studentList
	 */
	public ViewDroppedStudentsDialog(ArrayList<Student> studentList) {
		KwikGradeUIManager.setUpUI(this, contentPanel, 451, 482);

		// Set up table to view dropped students
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		setTitle("Inactive Students");
		{
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			contentPanel.add(scrollPane, gbc_scrollPane);
			{
				inactiveStudentTable = new JTable();
				scrollPane.setViewportView(inactiveStudentTable);
			}
		}
		
		inactiveStudentTable.setModel(generateStudentTableModel(studentList));

		// Set close button listener
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton closeButton = new JButton("Close");
				closeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				closeButton.setActionCommand("Close");
				buttonPane.add(closeButton);
				getRootPane().setDefaultButton(closeButton);
			}
		}
	}


	public DefaultTableModel generateStudentTableModel(ArrayList<Student> Students) {
		DefaultTableModel studentTableModel;
		studentTableModel = new DefaultTableModel();
		Object[] title = {"First Name", "Middle Initial", "Last Name", "Grade"};
		studentTableModel.setColumnIdentifiers(title);
		for(int i = 0; i < Students.size(); i++) {
			studentTableModel.addRow(new Object[] {Students.get(i).getfName(),Students.get(i).getMiddleInitial(), Students.get(i).getlName(), Students.get(i).getOverallGradeObject().getOverallGradeValue()} );
		}
		return studentTableModel;
	}
}
