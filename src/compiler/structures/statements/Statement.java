package compiler.structures.statements;

import compiler.structures.Codeable;

public class Statement implements Codeable {

    private int line;

    public Statement(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

    }
}
