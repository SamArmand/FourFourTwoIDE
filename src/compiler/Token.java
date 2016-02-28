package compiler;

import java.util.Arrays;

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

	public int getLine() {
		return line;
	}

	//returns formatted Token info
	public String toString() {

		return String.format("TOKEN | Type: %-15s Lexeme: %-15s Line: %-5s", type, lexeme, line);
		
	}

	//checks if token belongs to set
	public boolean belongsTo(String[] set) {

		return Arrays.asList(set).contains(type);

	}
	
}
