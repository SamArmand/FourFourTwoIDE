package compiler.structures;

import ui.Outputter;

import java.util.ArrayList;

public class Global {

    private static ArrayList<Class> classes = new ArrayList<>();
    private static Function program;
    private static ArrayList<Function> functions = new ArrayList<>();

    public static void insert(Class newClass) {

        if (exists(newClass.getName()))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", newClass.getLine()))
                    .append("Duplicate declaration of class ").append(newClass.getName()).append("\n");

        else
            classes.add(newClass);

    }

    public static void insert(Function function) {

        if (exists(function))
            Outputter.errorStrings.append(String.format("Error | Line: %-5s | ", function.getLine()))
                    .append("Duplicate declaration of function ").append(function.getSignature()).append("\n");

        else if (function.getType() != null)
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

    public static boolean exists(String typeName) {

        for (Class c : classes)
            if (c.getName().equals(typeName))
                return true;

        return false;

    }

    public static boolean exists(Function function) {

        for (Function f : functions)
            if (f.equals(function))
                return true;

        return false;

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

    public static void generateCode() {

    }

    public static void checkTypes() {
        classes.forEach(Class::checkTypes);
        program.checkTypes();
        functions.forEach(Function::checkTypes);
    }
}
