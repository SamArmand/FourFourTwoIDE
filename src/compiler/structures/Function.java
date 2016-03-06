package compiler.structures;

import ui.Outputter;

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

        if (exists(parameter)) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", parameter.getLine()))
                    .append("Duplicate declaration of parameter ").append(parameter.getName()).append("\n");
            return;
        }

        parameters.add(parameter);


    }

    public void insertVariable(Variable variable) {

        if (exists(variable)) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", variable.getLine()))
                    .append("Duplicate declaration of variable ").append(variable.getName()).append("\n");
            return;
        }

        variables.add(variable);

    }

    public boolean equals(Function function) {

        if (name.equals(function.getName())
                && parameters.size() == function.getParameters().size()) {

            for (int i = 0; i < parameters.size(); ++i) {

                if (!parameters.get(i).getType().equals(function.parameters.get(i).getType()))
                    return false;

            }

            return true;


        }

        return false;

    }

    public void setType(String typeName) {

        type = Global.getClassDefinition(typeName);

        if (type == null)
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                .append("Invalid type ").append(typeName).append("\n");


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

    public boolean exists(Variable variable) {

        for (Variable v : variables)
            if (v.getName().equals(variable.getName()))
                return true;
        for (Variable parameter : parameters)
            if (parameter.getName().equals(variable.getName()))
                return true;

        return false;

    }

    public String print(String tabs) {

        StringBuilder stringBuilder = new StringBuilder();

        String newTabs = tabs + '\t';

        stringBuilder.append(tabs).append("FUNCTION: ").append(name == null ? "program" : name)
                .append(" RETURNS: ").append(type == null ? "void" : type.getName()).append("\n");
        for (Variable parameter : parameters)
            stringBuilder.append(tabs).append(parameter.print("PARAMETER", newTabs));
        for (Variable variable : variables)
            stringBuilder.append(variable.print("VARIABLE", newTabs));


        return stringBuilder.toString();


    }



}
