import java.util.HashMap;

public class ReferenceMonitor {

	static HashMap<String, String> runManager = new HashMap<String, String>();
	static String resultLine;

	public ReferenceMonitor() {
		// Initially add a blank Hal and Lyle to the read manager
		ObjectManager.getReadManager().put("HAL", 0);
		ObjectManager.getReadManager().put("LYLE", 0);
		runManager.put("HAL", "temp");
		runManager.put("LYLE", "temp");
	}

	// Create a new object within the object manager
	static void createNewObject(String name, SecurityLevel secLev) {
		ObjectManager.createNewObject(name, secLev);
	}

	// Return Hashmap that maintains the RUN call
	public static HashMap<String, String> getRunManager() {
		return runManager;
	}

	// Return a completed byte in a string form
	public static String getResultLine() {
		return resultLine;
	}

	// Check to see if the passed write instruction is safe based on security
	// principles. If so, send to object manager for execution
	static void writeExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		SecurityLevel subjSec = SecureSystem.getSubjectManager().get(subj);
		int subjSecLevel = subjSec.getDomination();

		SecurityLevel objSec = ObjectManager.getObjectManager().get(obj);
		int objSecLevel = objSec.getDomination();

		if (subjSecLevel <= objSecLevel) {
			ObjectManager.writeExecute(instr);
		}
	}

	// Check to see if the passed read instruction is safe based on security
	// principles. If so, send to object manager for execution
	static void readExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		SecurityLevel subjSec = SecureSystem.getSubjectManager().get(subj);
		int subjSecLevel = subjSec.getDomination();

		SecurityLevel objSec = ObjectManager.getObjectManager().get(obj);
		int objSecLevel = objSec.getDomination();

		if (subjSecLevel >= objSecLevel) {
			ObjectManager.readExecute(instr);
		} else {
			ObjectManager.badReadExecute(instr);
		}
	}

	// Create a new object
	static void createExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		SecurityLevel subjSec = SecureSystem.getSubjectManager().get(subj);
		createNewObject(instr.getObject(), subjSec);
	}

	// Execute the RUN call.
	static void runExecute(InstructionObject instr) {
		int temp = ObjectManager.getReadManager().get(instr.getSubject());
		String curBit = runManager.get(instr.getSubject());
		// If first bit for the byte
		if (curBit.equals("temp")) {
			if (temp != 0) {
				curBit = "1";
				runManager.put(instr.getSubject(), curBit);
			} else {
				curBit = "0";
				runManager.put(instr.getSubject(), curBit);
			}
		}
		// Otherwise, make sure less than 8 bits
		else if (curBit.length() < 8) {
			if (temp != 0) {
				curBit = curBit.concat("1");
				runManager.put(instr.getSubject(), curBit);
			} else {
				curBit = curBit.concat("0");
				runManager.put(instr.getSubject(), curBit);
			}
		}
		// If 8 bits, we have a byte so make that the result string
		if (curBit.length() == 8) {
			resultLine = curBit;
			int charTemp = Integer.parseInt(resultLine, 2);
			resultLine = new Character((char) charTemp).toString();
		}

	}

	// Execute the DESTROY call. Check to make sure subject has the correct
	// SecurityLevel
	static void destroyExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		String obj = instr.getObject();

		SecurityLevel subjSec = SecureSystem.getSubjectManager().get(subj);
		int subjSecLevel = subjSec.getDomination();

		SecurityLevel objSec = ObjectManager.getObjectManager().get(obj);
		int objSecLevel = objSec.getDomination();

		if (subjSecLevel <= objSecLevel) {
			ObjectManager.destroyExecute(instr);
		} else {
			System.out
					.println("This destroy call is invalid and did not occur!");
		}
	}

}
