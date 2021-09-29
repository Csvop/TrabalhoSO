public class App {
    public static void main(String[] args) {

		VM vm = new VM();

		//Cria processos na memória e coloca na fila de prontos
		vm.load(Program.PA);

		//manda rodar a maquina com os programas carregados
		vm.run();

	}
}
