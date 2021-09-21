package virtualmachine;

import java.util.Scanner;
import util.Console;

import hardware.Opcode;
import hardware.CPU;

public class TrapHandling {

    public static void trap(CPU cpu) {
        Console.log("reg[8] = " + cpu.reg[8]);
        Console.log("reg[9] = " + cpu.reg[9]);

        switch (cpu.reg[8]) {
            case 1:
                Console.log("ENTRADA [Trap IN]");
                Console.print("\n > Digite um valor inteiro: ");

                Scanner in = new Scanner(System.in);
                String input = in.nextLine();

                // Converte o input para um valor inteiro
                int value = Integer.parseInt(input);

                cpu.m.data[cpu.reg[9]].opc = Opcode.DATA;
                cpu.m.data[cpu.reg[9]].p = value;

                Console.log("Valor armazenado " + cpu.m.data[cpu.reg[9]].p);
                Console.log("Posição " + cpu.reg[9]);
                break;

            case 2:
                Console.log("SAÍDA [Trap OUT]");
                Console.log("Valor: " + cpu.m.data[cpu.reg[9]].p);
                break;
        }
    }
}
