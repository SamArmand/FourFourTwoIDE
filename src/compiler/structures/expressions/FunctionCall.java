package compiler.structures.expressions;

import compiler.structures.*;
import compiler.structures.Class;
import ui.Outputter;

import java.util.ArrayList;

public class FunctionCall implements Codeable {

    private ArrayList<Variable> variableNest;
    private Function function;
    private ArrayList<Expression> parameters;

    private int line;

    private Function currentScope;

    public FunctionCall(Function currentScope) {

        parameters = new ArrayList<>();
        this.currentScope = currentScope;
        function = new Function(0);
    }

    public void setFunctionName(String name) {
        function.setName(name);
    }

    public Class getResolvedType() {
        return function.getType();
    }

    public void setVariableNest(ArrayList<Variable> variableNest) {
        this.variableNest = variableNest;
    }

    public ArrayList<Expression> getParameters() {
        return parameters;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void bindFunction() {

        for (Expression expression : parameters) {

            Variable parameter = new Variable(0);
            parameter.setType(expression.getResolvedType().getName());

            function.getParameters().add(parameter);

        }


        if (variableNest.size() == 0) {

            Function calledFunction = currentScope.getFunctionCall(function);

            if (calledFunction != null)
                this.function = calledFunction;

            else
                Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                        .append("Function ").append(function.getSignature())
                        .append(" is not in scope\n");

        }

        else {

            Class scope = variableNest.get(variableNest.size()-1).getType();

            Function calledFunction = scope.getFunctionCall(function);

            if (calledFunction != null)
                this.function = calledFunction;

            else
                Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                        .append("No member function ").append(function.getSignature())
                        .append(" is defined for class ").append(scope.getName()).append("\n");

        }

    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        parameters.forEach(Expression::checkTypes);

        for (Expression parameter : parameters) {
            if (parameter.getResolvedType() == null)
                return;
        }

        bindFunction();

    }

}
