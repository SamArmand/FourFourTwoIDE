package compiler.structures.expressions;

import ui.Outputter;

public class ArithmeticExpression extends Expression {

    private Term term;
    private String operator;
    private ArithmeticExpression arithmeticExpression;

    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setArithmeticExpression(ArithmeticExpression arithmeticExpression) {

        this.arithmeticExpression = arithmeticExpression;

    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        term.checkTypes();

        if (term.getResolvedType() == null)
            return;

        setResolvedType(term.getResolvedType());

        if (arithmeticExpression == null)
            return;

        if (arithmeticExpression.getResolvedType() == null)
            arithmeticExpression.checkTypes();

        if (arithmeticExpression.getResolvedType() == null)
            return;

        if (!(term.getResolvedType().isNumber() && arithmeticExpression.getResolvedType().isNumber()))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", getLine()))
                    .append("Cannot evaluate arithmetic expression between types ").append(term.getResolvedType().getName())
                    .append(" and ").append(arithmeticExpression.getResolvedType().getName()).append("\n");

        if (getResolvedType().getName().equals("int")
                && arithmeticExpression.getResolvedType().getName().equals("float"))
            setResolvedType(arithmeticExpression.getResolvedType());

    }

}
