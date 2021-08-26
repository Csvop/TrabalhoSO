package virtualmachine;
// PUCRS - Escola Politécnica - Sistemas Operacionais
// Prof. Fernando Dotti
// Código fornecido como parte da solução do projeto de Sistemas Operacionais
//
// Fase 1 - máquina virtual (vide enunciado correspondente)
//

import java.util.*;

public class Sistema {
	
	// -------------------------------------------------------------------------------------------------------
	// --------------------- H A R D W A R E - definicoes de HW ---------------------------------------------- 

	// -------------------------------------------------------------------------------------------------------
	// --------------------- M E M O R I A -  definicoes de opcode e palavra de memoria ---------------------- 
	
	public class Word { 	// cada posicao da memoria tem uma instrucao (ou um dado)
		public Opcode opc; 	//
		public int r1; 		// indice do primeiro registrador da operacao (Rs ou Rd cfe opcode na tabela)
		public int r2; 		// indice do segundo registrador da operacao (Rc ou Rs cfe operacao)
		public int p; 		// parametro para instrucao (k ou A cfe operacao), ou o dado, se opcode = DADO

		public Word(Opcode _opc, int _r1, int _r2, int _p) {  
			opc = _opc;   r1 = _r1;    r2 = _r2;	p = _p;
		}
	}
    // -------------------------------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------------------------------
    // --------------------- C P U  -  definicoes da CPU ----------------------------------------------------- 

	public enum Opcode {
		DATA, ___,		    // se memoria nesta posicao tem um dado, usa DATA, se nao usada ee NULO ___
	
		// Instruções JUMP
	
		/** (-1, -1, k)  || Rd ← k */                                   JMP, 
		/** (Rs, -1, -1) || PC ← Rs */                                  JMPI, 
		/** (Rs, Rc, -1) || if Rc > 0 then PC ← Rs Else PC ← PC +1 */   JMPIG, 
		/** (Rs, Rc, -1) || if Rc < 0 then PC ← Rs Else PC ← PC +1 */   JMPIL, 
		/** (Rs, Rc, -1) || if Rc = 0 then PC ← Rs Else PC ← PC +1 */   JMPIE, 
		/** (-1, -1, A)  || PC ← [A] */                                 JMPIM, 
		/** (-1, Rc, A)  || if Rc > 0 then PC ← [A] Else PC ← PC +1 */  JMPIGM, 
		/** (-1, Rc, A)  || if Rc < 0 then PC ← [A] Else PC ← PC +1 */  JMPILM, 
		/** (-1, Rc, A)  || if Rc = 0 then PC ← [A] Else PC ← PC +1 */  JMPIEM, 
		
		// Instruções Aritméticas
	
		/** (Rd, -1, k)  || Rd ← Rd + k */      ADDI, 
		/** (Rd, -1, k)  || Rd ← Rd – k */      SUBI, 
		/** (Rd, Rs, -1) || Rd ← Rd + Rs */     ADD, 
		/** (Rd, Rs, -1) || Rd ← Rd - Rs */     SUB, 
		/** (Rd, Rs, -1) || Rd ← Rd * Rs */     MULT,
	
		// Instruções de Movimentação
	
		/** (Rd, -1, k)  || Rd ← k */           LDI,
		/** (Rd, -1, A)  || Rd ← [A] */         LDD, 
		/** (Rs, -1, A)  || [A] ← Rs */         STD,
		/** (Rd, Rs, -1) || Rd ← [Rs] */        LDX, 
		/** (Rd, Rs, -1) || [Rd] ←Rs */         STX, 
	
		/** T ← Ra, Ra ← Rb, Rb ← T */          SWAP, 
		/** parâmetros em r[8] e r[9] */        TRAP,
		
		STOP, 
	}
	

	public class CPU {
							// característica do processador: contexto da CPU ...
		private int pc; 			// ... composto de program counter,
		private Word ir; 			// instruction register,
		private int[] reg;       	// registradores da CPU

		private Word[] m;   // CPU acessa MEMORIA, guarda referencia 'm' a ela. memoria nao muda. ee sempre a mesma.
			
		private Aux aux = new Aux();

		public CPU(Word[] _m) {     // ref a MEMORIA e interrupt handler passada na criacao da CPU
			m = _m; 				// usa o atributo 'm' para acessar a memoria.
			reg = new int[10]; 		// aloca o espaço dos registradores
		}

