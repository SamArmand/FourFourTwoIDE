package compiler.structures.statements;

import compiler.structures.expressions.VariableCall;

public class GetStatement extends Statement {

    private VariableCall variableCall;

    public GetStatement(int line) {
        super(line);
    }

    public void setVariableCall(VariableCall variableCall) {
        this.variableCall = variableCall;
    }

    @Override
    public void generateCode() {

    }

}
