public class SecurityLevel {

	int domination;
	static SecurityLevel HIGH = new SecurityLevel(1);
	static SecurityLevel LOW = new SecurityLevel(0);

	// Creates a security level
	public SecurityLevel(int level) {
		domination = level;
	}

	// Returns the domination for checking security levels later for who
	// dominates
	int getDomination() {
		return domination;
	}

}