		public void setContext(int _pc) {  // no futuro esta funcao vai ter que ser 
			pc = _pc;                                              // limite e pc (deve ser zero nesta versao)
		}
	
        public void showState(){
			 System.out.println("       "+ pc); 
			   System.out.print("           ");
			 for (int i=0; i<8; i++) { System.out.print("r"+i);   System.out.print(": "+reg[i]+"     "); };  
			 System.out.println("");
			 System.out.print("           ");  aux.dump(ir);
		}

		public void run() { 		// execucao da CPU supoe que o contexto da CPU, vide acima, esta devidamente setado
			while (true) { 	
				boolean flagEndInv = false;
				boolean flagIntrInv = false;
				boolean flagOverflow = false;
				int aux = 0;
				// ciclo de instrucoes. acaba cfe instrucao, veja cada caso.
				// FETCH
					ir = m[pc]; 	// busca posicao da memoria apontada por pc, guarda em ir
					//if debug
					    showState();
				// EXECUTA INSTRUCAO NO ir
					switch (ir.opc) { // para cada opcode, sua execução

						//Instruções JUMP
						case JMP: //  PC ← k
							if(!(ir.p < -1 || ir.p > 1023))
							{
								pc = ir.p;
							} else {
								flagOverflow =  true;
							}
						    break;

						case JMPI: // PC ← Rs 
							pc = ir.r1;
							break;

						case JMPIG: // If Rc > 0 Then PC ← Rs Else PC ← PC +1
							if (reg[ir.r2] > 0) {
								pc = reg[ir.r1];
							} else {
								pc++;
							}
							break;

						case JMPIL: // If Rc < 0 Then PC ← Rs Else PC ← PC +1
							if (reg[ir.r2] < 0) {
								pc = reg[ir.r1];
							} else {
								pc++;
							}
							break;

						case JMPIE: // If Rc = 0 Then PC ← Rs Else PC ← PC +1
							if (reg[ir.r2] == 0) {
								pc = reg[ir.r1];
							} else {
								pc++;
							}
							break;
						
						case JMPIM: // PC ← [A]
							pc = ir.p;
							break;
						
						case JMPIGM: // If Rc > 0 Then PC ← [A] Else PC ← PC +1
							if (reg[ir.r2] > 0) {
								pc = reg[ir.p];
							} else {
								pc++;
							}
							break;

						case JMPILM: // If Rc < 0 Then PC ← [A] Else PC ← PC +1
							if (reg[ir.r2] < 0) {
								pc = reg[ir.p];
							} else {
								pc++;
							}
							break;

						case JMPIEM: // If Rc = 0 Then PC ← [A] Else PC ← PC +1
							if (reg[ir.r2] == 0) {
								pc = reg[ir.p];
							} else {
								pc++;
							}
							break;

						//Instruções Aritméticas
						case ADDI: // Rd ← Rd + k
							aux = reg[ir.r1] + ir.p;
							if(aux == -2)
							{
								flagOverflow = true;
							} else {
								reg[ir.r1] = reg[ir.r1] + ir.p;
								pc++;
							}
							break;
						
						case SUBI: // Rd ← Rd – k 
							aux = reg[ir.r1] - ir.p;
							if(aux == -2)
							{
								flagOverflow = true;
							} else {
								reg[ir.r1] = reg[ir.r1] - ir.p;
								pc++;
							}
							break;

						case ADD: // Rd ← Rd + Rs
							aux = reg[ir.r1] + reg[ir.r2];
							if(aux == -2)
							{
								flagOverflow = true;
							} else {
								reg[ir.r1] = reg[ir.r1] + reg[ir.r2];
								pc++;
							}
							break;

						case SUB: // Rd ← Rd - Rs
							aux = reg[ir.r1] - reg[ir.r2];
							if(aux == -2)
							{
								flagOverflow = true;
							} else {
								reg[ir.r1] = reg[ir.r1] - reg[ir.r2];
								pc++;
							}
							break;

						case MULT: // Rd ← Rd * Rs
							aux = reg[ir.r1] * reg[ir.r2];
							if(aux == -2)
							{
								flagOverflow = true;
							} else {
								reg[ir.r1] = reg[ir.r1] * reg[ir.r2];
								pc++;
							}
							break;	
						
						//Instruções de Movimentação
						case LDI: // Rd ← k
							try {
								reg[ir.r1] = ir.p;
								pc++;
							} catch (Exception e) {
								flagEndInv = true;
							}
							break;
							
						
						case LDD: // Rd ← [A]
							try {
								reg[ir.r1] = m[ir.p].p; // m == memoria
								pc++;
							} catch (Exception e) {
								flagEndInv = true;
							}
							break;
							

						case STD: // [A] ← Rs
							try {
								m[ir.p].opc = Opcode.DATA;
								m[ir.p].p = reg[ir.r1];
								pc++;
							} catch (Exception e) {
								flagEndInv = true;
							}
							break;

						case LDX: // Rd ← [Rs]
							try {
								reg[ir.r1] = m[ir.r2].p; // m == memoria
								pc++;
							} catch (Exception e) {
								flagEndInv = true;
							}
							break;
							

						case STX: // [Rd] ←Rs
							try {
								m[reg[ir.r1]].opc = Opcode.DATA;      
								m[reg[ir.r1]].p = reg[ir.r2];          
								pc++;
							} catch (Exception e) {
								flagEndInv = true;
							}
							break;
							

						case STOP: // por enquanto, para execucao
							break;

						case TRAP: // trap
							if(reg[8] == 1) { //IN
								Scanner in = new Scanner(System.in);
								m[reg[9]].opc = Opcode.DATA;
								m[reg[9]].p = in.nextInt();
								in.close();
							} else { //OUT
								System.out.println("\nOUTPUT -> " + m[reg[9]].p + " <- OUTPUT\n");
							}
							pc++;
							break;

						default:
							// INSTRUCAO INVALIDA
							flagIntrInv = true;
							break;
					}
				
				// VERIFICA INTERRUPÇÃO !!! - TERCEIRA FASE DO CICLO DE INSTRUÇÕES
				if (ir.opc==Opcode.STOP) {   
					break; // break sai do loop da cpu
				}

				// ENDERECO INVÁLIDO
				if(flagEndInv == true) 
				{
					System.out.println("\n-------------------------------------------------");
					System.out.println("---------INTERRUPÇÃO: Endereço Inválido!---------");
					System.out.println("-------------------------------------------------\n");
					break;
				}

				// INSTRUCAO INVALIDA
				if(flagIntrInv == true)
				{
					System.out.println("\n-------------------------------------------------");
					System.out.println("---------NTERRUPÇÃO: Instrução Inválida!---------");
					System.out.println("-------------------------------------------------\n");
					break;
				}

				// OVERFLOW
				if(flagOverflow == true)
				{
					System.out.println("\n----------------------------------------");
					System.out.println("---------INTERRUPÇÃO: Overflow!---------");
					System.out.println("----------------------------------------\n");
					break;
				}
			}
		}
	}
    // ------------------ C P U - fim ------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------

	
    // ------------------- V M  - constituida de CPU e MEMORIA -----------------------------------------------
    // -------------------------- atributos e construcao da VM -----------------------------------------------
	public class VM {
		public int tamMem;    
        public Word[] m;     
        public CPU cpu;    

