import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class SecureSystem {
	ReferenceMonitor refMon = new ReferenceMonitor();
	static InstructionObject instrobj;
	static HashMap<String, SecurityLevel> subjectManager = new HashMap<String, SecurityLevel>();

	public static void main(String[] args) throws FileNotFoundException {

		// Create the SecureSystem and take in the passed txt file
		SecureSystem sys = new SecureSystem(args[0]);
		File file2 = new File(args[0]);

		// Create low and high Security Levels
		SecurityLevel low = SecurityLevel.LOW;
		SecurityLevel high = SecurityLevel.HIGH;

		// Create Lyle and Hal
		sys.createSubject("Lyle", low);
		sys.createSubject("Hal", high);

		// Create LObj and HObj
		sys.getReferenceMonitor().createNewObject("LObj", low);
		sys.getReferenceMonitor().createNewObject("HObj", high);

		// Parse the passed txt file until end, while printing the state after
		// each line
		Scanner scan = new Scanner(file2);
		while (scan.hasNextLine()) {
			String curLine = scan.nextLine();
			instrobj = new InstructionObject(curLine);
			printState();
		}
		scan.close();
	}

	// SecureSystem constructor
	public SecureSystem(String fileName) throws FileNotFoundException {
		File file1 = new File(fileName);
		System.out.println("Reading from file: " + file1);
		System.out.println();
	}

	// Constructor for a subject manager
	void createSubject(String name, SecurityLevel secLev) {
		subjectManager.put(name, secLev);
	}

	// Returns the subject manager
	public static HashMap<String, SecurityLevel> getSubjectManager() {
		return subjectManager;
	}

	// Returns the reference monitor
	public ReferenceMonitor getReferenceMonitor() {
		return refMon;
	}

	// Prints the status of all the objects and subjects after each line of the
	// txt
	static void printState() {
		if (instrobj.getInstruction().equals("BAD")) {
			System.out.println("Bad Instruction");
			System.out.println("The current state is: ");
			System.out.println("   LObj has value: "
					+ ObjectManager.getValueManager().get("LObj"));
			System.out.println("   HObj has value: "
					+ ObjectManager.getValueManager().get("HObj"));
			System.out.println("   Lyle has recently read: "
					+ ObjectManager.getReadManager().get("Lyle"));
			System.out.println("   Hal has recently read: "
					+ ObjectManager.getReadManager().get("Hal"));
			System.out.println();
		} else if (instrobj.getInstruction().equals("READ")) {
			System.out.println(instrobj.getSubject() + " reads "
					+ instrobj.getObject());
			System.out.println("The current state is: ");
			System.out.println("   LObj has value: "
					+ ObjectManager.getValueManager().get("LObj"));
			System.out.println("   HObj has value: "
					+ ObjectManager.getValueManager().get("HObj"));
			System.out.println("   Lyle has recently read: "
					+ ObjectManager.getReadManager().get("Lyle"));
			System.out.println("   Hal has recently read: "
					+ ObjectManager.getReadManager().get("Hal"));
			System.out.println();
		} else if (instrobj.getInstruction().equals("WRITE")) {
			System.out.println(instrobj.getSubject() + " writes value "
					+ instrobj.getValue() + " to " + instrobj.getObject());
			System.out.println("The current state is: ");
			System.out.println("   LObj has value: "
					+ ObjectManager.getValueManager().get("LObj"));
			System.out.println("   HObj has value: "
					+ ObjectManager.getValueManager().get("HObj"));
			System.out.println("   Lyle has recently read: "
					+ ObjectManager.getReadManager().get("Lyle"));
			System.out.println("   Hal has recently read: "
					+ ObjectManager.getReadManager().get("Hal"));
			System.out.println();
		} else if (instrobj.getInstruction().equals("CREATE")) {
			System.out.println(instrobj.getSubject() + " creates object "
					+ instrobj.getObject());
			System.out.println("The current state is: ");
			System.out.println("   LObj has value: "
					+ ObjectManager.getValueManager().get("LObj"));
			System.out.println("   HObj has value: "
					+ ObjectManager.getValueManager().get("HObj"));
			System.out.println("   Lyle has recently read: "
					+ ObjectManager.getReadManager().get("Lyle"));
			System.out.println("   Hal has recently read: "
					+ ObjectManager.getReadManager().get("Hal"));
			System.out.println();
			System.out.println("The current ObjectManager is: ");
			for (String name: ObjectManager.getObjectManager().keySet()) {
				int value = ObjectManager.getObjectManager().get(name).getDomination();
				System.out.println(name + " " + value);
			}
		} else if (instrobj.getInstruction().equals("DESTROY")) {
			System.out.println(instrobj.getSubject() + " destroys object "
					+ instrobj.getObject());
			System.out.println("The current state is: ");
			System.out.println("   LObj has value: "
					+ ObjectManager.getValueManager().get("LObj"));
			System.out.println("   HObj has value: "
					+ ObjectManager.getValueManager().get("HObj"));
			System.out.println("   Lyle has recently read: "
					+ ObjectManager.getReadManager().get("Lyle"));
			System.out.println("   Hal has recently read: "
					+ ObjectManager.getReadManager().get("Hal"));
			System.out.println();
			System.out.println("The current ObjectManager is: ");
			for (String name: ObjectManager.getObjectManager().keySet()) {
				int value = ObjectManager.getObjectManager().get(name).getDomination();
				System.out.println(name + " " + value);
			}
		}
		System.out.println();
	}
}
