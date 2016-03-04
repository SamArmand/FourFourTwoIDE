package compiler.structures;

import compiler.Outputter;

import java.util.ArrayList;

public class Function {

    private Class type;
    private Class parent;
    private String name;
    private ArrayList<Variable> parameters;
    private ArrayList<Variable> variables;
    private ArrayList<Statement> statements;
    private int line;

    public Function(int line) {
        parameters = new ArrayList<>();
        variables = new ArrayList<>();
        statements = new ArrayList<>();
        this.line = line;
    }

    public void insertParameter(Variable parameter) {

        if (getVariable(parameter) != null) {
            Outputter.errorStrings.append("Duplicate declaration of parameter ").append(parameter.getName())
                    .append(" at line ").append(parameter.getLine()).append("\n");
            return;
        }

        parameters.add(parameter);


    }

    public void insertVariable(Variable variable) {

        if (getVariable(variable) != null) {
            Outputter.errorStrings.append("Duplicate declaration of variable ").append(variable.getName())
                    .append(" at line ").append(variable.getLine()).append("\n");
            return;
        }

        variables.add(variable);

    }

    public boolean equals(Function function) {
        return (name.equals(function.getName())
                & parameters.size() == function.getParameters().size());
    }

    public void setType(String typeString) {
        type = Global.getClassDefinition(typeString);
    }

    public Class getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Variable> getParameters() {
        return parameters;
    }

    public int getLine() {
        return line;
    }

    public void setParent(Class parent) {
        this.parent = parent;
    }

    public Variable getVariable(Variable variable) {

        for (Variable v : variables)
            if (v.equals(variable))
                return v;
        for (Variable parameter : parameters)
            if (parameter.equals(variable))
                return parameter;

        return null;

    }

    public Variable getVariableCall(Variable variable) {

        Variable v = getVariable(variable);

        if (v == null)
            return parent.getVariable(variable);

        return v;

    }

    public Function getFunctionCall(Function function) {

        return parent.getFunctionCall(function);

    }

}
