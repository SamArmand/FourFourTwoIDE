package compiler.structures;

import compiler.Outputter;

import java.util.ArrayList;

public class Global {

    private static ArrayList<Class> classes = new ArrayList<>();
    private static Function program;
    private static ArrayList<Function> functions = new ArrayList<>();

    public static void insert(Class newClass) {

        if (getClassDefinition(newClass.getName()) != null) {
            Outputter.errorStrings.append("Duplicate declaration of class ").append(newClass.getName())
                    .append("  at line ").append(newClass.getLine()).append("\n");
            return;
        }

        classes.add(newClass);

    }

    public static void insert(Function function) {

        if (getFunction(function) != null) {
            Outputter.errorStrings.append("Duplicate declaration of function ").append(function.getName())
                    .append("  at line ").append(function.getLine()).append("\n");
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

}
