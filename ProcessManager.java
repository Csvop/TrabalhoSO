import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ProcessManager {
    public MemoryManager mm;
    public LinkedList<PCB> readyQueue;
    private static int newProcessId = 0;
    Semaphore mutex = new Semaphore(1);
    Semaphore semESC;

    public ProcessManager(MemoryManager mm, LinkedList<PCB> readyQueue, Semaphore semESC) {
        this.mm = mm;
        this.readyQueue = readyQueue;
        this.semESC = semESC;
    }

    public void createProcess(Word[] program) {
        mutex.acquireUninterruptibly();
        ArrayList<Integer> allocatedPages = mm.allocate(program);
        PCB pcb = new PCB(newProcessId, allocatedPages);
        newProcessId++;

        if (readyQueue.isEmpty()) {
            readyQueue.addLast(pcb);
            semESC.release();
        } else {
            readyQueue.addLast(pcb);
        }
        mutex.release();
    }

    public void endProcess(PCB processo) {
        mutex.acquireUninterruptibly();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        mm.printMemorySegment(0, 100);
        mm.unallocate(processo);
        readyQueue.remove(processo);
        mutex.release();
    }
}