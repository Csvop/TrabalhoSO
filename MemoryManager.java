import java.util.ArrayList;

public class MemoryManager {
    public final int MEMORY_SIZE = Memory.get().size;
    public int pageSize;
    public int frameSize;
    public int frameCount;
    public boolean[] availableFrames;

    private MemoryManager() {
        this.frameSize = 16;
        this.pageSize = 16;
        this.frameCount = this.MEMORY_SIZE / this.pageSize;

        // pageSize = frameSize = 16
        // frameCount(64) || nroPaginas(64) = tamMemoria(1024) / pageSize(16)
        availableFrames = initFrames(Memory.get().size, frameSize);
    }

    // Inicializa o array de frames com valor TRUE
    private boolean[] initFrames(int tamMem, int pageSize) {
        availableFrames = new boolean[(tamMem / pageSize)];

        for (int i = 0; i < availableFrames.length; i++) {
            availableFrames[i] = true;
        }

        return availableFrames;
    }

    // Dada uma demanda em número de palavras, o gerente deve responder se a alocação é possível
    public boolean temEspacoParaAlocar(int numeroPalavras) {
        int quantidadeDeFramesQueVaiOcupar = 0;

        // Se for exatamente o tamanho da Pagina
        if (numeroPalavras % frameSize == 0) {
            quantidadeDeFramesQueVaiOcupar = ((numeroPalavras / frameSize));
        }
        // Se for quebrado o tamanho da pagina
        else {
            quantidadeDeFramesQueVaiOcupar = ((numeroPalavras / frameSize) + 1);
        }

        int quantidadeDeFramesDisponiveis = 0;
        for (int i = 0; i < availableFrames.length; i++) {
            if (availableFrames[i]) {
                quantidadeDeFramesDisponiveis++;
            }
        }

        return (quantidadeDeFramesQueVaiOcupar <= quantidadeDeFramesDisponiveis);
    }

    // Retornar o conjunto de frames alocados
    // Retorna um array de inteiros com os índices dos frames.
    public ArrayList<Integer> allocate(Word[] p) {
        int quantidadeDeFramesQueVaiOcupar = 0;

        // Se for exatamente o tamanho da Pagina
        if (p.length % frameSize == 0) {
            quantidadeDeFramesQueVaiOcupar = ((p.length / frameSize));
        }
        // Se for quebrado o tamanho da pagina
        else {
            quantidadeDeFramesQueVaiOcupar = ((p.length / frameSize) + 1);
        }

        int quantidadeNovosFramesOcupados = 0;
        int posicao = 0;

        ArrayList<Integer> paginas = new ArrayList<>();

        for (int f = 0; f < availableFrames.length; f++) {
            if (availableFrames[f] == true) {
                availableFrames[f] = false;
                quantidadeNovosFramesOcupados++;
                paginas.add(f);
            }

            for (int j = (f * frameSize); j < (f + 1) * frameSize; j++) {
                if (posicao < p.length) {
                    Memory.get().data[j].opc = p[posicao].opc;
                    Memory.get().data[j].r1 = p[posicao].r1;
                    Memory.get().data[j].r2 = p[posicao].r2;
                    Memory.get().data[j].p = p[posicao].p;
                    posicao++;
                } else {
                    break;
                }
            }
            if (quantidadeNovosFramesOcupados == quantidadeDeFramesQueVaiOcupar) {
                return paginas;
            }
        }
        return null;
    }

    // Dado um array de inteiros com as páginas de um processo, o gerente desloca as páginas.
    public void unallocate(ArrayList<Integer> paginasAlocadas) {
        for (Integer pagina : paginasAlocadas) {
            for (int i = 0; i < availableFrames.length; i++) {
                if (pagina == i) {

                    // Libera o frame
                    availableFrames[i] = true;

                    // Libera a memoria
                    for (int position = (i * frameSize); position < (i + 1) * frameSize; position++) {
                        Memory.get().write(Word.BLANK, position);
                    }
                }
            }
        }
    }

    // Métodos auxiliares
    public void dump(Word w) {
        Console.print("[ "); 
        Console.print(w.opc); Console.print(", ");
        Console.print(w.r1);  Console.print(", ");
        Console.print(w.r2);  Console.print(", ");
        Console.print(w.p);  Console.log("  ] ");
    }

    public void dump(Word[] m, int ini, int fim) {
        Console.debug(" > Memory.dump() \n");
        for (int i = ini; i < fim; i++) {
            Console.print(i); Console.print(":  ");  dump(m[i]);
        }
        Console.print("\n");
    }

    public void dump(int ini, int fim) {
        Memory m = Memory.get();
        Console.debug(" > Memory.dump() \n");
        for (int i = ini; i < fim; i++) {
            Console.print(i); Console.print(":  ");  dump(m.data[i]);
        }
        Console.print("\n");
    }

    public void dump(boolean[] frames) {
        Console.debug(" > Memory.dump(frames) \n");
        for (int i = 0; i < frames.length; i++) {
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
            Console.print("[" + i + "] (" + frames[i] + ") --- "); i++;
              Console.log("[" + i + "] (" + frames[i] + ")");
        }
        Console.print("\n");
    }


    // Singleton
    private static MemoryManager INSTANCE;

    public static void init() {
        if (INSTANCE == null) INSTANCE = new MemoryManager();
    }

    public static MemoryManager get() {
        init();
        return INSTANCE;
    }

}
