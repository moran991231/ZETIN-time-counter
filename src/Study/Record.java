package Study;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Record extends JFrame {
	public static final String PATH = "C:\\Users\\moran\\Desktop\\ÀÚÃ¼´ëÈ¸\\";

	// Runner, presentRunner
	public static int presentNumber = 0; // index of presentRunner on name.txt
	public static ArrayList<String> runnerList; // string of name.txt split as "\n"
	public static String[] presentRunner = new String[4]; // {"Univ", "Name", "Robot's name", "sector"}
	public final static int UNIV = 0;
	public final static int NAME = 1;
	public final static int ROBOT_NAME = 2;
	public final static int SECTOR = 3;

	// about Record
	private ArrayList<String> personalRecord;
	private static HashMap<String, String> rankingMap; // use map for predicting duplication
	private static ArrayList<String> rankingList; // after sorting map, ranking is saved in rankingList

	// File I/O
	private File nameFile, personalFile, rankingFile;
	private FileReader fileReader;
	private BufferedReader reader;
	private BufferedWriter writer;

	// swing components:
	private JLabel sectorLabel;
	private JLabel nextRunnerLabel;
	private JScrollPane scroll;
	private JTable nameTable;

	// about JTable
	private DefaultTableModel model;
	private Vector<String> columnVec;
	private int labelFontSize = 25;
	private int tableFontSize = 13;
	private Font labelFont = new Font("¸¼Àº °íµñ", Font.BOLD, 25);
	private Font tableFont = new Font("¸¼Àº °íµñ", Font.PLAIN, 13);

	//object variable of another class
	private TimeWindow timeWindow;

	
	// Generator
	public Record(TimeWindow t) throws IOException {

		setSize(300, 400);
		setLocation(0, 620);
		setTitle("Name List Window: Record");
		
		// north: sectorLabel
		sectorLabel = new JLabel("<ºÎ¹®¸í>");
		sectorLabel.setHorizontalAlignment(JLabel.CENTER);
		sectorLabel.setFont(labelFont);
		add(sectorLabel, BorderLayout.NORTH);

		// south: nextRunnerLabel
		nextRunnerLabel = new JLabel("´ÙÀ½ ¼±¼ö: È«±æµ¿");
		nextRunnerLabel.setHorizontalAlignment(JLabel.CENTER);
		nextRunnerLabel.setFont(labelFont);
		add(nextRunnerLabel, BorderLayout.SOUTH);

		// center: nameTable
		columnVec = new Vector<String>();
		columnVec.addElement("  ");
		columnVec.addElement("ºÎ¹®");
		columnVec.addElement("¼Ò¼Ó");
		columnVec.addElement("ÀÌ¸§");
		columnVec.addElement("   ");

		model = new DefaultTableModel(columnVec, 0);

		scroll = new JScrollPane(nameTable);
		nameTable = new JTable(model);
		nameTable.setFont(tableFont);
		add(scroll);

		
		// about runner data, record, ranking
		this.timeWindow = t;
		nameFile = new File(PATH + "name.txt");
		personalRecord = new ArrayList<String>();

		readRunners(); // read name.txt and set runnerList
		setPresentRunner(0);

		// ranking file must be initialized after readRunners();
		// and everytime changing runner, ranking file, map, list must be initialize.
		rankingFile = new File(PATH + "ranking_" + presentRunner[SECTOR] + ".txt");
		rankingMap = new HashMap<String, String>();
		rankingList = new ArrayList<String>();

		timeWindow.setNames(presentRunner);

		readRecord();
		showRecord();

		readRanking();
		showRanking();

		refreshTable();

		setVisible(true);

	} // generator end
	

	// Set method
	public void setPresentRunner(int index) {
		// set presentNumber and presentRunner data as 'index'th Runner's on runnerList
		presentNumber = index;
		presentRunner = runnerList.get(index).split("\\s"); // "\\s" means space (blank)
	}

	
	// Change methods
	public void changeLabelFontSize(int d) {
		if (labelFontSize + d > 0)
			labelFontSize += d;
		labelFont = new Font("¸¼Àº °íµñ", Font.BOLD, labelFontSize);
		sectorLabel.setFont(labelFont);
		nextRunnerLabel.setFont(labelFont);
	}

	public void changeTableFontSize(int d) {
		if (tableFontSize + d > 0)
			tableFontSize += d;
		tableFont = new Font("¸¼Àº °íµñ", Font.PLAIN, tableFontSize);
		nameTable.setFont(tableFont);
		nameTable.getTableHeader().setFont(tableFont);
		nameTable.setRowHeight((int) (tableFontSize * 2.0f));
		setVisible(true);
	}

	
	// Read methods
	public void readRunners() { // read name.txt, and set the runnerList
		runnerList = new ArrayList<String>();
		
		try {
			fileReader = new FileReader(nameFile);
			reader = new BufferedReader(fileReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String s = "";
		try {
			while ((s = reader.readLine()) != null)
				runnerList.add(s);
			fileReader.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readRecord() { // read personalRecord from file
		personalFile = new File(PATH + presentRunner[NAME] + ".txt");
		
		try {
			fileReader = new FileReader(personalFile);
			reader = new BufferedReader(fileReader);

			// I don't understand know below sentence.........
			personalRecord = reader.lines().collect(Collectors.toCollection(ArrayList::new));
			reader.close();
			
		} catch (IOException e) {
			
			try {
				personalFile.createNewFile();
			} catch (IOException e1) { }
			readRecord();
			personalRecord = new ArrayList<String>();

		}
	}

	public void readRanking() { // read ranking and save at rankingList
		try {
			reader = new BufferedReader(new FileReader(rankingFile));
			rankingList = reader.lines().collect(Collectors.toCollection(ArrayList::new));
			String[] strArr;

			for (String data : rankingList) {
				strArr = data.split("\\s");
				rankingMap.put(strArr[1], strArr[0]);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reread() {  //reread name.txt and other data
		readRunners();
		setPresentRunner(Record.presentNumber);
		readRecord();
		readRanking();
		showRecord();
		showRanking();
		refreshTable();
		setVisible(true);
	}

	
	// Show methods
	public void showRecord() { // show List of personalRecord on TextArea on TimeWindow
		timeWindow.setRecordTextArea("");

		for (String s : personalRecord) {
			timeWindow.appendRecordTextArea(String.format("%s\n", s));
		}
	}

	public void showRanking() { // show rankingList on TextArea of TimeWindow
		timeWindow.setRankingTextArea("<" + presentRunner[SECTOR] + " ºÎ¹® ·©Å·>\n");
		int i = 1;
		for (String s : rankingList) {
			timeWindow.appendRankingTextArea(String.format("%-2dÀ§ %s \n", i, s));
			i++;
		}
	}

	
	// Add methods
	public void addRecord(String str) { // add str to personalRecord, sort, save, and show on TextArea
		personalRecord.add(str);
		Collections.sort(personalRecord);
		personalRecordSave();
		showRecord();
	}

	public void addToRanking() {
		/*
		 * add the shortest personalRecord to RankingMap and List use this method when
		 * refresh ranking, too
		 */
		try {
			rankingMap.put(presentRunner[NAME], personalRecord.get(0));
		} catch (IndexOutOfBoundsException e) {
			rankingMap.remove(presentRunner[NAME]);
			System.out.println(presentRunner[NAME] + " ±â·Ï ¾ø¾î¼­ ·©Å·¿¡¼­ Áö¿ò");
		}

		// input ranking to rankingList in order
		rankingList = new ArrayList<String>();
		List<String> keySetList = new ArrayList<>(rankingMap.keySet());

		// lambda expression
		Collections.sort(keySetList, (o1, o2) -> (rankingMap.get(o1).compareTo(rankingMap.get(o2))));

		for (String key : keySetList) {
			rankingList.add(rankingMap.get(key) + " " + key);
		}
		rankingSave();
		showRanking();
	}
	
	public void addRunnersToTable() {
		model = new DefaultTableModel(columnVec, 0);
		int listSize = runnerList.size();
		String[] iArr;

		for (int i = 0; i < listSize; i++) {
			iArr = runnerList.get(i).split("\\s");

			Vector<String> iVec = new Vector<String>(5);
			iVec.add(i + "");
			iVec.add(iArr[SECTOR]);
			iVec.add(iArr[UNIV]);
			iVec.add(iArr[NAME]);
			if (i == presentNumber) {
				iVec.add("ÇöÀç");
				if (i < listSize - 1)
					nextRunnerLabel.setText("´ÙÀ½: " + runnerList.get(i + 1).split("\\s")[1]);
				else if (i == listSize - 1)
					nextRunnerLabel.setText("´ÙÀ½:   -  ");

			} else
				iVec.add("");
			model.addRow(iVec);

		}

		remove(scroll);
		nameTable = new JTable(model);
		nameTable.setFont(tableFont);
		scroll = new JScrollPane(nameTable);
		add(scroll);
		setVisible(true);
	}

	
	// RefreshTable
	public void refreshTable() {
		sectorLabel.setText("<" + presentRunner[SECTOR] + ">");
		addRunnersToTable();
		nameTable.setRowHeight(tableFontSize + 2);
	}

	
	// Change or Select presentRunnerdata (Number and Runner)
	public void changeRunner(int d) { // for Pre/Next Runner Button on ButtonWindow
		if ((TimerDownThread.isActive == false) && (TimerUpThread.isActive == false))
			if ((presentNumber + d >= 0) && (presentNumber + d < runnerList.size())) {
				presentNumber += d;
				personalRecord = new ArrayList<String>();
				setPresentRunner(presentNumber);
				timeWindow.setNames(presentRunner);
				timeWindow.timeTextReset();
				readRecord();
				showRecord();

				rankingFile = new File(PATH + "ranking_" + presentRunner[SECTOR] + ".txt");
				rankingMap = new HashMap<String, String>();
				rankingList = new ArrayList<String>();
				readRanking();
				showRanking();

				refreshTable();
			}
	}

	public void selectRunner(int index) { // for runnerComoBox on ButtonWindow
		
		if ((TimerDownThread.isActive == false) && (TimerUpThread.isActive == false)) {
			if (0 <= index && index <= runnerList.size()) {
				personalRecord = new ArrayList<String>();
				setPresentRunner(index);

				refreshTable();

				timeWindow.setNames(presentRunner);
				timeWindow.timeTextReset();
				readRecord();
				showRecord();

				rankingFile = new File(PATH + "ranking_" + presentRunner[SECTOR] + ".txt");
				rankingMap = new HashMap<String, String>();
				rankingList = new ArrayList<String>();
				readRanking();
				showRanking();
			}
		}
	}

	
	// Save methods
	public void personalRecordSave() {
		try {
			writer = new BufferedWriter(new FileWriter(PATH + presentRunner[NAME] + ".txt"));
			for (String s : personalRecord) {
				writer.append(s + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rankingSave() { // save rankingList as ranking_(sector name).txt
		try {
			writer = new BufferedWriter(new FileWriter(rankingFile));
			for (String s : rankingList) {
				writer.append(s + "\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	// Remove method
	public void removeRecord(String str) {
		
		if (personalRecord.contains(str)) {
			personalRecord.remove(str);
			personalRecordSave();
			showRecord();
		}
	}


	// Shortest ~~
	public String getShortestPersonal(String name) {
		if (rankingMap.get(name) != null)
			return rankingMap.get(name);
		else
			return "-";
	}


	// Compare methods
	public static int compareWithRanking(String time) {
		// compare String time with ranking and return the time's placing(ranking)
		int state = 0;
		try {
			for (int i = 0; i <= 4; i++) 
				if (time.compareTo(rankingList.get(i)) >= 0)
					state++;
			
		} catch (IndexOutOfBoundsException e) {	}
		
		return state;
	}

}
