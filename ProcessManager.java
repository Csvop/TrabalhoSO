import java.util.LinkedList;
import java.util.Queue;

public class ProcessManager {
    private static ProcessManager INSTANCE;

    public MemoryManager mm;
    public int processId = 0;

    public Queue<PCB> pcbList;

    public ProcessManager() {
        this.mm = MemoryManager.get();
        this.pcbList = new LinkedList<>();
    }

    /**
     * 
     * @param p programa. Ex.: bubbleSort
     * @return referência para o novo processo criado 
     */
    public PCB create(Word[] p) {            Console.debug(" > ProcessManager.create()");
        PCB processControlBlock;

        if (mm.temEspacoParaAlocar(p.length)) {
            processControlBlock = new PCB(processId, mm.allocate(p));
            ++processId;

            pcbList.add(processControlBlock);
        } else {
            Console.error("Sem espaço na memória para criar o processo de ID:"+processId);
            processControlBlock = null;
        }

        return processControlBlock;
    }

    public void finish(PCB processo) {          Console.debug(" > ProcessManager.finish()");
        mm.unallocate(processo.getAllocatedPages());
        pcbList.remove(processo);
    }

    public PCB getProcess(int id) {
        if (pcbList.peek().getId() == id) {
            return pcbList.peek();
        } else {
            Console.error("Não foi possível encontrar o processo de ID:"+processId);
            return null;
        }
    }



    /**
     * Cria uma instância única para a classe ProcessManager.
     */
    public static void init() {
        if (INSTANCE == null) INSTANCE = new ProcessManager();
    }

    /**
     * @return instância única da ProcessManager.
     */
    public static ProcessManager get() {
        init();
        return INSTANCE;
    }
}