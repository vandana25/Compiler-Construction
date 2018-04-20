package projectTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirstSet {
	private static Map<Integer, List<String>> firstSetMap = new HashMap<Integer, List<String>>();
	//WE are not converting non-terminal Symbols to actual values (from project one) here.
	//Symbols symbols  = new Symbols();
	Map<String, List<Integer>> nonTerminalVsRuleIdsMap = null;
	List<String> simpleRuleList = null;
	boolean[] checkProcessed;
	
	public static Map<Integer, List<String>> getFirstSetMap() {
		return firstSetMap;
	}

	public FirstSet() {
		nonTerminalVsRuleIdsMap = Symbols.getNonTerminalVsRuleIdsMap();
		simpleRuleList = Symbols.getSimpleRulesList();
		if(simpleRuleList != null) {
			checkProcessed = new boolean[simpleRuleList.size()];
		}
	}
	
//	Map<Integer, List<String>> firstSetMap = new HashMap<Integer, List<String>>();
	String thisRule = "";
	
	public Map<Integer, List<String>> getFirstSet() {
		int index = 0;
		int countProcessed = 0;
		//loop for rules with Terminals. We need to process each terminal symbol just once.
		//parse the whole list just once. as it will process all terminal symbols once. 
		while (index < simpleRuleList.size()) {
			thisRule = simpleRuleList.get(index);
			if (processForTerminalSymbols(thisRule, index)) {
				// simpleRuleList.remove(index);
				checkProcessed[index] = true;
				countProcessed++;
			}
			index++;
		}
		index = 0;
		//loop for rules with non-terminals.
		while(countProcessed < simpleRuleList.size()) {
			if(!checkProcessed[index]) {
				thisRule = simpleRuleList.get(index);
				if(processForNonTerminalSymbols(thisRule, index)) {
					//simpleRuleList.remove(index);
					checkProcessed[index] = true;
					countProcessed++;
				}
			}
			if(index < simpleRuleList.size()) {
				index++;
			} else {
				index = 0;
			}
		}
		
		return firstSetMap;
		
	}
	
	private boolean processForTerminalSymbols(String rule, int ruleId) {
		String[] parts = rule.split("=");
		//String left = parts[0].trim();
		String[] words = parts[1].trim().split("\\s+");
		//if this is for a T symbol
			//if yes, add to the map and return true
		char thisChar = words[0].charAt(0); 
		if(thisChar >= 97 && thisChar <= 122 || thisChar == 39) {//its a terminal symbol
			List<String> values = new ArrayList<String>();
			values.add(words[0]);
			firstSetMap.put(ruleId, values);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean processForNonTerminalSymbols(String rule, int ruleId) {
		String[] parts = rule.split("=");
		//String left = parts[0].trim();
		String[] words = parts[1].trim().split("\\s+");
		//char thisChar = words[0].charAt(0);
		List<String> queue = new LinkedList<String>();
		queue.add(words[0]);
		List<String> values = new ArrayList<String>();
		List<Integer> tempRuleIds = new ArrayList<Integer>();
		String thisRule = "";
		while(!queue.isEmpty()) {
			//check while ruleIds correspond to this non-terminal.
			//if first set of these rule ids exists, put them in values,
			//else put the rule ids back in queue and iterate.
			thisRule = queue.remove(0);
			tempRuleIds = nonTerminalVsRuleIdsMap.get(thisRule);
			if(tempRuleIds ==  null) {
				continue;
			}
			for(Integer id: tempRuleIds) {
				if(firstSetMap.containsKey(id)) {
					for(String s : firstSetMap.get(id)) {
						values.add(s);
					}
				} else {
					String rul = simpleRuleList.get(id);
					String right = rul.split("=")[1].trim();
					String val = right.split("\\s+")[0].trim();
					//queue.add(rul.split("=")[1].split("\\s+")[0]);
					queue.add(val);
				}
			}
		
//			//check while ruleIds correspond to this non-terminal.
//			//if first set of these rule ids exists, put them in values,
//			//else put the rule ids back in queue and iterate.
//			thisRule = queue.remove(0);
//			tempRuleIds = nonTerminalVsRuleIdsMap.get(thisRule);
//			for(Integer id: tempRuleIds) {
//				if(firstSetMap.containsKey(id)) {
//					for(String s : firstSetMap.get(id)) {
//						values.add(s);
//					}
//				} else {
//					queue.add(thisRule);
//				}
//			}
			
		}
		firstSetMap.put(ruleId, values);
		return true;
	}
	
}
