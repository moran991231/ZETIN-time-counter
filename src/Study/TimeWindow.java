package Study;

import javax.swing.*;
import java.awt.*;

public class TimeWindow extends JFrame {
	private final String PATH = Record.PATH;

	private JPanel timePanel, namePanel, sponsorPanel, penaltyPanel1, penaltyPanel2;
	private JLabel sectorLabel, univLabel, nameLabel, robotLabel;
	private JLabel sponsorLabel1, sponsorLabel2, sponsorLabel3;
	private JTextArea recordTextArea, rankingTextArea;
	private ImageIcon icon1, icon2;
	private Image temp1, temp2;
	private TimerDownThread downThread;
	private TimerUpThread upThread;
	
	public JLabel time4minLabel, timeDrivingLabel;
	public JTextField penalty4minTextField;
	public JLabel penalty4minLabel, penaltyDcLabel;

	private final Color PALE_GREEN = new Color(173, 223, 179);
	private final Color PALE_YELLOW = new Color(255, 233, 151);
	private final Color CORAL = new Color(255, 102, 102);
	private final Color GREEN = new Color(0, 204, 0);

	private final float ratio1 = (float) 500 / 82; // ¼­¿ï½Ã¸³´ë.png (length)/(width)
	private final float ratio2 = (float) 1571 / 132; // ÆÄ¿ïÇÏ¹ö.png (length)/(width)

	private int height = 20;
	private int timerSize = 40;
	private int recordSize = 20;
	private int nameSize = 20;

