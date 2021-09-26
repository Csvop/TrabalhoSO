package software;

import hardware.Memory;
import hardware.Word;

public class ProcessManager {
    
    public ProcessManager() {

    }
    
    boolean createProcess(Word[] p) {
        //verifica tamanho do programa
        //pede memória
        //se nao tem memória, retorna negativo
        int programSize = p.length;
        int memorySize = Memory.get().size; //Fazer a verificacão de espaco
        if(programSize > memorySize) {
            return false;
        }

        //cria PCB

        new MemoryManager().allocate(p);

        return true;
    }

}
