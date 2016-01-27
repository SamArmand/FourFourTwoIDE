package compiler;

//Data structure for storing information about a token
public class Token {

	private String name;
	private String lexeme;
	private int lineNum;
	
	//Constructor
	public Token(String name, String lexeme, int lineNum) {
		
		this.name = name;
		this.lexeme = lexeme;
		this.lineNum = lineNum;
		
	}

	public String getName() {
		return name;
	}

	//returns formatted Token info
	public String toString() {
		
		return String.format("Token: %-15s Lexeme: %-15s Line: %-15s", name, lexeme, lineNum);
		
	}
	
}
