package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;

import compiler.Source;
import compiler.Lexer;
import compiler.Parser;

public class Controller {

    @FXML
    Button compileButton;
    @FXML
    TextArea editor;
    @FXML
    TextArea errorsOutput;
    @FXML
    TextArea tokensOutput;
    @FXML
    TextArea derivationOutput;
    @FXML
    TextArea symbolTablesOutput;

    //handler for compileButton click action
    public void compile() {

        //save input to a file
        save(editor.getText());

        //define input stream
        BufferedReader code;

        //define output streams
        PrintWriter tokens;
        PrintWriter errors;
        PrintWriter derivation;
        PrintWriter symbolTables;

        //create StringBuilders for output
        Outputter.errorStrings = new StringBuilder();
        Outputter.tokenStrings = new StringBuilder();
        Outputter.derivationStrings = new StringBuilder();
        Outputter.symbolTablesStrings = new StringBuilder();

        //Set up streams
        try
        {
            code = new BufferedReader(new FileReader("source.txt"));
            tokens = new PrintWriter(new FileOutputStream("tokens.txt"));
            errors = new PrintWriter(new FileOutputStream("errors.txt"));
            derivation = new PrintWriter(new FileOutputStream("derivation.txt"));
            symbolTables = new PrintWriter(new FileOutputStream("symbol_tables.txt"));

        }

        catch(FileNotFoundException e)
        {
            errorsOutput.appendText("Error opening files. Compilation aborted.\r\n");
            Outputter.errorStrings.append("Error opening files. Compilation aborted.\r\n");
            return;
        }

        //Streams are set up at this point

        Source source = new Source(code); //Object for tracking progress and backtracking through source code. See Source.java for more info.
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);

        parser.parse();

        tokens.print(Outputter.tokenStrings.toString());
        errors.print(Outputter.errorStrings.toString());
        derivation.print(Outputter.derivationStrings.toString());
        symbolTables.print(Outputter.symbolTablesStrings.toString());

        tokens.close();
        errors.close();
        derivation.close();
        symbolTables.close();

        try {
            code.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        errorsOutput.setText(Outputter.errorStrings.toString());
        tokensOutput.setText(Outputter.tokenStrings.toString());
        derivationOutput.setText(Outputter.derivationStrings.toString());
        symbolTablesOutput.setText(Outputter.symbolTablesStrings.toString());

    }

    private void save(String sourceCode) {

        try (PrintWriter file = new PrintWriter(new FileOutputStream("source.txt"))) {
            file.print(sourceCode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
