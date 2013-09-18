public class ReferenceMonitor {

	public ReferenceMonitor() {
		// Initially add a blank Hal and Lyle to the read manager
		ObjectManager.getReadManager().put("Hal", 0);
		ObjectManager.getReadManager().put("Lyle", 0);
	}

	// Create a new object within the object manager
	static void createNewObject(String name, SecurityLevel secLev) {
		ObjectManager.createNewObject(name, secLev);
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

	static void createExecute(InstructionObject instr) {
		String subj = instr.getSubject();
		SecurityLevel subjSec = SecureSystem.getSubjectManager().get(subj);
		createNewObject(instr.getObject(), subjSec);
	}
	
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
			System.out.println("This destroy call is invalid and did not occur!");
		}
	}

}
