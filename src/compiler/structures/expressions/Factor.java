package compiler.structures.expressions;

import compiler.structures.Class;
import compiler.structures.Codeable;
import compiler.structures.Global;
import ui.Outputter;

public class Factor implements Codeable {

    private String unary;
    private Factor factor;

    private String number;

    private VariableCall variableCall;
    private FunctionCall functionCall;

    private ArithmeticExpression arithmeticExpression;

    private Class resolvedType;

    private int line;

    public void setUnary(String unary) {
        this.unary = unary;
    }

    public void setFactor(Factor factor) {

        this.factor = factor;

    }

    public void setNumber(String number) {

        this.number = number;

        if (number.contains("."))
            resolvedType = Global.getClassDefinition("float");
        else
            resolvedType = Global.getClassDefinition("int");
    }

    public void setVariableCall(VariableCall variableCall) {

        this.variableCall = variableCall;

        resolvedType = variableCall.getResolvedType();

    }

    public void setFunctionCall(FunctionCall functionCall) {

        this.functionCall = functionCall;

        resolvedType = functionCall.getResolvedType();
    }

    public void setArithmeticExpression(ArithmeticExpression arithmeticExpression) {
        this.arithmeticExpression = arithmeticExpression;

        resolvedType = arithmeticExpression.getResolvedType();
    }

    public Class getResolvedType() {
        return resolvedType;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        if (factor != null) {

            if (factor.getResolvedType() == null)
                factor.checkTypes();

            if (factor.getResolvedType() == null)
                return;

            if ((unary.equals("+") || unary.equals("-")) && !factor.resolvedType.isNumber())
                Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                        .append("Cannot apply unary operator ").append(unary)
                        .append(" to factor of type ").append(factor.resolvedType.getName()).append("\n");

        }

        if (functionCall != null) {

            if (functionCall.getResolvedType() == null)
                functionCall.checkTypes();

            resolvedType = functionCall.getResolvedType();

        }

        if (arithmeticExpression != null) {

            arithmeticExpression.checkTypes();

            resolvedType = arithmeticExpression.getResolvedType();

        }



    }

}
