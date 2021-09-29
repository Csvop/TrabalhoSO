import java.util.ArrayList;
import java.util.Arrays;

public class MemoryManager {
    public Word[] memory;
    public int pageSize;
    public int frames;
    public boolean[] availableFrames;
    public int auxPage;

    public MemoryManager(Word[] memory) {
        this.memory = memory;
        pageSize = 16;
        frames = memory.length/pageSize;
        availableFrames = new boolean[frames];
        Arrays.fill(availableFrames, true);
    }

    public ArrayList<Integer> allocate(Word[] program) {
        int tamProg = program.length;
        if(tamProg%pageSize == 0){
            auxPage = ((tamProg/pageSize));
        }
        else{
            auxPage = ((tamProg/pageSize)+1);
        }
        int cont = 0;
        int posProg = 0;

        ArrayList<Integer> pages = new ArrayList<>();
        for(int i=0; i<availableFrames.length; i++){
            if(availableFrames[i] == true){
                cont++;
                availableFrames[i] = false;
                pages.add(i);
                for (int j=(i*pageSize); j<(i+1)*pageSize; j++) {
                    if(posProg < program.length){
                        memory[j].opc = program[posProg].opc;
                        memory[j].r1 = program[posProg].r1;
                        memory[j].r2 = program[posProg].r2;
                        memory[j].p = program[posProg].p;
                        posProg++;
                    }
                    else{
                        break;
                    }
                }
            }
            if(cont == auxPage){ 
                return pages;
            }
        }    
        return null;
    }

    public void unallocate(PCB processo) {
        ArrayList<Integer> pages = processo.getAllocatedPages();
        for(int i = 0; i < pages.size(); i ++) {
            availableFrames[pages.get(i)] = true;
            for (int j = pageSize * pages.get(i); j < pageSize * (pages.get(i) + 1); j++) {
                memory[j].opc = Opcode.___;
                memory[j].r1 = -1;
                memory[j].r2 = -1;
                memory[j].p = -1;
            }
        }
    }
}
