package compiler;

public class Lexer {

	private Source source;
	private StringBuilder errorStrings;
	private StringBuilder tokenStrings;
	
	public Lexer(Source source, StringBuilder errorStrings, StringBuilder tokenStrings) {
		
		this.source = source;
		this.errorStrings = errorStrings;
		this.tokenStrings = tokenStrings;
		
	}
	
	//Check if the character is a letter
	private boolean isLetter(char c) {

		return ("" + c).matches("[a-zA-Z]");

	}
	
	//Check if the character is a digit
	private boolean isDigit(char c) {

		return ("" + c).matches("[0-9]");

	}
	
	//Check if the character is alphanumeric
	private boolean isAlphanumeric(char c) {

		return isLetter(c) || isDigit(c) || c == '_';

	}
	
	//Check if the token is a reserver word
	private boolean isReservedWord(String s) {

		return s.equals("and") || s.equals("not") || s.equals("or")
				|| s.equals("if") || s.equals("then") || s.equals("else")
				|| s.equals("for") || s.equals("class")
				|| s.equals("int") || s.equals("float") || s.equals("get")
				|| s.equals("put") || s.equals("return") || s.equals("program");

	}
	
	//This is a handwritten method for analyzing tokens.
	public Token nextToken() {

		String lexeme = "";

		Token token = null;
		
		char c = source.nextChar();

		while (c == ' ') c = source.nextChar();

		if (source.isEndOfFile()) token = new Token("EOF", "$", source.getCurrentLineNumber());

		else if (c == '*') token = new Token("ASTERISK", "*", source.getCurrentLineNumber());

		else if (c == '+') token = new Token("PLUS", "+", source.getCurrentLineNumber());

		else if (c == '-') token = new Token("MINUS", "-", source.getCurrentLineNumber());

		else if (c == ';') token = new Token("SEMICOLON", ";", source.getCurrentLineNumber());

		else if (c == ',') token = new Token("COMMA", ",", source.getCurrentLineNumber());

		else if (c == '(') token = new Token("OPAREN", "(", source.getCurrentLineNumber());

		else if (c == ')') token = new Token("CPAREN", ")", source.getCurrentLineNumber());

		else if (c == '{') token = new Token("OBRACE", "{", source.getCurrentLineNumber());

		else if (c == '}') token = new Token("CBRACE", "}", source.getCurrentLineNumber());

		else if (c == '[') token = new Token("OBRACKET", "[", source.getCurrentLineNumber());

		else if (c == ']') token = new Token("CBRACKET", "]", source.getCurrentLineNumber());

		else if (c == '.') token = new Token("DOT", ".", source.getCurrentLineNumber());

		else if (c == '=') {

			c = source.nextChar();

			if (c == '=') token = new Token("EQ", "==", source.getCurrentLineNumber());

			else {

				token = new Token("DEF", "=", source.getCurrentLineNumber());
				source.backtrack();

			}

		}

		else if (c == '<') {

			c = source.nextChar();

			if (c == '>') token = new Token("NEQ", "<>", source.getCurrentLineNumber());

			else if (c == '=') token = new Token("LEQ", "<=", source.getCurrentLineNumber());

			else {

				token = new Token("LESS", "<", source.getCurrentLineNumber());
				source.backtrack();

			}

		}

		else if (c == '>') {

			c = source.nextChar();

			if (c == '=') token = new Token("GEQ", ">=", source.getCurrentLineNumber());

			else {

				token = new Token("GREATER", ">", source.getCurrentLineNumber());
				source.backtrack();

			}

		}

		else if (isLetter(c)) {

			lexeme += c;

			c = source.nextChar();

			while (isAlphanumeric(c)) {

				lexeme = lexeme + c;
				c = source.nextChar();

			}

			if (isReservedWord(lexeme))
				token = new Token(lexeme.toUpperCase(), lexeme, source.getCurrentLineNumber());

			else token = new Token("ID", lexeme, source.getCurrentLineNumber());

			source.backtrack();

		}


		else if (c == '/') {

			c = source.nextChar();
			if (c == '/'){

				token = new Token("COMMENT", "//", source.getCurrentLineNumber());

				source.setLastOCOMMENTLine(source.getCurrentLineNumber());

				while (!source.isNewLine()) source.nextChar();

			}

			else if(c == '*'){

				source.setLastOCOMMENTLine(source.getCurrentLineNumber());

				source.setComment(true);

				while (!source.isEndOfFile()) {

					c = source.nextChar();

					if (c == '*') {

						c = source.nextChar();

						if (c == '/') {

							token = new Token("BLOCKCOMMENT", "/**/", source.getLastOCOMMENTLine());
							source.setComment(false);
							break;

						}

					}

				}

				if (source.isComment()) {

					errorStrings.append("ERROR: Unclosed comment at line ").append(source.getLastOCOMMENTLine()).append("\n");
					token = new Token("ERROR", "/*", source.getCurrentLineNumber());

				}


			}

			else {

				token = new Token("FWDSLASH", "/", source.getCurrentLineNumber());
				source.backtrack();

			}

		}

		else if (c == '0') {

			lexeme += c;
			c = source.nextChar();

			if (c != '.') {

				token = new Token("INTEGER", lexeme, source.getCurrentLineNumber());
				source.backtrack();

			}

			//it is a '.'
			else {

				//tokenString is "0."
				lexeme += c;
				c = source.nextChar();

				if (isDigit(c)) {

					while (isDigit(c)) {

						lexeme += c;
						c = source.nextChar();

					}

					//no trailing 0s
					//check the last character in tokenString
					if (lexeme.charAt(lexeme.length()-1) == '0' && !lexeme.equals("0.0")) {

						errorStrings.append("ERROR: Unknown Token: ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber()).append("\n");
						source.backtrack();
						token = new Token("ERROR", lexeme, source.getCurrentLineNumber());

					}

					else {

						token = new Token("FRACTION", lexeme, source.getCurrentLineNumber());
						source.backtrack();

					}

				}

				//no digit after "0."
				else {
					errorStrings.append("ERROR: Unknown Token: ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber()).append("\n");
					source.backtrack();
					token = new Token("ERROR", "" + c, source.getCurrentLineNumber());
				}

			}

		}

		//Will only get here if it's nonzero
		else if (isDigit(c)) {

			lexeme += c;
			c = source.nextChar();

			while (isDigit(c)) {

				lexeme += c;
				c = source.nextChar();

			}

			//checking if fraction
			if (c == '.') {

				lexeme += c;
				c = source.nextChar();

				if (isDigit(c)) {

					while (isDigit(c)) {

						lexeme += c;
						c = source.nextChar();

					}

					//Check if trailing zero in fraction is not immediately after the decimal point
					if (lexeme.charAt(lexeme.length()-1) == '0' && (lexeme.charAt(lexeme.length()-2) != '.')) {

						errorStrings.append("ERROR: Unknown Token: ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber()).append("\n");
						source.backtrack();
						token = new Token("ERROR", lexeme, source.getCurrentLineNumber());

					}

					//valid float
					else {

						token = new Token("FRACTION", lexeme, source.getCurrentLineNumber());
						source.backtrack();

					}

				}

				//No digit after the dot
				else {

					errorStrings.append("ERROR: Unknown Token ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber()).append("\n");
					source.backtrack();
					token = new Token("ERROR", lexeme, source.getCurrentLineNumber());

				}

			}

			//Valid integer
			else {

				token = new Token("INTEGER", lexeme, source.getCurrentLineNumber());
				source.backtrack();

			}

		}

		else {

			errorStrings.append("ERROR: Unknown Character: ").append(c).append(" at line ").append(source.getCurrentLineNumber()).append("\n");
			token = new Token("ERROR", "" + c, source.getCurrentLineNumber());

		}

		assert token != null;
		tokenStrings.append(token.toString()).append("\n");
		return token;
		
	}
	
}