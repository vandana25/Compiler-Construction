package projectTwo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import projectOne.LexPojo;
import projectOne.Lexer;
import projectOne.Lexicon;

public class Parser {
	public static Map<String, Integer> lexiconMap;
	public static List<LexPojo> lexPojoList;
	
	public Parser() {
		lexiconMap = new Lexicon().createLexiconFromFile("token.txt");
		lexPojoList = new ArrayList<LexPojo>();
	}
	 
	public static Map<String, Integer> getLexiconMap() {
		return lexiconMap;
	}
	
	public static List<LexPojo> getLexPojoList() {
		return lexPojoList;
	}

	protected void parseUserInput(String fileName) {
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			String thisLine = "";
			int lineNumber=1;
			
			
////			thisLine = sc.nextLine();
//			String words[] = thisLine.split("\\s+");
//			String temp = words[words.length-1];
			
			Lexer lexer = new Lexer();
//			Map<String, Integer> lexiconMap = new Lexicon().createLexiconFromFile("token.txt");
			//create lexPojo list.
			while(sc.hasNextLine() && !(thisLine = sc.nextLine()).equals("")) {
				//this will create a list of LexPojo for the code input by user to be parsed
				lexer.processOneLine(lexPojoList, lexiconMap, thisLine, lineNumber);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			
		}
		
	}
}
