package compiler.structures.statements;

import compiler.structures.Variable;
import compiler.structures.expressions.Expression;

import java.util.ArrayList;

public class ForStatement extends Statement {

    private Variable variable;
    private AssignmentStatement initialAssignmentStatement;
    private Expression expression;
    private AssignmentStatement loopAssignmentStatement;
    private ArrayList<Statement> loopStatements;

    public ForStatement(int line) {
        super(line);
        loopStatements = new ArrayList<>();
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public void setInitialAssignmentStatement(AssignmentStatement initialAssignmentStatement) {
        this.initialAssignmentStatement = initialAssignmentStatement;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public void setLoopAssignmentStatement(AssignmentStatement loopAssignmentStatement) {
        this.loopAssignmentStatement = loopAssignmentStatement;
    }

    public ArrayList<Statement> getLoopStatements() {
        return loopStatements;
    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        initialAssignmentStatement.checkTypes();
        expression.checkTypes();
        loopAssignmentStatement.checkTypes();

    }

}
