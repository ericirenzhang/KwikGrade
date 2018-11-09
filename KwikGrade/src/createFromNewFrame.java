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

public class createFromNewFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField course_num_txt;
	private JTextField course_term_txt;
	private JTextField course_ttl_txt;
	private String course_number;
	private String course_term;
	private String course_title;
	
	public String get_num() {
		return course_number;
	}
	public String get_term() {
		return course_term;
	}
	public String get_title() {
		return course_title;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			createFromNewFrame dialog = new createFromNewFrame();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public createFromNewFrame() {
		setBounds(100, 100, 491, 413);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		course_num_txt = new JTextField();
		course_num_txt.setBounds(164, 29, 297, 36);
		contentPanel.add(course_num_txt);
		course_num_txt.setColumns(10);
		
		course_term_txt = new JTextField();
		course_term_txt.setColumns(10);
		course_term_txt.setBounds(164, 78, 297, 36);
		contentPanel.add(course_term_txt);
		
		course_ttl_txt = new JTextField();
		course_ttl_txt.setColumns(10);
		course_ttl_txt.setBounds(164, 127, 297, 36);
		contentPanel.add(course_ttl_txt);
		
		JLabel coursenum_lbl = new JLabel("Course Number");
		coursenum_lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		coursenum_lbl.setHorizontalAlignment(SwingConstants.TRAILING);
		coursenum_lbl.setBounds(12, 33, 140, 26);
		contentPanel.add(coursenum_lbl);
		
		JLabel courseterm_lbl = new JLabel("Course Term");
		courseterm_lbl.setHorizontalAlignment(SwingConstants.TRAILING);
		courseterm_lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		courseterm_lbl.setBounds(12, 82, 140, 26);
		contentPanel.add(courseterm_lbl);
		
		JLabel coursetitle_lbl = new JLabel("Course Title");
		coursetitle_lbl.setHorizontalAlignment(SwingConstants.TRAILING);
		coursetitle_lbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		coursetitle_lbl.setBounds(12, 131, 140, 26);
		contentPanel.add(coursetitle_lbl);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						course_number = course_num_txt.getText();
						course_term = course_term_txt.getText();
						course_title = course_ttl_txt.getText();
						get_title();
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
}
