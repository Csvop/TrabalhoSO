import java.util.Scanner;

public class Shell extends Thread {

    public Scanner ler = new Scanner(System.in);

    public boolean active;
    
	public Shell(){
        this.active = true;
	}

    public void showOptions() {
        SystemOut.log("=========== MENU ===========");
        SystemOut.log("1. Espiar o que a CPU está executando");
        SystemOut.log("2. Executar programas");
        SystemOut.log("3. Terminar o programa");
    }

    
    public void showMenu() {
        showOptions();

        // Variável input recebe o valor inserido pelo terminal
        SystemOut.print("\n > Digite a opção: ");
        String input = ler.nextLine();

        // Converte o input para um valor inteiro
        int option = Integer.parseInt(input);

        switch(option) {
            case 1:
                espiarCPU();
                break;

            case 2:
                executarProgramas();
                break;

            case 3:
                this.active = false;
                SystemOut.log("\nVolte logo!");
                break;
                
            default:
                showMenu();
                break;

        }
    }

    public void espiarCPU() {
        SystemOut.log("--------------------------");
        SystemOut.log("ESPIANDO CPU");

        // Pausa de 2 segundos;
        SystemOut.wait(2000);
        
        showMenu();
    }

    public void executarProgramas() {
        SystemOut.log("--------------------------");
        SystemOut.log("EXECUTANDO PROGRAMAS");
        
        // Pausa de 2 segundos;
        SystemOut.wait(2000);
        
        showMenu();
    }

	public void run(){

		// Programas
		

		// Menu
		while(active){
			showMenu();
		}		
	}	
}
