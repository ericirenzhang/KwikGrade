package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.awt.event.ActionEvent;

public class ChangeCredsFrame extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField newPasswordField;
	private JPasswordField retypePasswordField;
	private JTextField newUsernameField;

	/**
	 * Create the dialog.
	 */
	public ChangeCredsFrame() {
		setBounds(100, 100, 600, 300);
		setLocationRelativeTo ( null );
		contentPanel.setLayout(null);

		// TODO: Update this with GridBagLayout so this scales upon resizing.
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel newUsernameLabel = new JLabel("New Username");
		newUsernameLabel.setBounds(67, 74, 95, 14);
		contentPanel.add(newUsernameLabel);
		newUsernameField = new JTextField();
		newUsernameField.setBounds(172, 71, 133, 20);
		contentPanel.add(newUsernameField);
		newUsernameField.setColumns(10);

		JLabel newPasswordLabel = new JLabel("New Password");
		newPasswordLabel.setBounds(67, 108, 95, 14);
		contentPanel.add(newPasswordLabel);
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(172, 105, 133, 20);
		contentPanel.add(newPasswordField);

		JLabel retypePasswordLabel = new JLabel("Re-type Password");
		retypePasswordLabel.setBounds(67, 139, 105, 14);
		contentPanel.add(retypePasswordLabel);
		retypePasswordField = new JPasswordField();
		retypePasswordField.setBounds(172, 136, 133, 20);
		contentPanel.add(retypePasswordField);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newPasswordField.getText().equals(retypePasswordField.getText())) { //checks if passwords match
					JOptionPane.showMessageDialog(null, "Username and Password Set Successfully!");
					try {
						FileWriter changedCreds = new FileWriter("logincredentials.txt", false);
						changedCreds.write(newUsernameField.getText()+","+ newPasswordField.getText());
						changedCreds.close();
					}
					catch(Exception e1) {
						JOptionPane.showMessageDialog(null, "Could not find \"logincredentials.txt\", please create one.");
					}
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Make sure your passwords match!");
				}
			}
		});

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}