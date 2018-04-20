package projectOne;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/***
 * 
 * @author Vandana
 * email: vandana25chauhan@gmail.com
 * This class creates Lexicon as a map from input file.
 */
public class Lexicon {
	/***
	 * method creates a map containing all lexicons from File
	 * @param fileName to read lexicons from
	 * @return
	 */
	public static Map<Integer, String> tokenIdVsTokenNameMap;
	public Lexicon() {
		tokenIdVsTokenNameMap = new HashMap<Integer, String>();
	}
	
	
	public static Map<Integer, String> getTokenIdVsTokenNameMap() {
		return tokenIdVsTokenNameMap;
	}


	public Map<String, Integer> createLexiconFromFile(String fileName) {
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		
		Scanner sc = null;
		try {
			File file = new File(fileName);
			sc = new Scanner(file);
			String thisLine = "";
			while(sc.hasNextLine()) {
				thisLine = sc.nextLine();
				String words[] = thisLine.split("\\s+");
				String temp = words[words.length-1];
				int value = Integer.parseInt(words[0]);
				String key = temp.substring(1, temp.length()-1); 
				//
				String tokenName = words[1];
				tokenIdVsTokenNameMap.put(value, tokenName);
				//
				myMap.put(key, value);
			}
			System.out.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
		return myMap;
	}
}
