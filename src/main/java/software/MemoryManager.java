package software;

import hardware.Memory;
import hardware.Word;

import java.util.ArrayList;

public class MemoryManager {
    public final int MEMORY_SIZE = Memory.get().size;
    public int pageSize;
    public int frameSize;
    public int frameCount;
    public boolean[] availableFrames;

    public MemoryManager() {
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
                        Memory.get().write(Memory.BLANK, position);
                    }
                }
            }
        }
    }
}
