import java.util.ArrayList;

public class Context {
    private ArrayList<Integer> allocatedPages;
    private int[] reg;
    private int pc;
    private Word ir;

    public Context(ArrayList<Integer> allocatedPages, int[] reg, int pc, Word ir) {
        this.allocatedPages = allocatedPages;
        this.reg = reg;
        this.pc = pc;
        this.ir = ir;
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
}