        public VM(){   // vm deve ser configurada com endereço de tratamento de interrupcoes
	     // memória
  		 	 tamMem = 1024;
			 m = new Word[tamMem]; // m ee a memoria
			 for (int i=0; i<tamMem; i++) { 
				 m[i] = new Word(Opcode.___,-1,-1,-1);
			 };
	  	 // cpu
			 cpu = new CPU(m);
	    }	
	}
    // ------------------- V M  - fim ------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------

    // --------------------H A R D W A R E - fim -------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------
	// ------------------- S O F T W A R E - inicio ----------------------------------------------------------

	// ------------------- VAZIO
	

	// -------------------------------------------------------------------------------------------------------
    // -------------------  S I S T E M A --------------------------------------------------------------------

	public VM vm;

    public Sistema(){   // a VM com tratamento de interrupções
		 vm = new VM();
	}

    // -------------------  S I S T E M A - fim --------------------------------------------------------------
    // -------------------------------------------------------------------------------------------------------

	
    // -------------------------------------------------------------------------------------------------------
    // ------------------- instancia e testa sistema
	public static void main(String args[]) {
		Sistema s = new Sistema();
		s.test1();
	}
    // -------------------------------------------------------------------------------------------------------
    // --------------- TUDO ABAIXO DE MAIN É AUXILIAR PARA FUNCIONAMENTO DO SISTEMA - nao faz parte 

