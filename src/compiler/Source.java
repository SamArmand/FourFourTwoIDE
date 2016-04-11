package compiler;

import ui.Outputter;

import java.io.BufferedReader;
import java.io.IOException;

/*A helper class to progress and backtrack through source the file,
to check if the end of the file was reached,
and to check if we are in a comment mode.*/
public class Source {

	private int currentLineNumber; //number of current line
	private int lastOCOMMENTLine; //location of last open comment
	private boolean comment; //whether or not we are in a comment mode
	private boolean endOfFile; //whether or not the end of the file was reached
	private BufferedReader code;

	private boolean newLine;

	//Getters and Setters

	int getCurrentLineNumber() {

		if (newLine)
			return (currentLineNumber-1);
		return currentLineNumber;

	}

	int getLastOCOMMENTLine() {
		return lastOCOMMENTLine;
	}

	void setLastOCOMMENTLine(int lastOCOMMENTLine) {
		this.lastOCOMMENTLine = lastOCOMMENTLine;
	}

	boolean isComment() {
		return comment;
	}

	void setComment(boolean comment) {
		this.comment = comment;
	}

	boolean isEndOfFile() {
		return endOfFile;
	}

	boolean isNewLine() {
		return newLine;
	}

	//Default constructor
	public Source(BufferedReader code) {

		currentLineNumber = 1;
		lastOCOMMENTLine = 0;
		endOfFile = false;
		newLine = false;
		this.code = code;
		
	}
	
	//Skip to next character
	char nextChar() {

		int i = -1;

		try {

			//mark current position and set read ahead to 1 character
			code.mark(1);

			i = code.read();

			if (i == '\n') {
				currentLineNumber++;
				newLine = true;
			}
			else
				newLine = false;

			if (i == '\r' || i == '\t' || i == '\n') i = ' ';

			else if (i == -1) endOfFile = true;

		}
		catch (IOException e) {
			Outputter.errorStrings.append("ERROR: Could not retrieve next character from source.");
			System.exit(0);
		}

		return (char) i;

	}
	
	//Backup to previous character.
	void backtrack() {

		try {
			code.reset();
		}
		catch (IOException e) {
			Outputter.errorStrings.append("ERROR: Could not backtrack to previous character in source.");
			System.exit(0);
		}

		if (newLine)
			currentLineNumber--;
	}
	
}