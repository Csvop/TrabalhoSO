package virtualmachine;

import hardware.CPU;
import hardware.Memory;

public class VM {
    public CPU cpu;

    public VM() { // vm deve ser configurada com endereço de tratamento de interrupcoes
        Memory.init(1024);
        cpu = new CPU();
    }

}
