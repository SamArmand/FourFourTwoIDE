package compiler.structures;

import compiler.structures.statements.Statement;
import ui.Outputter;

import java.util.ArrayList;

public class Function implements Codeable {

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

        if (parameter.getType() == null)
            return;

        parameters.add(parameter);

    }

    public void insertVariable(Variable variable) {

        if (exists(variable)) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", variable.getLine()))
                    .append("Duplicate declaration of variable ").append(variable.getName()).append("\n");
            return;
        }

        if (variable.getType() == null)
            return;

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

        //TODO check for subArray!!!!

        ArrayList<Variable> allVariables = new ArrayList<>();
        allVariables.addAll(variables);
        allVariables.addAll(parameters);

        for (Variable v : allVariables) {
            if (v.equals(variable)) {
                Variable returnedVariable = new Variable(v);
                for (Integer ignored : variable.getDimensions())
                    returnedVariable.getDimensions().remove(0);
                return returnedVariable;
            }
        }

        return null;

    }

    public Variable getVariableCall(Variable variable) {

        Variable v = getVariable(variable);

        if (v == null && parent != null)
            return parent.getVariable(variable);

        return v;

    }

    public Function getFunctionCall(Function function) {

        Function f = null;

        if (parent != null)
            f = parent.getFunctionCall(function);

        if (f == null)
            f = Global.getFunction(function);

        return f;

    }

    public String getSignature() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("(");

        if (parameters.size() > 0)
            stringBuilder.append(parameters.get(0).getType().getName());

        for (int i = 1; i < parameters.size(); ++i)
            stringBuilder.append(", ").append(parameters.get(i).getType().getName());


        stringBuilder.append(")");

        return stringBuilder.toString();

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

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    @Override
    public void generateCode() {

    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    @Override
    public void checkTypes() {
        statements.forEach(Statement::checkTypes);
    }

}
