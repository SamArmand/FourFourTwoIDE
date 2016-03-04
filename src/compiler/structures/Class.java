package compiler.structures;

import compiler.Outputter;

import java.util.ArrayList;

public class Class {

    private String name;
    private ArrayList<Variable> variables;
    private ArrayList<Function> functions;
    private int line;

    public Class(int line) {
        variables = new ArrayList<>();
        functions = new ArrayList<>();
        this.line = line;
    }

    public void insert(Variable variable) {

        if (getVariable(variable) != null) {
            Outputter.errorStrings.append("Duplicate declaration of variable ").append(variable.getName())
                    .append(" at line ").append(variable.getLine()).append("\n");
            return;
        }

        variables.add(variable);

    }

    public void insert(Function function) {

        if (getFunction(function) != null) {
            Outputter.errorStrings.append("Duplicate declaration of function ").append(function.getName())
                    .append(" with ").append(function.getParameters().size()).append(" parameters at line ")
                    .append(function.getLine()).append("\n");
            return;
        }

        function.setParent(this);
        functions.add(function);
    }

    public Function getFunction(Function function) {

        for (Function f : functions)
            if (f.equals(function))
                return f;

        return null;

    }

    public Function getFunctionCall(Function function) {

        Function f = getFunction(function);

        if (f == null)
            return Global.getFunction(function);

        return f;

    }

    public Variable getVariable(Variable variable) {

        for (Variable v : variables)
            if (v.equals(variable))
                return v;

        return null;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLine() {
        return line;
    }

}
