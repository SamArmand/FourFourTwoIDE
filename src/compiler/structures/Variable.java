package compiler.structures;

import ui.Outputter;

import java.util.ArrayList;

public class Variable implements Codeable {

    private Class type;
    private String name;
    private int line;
    private ArrayList<Integer> dimensions;
    private long address;

    public Variable(int line) {
        dimensions = new ArrayList<>();
        this.line = line;
    }

    public Variable(Variable variable) {
        this.type = variable.type;
        this.name = variable.name;
        this.line = variable.line;
        this.dimensions = variable.dimensions;
        this.address = variable.address;
    }

    public void setType(String typeName) {
        type = Global.getClassDefinition(typeName);

        if (type == null)
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                .append("Invalid type ").append(typeName).append("\n");

    }

    public Class getType() {

        if (dimensions.size() == 0)
            return type;

        String dimensionString = "";

        for (int i = 0; i < dimensions.size(); ++i)
            dimensionString += "[]";

        Class variableType = new Class(0);

        variableType.setName(type.getName()+dimensionString);

        return variableType;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getDimensions() {
        return dimensions;
    }

    public int getLine() {
        return line;
    }

    public boolean equals(Variable variable) {
        return (name.equals(variable.name)
                && (dimensions.size() <= variable.dimensions.size()));
    }

    public long getAddress() {
        return address;
    }

    public String print(String varType, String tabs) {

        return tabs + varType + ": " +
                name + " TYPE: " + getType().getName() +
                "\n";

    }

    @Override
    public void generateCode() {

    }

    @Override
    public void checkTypes() {

    }

}
