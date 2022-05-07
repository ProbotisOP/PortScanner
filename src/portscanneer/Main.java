package portscanneer;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements Runnable {

	int start;
	int end;
	 public static final String GREEN = "\033[0;32m";   // GREEN
	public void run() {

		Scanner s = new Scanner(System.in);
		System.out.println("Enter the host IP");
		String ip = s.nextLine();

		if (isValidIPAddress(ip)) {

			System.out.println("Enter the PORT range : ");
			System.out.println("eg: 21 - 443 ");

			String range = "";
			range = s.nextLine();

			String[] starting = range.split("-");
			System.out.println();

			try {
				start = Integer.parseInt(starting[0]);
				end = Integer.parseInt(starting[1]);

			} catch (java.lang.NumberFormatException e) {
				System.out.println("Please Enter the port range Correctly ");
				System.out.println("eg: 21 - 443  (only single hyphen)");
			}

			System.out.println("STARTING SCAN....");

			for (int i = start; i < end; i++) {
				try {
					Socket socket = new Socket();
					socket.connect(new InetSocketAddress(ip, i), 200);
					socket.close();
					System.out.println("port " + i + " is OPEN");
				} catch (Exception ex) {

				}
			}

			System.out.println("Done");

		}

		else {
			System.out.println("Please Enter the correct IP ");
		}
	}

	// CREDIT : geeksforgeeks
	public static boolean isValidIPAddress(String ip) {

		// Regex for digit from 0 to 255.
		String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";

		// Regex for a digit from 0 to 255 and
		// followed by a dot, repeat 4 times.
		// this is the regex to validate an IP address.
		String regex = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;

		// Compile the ReGex
		Pattern p = Pattern.compile(regex);

		// If the IP address is empty
		// return false
		if (ip == null) {
			return false;

		}

		Matcher m = p.matcher(ip);

		// Return if the IP address
		// matched the ReGex
		return m.matches();
	}

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

		Main m = new Main();

		service.execute(() -> {
			m.run();
			;
		});

	}

}
