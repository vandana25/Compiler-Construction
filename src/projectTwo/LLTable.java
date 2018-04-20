package projectTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LLTable {
	Map<String, Integer> terminalSymMap;
	Map<String, Integer> nonTerminalSymMap;
	List<String> simpleRulesList;
	Map<Integer, List<String>> firstSetMap;
	static int[][] array;
	
	public LLTable() {
		terminalSymMap = Symbols.getTerminalSymMap(); //m
		nonTerminalSymMap = Symbols.getNonTerminalSymMap(); //n
		simpleRulesList = Symbols.getSimpleRulesList();
		firstSetMap = FirstSet.getFirstSetMap();
		//array = new int[nonTerminalSymMap.size()][terminalSymMap.size()];
	}
	
	public static int[][] getArray() {
		return array;
	}

	public int[][] populateLLTable() {
		array = new int[nonTerminalSymMap.size()][terminalSymMap.size()];
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array[0].length; j++) {
				//System.out.println(array[i][j]+"\\t");
				array[i][j] = -1;
			}
//			System.out.println();
		}
		int index = 0;
		for(String thisRule : simpleRulesList) {
			String lhs = thisRule.split("=")[0].trim();
			int m = nonTerminalSymMap.get(lhs);
			List<String> firstSetResult = firstSetMap.get(index);
			if(firstSetResult != null) {
				for(String terminalSym : firstSetResult) {
					int n = terminalSymMap.get(terminalSym);
					array[m][n] = index;
				}
			}
			index++;
		}
		FollowSet followSet = new FollowSet();
		Map<String, List<String>> allFollowSet = followSet.getFollowSet();
		List<String> tokenWithEpsilonOnRight = new ArrayList<String>();

//		List<Integer> ruleIds = new ArrayList<Integer>();
		List<Integer> tempIds = new ArrayList<Integer>();
		List<Integer> nTermRuleIds = new ArrayList<Integer>();
		for(String nonTerminalSym : nonTerminalSymMap.keySet()) {
			if(followSet.hasEpsionOnRight(nonTerminalSym, tempIds)) {
				tokenWithEpsilonOnRight.add(nonTerminalSym);
				nTermRuleIds.add(tempIds.remove(0));
			}
		}
		
		
		
		index = 0;
		for(String token : tokenWithEpsilonOnRight) {
			List<String> thisFolowSet = allFollowSet.get(token);
			int m = nonTerminalSymMap.get(token);
			for(String nTerminal : thisFolowSet) {
				int n = terminalSymMap.get(nTerminal);
				array[m][n] = nTermRuleIds.get(index);
			}
			index++;
		}
		//for each rule id
		// get the LHS N-T and position (say n)
		//get the first set of this rule id
		//for each element in first set
			//determine the position (T)
			//insert in array[m][T]
		
		return array;
	}
}
