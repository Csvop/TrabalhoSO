public class Memory {

    public int size;
    public Word[] data;

    private Memory(int _size) {
        this.size = _size;
        data = new Word[size];
    }

    // Memory CRUD:
    //   Create/Update = write()
    //   Read          = read()
    //   Delete        = delete()

    public void write(Word _word, int _position) {
        Word.copy(data[_position] = _word);
    }

    public Word read(int _position) {
        return Word.copy(data[_position]);
    }

    public void delete(int _position) {
        Memory.get().data[_position] = Word.copy(Word.BLANK);
    }

    
    public void clearMemory() {
        for (int i = 0; i < size; i++) {
            delete(i);
        }
    }


    // Singleton
    private static Memory INSTANCE;

    public static void init(int _size) {
        if (INSTANCE == null) {
            INSTANCE = new Memory(_size);
            Console.debug(" > Memory.init(size: "+_size+") ");
            INSTANCE.clearMemory();
        } else {
            Console.error("Can't init memory because memory is not null.");
        }
    }

    public static Memory get() {
        return INSTANCE;
    }

}
