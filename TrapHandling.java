import java.util.Scanner;

public class TrapHandling {

    public static void trap(CPU cpu) {
        SystemOut.debug(" > CPU.reg[8] = " + cpu.reg[8]);
        SystemOut.debug(" > CPU.reg[9] = " + cpu.reg[9]);

        switch (cpu.reg[8]) {
            case 1:
                SystemOut.print("\n > Digite um valor inteiro: ");

                Scanner in = new Scanner(System.in);
                String input = in.nextLine();

                // Converte o input para um valor inteiro
                int value = Integer.parseInt(input);

                cpu.memory[cpu.reg[9]].opc = Opcode.DATA;
                cpu.memory[cpu.reg[9]].p = value;

                SystemOut.info(
                    "O valor " + cpu.memory[cpu.reg[9]].p + 
                    " foi armazenado na posição [" + cpu.reg[9] + "] da memória."
                );
                break;

            case 2:
                SystemOut.info(
                    "O valor armazenado na posição [" + cpu.reg[9] + 
                    "] da memória é " + cpu.memory[cpu.reg[9]].p
                );
                break;
        }
    }
}