	private Font timeFont;
	private Font nameFont1, nameFont2; // namePanel label Font (1: runner data, 2: penalties)
	private Font recordFont;

	
	// Generator
	public TimeWindow() { 
		setSize(600, 600);
		setTitle("Time Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timeFont = new Font("¸¼Àº °íµñ", Font.BOLD, timerSize);
		nameFont1 = new Font("¸¼Àº °íµñ", Font.BOLD, nameSize);
		nameFont2 = new Font("¸¼Àº °íµñ", Font.PLAIN, nameSize);
		recordFont = new Font("¸¼Àº °íµñ", Font.PLAIN, recordSize);

		// BorderLayout.NORTH
		// setting timePanel (show limit time(4min,timer up) and driving time (timer
		// down)
		timePanel = new JPanel(new GridLayout(2, 0));

		time4minLabel = new JLabel("04:00.000");
		time4minLabel.setFont(timeFont);
		time4minLabel.setHorizontalAlignment(JLabel.CENTER);
		time4minLabel.setOpaque(true);
		time4minLabel.setBackground(PALE_GREEN);

		timeDrivingLabel = new JLabel("00:00.000");
		timeDrivingLabel.setFont(timeFont);
		timeDrivingLabel.setHorizontalAlignment(JLabel.CENTER);
		timeDrivingLabel.setOpaque(true);
		timeDrivingLabel.setBackground(GREEN);

		timePanel.add(time4minLabel);
		timePanel.add(timeDrivingLabel);

		// BorderLayout.WEST
		// setting namePanel (showpresent runner's data : sector, univ, name, robot and
		// penalties)
		namePanel = new JPanel(new GridLayout(0, 1));

		sectorLabel = new JLabel("Freshman");
		sectorLabel.setFont(nameFont1);
		sectorLabel.setHorizontalAlignment(JLabel.CENTER);
		sectorLabel.setForeground(Color.BLUE);

		univLabel = new JLabel("¼­¿ï½Ã¸³´ëÇÐ±³");
		univLabel.setFont(nameFont1);
		univLabel.setHorizontalAlignment(JLabel.CENTER);

		nameLabel = new JLabel("¹ÚÀç¼±");
		nameLabel.setFont(nameFont1);
		nameLabel.setHorizontalAlignment(JLabel.CENTER);

		robotLabel = new JLabel("´ó´ó´ó");
		robotLabel.setFont(nameFont1);
		robotLabel.setHorizontalAlignment(JLabel.CENTER);

		namePanel.add(sectorLabel);
		namePanel.add(univLabel);
		namePanel.add(nameLabel);
		namePanel.add(robotLabel);

		// setting penaltyPanels in namePanel
		penaltyPanel1 = new JPanel();
		// penaltyPanel1.setSize(WIDTH, nameSize / 2);

		penalty4minTextField = new JTextField(3);
		penalty4minTextField.setText("0");
		penalty4minTextField.setFont(nameFont2);
		penalty4minTextField.setHorizontalAlignment(JLabel.RIGHT);
		penalty4minTextField.setEditable(false);
		penalty4minLabel = new JLabel("ÃÊ °æ¿¬ ÆÐ³ÎÆ¼");
		penalty4minLabel.setFont(nameFont2);

		penaltyDcLabel = new JLabel("7ÃÊ DC ÆÐ³ÎÆ¼");
		penaltyDcLabel.setFont(nameFont2);
		penaltyDcLabel.setHorizontalAlignment(JLabel.CENTER);
		penaltyDcLabel.setVisible(false);

		penaltyPanel1.add(penalty4minTextField);
		penaltyPanel1.add(penalty4minLabel);

		// setting penaltyPanel2 (penaltyPanel 1 + penaltyDcLabel)
		penaltyPanel2 = new JPanel(new GridLayout(0, 1));
		penaltyPanel2.add(penaltyPanel1);
		penaltyPanel2.add(penaltyDcLabel);

		// add penaltyPanel2 to namePanel
		namePanel.add(penaltyPanel2);

		// BorderLayout.CENTER
		// setting recordTextArea
		recordTextArea = new JTextArea();
		recordTextArea.setEditable(false);
		recordTextArea.setBackground(new Color(230, 230, 230));
		recordTextArea.setFont(recordFont);

		// BorderLayout.EAST
		// setting rankingTextArea
		rankingTextArea = new JTextArea();
		rankingTextArea.setEditable(false);
		rankingTextArea.setBackground(new Color(248, 229, 154));
		rankingTextArea.setFont(recordFont);

		// BorderLayout.SOUTH
		changeHeight(0); // sponsorPanel generate and add to JFrame

		// add to JFrame
		add(timePanel, BorderLayout.NORTH);
		add(namePanel, BorderLayout.WEST);
		add(recordTextArea, BorderLayout.CENTER);
		add(rankingTextArea, BorderLayout.EAST);

		setVisible(true);
	} // generator end

	
	// Get Label Text methods
	public String getTimeDrivingLabelText() {
		return timeDrivingLabel.getText();
	}

	
	// Set Label, TextArea, TextField Text methods
	public void setTime4minLabelText(String str) {
		time4minLabel.setText(str);
	}

	public void setTimeDrivingLabelText(String str) {
		timeDrivingLabel.setText(str);
	}

	public void setNames(String[] presentRunner) {
		univLabel.setText(presentRunner[Record.UNIV]);
		nameLabel.setText(presentRunner[Record.NAME]);
		robotLabel.setText(presentRunner[Record.ROBOT_NAME]);
		sectorLabel.setText(presentRunner[Record.SECTOR]);
	}

	public void setTimeDrivingLabelColor(int state) {
		switch (state) {
		case -1:
		case 0:
			timeDrivingLabel.setBackground(GREEN);
			break;
		case 1:
			timeDrivingLabel.setBackground(new Color(137, 197, 65));
			break;
		case 2:
			timeDrivingLabel.setBackground(new Color(245, 237, 50));
			break;
		case 3:
			timeDrivingLabel.setBackground(new Color(253, 200, 84));
			break;
		case 4:
			timeDrivingLabel.setBackground(new Color(249, 176, 125));
			break;

		default:
			timeDrivingLabel.setBackground(new Color(251, 198, 201));
			break;

		}
	}

	public void setRecordTextArea(String str) {
		recordTextArea.setText(str);
	}

	public void setRankingTextArea(String str) {
		rankingTextArea.setText(str);
	}

	public void setPenaltyTextField(int time) {
		penalty4minTextField.setText("" + time);
	}

	
	// Reset Texts about time method (timer, penalties...)
	public void timeTextReset() {
		time4minLabel.setText("04:00.000");
		time4minLabel.setBackground(PALE_GREEN);
		timeDrivingLabel.setText("00:00.000");
		timeDrivingLabel.setBackground(GREEN);

		ButtonWindow.penalty4minTextField.setText("0");
		ButtonWindow.penalty4minTextField.getAction();
		ButtonWindow.penaltyDcCheckBox.setSelected(false);
		ButtonWindow.limitTimeTextField.setText("04:00.000");

		this.penalty4minTextField.setText("0");
		this.penaltyDcLabel.setVisible(false);

		TimerDownThread.resetSettings();
		TimerUpThread.penaltyTime = 0;
	}

	public void setVisibleDcpenalty(boolean b) {
		penaltyDcLabel.setVisible(b);
	}

	
	// Append String to TextAreas methods
	public void appendRecordTextArea(String str) {
		recordTextArea.append(str);
	}

	public void appendRankingTextArea(String str) {
		rankingTextArea.append(str);
	}


	// Change Size methods
	public void changeTimerSize(int d) {
		if (timerSize + d > 0) {
			timerSize += d;
			timeFont = new Font("¸¼Àº °íµñ", Font.BOLD, timerSize);
			time4minLabel.setFont(timeFont);
			timeDrivingLabel.setFont(timeFont);
		}
	}

	
	 // Change Text size methods. These methods are called and used on ButtonWindow2
	public void changeNameSize(int d) { // size of labels on namePanel
		if (nameSize + d >= 1) {
			nameSize += d;
			nameFont1 = new Font("¸¼Àº °íµñ", Font.BOLD, nameSize);
			nameFont2 = new Font("¸¼Àº °íµñ", Font.BOLD, nameSize / 2 + 5);

			univLabel.setFont(nameFont1);
			nameLabel.setFont(nameFont1);
			robotLabel.setFont(nameFont1);

			penaltyDcLabel.setFont(nameFont2);
			penalty4minLabel.setFont(nameFont2);
		}
	}

	public void changeRecordSize(int d) { // size of personal and ranking records
		if (recordSize + d >= 1) {
			recordSize += d;
			recordFont = new Font("¸¼Àº °íµñ", Font.PLAIN, recordSize);
			recordTextArea.setFont(recordFont);
			rankingTextArea.setFont(recordFont);
		}
	}

	public void changeHeight(int change) { // size of sponsor labels
		height += change;
		if (height <= 1)
			height = 2;
		try {
			remove(sponsorPanel);
		} catch (Exception e) {
		}

		sponsorPanel = new JPanel();
		icon1 = new ImageIcon(PATH + "½ºÆù¼­ ¾ÆÀÌÄÜ\\¼­¿ï½Ã¸³´ë.png");
		temp1 = icon1.getImage();
		temp2 = temp1.getScaledInstance((int) (height * ratio1), height, Image.SCALE_SMOOTH);
		icon1 = new ImageIcon(temp2);

		icon2 = new ImageIcon(PATH + "½ºÆù¼­ ¾ÆÀÌÄÜ\\ÆÄ¿ïÇÏ¹ö.png");
		temp1 = icon2.getImage();
		temp2 = temp1.getScaledInstance((int) (height * ratio2), height, Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(temp2);

		sponsorLabel1 = new JLabel("ÈÄ¿ø");
		sponsorLabel1.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, height));
		sponsorLabel2 = new JLabel(icon1);
		sponsorLabel3 = new JLabel(icon2);

		sponsorPanel.add(sponsorLabel1);
		sponsorPanel.add(sponsorLabel2);
		sponsorPanel.add(sponsorLabel3);
		add(sponsorPanel, BorderLayout.SOUTH);

		setVisible(true);

		icon1 = icon2 = null;
		temp1 = temp2 = null;
	}

	
	// methods associated with Time and Thread
	public void start4min() { // start a new down timer
		if (TimerDownThread.isActive == false) {
			TimerDownThread.isActive = true;

			time4minLabel.setBackground(PALE_GREEN);
			timeDrivingLabel.setBackground(GREEN);
			/*
			 * I can't edit penaltyTime (4min) while Driving.
			 * However, I can edit DC penalty while driving.
			 */
			ButtonWindow.limitTimeTextField.setEditable(false);

			downThread = new TimerDownThread(this);
			downThread.start();

		} else if (TimerDownThread.isPaused == true) { // continue the paused down timer
			TimerDownThread.isPaused = false;
			time4minLabel.setBackground(PALE_GREEN);
			ButtonWindow.limitTimeTextField.setEditable(false);

			downThread = new TimerDownThread(this);
			downThread.start();
		}
	}

