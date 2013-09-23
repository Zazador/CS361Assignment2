import java.io.*;
import java.util.*;

public class CovertChannel {

	public static void main(String[] args) throws IOException {
		// Create the SecureSystem and take in the passed txt file
		File file2 = new File(args[0]);
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
		byte[] newLine = System.getProperty("line.separator").getBytes();
		FileOutputStream fos = new FileOutputStream(fileName);
		while (scan.hasNextLine()) {
			String curLine = scan.nextLine();
			byte[] buf = curLine.getBytes();

			ByteArrayInputStream input = new ByteArrayInputStream(buf);
			int numOfBytes = input.available();
			


			while (numOfBytes > 0) {
				int inputByte = input.read();
				String inputBit = Integer.toBinaryString(inputByte);
				int inputLength = inputBit.length();

				if (inputLength != 8) {
					while (inputLength != 8) {
						String zero = "0";
						inputBit = zero.concat(inputBit);
						inputLength = inputBit.length();
					}
				}

				for (int i = 0; i < inputLength; i++) {
					if (inputBit.charAt(i) == '0') {
						String[] instr = { "CREATE HAL OBJ", "CREATE LYLE OBJ",
								"WRITE LYLE OBJ 1", "READ LYLE OBJ",
								"DESTROY LYLE OBJ", "RUN LYLE" };
						SecureSystem.passInstructions(instr);
					} else {
						String[] instr = { "CREATE LYLE OBJ",
								"WRITE LYLE OBJ 1", "READ LYLE OBJ",
								"DESTROY LYLE OBJ", "RUN LYLE" };
						SecureSystem.passInstructions(instr);
					}
				}
				numOfBytes--;
				String result = ReferenceMonitor.getResultLine();
				byte[] resultArray = result.getBytes();
				fos.write(resultArray);
				ReferenceMonitor.getRunManager().put("LYLE", "temp");
			}
			fos.write(newLine);
		}
		scan.close();
		fos.close();
	}

}
