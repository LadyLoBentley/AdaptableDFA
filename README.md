# AdaptableDFA

DFA Project-Launch - README.txt

1. Overview

The DFA Project-Launch simulates a Deterministic Finite Automata (DFA) that can recognize any given language.

	- Default Language (L} = {abc, acb, bcb,abcaa}

While TestDFA is configured to test this language, the DFA implementation is flexible and adaptable to recognize ant language provided through modifications of the DFA construction. TestDFA demonstrates the DFA’s flexibility by testing with randomly generated strings as inputs.

2. Files

The DFA Project-Launch consists of 5 files:

    I. DFA.java: Manages the DFA logic, including transitions, acceptance conditions, and adding new strings and symbols to the language.

    II. State.java: Represents each state within the DFA, including properties and transitions.

    III. Transition.java: Defines the transitions between states based on input symbols.

    IV. StringGenerator.java: Generates test strings at random to validate DFA recognition abilities.

    V. TestDFA.java: Contains test cases to interact with the DFA serving as the main interface for the user.

3. Class and Method Descriptions

I. DFA.java

Implements the core functionality of the DFA by maintaining states, transitions, symbols, and languages recognized by the DFA. It supports the addition of new strings and symbols, generating random strings, displaying the DFA components, and processing the strings to determine if they are accepted or rejected by the DFA.

     DFA(ArrayList<String> l): Constructor that initializes the DFA with a given list of valid strings and sets up the states,   transitions, and symbols required for processing the language.

    addNewSymbol(char symbol): Adds a new symbol to the list of valid symbols if it is not a duplicate. It also creates a self-loop transition on the reject state for this symbol.

    addNewString(): Generates and adds a new string to the DFA language using the StringGenerator class ensuring that there are no duplicate strings added to the language.

    addState(String id, boolean isFinal): Adds a new state with a unique identifier to the DFA.

    getState(State current, char symbol, boolean isFinal): Returns an existing state that matches the specified parameters as long as it is not a reject state. Otherwise, it returns a new state.

    addTransition(State from, char symbol, State to): Adds a transition from one state to another based on a symbol. If a duplicate is found or starting state transitions to a reject state based on same input symbol, it adjusts the transition accordingly.

    addStringToDFA(String newStr): Adds a specified string to the DFA’s language. For each character in the string sequence, it creates states with necessary transitions marking the last state as accepting.

    displayDFA(): Displays all states and transitions within the DFA.

    processString(String input): Processes an input string character-by-character, checking for transitions between states. If the end state is final, the string is accepted. Otherwise, it is rejected.

II. State.java

Represents a single state within the DFA identified by a unique identifier (stateId). Each State object includes a boolean property to determine whether it is an accepting state or non-accepting state.

    State(): Default constructor for a creating a State object without setting an identifier or final status

    State(String id, boolean isFinal): Initializes a new State object with a unique identifier and a boolean indicating whether it is a final state.

    getId(): Returns true if the State is a final accepting state. Otherwise, returns false.

    setID(String id): Sets or updates the unique identifier for the State object.

    setFinal(boolean isFinal): Changes the final status of the State object marking it as accepting or non-accepting.
 
III. Transition.java

Models a transition between two states within the DFA. Each Transition object specifies a starting state, an input symbol that triggers a transition, and the destination state.

    Transition(State from, char symbol, State to): Constructor that initializes the transition with a starting state, an input symbol, and a destination state.

    getStartState(): Returns the starting State object of the transition.

    getInput(): Returns the char type input symbol that triggers the transition.

    getNextState(): Returns the destination State to which the DFA transitions.

    setNextState(State nextState): Updates the destination state of the transition to a new State object.

    toString(): Provides a String representation of the transition, detailing the start state, input symbol, and destination state used for debugging.

IV. StringGenerator.java

Responsible for generating random strings using the valid symbols defined for the DFA’s language.

    StringGenerator(ArrayList<Character> symbols): Constructor that initializes the StringGenerator object with a specified list of valid symbols for the language.

    generateString(int length): Generates a random string of the specified length using the symbols available in the DFA’s language. 

V. TestDFA.java

Acts as the main testing suite for testing the DFA’s behavior. It includes the predefined strings of the default language, tests the DFA’s response to both valid and invalid strings, and generates new strings to check the flexibility of the DFA.

    main(String[] args): The main function for running tests on the DFA. 

    • Initializes the language by setting up a list of strings {abc, act, bob, abcaa} representing the default language.

    • Initialize the DFA object using the predefined language.

    • Displays the DFA by outputting the current configuration of the DFA.

    • Acceptance Tests: Processes each string in the language to verify that the DFA accepts the strings.

    • Rejection Tests: Processes various invalid strings to ensure the DFA correctly rejects them.

    • Edge Cases: Processes an empty string and strings of unexpected lengths.

    • Generates new, unique strings not initially part of the language, adds them to the DFA, and validates their acceptance. Each additional string updates the components of the DFA which is displayed with each transition.
