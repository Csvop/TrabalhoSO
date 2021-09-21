package virtualmachine;
// PUCRS - Escola Politécnica - Sistemas Operacionais
// Prof. Fernando Dotti
// Código fornecido como parte da solução do projeto de Sistemas Operacionais
//
// Fase 1 - máquina virtual (vide enunciado correspondente)
//

import hardware.*;
import software.MemoryManager;
import util.*;

public class Sistema {

	public VM vm;

    public Sistema(){   // a VM com tratamento de interrupções
		 vm = new VM();
	}

	public static void main(String args[]) {
		Console.info("Iniciando Máquina Virtual... ");

		Sistema s = new Sistema();
		// s.test1(); 	// Executa o PA
		// s.test2(); 	// Executa o PB
		// s.test3(); 	// Executa o PC (not working)
		s.test(); 	// Programa de Trap In
		// s.test5(); 	// Programa de Trap Out

		Console.info("Encerrando Máquina Virtual...");
	}

	public void test(){
		Word[] p = new Programas().PA;
		Aux aux = new Aux();
		//aux.carga(p, Memory.get().data);
		MemoryManager mm = new MemoryManager();
		mm.allocate(p);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(Memory.get().data, 0, 50);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(Memory.get().data, 0, 50);
	}

	public void test1(){
		Aux aux = new Aux();
		Word[] p = new Programas().PA;
		aux.carga(p, Memory.get().data);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(Memory.get().data, 0, 50);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(Memory.get().data, 0, 50);
	}

	public void test2(){
		Aux aux = new Aux();
		Word[] p = new Programas().PB;
		aux.carga(p, Memory.get().data);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(Memory.get().data, 0, 20);
		System.out.println("---------------------------------- após execucao ");
		vm.cpu.run();
		aux.dump(Memory.get().data, 0, 20);
	}

	public void test3(){
		Aux aux = new Aux();
		Word[] p = new Programas().PC;
		aux.carga(p, Memory.get().data);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(Memory.get().data, 0, 50);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(Memory.get().data, 0, 50);
	}

	public void test4(){
		Aux aux = new Aux();
		Word[] p = new Programas().progIN;
		aux.carga(p, Memory.get().data);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(Memory.get().data, 0, 11);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(Memory.get().data, 0, 11);
	}

	public void test5(){
		Aux aux = new Aux();
		Word[] p = new Programas().progOUT;
		aux.carga(p, Memory.get().data);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(Memory.get().data, 0, 11);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(Memory.get().data, 0, 11);
	}


}