package compiler;

import java.io.BufferedReader;
import java.io.IOException;

/*A helper class to progress and backtrack through the file
and to check if the end of the file was reached*/
public class Source {

	private int currentLineNumber; //number of current line
	private int lastOCOMMENTLine; //location of last open comment
	private boolean comment;
	private BufferedReader code;
	private StringBuilder errorStrings;

	//Getters and Setters
	public int getCurrentLineNumber() {
		return currentLineNumber;
	}

	public int getLastOCOMMENTLine() {
		return lastOCOMMENTLine;
	}

	public void setLastOCOMMENTLine(int lastOCOMMENTLine) {
		this.lastOCOMMENTLine = lastOCOMMENTLine;
	}

	public boolean isComment() {
		return comment;
	}

	public void setComment(boolean comment) {
		this.comment = comment;
	}

	//Default constructor
	public Source(BufferedReader code, StringBuilder errorStrings) {

		currentLineNumber = 1;
		lastOCOMMENTLine = 0;
		this.code = code;
		this.errorStrings = errorStrings;
		
	}
	
	//Skip to next character
	public char nextChar() {

		int c = -1;

		try {

			//mark current position and set read ahead to 1 character
			//used for backtracking
			code.mark(1);

			c = code.read();

			//reached new line in source code
			if (c == '\n') currentLineNumber++;

			//treat all space-like characters the same way
			if (c == '\r' || c == '\t' || c == '\n') return ' ';

		}
		catch (IOException e) {
			errorStrings.append("ERROR: Could not retrieve next character from source.");
			System.exit(0);
		}

		return (char) c;

	}
	
	//Backtrack to previous character.
	public void backtrack() {

		try {
			code.reset();
		}
		catch (IOException e) {
			errorStrings.append("ERROR: Could not retrieve next character from source.");
			System.exit(0);
		}
		
	}
	
}