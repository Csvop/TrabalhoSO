package virtualmachine;

import hardware.Opcode;
import hardware.Word;

public class Program {

    public static final Word[] TRAP_IN = new Word[] {
        new Word(Opcode.LDI, 8, -1, 1), 
        new Word(Opcode.LDI, 9, -1, 10), 
        new Word(Opcode.TRAP, -1, -1, -1),
        new Word(Opcode.STOP, -1, -1, -1) 
    };

    public static final Word[] TRAP_OUT = new Word[] {
        new Word(Opcode.LDI, 0, -1, 999), 
        new Word(Opcode.STD, 0, -1, 10), 
        new Word(Opcode.LDI, 8, -1, 2),
        new Word(Opcode.LDI, 9, -1, 10), 
        new Word(Opcode.TRAP, -1, -1, -1), 
        new Word(Opcode.STOP, -1, -1, -1) 
    };

    public static final Word[] FIBONACCI_10 = new Word[] { // mesmo que prog exemplo, so que usa r0 no lugar de r8

        new Word(Opcode.LDI, 4, -1, 10), // Numero de numeros na sequencia !!é um INDEX!!
        new Word(Opcode.LDI, 1, -1, 0), 
        new Word(Opcode.STD, 1, -1, 20), // 20 posicao de memoria onde inicia a
                                         // serie de fibonacci gerada
        new Word(Opcode.LDI, 2, -1, 1), 
        new Word(Opcode.STD, 2, -1, 21), 
        new Word(Opcode.LDI, 0, -1, 22),
        new Word(Opcode.LDI, 6, -1, 6), 
        new Word(Opcode.LDI, 7, -1, 20), // final da escala
        new Word(Opcode.ADD, 7, 4, -1), 
        new Word(Opcode.LDI, 3, -1, 0), 
        new Word(Opcode.ADD, 3, 1, -1),
        new Word(Opcode.LDI, 1, -1, 0), 
        new Word(Opcode.ADD, 1, 2, -1), 
        new Word(Opcode.ADD, 2, 3, -1),
        new Word(Opcode.STX, 0, 2, -1), 
        new Word(Opcode.ADDI, 0, -1, 1), 
        new Word(Opcode.SUB, 7, 0, -1),
        new Word(Opcode.JMPIG, 6, 7, -1), 
        new Word(Opcode.STOP, -1, -1, -1), // POS 16
    };

    public static final Word[] FATORIAL = new Word[] { // este fatorial so aceita valores positivos. nao pode ser zero
                                          // linha coment
        new Word(Opcode.LDI, 0, -1, 6), // 0 r0 é valor a calcular fatorial
        new Word(Opcode.LDI, 1, -1, 1), // 1 r1 é 1 para multiplicar (por r0)
        new Word(Opcode.LDI, 6, -1, 1), // 2 r6 é 1 para ser o decremento
        new Word(Opcode.LDI, 7, -1, 8), // 3 r7 tem posicao de stop do programa = 8
        new Word(Opcode.JMPIE, 7, 0, 0), // 4 se r0=0 pula para r7(=8)
        new Word(Opcode.MULT, 1, 0, -1), // 5 r1 = r1 * r0
        new Word(Opcode.SUB, 0, 6, -1), // 6 decrementa r0 1
        new Word(Opcode.JMP, -1, -1, 4), // 7 vai p posicao 4
        new Word(Opcode.STD, 1, -1, 10), // 8 coloca valor de r1 na posição 10
        new Word(Opcode.STOP, -1, -1, -1), // 9 stop
        new Word(Opcode.DATA, -1, -1, -1) 
    }; // 10 ao final o valor do fatorial estará na posição 10 da memória

    public static final Word[] PA = new Word[] { // mesmo que prog exemplo, so que usa r0 no lugar de r8
        new Word(Opcode.LDI, 4, -1, 10), // 0 Numero de numeros na sequencia !!é um INDEX!!
        new Word(Opcode.LDI, 1, -1, 20), // 1
        new Word(Opcode.JMPIL, 1, 4, -1), // 2
        new Word(Opcode.LDI, 1, -1, 0), // 3
        new Word(Opcode.STD, 1, -1, 21), // 4 20 posicao de memoria onde inicia a serie de fibonacci gerada
        new Word(Opcode.LDI, 2, -1, 1), // 5
        new Word(Opcode.STD, 2, -1, 22), // 6
        new Word(Opcode.LDI, 0, -1, 23), // 7
        new Word(Opcode.LDI, 6, -1, 8), // 8
        new Word(Opcode.LDI, 7, -1, 21), // 9 final da escala
        new Word(Opcode.ADD, 7, 4, -1), // 10
        new Word(Opcode.LDI, 3, -1, 0), // 11
        new Word(Opcode.ADD, 3, 1, -1), // 12
        new Word(Opcode.LDI, 1, -1, 0), // 13
        new Word(Opcode.ADD, 1, 2, -1), // 14
        new Word(Opcode.ADD, 2, 3, -1), // 15
        new Word(Opcode.STX, 0, 2, -1), // 16
        new Word(Opcode.ADDI, 0, -1, 1), // 17
        new Word(Opcode.SUB, 7, 0, -1), // 18
        new Word(Opcode.JMPIG, 6, 7, -1), // 19
        new Word(Opcode.STOP, -1, -1, -1),// 20
    }; // ate aqui - serie de fibonacci ficara armazenada

