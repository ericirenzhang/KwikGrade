import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateFromNewFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField courseNumText;
	private JTextField courseTermText;
	private JTextField courseTitleText;
	private String courseNum;
	private String courseTerm;
	private String courseTitle;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateFromNewFrame dialog = new CreateFromNewFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreateFromNewFrame() {
		setBounds(100, 100, 491, 413);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		courseNumText = new JTextField();
		courseNumText.setBounds(164, 29, 297, 36);
		contentPanel.add(courseNumText);
		courseNumText.setColumns(10);
		
		courseTermText = new JTextField();
		courseTermText.setColumns(10);
		courseTermText.setBounds(164, 78, 297, 36);
		contentPanel.add(courseTermText);
		
		courseTitleText = new JTextField();
		courseTitleText.setColumns(10);
		courseTitleText.setBounds(164, 127, 297, 36);
		contentPanel.add(courseTitleText);
		
		JLabel courseNumberLabel = new JLabel("Course Number");
		courseNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseNumberLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseNumberLabel.setBounds(12, 33, 140, 26);
		contentPanel.add(courseNumberLabel);
		
		JLabel courseterm_lbl = new JLabel("Course Term");
		courseterm_lbl.setHorizontalAlignment(SwingConstants.TRAILING);
		courseterm_lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseterm_lbl.setBounds(12, 82, 140, 26);
		contentPanel.add(courseterm_lbl);
		
		JLabel courseTitleLabel = new JLabel("Course Title");
		courseTitleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		courseTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseTitleLabel.setBounds(12, 131, 140, 26);
		contentPanel.add(courseTitleLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						courseNum = courseNumText.getText();
						courseTerm = courseTermText.getText();
						courseTitle = courseTitleText.getText();
						dispose();
						
					}
				});
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
}
