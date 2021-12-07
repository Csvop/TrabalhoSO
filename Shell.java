import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Shell extends Thread {
    Semaphore semaphore = new Semaphore(0);

    public Scanner ler = new Scanner(System.in);

    public boolean active;

    public Shell() {
        this.active = true;
    }

    public void showOptionsMenuPrincipal(VM vm) {
        SystemOut.log("\n=========== MENU ===========");
        SystemOut.print(Dye.cyan("(Virtual Machine) memory size: " + vm.tamMem));

        SystemOut.log("\n1. Espiar a CPU esta executando");
        SystemOut.log("2. Executar comandos");

        SystemOut.log("\n0. Terminar o programa");
    }

    public void showMenuPrincipal(VM vm) {
        showOptionsMenuPrincipal(vm);

        // Variável input recebe o valor inserido pelo terminal
        SystemOut.print("\n > Digite a opcao: ");
        String input = ler.nextLine();

        // Converte o input para um valor inteiro
        int option = Integer.parseInt(input);

        switch (option) {
            case 1:
                espiarCPU(vm);
                break;

            case 2:
                executarComandos(vm);
                break;

            case 0:
                this.active = false;
                SystemOut.log("\nVolte logo!");

                // Pausa de 2 segundos;
                SystemOut.wait(2000);

                break;

            default:
                showMenuPrincipal(vm);
                break;

        }
    }

    public void espiarCPU(VM vm) {
        SystemOut.log("--------------------------");
        SystemOut.log("ESPIANDO CPU");

        SystemOut.debug("Instruction register:" + vm.cpu.ir);
        SystemOut.debug("Program counter:" + vm.cpu.pc);
        vm.cpu.printRegistradores();
        SystemOut.debug("Current process id:" + vm.cpu.processId);
        SystemOut.debug("Current interruption:" + vm.cpu.interrupt);

        // Pausa de 1 segundo;
        SystemOut.wait(1000);

        showMenuPrincipal(vm);
    }

    public void executarComandos(VM vm) {
        int option; // opção do menu escolhida pelo usuário

        do {
            SystemOut.log("--------------------------");
            SystemOut.log("EXECUTAR COMANDOS");

            SystemOut.log("1. Executar Programa A");
            SystemOut.log("2. Executar Programa B");
            SystemOut.log("3. Executar Programa C");
            SystemOut.log("4. Executar Programa Trap In");
            SystemOut.log("5. Executar Programa Trap Out");
            SystemOut.log("6. Executar todos os programas\n");

            SystemOut.log("7. Definir frame como nao disponivel");
            SystemOut.log("8. Exibir dump dos frames da memoria");
            SystemOut.log("9. Exibir dump da memoria (primeiras 100 posicoes)");
            SystemOut.log("10. Limpar todas as posicoes de memoria\n");

            SystemOut.log("0. Retornar ao menu anterior");

            // Variável input recebe o valor inserido pelo terminal
            SystemOut.print("\n > Digite a opcao: ");
            String input = ler.nextLine();

            // Converte o input para um valor inteiro
            option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    SystemOut.print("\nCarregando Programa A na memoria... ");
                    vm.load(Program.PA);
                    SystemOut.log("(done)");
                    break;

                case 2:
                    SystemOut.print("\nCarregando Programa B na memoria... ");
                    vm.load(Program.PB);
                    SystemOut.log("(done)");
                    break;

                case 3:
                    SystemOut.print("\nCarregando Programa C na memoria... ");
                    vm.load(Program.PC);
                    SystemOut.log("(done)");
                    break;

                case 4:
                    SystemOut.print("\nCarregando Programa Trap In na memoria... ");
                    vm.load(Program.TRAP_IN);
                    SystemOut.log("(done)");
                    break;

                case 5:
                    SystemOut.print("\nCarregando Programa Trap Out na memoria... ");
                    vm.load(Program.TRAP_OUT);
                    SystemOut.log("(done)");
                    break;

                case 6:
                    SystemOut.print("\nCarregando na memoria os programas: A, B, C... ");
                    vm.load(Program.PA);
                    vm.load(Program.PB);
                    vm.load(Program.PC);
                    // vm.load(Program.TRAP_IN);
                    // vm.load(Program.TRAP_OUT);
                    SystemOut.log("(done)");
                    break;

                case 7:
                    vm.mm.availableFrames[1] = false; // Simula um frame ocupado para evidenciar o split do
                                                      // programa em 2 frames não consecutivos
                    SystemOut.log("O segundo frame da memória foi definido como nao disponível");
                    vm.dump(vm.mm.availableFrames); // Printa os frames livres/ocupados (false = ocupado)
                    break;

                case 8:
                    SystemOut
                            .log(Dye.cyan("(Memoria) frame count: " + vm.mm.frames + ", page size: " + vm.mm.pageSize));
                    vm.dump(vm.mm.availableFrames); // Printa os frames livres/ocupados (false = ocupado)
                    break;

                case 9:
                    vm.dump(0, 100);
                    break;

                case 10:
                    SystemOut.print("\nLimpando todas as posicoes da memoria... ");
                    vm.wipeMemory();
                    SystemOut.log("(done!)");
                    break;

                case 0:
                    SystemOut.log("\nRetornando ao menu principal...");
                    break;

                default:
                    executarComandos(vm);
                    break;

            }
        } while (option != 0);

        showMenuPrincipal(vm);
    }

    public void run() {
        // Programas (nossos programas são static, não precisam ser instanciados aqui.)
        VM vm = new VM(semaphore);
        // Menu
        while (active) {
            showMenuPrincipal(vm);
        }
    }
}
