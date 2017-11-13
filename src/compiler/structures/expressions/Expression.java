package compiler.structures.expressions;

import compiler.structures.Class;
import compiler.structures.Codeable;
import ui.Outputter;

public class Expression implements Codeable {

    private Class resolvedType;

    private ArithmeticExpression leftOperand, rightOperand;
    private String operator;
    private int line;

    public void setLeftOperand(ArithmeticExpression leftOperand) {

        this.leftOperand = leftOperand;

    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setRightOperand(ArithmeticExpression rightOperand) {

        this.rightOperand = rightOperand;

    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    void setResolvedType(Class resolvedType) {
        this.resolvedType = resolvedType;
    }

    public Class getResolvedType() {
        return resolvedType;
    }

    @Override
    public void generateCode() {

    }

    public void checkTypes() {

        leftOperand.checkTypes();

        if (leftOperand.getResolvedType() == null)
            return;

        resolvedType = leftOperand.getResolvedType();

        if (rightOperand == null)
            return;

        resolvedType = new Class(0);
        resolvedType.setName("boolean");

        if (rightOperand.getResolvedType() == null)
            rightOperand.checkTypes();

        if (rightOperand.getResolvedType() != null && (!(rightOperand.getResolvedType().isNumber() && leftOperand.getResolvedType().isNumber())))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line)).append("Cannot compare expression of type ").append(leftOperand.getResolvedType().getName()).append(" with expression of type ").append(rightOperand.getResolvedType().getName()).append("\n");

    }

}
