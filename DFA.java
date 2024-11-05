import java.util.ArrayList;
import java.util.Random;

public class DFA {
	
	private ArrayList<String> language;			// Keeps track of valid strings accepted by the DFA
	private ArrayList<State> states;			// Keeps track of the states in the DFA
	private ArrayList<Transition> transitions;	// Keeps track of the transitions b/t states in the DFA
	private ArrayList<Character> symbols;		// Valid symbols that make up the language 
	private StringGenerator randomStr;			// Object that generates random strings and keeps track of valid symbols
	private State startState;					// The starting state of the DFA
	private State rejectState;					// The reject state (rejects invalid strings that are not accepted by DFA)
	private int stateCounter;					// Counter used as unique identifiers for states
	
	
	/**
	 * Initializes the DFA objects that accepts valid strings of a language or reject strings that are not part of
	 * the language.
	 * 
	 * @param l An ArrayList containing valid strings accepted by the DFA.
	 */
	 public DFA(ArrayList<String> l) {
		
		this.language = l;
		this.states = new ArrayList<>();
		this.transitions = new ArrayList<>();
		this.symbols = new ArrayList<>();
		this.startState = new State("q_start", false);
		this.rejectState = new State("q_rej", false);
		this.states.add(startState);		// Add the starting state to the list of State objects
		
		// Add the allowable symbols to generate a new string and add existing string to DFA
		for(String str : language) {							// For each string,
			for(int i = 0; 
					i < str.length(); i++) {
				addNewSymbol(str.charAt(i));	// Add the valid symbol to the StringGenerator object
			}
			addStringToDFA(str);	// Add string to the DFA
		}
		
		this.randomStr = new StringGenerator(symbols);	// Initialize the random string generator
	}
	
	/**
	 * DFA interacts with the abstract class, StringGenerator, to add a new symbol to the list of valid symbols of the
	 * language.
	 * 
	 * @param symbol A character from a valid string of the Language.
	 */
	public void addNewSymbol(char symbol) {
		if (symbols.contains(symbol)) {	// If the symbol already exists in the symbols list,
			return;
		}
		symbols.add(symbol);
		transitions.add(new Transition(rejectState, symbol, rejectState)); // reject State loops back to itself
	}
	
	/**
	 * Adds a new string that is randomly generated from the StringGenerator class to the language to be accepted by the 
	 * DFA. 
	 */
	public String addNewString() {
		Random rand = new Random();		// Create Random object
		String newStr;	
		boolean isUnique;
		
		do {	
			newStr = randomStr.generateString(rand.nextInt(10) + 1); 	// Generates a string with a length between 1 and 10 
			isUnique = true;		// Assumes that the string is not a duplicate
			
			for (String str : language) {		// For each string in the language,
				if (str.equals(newStr)) {		// if the string is a duplicate,
					isUnique = false;			// it is no longer unique.
					break;					
				}
			}
		} while(!isUnique);						// Must generate a new string until it is no longer a duplicate.
		
		
		language.add(newStr);					// Now the string can be added to the language
		System.out.println(newStr + " is added to the language!");
		addStringToDFA(newStr);
		return newStr;
	}
	
	/**
	 * Creates and returns a new state in the DFA with a unique identifier.
	 * 
	 * @param id The unique identifier for the state to search for or create.
	 * @param isFinal A boolean value indicating if the state is accepting (true) or non-accepting (false).
	 * @return The found or newly created State object with the specified id and isFinal status.
	 */
	public State addState(String id, boolean isFinal) {
		State newState = new State(id, isFinal);	// Otherwise, state does not exist
		states.add(newState);						// and we need to return a new state 
		return newState;
	}
	
