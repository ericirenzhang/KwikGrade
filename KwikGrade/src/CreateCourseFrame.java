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
			JButton btnNewButton = new JButton("Create From New");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CreateFromNewFrame createFromNew = new CreateFromNewFrame();
					createFromNew.setModal(true);
					createFromNew.setVisible(true);
					
					courseNum = createFromNew.getCourseNum();
					courseTerm = createFromNew.getCourseTerm();
					courseTitle = createFromNew.getCourseTitle();
					
					dispose();
				}
			});
			btnNewButton.setBounds(10, 69, 183, 181);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnCreateFromExisting = new JButton("Create From Existing");
			btnCreateFromExisting.setBounds(241, 69, 183, 181);
			contentPanel.add(btnCreateFromExisting);
		}
		{
			JLabel lblNewLabel = new JLabel("How would you like to create your course?");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNewLabel.setBounds(66, 25, 302, 20);
			contentPanel.add(lblNewLabel);
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
	
	

}