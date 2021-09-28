public class VM {
    private static VM INSTANCE;
    public CPU cpu;

    public MemoryManager mm;
    public ProcessManager pm;
    public Escalonador esc;

    public VM() { // vm deve ser configurada com endereço de tratamento de interrupcoes
        Memory.init(1024);
        cpu = new CPU();
        
        mm = MemoryManager.get();
        pm = ProcessManager.get();
        esc = Escalonador.get();
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
