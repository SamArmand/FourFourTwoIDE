package ui;

import compiler.Lexer;
import compiler.Parser;
import compiler.Source;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;

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
    @FXML
    TextArea moonCodeOutput;

    //handler for compileButton click action
    public void compile() {

        //save input to a file
        try (PrintWriter file = new PrintWriter(new FileOutputStream("source.txt"))) {
            file.print(editor.getText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //define input stream
        BufferedReader code;

        //define output streams
        PrintWriter tokens;
        PrintWriter errors;
        PrintWriter derivation;
        PrintWriter symbolTables;
        PrintStream moonCode;

        //create StringBuilders for output
        Outputter.errorStrings = new StringBuilder();
        Outputter.tokenStrings = new StringBuilder();
        Outputter.derivationStrings = new StringBuilder();
        Outputter.symbolTablesStrings = new StringBuilder();
        Outputter.moonCodeStrings = new StringBuilder();

        //Set up streams
        try
        {
            // reading the input file into the compiler
            code = new BufferedReader(new FileReader("source.txt"));
            // writing into output files
            tokens = new PrintWriter(new FileOutputStream("tokens.txt"));
            errors = new PrintWriter(new FileOutputStream("errors.txt"));
            derivation = new PrintWriter(new FileOutputStream("derivation.txt"));
            symbolTables = new PrintWriter(new FileOutputStream("symbol_tables.txt"));
            moonCode = new PrintStream(new File("moon_code.m"));

        }

        catch(FileNotFoundException e)
        {
            Outputter.errorStrings.append("Error opening files. Compilation aborted.\r\n");
            return;
        }

        //Streams are set up at this point

        //Object for tracking progress and backtracking through source code. See Source.java for more info.
        new Parser(new Lexer(new Source(code))).parse();

        tokens.print(Outputter.tokenStrings.toString());
        errors.print(Outputter.errorStrings.toString());
        derivation.print(Outputter.derivationStrings.toString());
        symbolTables.print(Outputter.symbolTablesStrings.toString());
        moonCode.print(Outputter.moonCodeStrings.toString());

        tokens.close();
        errors.close();
        derivation.close();
        symbolTables.close();
        moonCode.close();

        try {
            code.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        errorsOutput.setText(Outputter.errorStrings.toString());
        tokensOutput.setText(Outputter.tokenStrings.toString());
        derivationOutput.setText(Outputter.derivationStrings.toString());
        symbolTablesOutput.setText(Outputter.symbolTablesStrings.toString());
        moonCodeOutput.setText(Outputter.moonCodeStrings.toString());

    }

}
