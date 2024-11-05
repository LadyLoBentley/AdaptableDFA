
public class Transition {
	
	// public fields
	private State startState;	 // The state from which the transition occurs
	private char input;	 	 	 // The input symbol for the transition
	private State nextState;		 // The state to which the DFA transitions

	/**
	 * Initializes a new Transition object with a starting state, an input that triggers a transition, 
	 * and the State object that the input transitions to. 
	 * 
	 * @param from The starting state of the transition.
	 * @param symbol The symbol that triggers the transition.
	 * @param to The state to transition to. 
	 */
	public Transition (State from, char symbol, State to) {
		this.startState = from;
		this.input = symbol;
		this.nextState = to;
	}
	
	/**
	 * Returns the State object that the transition starts from.
	 * 
	 * @return The starting State object of the transition.
	 */
	public State getStartState() {
		return startState;
	}
	
	/**
	 * Returns the input symbol that triggers the transition. 
	 * 
	 * @return The input symbol as a char.
	 */
	public char getInput() {
		return input;
	}
	
	/**
	 * Returns the State object that the transition leads to.
	 * 
	 * @return The State object to transition to.
	 */
	public State getNextState() {
		return nextState;
	}
	
	/**
	 *  Replaces the state to which the DFA transitions to a new state.
	 * 
	 * @param nextState The desired state user wants to transition to.
	 */
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	
	/**
	 * Returns a String representation of the Transition object. 
	 * 
	 * @return Transition object represented as a String.
	 */
	@Override
	public String toString() {
		return String.format("Transition from %s state on input '%c' to %s state", startState.getId(), 
								input, nextState.getId());
	}	
}