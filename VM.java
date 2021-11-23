import java.util.LinkedList;

public class VM {
    //Hardwares
    public int tamMem;  //ok 
    public Word[] mem; //ok
    public CPU cpu; //ok

    //Software - Gerenciadores
    public MemoryManager mm; //ok
    public ProcessManager pm; //ok
    public Scheduler scheduler; //ok
    public LinkedList<PCB> readyQueue; //ok
    public Routine routine; //ok

    public VM(){
		//Hardware
        tamMem = 512;
        mem = blankMemory(tamMem);

        //Software
        readyQueue = new LinkedList<PCB>();
        scheduler = new Scheduler(readyQueue);
        mm = new MemoryManager(this.mem);
        pm = new ProcessManager(mm, readyQueue);
        routine = new Routine(pm, scheduler);
        cpu = new CPU(mem, mm);

        
	}

    public void load(Word[] program) {
        pm.createProcess(program);
    }

	public void dump(int ini, int fim) {
		for (int i = ini; i < fim; i++) {
			SystemOut.log(i + ": " + mem[i]);
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
                    readyQueue.forEach((e) -> SystemOut.log(e));
                    break;
                case TRAP:
                    routine.stop(process);
                    break;
            }
        }
    }

    public void dump(boolean[] frames) {
        SystemOut.debug(" > Memory.dump(frames) \n");
        for (int i = 0; i < frames.length; i++) {
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            SystemOut.print("[" + i + "] (" + frames[i] + ") --- "); i++;
              SystemOut.log("[" + i + "] (" + frames[i] + ")");
        }
        SystemOut.print("\n");
    }
}
