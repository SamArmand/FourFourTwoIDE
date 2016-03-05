package compiler.structures;

import ui.Outputter;

import java.util.ArrayList;

public class Variable {

    private Class type;
    private String name;
    private int line;
    private ArrayList<Integer> dimensions;

    public Variable(int line) {
        dimensions = new ArrayList<>();
        this.line = line;
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

    public String print(String varType, String tabs) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(tabs).append(varType).append(": ")
                .append(name).append(" TYPE: ").append(type.getName());

        for (Integer dimension : dimensions)
            stringBuilder.append("[").append(dimension.intValue()).append("]");

        stringBuilder.append("\n");

        return stringBuilder.toString();

    }
}
