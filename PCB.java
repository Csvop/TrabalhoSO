import java.util.ArrayList;


public class PCB {
    public int id;
    public ArrayList<Integer> allocatedPages;
    
    // CPU context
    public int pc;
    public int[] reg;
    public Word ir;

    private Context context;

    public PCB(int id, ArrayList<Integer> allocatedPages) {
        this.id = id;
        this.allocatedPages = allocatedPages;
        this.context = new Context(allocatedPages, new int[10], 0, Word.copy(Word.BLANK), id);
    }

    //retorna a lista de paginas de um processo
    public ArrayList<Integer> getAllocatedPages() {
        return this.allocatedPages;
    }

    public int getId() {
        return this.id;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
