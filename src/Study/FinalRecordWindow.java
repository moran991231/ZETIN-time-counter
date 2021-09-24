package Study;

import javax.swing.*;
import java.awt.*;

public class FinalRecordWindow extends JFrame {

	private JPanel panel;
	private JLabel sectorLabel, nameLabel, shortestRecordLabel;
	
	private int LabelSize = 30;
	
	private Font LabelFont = new Font("¸¼Àº °íµñ", Font.BOLD, 30);

	
	// Generator
	public FinalRecordWindow() {
		setSize(400, 400);
		setTitle("Final Record Window");
		setLocation(320, 620);

		panel = new JPanel(new GridLayout(3, 3));
		panel.setBackground(new Color(0, 73, 140));

		sectorLabel = new JLabel("ºÎ¹®¸í");
		nameLabel = new JLabel("*^_^*");
		shortestRecordLabel = new JLabel("01:23.456");

		sectorLabel.setHorizontalAlignment(JLabel.CENTER);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		shortestRecordLabel.setHorizontalAlignment(JLabel.CENTER);

		sectorLabel.setForeground(Color.white);
		nameLabel.setForeground(Color.white);
		shortestRecordLabel.setForeground(Color.yellow);
		
		panel.add(sectorLabel);
		panel.add(nameLabel);
		panel.add(shortestRecordLabel);
		
		add(panel);

		changeFontSize(0);

		setVisible(true);
	}

	
	public void setTexts(String sector, String name, String shortestRecord) {
		sectorLabel.setText(sector);
		nameLabel.setText(name);
		shortestRecordLabel.setText(shortestRecord);
	}
	
	public void changeFontSize(int d) {
		if (LabelSize + d > 0) 
			LabelSize += d;
		LabelFont = new Font("¸¼Àº °íµñ", Font.BOLD, LabelSize);
		sectorLabel.setFont(LabelFont);
		nameLabel.setFont(LabelFont);
		shortestRecordLabel.setFont(LabelFont);
	}

}
