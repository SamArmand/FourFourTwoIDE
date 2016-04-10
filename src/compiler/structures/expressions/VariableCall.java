package compiler.structures.expressions;

import compiler.structures.Class;
import compiler.structures.Codeable;
import compiler.structures.Function;
import compiler.structures.Variable;
import ui.Outputter;

import java.util.ArrayList;

public class VariableCall implements Codeable {

    private ArrayList<Variable> variableNest;
    private ArrayList<ArrayList<ArithmeticExpression>> indiceLists;
    private Function currentScope;

    private int line;

    public VariableCall(Function currentScope) {
        variableNest = new ArrayList<>();
        indiceLists = new ArrayList<>();
        this.currentScope = currentScope;
    }

    public ArrayList<Variable> getVariableNest() {
        return variableNest;
    }

    public ArrayList<ArrayList<ArithmeticExpression>> getIndiceLists() {
        return indiceLists;
    }

    public void addVariable(Variable variable) {

        if (variableNest.size() == 0) {

            Variable calledVariable = currentScope.getVariableCall(variable);

            if (calledVariable != null)
                variableNest.add(calledVariable);

            else {
                Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                        .append("Variable ").append(variable.getNameAndDimensions())
                        .append(" is not in scope\n");
            }

        }

        else {

            Class scope = variableNest.get(variableNest.size()-1).getType();

            Variable calledVariable = scope.getVariable(variable);

            if (calledVariable != null)
                variableNest.add(calledVariable);

            else {
                Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                        .append("No member variable ").append(variable.getNameAndDimensions())
                        .append(" is defined for class ").append(scope.getName()).append("\n");
            }

        }

    }

    public Class getResolvedType() {

        if (variableNest.isEmpty()) {
            Class undefined = new Class(0);
            undefined.setName("?");
            return undefined;
        }


        Class type = new Class (0);

        type.setName(variableNest.get(variableNest.size()-1).getType().getName());


        return type;

    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

        for (ArrayList<ArithmeticExpression> indiceList : indiceLists) {

            indiceList.forEach(ArithmeticExpression::checkTypes);

        }

    }

}
