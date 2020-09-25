package com.alexfoglia.xlsparser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel
{
	
	private int[] ages;
	private double[] counts;
	private int nBin;
	private int minAge;
	private int maxAge;
	private double dBin;
	private int[] leftBounds;
	private double realmean;
	private double realsd;
	private double[] expectedGauss;
	private double[] uncertainties;

	public DrawPanel(int width, int height)
	{
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(width, height));

	}

	public void draw(int[] ages, int nBin)
	{
		this.ages = ages;
		this.nBin = nBin <= 0 ? 1 + (int)(Math.sqrt(ages.length)) : nBin;
		this.minAge = Utils.min(ages);
		this.maxAge = Utils.max(ages);
		this.dBin = (this.maxAge - this.minAge)/(double)nBin;
		this.realmean = Utils.mean(ages);
		this.realsd = Utils.stdDev(ages);
		this.counts = new double[nBin];
		this.leftBounds = new int[nBin];
		this.expectedGauss = new double[nBin];
		this.uncertainties = new double[nBin];
		
		for(int i = 0; i < nBin; i++)
		{
			int leftBound = minAge + (int)Math.round(i*dBin);
			int rightBound = minAge + (int)Math.round((i+1)*dBin);
			this.counts[i] = Utils.count(ages, leftBound, rightBound);
			this.uncertainties[i] = Math.sqrt(this.counts[i]);
			this.leftBounds[i] = leftBound;
			
			double x1 = leftBound;
			double x2 = leftBound + dBin;
			double z1 = (x1 - realmean)/realsd;
			double z2 = (x2 - realmean)/realsd;
			this.expectedGauss[i] = (Utils.cnorm(z2) - Utils.cnorm(z1))*ages.length;
		}
		
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(counts != null)
		{
			g.setFont(g.getFont().deriveFont(10.0f));

			double maxCount = Math.max(Utils.max(counts) + Utils.max(uncertainties), Utils.max(expectedGauss));
			int offsetY = getHeight()/10;
			int offsetX = getWidth()/20;
			double scaleX = (19*getWidth()/20 - offsetX) / (double)(nBin);
			double scaleY = (getHeight() - offsetY) / (double)(maxCount);
			
			g.drawString("Total Observations: " +ages.length+"    Min age: "+minAge+"    Max age: "+maxAge+"    Mean: "+realmean + "    Std.Dev: "+realsd, 2*offsetX, getHeight()-offsetY/3);
			
			for(int i = 0; i <= maxCount; i++)
			{
				g.setColor(Color.BLACK);
				int y = getHeight() - (int)(i*scaleY) - offsetY;
				g.drawLine(offsetX - 5, y, offsetX + 5, y);
				g.drawString(""+i, offsetX - 20, y);
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(offsetX+5, y, getWidth(), y);
			}
			
			g.setColor(Color.BLACK);
			g.drawLine(offsetX,  getHeight()-offsetY, getWidth(), getHeight()-offsetY);
			g.drawLine(offsetX, getHeight()-offsetY, offsetX, 0);
			
			for(int i = 0; i < counts.length; i++)
			{
				g.setColor(Color.BLACK);
				int x0 = offsetX + (int)(i*scaleX);
				int x1 = offsetX + (int)((i+1)*scaleX);
				int gx0 = (int) ((x1 + x0)/2.0);
				int gy0 = getHeight() - (int)(expectedGauss[i]*scaleY) - offsetY;
				int height = getHeight() - (int)(counts[i]*scaleY) - offsetY;
				int unc_height = (int) (uncertainties[i] * scaleY);
				g.drawLine(x0, height, x1, height);
				g.drawLine(x0, height, x0, getHeight() - offsetY);
				g.drawLine(x1, height, x1, getHeight() - offsetY);
				g.fillOval(gx0-3, height-3, 6, 6);
				g.drawLine(gx0, height, gx0, height - unc_height);
				g.drawLine(gx0, height, gx0, height + unc_height);
				
				g.drawString(""+(leftBounds[i]), x0 - 6, getHeight() - offsetY + 12);
			
				g.setColor(Color.RED);
				
				g.fillOval(gx0-3, gy0-3, 7, 7);
			}
			
			g.setColor(Color.BLACK);
			g.drawString(""+((int)Math.round(dBin)+(leftBounds[leftBounds.length-1])), (int) (offsetX + counts.length*scaleX), getHeight() - offsetY + 12);
			
			g.setFont(g.getFont().deriveFont(Font.BOLD).deriveFont(11.0f));
			g.drawString("Age", (int) (offsetX + counts.length*scaleX), getHeight() - offsetY + 30);
			g.drawString("Count", 3, offsetY/3);
		}
	}

	
	public void erase()
	{
		this.counts = null;
		repaint();
	}

}
