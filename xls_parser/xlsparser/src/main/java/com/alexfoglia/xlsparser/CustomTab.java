package com.alexfoglia.xlsparser;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CustomTab extends JPanel
{
	private JPanel north,nw,nc,ncn,ncs,ne,nen1,nes1;
	private JTable center;
	private JPanel south;
	private JTextField txtDC,txtICD;
	private JTextArea txtTextPresent,txtTextNotPresent;
	
	public CustomTab()
	{
		this.north = new JPanel();
		this.nw = new JPanel();
		this.nc = new JPanel();
		this.ncn = new JPanel();
		this.ncs = new JPanel();
		this.ne = new JPanel();
		this.nen1 = new JPanel();
		this.nes1 = new JPanel();
		this.center = new JTable();
		this.south = new JPanel();
		this.txtDC = new JTextField(10);
		this.txtICD = new JTextField(10);
		this.txtTextPresent = new JTextArea(4,30);
		this.txtTextNotPresent = new JTextArea(4,30);

		setLayout(new BorderLayout());
		north.setLayout(new BorderLayout());
		ne.setLayout(new BorderLayout());
		nc.setLayout(new BorderLayout());
		nw.setLayout(new BorderLayout());
		nen1.setLayout(new BorderLayout());
		nes1.setLayout(new BorderLayout());
		
		nw.add(new JLabel("Diag. code"), BorderLayout.NORTH);
		nw.add(this.txtDC, BorderLayout.SOUTH);
		nc.add(ncn, BorderLayout.NORTH);
		nc.add(ncs, BorderLayout.SOUTH);
		ncn.add(new JLabel("ICD9CM code"));
		ncs.add(this.txtICD);
		ne.add(nen1, BorderLayout.NORTH);
		ne.add(nes1,BorderLayout.SOUTH);
		
		nen1.add(new JLabel("Text present"), BorderLayout.NORTH);
		nen1.add(new JScrollPane(this.txtTextPresent), BorderLayout.SOUTH);
		nes1.add(new JLabel("Text not present"), BorderLayout.NORTH);
		nes1.add(new JScrollPane(this.txtTextNotPresent), BorderLayout.SOUTH);
		north.add(ne, BorderLayout.EAST);
		north.add(nc, BorderLayout.CENTER);
		north.add(nw, BorderLayout.WEST);
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}
	
	public JTable getTable()
	{
		return this.center;
	}

}
