import java.util.Queue;

public class Scheduler {
    public Queue<PCB> readyQueue;

    public Scheduler(Queue<PCB> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public PCB schedule() {
        PCB pcb = readyQueue.remove();
        return pcb;
    }
}
