package virtualmachine;

import hardware.CPU;
import hardware.Opcode;
import hardware.Word;

public class VM {
    public int tamMem;
    public Word[] m;
    public CPU cpu;

    public VM() { // vm deve ser configurada com endereço de tratamento de interrupcoes
        tamMem = 1024;
        m = new Word[tamMem];

        for (int i = 0; i < tamMem; i++) {
            m[i] = new Word(Opcode.___, -1, -1, -1);
        }
        cpu = new CPU(m);
    }

}
