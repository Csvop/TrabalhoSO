import java.util.ArrayList;

public class ConsoleProcess {
    int id;
    int memoryAddress;
    ArrayList<Integer> allocatedPages;
    String type;

    public ConsoleProcess(int id, ArrayList<Integer> allocatedPages, String type, int r2) {
        this.id = id;
        this.allocatedPages = allocatedPages;
        this.type = type;
        this.memoryAddress = r2;
    }
}
