import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Console extends Thread {
    LinkedList<PCB> consoleOrderQueue;
    Word[] memoria;
    CPU cpu;
    Semaphore semAPP;
    Semaphore mutex = new Semaphore(1);

    public Console(CPU cpu, Word[] memoria, Semaphore semAPP) {
        this.consoleOrderQueue = new LinkedList<PCB>();

        this.memoria = memoria;
        this.cpu = cpu;
        this.semAPP = semAPP;
        super.setName("Console");
    }

    public void addToQueue(PCB process) {
        consoleOrderQueue.add(process);
    }

    public void run() {
        while (true) {
            if (consoleOrderQueue.isEmpty()) {
                continue;
            }

            SystemOut.debug(" > CPU.reg[8] = " + cpu.reg[8]);
            SystemOut.debug(" > CPU.reg[9] = " + cpu.reg[9]);

            switch (cpu.reg[8]) {
                case 1:
                    SystemOut.print("\n > Digite um valor inteiro: ");

                    try {
                        semAPP.acquire();
                        mutex.acquire();
                        Thread.sleep(10000);
                        mutex.release();
                    } catch (InterruptedException e) {
                    }

                    Scanner in = new Scanner(System.in);
                    String input = in.nextLine();

                    // Converte o input para um valor inteiro
                    int value = Integer.parseInt(input);

                    cpu.memory[cpu.reg[9]].opc = Opcode.DATA;
                    cpu.memory[cpu.reg[9]].p = value;

                    SystemOut.info(
                            "O valor " + cpu.memory[cpu.reg[9]].p +
                                    " foi armazenado na posição [" + cpu.reg[9] + "] da memória.");

                    consoleOrderQueue.poll();
                    break;

                case 2:
                    SystemOut.info(
                            "O valor armazenado na posição [" + cpu.reg[9] +
                                    "] da memória é " + cpu.memory[cpu.reg[9]].p);
                    consoleOrderQueue.poll();
                    break;
            }
        }
    }

}
