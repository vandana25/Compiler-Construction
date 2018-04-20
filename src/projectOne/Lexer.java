package projectOne;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/***
 * 
 * This class takes an input String and matches it against the regexes (of identifier, integer, float, String).
 *
 */
public class Lexer {
	Map<String, Integer> lexcon = new HashMap<String, Integer>();
	Map<String, Integer> lexiconMap = null;
	LexPojo lexPojo;
	
	/***
	 * method checks if a token is int, float, String, or an identifier, and populates the pojo object.
	 * @param pojo the object to be populated
	 * @param str token
	 */
	public void processRegex(LexPojo pojo, String str, Map<String, Integer> lexiconMap) {
		//check if it is in map
		if(lexiconMap.containsKey(str)) {
			pojo.setTokenId(lexiconMap.get(str));
			pojo.setStr(str);
			return;
		}
		String idRegex = "((_|[a-zA-Z])+)[0-9]*$";
		String intRegex = "(\\+|-)?[0-9]+";
		String floatRegex = "^[\\+-]?([0-9]*[.])?[0-9]+$";
		String stringRegex = "'\"' .* '\"'";
		Pattern p = Pattern.compile(idRegex);
		Matcher m = p.matcher(str);
		//check if it is an identifier.
		if(Pattern.compile(idRegex).matcher(str).matches()) {
			pojo.setTokenId(2);
			pojo.setStr(str);
		} else if(Pattern.compile(intRegex).matcher(str).matches()) {////check if it is an integer.
			pojo.setTokenId(3);
			pojo.setIsInt(true);
			pojo.setInteger(str);
			pojo.setStr(str);
		} else if(Pattern.compile(floatRegex).matcher(str).matches()) { //check if it is a floating-point number.
			pojo.setTokenId(4);
			pojo.setIsFloat(true);
			pojo.setFloatingNumber(str);
			pojo.setStr(str);
		} else if(Pattern.compile(stringRegex).matcher(str).matches()) {//check if it is a String.
			pojo.setTokenId(5);
//			pojo.setIsFloat(false);
//			pojo.setFloatingNumber(str);
			pojo.setStr(str);
		}
		
	}
	
	public void processOneLine(List<LexPojo> list, Map<String, Integer> mapp, String thisLine, int lineNumber) {
		int startIndex = 0;
		int currIndex = 0;
		int len = thisLine.length();
		char thisChar = ' ';
		String thisWord = "";
		//read the input line char-by-char
		while(currIndex < len) {
			thisChar = thisLine.charAt(currIndex);
			if(thisChar == '.') {
				currIndex++;
				continue;
			} else if (thisChar == '"') {// continue till you find another double-quote
				currIndex++;
				while (currIndex < len) {
					if (thisLine.charAt(currIndex) != '"') {
						currIndex++;
					} else {
						break;
					}
				}
				//this token is surrounded by "s, so it's a string.
				//populated values accordingly.
				thisWord = thisLine.substring(startIndex+1, currIndex);
				startIndex = currIndex+1;
				LexPojo pojo = new LexPojo();
				pojo.setLineNumber(lineNumber);
				pojo.setStr(thisWord);
				pojo.setTokenId(5);
				list.add(pojo);
				thisWord = "";
				currIndex++;
				continue;
			}
			//if a comment starts from here, skip everything that follows.
			if(thisChar == '/') {
				if(currIndex+1 < len && thisLine.charAt(currIndex+1) == '/') {
					break;
				}
			}
			//if thisChar represents a space, or a key  present in map,
			//call processRegex
			if(thisChar == ' ' || mapp.containsKey(thisChar+"")) {
				LexPojo pojo = new LexPojo();
				thisWord = thisLine.substring(startIndex, currIndex).trim();
				startIndex = currIndex;
				if(thisWord.length() > 0) {
					pojo.setLineNumber(lineNumber);
					processRegex(pojo, thisWord, mapp);
					list.add(pojo);
					thisWord = "";
				}
				
				if(thisChar != ' ') {
					pojo = new LexPojo();
					pojo.setLineNumber(lineNumber);
					processRegex(pojo, thisChar+"", mapp);
					list.add(pojo);
				}
				startIndex++;
			}
			
			currIndex++;
		}
	}
	
	/***
	 * method takes input from console, and populates the corresponding pojo objects with values.
	 * @return list of LexPojo having respective values.
	 */
	public List<LexPojo> processInput(Map<String, Integer> map) {
		lexiconMap = map;
		List<LexPojo> output = new ArrayList<LexPojo>();
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			String thisLine = "";
			int lineNumber=1;
			//this will wait for an empty newline for termination.
			while(sc.hasNextLine() && !(thisLine = sc.nextLine()).equals("")) {
				processOneLine(output, map, thisLine, lineNumber);
//				int startIndex = 0;
//				int currIndex = 0;
//				int len = thisLine.length();
//				char thisChar = ' ';
//				String thisWord = "";
//				//read the input line char-by-char
//				while(currIndex < len) {
//					thisChar = thisLine.charAt(currIndex);
//					if(thisChar == '.') {
//						currIndex++;
//						continue;
//					} else if (thisChar == '"') {// continue till you find another double-quote
//						currIndex++;
//						while (currIndex < len) {
//							if (thisLine.charAt(currIndex) != '"') {
//								currIndex++;
//							} else {
//								break;
//							}
//						}
//						//this token is surrounded by "s, so it's a string.
//						//populated values accordingly.
//						thisWord = thisLine.substring(startIndex+1, currIndex);
//						startIndex = currIndex+1;
//						LexPojo pojo = new LexPojo();
//						pojo.setLineNumber(lineNumber);
//						pojo.setStr(thisWord);
//						pojo.setTokenId(5);
//						output.add(pojo);
//						thisWord = "";
//						currIndex++;
//						continue;
//					}
//					//if a comment starts from here, skip everything that follows.
//					if(thisChar == '/') {
//						if(currIndex+1 < len && thisLine.charAt(currIndex+1) == '/') {
//							break;
//						}
//					}
//					//if thisChar represents a space, or a key  present in map,
//					//call processRegex
//					if(thisChar == ' ' || lexiconMap.containsKey(thisChar+"")) {
//						LexPojo pojo = new LexPojo();
//						thisWord = thisLine.substring(startIndex, currIndex).trim();
//						startIndex = currIndex;
//						if(thisWord.length() > 0) {
//							pojo.setLineNumber(lineNumber);
//							processRegex(pojo, thisWord);
//							output.add(pojo);
//							thisWord = "";
//						}
//						
//						if(thisChar != ' ') {
//							pojo = new LexPojo();
//							pojo.setLineNumber(lineNumber);
//							processRegex(pojo, thisChar+"");
//							output.add(pojo);
//						}
//						startIndex++;
//					}
//					
//					currIndex++;
//				}
				lineNumber++;
			}
			//add an extra entry for end of file.
			lexPojo = new LexPojo();
			lexPojo.setLineNumber(lineNumber-1);
			lexPojo.setTokenId(lexiconMap.get(""));
			lexPojo.setStr("");
			output.add(lexPojo);
			System.out.println();
		} 
		finally {
			sc.close();
		}
		return output;
	}
}