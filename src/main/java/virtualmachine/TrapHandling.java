package virtualmachine;

import java.util.Scanner;
import util.Console;

import hardware.Opcode;
import hardware.CPU;

public class TrapHandling {

    public static void trap(CPU cpu) {
        Console.debug(" > CPU.reg[8] = " + cpu.reg[8]);
        Console.debug(" > CPU.reg[9] = " + cpu.reg[9]);

        switch (cpu.reg[8]) {
            case 1:
                Console.print("\n > Digite um valor inteiro: ");

                Scanner in = new Scanner(System.in);
                String input = in.nextLine();

                // Converte o input para um valor inteiro
                int value = Integer.parseInt(input);

                cpu.m.data[cpu.reg[9]].opc = Opcode.DATA;
                cpu.m.data[cpu.reg[9]].p = value;

                Console.info(
                    "O valor " + cpu.m.data[cpu.reg[9]].p + 
                    " foi armazenado na posição [" + cpu.reg[9] + "] da memória."
                );
                break;

            case 2:
                Console.info(
                    "O valor armazenado na posição [" + cpu.reg[9] + 
                    "] da memória é " + cpu.m.data[cpu.reg[9]].p
                );
                break;
        }
    }
}
