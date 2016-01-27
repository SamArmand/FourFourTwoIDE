package compiler;

import java.io.BufferedReader;
import java.io.IOException;

/*A mechanism to progress and backtrack through the file
and to check if the end of the file was reached*/
public class Source {

	private String currentLine; //the current line being worked on
	private int currentLineNumber; //number of current line
	private int lastOCOMMENTLine; //location of last open comment
	private boolean comment;
	private boolean endOfFile;
	private BufferedReader code;
	private StringBuilder errorStrings;

	//Getters and Setters

	public String getCurrentLine() {
		return currentLine;
	}

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

	public boolean isEndOfFile() {
		return endOfFile;
	}

	//Default constructor
	public Source(BufferedReader code, StringBuilder errorStrings) {
		
		currentLine = null;
		currentLineNumber = 1;
		lastOCOMMENTLine = 0;
		endOfFile = false;
		this.code = code;
		this.errorStrings = errorStrings;
		
	}
	
	//Skip to next character
	public char nextChar() {

		try {

			//mark current position and set read ahead to 1 character
			code.mark(1);

			int c = code.read();

			if (c == '\n') {

				currentLineNumber++;

				return ' ';

			}

			if (c == '\r' || c == '\t') return ' ';

			if (c == -1) endOfFile = true;

			return (char) c;

		}
		catch (IOException e) {

			errorStrings.append("ERROR: Could not retrieve next character from source.");
			System.exit(0);
			return ' '; //for compiler to stop complaining

		}



	}
	
	//Backup to previous character.
	public void backupChar() {

		try {
			code.reset();
		}
		catch (IOException e) {
			errorStrings.append("ERROR: Could not retrieve next character from source.");
			System.exit(0);
		}
		
	}
	
}