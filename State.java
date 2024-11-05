
public class State {
	
	// private fields
	private String stateId;		// Identify the state 
	private boolean isFinal;	// Determines if the state is final
	
	public State() {}
	
	/**
	 * Initializes a new State object with a unique state identifier and 
	 * a boolean to determine if it is a final state.
	 * 
	 * @param id Unique identifier for the state
	 * @param isFinal A boolean indicating whether this state is an accepting state
	 */
	public State(String id, boolean isFinal) {
		this.stateId = id;
		this.isFinal = isFinal;
	}
	
	/**
	 * Returns the unique identifier of the State object.
	 * 
	 * @return The state identifier as a String.
	 */
	public String getId() {
		return stateId;
	}
	
	/**
	 * Determines if the State object is a final (accepting) state.
	 * 
	 * @return true if the state is final. Otherwise, returns false. 
	 */
	public boolean isFinal() {
		return isFinal;
	}
	
	/**
	 * Sets a new unique identifier for the State object.
	 * 
	 * @param id The new state identifier to replace the current state identifier. 
	 */
	public void setID(String id) {
		stateId = id;
	}
	
	/**
	 * Changes whether the state is final or not.
	 * 
	 * @param isFinal A boolean determining whether the state is an accepting state or not.
	 */
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
}
