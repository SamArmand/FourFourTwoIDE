package compiler;

//Data structure for storing information about a token
public class Token {

	private String type;
	private String lexeme;
	private int lineNum;
	
	//Constructor
	public Token(String type, String lexeme, int lineNum) {
		
		this.type = type;
		this.lexeme = lexeme;
		this.lineNum = lineNum;
		
	}

	public String getType() {
		return type;
	}

	//returns formatted Token info
	public String toString() {
		
		return String.format("TOKEN | Type: %-8s Lexeme: %-15s Line: %-5s", type, lexeme, lineNum);
		
	}
	
}
