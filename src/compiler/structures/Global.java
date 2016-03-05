package compiler.structures;

import ui.Outputter;

import java.util.ArrayList;

public class Global {

    private static ArrayList<Class> classes = new ArrayList<>();
    private static Function program;
    private static ArrayList<Function> functions = new ArrayList<>();

    public static void insert(Class newClass) {

        if (getClassDefinition(newClass.getName()) != null) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", newClass.getLine()))
                    .append("Duplicate declaration of class ").append(newClass.getName()).append("\n");
            return;
        }

        classes.add(newClass);

    }

    public static void insert(Function function) {

        if (getFunction(function) != null) {
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", function.getLine()))
                    .append("Duplicate declaration of function ").append(function.getName()).append("\n");
            return;
        }

        functions.add(function);
    }

    public static void setProgram(Function function) {
        program = function;
    }

    public static Class getClassDefinition(String typeName) {

        for (Class c : classes)
            if (c.getName().equals(typeName))
                return c;

        return null;

    }

    public static Function getFunction(Function function) {

        for (Function f : functions)
            if (f.equals(function))
                return f;

        return null;

    }

    public static void clear() {
        classes = new ArrayList<>();
        program = null;
        functions = new ArrayList<>();
    }

    public static void print() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Class c : classes)
            stringBuilder.append(c.print());
        if (program != null)
            stringBuilder.append(program.print(""));
        for (Function function : functions)
            stringBuilder.append(function.print(""));

        Outputter.symbolTablesStrings.append(stringBuilder.toString());

    }

}
