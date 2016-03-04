package compiler.structures;

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
}
