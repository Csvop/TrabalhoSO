package virtualmachine;
// PUCRS - Escola Politécnica - Sistemas Operacionais
// Prof. Fernando Dotti
// Código fornecido como parte da solução do projeto de Sistemas Operacionais
//
// Fase 1 - máquina virtual (vide enunciado correspondente)
//

import software.*;
import util.*;

public class Sistema {

	public VM vm;

    public Sistema(){   // a VM com tratamento de interrupções
		vm = VM.get();
	}

	public static void main(String args[]) {
		Console.info("Iniciando Máquina Virtual... ");

		Sistema s = new Sistema();

		s.trapInAndOutTest();
		//s.trapInTest();
		//s.trapOutTest();
		// s.test1(); 	
		// s.test2(); 	// Executa o PB
		// s.test3(); 	// Executa o PC (not working)
		
		Console.info("Encerrando Máquina Virtual...");
	}

	public void trapInAndOutTest(){
		Console.info("Iniciando trapInTest()");
		

		PCB processTrapIn = vm.pm.create(Program.TRAP_IN);
		Console.debug(" > Processo TRAP_IN criado.");

		VM.get().mm.dump(0, 40);
		VM.get().mm.dump(VM.get().mm.availableFrames);


		PCB processTrapOut = vm.pm.create(Program.TRAP_OUT);
		Console.debug(" > Processo TRAP_OUT criado.");

		VM.get().mm.dump(0, 40);
		VM.get().mm.dump(VM.get().mm.availableFrames);
		
		Console.debug(" > CPU.run() ");
		vm.cpu.setContext(
			processTrapIn.getAllocatedPages(),  
			processTrapIn.pc, 
			processTrapIn.getId(), 
			processTrapIn.reg
		);
		vm.cpu.run();

		Console.debug(" > Programa TRAP_IN encerrado.");
		VM.get().mm.dump(0, 40);


		// Console.info("Iniciando trapOutTest()");
		
		// Console.debug(" > CPU.run() ");
		// vm.cpu.setContext(trapOutPages, 0, vm.cpu.currentProcessId, vm.cpu.reg);
		// vm.cpu.run();

		// Console.debug(" > Programa TRAP_IN encerrado.");
		// VM.get().mm.dump(0, 40);

	}

	// public void trapInTest(){
	// 	Console.info("Iniciando trapInTest()");

	// 	MemoryManager mm = new MemoryManager();
	// 	ArrayList<Integer> trapInPages = mm.allocate(Program.TRAP_IN);
	// 	Console.debug(" > Programa TRAP_IN carregado.");
	// 	mm.dump(Memory.get().data, 0, 40);
		
	// 	Console.debug(" > CPU.run() ");
	// 	//vm.cpu.setContext(0, trapInPages);
	// 	vm.cpu.run();

	// 	Console.debug(" > Programa TRAP_IN encerrado.");
	// 	mm.dump(Memory.get().data, 0, 40);

	// 	Console.debug(" > Memory.clearMemory() ");
	// 	Memory.get().clearMemory();
	// }
	
	// public void trapOutTest(){
	// 	Console.info("Iniciando trapOutTest()");

	// 	MemoryManager mm = new MemoryManager();
	// 	ArrayList<Integer> trapOutPages = mm.allocate(Program.TRAP_OUT);

	// 	Console.debug(" > Programa TRAP_OUT carregado.");
	// 	mm.dump(Memory.get().data, 0, 40);
		
	// 	Console.debug(" > CPU.run() ");
	// 	//vm.cpu.setContext(0, trapOutPages);
	// 	vm.cpu.run();

	// 	Console.debug(" > Programa TRAP_OUT encerrado.");
	// 	mm.dump(Memory.get().data, 0, 40);

	// 	Console.debug(" > Memory.clearMemory() ");
	// 	Memory.get().clearMemory();
	// }

	// public void test1(){
	// 	Console.debug(" > Iniciando test1()");

	// 	MemoryManager mm = new MemoryManager();
	// 	mm.allocate(Program.PA);

	// 	Console.debug(" > Programa A carregado.");
	// 	mm.dump(Memory.get().data, 0, 50);
		
	// 	Console.debug(" > CPU.run() ");
	// 	//vm.cpu.setContext(0);
	// 	vm.cpu.run();

	// 	Console.debug(" > Programa A encerrado.");
	// 	mm.dump(Memory.get().data, 0, 50);

	// 	Console.debug(" > Memory.clearMemory() ");
	// 	Memory.get().clearMemory();
	// }

	// public void test2(){
	// 	Console.debug(" > Iniciando test2()");

	// 	MemoryManager mm = new MemoryManager();
	// 	mm.allocate(Program.PB);

	// 	Console.debug(" > Programa B carregado.");
	// 	mm.dump(Memory.get().data, 0, 20);
		
	// 	Console.debug(" > CPU.run() ");
	// 	//vm.cpu.setContext(0);
	// 	vm.cpu.run();

	// 	Console.debug(" > Programa B encerrado.");
	// 	mm.dump(Memory.get().data, 0, 20);

	// 	Console.debug(" > Memory.clearMemory() ");
	// 	Memory.get().clearMemory();
	// }

	// public void test3(){
	// 	Console.debug(" > Iniciando test3()");

	// 	MemoryManager mm = new MemoryManager();
	// 	mm.allocate(Program.PC);

	// 	Console.debug(" > Programa C carregado.");
	// 	mm.dump(Memory.get().data, 0, 50);
		
	// 	Console.debug(" > CPU.run() ");
	// 	//vm.cpu.setContext(0);
	// 	vm.cpu.run();

	// 	Console.debug(" > Programa C encerrado.");
	// 	mm.dump(Memory.get().data, 0, 50);

	// 	Console.debug(" > Memory.clearMemory() ");
	// 	Memory.get().clearMemory();
	// }

}