	/**
	 * Returns an existing state or a new state depending on if there exists a transition from current state
	 * to another state triggered by an input specified in 'symbol' argument. If the current state is found
	 * with equivalent input triggers, it checks the states' statuses to determine if they differ and updates
	 * the status to accommodate a new string in the language only if the transition state is not final. 
	 * On the other hand, a transition state is not found and a new state is created with a unique identifier.
	 * 
	 * @param current The current State object used to find the State object to transition to.
	 * @param symbol A char input used to trigger a transition.
	 * @param isFinal A boolean value that determines whether a State object is accepting or non-accepting.
	 * @return A state object that the Current State object transitions to when triggered by a char input.
	 */
	public State getState(State current, char symbol, boolean isFinal) {
		for(Transition transition : transitions) {		// For each transition, check if same start state and input, 
			
			if((transition.getStartState().equals(current)) && (transition.getInput() == symbol)) {			
				// If so, do the states' statuses and match and the transition state isn't already a final?
				
				State nextState = transition.getNextState();
				
				if (!nextState.equals(rejectState)) {
				
					// System.out.println("getState nextState: " + nextState.getId());
				
					if((nextState.isFinal() != isFinal) && (isFinal)) {
						transition.getNextState().setFinal(isFinal);	// If so, change transition state to final state 
					
					}
					return transition.getNextState();		// Return found transition state
				}
			}
		}
		String id = String.format("q_%d", stateCounter);	// State not found, so create unique id
		stateCounter++;										// Increment the number of states
		return addState(id, isFinal);						// Return the newly created state to add to the DFA
	}

	/**
	 * Creates a transition from the starting state to another state based on a input in the DFA. If there 
	 * is an existing transition in the DFA sharing the same starting state and is triggered by the same input, 
	 * it will check if they transition to the same state to determine if this is a duplicate. Otherwise, it 
	 * will determine if the starting state transitions to the reject state. If rejected, the next state will 
	 * be updated to the the state specified in 'to' argument. Otherwise, a new transition will be added to the
	 * DFA. 
	 * 
	 * @param from The State object that is triggered by an input to transition to the next state.
	 * @param symbol The Character input that triggers a transition from one State object to another.
	 * @param to The State object that another State transitions to when a particular input is read by the DFA.
	 */
	public void addTransition(State from, char symbol, State to) {
		// System.out.println("To: " + to.getId());
		
		for (Transition transition : transitions) {					// For each transition,
			if ((from.equals(transition.getStartState())) && (transition.getInput() == symbol)) {	
										// if it starts at the same state and transitions due to the same input
				
				//System.out.println("Matching transition found for state " + from.getId() + " with symbol " + symbol);
				
				State nextState = transition.getNextState();
					
				if (nextState.equals(to)) {		// and it transitions to the same state, it is a duplicate
					// System.out.println("this is a duplicate");
					return;										
				}
				if(nextState.equals(rejectState)) {		// If the next state is a reject state,
					transition.setNextState(to);		// replace reject state with to
					return;
				}
			}
		}
		//System.out.println(String.format("Adding transition: %s --[%c]--> %s", from.getId(), symbol, to.getId()));
		transitions.add(new Transition(from, symbol, to));		// No duplicate transitions, so add new transition
	}
	
	/**
	 * Adds a newly generated string to the language allowing it to be accepted by the DFA. Begins at the starting 
	 * state reading one character at a time checking if a transition state already exists. If not, the next state
	 * is created, then a transition is created. Once all the characters in the string are read, the current state
	 * will be an accepting state. Thus, any other input should transition to the reject state.
	 * 
	 * @param newStr A String object generated at random to be added to the language and accepted by the DFA.
	 */
	public void addStringToDFA(String newStr) {
		State current = startState;				// Set the start state as the current state
		boolean isFinal = false;
		
		for(int i = 0; i < newStr.length(); i++) {	
			if (i == newStr.length() - 1) {		// if this is the last character in the string,
				isFinal = true;					// Make the state final to accept the string
			}
			
			if (current.equals(rejectState)) {
				isFinal = false;
			}
		
			char symbol = newStr.charAt(i);
			
			// Find the next state if already exists or create a new state to transition to
			State nextState = getState(current, symbol, isFinal);
			
			// Find the transition to next state if exists or create new transition to next state
			addTransition(current, symbol, nextState);
			
			// System.out.println(current.getId() + "->" + nextState.getId());
			
			current = nextState;	// Iterate to the next state in the sequence 	
		}		
		
		for(Character symbol : symbols) {		// Go through each symbol, 
			addTransition(current, symbol, rejectState);	// and use it to transition final state to reject state
		}
	}
	
