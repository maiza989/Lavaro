package com.example.twliovoicemail;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class SMSPanel extends JPanel {

	
	private final JLabel lblTragetPhone = new JLabel("Traget Phone=", JLabel.RIGHT);
	private JTextField txtTragetPhone;
	private SMSHandler s;
	
	public SMSPanel() {
		/**
		 *  setting layout and title border.
		 */
		setLayout(new GridLayout(5, 2));
		setBorder(BorderFactory.createTitledBorder("SMS"));
		
		s = new SMSHandler();
		
		/**
		 *  creating TextField and buttons
		 */
		txtTragetPhone = new JTextField(2);
		JButton b1 = new JButton("Send");
		/**
		 * adding TextFeild and buttons
		 */
		add(txtTragetPhone);
		add(b1);
		
		b1.addActionListener(new ActionListener() {
		
		
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
	}//end of SMSPanel
	
	
}// end of SMSPanel
