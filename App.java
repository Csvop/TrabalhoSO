public class App {
	public static void main(String[] args) {
		VM.get(); // Cria uma instância singleton da VM que é um gatilho para a execução das
					// threads

		// VM vm = new VM();

		// vm.mm.availableFrames[1] = false; // Simula um frame ocupado para evidenciar
		// o split do programa em 2 frames não consecutivos

		// //Cria processos na memória e coloca na fila de prontos
		// vm.load(Program.PC);
		// // vm.load(Program.PB);
		// // vm.load(Program.PA);
		// // vm.load(Program.TRAP_IN);
		// // vm.load(Program.TRAP_OUT);

		// vm.dump(vm.mm.availableFrames); // Printa os frames livres/ocupados (false =
		// ocupado)

		// SystemOut.log("\n\n\n All Programas loaded DUMP");
		// vm.dump(0, 100);

		// // Manda rodar a maquina com os programas carregados
		// vm.run();

		// SystemOut.log("\n\n\n FINAL DUMP"); // Limpou todos os programas da memória
		// vm.dump(0, 100);
	}
}
