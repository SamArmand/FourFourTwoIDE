package compiler;

//Data structure for storing information about a token
public class Token {

	private String type;
	private String lexeme;
	private int line;
	
	//Constructor
	public Token(String type, String lexeme, int line) {
		
		this.type = type;
		this.lexeme = lexeme;
		this.line = line;
		
	}

	public String getType() {
		return type;
	}

	//returns formatted Token info
	public String toString() {

		return String.format("TOKEN | Type: %-15s Lexeme: %-15s Line: %-5s", type, lexeme, line);
		
	}
	
}