	// -------------------------------------------- teste do sistema ,  veja classe de programas
	public void test1(){
		Aux aux = new Aux();
		Word[] p = new Programas().PA;
		aux.carga(p, vm.m);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(vm.m, 0, 50);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(vm.m, 0, 50);
	}

	public void test2(){
		Aux aux = new Aux();
		Word[] p = new Programas().PB;
		aux.carga(p, vm.m);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(vm.m, 0, 20);
		System.out.println("---------------------------------- após execucao ");
		vm.cpu.run();
		aux.dump(vm.m, 0, 20);
	}

	public void test3(){
		Aux aux = new Aux();
		Word[] p = new Programas().PC;
		aux.carga(p, vm.m);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(vm.m, 0, 50);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(vm.m, 0, 50);
	}

	public void test4(){
		Aux aux = new Aux();
		Word[] p = new Programas().fibonacci10;
		aux.carga(p, vm.m);
		vm.cpu.setContext(0);
		System.out.println("---------------------------------- programa carregado ");
		aux.dump(vm.m, 0, 30);
		vm.cpu.run();
		System.out.println("---------------------------------- após execucao ");
		aux.dump(vm.m, 0, 35);
	}
	// -------------------------------------------  classes e funcoes auxiliares
    public class Aux {
		public void dump(Word w) {
			System.out.print("[ "); 
			System.out.print(w.opc); System.out.print(", ");
			System.out.print(w.r1);  System.out.print(", ");
			System.out.print(w.r2);  System.out.print(", ");
			System.out.print(w.p);  System.out.println("  ] ");
		}
		public void dump(Word[] m, int ini, int fim) {
			for (int i = ini; i < fim; i++) {
				System.out.print(i); System.out.print(":  ");  dump(m[i]);
			}
		}
		public void carga(Word[] p, Word[] m) {
			for (int i = 0; i < p.length; i++) {
				m[i].opc = p[i].opc;     m[i].r1 = p[i].r1;     m[i].r2 = p[i].r2;     m[i].p = p[i].p;
			}
		}
   }
   // -------------------------------------------  fim classes e funcoes auxiliares
	
   //  -------------------------------------------- programas aa disposicao para copiar na memoria (vide aux.carga)
   public class Programas {
	   public Word[] progIN = new Word[] {
		    //       OPCODE      R1  R2  P         :: VEJA AS COLUNAS VERMELHAS DA TABELA DE DEFINICAO DE OPERACOES
			//                                     :: -1 SIGNIFICA QUE O PARAMETRO NAO EXISTE PARA A OPERACAO DEFINIDA
		    new Word(Opcode.LDI, 8, -1, 1), 	
			new Word(Opcode.LDI, 9, -1, 33),  
			new Word(Opcode.TRAP, -1, -1, -1),
			new Word(Opcode.STOP, -1, -1, -1) };

		public Word[] progOUT = new Word[] {
			//       OPCODE      R1  R2  P         :: VEJA AS COLUNAS VERMELHAS DA TABELA DE DEFINICAO DE OPERACOES
			//                                     :: -1 SIGNIFICA QUE O PARAMETRO NAO EXISTE PARA A OPERACAO DEFINIDA
			new Word(Opcode.LDI, 0, -1, 999),
			new Word(Opcode.STD, 0, -1, 33),
			new Word(Opcode.LDI, 8, -1, 2), 	
			new Word(Opcode.LDI, 9, -1, 33),  
			new Word(Opcode.TRAP, -1, -1, -1),
			new Word(Opcode.STOP, -1, -1, -1) };

	   public Word[] fibonacci10 = new Word[] { // mesmo que prog exemplo, so que usa r0 no lugar de r8
			
			new Word(Opcode.LDI, 4, -1, 10), //Numero de numeros na sequencia !!é um INDEX!!
			new Word(Opcode.LDI, 1, -1, 0), 
			new Word(Opcode.STD, 1, -1, 20), // 20 posicao de memoria onde inicia a serie de fibonacci gerada  
			new Word(Opcode.LDI, 2, -1, 1),
			new Word(Opcode.STD, 2, -1, 21),      
			new Word(Opcode.LDI, 0, -1, 22),       
			new Word(Opcode.LDI, 6, -1, 6),
			new Word(Opcode.LDI, 7, -1, 20), // final da escala
			new Word(Opcode.ADD, 7, 4, -1),
			new Word(Opcode.LDI, 3, -1, 0), 
			new Word(Opcode.ADD, 3, 1, -1),
			new Word(Opcode.LDI, 1, -1, 0), 
			new Word(Opcode.ADD, 1, 2, -1), 
			new Word(Opcode.ADD, 2, 3, -1),
			new Word(Opcode.STX, 0, 2, -1), 
			new Word(Opcode.ADDI, 0, -1, 1), 
			new Word(Opcode.SUB, 7, 0, -1),
			new Word(Opcode.JMPIG, 6, 7, -1), 
			new Word(Opcode.STOP, -1, -1, -1),   // POS 16
		};   

