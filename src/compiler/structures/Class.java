package compiler.structures;

import ui.Outputter;

import java.util.ArrayList;

public class Class implements Codeable {

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
        if (exists(variable))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", variable.getLine()))
                    .append("Duplicate declaration of variable ").append(variable.getName()).append("\n");

        if (variable.getType() != null)
            variables.add(variable);
    }

    public void insert(Function function) {
        if (exists(function))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", function.getLine()))
                    .append("Duplicate declaration of function ").append(function.getSignature())
                    .append(" with same parameters\n");

        if (function.getType() != null) {
            //function.setParent(this);
            functions.add(function);
        }
    }

    private Function getFunction(Function function) {
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
            if (v.equals(variable)) {

                if (variable.getDimensions() == v.getDimensions())
                    return v;

                Variable subArray = new Variable(v);
                for (Integer ignored : variable.getDimensions())
                    subArray.getDimensions().remove(0);

                return subArray;
            }

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

    private boolean exists(Variable variable) {
        for (Variable v : variables)
            if (v.getName().equals(variable.getName()))
                return true;

        return false;
    }

    private boolean exists(Function function) {
        for (Function f : functions)
            if (f.equals(function))
                return true;

        return false;
    }

    public boolean equals(Class aClass) {
        return name.equals(aClass.name);
    }

    String print() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CLASS: ").append(name).append("\n");

        for (Variable variable : variables)
            stringBuilder.append(variable.print("Variable", "\t"));
        for (Function function : functions)
            stringBuilder.append(function.print("\t"));

        return stringBuilder.toString();
    }

    public boolean isNumber() {

        return (name.equals("int") || name.equals("float"));

    }


    //codegen stuff


    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {
        functions.forEach(Function::checkTypes);
    }

    int getSize() {
        int size = 0;

        for (Variable variable: variables)
            size += variable.getSize();

        if (name.equals("int"))
            size = 1;
        else if (name.equals("float"))
            size = 2;

        return size;

    }

}
