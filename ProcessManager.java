import java.util.ArrayList;
import java.util.LinkedList;

public class ProcessManager {
    public MemoryManager mm;
    public LinkedList<PCB> readyQueue;
    private static int id = 0;

    public ProcessManager(MemoryManager mm, LinkedList<PCB> readyQueue) {
        this.mm = mm;
        this.readyQueue = readyQueue;
    }

    public void build(Word[] program) {
        ArrayList<Integer> pagesAllocated = mm.allocate(program);
        PCB process = new PCB(id, pagesAllocated);
        id++;
        readyQueue.add(process);
        //readyQueue.forEach((e) -> System.out.println(e));

    }

    public void endProcess(PCB processo) {
        mm.unallocate(processo);
        readyQueue.remove(processo);
    }
}