    public static final Word[] PB = new Word[] { 
        new Word(Opcode.LDI, 0, -1, 6), // 0 - r0 é valor a calcular fatorial
        new Word(Opcode.LDI, 1, -1, 12), // 1
        new Word(Opcode.JMPIL, 1, 0, -1), // 2 -IF(R0 < 0) -> posicao 6
        new Word(Opcode.LDI, 1, -1, 1), // 3 r1 é 1 para multiplicar (por r0)
        new Word(Opcode.LDI, 6, -1, 1), // 4 r6 é 1 para ser o decremento
        new Word(Opcode.LDI, 7, -1, 10), // 5 r7 tem posicao de stop do programa = 10
        new Word(Opcode.JMPIE, 7, 0, 0), // 6 se r0=0 pula para r7(=10)
        new Word(Opcode.MULT, 1, 0, -1), // 7 r1 = r1 * r0
        new Word(Opcode.SUB, 0, 6, -1), // 8 decrementa r0 1
        new Word(Opcode.JMP, -1, -1, 6), // 9 vai p posicao 6
        new Word(Opcode.STD, 1, -1, 15), // 10 coloca valor de r1 na posição 40
        new Word(Opcode.STOP, -1, -1, -1), // 11
        new Word(Opcode.STD, 0, -1, 15), // 12 coloca valor de r1 na posição 40
        new Word(Opcode.STOP, -1, -1, -1),// 13
    };

    public static final Word[] PC = new Word[] { 
        new Word(Opcode.LDI, 0, -1, 7), // carregando valores do array na memoria
        new Word(Opcode.STD, 0, -1, 40),
        new Word(Opcode.LDI, 0, -1, 12), new Word(Opcode.STD, 0, -1, 41),
        new Word(Opcode.LDI, 0, -1, 15), new Word(Opcode.STD, 0, -1, 42),
        new Word(Opcode.LDI, 0, -1, 9), new Word(Opcode.STD, 0, -1, 43),
        new Word(Opcode.LDI, 0, -1, 32), new Word(Opcode.STD, 0, -1, 44),
        new Word(Opcode.LDI, 0, -1, 2), new Word(Opcode.STD, 0, -1, 45),
        new Word(Opcode.LDI, 0, -1, -5), new Word(Opcode.STD, 0, -1, 46), // fim do array

        new Word(Opcode.LDI, 0, -1, 40), // guardando no registrador 0 o valor 40
        new Word(Opcode.LDI, 3, -1, 6), // guardando no registrador 3 o valor 7
        new Word(Opcode.LDI, 4, -1, 6), // guardando no registrador 4 o valor 6
        new Word(Opcode.LDI, 5, -1, 20), // guardando no registrador 5 o valor 20
        new Word(Opcode.LDI, 6, -1, 33), // guardando no registrador 6 o valor 33
        new Word(Opcode.LDI, 7, -1, 38), // guardando no registrador 7 o valor 38

        new Word(Opcode.JMPIE, 6, 3, -1), // inicio loop - verifica se o valor do registrador 3 é zero, se sim vai
                                            // para a posição do registrador 6
        new Word(Opcode.SUBI, 3, -1, 1), // se o registrador 3 não é zero, armazena no registrador 3 o valor de r3-1
        new Word(Opcode.LDX, 1, 0, -1), // armazena no registrador 1 o valor da memória indicado pelo registrador 0
        new Word(Opcode.ADDI, 0, -1, 1), // acrescenta 1 no registrador 0
        new Word(Opcode.LDX, 2, 0, -1), // armazena no registrador 2 o valor da memória indicado pelo registrador 0
        new Word(Opcode.SUB, 2, 1, -1), // subtrai do registrador 2 o valor do registrador 1
        new Word(Opcode.JMPIG, 5, 2, -1), // verifica se o registrador 2 é maior que zero, se sim pula para a linha
                                            // registrada no registrador 5

        new Word(Opcode.LDX, 2, 0, -1), // armazena no registrador 2 o valor indicado pelo registrador 0
        new Word(Opcode.STX, 0, 1, -1), // armazena na posição da memória indicada pelo registrador 0 o valor do
                                        // registrador 2
        new Word(Opcode.SUBI, 0, -1, 1), // subtrai 1 do registrador 0
        new Word(Opcode.STX, 0, 2, -1), // armazena na posição da memória indicada pelo registrador
        new Word(Opcode.ADDI, 0, -1, 1), // adiciona 1 ao registrador 0
        new Word(Opcode.JMPI, 5, 0, -1), // Coloca no program counter o valor do registrador 5

        new Word(Opcode.JMPIE, 7, 4, -1), // verifica se o valor do registrador 4 é igual a zero, se sim pula para a
                                            // linha indicada pelo registrador 7
        new Word(Opcode.SUBI, 4, -1, 1), // subtrai 1 do registrador 4
        new Word(Opcode.LDI, 0, -1, 40), // armazena no registrador 0 o valor 40
        new Word(Opcode.LDI, 3, -1, 6), // armazena no registrador 3 o valor 6
        new Word(Opcode.JMPIG, 5, 0, -1), // fim do loop - verifica se o valor do registrador 0 é maior do que zero,
                                            // se sim pula para a linha no valor do registrador 5

        new Word(Opcode.STOP, -1, -1, -1) 
    };
}
