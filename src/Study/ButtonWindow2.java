package Study;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

//Buttons for changing the size of texts on TimeWindow
public class ButtonWindow2 extends JFrame implements ActionListener {
	private JButton heightUp, heightDown;
	private JButton timerUp, timerDown;
	private JButton recordUp, recordDown;
	private JButton nameUp, nameDown;
	private JButton sensitivityUp, sensitivityDown;
	private JButton sectorUp, sectorDown, nameListUp, nameListDown;

	private JPanel panel;

	private TimeWindow timeWindow;
	private Record record;
	private FinalRecordWindow finalRecordWindow;

	
	// Generator
	public ButtonWindow2(TimeWindow t, Record r, FinalRecordWindow f) {
		setSize(400, 400);
		setTitle("Button Window 2: Size Control");
		setLocation(1200, 0);

		this.timeWindow = t;
		this.record = r;
		this.finalRecordWindow = f;

		panel = new JPanel(new GridLayout(0, 2));

		heightUp = new JButton("�Ŀ� Ű���");
		heightDown = new JButton("�Ŀ� ���̱�");
		timerUp = new JButton("Ÿ�̸� Ű���");
		timerDown = new JButton("Ÿ�̸� ���̱�");
		recordUp = new JButton("��� Ű���");
		recordDown = new JButton("��� ���̱�");
		nameUp = new JButton("�̸� Ű���");
		nameDown = new JButton("�̸� ���̱�");
		sensitivityUp = new JButton("���� ����");
		sensitivityDown = new JButton("�赵 ����");
		sectorDown = new JButton("�ι�, �������� ����");
		sectorUp = new JButton("�ι�, �������� ����");
		nameListDown = new JButton("��� ũ�� ����");
		nameListUp = new JButton("��� ũ�� ����");

		timerUp.addActionListener(this);
		timerDown.addActionListener(this);
		nameUp.addActionListener(this);
		nameDown.addActionListener(this);
		recordUp.addActionListener(this);
		recordDown.addActionListener(this);
		heightUp.addActionListener(this);
		heightDown.addActionListener(this);
		sensitivityUp.addActionListener(this);
		sensitivityDown.addActionListener(this);
		sectorDown.addActionListener(this);
		sectorUp.addActionListener(this);
		nameListDown.addActionListener(this);
		nameListUp.addActionListener(this);

		panel.add(heightDown);
		panel.add(heightUp);
		panel.add(timerDown);
		panel.add(timerUp);
		panel.add(recordDown);
		panel.add(recordUp);
		panel.add(nameDown);
		panel.add(nameUp);
		//panel.add(sensitivityDown);
		//panel.add(sensitivityUp);
		panel.add(sectorDown);
		panel.add(sectorUp);
		panel.add(nameListDown);
		panel.add(nameListUp);

		add(panel);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();

		if (b == heightUp) {
			timeWindow.changeHeight(1);

		} else if (b == heightDown) {
			timeWindow.changeHeight(-1);

		} else if (b == timerUp) {
			timeWindow.changeTimerSize(2);

		} else if (b == timerDown) {
			timeWindow.changeTimerSize(-2);

		} else if (b == recordUp) {
			timeWindow.changeRecordSize(1);

		} else if (b == recordDown) {
			timeWindow.changeRecordSize(-1);

		} else if (b == nameDown) {
			timeWindow.changeNameSize(-1);

		} else if (b == nameUp) {
			timeWindow.changeNameSize(1);

		} else if (b == sectorDown) {
			record.changeLabelFontSize(-1);
			finalRecordWindow.changeFontSize(-1);
			
		} else if (b == sectorUp) {
			record.changeLabelFontSize(1);
			finalRecordWindow.changeFontSize(1);
			
		} else if (b == nameListDown) {
			record.changeTableFontSize(-1);
			
		} else if (b == nameListUp) {
			record.changeTableFontSize(1);

		} else if (b == sensitivityDown) {

		} else if (b == sensitivityUp) {

		}
	}
}
