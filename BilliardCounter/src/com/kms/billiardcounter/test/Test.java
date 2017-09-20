package com.kms.billiardcounter.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Test {

	public static void main(String[] args) {
	
		JFrame f = new JFrame( "testing" );
		
		JPanel p = new JPanel();
		
		ArrayList<JButton> l = new ArrayList<JButton>();
		
		JButton b = new JButton();
		
		b.setPreferredSize( new Dimension( 200, 50 ) );
		b.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				l.remove( b );
				
			}
			
		});
		
		p.add( b );
		
		f.setBounds( 400, 150, 400, 300 );
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.add( p );
		
		f.setVisible( true );
		
	}
	
}
