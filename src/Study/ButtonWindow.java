package Study;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class ButtonWindow extends JFrame {

	final String PATH = Record.PATH;

	JButton start4min, pause4min, stop4min;
	JButton startCountUp;
	JButton stopCountUp; // Force Stop Button when out of phase
	JButton saveRecord;
	JButton previousRunner, nextRunner;
	JButton showButtonWindow2;
	JButton reRead;
	JButton showFinalRecord;
	private JComboBox<String> runnerComboBox;

	public static JTextField penalty4minTextField, limitTimeTextField, manualSaveTextField, manualRemoveTextField;
	private JLabel penaltyLabel, limitTimeLabel, manualSaveLabel, manualRemoveLabel;
	public static JCheckBox penaltyDcCheckBox;

	private JPanel panel, timeControlPanel, penaltyPanel, limitPanel, manualControlPanel, manualSavePanel,
			manualRemovePanel;

	TimeWindow timeWindow;
	private ButtonWindow2 buttonWindow2;
	private Record record;
	private FinalRecordWindow finalRecordWindow;

	final Color PALE_GREEN = new Color(173, 223, 179);
	final Color CORAL = new Color(255, 102, 102);

	final Color PALE_PINK = new Color(248, 190, 214);
	final Color PALE_VIOLET = new Color(229, 155, 220);
	final Color MINT = new Color(156, 219, 217);
	final Color YELLOW = new Color(255, 238, 118);

	
	// Generator
	public ButtonWindow() throws IOException { 

		setTitle("Button Window");
		setSize(600, 700);
		setLocation(600, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timeWindow = new TimeWindow(); // create object of TimeWindow
		record = new Record(timeWindow); // create object of Record
		finalRecordWindow = new FinalRecordWindow();
		buttonWindow2 = new ButtonWindow2(timeWindow, record, finalRecordWindow); // pass timeWindow as a parameter

		// setting the icon of windows
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage(PATH + "스폰서 아이콘\\아이콘.png");

		this.setIconImage(img);
		timeWindow.setIconImage(img);
		record.setIconImage(img);
		finalRecordWindow.setIconImage(img);
		buttonWindow2.setIconImage(img);

		// declaration of action listeners : local variable
		ButtonListener buttonListener = new ButtonListener();
		ComboBoxListener comboBoxListener = new ComboBoxListener();
		TextFieldListener textFieldListener = new TextFieldListener();

		// generation of control buttons
		start4min = new JButton("경연시간 시작/재개");
		pause4min = new JButton("경연시간 일시정지");
		stop4min = new JButton("경연시간 정지");
		startCountUp = new JButton("기록 시작");
		stopCountUp = new JButton("기록 중지(탈조)");
		saveRecord = new JButton("시간기록");
		previousRunner = new JButton("이전 선수");
		nextRunner = new JButton("다음 선수");
		showButtonWindow2 = new JButton("버튼창2 보이기");
		reRead = new JButton("파일 다시 읽기");
		showFinalRecord = new JButton("최종 기록창 보이기");

		// generation and setting combo box
		String[] str = Record.runnerList.toArray(new String[Record.runnerList.size()]);
		runnerComboBox = new JComboBox<String>(str); // comboBox : select a specific runner
		runnerComboBox.setFont(new Font("맑은 고딕", Font.BOLD, 15));

		// Button Color
		start4min.setBackground(PALE_PINK);
		pause4min.setBackground(PALE_PINK);
		stop4min.setBackground(PALE_PINK);
		startCountUp.setBackground(PALE_VIOLET);
		stopCountUp.setBackground(PALE_VIOLET);
		saveRecord.setBackground(PALE_VIOLET);
		previousRunner.setBackground(MINT);
		nextRunner.setBackground(MINT);
		showButtonWindow2.setBackground(MINT);
		reRead.setBackground(YELLOW);
		showFinalRecord.setBackground(YELLOW);

		// Button Font
		Font butF = new Font("맑은 고딕", Font.BOLD, 16);
		start4min.setFont(butF);
		pause4min.setFont(butF);
		stop4min.setFont(butF);
		startCountUp.setFont(butF);
		stopCountUp.setFont(butF);
		saveRecord.setFont(butF);
		previousRunner.setFont(butF);
		nextRunner.setFont(butF);
		showButtonWindow2.setFont(butF);
		reRead.setFont(butF);
		showFinalRecord.setFont(butF);

		// addActionListeners to Components
		start4min.addActionListener(buttonListener);
		pause4min.addActionListener(buttonListener);
		stop4min.addActionListener(buttonListener);
		startCountUp.addActionListener(buttonListener);
		stopCountUp.addActionListener(buttonListener);
		saveRecord.addActionListener(buttonListener);
		previousRunner.addActionListener(buttonListener);
		nextRunner.addActionListener(buttonListener);
		showButtonWindow2.addActionListener(buttonListener);
		reRead.addActionListener(buttonListener);
		showFinalRecord.addActionListener(buttonListener);

		runnerComboBox.addActionListener(comboBoxListener);

		// generate panel
		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3, 2, 2));

		// time control components on timeControlPanel
		timeControlPanel = new JPanel();
		// penaltyPanel on timeControlPanel
		penaltyPanel = new JPanel();
		penalty4minTextField = new JTextField(3);
		penalty4minTextField.setHorizontalAlignment(JLabel.RIGHT);
		penalty4minTextField.setText("0");
		penaltyLabel = new JLabel("초 경연 패널티");

		penaltyPanel.add(penalty4minTextField);
		penaltyPanel.add(penaltyLabel);

		penaltyDcCheckBox = new JCheckBox("DC 7초 페널티");

		// add action listeners to text field and check box
		penalty4minTextField.addActionListener(textFieldListener);
		penaltyDcCheckBox.addActionListener(new CheckBoxListener());

		// generate limitPanel : set 4min (limit time)
		limitPanel = new JPanel(new GridLayout(0, 1));
		limitTimeLabel = new JLabel("경기시간 설정하기 ");
		limitTimeTextField = new JTextField(10);
		limitTimeTextField.setHorizontalAlignment(JLabel.CENTER);
		limitTimeTextField.setText("04:00.000");

		// add action listeners to components
		limitTimeTextField.addActionListener(textFieldListener);

		// add components to limitPanel
		limitPanel.add(limitTimeLabel);
		limitPanel.add(limitTimeTextField);

		// add components to timeControlPanel
		timeControlPanel.add(penaltyPanel);
		timeControlPanel.add(penaltyDcCheckBox);
		timeControlPanel.add(limitPanel);

		// generate manualControlPanel
		manualControlPanel = new JPanel(new GridLayout(0, 1));

		// manualSavePanel
		manualSavePanel = new JPanel();
		manualSaveLabel = new JLabel("기록추가");
		manualSaveTextField = new JTextField(10);
		manualSaveTextField.setText("00:00.000");
		manualSaveTextField.setHorizontalAlignment(JLabel.CENTER);
		manualSavePanel.add(manualSaveLabel);
		manualSavePanel.add(manualSaveTextField);

		// manualRemovePanel
		manualRemovePanel = new JPanel();
		manualRemoveLabel = new JLabel("기록제거");
		manualRemoveTextField = new JTextField(10);
		manualRemoveTextField.setText("00:00.000");
		manualRemoveTextField.setHorizontalAlignment(JLabel.CENTER);
		manualRemovePanel.add(manualRemoveLabel);
		manualRemovePanel.add(manualRemoveTextField);

		// add actionListener to components
		manualSaveTextField.addActionListener(textFieldListener);
		manualRemoveTextField.addActionListener(textFieldListener);

		// add save/remove Panels to manualCotrolPanel
		manualControlPanel.add(manualSavePanel);
		manualControlPanel.add(manualRemovePanel);

		// add components on panel
		panel.add(start4min);
		panel.add(pause4min);
		panel.add(stop4min);
		panel.add(startCountUp);
		panel.add(stopCountUp);
		panel.add(saveRecord);
		panel.add(previousRunner);
		panel.add(nextRunner);
		panel.add(showButtonWindow2);
		panel.add(reRead);
		panel.add(showFinalRecord);
		panel.add(timeControlPanel);
		panel.add(manualControlPanel);

		add(panel, BorderLayout.CENTER);
		add(runnerComboBox, BorderLayout.SOUTH);

		setVisible(true);

	}// generator end

	
	// ActionListeners
	class TextFieldListener implements ActionListener { // controlling 4min penalty time
		public void actionPerformed(ActionEvent e) {
			JTextField tf = (JTextField) e.getSource();
			if (tf == penalty4minTextField) {
				int penaltySec = Integer.parseInt(penalty4minTextField.getText());
				timeWindow.setPenaltyTextField(penaltySec);
				TimerDownThread.penaltyTime = (long) (penaltySec * 1000);

				long settingTime = TimerDownThread.limitTime - TimerDownThread.penaltyTime;
				String timeStr = TimerDownThread.longTimeToString(settingTime);
				timeWindow.setTime4minLabelText(timeStr);
				System.out.println("동작!");

			} else if (tf == limitTimeTextField) {
				TimerDownThread.setLimitTime(tf.getText());

			} else if (tf == manualSaveTextField) {
				record.addRecord(tf.getText());
				record.addToRanking(); // add the shortest personal record to Ranking Map and List
				manualSaveTextField.setText("00:00.000");

			} else if (tf == manualRemoveTextField) {
				record.removeRecord(tf.getText());
				record.addToRanking();
				manualRemoveTextField.setText("00:00.000");
			}
		}

	} // class TextFieldListener End

	class CheckBoxListener implements ActionListener { // controlling DC (7secs) penalty time
		public void actionPerformed(ActionEvent e) {
			JCheckBox c = (JCheckBox) e.getSource();
			timeWindow.setVisibleDcpenalty(c.isSelected());

			if (c.isSelected()) {
				TimerUpThread.penaltyTime = 7_000;
				timeWindow.setTimeDrivingLabelText("00:07.000");
			} else {
				TimerUpThread.penaltyTime = 0;
				timeWindow.setTimeDrivingLabelText("00:00.000");
			}
		}
	} // class CheckBoxListener End

	class ButtonListener implements ActionListener { // Button Action Listener
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();

			if (b == start4min) {
				timeWindow.start4min();

			} else if (b == pause4min) {
				timeWindow.pause4min();
				limitTimeTextField.setText(TimerDownThread.longTimeToString(TimerDownThread.limitTime));

			} else if (b == stop4min) {
				if (TimerDownThread.isActive == true)
					timeWindow.stop4min();

			} else if (b == startCountUp) {
				timeWindow.startCountUp();

			} else if (b == stopCountUp) {
				timeWindow.stopCountUp();
				timeWindow.timeDrivingLabel.setBackground(CORAL);

			} else if (b == previousRunner) {
				record.changeRunner(-1);
				runnerComboBox.setSelectedIndex(Record.presentNumber);

			} else if (b == nextRunner) {
				record.changeRunner(1);
				runnerComboBox.setSelectedIndex(Record.presentNumber);

			} else if (b == saveRecord) {
				// This button stop upThread, and add timeDrivingLabel Text to personal Ranking
				// if (TimerUpThread.isActive) {
				timeWindow.stopCountUp();
				record.addRecord(timeWindow.getTimeDrivingLabelText());

				// if (record.isShortestPersonal(timeWindow.getTimeDrivingLabelText()) == true)
				record.addToRanking();
				// }
			} else if (b == showButtonWindow2) {
				buttonWindow2.setVisible(true);

			} else if (b == reRead) {
				record.reread();
				timeWindow.setNames(Record.presentRunner);

				String[] str = Record.runnerList.toArray(new String[Record.runnerList.size()]);
				runnerComboBox = new JComboBox<String>(str); // comboBox : select a specific runner
				runnerComboBox.setSelectedIndex(Record.presentNumber);
				remove(runnerComboBox);
				add(runnerComboBox, BorderLayout.SOUTH);
				setVisible(true);

			} else if (b == showFinalRecord) {
				String[] runner = Record.presentRunner;
				finalRecordWindow.setTexts(runner[3], runner[1], record.getShortestPersonal(runner[1]));
				finalRecordWindow.setVisible(true);
			}
		}
	} // class ButtonListener End

	class ComboBoxListener implements ActionListener { // controlling present runner
		public void actionPerformed(ActionEvent e) {
			JComboBox c = (JComboBox) e.getSource();
			int index = c.getSelectedIndex();
			record.selectRunner(index);

			if (TimerUpThread.isActive == true || TimerDownThread.isActive == true) {
				c.setSelectedIndex(Record.presentNumber);
				// if Timer is active, I can't change present runner.
			}
		}
	} // class ComboBoxListenrer End

}
