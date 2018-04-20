package projectTwo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import projectOne.LexPojo;
import projectOne.Lexicon;

public class ParseMachine {
	Stack<String> stack;
	Map<String, Integer> lexiconMap;
	List<LexPojo> lexPojoList;
	Map<Integer, String> tokenIdVsTokenMap;
//	Symbols symbols = new Symbols();
	//LLTable llTable = new LLTable();
	static Map<String, Integer> terminals;
	static Map<String, Integer> nonTerminals;
	int[][] llTable;
	public static PstNode root;
	
	public ParseMachine() {
		stack = new Stack<String>();
		lexiconMap = Parser.getLexiconMap();
		lexPojoList = Parser.getLexPojoList();
		tokenIdVsTokenMap = Lexicon.getTokenIdVsTokenNameMap();
		tokenIdVsTokenMap.put(2, "id");
		tokenIdVsTokenMap.put(3, "int");
		tokenIdVsTokenMap.put(4, "float");
		tokenIdVsTokenMap.put(5, "string");
		llTable = LLTable.getArray();
	}
	
	public void startParserMachine() {
		String inputFront = "";
		stack.push(Symbols.getStartSymbol());
		//
		Stack<PstNode> nodeStack = new Stack<PstNode>();
		root = new PstNode(stack.peek());
		nodeStack.push(root);
		//
		LexPojo thisPojo = lexPojoList.get(0);
		inputFront = tokenIdVsTokenMap.get(thisPojo.getTokenId());
		nonTerminals = Symbols.getNonTerminalSymMap();
		terminals = Symbols.getTerminalSymMap();
		boolean emptyStack = false;
		int deleteMe = 1;
		while(!stack.isEmpty()) {
			System.out.println("iteration: "+(deleteMe++)+", stack top: "+stack.peek());
			if(emptyStack) {
				stack.pop();
			}
			if(stack.peek().equalsIgnoreCase(inputFront)){			
				stack.pop();
				//
				PstNode temp =nodeStack.pop();
				PstNode terminalNode = new PstNode(inputFront);
				List<PstNode> l = new ArrayList<PstNode>();
				l.add(terminalNode);
				terminalNode.setParent(temp);
				temp.setChildren(l);
				//
				if(lexPojoList != null || lexPojoList.size() >0) {
					thisPojo = lexPojoList.remove(0);
					inputFront = tokenIdVsTokenMap.get(thisPojo.getTokenId()); 
				} 
			}
			else {
				if(stack.peek().charAt(0)>=97 && stack.peek().charAt(0) <=122 || stack.peek().charAt(0)==39){// top is a terminal symbol
					System.out.println("User Input is Invalif1 for the given grammer");
					return;
				}
				String top = stack.pop();
				int m=nonTerminals.get(top);
				int n = 0;
				if(terminals != null && inputFront != null && inputFront.length() != 0) {
					if(terminals.containsKey(inputFront))
					n= terminals.get(inputFront);
				}
				int ruleNumber = llTable[m][n];
				if(ruleNumber == -1) {
					System.out.println("User Input is Invalif2 for the given grammer");
					return;
				}
				
				String rhs= Symbols.getSimpleRulesList().get(ruleNumber).split("=")[1];
				
				rhs = (reverseString(rhs));
				//
				PstNode mom = nodeStack.pop();
				List<PstNode> listTemp = new ArrayList<PstNode>();
				String s[] = rhs.trim().split("\\s+");
				for(int i=0; i<s.length; i++) {
					PstNode temp = new PstNode(s[i].trim());
					temp.setParent(mom);
					nodeStack.push(temp);
					listTemp.add(temp);
				}
				mom.setChildren(listTemp);
				//
				for(String word : rhs.split("\\s+")) {
					if(!word.trim().equalsIgnoreCase("eps")) {
						stack.push(word);
					}
					
				}
			}
			if(lexPojoList.size() == 0) {
				emptyStack = true;
			}
		}
		System.out.println("Valid Input");
	}
	
	public String reverseString(String str){
		StringBuilder reverseString = new StringBuilder();
		String[] s = str.trim().split("\\s");
		for(int i=s.length-1; i>=0; i--)
		reverseString.append(s[i]+ " ");
		
		return reverseString.toString().trim();
	}
	
}
