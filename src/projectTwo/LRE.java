package projectTwo;

import java.util.ArrayList;
import java.util.List;

public class LRE {
	/***
	 * takes one line of grammar as it is from the input file and converts in to
	 * separate rules.
	 * 
	 * @param line
	 * @return
	 */
	//List<String> allStrings= new ArrayList<>();
	static int count=0;
	public List<String> parseOneLine(String line) {
		 List<String> output = new ArrayList<>();
		if (detectRecursion(line)) {
			output.addAll(resolveRecursion(line));
		}
		else{
			String[] splitByEquals= line.trim().split("\\=");
			String firstExp =splitByEquals[0].trim();
			String restExp=splitByEquals[1].trim();
			String[] splitByPipe = restExp.trim().split("\\|");
			for(int i=0;i<splitByPipe.length;i++){
				StringBuilder sb = new StringBuilder();
				sb.append(firstExp+" = "+splitByPipe[i]);
				output.add(sb.toString());
			}
		}
	//	allStrings.addAll(output);
	//	display(allStrings);
		return output;
	}

	/***
	 * takes a line of grammar, and checks if there is a recursion present.
	 * 
	 * @param line
	 * @return
	 */
	
	public boolean detectRecursion(String line) {
		String[] splitByEquals= line.trim().split("\\=");
		String firstExp =splitByEquals[0].trim();
		String restExp=splitByEquals[1].trim();
		String[] splitByPipe = restExp.trim().split("\\|");
		for(int i=0;i<splitByPipe.length;i++){
			String currentString = splitByPipe[i].trim();
			StringBuilder firstWord= new StringBuilder();
			for(int j=0;j<currentString.length();j++){
				
				if(currentString.charAt(j)!= ' '){
					firstWord.append(currentString.charAt(j));
				}
				else if(firstExp.equals(firstWord.toString().trim())){
						return true;
				}
				else {
					break;
				}
			}
		}
		
		return false;
	}

	/***
	 * takes a line of grammar, and resolves recursion, if present.
	 * 
	 * @param line
	 * @return
	 */
	public List<String> resolveRecursion(String line) {
		count++;
		List<String> list = new ArrayList<>();
		String[] splited = line.trim().split("\\=");
		String firstExp = splited[0].trim();
		String[] removePipe = splited[1].trim().split("\\|");
		
		for(int i=0;i<removePipe.length;i++){
			String exp = removePipe[i].trim();
			String[] splitExp = exp.trim().split("\\s+");
			if(splitExp[0].trim().equals(firstExp)){
				StringBuilder sbRec = new StringBuilder();
				sbRec.append("Q"+count+" =");
				for(int k=1;k<splitExp.length;k++){
					sbRec.append(splitExp[k]+ " ");
					if(k==splitExp.length-1){
						sbRec.append("Q"+count);
						list.add(sbRec.toString().trim());
					}
				}
			}
			else {
				StringBuilder sbWithOutRec = new StringBuilder();
				sbWithOutRec.append(firstExp+" "+ "= ");
				for(int l =0;l<splitExp.length;l++){
					sbWithOutRec.append(splitExp[l]+ " ");
					if(l==splitExp.length-1){
						sbWithOutRec.append("Q"+count);
						list.add(sbWithOutRec.toString().trim());
					}
				}
			}
		}	
		StringBuilder sbeps = new StringBuilder();
		sbeps.append("Q"+count+" = "+"eps");
		list.add(sbeps.toString());
		return list;
	}

}
