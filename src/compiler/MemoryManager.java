package compiler;

//not using this
//was going to but was unsure how to do it
public class MemoryManager {

    private static long memory;

    public static long getMemory(int increment) {
        return memory += increment;
    }

    public static void reset() {
        memory = 0;
    }

}
