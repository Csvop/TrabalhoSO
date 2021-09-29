import java.util.LinkedList;
import java.util.Queue;

public class VM {
    //Hardwares
    public int tamMem;    
    public Word[] mem;
    public CPU cpu;

    //Software - Gerenciadores
    public MemoryManager mm;
    public ProcessManager pm;
    public Scheduler scheduler;
    public Queue<PCB> readyQueue;
    public Routine routine;

    public VM(){
		//Hardware
        blankMemory(1024);
		cpu = new CPU();

        //Software
        readyQueue = new LinkedList<PCB>();
        scheduler = new Scheduler(readyQueue);
        mm = new MemoryManager(this.mem);
        pm = new ProcessManager(mm, readyQueue);
        this.routine = new Routine(pm, scheduler);
	}

    public void load(Word[] program) {
        pm.build(program);
    }

	public void dump(int ini, int fim) {
		for (int i = ini; i < fim; i++) {
			System.out.println(i + ": " + mem[i]);
		}
	}

    public Word[] blankMemory(int tamMem) {
		mem = new Word[tamMem];
		for (int i=0; i<tamMem; i++)
			mem[i] = Word.BLANK;
        return mem;
    }

    private void runCPU(PCB pcb) {
        cpu.setContext(pcb.getAllocatedPages(), pcb.pc, pcb.id, pcb.reg);
        cpu.run();
    }

    public void run() {
        while(true) {
            if(readyQueue.isEmpty()) {
                return;
            }

            PCB process = scheduler.schedule();
            runCPU(process);
        }
    }
}
