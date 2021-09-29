import java.util.LinkedList;

public class Scheduler {
    public LinkedList<PCB> readyQueue;

    public Scheduler(LinkedList<PCB> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public PCB schedule() {
        PCB process = readyQueue.getFirst();

        readyQueue.removeFirst();

        return process;
    }

}