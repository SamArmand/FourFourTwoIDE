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
	
	//Test to check if character is a letter
	public boolean isLetter(char c) {

		return ("" + c).matches("[a-zA-Z]");

	}
	
	//Test to check if character is a digit
	public boolean isDigit(char c) {

		return ("" + c).matches("[0-9]");

	}
	
	//Test to check if character is alphanumeric or an underscore
	public boolean isAlphanum(char c) {

		return isLetter(c) || isDigit(c) || c == '_';

	}
	
	//Test to see if character is a reserved word
	public boolean isReservedWord(String s) {

		return s.equals("and") || s.equals("not") || s.equals("or")
				|| s.equals("if") || s.equals("then") || s.equals("else")
				|| s.equals("for") || s.equals("class")
				|| s.equals("int") || s.equals("float") || s.equals("get")
				|| s.equals("put") || s.equals("return");

	}
	
	/*This is a handwritten method for analyzing tokens.
	Since errors are detected within this method and written to errors.txt
	this method could throw an IOException*/ 
	public Token nextToken() {

		String lexeme = "";

		Token token = null;
		
		char c = source.nextChar();

		while (c == ' ') c = source.nextChar();

		if (source.isEndOfFile()) token = new Token("EOF", "$", source.getCurrentLineNumber(c));

		else if (c == '*') token = new Token("ASTERISK", "*", source.getCurrentLineNumber(c));

		else if (c == '+') token = new Token("PLUS", "+", source.getCurrentLineNumber(c));

		else if (c == '-') token = new Token("MINUS", "-", source.getCurrentLineNumber(c));

		else if (c == ';') token = new Token("SEMICOLON", ";", source.getCurrentLineNumber(c));

		else if (c == ',') token = new Token("COMMA", ",", source.getCurrentLineNumber(c));

		else if (c == '(') token = new Token("OPAREN", "(", source.getCurrentLineNumber(c));

		else if (c == ')') token = new Token("CPAREN", ")", source.getCurrentLineNumber(c));

		else if (c == '{') token = new Token("OBRACE", "{", source.getCurrentLineNumber(c));

		else if (c == '}') token = new Token("CBRACE", "}", source.getCurrentLineNumber(c));

		else if (c == '[') token = new Token("OBRACKET", "[", source.getCurrentLineNumber(c));

		else if (c == ']') token = new Token("CBRACKET", "]", source.getCurrentLineNumber(c));

		else if (c == '.') token = new Token("DOT", ".", source.getCurrentLineNumber(c));

		else if (c == '=') {

			c = source.nextChar();

			if (c == '=') token = new Token("EQ", "==", source.getCurrentLineNumber(c));

			else {

				token = new Token("DEF", "=", source.getCurrentLineNumber(c));
				source.backtrack();

			}

		}

		else if (c == '<') {

			c = source.nextChar();

			if (c == '>') token = new Token("NEQ", "<>", source.getCurrentLineNumber(c));

			else if (c == '=') token = new Token("LEQ", "<=", source.getCurrentLineNumber(c));

			else {

				token = new Token("LESS", "<", source.getCurrentLineNumber(c));
				source.backtrack();

			}

		}

		else if (c == '>') {

			c = source.nextChar();

			if (c == '=') token = new Token("GEQ", ">=", source.getCurrentLineNumber(c));

			else {

				token = new Token("GREATER", ">", source.getCurrentLineNumber(c));
				source.backtrack();

			}

		}

		else if (isLetter(c)) {

			lexeme += c;

			c = source.nextChar();

			while (isAlphanum(c)) {

				lexeme = lexeme + c;
				c = source.nextChar();

			}

			if (isReservedWord(lexeme))
				token = new Token(lexeme.toUpperCase(), lexeme, source.getCurrentLineNumber(c));

			else token = new Token("ID", lexeme, source.getCurrentLineNumber(c));

			source.backtrack();

		}


		else if (c == '/') {

			c = source.nextChar();
			if (c == '/'){

				token = new Token("COMMENT", "//", source.getCurrentLineNumber(c));

				source.setLastOCOMMENTLine(source.getCurrentLineNumber(c));

				while (source.getCurrentLineNumber(c) == source.getLastOCOMMENTLine()) source.nextChar();

			}

			else if(c == '*'){

				source.setLastOCOMMENTLine(source.getCurrentLineNumber(c));

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
					token = new Token("ERROR", lexeme, source.getCurrentLineNumber(c));

				}


			}

			else {

				token = new Token("FWDSLASH", "/", source.getCurrentLineNumber(c));
				source.backtrack();

			}

		}

		else if (c == '0') {

			lexeme += c;
			c = source.nextChar();

			if (c != '.') {

				token = new Token("INT", lexeme, source.getCurrentLineNumber(c));
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

						errorStrings.append("ERROR: Unknown Token: ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber(c)).append("\n");
						source.backtrack();
						token = new Token("ERROR", lexeme, source.getCurrentLineNumber(c));

					}

					else {

						token = new Token("NUM", lexeme, source.getCurrentLineNumber(c));
						source.backtrack();

					}

				}

				//no digit after "0."
				else {
					errorStrings.append("ERROR: Unknown Token: ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber(c)).append("\n");
					source.backtrack();
					token = new Token("ERROR", lexeme, source.getCurrentLineNumber(c));
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

					// Ex: 1.0 or 42545235.0 etc. is valid but 1.010 is not
					if (lexeme.charAt(lexeme.length()-1) == '0' && (lexeme.charAt(lexeme.length()-2) != '.')) {

						errorStrings.append("ERROR: Unknown Token: ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber(c)).append("\n");
						source.backtrack();
						token = new Token("ERROR", lexeme, source.getCurrentLineNumber(c));

					}

					else {

						token = new Token("NUM", lexeme, source.getCurrentLineNumber(c));
						source.backtrack();

					}

				}

				//No number after the .
				else {

					errorStrings.append("ERROR: Unknown Token ").append(lexeme).append(" at line ").append(source.getCurrentLineNumber(c)).append("\n");
					source.backtrack();
					token = new Token("ERROR", lexeme, source.getCurrentLineNumber(c));

				}

			}

			//Valid number
			else {

				token = new Token("INT", lexeme, source.getCurrentLineNumber(c));
				source.backtrack();

			}

		}

		else {

			errorStrings.append("ERROR: Unknown Character: ").append(c).append(" at line ").append(source.getCurrentLineNumber(c)).append("\n");
			token = new Token("ERROR", lexeme, source.getCurrentLineNumber(c));

		}

		assert token != null;
		tokenStrings.append(token.toString()).append("\n");
		return token;
		
	}
	
}