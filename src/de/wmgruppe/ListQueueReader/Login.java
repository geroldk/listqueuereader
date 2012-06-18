package de.wmgruppe.ListQueueReader;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.resource.ResourceException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.PopupFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.Popup;

import com.ibm.vse.connector.InvalidPasswordException;
import com.ibm.vse.connector.VSEConnectionSpec;
import com.ibm.vse.connector.VSESystem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textServer;
	private JTextField textPort;
	private JTextField textUsername;
	private JPasswordField passwordField;
	private VSESystem system;


	/**
	 * Create the frame.
	 */
	public Login(VSESystem system) {
		this.system = system;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 406, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblServer = new JLabel("Server:");

		textServer = new JTextField();
		textServer.setText("172.20.160.54");
		textServer.setColumns(10);

		JLabel lblNewLabel = new JLabel("Port:");

		textPort = new JTextField();
		textPort.setText("2893");
		textPort.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Username:");

		textUsername = new JTextField();
		textUsername.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Kennwort");

		passwordField = new JPasswordField();

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VSEConnectionSpec spec;
				try {
					char passw[] = Login.this.passwordField.getPassword();
					spec = new VSEConnectionSpec(InetAddress
							.getByName(Login.this.textServer.getText()), Integer
							.valueOf(Login.this.textPort.getText()),
							Login.this.textUsername.getText(), String
									.valueOf(passw));
					spec.setMaxConnections(5);
					spec.setLogonMode(true);
					Login.this.system.setConnectionSpec(spec);
					Login.this.system.connect();
					Arrays.fill(passw, '\0');
					JOptionPane.showMessageDialog(Login.this, "Erfolgreich verbunden" ,"Erfolg", JOptionPane.INFORMATION_MESSAGE);
					Login.this.dispose();
					

				} catch (UnknownHostException e1) {
					JOptionPane.showMessageDialog(Login.this, e1.getMessage() ,"ERROR", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ResourceException e2) {
					JOptionPane.showMessageDialog(Login.this, e2.getMessage() ,"ERROR", JOptionPane.ERROR_MESSAGE);
					e2.printStackTrace();
				} catch (IOException e3) {
					JOptionPane.showMessageDialog(Login.this, e3.getMessage() ,"ERROR", JOptionPane.ERROR_MESSAGE);
					e3.printStackTrace();
				} catch (InvalidPasswordException e4) {
					JOptionPane.showMessageDialog(Login.this, e4.getMessage() ,"ERROR", JOptionPane.ERROR_MESSAGE);
					e4.printStackTrace();
				}

			}
		});

		JButton btnNewButton_1 = new JButton("Abbrechen");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.this.dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane
				.setHorizontalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblServer)
																										.addGap(18)
																										.addComponent(
																												textServer,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								gl_contentPane
																										.createSequentialGroup()
																										.addComponent(
																												lblNewLabel_1)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textUsername,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(18)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblNewLabel_2)
																						.addComponent(
																								lblNewLabel))
																		.addGap(18)
																		.addGroup(
																				gl_contentPane
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								passwordField)
																						.addComponent(
																								textPort)))
														.addGroup(
																gl_contentPane
																		.createSequentialGroup()
																		.addComponent(
																				btnNewButton)
																		.addGap(18)
																		.addComponent(
																				btnNewButton_1)))
										.addContainerGap(112, Short.MAX_VALUE)));
		gl_contentPane
				.setVerticalGroup(gl_contentPane
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_contentPane
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblServer)
														.addComponent(
																textServer,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblNewLabel)
														.addComponent(
																textPort,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNewLabel_1)
														.addComponent(
																textUsername,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																lblNewLabel_2)
														.addComponent(
																passwordField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(43)
										.addGroup(
												gl_contentPane
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																btnNewButton)
														.addComponent(
																btnNewButton_1))
										.addContainerGap(128, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
