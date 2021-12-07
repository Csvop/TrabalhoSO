import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class VM {
    // Hardwares
    public int tamMem; // ok
    public Word[] mem; // ok
    public CPU cpu; // ok

    // Software - Gerenciadores
    public MemoryManager mm; // ok
    public ProcessManager pm; // ok
    public Scheduler scheduler; // ok
    public LinkedList<PCB> readyQueue; // ok
    public LinkedList<PCB> blockedQueue; // ok
    public ConcurrentLinkedQueue<PCB> orderQueue; // ok
    public Routines routine; // ok
    public Console console; // (THREAD)

    // Semaforos
    public Semaphore semCPU = new Semaphore(0);
    public Semaphore semESC = new Semaphore(0);

    public VM(Semaphore semAllAPP) {
        // Hardware
        tamMem = 1024;
        mem = blankMemory(tamMem);

        readyQueue = new LinkedList<PCB>();
        blockedQueue = new LinkedList<PCB>();
        orderQueue = new ConcurrentLinkedQueue<PCB>();

        cpu = new CPU(this.mem, this.semCPU, this.semESC);

        mm = new MemoryManager(this.mem);

        pm = new ProcessManager(mm, readyQueue, semESC);

        scheduler = new Scheduler(readyQueue, semESC, semCPU, cpu);

        console = new Console(cpu, mem, semAllAPP);

        routine = new Routines(pm, scheduler, semESC, blockedQueue, console);

        // Configura o processo inicial
        cpu.configure(routine, mm);

        // Init threads
        this.init();
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
        for (int i = 0; i < tamMem; i++)
            mem[i] = Word.copy(Word.BLANK);
        return mem;
    }

    public void wipeMemory() {
        this.mem = blankMemory(this.tamMem);
        Arrays.fill(mm.availableFrames, true);
    }

    public void dump(boolean[] frames) {
        SystemOut.debug(" > Memory.dump(frames) \n");
        SystemOut.log("Frames da memória disponíveis:");
        for (int i = 0; i < frames.length; i++) {
            // if (frames[0] == false) Dye.green(frames[0]+""); else
            // Dye.green(frames[0]+"");
            SystemOut.print("[" + i + "] (" + ((frames[i]) ? Dye.blue("" + frames[i]) : Dye.red("" + frames[i]))
                    + ") --- ");
            i++;
            SystemOut.print("[" + i + "] (" + ((frames[i]) ? Dye.blue("" + frames[i]) : Dye.red("" + frames[i]))
                    + ") --- ");
            i++;
            SystemOut.print("[" + i + "] (" + ((frames[i]) ? Dye.blue("" + frames[i]) : Dye.red("" + frames[i]))
                    + ") --- ");
            i++;
            SystemOut.log(
                    "[" + i + "] (" + ((frames[i]) ? Dye.blue("" + frames[i]) : Dye.red("" + frames[i])) + ")");
        }
        SystemOut.print("\n");
    }

    public void init() {
        cpu.setName("CPU");
        cpu.start();

        scheduler.setName("Scheduler");
        scheduler.start();

        console.setName("Console");
        console.start();
    }
}
