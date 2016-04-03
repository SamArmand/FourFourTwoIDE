package compiler.structures.statements;

import compiler.structures.expressions.Expression;

import java.util.ArrayList;

public class IfStatement extends Statement {

    private Expression expression;
    private ArrayList<Statement> thenStatementBlock, elseStatementBlock;

    public IfStatement(int line) {
        super(line);
        thenStatementBlock = new ArrayList<>();
        elseStatementBlock = new ArrayList<>();
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public ArrayList<Statement> getThenStatementBlock() {
        return thenStatementBlock;
    }

    public ArrayList<Statement> getElseStatementBlock() {
        return elseStatementBlock;
    }

    @Override
    public void checkTypes() {

        expression.checkTypes();

        thenStatementBlock.forEach(Statement::checkTypes);

        elseStatementBlock.forEach(Statement::checkTypes);

    }

}
