import java.util.LinkedList;
import java.util.concurrent.Semaphore;

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

    //Semaforos
    public Semaphore semCPU;
    public Semaphore semESC;

    public VM(){
        //Semaforos
        semCPU = new Semaphore(0);
        semESC = new Semaphore(0);

		//Hardware
        tamMem = 512;
        mem = blankMemory(tamMem);

        //Software
        readyQueue = new LinkedList<PCB>();
        scheduler = new Scheduler(readyQueue);
        mm = new MemoryManager(this.mem);
        pm = new ProcessManager(mm, readyQueue);
        routine = new Routine(pm, scheduler);
        cpu = new CPU(mem, mm, semCPU, semESC, routine);
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

    public void run() {
        cpu.setName("CPU");
        cpu.start();
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
