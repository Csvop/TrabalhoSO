public class App {
    public static void main(String[] args) {
		VM vm = new VM();

		//Cria processos na memória e coloca na fila de prontos
		vm.load(Program.PA);
		vm.load(Program.PB);
		vm.load(Program.PC);

		//vm.dump(0, 100);

		//manda rodar a maquina com os programas carregados
		vm.run();

		System.out.println("\n\n\n FINAL DUMP");
		//vm.dump(0, 100);
	}
}
