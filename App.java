public class App {
    public static void main(String[] args) {
		VM vm = new VM();

		vm.mm.availableFrames[1] = false;

		//Cria processos na memória e coloca na fila de prontos
		vm.load(Program.PC);
		vm.load(Program.PB);
		vm.load(Program.PA);

		Console.log("\n\n\n All Programas loaded DUMP");
		vm.dump(0, 100);

		//manda rodar a maquina com os programas carregados
		vm.run();

		Console.log("\n\n\n FINAL DUMP");
		vm.dump(0, 100);
	}
}
