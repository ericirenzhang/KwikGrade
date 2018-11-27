package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.awt.event.ActionEvent;

public class ChangeCreds extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField newPasswordText;
	private JPasswordField retypePasswordText;
	private JTextField newUsernameText;

	/**
	 * Create the dialog.
	 */
	public ChangeCreds() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		newPasswordText = new JPasswordField();
		newPasswordText.setBounds(172, 105, 133, 20);
		contentPanel.add(newPasswordText);
		
		retypePasswordText = new JPasswordField();
		retypePasswordText.setBounds(172, 136, 133, 20);
		contentPanel.add(retypePasswordText);
		
		newUsernameText = new JTextField();
		newUsernameText.setBounds(172, 71, 133, 20);
		contentPanel.add(newUsernameText);
		newUsernameText.setColumns(10);
		
		JLabel newUsernameLabel = new JLabel("New Username");
		newUsernameLabel.setBounds(67, 74, 95, 14);
		contentPanel.add(newUsernameLabel);
		
		JLabel newPasswordLabel = new JLabel("New Password");
		newPasswordLabel.setBounds(67, 108, 95, 14);
		contentPanel.add(newPasswordLabel);
		
		JLabel retypePasswordLabel = new JLabel("Re-type Password");
		retypePasswordLabel.setBounds(67, 139, 105, 14);
		contentPanel.add(retypePasswordLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(newPasswordText.getText().equals(retypePasswordText.getText())) { //checks if passwords match
							JOptionPane.showMessageDialog(null, "Username and Password Set Successfully!");
							try {
								FileWriter changedCreds = new FileWriter("logincredentials.txt", false);
								changedCreds.write(newUsernameText.getText()+","+newPasswordText.getText());
								changedCreds.close();
							}
							catch(Exception e1) {
								System.out.println("COULD NOT FIND FILE!!!!");
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
			}
			{
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
	}
}