package projectOne;

public class LexPojo {
	private boolean isInt = false;
	private boolean isFloat = false;
	private String integer;
	private String floatingNumber;
	private int tokenId;
	private int lineNumber;
	private String str;
	
	public boolean isInt() {
		return isInt;
	}

	public void setIsInt(boolean isInt) {
		this.isInt = isInt;
	}

	public boolean isFloat() {
		return isFloat;
	}

	public void setIsFloat(boolean isFloat) {
		this.isFloat = isFloat;
	}

	public String getInteger() {
		return integer;
	}

	public void setInteger(String integer) {
		this.integer = integer;
	}

	public String getFloatingNumber() {
		return floatingNumber;
	}

	public void setFloatingNumber(String floatingNumber) {
		this.floatingNumber = floatingNumber;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append("Tok: "+this.tokenId+" line= "+this.lineNumber+" str= \""+this.str+"\""+ " ");
		if(isInt){
			str.append("int= "+this.integer);
		}
		if(isFloat){
			str.append("float= "+this.floatingNumber);
		}
		return str.toString();
	}
}
