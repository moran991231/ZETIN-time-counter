package Study;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Study.ButtonWindow;

public class Study {
	static boolean isG = false;
	static TimerUpThread TimerUpThread;
	public static ButtonWindow buttonWindow;
	final static Color CORAL = new Color(255, 102, 102);

	public Study() throws IOException {
		buttonWindow = new ButtonWindow();
	}

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			// 클래스 이름을 식별자로 사용하여 포트 오픈
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				// 포트 설정(통신속도 설정. 기본 9600으로 사용)
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				// Input,OutputStream 버퍼 생성 후 오픈
				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

				// 읽기, 쓰기 쓰레드 작동
				(new Thread(new SerialReader(in))).start();
				(new Thread(new SerialWriter(out))).start();

			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	// String to Hex String
	public static String stringToHex0x(String s) {
		String result = "";

		for (int i = 0; i < s.length(); i++) {
			result += String.format("0x%02X ", (int) s.charAt(i));
		}

		return result;
	}

	/** */
	// 데이터 수신

	/*
	 * public static class SerialReader implements Runnable { InputStream in;
	 * 
	 * public SerialReader ( InputStream in ) { this.in = in; }
	 * 
	 * public void run () { byte[] buffer = new byte[1024]; int len = -1; try {
	 * while ( ( len = this.in.read(buffer)) > -1 ) { System.out.print(new
	 * String(buffer,0,len)); } } catch ( IOException e ) { e.printStackTrace(); } }
	 * }
	 */

	public static class SerialReader implements Runnable {
		InputStream in;
		String str = "";
		String strText = "";

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			String text;

			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					text = new String(buffer, 0, len);
					System.out.print(text);

					if (text.equals("G")) {
						isG = false;
					}

					if (isG)
						if (!text.isEmpty())
							str = str + text;

					if (text.contains("S")) {
						System.out.println(" Start");
						buttonWindow.startCountUp.doClick(1);
						str = "";
					}
					if (text.contains("E")) {
						buttonWindow.timeWindow.stopCountUp();
						isG = true;
					}
					if (text.contains("F")) {
						System.out.println(" Fitin");
						buttonWindow.timeWindow.setTrueTime(str);
						buttonWindow.saveRecord.doClick(1);
					}
					if (text.contains("N") || text.contains("R")) {
						System.out.println(" Notfitin");
						buttonWindow.timeWindow.timeDrivingLabel.setBackground(CORAL);
						buttonWindow.stopCountUp.doClick(1);
					}


				}
			}


			catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/** */
// 데이터 송신
	public static class SerialWriter implements Runnable {
		OutputStream out;

		public SerialWriter(OutputStream out) {
			this.out = out;

		}

		public void run() {
			try {
				int c = 0;
				while ((c = System.in.read()) > -1) {
					this.out.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		try {
			(new Study()).connect("COM3"); // 입력한 포트로 연결
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
