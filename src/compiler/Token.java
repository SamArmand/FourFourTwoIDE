package compiler;

import java.util.Arrays;

//Data structure for storing information about a token
class Token {
	private String type;
	private String lexeme;
	private int line;
	
	//Constructor
	Token(String type, String lexeme, int line) {
		this.type = type;
		this.lexeme = lexeme;
		this.line = line;
	}

	public String getType() {
		return type;
	}

	String getLexeme() {
		return lexeme;
	}

	public int getLine() {
		return line;
	}

	//returns formatted Token info
	public String toString() {
		return String.format("TOKEN | Type: %-15s Lexeme: %-15s Line: %-5s", type, lexeme, line);
	}

	//checks if token belongs to set
	boolean belongsTo(String[] set) {
		return Arrays.asList(set).contains(type);
	}

	static String toDescription(String expectedToken) {
		switch (expectedToken) {
			case "SEMICOLON":
				return ";";
			case "COMMA":
				return ",";
			case "DOT":
				return ".";
			case "PLUS":
				return "+";
			case "MINUS":
				return "-";
			case "OPAREN":
				return "(";
			case "CPAREN":
				return ")";
			case "OBRACE":
				return "{";
			case "CBRACE":
				return "}";
			case "OBRACKET":
				return "[";
			case "CBRACKET":
				return "]";
			case "ASTERISK":
				return "*";
			case "EOF":
				return "end of file";
			case "FWDSLASH":
				return "/";
			case "BLOCKCOMMENT":
				return "block comment";
			case "DEF":
				return "=";
			case "EQ":
				return "==";
			case "LESS":
				return "<";
			case "LEQ":
				return "<=";
			case "NEQ":
				return "<>";
			case "GREATER":
				return ">";
			case "GEQ":
				return ">=";
			default:
				return expectedToken.toLowerCase();
		}
	}
}
