package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateCourseFrame extends JDialog {
	
	private String courseNum;
	private String courseTerm;
	private String courseTitle;
	private String filePath;
	private boolean bulkAddStudents;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateCourseFrame dialog = new CreateCourseFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreateCourseFrame() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton createNewButton = new JButton("Create From New");
			createNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CreateFromNewFrame createFromNew = new CreateFromNewFrame();
					createFromNew.setModal(true);
					createFromNew.setVisible(true);

					courseNum = createFromNew.getCourseNum();
					courseTerm = createFromNew.getCourseTerm();
					courseTitle = createFromNew.getCourseTitle();
					bulkAddStudents = createFromNew.getBulkAddStudents();
					filePath = createFromNew.getFilePath();
					
					dispose();
				}
			});
			createNewButton.setBounds(10, 69, 183, 181);
			contentPanel.add(createNewButton);
		}
		{
			JButton createExistingButton = new JButton("Create From Existing");
			createExistingButton.setBounds(241, 69, 183, 181);
			contentPanel.add(createExistingButton);
		}
		{
			JLabel createLabel = new JLabel("How would you like to create your course?");
			createLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			createLabel.setBounds(66, 25, 302, 20);
			contentPanel.add(createLabel);
		}
	}
	
	//==========================
	// Getters
	//==========================
	
	public String getCourseNum() {
		return this.courseNum;
	}
	
	public String getCourseTerm() {
		return this.courseTerm;
	}
 	public String getCourseTitle() {
		return this.courseTitle;
	}
 	public boolean getBulkAddStudents() {
 		return this.bulkAddStudents;
 	}
 	public String getFilePath() {
 		return this.filePath;
 	}
	
	//==========================
	// Setters
	//==========================
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}
	public void setCourseTerm(String courseTerm) {
		this.courseTerm = courseTerm;
	}
 	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
 	public void setBulkAddSTudents(boolean bulkAddStudents) {
 		this.bulkAddStudents = bulkAddStudents;
 	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}