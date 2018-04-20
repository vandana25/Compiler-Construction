package projectTwo;

import java.util.List;
import java.util.Map;

public class TestProject2 {
	static List<String> ruleList;
	static Map<String, Integer> terminals;
	static Map<String, Integer> nonTerminals;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Symbols symbols = new Symbols();
		
		//parse grammar and populate Terminal and Non-Terminal Symbol Set
		symbols.readGrammarFile("grammarTest.txt");
		symbols.populateNonTerminalVsRuleIdsMap();
		
		//create first Set.
		FirstSet object = new FirstSet();
		Map<Integer, List<String>> firstSet = object.getFirstSet();
		System.out.println("FirstSet:");
		for(Map.Entry<Integer, List<String>> entry: firstSet.entrySet()) {
			System.out.print(entry.getKey()+": ");
			List<String> values = entry.getValue();
			for(String s : values) {
				System.out.print(s+", ");
			}
			System.out.println();
		}
		System.out.println("End");
		//create follow set. Not to Do.
		FollowSet followSet = new FollowSet();
		Map<String, List<String>> fSet = followSet.getFollowSet();
		for(Map.Entry<String, List<String>> entry : fSet.entrySet()){
			List<String> str = entry.getValue();
			System.out.print("Follow set Key " + entry.getKey()+" :");
			System.out.print("Fllow set Value :" );
			for(String s : str){
				System.out.print(s+" :");
				
			}
			System.out.println();
		}
		
		//create LL table.
		LLTable llTable = new LLTable();
		int[][] res = llTable.populateLLTable();
		System.out.println("Non-Terminal Symbols positions");
		for(Map.Entry<String, Integer> nt : Symbols.getNonTerminalSymMap().entrySet()) {
			System.out.println(nt.getKey()+": "+nt.getValue());
			System.out.println();
		}
		System.out.println("Terminal Symbols positions");
		for(Map.Entry<String, Integer> nt : Symbols.getTerminalSymMap().entrySet()) {
			System.out.println(nt.getKey()+": "+nt.getValue());
			System.out.println();
		}
		
		for(int i=0; i<res.length; i++) {
			for(int j=0; j<res[0].length; j++) {
				System.out.print(res[i][j]+"  ");
			}
			System.out.println();
		}
		
		ruleList = symbols.getSimpleRulesList();
		terminals = symbols.getTerminalSymMap();
		nonTerminals = symbols.getNonTerminalSymMap();
		System.out.println("Printing All Simple Rules:");
		for(String str : ruleList) {
			System.out.println(str);
		}
		System.out.println("Printing non-terminal symbols:");
		for(Map.Entry<String, Integer> str : nonTerminals.entrySet()) {
			System.out.println(str.getKey()+" : "+str.getValue());
		}
		System.out.println("Printing Terminal symbols:");
		for(Map.Entry<String, Integer> str : terminals.entrySet()) {
			System.out.println(str.getKey()+" : "+str.getValue());
		}

		
		
		//run parse machine
		Parser parser = new Parser();
		parser.parseUserInput("code.txt");
		ParseMachine parseMachine = new ParseMachine();
		parseMachine.startParserMachine();
		
		//create PST
		PstNode root = ParseMachine.root;
		Pst pst = new Pst();
		pst.serializeTree(root);
		
		//create PST to AST.
	}

}
