package compiler.structures;

import ui.Outputter;

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

        if (exists(variable)) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", variable.getLine()))
                    .append("Duplicate declaration of variable ").append(variable.getName()).append("\n");
            return;
        }

        variables.add(variable);

    }

    public void insert(Function function) {

        if (exists(function)) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", function.getLine()))
                    .append("Duplicate declaration of function ").append(function.getName())
                    .append(" with same parameters\n");
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

    public boolean exists(Variable variable) {

        for (Variable v : variables)
            if (v.getName().equals(variable.getName()))
                return true;

        return false;

    }

    public boolean exists(Function function) {

        for (Function f : functions)
            if (f.equals(function))
                return true;

        return false;

    }

    public boolean equals(Class aClass) {
        return name.equals(aClass.name);
    }


    public String print() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CLASS: ").append(name).append("\n");

        for (Variable variable : variables)
            stringBuilder.append(variable.print("Variable", "\t"));
        for (Function function : functions)
            stringBuilder.append(function.print("\t"));

        return stringBuilder.toString();


    }

}
