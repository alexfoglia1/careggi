package com.alexfoglia.xlsparser;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Gui extends JFrame
{
	private static final Toolkit t = Toolkit.getDefaultToolkit();
	private static final int SCREEN_WIDTH = t.getScreenSize().width;
	private static final int SCREEN_HEIGHT = t.getScreenSize().height;
	
	private JButton btnChooseFile, btnParse;
	private JTable tab0,tab1,tab2;
	private JPanel tab3;
	private JPanel hdp;
	private DrawPanel dp;
	private JRadioButton goods, ambiguous, onlyTorach, ageNow, ageSurg;
	private JComboBox<Integer> nBins;
	private JTabbedPane tabs;
	private File filePath;
	private JLabel chosenFile;
	private XlsParser parser;
	private XlsWriter writer;
	private List<PatientModel> patients;
	private boolean[] selectedSources;
	private ActionListener drawInsightsAction;
	private CustomTab tabCustom;
	
	
	public Gui()
	{
		super("Xls Parser");
		this.btnChooseFile = new JButton("Select File");
		this.btnParse = new JButton("Parse");
		this.tabs = new JTabbedPane();
		this.tab0 = new JTable();
		this.tab1 = new JTable();
		this.tab2 = new JTable();
		this.tab3 = new JPanel();
		this.tabCustom = new CustomTab();
		this.chosenFile = new JLabel("No File Selected");
		this.parser = new XlsParser();
		this.writer = new XlsWriter();
		this.goods = new JRadioButton("Good");
		this.ambiguous = new JRadioButton("Ambiguos");
		this.onlyTorach = new JRadioButton("Only Thorac.");
		this.ageNow = new JRadioButton("Current Age");
		this.ageSurg = new JRadioButton("Surgery Age");
		this.ageNow.setSelected(true);
		this.ageSurg.setSelected(false);
		this.goods.setSelected(false);
		this.ambiguous.setSelected(false);
		this.onlyTorach.setSelected(false);
		this.nBins = new JComboBox<>();
		this.hdp = new JPanel();
		this.dp = new DrawPanel(37*SCREEN_WIDTH/40, 275*SCREEN_HEIGHT/400);
		this.tab3.setLayout(new BorderLayout());
		this.selectedSources = new boolean[] {false, false, false};
		
		createGui();
		createEvents();
		display();
	}

	private void createEvents()
	{

		goods.addChangeListener((arg0)-> {
			selectedSources[PatientModel.STATUS_OK] = goods.isSelected();
		});
		
		ambiguous.addChangeListener((arg0)-> {
			selectedSources[PatientModel.STATUS_AM] = ambiguous.isSelected();
		});
		
		onlyTorach.addChangeListener((arg0)-> {
			selectedSources[PatientModel.STATUS_OT] = onlyTorach.isSelected();
		});
		
		ageSurg.addChangeListener((arg0)->{
			ageNow.setSelected(!ageSurg.isSelected());
		});
		
		ageNow.addChangeListener((arg0)->{
			ageSurg.setSelected(!ageNow.isSelected());
		});
		
		this.drawInsightsAction = (arg0) -> {
			if(this.patients != null)
			{
				int nBin = (int) nBins.getSelectedItem();
				
				PatientDBInterfaceImpl dbIntface = new PatientDBInterfaceImpl(this.patients);
				List<PatientModel> insightsTarget = new ArrayList<>();
				for(int i = 0; i < selectedSources.length; i++)
				{
					if(selectedSources[i])
					{
						List<PatientModel> source = (dbIntface.searchByStatus(i));
						source.forEach(x -> insightsTarget.add(x));
					}
				}

				if(!insightsTarget.isEmpty())
				{
					dp.draw(new PatientDBInterfaceImpl(insightsTarget).searchAges(ageNow.isSelected()), nBin);
				}
				else
				{
					dp.erase();
				}
			}
			
		};
		
		Arrays.asList(goods, ambiguous, onlyTorach, ageNow, ageSurg).forEach(x -> x.addActionListener(drawInsightsAction));
		nBins.addActionListener(drawInsightsAction);
		
		btnChooseFile.addActionListener((arg0)->{
			JFileChooser fChoose = new JFileChooser(".");
			fChoose.showOpenDialog(this);
			this.filePath = fChoose.getSelectedFile();
			if(this.filePath != null)
			{
				this.chosenFile.setText(this.filePath.getAbsolutePath());
			}
		});
		
		btnParse.addActionListener((arg0)->{
			
			if(this.filePath == null)
			{
				JOptionPane.showMessageDialog(this, "No file selected");
			}
			else
			{
				try
				{
					this.patients = parser.parse(this.filePath.getAbsolutePath());
					//this.patients = generateRandom(20000, 5000, 5000, 75, 10);
					PatientDBInterfaceImpl dbIntface = new PatientDBInterfaceImpl(this.patients);
					
					nBins.removeActionListener(drawInsightsAction);
					int bestNBins = 1 + (int)(Math.sqrt(this.patients.size()));
					nBins.removeAllItems();
					int limit = Math.max(bestNBins, dbIntface.searchAges(true).length);
					for(int i = 1; i <= limit ; i++)
					{
						nBins.addItem(i);
					}
					nBins.setSelectedItem(bestNBins);
					nBins.addActionListener(drawInsightsAction);
					
					List<PatientModel> good = dbIntface.searchByStatus(PatientModel.STATUS_OK);
					List<PatientModel> suspicious = dbIntface.searchByStatus(PatientModel.STATUS_AM);
					List<PatientModel> onlyTorach = dbIntface.searchByStatus(PatientModel.STATUS_OT);
					
					PatientTableModel mod0 = new PatientTableModel(good);
					PatientTableModel mod1 = new PatientTableModel(suspicious);
					PatientTableModel mod2 = new PatientTableModel(onlyTorach);
					tab0.setModel(mod0);
					tab1.setModel(mod1);
					tab2.setModel(mod2);

					String outputFile = this.filePath.getParentFile().getAbsolutePath() + "/" + filePath.getName() + "OUTPUT.xls";
					writer.write(patients, outputFile);
					JOptionPane.showMessageDialog(this, "Written file: "+outputFile);
				}catch(IllegalArgumentException e)
				{
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
				}
			}
			
		});
		
	}

	private List<PatientModel> generateRandom(int goods, int amb, int onlyT, double meanAge, double sd)
	{
		List<PatientModel> patients = new ArrayList<>();
		Random r = new Random();
		
		for(int i = 0; i < goods; i++)
		{
			PatientModel pat = generateRandomPatient(meanAge, sd, r, i, PatientModel.STATUS_OK);
			patients.add(pat);
		}
		for(int i = 0; i < amb; i++)
		{
			PatientModel pat = generateRandomPatient(meanAge, sd, r, i + goods, PatientModel.STATUS_AM);
			patients.add(pat);
		}
		for(int i = 0; i < onlyT; i++)
		{
			PatientModel pat = generateRandomPatient(meanAge, sd, r, i + goods + amb, PatientModel.STATUS_OT);
			patients.add(pat);
		}
		
		return patients;
	}

	private PatientModel generateRandomPatient(double meanAge, double sd, Random r, int index, int status)
	{
		String name = "RandomPatient "+index;
		int bday = 10 + (int)(Math.random()*18);
		int bmonth = 1 + (int)(Math.random()*12);
		double z = r.nextGaussian();
		double x = z*sd + meanAge;
		int byear = (int)(2020 - x);
		String birthdate = String.format("%d/%s/%d", bday, bmonth < 10 ? "0"+bmonth : bmonth, byear);
		String surgdate = "01/07/2020";
		String icd9cm = "000.0";
		String diagcode = "00.00";
		String age = "" + Utils.getAge(birthdate, null);
		String surgage = "" + Utils.getAge(birthdate, surgdate);
		PatientModel pat = new PatientModel(name, age, birthdate, diagcode, icd9cm, surgdate, surgage);
		pat.setStatus(status);
		return pat;
	}

	private void createGui()
	{
		Container c = getContentPane();
		
		JPanel north, south;
		north = new JPanel();
		tabs.addTab("Good", new JScrollPane(tab0));
		tabs.addTab("Ambiguous", new JScrollPane(tab1));
		tabs.addTab("Only thorac.", new JScrollPane(tab2));
		tabs.addTab("Custom", new JScrollPane(tabCustom));
		
		JPanel hdpleft = new JPanel();
		JPanel hdpright = new JPanel();
		JPanel hdpcenter = new JPanel();
		hdp.setLayout(new BorderLayout());
		hdpleft.add(goods);
		hdpleft.add(ambiguous);
		hdpleft.add(onlyTorach);
		hdpcenter.add(ageNow);
		hdpcenter.add(ageSurg);
		hdpright.add(new JLabel("N Bins"));
		hdpright.add(nBins);
		hdp.add(hdpleft, BorderLayout.WEST);
		hdp.add(hdpcenter, BorderLayout.CENTER);
		hdp.add(hdpright, BorderLayout.EAST);
		tab3.add(hdp, BorderLayout.NORTH);
		tab3.add(dp, BorderLayout.SOUTH);
		tabs.addTab("Stats", tab3);
		
		south = new JPanel();
		
		north.add(this.btnChooseFile);
		north.add(this.chosenFile);
		south.add(this.btnParse);
		
		c.add(north,BorderLayout.NORTH);
		c.add(tabs, BorderLayout.CENTER);
		c.add(south, BorderLayout.SOUTH);
	}

	private void display()
	{
		setLocation(SCREEN_WIDTH/50, SCREEN_HEIGHT/50);
		setSize(new Dimension(22*SCREEN_WIDTH/23, 19*SCREEN_HEIGHT/21));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.filePath = new File("../data/RegistroOperatorioNote 15-20.xls");
		if(this.filePath != null)
		{
			this.chosenFile.setText(this.filePath.getAbsolutePath());
		}
		
	}

}
