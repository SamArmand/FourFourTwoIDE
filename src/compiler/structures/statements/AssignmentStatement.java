package compiler.structures.statements;

import compiler.structures.expressions.Expression;
import compiler.structures.expressions.VariableCall;
import ui.Outputter;

public class AssignmentStatement extends Statement {

    private VariableCall variableCall;
    private Expression expression;

    public AssignmentStatement(int line) {
        super(line);
    }

    public void setVariableCall(VariableCall variableCall) {
        this.variableCall = variableCall;
    }

    public void setExpression(Expression expression) {

        this.expression = expression;
    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        expression.checkTypes();

        if (expression.getResolvedType() == null)
            return;

        if (!expression.getResolvedType().equals(variableCall.getResolvedType())) {

            if (!variableCall.getResolvedType().getName().equals("?")
                    && !expression.getResolvedType().getName().equals("?"))
                Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", getLine()))
                        .append("Cannot assign value of type ").append(expression.getResolvedType().getName())
                        .append(" to a variable of type ").append(variableCall.getResolvedType().getName()).append("\n");

        }

    }

}
