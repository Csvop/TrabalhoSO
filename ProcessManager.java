import java.util.ArrayList;
import java.util.LinkedList;

public class ProcessManager {
    public MemoryManager mm;
    public LinkedList<PCB> readyQueue;
    private static int newProcessId = 0;

    public ProcessManager(MemoryManager mm, LinkedList<PCB> readyQueue) {
        this.mm = mm;
        this.readyQueue = readyQueue;
    }

    public void createProcess(Word[] program) {
        ArrayList<Integer> allocatedPages = mm.allocate(program);
        PCB newProcess = new PCB(newProcessId, allocatedPages);
        newProcessId++;
        readyQueue.addLast(newProcess);
        //readyQueue.forEach((e) -> System.out.println(e));
    }

    public void endProcess(PCB processo) {
        mm.unallocate(processo);
        readyQueue.remove(processo);
    }
}