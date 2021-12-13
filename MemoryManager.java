import java.util.ArrayList;
import java.util.Arrays;

public class MemoryManager {
    public Word[] memory;
    public int pageSize;
    public int frameSize;
    public boolean[] availableFrames;

    public MemoryManager(Word[] memory) {
        this.memory = memory;
        this.pageSize = 16;

        int amount = memory.length / pageSize;
        createFrames(amount);
    }

    public void createFrames(int length) {
        this.frameSize = length;
        this.availableFrames = new boolean[length];
        Arrays.fill(availableFrames, true);
    }

    public ArrayList<Integer> allocate(Word[] program) {
        ArrayList<Integer> allocatedPages;

        if (isAllocable(program.length)) {
            allocatedPages = allocatePages(program);
        } else {
            SystemOut.print("\n");
            SystemOut.warn("Programa nao alocavel. Nao ha espaco suficiente na memoria. ");
            SystemOut.debug("Tamanho do programa: " + program.length);
            SystemOut.debug("Memoria livre: " + availableMemoryPositionsCount());

            allocatedPages = null;
        }
        return allocatedPages;
    }

    public boolean isAllocable(int programSize) {
        return programSize < availableMemoryPositionsCount();
    }

    public int availableMemoryPositionsCount() {
        int count = 0;

        for (boolean frame : availableFrames) {
            if (frame == true)
                count += pageSize;
        }

        return count;
    }

    private int countHowManyPagesThisProgramNeeds(int programSize) {
        int numberOfPagesThisProgramNeeds = 0;

        if (programSize % pageSize == 0) {
            numberOfPagesThisProgramNeeds = ((programSize / pageSize));
        } else {
            numberOfPagesThisProgramNeeds = ((programSize / pageSize) + 1);
        }

        return numberOfPagesThisProgramNeeds;
    }

    private ArrayList<Integer> allocatePages(Word[] program) {
        ArrayList<Integer> allocatedPages = new ArrayList<>();

        int numberOfPagesThisProgramNeeds = countHowManyPagesThisProgramNeeds(program.length);
        int cont = 0;
        int posProg = 0;

        for (int i = 0; i < availableFrames.length; i++) {
            if (isFrameAvailable(i)) {
                cont++;
                setFrameUnavailable(i);
                allocatedPages.add(i);
                for (int j = (i * pageSize); j < (i + 1) * pageSize; j++) {
                    if (posProg < program.length) {
                        memory[j] = Word.copy(program[posProg]);
                        posProg++;
                    } else {
                        break;
                    }
                }
            }
            if (cont == numberOfPagesThisProgramNeeds) {
                break;
            }
        }

        return allocatedPages;
    }

    public void unallocate(PCB process) {
        ArrayList<Integer> pages = process.getAllocatedPages();

        for (int i = 0; i < pages.size(); i++) {
            availableFrames[pages.get(i)] = true;

            for (int j = pageSize * pages.get(i); j < pageSize * (pages.get(i) + 1); j++) {
                memory[j] = Word.copy(Word.BLANK);
            }
        }
    }

    public void printMemorySegment(int start, int end) {
        if (start >= end || end > memory.length) {
            return;
        }

        for (int i = 0; i < end; i++) {
            SystemOut.log(memory[i].toString());
        }
    }

    public boolean isFrameAvailable(int frame) {
        return availableFrames[frame] == true;
    }

    public void setFrameUnavailable(int frame) {
        availableFrames[frame] = false;
    }

    public void dump(int ini, int fim) {
        for (int i = ini; i < fim; i++) {
            SystemOut.log(i + ": " + memory[i]);
        }
    }

    public void dump(boolean[] frames) {
        SystemOut.debug(" > Memory.dump(frames) \n");
        SystemOut.log("Frames da memória disponíveis:");
        for (int i = 0; i < frames.length; i++) {
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

    public void wipeMemory() {
        this.memory = getCleanMemory();
        Arrays.fill(availableFrames, true);
    }

    public Word[] getCleanMemory() {
        int memorySize = this.memory.length;
        Word[] memory = new Word[memorySize];

        for (int i = 0; i < memorySize; i++)
            memory[i] = Word.copy(Word.BLANK);

        return memory;
    }
}
