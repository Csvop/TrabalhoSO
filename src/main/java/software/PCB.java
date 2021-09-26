package software;

import hardware.Interrupt;

import java.util.ArrayList;

public class PCB {
    public int id;
    public Interrupt interrupt;
    public ArrayList<Integer> allocatedPages;

    // CPU context
    public int pc;
    public Status status;
    public int[] reg;

    public PCB(int id, ArrayList<Integer> allocatedPages) {
        this.allocatedPages = allocatedPages;
        this.id = id;
        this.interrupt = Interrupt.NONE;
        this.pc = 0;
        this.status = Status.READY;
        this.reg = new int[10];
    }

    //retorna a lista de paginas de um processo
    public ArrayList<Integer> getAllocatedPages() {
        return this.allocatedPages;
    }

    public int getId() {
        return this.id;
    }

}

enum Status {
    READY, BLOCKED, RUNNING, DONE
}
