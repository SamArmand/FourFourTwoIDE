package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;

import compiler.Source;
import compiler.Lexer;
import compiler.Token;

public class Controller {

    @FXML
    Button compileButton;
    @FXML
    TextArea editor;
    @FXML
    TextArea errorsOutput;
    @FXML
    TextArea tokensOutput;

    //handler for compileButton click action
    public void compile() {

        //save input to a file
        save(editor.getText());

        //define input stream
        BufferedReader code;

        //define output streams
        PrintWriter tokens;
        PrintWriter errors;

        //create StringBuilders for output
        StringBuilder errorStrings = new StringBuilder();
        StringBuilder tokenStrings = new StringBuilder();

        //Set up streams
        try
        {
            code = new BufferedReader(new FileReader("source.txt"));
            tokens = new PrintWriter(new FileOutputStream("tokens.txt"));
            errors = new PrintWriter(new FileOutputStream("errors.txt"));
        }

        catch(FileNotFoundException e)
        {
            errorsOutput.appendText("Error opening files. Compilation aborted.\r\n");
            System.err.println("Error opening files. Compilation aborted.");
            return;
        }

        //Streams are set up at this point

        Source source = new Source(code, errorStrings); //Object for tracking progress and backtracking through source code. See Source.java for more info.
        Lexer lexer = new Lexer(source, errorStrings, tokenStrings);

        Token token;

        do {
            token = lexer.nextToken();
        } while (!token.getName().equals("EOF"));

        tokens.print(tokenStrings.toString());
        errors.print(errorStrings.toString());

        tokens.close();
        errors.close();

        try {
            code.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        errorsOutput.setText(errorStrings.toString());
        tokensOutput.setText(tokenStrings.toString());

    }

    private void save(String sourceCode) {

        try (PrintWriter file = new PrintWriter(new FileOutputStream("source.txt"))) {
            file.print(sourceCode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
