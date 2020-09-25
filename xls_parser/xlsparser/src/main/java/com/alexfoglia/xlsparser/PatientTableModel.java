package com.alexfoglia.xlsparser;

import java.util.List;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class PatientTableModel extends AbstractTableModel
{
    private String[] columnNames =
    {
    	"Name", "Age", "Birth Date", "Diag. Code", "ICD9CM Code", "Surgery Date"
    };
    private List<PatientModel> patients;
 
    public PatientTableModel(List<PatientModel> patients)
    {
        this.patients = patients;
    }
 
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }
 
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
 
    @Override
    public int getRowCount()
    {
        return patients.size();
    }
 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int column)
	{
	    return String.class;
	}
		 
	public boolean isCellEditable(int row, int column)
	{
	    return false;
	}
	 
	@Override
	public Object getValueAt(int row, int column)
	{
	    PatientModel pat = getPatientModel(row);
	 
	    switch (column)
	    {
	        case 0: return pat.getName();
	        case 1: return pat.getAge(true);
	        case 2: return pat.getBirthdate();
	        case 3: return pat.getDiag_code();
	        case 4: return pat.getIcd9cm_code();
	        case 5: return pat.getSurgery_date();
	        default: return null;
	    }
	}
 
	public PatientModel getPatientModel(int row)
	{
	    return patients.get(row);
	}
	
}
