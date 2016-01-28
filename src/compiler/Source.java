package compiler;

import java.io.BufferedReader;
import java.io.IOException;

/*A mechanism to progress and backtrack through the file
and to check if the end of the file was reached*/
public class Source {

	private int currentLineNumber; //number of current line
	private int lastOCOMMENTLine; //location of last open comment
	private boolean comment;
	private boolean endOfFile;
	private BufferedReader code;
	private StringBuilder errorStrings;

	//Getters and Setters

	public int getCurrentLineNumber(char c) {

		if (c == '\n')
			return (currentLineNumber-1);
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

	public boolean isEndOfFile() {
		return endOfFile;
	}

	//Default constructor
	public Source(BufferedReader code, StringBuilder errorStrings) {

		currentLineNumber = 1;
		lastOCOMMENTLine = 0;
		endOfFile = false;
		this.code = code;
		this.errorStrings = errorStrings;
		
	}
	
	//Skip to next character
	public char nextChar() {

		int i = -1;

		try {

			//mark current position and set read ahead to 1 character
			code.mark(1);

			i = code.read();

			if (i == '\n') {
				currentLineNumber++;
			}

			if (i == '\r' || i == '\t' || i == '\n') i = ' ';

			else if (i == -1) endOfFile = true;

		}
		catch (IOException e) {
			errorStrings.append("ERROR: Could not retrieve next character from source.");
			System.exit(0);
		}

		return (char) i;

	}
	
	//Backup to previous character.
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