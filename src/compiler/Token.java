package compiler;

//Data structure for storing information about a token
public class Token {

	private String token;
	private String lexeme;
	private int lineNum;
	
	//Constructor
	public Token(String token, String lexeme, int lineNum) {
		
		this.token = token;
		this.lexeme = lexeme;
		this.lineNum = lineNum;
		
	}
	
	//returns formatted  info
	public String toString() {
		
		return String.format("Token: %-15s Lexeme: %-15s Line: %-15s", token, lexeme, lineNum);
		
	}
	
}
