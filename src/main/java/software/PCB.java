package software;

import hardware.Interrupt;

import java.util.ArrayList;

public class PCB {
    public int id;
    public ArrayList<Integer> allocatedPages;
    
    // CPU context
    public int pc;
    public int[] reg;
    public Status status;
    public Interrupt interrupt;

    public PCB(int id, ArrayList<Integer> allocatedPages) {
        this.allocatedPages = allocatedPages;
        this.id = id;
        this.pc = 0;
        this.status = Status.READY;
        this.reg = new int[10];
        this.interrupt = Interrupt.NONE;
    }

    //retorna a lista de paginas de um processo
    public ArrayList<Integer> getAllocatedPages() {
        return this.allocatedPages;
    }

    public int getId() {
        return this.id;
    }

}
