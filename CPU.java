import java.util.ArrayList;
import java.util.List;

public class CPU {
    // característica do processador: contexto da CPU ...
    public int pc; // ... composto de program counter,
    public Word ir; // instruction register,
    public int[] reg; // registradores da CPU
    public Word[] memory;
    public MemoryManager mm;
    public List<Integer> paginas; //Paginas Alocadas
    public Interrupt interrupt;
    public int timer; //conta instrucoes


    public CPU(Word[] memory, MemoryManager mm) { // ref a MEMORIA e interrupt handler passada na criacao da CPU
        this.memory = memory; // usa o atributo 'm' para acessar a memoria.
        reg = new int[10]; // aloca o espaço dos registradores
        this.mm = mm;
    }

/**
     * Converte de um endereço lógico em um endereço físico.
     */
    private int translate(int pc) {

        boolean isValid = true;

        int pageSize = mm.pageSize;
        int index = pc / pageSize;
        int res = 0;

        try {
            paginas.get(index);
        } catch (Exception e) {
            interrupt = Interrupt.INVALID_ADDRESS;
            isValid = false;
        }

        if (isValid) {
            res = (paginas.get(index) * pageSize) + (pc % pageSize);
        }

        return res;
    }

    public void setContext(ArrayList<Integer> _paginas, int _pc, int _id, int[] _reg) { // no futuro esta funcao vai ter
                                                                                        // que ser
        paginas = _paginas;
        pc = _pc; // limite e pc (deve ser zero nesta versao)
        reg = _reg;
        interrupt = Interrupt.NONE;
    }

    public Interrupt run() { // execucao da CPU supoe que o contexto da CPU, vide acima, esta devidamente setado

        timer = 0;
        while (interrupt == Interrupt.NONE) {
            ir = memory[translate(pc)]; // busca posicao da memoria apontada por pc, guarda em ir
            
            int aux = 0;
             
            timer++;
            if(timer >= 5) {
                interrupt = Interrupt.TIMER;
            }

            switch (ir.opc) { // para cada opcode, sua execução

                // Instruções JUMP
                case JMP: // PC ← k
                    if (!(ir.p < -1 || ir.p > 1023)) {
                        pc = ir.p;
                    } else {
                        interrupt = Interrupt.OVERFLOW;
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

                // Instruções Aritméticas
                case ADDI: // Rd ← Rd + k
                    aux = reg[ir.r1] + ir.p;
                    if (aux == -2) {
                        interrupt = Interrupt.OVERFLOW;
                    } else {
                        reg[ir.r1] = reg[ir.r1] + ir.p;
                        pc++;
                    }
                    break;

                case SUBI: // Rd ← Rd – k
                    aux = reg[ir.r1] - ir.p;
                    if (aux == -2) {
                        interrupt = Interrupt.OVERFLOW;
                    } else {
                        reg[ir.r1] = reg[ir.r1] - ir.p;
                        pc++;
                    }
                    break;

                case ADD: // Rd ← Rd + Rs
                    aux = reg[ir.r1] + reg[ir.r2];
                    if (aux == -2) {
                        interrupt = Interrupt.OVERFLOW;
                    } else {
                        reg[ir.r1] = reg[ir.r1] + reg[ir.r2];
                        pc++;
                    }
                    break;

                case SUB: // Rd ← Rd - Rs
                    aux = reg[ir.r1] - reg[ir.r2];
                    if (aux == -2) {
                        interrupt = Interrupt.OVERFLOW;
                    } else {
                        reg[ir.r1] = reg[ir.r1] - reg[ir.r2];
                        pc++;
                    }
                    break;

                case MULT: // Rd ← Rd * Rs
                    aux = reg[ir.r1] * reg[ir.r2];
                    if (aux == -2) {
                        interrupt = Interrupt.OVERFLOW;
                    } else {
                        reg[ir.r1] = reg[ir.r1] * reg[ir.r2];
                        pc++;
                    }
                    break;

                // Instruções de Movimentação
                case LDI: // Rd ← k
                    try {
                        reg[ir.r1] = ir.p;
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case LDD: // Rd ← [A]
                    try {
                        reg[ir.r1] = memory[translate(ir.p)].p; // m == memoria
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case STD: // [A] ← Rs
                    try {
                        memory[translate(ir.p)].opc = Opcode.DATA;
                        memory[translate(ir.p)].p = reg[ir.r1];
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case LDX: // Rd ← [Rs]
                    try {
                        reg[ir.r1] = memory[translate(ir.r2)].p; // m == memoria
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case STX: // [Rd] ←Rs
                    try {
                        memory[translate(reg[ir.r1])].opc = Opcode.DATA;
                        memory[translate(reg[ir.r1])].p = reg[ir.r2];
                        pc++;
                    } catch (Exception e) {
                        interrupt = Interrupt.INVALID_ADDRESS;
                    }
                    break;

                case STOP: // por enquanto, para execucao
                    interrupt = Interrupt.STOP;
                    break;

                case TRAP: // trap
                    interrupt = Interrupt.TRAP;
                    pc++;
                    break;

                default:
                    interrupt = Interrupt.INVALID_INSTRUCTION;
                    break;
            }

            if (interrupt != Interrupt.NONE) {
				System.out.print("v Interrupção v");
				switch (interrupt){
                    case INVALID_ADDRESS:
                        Console.print("\n");
                        Console.warn(" > Interrupt.INVALID_ADDRESS");
                        return interrupt;

                    case INVALID_INSTRUCTION:
                        Console.print("\n");
                        Console.warn(" > Interrupt.INVALID_INSTRUCTION");
                        return interrupt;

                    case OVERFLOW:
                        Console.print("\n");
                        Console.warn(" > Interrupt.OVERFLOW");
                        return interrupt;

                    case TIMER:
                        Console.print("\n");
                        Console.warn(" > Interrupt.TIMER");
                        return interrupt;

                    case TRAP:
                        TrapHandling.trap(this);
                        interrupt = Interrupt.NONE;
                        return interrupt;

                    case STOP:
                        Console.print("\n");
                        Console.warn(" > Interrupt.STOP");
                        return interrupt;

                    case NONE:
                        break;
                        
                    default:
                        break;
                }
			}
        }
        return null;
    }
}
