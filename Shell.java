import java.util.Arrays;
import java.util.Scanner;

public class Shell extends Thread {

    public Scanner ler = new Scanner(System.in);

    public boolean active;

    public Shell() {
        this.active = true;
    }

    public void showOptionsMenuPrincipal() {
        SystemOut.log("\n=========== MENU ===========");
        SystemOut.log("1. Espiar o que a CPU esta executando");
        SystemOut.log("2. Executar comandos");

        SystemOut.log("3. (non-threaded cpu test) Run CPU");

        SystemOut.log("\n0. Terminar o programa");
    }

    public void showMenuPrincipal() {
        showOptionsMenuPrincipal();

        // Variável input recebe o valor inserido pelo terminal
        SystemOut.print("\n > Digite a opcao: ");
        String input = ler.nextLine();

        // Converte o input para um valor inteiro
        int option = Integer.parseInt(input);

        switch (option) {
            case 1:
                espiarCPU();
                break;

            case 2:
                executarComandos();
                break;

            case 3:
                SystemOut.log("--------------------------");
                SystemOut.log("CPU RUNNING");
                VM.get().run();
                break;

            case 0:
                this.active = false;
                SystemOut.log("\nVolte logo!");

                // Pausa de 2 segundos;
                SystemOut.wait(2000);

                break;

            default:
                showMenuPrincipal();
                break;

        }
    }

    public void espiarCPU() {
        SystemOut.log("--------------------------");
        SystemOut.log("ESPIANDO CPU");

        SystemOut.log("\n>WIP<\n");

        // Pausa de 1 segundo;
        SystemOut.wait(1000);

        showMenuPrincipal();
    }

    public void executarComandos() {
        int option; // opção do menu escolhida pelo usuário

        do {
            SystemOut.log("--------------------------");
            SystemOut.log("EXECUTAR COMANDOS");

            SystemOut.log("1. Carregar Programa C na memoria");
            SystemOut.log("2. Carregar Programa B na memoria");
            SystemOut.log("3. Carregar Programa A na memoria");
            SystemOut.log("4. Carregar Programa Trap In na memoria");
            SystemOut.log("5. Carregar Programa Trap Out na memoria");

            SystemOut.log("6. Definir frame como nao disponivel");
            SystemOut.log("7. Exibir dump dos frames da memoria");
            SystemOut.log("8. Exibir dump da memoria (primeiras 100 posicoes)");
            SystemOut.log("9. Limpar todas as posicoes de memoria");

            SystemOut.log("0. Retornar ao menu anterior");

            // Variável input recebe o valor inserido pelo terminal
            SystemOut.print("\n > Digite a opcao: ");
            String input = ler.nextLine();

            // Converte o input para um valor inteiro
            option = Integer.parseInt(input);

            switch (option) {
                case 1:
                    SystemOut.print("\nCarregando Programa C na memoria... ");
                    VM.get().load(Program.PC);
                    SystemOut.log("(done!)");
                    break;

                case 2:
                    SystemOut.print("\nCarregando Programa B na memoria... ");
                    VM.get().load(Program.PB);
                    SystemOut.log("(done!)");
                    break;

                case 3:
                    SystemOut.print("\nCarregando Programa A na memoria... ");
                    VM.get().load(Program.PA);
                    SystemOut.log("(done!)");
                    break;

                case 4:
                    SystemOut.print("\nCarregando Programa Trap In na memoria... ");
                    VM.get().load(Program.TRAP_IN);
                    SystemOut.log("(done!)");
                    break;

                case 5:
                    SystemOut.print("\nCarregando Programa Trap Out na memoria... ");
                    VM.get().load(Program.TRAP_OUT);
                    SystemOut.log("(done!)");
                    break;

                case 6:
                    VM.get().mm.availableFrames[1] = false; // Simula um frame ocupado para evidenciar o split do
                                                            // programa em 2 frames não consecutivos
                    SystemOut.log("O segundo frame da memória foi definido como nao disponível");
                    break;

                case 7:
                    VM.get().dump(VM.get().mm.availableFrames); // Printa os frames livres/ocupados (false = ocupado)
                    break;

                case 8:
                    VM.get().dump(0, 100);
                    break;

                case 9:
                    SystemOut.print("\nLimpando todas as posicoes da memoria... ");
                    VM.get().wipeMemory();
                    SystemOut.log("(done!)");
                    break;

                case 0:
                    SystemOut.log("\nRetornando ao menu principal...");
                    break;

                default:
                    executarComandos();
                    break;

            }
        } while (option != 0);

        showMenuPrincipal();
    }

    public void run() {

        // Programas (nossos programas são static, não precisam ser instanciados aqui.)

        // Menu
        while (active) {
            showMenuPrincipal();
        }
    }
}
