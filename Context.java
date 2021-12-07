import java.util.ArrayList;

public class Context {
    private ArrayList<Integer> allocatedPages;
    private int[] reg;
    private int pc;
    private Word ir;
    private int id;

    public Context(ArrayList<Integer> allocatedPages, int[] reg, int pc, Word ir, int id) {
        this.allocatedPages = allocatedPages;
        this.reg = reg;
        this.pc = pc;
        this.ir = ir;
        this.id = id;
    }

    public ArrayList<Integer> getAllocatedPages() {
        return allocatedPages;
    }

    public int[] getReg() {
        return reg;
    }

    public int getPc() {
        return pc;
    }

    public Word getIr() {
        return ir;
    }

    public int getProcessId() {
        return id;
    }
}