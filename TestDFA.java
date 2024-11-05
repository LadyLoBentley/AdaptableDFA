import java.util.ArrayList;

public class TestDFA {
	
	public static void main(String[] args) {
		
		// Arraylist containing all of the strings that are accepted by the language
		ArrayList<String> language = new ArrayList<>();
		language.add("abc");
		language.add("acb");
		language.add("bcb");
		language.add("abcaa");	
		
		// Initialize the DFA object
		DFA dfa = new DFA(language);	
		
		// Display the current DFA
		dfa.displayDFA();
		
		//Show that the current Strings in the language are accepted by the DFA
		for (String str : language) {
			dfa.processString(str);
		}
		
		// Demonstrate that invalid Strings are rejected by the DFA
		dfa.processString("bbcba");
		dfa.processString("c");
		dfa.processString("cba");
		dfa.processString("baaabaaab");
		
		// Test the DFA with an empty string
		dfa.processString("");
				
		// Test the DFA with Strings that are too short or too long
		dfa.processString("ab");
		dfa.processString("abcc");
			
		// Generate new strings
		String str1 = dfa.addNewString();	// Already added to language in DFA class
		
		// Display the updated DFA and process the randomly generated string
		dfa.displayDFA();
		dfa.processString(str1);	
		
		String str2 = dfa.addNewString();	// Already added to language in DFA class
		dfa.displayDFA();
		dfa.processString(str2);	
	}
}
