package virtualmachine;

import hardware.Memory;
import hardware.Word;

public class SistemaTestesDaEntrega1 {
	public VM vm;

    public Sistema(){   // a VM com tratamento de interrupções
		 vm = new VM();
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