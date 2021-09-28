package software;

import hardware.Word;
import java.util.ArrayList;


public class PCB {
    public int id;
    public ArrayList<Integer> allocatedPages;
    
    // CPU context
    public int pc;
    public int[] reg;
    public Word ir;

    public PCB(int id, ArrayList<Integer> allocatedPages) {
        this.id = id;
        this.allocatedPages = allocatedPages;
        this.reg = new int[10];
        this.pc = 0;
        this.ir = Word.BLANK;
    }

    //retorna a lista de paginas de um processo
    public ArrayList<Integer> getAllocatedPages() {
        return this.allocatedPages;
    }

    public int getId() {
        return this.id;
    }

}
