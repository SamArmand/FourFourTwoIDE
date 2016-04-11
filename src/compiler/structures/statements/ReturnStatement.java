package compiler.structures.statements;

import compiler.structures.Function;
import compiler.structures.expressions.Expression;
import ui.Outputter;

public class ReturnStatement extends Statement {

    private Expression expression;
    private Function currentFunction;

    public ReturnStatement(int line, Function currentFunction) {
        super(line);
        this.currentFunction = currentFunction;
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

        if (!currentFunction.getType().equals(expression.getResolvedType()))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", getLine()))
                    .append("Type mismatch between returned type ").append(expression.getResolvedType().getName())
                    .append(" and function return type ").append(currentFunction.getType().getName()).append("\n");

    }

}
