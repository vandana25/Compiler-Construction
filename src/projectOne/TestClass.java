package projectOne;

import java.util.List;
import java.util.Map;
/***
 * 
 * @author vandana
 * email: vandana25chauhan@gmail.com
 * This class drives the application.
 *
 */
public class TestClass {
	public static void main(String[] args) {
		Lexer lexer = new Lexer();
		//Assuming that the file is present at the root of the project directory.	
		Map<String, Integer> lexiconMap = new Lexicon().createLexiconFromFile("token.txt");
		List<LexPojo> output =  lexer.processInput(lexiconMap);
		for(LexPojo thisPojo : output) {
			System.out.println(thisPojo);
		}
	}
}