	/**
	 * Displays each component of the DFA. It goes through each state in the states list beginning with the starting 
	 * state and checks if there are any transitions from the current state to display. At the end, it will show how 
	 * the 
	 * reject state loops to itself if any subsequent input after a valid string is accepted.
	 * 
	 */
	public void displayDFA() {
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("DFA States and Transitions:");	
		
		for(State state : states) {		// Display each state
			
			System.out.print(String.format("State: %s ", state.getId()));	// using unique identifier
			
			if (state.isFinal()) {				// as well as the state's status
				System.out.println("(Final)");	
			}
			else {
				System.out.println("(Non-Final)");
			}
			
			for(Transition transition : transitions) {				// Then go through each transition
				if (transition.getStartState().equals(state)) {		// and see if there is any transitions at current state
					System.out.println(String.format("	"
							+ "--[ %c ]--> %s", transition.getInput(), 	
							transition.getNextState().getId()));	// Display those transitions
				}
			}
			
			System.out.println("");
		}
		
		System.out.println(String.format("Reject State: %s", rejectState.getId()));		// Display the reject state
		for(Character symbol : symbols) {									// for each symbol, show how it self loops
			System.out.println(String.format("	--[ %c ]--> %s	(self-loop)", symbol, rejectState.getId()));
		}
		System.out.println("------------------------------------------------------------------------------");
	}
	
	/**
	 * Processes a string to determine if it is part of the language. The DFA begins at the starting state and looks for
	 * a transition for the first character in the string sequence. If it exists, it transitions to the next state and the 
	 * next character in the string is analyzed. The DFA analyzes the string one character at a time until it reaches the end 
	 * of the sequence or rejects the string due to no valid transition. there is a final check to determine whether if the 
	 * current State's status is accepting or non-accepting. If accepting, the string is part of the language. Otherwise, the 
	 * string is rejected. On the other hand, if the current State is the reject State, further inputs will continuously loop 
	 * through the reject state. 
	 * 
	 * @param input A string sequence to be analyzed by the DFA to determine whether it is part of the given language.
	 */
	public void processString(String input) {
		System.out.println("------------------------------------------------------------------------------");
		State current = startState;			// Set the start state as the current state
		System.out.println("Processing the string: " + input);
		
		for (int i = 0; i < input.length(); i++) {	// Iterate through each character in the string
			char symbol = input.charAt(i);	
			
			System.out.println(String.format("Current State: %s, Input: %c", current.getId(), symbol));
			
			State nextState = null; 	// Find the transition for the current symbol
			for(Transition transition : transitions) {		// If at same state and symbol matches the transition input,
				if (current.equals(transition.getStartState()) && symbol == transition.getInput()) {	
					nextState = transition.getNextState();		// We found the transition state
					break;			// and no longer need to iterate through the loop.
				}
			}
			
			if (nextState == null) {		// If no transition state is found, 
				System.out.println("No transition is found, moving to the reject State.");
				current = rejectState;	// transition to reject state due to string not valid 
				break; 
			}
			else {		// Otherwise, a transition exists and we move to the next state
				System.out.println(String.format("--> Next State: %s", nextState.getId()));
				current = nextState;
			}
		}	
		
		// Final check to determine if current State is a final state - if the string is accepted or rejected
		if(current.isFinal()) {
			System.out.println(String.format("Final State: %s (Accepting). The string is accepted.", current.getId()));
		}
		else {
			System.out.println(String.format("Final State: %s (Non-Accepting). The string is rejected.", current.getId()));
		}
		System.out.println("------------------------------------------------------------------------------");
	}	
}
