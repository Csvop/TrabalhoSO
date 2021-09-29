import java.util.LinkedList;

public class Scheduler {
    public LinkedList<PCB> readyQueue;
    public int aux;

    public Scheduler(LinkedList<PCB> readyQueue) {
        this.readyQueue = readyQueue;
        this.aux = 0;
    }

    public PCB schedule() {
        if(aux >= readyQueue.size()) {
            aux = 0;
        }

        PCB pcb = readyQueue.get(aux);
        int old = aux;

        aux = (aux + 1) % readyQueue.size();
        readyQueue.remove(old);

        return pcb;
    }
}