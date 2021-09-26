package virtualmachine;

import hardware.CPU;
import hardware.Memory;
import software.ProcessManager;
import software.MemoryManager;

public class VM {
    private static VM INSTANCE;
    public CPU cpu;
    public ProcessManager pm;
    public MemoryManager mm;

    public VM() { // vm deve ser configurada com endereço de tratamento de interrupcoes
        Memory.init(1024);
        ProcessManager.init();
        cpu = new CPU();
        pm = ProcessManager.get();
    }

     /**
     * Cria uma instância única para a classe VM.
     */
    public static void init() {
        if (INSTANCE == null) INSTANCE = new VM();
    }

    /**
     * @return instância única da VM.
     */
    public static VM get() {
        VM.init();
        return INSTANCE;
    }
}
