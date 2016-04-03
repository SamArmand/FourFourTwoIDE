package compiler.structures.statements;

import compiler.structures.expressions.VariableCall;

public class PutStatement extends Statement {

    private VariableCall variableCall;

    public PutStatement(int line) {
        super(line);
    }

    public void setVariableCall(VariableCall variableCall) {
        this.variableCall = variableCall;
    }

    @Override
    public void generateCode() {

    }

}