	public void pause4min() {// pause timer up thread (turn isPaused into true)
		if (TimerDownThread.isActive == true) {
			TimerDownThread.isPaused = true;
			time4minLabel.setBackground(PALE_YELLOW);
			ButtonWindow.limitTimeTextField.setEditable(true);

			downThread.pause();
			downThread.interrupt(); // TimerDownThread.isActive is still true!!!!
		}
	}

	public void stop4min() {
		time4minLabel.setBackground(CORAL);
		TimerDownThread.isActive = false;
		ButtonWindow.limitTimeTextField.setEditable(true);

		downThread.interrupt();
	}

	public void startCountUp() {
		if ((TimerDownThread.isActive == true) && (TimerUpThread.isActive == false)) {
			timeDrivingLabel.setBackground(GREEN);
			TimerUpThread.isActive = true;

			upThread = new TimerUpThread(this);
			upThread.start(); // count up (record) start
		}
	}

	public void stopCountUp() {
		if (TimerUpThread.isActive == true) {
			TimerUpThread.isActive = false;
			upThread.interrupt();
		}
	}

	
	// set timeDrivingLabel Text to true time (ARM's time)
	public void setTrueTime(String str) {  //str: String of ARM's time with null char

		int i = str.indexOf("0");
		String armTime = str.substring(i, i + 8);    // ARM's time is 8-digits 
		String armSecStr = armTime.substring(0, 4);  // first 4-digits are in [sec]
		String armMsStr = armTime.substring(4);		 // other 4-digits are in [0.1 ms]
		System.out.println(armSecStr + "-" + armMsStr);

		try {
			int armSec = Integer.parseInt(armSecStr);
			int armMs = Integer.parseInt(armMsStr);

			int min = armSec / 60;
			int sec = armSec % 60;
			int ms = armMs / 10;
			String timeStr = String.format("%02d:%02d.%03d", min, sec, ms);

			this.timeDrivingLabel.setText(timeStr);
			System.out.println("set true time!");

		} catch (NumberFormatException e) {
			System.out.println("¹º°¡ ÀÌ»óÇØ");
		}
	}

}
