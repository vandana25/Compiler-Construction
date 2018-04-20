package projectTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FollowSet {
	Map<String, List<Integer>> nonTerminalVsRuleIdsMap = null;
	List<String> simpleRuleList = null;
	Map<String, Integer> nonTerminalSymMap;
	boolean[] checkProcessed;
	String[] nonTerminalArray;
	Map<Integer, List<String>> firstSetMap;
	static Map<String, List<String>> followSetMap;
	
	
	public FollowSet() {
		nonTerminalVsRuleIdsMap = Symbols.getNonTerminalVsRuleIdsMap();
		simpleRuleList = Symbols.getSimpleRulesList();
		nonTerminalSymMap = Symbols.getNonTerminalSymMap();
		checkProcessed = new boolean[nonTerminalSymMap.size()];
		nonTerminalArray = new String[nonTerminalSymMap.size()];
		firstSetMap = FirstSet.getFirstSetMap();
		int i=0;
		for(Map.Entry<String, Integer> entry : nonTerminalSymMap.entrySet()) {
			nonTerminalArray[i++] = entry.getKey();
		}
		followSetMap = new HashMap<String, List<String>>();
	}
	
	public Map<String, List<String>> getFollowSet() {
		int countProcessed = 0;
		int index = 0;
		while(countProcessed < nonTerminalSymMap.size()) {
			if(!checkProcessed[index]) {
				if(getThisFollowSet(index, followSetMap)) {
					countProcessed++;
				}
			}
			index++;
			if(index == checkProcessed.length) {
				index = 0;
			}
//			String nt = nonTerminalArray[index];
			
		}
		return followSetMap;
	}
	
	
	public boolean getThisFollowSet(int index, Map<String, List<String>> temp) {
		//scan all rules
		//if T is on the right side and T is not on the left side
		//for each such rhs, 
		//find pos of T, 
		//get char at T+1: pos
		//if pos is t, add it to the list
		//if pos is nt, add to list FirstSet(nt).
		//if pos doesn't exist
		//add to list followSet(lhs)
		//if follow set doesn't exist skip it.
		Set<String> tempSet = new HashSet<String>();
		List<String> tempList = new ArrayList<String>();
		String base = nonTerminalArray[index];
		boolean operationSuccessful = false;
		for(String thisRule : simpleRuleList) {
			if(!thisRule.trim().startsWith(base)) {//base is absent on LHS
				int pos = -1;
				String lhs = thisRule.trim().split("=")[0].trim();
				String rhs = thisRule.trim().split("=")[1].trim();
				if(rhs.indexOf(base) != -1) {//base is present in RHS
					//int pos = rhs.indexOf(base);
					String[] words = thisRule.trim().split("=")[1].trim().split("\\s+");
					if(words[words.length - 1].trim().equals(base)) {//base is last token of the line. 
						//calculate followset of lhs
						if(followSetMap.containsKey(lhs)) {
							tempSet.addAll(followSetMap.get(lhs));
							//tempList.addAll(followSetMap.get(lhs));
							operationSuccessful = true;
							//checkProcessed[index] = true;
							//return true;
						} else {
							return false;
						}
					} else {//next is present. search the position of base in rhs
						for(int i=0; i<words.length; i++) {
							if(words[i].trim().equals(base)) {
								pos = i;
								break;
							}
						}
						String nextToken = words[++pos];
						if(nonTerminalSymMap.containsKey(nextToken)) {//following is a NT symbol
							//fetch all rules which has nextToken as lhs.
//							String rules = nonTerminalVsRuleIdsMap.values();
							for(Integer ruleId : nonTerminalVsRuleIdsMap.get(nextToken)) {
								tempSet.addAll(firstSetMap.get(ruleId));
								//tempList.addAll(firstSetMap.get(ruleId));
							}
							//tempList.addAll(firstSetMap.get(nextToken));
							//check if any rule has [base = eps] form.
							while(pos < words.length && hasEpsionOnRight(words[pos], new ArrayList<Integer>())) {
								pos++;
							}
							if(pos == words.length) {//all next tokens have epsilon rule. 
								//add followset of lhs.
								if(followSetMap.containsKey(lhs)) {
									tempSet.addAll(followSetMap.get(lhs));
									//tempList.addAll(followSetMap.get(lhs));
									operationSuccessful = true;
									//checkProcessed[index] = true;
								} else {
									continue;
								}
							} else {//next now has no epsilon Rule
								//add follow set of next.
								if(followSetMap.containsKey(nextToken)) {
									tempSet.addAll(followSetMap.get(nextToken));
									//tempList.addAll(followSetMap.get(nextToken));
									operationSuccessful = true;
									//checkProcessed[index] = true;
									//return true;
								} else {
									return false;
								}
							}
						} else {//following is a T symbol
							//tempList.add(nextToken);
							tempSet.add(nextToken);
							operationSuccessful = true;
							//checkProcessed[index] = true;
							//return true;
						}
					}
				}
			}
		}
		if(operationSuccessful) {
			tempList.addAll(tempSet);
			temp.put(base, tempList);
			checkProcessed[index] = true;
			return true;
		}
		return false;
	}
	
	//ruleId has no usage here directly. Its used to populate LL Table.
	public boolean hasEpsionOnRight(String token, List<Integer> ruleId) {
		int index = 0;
		for(String rule : simpleRuleList) {
			if(rule.trim().split("=")[0].trim().equals(token.trim()) && rule.trim().split("=")[1].trim().indexOf("eps") != -1) {
				ruleId.add(index);
				return true;
			}
			index++;
		}
		return false;
	}
}
