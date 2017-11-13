package compiler.structures;

import ui.Outputter;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Variable implements Codeable {

    private Class type;
    private String name;
    private int line;
    private ArrayList<Integer> dimensions;
    private long address;

    private int size;

    public Variable(int line) {
        dimensions = new ArrayList<>();
        this.line = line;
    }

    Variable(Variable variable) {
        this.type = variable.type;
        this.name = variable.name;
        this.line = variable.line;
        this.dimensions = new ArrayList<>();

        dimensions.addAll(variable.dimensions.stream().map(Integer::intValue).collect(Collectors.toList()));

        this.address = variable.address;
    }

    public void setType(String typeName) {

        for (char c : typeName.toCharArray()) {
            if (c == '[')
                dimensions.add(0);
        }

        type = Global.getClassDefinition(typeName.substring(0, dimensions.size() > 0 ? typeName.indexOf("[") : typeName.length()));

        if (type == null)
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", line))
                .append("Invalid type ").append(typeName).append("\n");

    }

    public Class getType() {

        if (dimensions.size() == 0)
            return type;

        StringBuilder actualType = new StringBuilder(type.getName());

        for (int i = 0; i < dimensions.size(); ++i)
            actualType.append("[]");

        Class variableType = new Class(0);

        variableType.setName(actualType.toString());

        return variableType;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public String getNameAndDimensions() {

        StringBuilder nameAndDimensions = new StringBuilder(name);

        for (Integer ignored : dimensions)
            nameAndDimensions.append("[]");

        return nameAndDimensions.toString();

    }

    public ArrayList<Integer> getDimensions() {
        return dimensions;
    }

    public int getLine() {
        return line;
    }

    public boolean equals(Variable variable) {
        return (name.equals(variable.name)
                && (variable.dimensions.size() <= dimensions.size()));
    }

    public long getAddress() {
        return address;
    }

    String print(String varType, String tabs) {

        return tabs + varType + ": " +
                name + " TYPE: " + getType().getName() +
                "\n";

    }

    //codegen stuff

    @Override
    public void generateCode() {

        Outputter.moonCodeStrings.append(name).append("\tres\t").append(getSize()).append("\n");

    }

    @Override
    public void checkTypes() {

    }

    int getSize() {

        int size = type.getSize();

        for (Integer dimension: dimensions)
            size *= dimension;

        return size;
    }

}
