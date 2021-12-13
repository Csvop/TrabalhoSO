import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class VM {
    // Hardwares
    public int memorySize; // ok
    public Word[] memory; // ok
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
        memorySize = 1024;
        memory = createMemory(memorySize);

        readyQueue = new LinkedList<PCB>();
        blockedQueue = new LinkedList<PCB>();
        orderQueue = new ConcurrentLinkedQueue<PCB>();

        cpu = new CPU(this.memory, this.semCPU, this.semESC);

        mm = new MemoryManager(this.memory);

        pm = new ProcessManager(mm, readyQueue, semESC);

        scheduler = new Scheduler(readyQueue, semESC, semCPU, cpu);

        console = new Console(cpu, memory, semAllAPP);

        routine = new Routines(pm, scheduler, semESC, blockedQueue, console);

        // Configura o processo inicial
        cpu.configure(routine, mm);

        // Init threads
        this.init();
    }

    public void load(Word[] program) {
        pm.createProcess(program);
    }

    public Word[] createMemory(int size) {
        memory = new Word[size];
        for (int i = 0; i < size; i++)
            memory[i] = Word.copy(Word.BLANK);
        return memory;
    }

    public void init() {
        cpu.start();
        scheduler.start();
        console.start();
    }

}
