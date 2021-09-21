package virtualmachine;

import hardware.CPU;
import hardware.Word;
import hardware.Memory;

public class VM {
    public int tamMem;
    public CPU cpu;

    public VM() { // vm deve ser configurada com endereço de tratamento de interrupcoes
        Memory.init(1024);

        cpu = new CPU();
    }

}
