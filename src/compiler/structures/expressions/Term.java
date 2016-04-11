package compiler.structures.expressions;

import compiler.structures.Class;
import compiler.structures.Codeable;
import ui.Outputter;

public class Term implements Codeable {

    private Factor leftOperand;
    private String operator;
    private Term rightOperand;
    private int line;

    private Class resolvedType;

    public void setLeftOperand(Factor leftOperand) {

        this.leftOperand = leftOperand;
        resolvedType = leftOperand.getResolvedType();

    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setRightOperand(Term rightOperand) {

        this.rightOperand = rightOperand;

    }

    public void setLine(int line) {
        this.line = line;
    }

    Class getResolvedType() {
        return resolvedType;
    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        leftOperand.checkTypes();

        if (leftOperand.getResolvedType() == null)
            return;

        resolvedType = leftOperand.getResolvedType();

        if (rightOperand == null)
            return;

        rightOperand.checkTypes();

        if (rightOperand.resolvedType == null)
            return;

        if (!(rightOperand.getResolvedType().isNumber() && leftOperand.getResolvedType().isNumber()))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                    .append("Cannot evaluate arithmetic expression between types ").append(leftOperand.getResolvedType().getName())
                    .append(" and ").append(rightOperand.resolvedType.getName()).append("\n");

        if (resolvedType.getName().equals("int")
                && rightOperand.resolvedType.getName().equals("float"))
            resolvedType = rightOperand.resolvedType;

    }


}