	   public Word[] fatorial = new Word[] { 	 // este fatorial so aceita valores positivos.   nao pode ser zero
												 // linha   coment
			new Word(Opcode.LDI, 0, -1, 6),      // 0   	r0 é valor a calcular fatorial
			new Word(Opcode.LDI, 1, -1, 1),      // 1   	r1 é 1 para multiplicar (por r0)
			new Word(Opcode.LDI, 6, -1, 1),      // 2   	r6 é 1 para ser o decremento
			new Word(Opcode.LDI, 7, -1, 8),      // 3   	r7 tem posicao de stop do programa = 8
			new Word(Opcode.JMPIE, 7, 0, 0),     // 4   	se r0=0 pula para r7(=8)
			new Word(Opcode.MULT, 1, 0, -1),     // 5   	r1 = r1 * r0
			new Word(Opcode.SUB, 0, 6, -1),      // 6   	decrementa r0 1 
			new Word(Opcode.JMP, -1, -1, 4),     // 7   	vai p posicao 4
			new Word(Opcode.STD, 1, -1, 10),     // 8   	coloca valor de r1 na posição 10
			new Word(Opcode.STOP, -1, -1, -1),    // 9   	stop
			new Word(Opcode.DATA, -1, -1, -1) };  // 10   ao final o valor do fatorial estará na posição 10 da memória        
			
		public Word[] PA = new Word[] { // mesmo que prog exemplo, so que usa r0 no lugar de r8
			new Word(Opcode.LDI, 4, -1, 10),//0 Numero de numeros na sequencia !!é um INDEX!!
			new Word(Opcode.LDI, 1, -1, 20),//1
			new Word(Opcode.JMPIL, 1, 4, -1),//2
			new Word(Opcode.LDI, 1, -1, 0),//3
			new Word(Opcode.STD, 1, -1, 21),//4 20 posicao de memoria onde inicia a serie de fibonacci gerada  
			new Word(Opcode.LDI, 2, -1, 1),//5
			new Word(Opcode.STD, 2, -1, 22),//6    
			new Word(Opcode.LDI, 0, -1, 23),//7    
			new Word(Opcode.LDI, 6, -1, 8),//8
			new Word(Opcode.LDI, 7, -1, 21),//9 final da escala
			new Word(Opcode.ADD, 7, 4, -1),//10
			new Word(Opcode.LDI, 3, -1, 0), //11
			new Word(Opcode.ADD, 3, 1, -1),//12
			new Word(Opcode.LDI, 1, -1, 0), //13
			new Word(Opcode.ADD, 1, 2, -1), //14
			new Word(Opcode.ADD, 2, 3, -1),//15
			new Word(Opcode.STX, 0, 2, -1), //16
			new Word(Opcode.ADDI, 0, -1, 1), //17
			new Word(Opcode.SUB, 7, 0, -1),//18
			new Word(Opcode.JMPIG, 6, 7, -1), //19
			new Word(Opcode.STOP, -1, -1, -1),//20
		};  // ate aqui - serie de fibonacci ficara armazenada

		public Word[] PB = new Word[] {
			new Word(Opcode.LDI, 0, -1, 6),//  0 - r0 é valor a calcular fatorial
			new Word(Opcode.LDI, 1, -1, 12), //1
			new Word(Opcode.JMPIL, 1, 0, -1),//2 -IF(R0 < 0) -> posicao 6
			new Word(Opcode.LDI, 1, -1, 1), // 3   	r1 é 1 para multiplicar (por r0)
			new Word(Opcode.LDI, 6, -1, 1), // 4   	r6 é 1 para ser o decremento
			new Word(Opcode.LDI, 7, -1, 10), //5  	r7 tem posicao de stop do programa = 10
			new Word(Opcode.JMPIE, 7, 0, 0),// 6    se r0=0 pula para r7(=10)
			new Word(Opcode.MULT, 1, 0, -1),// 7  	r1 = r1 * r0
			new Word(Opcode.SUB, 0, 6, -1), // 8  	decrementa r0 1 
			new Word(Opcode.JMP, -1, -1, 6),//9     vai p posicao 6
			new Word(Opcode.STD, 1, -1, 15),// 10	coloca valor de r1 na posição 40
			new Word(Opcode.STOP, -1, -1, -1),//11
			new Word(Opcode.STD, 0, -1, 15),// 12	coloca valor de r1 na posição 40
			new Word(Opcode.STOP, -1, -1, -1),//13
		};

