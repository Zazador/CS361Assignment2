import java.io.*;
import java.util.Scanner;


public class CovertChannel {

	public static void main(String[] args) throws FileNotFoundException {
		// Create the SecureSystem and take in the passed txt file
		SecureSystem sys = new SecureSystem(args[0]);
		File file2 = new File(args[0]);
		
		// Parse the passed txt file until end, while printing the state after
		// each line
		Scanner scan = new Scanner(file2);
		while (scan.hasNextLine()) {
			String curLine = scan.nextLine();
			byte[] buf = curLine.getBytes();
			
			ByteArrayInputStream input = new ByteArrayInputStream(buf);
			
			int inputByte = input.read();
			
			String inputBit = Integer.toBinaryString(inputByte);
			int inputLength = inputBit.length();
			
			if (inputLength != 8) {
				int moreBits = 8 - inputLength;
				while (inputLength != 8) {
					String zero = "0";
					inputBit = zero.concat(inputBit);
					inputLength = inputBit.length();
				}
			}
			
			System.out.println(inputBit);
			
			
		}
		scan.close();
	}

}
