import java.io.*;
import java.util.*;

public class CovertChannel {

	static long numOfBits = 0;
	static boolean verbose;

	// static String log = "";

	public static void main(String[] args) throws IOException {
		File file2;
		if (args[0].equals("v")) {
			System.out.println("VERBOSE");
			file2 = new File(args[1]);
			verbose = true;
		} else {
			file2 = new File(args[0]);
			verbose = false;
		}

		SecureSystem sys = new SecureSystem(args[0]);

		// Create low and high Security Levels
		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		// Create Lyle and Hal
		sys.createSubject("LYLE", low);
		sys.createSubject("HAL", high);

		// Parse the passed txt file until end, while printing the state after
		// each line
		Scanner scan = new Scanner(file2);
		String fileName = file2.getName() + ".out";
		String logName = "log.txt";
		byte[] newLine = System.getProperty("line.separator").getBytes();
		FileOutputStream fos = new FileOutputStream(fileName);
		FileOutputStream logger = new FileOutputStream(logName);
		final long startTime = System.currentTimeMillis();

		// While file has next line
		while (scan.hasNextLine()) {
			String curLine = scan.nextLine();
			byte[] buf = curLine.getBytes();

			ByteArrayInputStream input = new ByteArrayInputStream(buf);
			int numOfBytes = input.available();

			// While there are still bytes in the file to be parsed
			while (numOfBytes > 0) {
				int inputByte = input.read();
				String inputBit = Integer.toBinaryString(inputByte);
				int inputLength = inputBit.length();
				numOfBits += 8;

				// Make all bytes length 8 by appending any needed 0's
				if (inputLength != 8) {
					while (inputLength != 8) {
						String zero = "0";
						inputBit = zero.concat(inputBit);
						inputLength = inputBit.length();
					}
				}

				// Based on bit being 0 or 1, run appropriate instructions
				for (int i = 0; i < inputLength; i++) {
					if (inputBit.charAt(i) == '0') {
						String[] instr = { "CREATE HAL OBJ", "CREATE LYLE OBJ",
								"WRITE LYLE OBJ 1", "READ LYLE OBJ",
								"DESTROY LYLE OBJ", "RUN LYLE" };
						// If verbose flag is on, write to log
						if (verbose) {
							String log = Arrays.toString(instr);
							byte[] logResult = log.getBytes();
							logger.write(logResult);
							logger.write(newLine);
						}
						SecureSystem.passInstructions(instr);
					} else {
						String[] instr = { "CREATE LYLE OBJ",
								"WRITE LYLE OBJ 1", "READ LYLE OBJ",
								"DESTROY LYLE OBJ", "RUN LYLE" };
						// If verbose flag is on, write to log
						if (verbose) {
							String log = Arrays.toString(instr);
							byte[] logResult = log.getBytes();
							logger.write(logResult);
							logger.write(newLine);
						}
						SecureSystem.passInstructions(instr);
					}
				}
				// Write byte to out file, then reset the run manager for the
				// next byte
				numOfBytes--;
				String result = ReferenceMonitor.getResultLine();
				byte[] resultArray = result.getBytes();
				fos.write(resultArray);
				ReferenceMonitor.getRunManager().put("LYLE", "temp");
			}
			fos.write(newLine);
		}
		final long endTime = System.currentTimeMillis();

		// Compute bandwidth
		double bytes = file2.length();
		long bandwidth = numOfBits / (endTime - startTime);
		System.out.println("Document: " + file2.getName());
		System.out.println("Size: " + bytes + " bytes");
		System.out.println("Bandwidth: " + bandwidth + " bits/ms");
		scan.close();
		fos.close();
		logger.close();
	}

}