		public Word[] PC = new Word[]{
            new Word(Opcode.LDI, 0, -1, 7),  //carregando valores do array na memoria
            new Word(Opcode.STD, 0, -1, 40),

            new Word(Opcode.LDI, 0, -1, 12),
            new Word(Opcode.STD, 0, -1, 41),

            new Word(Opcode.LDI, 0, -1, 15),
            new Word(Opcode.STD, 0, -1, 42),

            new Word(Opcode.LDI, 0, -1, 9),
            new Word(Opcode.STD, 0, -1, 43),

            new Word(Opcode.LDI, 0, -1, 32),
            new Word(Opcode.STD, 0, -1, 44),

            new Word(Opcode.LDI, 0, -1, 2),
            new Word(Opcode.STD, 0, -1, 45),

            new Word(Opcode.LDI, 0, -1, -5),
            new Word(Opcode.STD, 0, -1, 46),// fim do array

            new Word(Opcode.LDI, 0, -1, 40), // guardando no registrador 0 o valor 40
            new Word(Opcode.LDI, 3, -1, 6), // guardando no registrador 3 o valor 7
            new Word(Opcode.LDI, 4, -1, 6),// guardando no registrador 4 o valor 6
            new Word(Opcode.LDI, 5, -1, 20),// guardando no registrador 5 o valor 20
            new Word(Opcode.LDI, 6, -1, 33),// guardando no registrador 6 o valor 33
            new Word(Opcode.LDI, 7, -1, 38),// guardando no registrador 7 o valor 38


            new Word(Opcode.JMPIE, 6, 3, -1), //inicio loop - verifica se o valor do registrador 3 é zero, se sim vai para a posição do registrador 6
            new Word(Opcode.SUBI, 3, -1, 1),// se o registrador 3 não é zero, armazena no registrador 3 o valor de r3-1
            new Word(Opcode.LDX, 1, 0, -1),// armazena no registrador 1 o valor da memória indicado pelo registrador 0
            new Word(Opcode.ADDI, 0, -1, 1),// acrescenta 1 no registrador 0
            new Word(Opcode.LDX, 2, 0, -1),// armazena no registrador 2 o valor da memória indicado pelo registrador 0
            new Word(Opcode.SUB, 2, 1, -1),// subtrai do registrador 2 o valor do registrador 1
            new Word(Opcode.JMPIG, 5, 2, -1),//verifica se o registrador 2 é maior que zero, se sim pula para a linha registrada no registrador 5

            new Word(Opcode.LDX, 2, 0, -1),// armazena no registrador 2 o valor indicado pelo registrador 0
            new Word(Opcode.STX, 0, 1, -1),// armazena na posição da memória indicada pelo registrador 0 o valor do registrador 2
            new Word(Opcode.SUBI, 0, -1, 1),// subtrai 1 do registrador 0
            new Word(Opcode.STX, 0, 2, -1),// armazena na posição da memória indicada pelo registrador
            new Word(Opcode.ADDI, 0, -1, 1),// adiciona 1 ao registrador 0
            new Word(Opcode.JMPI, 5, 0, -1),// Coloca no program counter o valor do registrador 5

            new Word(Opcode.JMPIE, 7, 4, -1),// verifica se o valor do registrador 4 é igual a zero, se sim pula para a linha indicada pelo registrador 7
            new Word(Opcode.SUBI, 4, -1, 1),// subtrai 1 do registrador 4
            new Word(Opcode.LDI, 0, -1, 40),// armazena no registrador 0 o valor 40
            new Word(Opcode.LDI, 3, -1, 6),// armazena no registrador 3 o valor 6
            new Word(Opcode.JMPIG, 5, 0, -1),//fim do loop - verifica se o valor do registrador 0 é maior do que zero, se sim pula para a linha no valor do registrador 5

            new Word(Opcode.STOP, -1, -1, -1)
    };
    }
}