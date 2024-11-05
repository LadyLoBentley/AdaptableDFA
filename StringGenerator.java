import java.util.ArrayList;
import java.util.Random;

public class StringGenerator {
	
	// public methods
	ArrayList<Character> symbols;		// ArrayList containing valid symbols in a language	
	
	/**
	 * Initializes a new StringGenerator object with an ArrayList of symbols permitted by
	 * the language to generate a random string.
	 **/
	public StringGenerator(ArrayList<Character> symbols) {
		this.symbols = symbols;	// Initialize the string generator
	}
	
	/**
	 * Generates a random string using the specified symbols in the language. The length of 
	 * the string is specified by user. 
	 * 
	 * @param length Length of the random string to be generated.
	 * @return a String created at random to be tested by the DFA to determine if the String
	 * belongs to the language.
	 */
	public String generateString(int length) {
		String randomString = "";		
		Random random = new Random();
		
		for (int i = 0; i < length; i++) {	// Add a character at each iteration to the string
			int index = random.nextInt(symbols.size());	// Choose a random index to find symbol,
			randomString += symbols.get(index);	// Then concatenate the symbol to string. 
		}
		
		return randomString;	// Return the randomly generated string to be tested by DFA.
	}
}