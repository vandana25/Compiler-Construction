package projectTwo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Symbols {
	/***
	 * This method reads grammar file and creates sets of terminal and non-terminal symbols.
	 * Also, it requests to simplify each line of grammar.
	 * @param fileName
	 */
	//contains all simplified rules. The index is ruleId, so don't change the list once it is fully populated.
	private static List<String> simpleRulesList = new ArrayList<String>();
	private static String startSymbol = "";
	//set containing all terminal and non-terminal symbols.
	private Set<String> terminalSet = new HashSet<String>();
	private Set<String> nonTerminalSet = new HashSet<String>();
	
	//a map containing positions for all terminal and non-terminal symbols. Do not change it, to be used for making LL table. 
	private static Map<String, Integer> terminalSymMap = null;
	private static Map<String, Integer> nonTerminalSymMap = null;
	
	//a map containing details about which non terminal symbols appear in left side of what all ruleIds.
	private static Map<String, List<Integer>> nonTerminalVsRuleIdsMap = new HashMap<String, List<Integer>>();
	
	public static List<String> getSimpleRulesList() {
		return simpleRulesList;
	}
	
	public static String getStartSymbol() {
		return startSymbol;
	}
	
	public static Map<String, Integer> getTerminalSymMap() {
		return terminalSymMap;
	}
	
	

	public static Map<String, Integer> getNonTerminalSymMap() {
		return nonTerminalSymMap;
	}


	public static Map<String, List<Integer>> getNonTerminalVsRuleIdsMap() {
		return nonTerminalVsRuleIdsMap;
	}


	protected void readGrammarFile(String fileName) {
		boolean startSymbolSet = false;
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);
			String thisLine = "";
			//Scanner sc = new Scanner(System.in);
			//String thisLine = "";
			List<String> thisList = new ArrayList<String>();
			LRE obj = new LRE();
			//this will wait for an empty newline for termination.
			while(sc.hasNextLine() && !(thisLine = sc.nextLine()).equals("")) {
				//thisLine = sc.nextLine();
				//populate terminal and nonTerminal Sets.
				//parseSymbols(thisLine);
				thisList = obj.parseOneLine(thisLine);
				if(!startSymbolSet) {
					startSymbol = thisLine.trim().split("=")[0].trim();
					startSymbolSet = true;
				}
				for(String s : thisList) {
					simpleRulesList.add(s);
					parseSymbols(s);
				}
			}
			terminalSymMap = convertSetToMap(terminalSet);
			nonTerminalSymMap = convertSetToMap(nonTerminalSet);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally {
			
		}
		
	}
	/***
	 * this method parses a line of grammar and populates terminal and non-terminal sets with the respective sets. 
	 * @param line
	 */
	private void parseSymbols(String line) {
		String[] words = line.split("\\s+");
		String thisWord = "";
		char thisChar = ' ';
		for(int i=0; i<words.length; i++) {
			thisWord = words[i].trim();
			if(thisWord != "=" && thisWord != "|") {
				thisChar = thisWord.charAt(0);
				if(thisChar >= 65 && thisChar <= 90) {//this symbol starts with Capital Letter and is a non-Terminal symbol.
					nonTerminalSet.add(thisWord);
				} else if (thisChar >= 97 && thisChar <= 122 || thisChar == 39) {//this symbol starts with Small Letter and is a terminal symbol.
					terminalSet.add(thisWord);
				}
			}
		}
	}
	
	private Map<String, Integer> convertSetToMap(Set<String> set) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int id = 0;
		for(String s : set) {
			map.put(s, id++);
		}
		return map;
	}
	
	public void populateNonTerminalVsRuleIdsMap() {
		for(int i=0; i<simpleRulesList.size(); i++) {
			List<Integer> ids = new ArrayList<Integer>();
			String thisRule = simpleRulesList.get(i);
			String left = thisRule.split("=")[0].trim();
			if(nonTerminalVsRuleIdsMap.get(left) != null) {
				ids = nonTerminalVsRuleIdsMap.get(left);
			}
			ids.add(i);
			nonTerminalVsRuleIdsMap.put(left, ids);
		}
	}
}
