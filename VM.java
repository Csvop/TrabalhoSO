import java.util.LinkedList;

public class VM {
    //Hardwares
    public int tamMem;    
    public Word[] mem;
    public CPU cpu;

    //Software - Gerenciadores
    public MemoryManager mm;
    public ProcessManager pm;
    public Scheduler scheduler;
    public LinkedList<PCB> readyQueue;
    public Routine routine;

    public VM(){
		//Hardware
        mem = blankMemory(512);

        //Software
        readyQueue = new LinkedList<PCB>();
        scheduler = new Scheduler(readyQueue);
        mm = new MemoryManager(this.mem);
        pm = new ProcessManager(mm, readyQueue);
        this.routine = new Routine(pm, scheduler);

        cpu = new CPU(mem, mm);
	}

    public void load(Word[] program) {
        pm.createProcess(program);
    }

	public void dump(int ini, int fim) {
		for (int i = ini; i < fim; i++) {
			Console.log(i + ": " + mem[i]);
		}
	}

    public Word[] blankMemory(int tamMem) {
		mem = new Word[tamMem];
		for (int i=0; i<tamMem; i++)
			mem[i] = Word.copy(Word.BLANK);
        return mem;
    }

    private Interrupt runCPU(PCB pcb) {
        cpu.setContext(pcb.getContext());
        return cpu.run();
    }

    public void run() {
        while(!readyQueue.isEmpty()) {

            PCB process = scheduler.schedule();

            Interrupt interrupt = runCPU(process);

            System.out.println(interrupt.toString());

            switch(interrupt) {
                case NONE:
                    break;
                case INVALID_ADDRESS:
                    routine.stop(process);
                    break;
                case INVALID_INSTRUCTION:
                    routine.stop(process);
                    break;
                case OVERFLOW:
                    routine.stop(process);
                    break;
                case STOP:
                    dump(0, 100);
                    routine.stop(process);
                    break;
                case TIMER:
                    //tenho que tirar da fila
                    process.setContext(cpu.getContext());
                    readyQueue.addLast(process);
                    readyQueue.forEach((e) -> Console.log(e));
                    break;
                case TRAP:
                    routine.stop(process);
                    break;
            }
        }
    }

    public void dump(boolean[] frames) {
        Console.debug(" > Memory.dump(frames) \n");
        for (int i = 0; i < frames.length; i++) {
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
              Console.log("[" + i + "] (" + frames[i] + ")");
        }
        Console.print("\n");
    }
}
