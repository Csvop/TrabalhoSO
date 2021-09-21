package hardware;

import util.Console;

public class Memory {
    public static final Word BLANK = new Word(Opcode.___, -1, -1, -1);

    private static Memory INSTANCE;
    public int size;
    public Word[] data;

    private Memory(int _size) {
        this.size = _size;
        data = new Word[size];
    }

    public static void init(int _size) {
        if (INSTANCE == null) {
            INSTANCE = new Memory(_size);
            Console.debug(" > Memory.init(size: "+_size+") ");
            INSTANCE.cleanMemory();
        } else {
            Console.error("Can't init memory because memory is not null.");
        }
    }

    public void write(Word _word, int _position) {
        Word.copy(data[_position] = _word);
    }

    public void cleanMemory() {
        for (int i = 0; i < size; i++) {
            Memory.get().data[i] = Word.copy(BLANK);
        }
    }

    public static Memory get() {
        return INSTANCE;
    }
}
