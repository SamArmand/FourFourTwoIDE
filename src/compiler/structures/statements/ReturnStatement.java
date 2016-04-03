package compiler.structures.statements;

import compiler.structures.expressions.Expression;

public class ReturnStatement extends Statement {

    private Expression expression;

    public ReturnStatement(int line) {
        super(line);
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
    }

}
